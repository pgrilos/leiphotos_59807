package leiphotos.domain.albums;

import leiphotos.domain.core.LibraryEvent;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Listener;
import java.util.List;
import java.util.Set;

public interface IAlbum extends Listener<LibraryEvent>  {

	/**
	 *@return o número de fotos no álbum 
	 *@ensures \result >= 0
	 */
	int numberOfPhotos();
	
	/**
	 * 
	 * @return o nome do álbum
	 */
	String getName();
	
	/**
	 * 
	 * @return as fotos no álbum por uma dada ordem
	 */
	List<IPhoto> getPhotos();
	
	/**
	 * 
	 * @param selectedPhotos
	 * @return 
	 */
	boolean addPhotos(Set<IPhoto> selectedPhotos);
	
	/**
	 * 
	 * @param selectedPhotos
	 * @return
	 */
	boolean removePhotos(Set<IPhoto> selectedPhotos);
	
}