package leiphotos.domain.core;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import leiphotos.domain.facade.IPhoto;

class RecentlyDeletedLibraryTest {

	private static final int SECONDS_IN_TRASH = 16; 
	private static final int SECONDS_TO_CHECK = 14;  
	
	private RecentlyDeletedLibrary library;

	@BeforeEach
	void setUp() {
		library = new RecentlyDeletedLibrary();
	}

	@Test
	void testAddPhoto() {
		MockPhoto photo = new MockPhoto(new File("Test.jpg"));

		assertTrue(library.addPhoto(photo));
		assertTrue(library.getPhotos().contains(photo));
		assertEquals(1, library.getNumberOfPhotos());
	}

	@Test
	void testAddExistingPhoto() {
		MockPhoto photo = new MockPhoto(new File("Test.jpg"));
		
		library.addPhoto(photo);
		assertFalse(library.addPhoto(photo));
		assertEquals(1, library.getNumberOfPhotos());
	}


	@Test
	void testDeletePhoto() {
		MockPhoto photo = new MockPhoto(new File("Test.jpg"));
		library.addPhoto(photo);
		
		assertTrue(library.deletePhoto(photo));
		assertEquals(0, library.getNumberOfPhotos());
	}

	@Test
	void testDeleteNotExistingPhoto() {
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		MockPhoto photo2 = new MockPhoto(new File("Two.jpg"));
		library.addPhoto(photo1);
		
		assertFalse(library.deletePhoto(photo2));
		assertEquals(1, library.getNumberOfPhotos());

	}


	@Test
	void testDeleteAll() {
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		MockPhoto photo2 = new MockPhoto(new File("Two.jpg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);
		
		assertTrue(library.deleteAll());
		assertEquals(0, library.getNumberOfPhotos());
	}

	@Test
	void testGetMatchesEmpty() {
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		Collection<IPhoto> matches = library.getMatches(".*");
		library.addPhoto(photo1);
		assertNotNull(matches);
		
		
		assertTrue(matches.isEmpty());
	}

	@Test
	void testGetMatchesNotEmpty() {
		MockPhoto photoY = new MockPhoto(new File("Y.jpg"),true);
		MockPhoto photoN = new MockPhoto(new File("N.jpg"),false);
		library.addPhoto(photoY);
		library.addPhoto(photoN);
		Collection<IPhoto> matches = library.getMatches(".*");

		assertEquals(1, matches.size());
        assertTrue(matches.contains(photoY));
        assertTrue(!matches.contains(photoN));		
	}

	@Test
	void testAutomaticDelete() throws InterruptedException {
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		MockPhoto photo2 = new MockPhoto(new File("Two.jpg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);
		Thread.sleep(SECONDS_IN_TRASH * 1000);
		Collection<IPhoto> photos = library.getPhotos();
		
		assertEquals(0,photos.size());
		
	}


	@Test
	void testAutomaticDeleteNoEffectTooSoon() {
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		MockPhoto photo2 = new MockPhoto(new File("Two.jpg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);
		Collection<IPhoto> photos = library.getPhotos();
		
		assertFalse(photos.isEmpty());
        assertEquals(2, photos.size());
	}

	@Test
	void testAutomaticDeleteNoEffectCheckedJustBefore() throws InterruptedException {    	
		MockPhoto photo1 = new MockPhoto(new File("One.jpg"));
		MockPhoto photo2 = new MockPhoto(new File("Two.jpg"));
		library.addPhoto(photo1);
		library.addPhoto(photo2);
		Thread.sleep(SECONDS_TO_CHECK * 1000);
		Collection<IPhoto> photos = library.getPhotos();
		
		assertFalse(photos.isEmpty());
        assertEquals(2, photos.size());
	}
	
	//COMPLETE ME

}