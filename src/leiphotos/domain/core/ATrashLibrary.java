package leiphotos.domain.core;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

import leiphotos.domain.facade.IPhoto;

public abstract class ATrashLibrary implements TrashLibrary{
	
	protected Map<IPhoto, LocalDateTime > photos;
	
	protected abstract void clean();
	protected abstract boolean cleaningTime();
	
	/**
	 * Construtor da classe
	 */
	public ATrashLibrary() {
		photos = new HashMap<>();
	}
	
	@Override
	public Collection<IPhoto> getPhotos(){
		if(cleaningTime()) {
			clean();
		}
		return photos.keySet();
	}
	
	@Override
	public boolean deleteAll() {
		if(!photos.isEmpty()) {
			photos.clear();
			return true;
		}
		return false;
	}
	
	
	@Override
    public String toString() {
		StringBuilder sb = new StringBuilder();
        sb.append("***** TRASH PHOTO LIBRARY: " + photos.size() + " photos *****\n");
        for (IPhoto photo : photos.keySet()) {
        	sb.append(photo.toString());
        }
        return sb.toString();
	}
}