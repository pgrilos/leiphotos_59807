package leiphotos.domain.core;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.RegExpMatchable;

/**
 * Classe Photo cujos objetos, mutáveis, representam fotos. Cada foto tem o caminho para o ficheiro onde
 * a música se encontra (com o formato jpeg), metadados e informação específica da aplicação como um título,
 * a data em que foi carregada na aplicação, se a foto foi marcada como favorita e o tamanho.
 */
public class Photo implements IPhoto, RegExpMatchable{

    private String title;
    private LocalDateTime time;
    private PhotoMetadata meta;
    private File path;
    private boolean isFavourite;

    /**
     * 
     * @param title titulo da foto
     * @param dateAddedLib data de adição à biblioteca
     * @param meta informaçoes da foto
     * @param pathToFile caminho ate a foto
     */
    public Photo(String title, LocalDateTime dateAddedLib, PhotoMetadata meta, File pathToFile){
        this.title = title;
        this.time = dateAddedLib;
        this.meta = meta;
        this.path = pathToFile;
        isFavourite = false;

    } 

    @Override
    public String title() {
        return title;
    }

    @Override
    public LocalDateTime capturedDate() {
    	return meta.date();
    }

    @Override
    public LocalDateTime addedDate() {
        return time;
    }

    @Override
    public boolean isFavourite() {
        return isFavourite;
    }

    @Override
    public void toggleFavourite() {
        if(isFavourite)
            isFavourite = false;
        else
            isFavourite = true;
    }

    @Override
    public Optional<? extends GPSCoordinates> getPlace() {
        if(meta.loc() != null) {
        	return Optional.of(meta.loc());
        }
    	return Optional.empty();
    }

    @Override
    public long size() {
        return path.length();
    }
    
    
    @Override
    public File file() {
        return path;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Photo other = (Photo) obj;
        return Objects.equals(title, other.title) &&
               Objects.equals(time, other.time) &&
               meta.equals(other.meta) &&
               Objects.equals(path, other.path) &&
               isFavourite == other.isFavourite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, meta, path, isFavourite);
    }

    @Override
    public boolean matches(String regexp) {
    	 Pattern p = Pattern.compile(regexp);
    	 Matcher m1 = p.matcher(title);
    	 Matcher m2 = p.matcher(time.toString());
    	 Matcher m3 = p.matcher(path.toString());
    	 return meta.matches(regexp) || m1.matches() || m2.matches() || m3.matches();
    }
    
    public PhotoMetadata meta() {
    	return this.meta;
    }
    
    @Override
	public String toString() {
    	StringBuilder sb = new StringBuilder();
        sb.append("File:").append(this.file().toString()).append("\n");
        sb.append("Title:" + this.title()).append(" Added:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        sb.append(this.addedDate().format(formatter))
        .append(" Size:")
        .append(this.size())
        .append("\n");
        Optional<? extends GPSCoordinates> locationOptional = this.getPlace();
        if(locationOptional.isPresent()) {
            sb.append("[{Lat:");
            DecimalFormat df = new DecimalFormat("#.##");
            sb.append(df.format(this.getPlace().get().latitude())					)
            .append(" Long:")
            .append(df.format(this.getPlace().get().longitude()))
            .append(" Desc:}, ")
            .append(this.capturedDate().format(formatter)).append(", ")
            .append(this.meta.camera() + ", " + this.meta.fabricante());
        }
        else {
        	
        	sb.append("[No Location, ")
            .append(this.capturedDate().format(formatter))
            .append(this.meta.camera() + ", " + this.meta.fabricante());
        }
        sb.append("]");
        if(isFavourite) {
        	sb.append(" FAV");
        }
        sb.append("\n");
        
        return sb.toString();
    }
    			
    
}
