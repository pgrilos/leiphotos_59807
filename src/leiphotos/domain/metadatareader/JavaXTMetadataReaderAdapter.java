package leiphotos.domain.metadatareader;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import leiphotos.services.JavaXTJpegMetadataReader;
/**
 * Classe JavaXTMetadataReaderAdapter que implementa JpegMetadataReader adaptando esta interface à
 * oferecida pela classe services.JavaXTJpegMetadataReader.
 */
public class JavaXTMetadataReaderAdapter implements JpegMetadataReader {
    private final JavaXTJpegMetadataReader javaXTJeader;

    /**
     * Construtor da classe
     * @param file ficheiro a ser lido
     * @throws JpegMetadataException
     */
    public JavaXTMetadataReaderAdapter(File file) throws JpegMetadataException {
        try {
            this.javaXTJeader = new JavaXTJpegMetadataReader(file);
        } catch (Exception e) {
            throw new JpegMetadataException("Arquivo não encontrado");
        }
    }

    @Override
    public String getCamera() {
        if(javaXTJeader.getCamara() != null)
            return javaXTJeader.getCamara();
        else
            return "";
    }

    @Override
    public String getManufacturer() {
        if(javaXTJeader.getManufacturer() != null)
            return javaXTJeader.getManufacturer();
        else
            return "";
    }

    @Override
    public LocalDateTime getDate() {

        String dateString = javaXTJeader.getDate();
        if(dateString != null) {
        	return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
        }         
        else {
        	return LocalDateTime.of(1970, 1, 1, 0, 0);
        }
            
    	
    }

    @Override
    public String getAperture() {
        if(javaXTJeader.getAperture() != null)
            return javaXTJeader.getAperture();
        else
            return "";
    }

    @Override
    public double[] getGpsLocation() {
        if(javaXTJeader.getGPS() != null)
    	    return javaXTJeader.getGPS();
    	else
            return null;
    }
    
}