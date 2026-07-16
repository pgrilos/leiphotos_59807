package leiphotos.domain.core;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.utils.RegExpMatchable;

/**
 * Record GPSLocation cujos objetos representam uma localização GPS, com as coordenadas e uma
 * descrição. Implementa RegExpMatchable baseando o match na descrição.
 */
public record GPSLocation(double latitude, double longitude, String description) implements GPSCoordinates, RegExpMatchable {

    @Override
    public boolean matches(String regexp) {
		Pattern p = Pattern.compile(regexp);
		Matcher m = p.matcher(description);
        return m.matches();
    }
    
    /**
	 * Returns the latitude value of the GPS coordinates.
	 *
	 * @return the latitude value
	 */
	public double latitude() {
		return latitude;
	}
	

	/**
	 * Returns the longitude value of the GPS coordinates.
	 *
	 * @return the longitude value
	 */
	public double longitude() {
		return longitude;
	}
	
}
