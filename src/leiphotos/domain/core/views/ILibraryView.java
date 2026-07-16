package leiphotos.domain.core.views;

import java.util.Comparator;
import java.util.List;

import leiphotos.domain.facade.IPhoto;

public interface ILibraryView {
	
	/**
	 * 
	 * @param c comparador 
	 */
	void setComparator(Comparator<IPhoto> c);
	
	/**
	 * 
	 * @return numero de fotos que pertecem a vista
	 */
	int numberOfPhotos();
	
	
	/**
	 * 
	 * @return lista de fotos na vista
	 */
	List<IPhoto> getPhotos();
	
	/**
	 * 
	 * @param regexp string a comparar
	 * @return lista de fotos na vista que fazem match com a 
	 *         expressão regular dada
	 */
	List<IPhoto> getMatches(String regexp);
}
