package leiphotos.domain.core;

import java.util.ArrayList;
import java.util.Collection;

import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.AbsSubject;

/**
 * Classe MainLibrary que implementa Library e estende AbsSubject<LibraryEvent>. Os seus objetos
 * representam uma biblioteca (normal).
 */
public class MainLibrary extends AbsSubject<LibraryEvent> implements Library{
	
	private Collection<IPhoto> photos;
	
	/**
	 * Construtor da classe
	 */
	public  MainLibrary() {
		photos = new ArrayList<>();
	}
	
	@Override
	public int getNumberOfPhotos() {
		return photos.size();
	}

	@Override
	public boolean addPhoto(IPhoto photo) {
		if(!photos.contains(photo)) {
			photos.add(photo);
			emitEvent(new PhotoAddedLibraryEvent(photo, this));
			return true;
		}
		return false;
		
	}

	@Override
	public boolean deletePhoto(IPhoto photo) {
		if(photos.contains(photo)) {
			photos.remove(photo);
			emitEvent(new PhotoDeletedLibraryEvent(photo, this));
			return true;
		}
		return false;
	}

	@Override
	public Collection<IPhoto> getPhotos() {
		return photos;
	}

	@Override
	public Collection<IPhoto> getMatches(String regexp) {
		Collection <IPhoto> match = new ArrayList<>();
		for(IPhoto photo:photos) {
			if(photo.matches(regexp)) {
				match.add(photo);
			}
		}
		return match;
	}
	
	

	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("***** MAIN PHOTO LIBRARY: " + photos.size() + " photos *****\n");
        for (IPhoto photo : photos) {
            sb.append(photo.toString());
        }
        return sb.toString();
	}
	
	
	

}
