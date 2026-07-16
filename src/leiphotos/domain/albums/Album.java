package leiphotos.domain.albums;

import leiphotos.domain.core.MainLibrary;

/**
 * Classe Album que estende AAlbum e cujos objetos representam álbuns normais.
 */
public class Album extends AAlbum {
	
	/**
	 * 
	 * @param name nome do album
	 * @param mainLib biblioteca do album
	 */
	public Album(String name, MainLibrary mainLib) {
        super(name,mainLib);
    }
}