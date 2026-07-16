package leiphotos.domain.albums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.facade.IPhoto;

/**
 * Classe AlbumsCatalog que implementa IAlbumsCatalog e tem a biblioteca a que os álbuns estão
 * associados e usa um mapa para guardar os álbuns existentes. Objetos representam catálogos de álbuns:
 */
public class AlbumsCatalog implements IAlbumsCatalog {
	
	private MainLibrary mainLibrary;
	private Map<String, IAlbum> albumsMap;
	
	/**
	 * Construtor de um catalogo de albuns
	 * @param mainLib biblioteca do catalogo de albuns
	 */
	public AlbumsCatalog(MainLibrary mainLib) {
		this.mainLibrary = mainLib;
		this.albumsMap = new HashMap<>();
	}

	@Override
	public boolean createAlbum(String albumName) {
		if (!albumsMap.containsKey(albumName)) {
            albumsMap.put(albumName, new Album(albumName, this.mainLibrary));
            return true;
        }
        return false;
	}

	@Override
	public boolean deleteAlbum(String albumName) {
		return albumsMap.remove(albumName) != null;
	}

	@Override
	public boolean containsAlbum(String albumName) {
		return albumsMap.containsKey(albumName);
	}

	@Override
	public boolean addPhotos(String albumName, Set<IPhoto> selectedPhotos) {
		IAlbum album = albumsMap.get(albumName);
		 if (album != null) {
	            return album.addPhotos(selectedPhotos);
	     }
	     return false;
	}

	@Override
	public boolean removePhotos(String albumName, Set<IPhoto> selectedPhotos) {
		IAlbum album = albumsMap.get(albumName);
        if (album != null) {
            return album.removePhotos(selectedPhotos);
        }
        return false;
	}

	@Override
	public List<IPhoto> getPhotos(String albumName) {
		IAlbum album = albumsMap.get(albumName);
        if (album != null) {
            return album.getPhotos();
        }
        return new ArrayList<>();
	}

	@Override
	public Set<String> getAlbumsNames() {
		return albumsMap.keySet();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("***** ALBUMS *****" + "\n");
		for(String name : albumsMap.keySet() ) {
			IAlbum album = albumsMap.get(name);
			sb.append("***** Album " + name + ": " + album.numberOfPhotos() + " photos *****" + "\n")
			.append(album.toString());
		}
		return sb.toString();
			
	}
	

}
