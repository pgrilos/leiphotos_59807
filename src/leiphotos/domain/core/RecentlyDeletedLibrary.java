package leiphotos.domain.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import leiphotos.domain.facade.IPhoto;

/**
 * Classe RecentlyDeletedLibrary que usa ATrashLibrary para fornecer uma implementação em que as
 * fotos são apagadas passado um determinado tempo de estarem no lixo, sendo a verificação feita apenas
 * quando já passou determinado tempo desde a última verificação.
 */
public class RecentlyDeletedLibrary extends ATrashLibrary implements Library, TrashLibrary{
	
	private int timeToClean = 15;
	private LocalDateTime lastClean;
	
	/**
	 * Construtor da classe
	 */
	public RecentlyDeletedLibrary() {
		super();
		lastClean = LocalDateTime.now();
	}

	@Override
	public int getNumberOfPhotos() {
		return photos.size();
	}

	@Override
	public boolean addPhoto(IPhoto photo) {
		for(IPhoto photoDeleted: photos.keySet()) {
			if(photoDeleted.equals(photo)) {
				return false;
			}
		}
		photos.put(photo, LocalDateTime.now());
		return true;
	}

	@Override
	public boolean deletePhoto(IPhoto photo) {
		for(IPhoto photoDeleted: photos.keySet()) {
			if(photoDeleted.equals(photo)) {
				photos.remove(photo);
				return true;
			}
		}
		return false;
	}

	@Override
	public Collection<IPhoto> getPhotos() {
		return super.getPhotos();
	}

	@Override
	public Collection<IPhoto> getMatches(String regexp) {
		Collection <IPhoto> match = new ArrayList<>();
		for(IPhoto photo: photos.keySet()) {
			if(photo.matches(regexp)) {
				match.add(photo);
			}
		}
		return match;
	}

	@Override
	public boolean deleteAll() {
		return super.deleteAll();
	}

	@Override
	protected void clean() {
		LocalDateTime now = LocalDateTime.now();
	    Iterator<IPhoto> iterator = photos.keySet().iterator();
	    while (iterator.hasNext()) {
	        IPhoto photo = iterator.next();
	        if (photos.get(photo).plusSeconds(timeToClean).isBefore(now)) {
	            iterator.remove(); 
	        }
	    }
	    lastClean = now;
//		for(IPhoto photo: photos.keySet()) {
//			if (photos.get(photo).plusSeconds(timeToClean).isBefore(LocalDateTime.now())){
//				photos.remove(photo);
//			}		
//		}
//		lastClean = LocalDateTime.now();		
	}

	@Override
	protected boolean cleaningTime() {
		if(lastClean.plusSeconds(timeToClean).isBefore(LocalDateTime.now())) {
			return true;
		}
		return false;
		
	}

}

