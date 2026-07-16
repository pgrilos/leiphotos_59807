package leiphotos.domain.metadatareader;

import java.io.FileNotFoundException;


@SuppressWarnings("serial")
public class JpegMetadataException extends Exception {
  public JpegMetadataException(String message) {
      super(message);
  }
}

//@SuppressWarnings("serial")
//public class JpegMetadataException extends FileNotFoundException {
//    public JpegMetadataException(String message) {
//        super(message);
//    }
//}
