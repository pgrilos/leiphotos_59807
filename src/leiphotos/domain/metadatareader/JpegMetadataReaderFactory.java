package leiphotos.domain.metadatareader;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Enumerado JpegMetadataReaderFactory que é um singleton cuja instância tem a responsabilidade de criar o
 * JpegMetadataReader que vai ser usado e o fornece a quem precisar através de um método 
 */

public enum JpegMetadataReaderFactory {
    INSTANCE;
    public static JpegMetadataReader createMetadataReader(File file) throws JpegMetadataException, FileNotFoundException{
        return new JavaXTMetadataReaderAdapter(file);
    }
}
