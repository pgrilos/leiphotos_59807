package leiphotos.domain.controllers;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.Set;

import leiphotos.domain.albums.IAlbumsCatalog;
import leiphotos.domain.facade.IAlbumsController;
import leiphotos.domain.facade.IPhoto;


/**
 * Classe AlbumsController que implementa IAlbumsController que representa controladores das interações com os álbuns.
 */
public class AlbumsController implements IAlbumsController {
	
	private IAlbumsCatalog albumsCatalog;
	private Optional<String> selectedAlbum;
 
	/**
	 * Construtor do controlador de albuns
	 * @param albumsCatalog catalogo de albuns do controlador
	 */
	public AlbumsController(IAlbumsCatalog albumsCatalog) {
		this.albumsCatalog = albumsCatalog;
		this.selectedAlbum = Optional.empty();
	}

	@Override
	public boolean createAlbum(String name) {	
		return this.albumsCatalog.createAlbum(name);
	}

	@Override
	public void removeAlbum() {
		if(selectedAlbum.isPresent()) {
			this.albumsCatalog.deleteAlbum(selectedAlbum.get());
		}
	}

	@Override
	public void selectAlbum(String name) {
		if(albumsCatalog.containsAlbum(name)) {
			this.selectedAlbum = Optional.of(name);			
		}
	}

	@Override
	public void addPhotos(Set<IPhoto> selectedPhotos) {
		if(selectedAlbum.isPresent()) {
			this.albumsCatalog.addPhotos(selectedAlbum.get(), selectedPhotos);
		}
	}

	@Override
	public void removePhotos(Set<IPhoto> selectedPhotos) {
		if(selectedAlbum.isPresent()) {
			this.albumsCatalog.removePhotos(selectedAlbum.get(), selectedPhotos);
		}
	}

	@Override
	public List<IPhoto> getPhotos() {
		if(selectedAlbum.isPresent()) {
			return this.albumsCatalog.getPhotos(selectedAlbum.get());
		}
		return new ArrayList<>();		
	}

	@Override
	public Optional<String> getSelectedAlbum() {
		return selectedAlbum;
	}

//@Override
//	public boolean createSmartAlbum(String name, Predicate<IPhoto> criteria) {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public Set<String> getAlbumNames() {
		return this.albumsCatalog.getAlbumsNames();
	}
	
	@Override
	public String toString() {
		return this.albumsCatalog.toString();
	}
	

}