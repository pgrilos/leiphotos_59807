package leiphotos.domain.core.views;

import java.util.function.Predicate;

import leiphotos.domain.core.Library;
import leiphotos.domain.facade.IPhoto;

/**
 * Classe TrashLibraryView que estende ALibraryView e cujos objetos representam vistas sobre
 * bibliotecas do tipo TrashLibrary.
 */
public class TrashLibraryView extends ALibraryView {

	/**
	 * 
	 * @param p predicado 
	 * @param lib biblioteca que a view pertence
	 */
    public TrashLibraryView(Predicate<IPhoto> p, Library lib){
        super(p,lib);
    }
}
