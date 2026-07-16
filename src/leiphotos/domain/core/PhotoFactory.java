package leiphotos.domain.core;



import java.io.File;
import java.time.LocalDateTime;

import leiphotos.domain.metadatareader.*;

/**
 * Enumerado PhotoFactory cuja única instância oferece um criador de fotos.
 */
public enum PhotoFactory {
    INSTANCE;

	/**
	 * Cria uma foto
	 * @param title titulo da foto
	 * @param pathToPhotoFile caminho da foto
	 * @return Photo a foto
	 * @throws java.io.FileNotFoundException
	 * @throws JpegMetadataException
	 */
    public Photo createPhoto(String title, String pathToPhotoFile) throws java.io.FileNotFoundException, JpegMetadataException{
        File f = new File(pathToPhotoFile);
        JpegMetadataReader j;
		try {
			j = new JavaXTMetadataReaderAdapter(f);
			if(j.getGpsLocation() !=  null) {
	        	double[] c = j.getGpsLocation();          
	            GPSLocation gps = new GPSLocation(c[1], c[0], "" );
	            PhotoMetadata meta = new PhotoMetadata(gps, j.getDate(), j.getCamera(), j.getManufacturer());
	        	return new Photo(title, LocalDateTime.now(), meta, f);
	        }
	        else {
	        	PhotoMetadata meta = new PhotoMetadata(null, j.getDate(), j.getCamera(), j.getManufacturer());
	        	return new Photo(title, LocalDateTime.now(), meta, f);
	        } 
		} catch (Exception e) {
			e.printStackTrace();
			throw new JpegMetadataException("erro");
		}       
        
    }
    
}
