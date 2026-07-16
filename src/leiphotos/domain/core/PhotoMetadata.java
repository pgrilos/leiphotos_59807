package leiphotos.domain.core;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import leiphotos.utils.RegExpMatchable;

/**
 * Record PhotoMetadata cujos objetos representam os metadados das fotos que são suportados, e que
 * atualmente são a localização, a data, a câmara e o fabricante. Implementa RegExpMatchable considerando
 * que o objeto emparelha se alguns dados emparelham com a regexp dada.
 */
public record PhotoMetadata(GPSLocation loc, LocalDateTime date, String camera, String fabricante) implements RegExpMatchable {

    @Override
    public boolean matches(String regexp) {
        Pattern p = Pattern.compile(regexp);
        Matcher m1 = p.matcher(camera);
        Matcher m2 = p.matcher(fabricante);
        Matcher m3 = p.matcher(date.toString());
        if(loc != null)
        	return loc.matches(regexp) || m1.matches() || m2.matches() || m3.matches();
        else
        	return m1.matches() || m2.matches() || m3.matches();
    }

    @Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PhotoMetadata other = (PhotoMetadata) obj;
        return Objects.equals(loc, other.loc) &&
               Objects.equals(date, other.date) &&
               Objects.equals(camera, other.camera) &&
               Objects.equals(fabricante, other.fabricante);
    }

    @Override
	public int hashCode() {
        return Objects.hash(loc, date, camera, fabricante);
    }
}
