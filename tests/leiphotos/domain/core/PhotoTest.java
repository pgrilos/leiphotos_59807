package leiphotos.domain.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;


import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.metadatareader.JpegMetadataException;
import leiphotos.domain.metadatareader.JpegMetadataReader;
import leiphotos.domain.metadatareader.JpegMetadataReaderFactory;

class PhotoTest {

	@Test
	void testCreatePhotoWithoutGPS() throws FileNotFoundException {
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		File expectedFile = new File("photos/Cars.jpg");
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		PhotoMetadata pm = new PhotoMetadata(null, expectedAddedDate, expectedTitle, "Teste");
		IPhoto m = new MockPhoto(expectedTitle, expectedCapturedDate, expectedAddedDate, false, expectedFile, expectedFile.length(), false);
		IPhoto p = new Photo(expectedTitle, expectedAddedDate, pm , expectedFile);
		assertEquals(m.getPlace(), p.getPlace());

	}

	@Test
	void testCreatePhotoWithGPS() throws FileNotFoundException, JpegMetadataException {
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		File expectedFile = new File("photos/AnelJVasconcelos.jpeg");
		String expectedTitle = "Test Photo";
		JpegMetadataReader x = JpegMetadataReaderFactory.createMetadataReader(expectedFile);
		GPSLocation gps = new GPSLocation(x.getGpsLocation()[0], x.getGpsLocation()[1], " ");
		PhotoMetadata pm = new PhotoMetadata(gps, expectedCapturedDate, x.getCamera(), expectedTitle);
		IPhoto p = new Photo(expectedTitle, expectedAddedDate, pm,expectedFile);
		assertEquals(p.getPlace(), Optional.of(gps));
	}

	@Test
	void testToggleFavourite() {
		File expectedFile = new File("tests/leiphotos/domain/core/Cars.jpeg");
		String expectedTitle = "Test Photo";
		IPhoto m = new MockPhoto(expectedTitle, null, null, true, null, 0, false);
		IPhoto p = new Photo(expectedTitle, null, null,expectedFile);
		p.toggleFavourite();
		assertEquals(m.isFavourite(), p.isFavourite());
	}

	@Test
	void testSize() { //requires the use of a mock file class
		long expectedSize = 1024;
		MockFile expectedFile = new MockFile("tests/leiphotos/domain/core/Cars.jpeg",expectedSize);
		String expectedTitle = "Test Photo";
		LocalDateTime expectedAddedDate = LocalDateTime.now();
		IPhoto p = new Photo(expectedTitle, expectedAddedDate, null, expectedFile);
		assertEquals(expectedFile.length(), p.size());
	}

	@Test
	void testNoMatches() throws FileNotFoundException, JpegMetadataException {
		String regexp = "Exp.*";
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		File expectedFile = new File("photos/AnelJVasconcelos.jpeg");
		String expectedTitle = "Test Photo";
		JpegMetadataReader x = JpegMetadataReaderFactory.createMetadataReader(expectedFile);
		GPSLocation gps = new GPSLocation(x.getGpsLocation()[0], x.getGpsLocation()[1], " ");
		PhotoMetadata pm = new PhotoMetadata(gps, expectedCapturedDate, x.getCamera(), expectedTitle);
		IPhoto p = new Photo("Title", null, pm, expectedFile );
		assertFalse(p.matches(regexp));
	}


	@Test
	void testMatchesTitle() {
		String regexp = "Test.*";
		File expectedFile = new File("tests/leiphotos/domain/core/Cars.jpeg");
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		PhotoMetadata pm = new PhotoMetadata(null, expectedCapturedDate, regexp, regexp);
		IPhoto p = new Photo("Test", expectedCapturedDate, pm,expectedFile);
		assertTrue(p.matches(regexp));

	}


	@Test
	void testMatchesFile() {
		String regexp = "photos/Bean.jpeg";
		File expectedFile = new File("photos/Bean.jpeg");
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		PhotoMetadata pm = new PhotoMetadata(null, expectedCapturedDate, regexp, regexp);
		IPhoto p = new Photo(null, LocalDateTime.now(),pm ,expectedFile);
		assertTrue(p.matches(regexp));
	}

	@Test
	void testEquals() throws JpegMetadataException, FileNotFoundException {
		File file1 = new File("photos/AnelJVasconcelos.jpeg");
		File file2 = new File("photos/Book.jpeg");
		File file3 = new File("photos/AnelJVasconcelos.jpeg");
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		String expectedTitle = "Test";
		LocalDateTime expectedAddedDate = LocalDateTime.now();

		JpegMetadataReader read = JpegMetadataReaderFactory.createMetadataReader(file1);
		GPSLocation gps = new GPSLocation(read.getGpsLocation()[0], read.getGpsLocation()[1], "null");
		PhotoMetadata pm = new PhotoMetadata(gps, expectedCapturedDate, read.getCamera(), read.getManufacturer());
		Photo photo = new Photo(expectedTitle, expectedAddedDate, pm, file1);

		JpegMetadataReader read1 = JpegMetadataReaderFactory.createMetadataReader(file2);
		
		GPSLocation gps1 = new GPSLocation(read1.getGpsLocation()[0], read1.getGpsLocation()[1], "null");

		Photo photo1 = new Photo(expectedTitle, expectedAddedDate, new PhotoMetadata(gps1, expectedCapturedDate, read1.getCamera(), read1.getManufacturer()), file2);

		JpegMetadataReader read2 = JpegMetadataReaderFactory.createMetadataReader(file3);
		
		GPSLocation gps2 = new GPSLocation(read2.getGpsLocation()[0], read2.getGpsLocation()[1], "null");

		PhotoMetadata pm1 = new PhotoMetadata(gps2, expectedCapturedDate, read2.getCamera(), read2.getManufacturer());

		Photo photo2 = new Photo(expectedTitle, expectedAddedDate, pm1, file3);


		assertFalse(photo.equals(photo1));
		assertTrue(photo.equals(photo2));
		assertFalse(photo1.equals(photo2));
	}

	@Test
	void testHashCode() throws JpegMetadataException, FileNotFoundException {
		File file1 = new File("photos/Book.jpeg");
		File file2 = new File("photos/Book.jpeg");
		LocalDateTime expectedCapturedDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		String expectedTitle = "Test";
		LocalDateTime expectedAddedDate = LocalDateTime.now();

		JpegMetadataReader read = JpegMetadataReaderFactory.createMetadataReader(file1);
		GPSLocation gps = new GPSLocation(read.getGpsLocation()[0], read.getGpsLocation()[1], "null");
		PhotoMetadata pm = new PhotoMetadata(gps, expectedCapturedDate, read.getCamera(), read.getManufacturer());
		Photo photo = new Photo(expectedTitle, expectedAddedDate, pm, file1);

		JpegMetadataReader read1 = JpegMetadataReaderFactory.createMetadataReader(file2);
		
		GPSLocation gps1 = new GPSLocation(read1.getGpsLocation()[0], read1.getGpsLocation()[1], "null");

		Photo photo1 = new Photo(expectedTitle, expectedAddedDate, new PhotoMetadata(gps1, expectedCapturedDate, read1.getCamera(), read1.getManufacturer()), file2);

		assertEquals(photo.hashCode(), photo1.hashCode());

	}

}