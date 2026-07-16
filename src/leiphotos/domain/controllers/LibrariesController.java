package leiphotos.domain.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.Photo;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.core.PhotoFactory;
import leiphotos.domain.facade.ILibrariesController;
import leiphotos.domain.facade.IPhoto;

/**
 * Classe LibrariesController que implementa ILibrariesController que representa controladores das interações com as
 * bibliotecas.
 */
public class LibrariesController implements ILibrariesController {
	
	private MainLibrary mainLibrary;
    private TrashLibrary trashLibrary;
    
    /**
     * Construtor de um controlador de bibliotecas
     * @param mainLib biblioteca normal do controlador
     * @param trashLib biblioteca das fotos apagadas do controlador
     */
	public LibrariesController(MainLibrary mainLib, TrashLibrary trashLib) {
		this.mainLibrary = mainLib;
		this.trashLibrary = trashLib;
	}

	@Override
	public Optional<IPhoto> importPhoto(String title, String pathToPhotoFile) {
		try {
			Photo photo = PhotoFactory.INSTANCE.createPhoto(title, pathToPhotoFile);
			mainLibrary.addPhoto(photo);
			return Optional.of(photo);
		}
		catch(Exception e){
			return Optional.empty();
		}
	}

	@Override
	public void deletePhotos(Set<IPhoto> selectedPhotos) {
		for(IPhoto photo : selectedPhotos) {
			if(mainLibrary.getPhotos().contains(photo)) {
				mainLibrary.deletePhoto(photo);
				trashLibrary.addPhoto(photo);
			}
		}
	}

	@Override
	public void emptyTrash() {
		trashLibrary.deleteAll();

	}

	@Override
	public void toggleFavourite(Set<IPhoto> selectedPhotos) {
		for(IPhoto photo : selectedPhotos) {
			if(mainLibrary.getPhotos().contains(photo)) {
				for(IPhoto mainPhoto : mainLibrary.getPhotos()) {
					if(mainPhoto.equals(photo)) {
						mainPhoto.toggleFavourite();
					}
				}
			}
		}

	}

	@Override
	public Iterable<IPhoto> getMatches(String regExp) {
		Collection<IPhoto> match = new ArrayList<>();
		for(IPhoto photo: mainLibrary.getPhotos()) {
			if(photo.matches(regExp)) {
				match.add(photo);
			}
		}
		return match;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(mainLibrary.toString());
		sb.append(trashLibrary.toString());
		return sb.toString();
	}
	

}
