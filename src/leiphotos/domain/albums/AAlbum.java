package leiphotos.domain.albums;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import leiphotos.domain.core.LibraryEvent;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.PhotoDeletedLibraryEvent;
import leiphotos.domain.facade.IPhoto;
/**
 * Classe abstrata AAlbum representa albuns com fotos de uma dada MainLibrary; um álbum tem um nome
 *(único) e não tem fotos repetidas.Tem uma implementação esqueleto de IAlbum fornecendo uma implementação
 * por omissão de todos os seus métodos. Fotos apagadas da biblioteca, se existiam no álbum, deixam de lá
 * estar.
 */
public abstract class AAlbum implements IAlbum {

    private String name;
    private List<IPhoto> photos;
    private MainLibrary mainLib;


    /**
     * Construtor da classe
     * @param name nome do album
     * @param mainLib biblioteca do album
     */
    protected AAlbum(String name, MainLibrary mainLib) {
        this.name = name;
        this.mainLib = mainLib;
        mainLib.registerListener(this);
        this.photos = new ArrayList<>();
    }


    @Override
    public void processEvent(LibraryEvent e) {
        if(e instanceof PhotoDeletedLibraryEvent ) {
            PhotoDeletedLibraryEvent event = (PhotoDeletedLibraryEvent) e;
            if(event.getLibrary() == mainLib && photos.contains(event.getPhoto())){
            	removePhoto(event.getPhoto());
            }
        }
    }

    private void removePhoto(IPhoto photo){
        photos.remove(photo);
    }


    @Override
    public int numberOfPhotos() {
        return photos.size();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<IPhoto> getPhotos() {
        return photos;
    }

    @Override
    public boolean addPhotos(Set<IPhoto> selectedPhotos) {
        boolean worked = true;
        for(IPhoto photo : selectedPhotos) {
        	if(!photos.contains(photo)) {
                photos.add(photo);
            }  
        	else {
        		worked = false;
        	}
        }
        return worked;
    }

    @Override
    public boolean removePhotos(Set<IPhoto> selectedPhotos) {
    	 boolean worked = true;
         for(IPhoto photo : selectedPhotos) {
         	if(!photos.contains(photo)) {
         		worked = false;
         	}
         }        
    	photos.removeAll(selectedPhotos);
    	return worked;
    }
    
    @Override
	public String toString() {
    	StringBuilder sb = new StringBuilder();
    	for(IPhoto photo : photos) {
    		sb.append(photo.file().getPath() + "\n");
    	}
    	return sb.toString();
    }
    

}
