package leiphotos.domain.core.views;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;


import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.ViewsType;

/**
 * Classe ViewsCatalog que implementa IViewsCatalog 
 * que representa catálogos de vistas de cada tipo em ViewsType.
 */
public class ViewsCatalog implements IViewsCatalog {

	protected Map<ViewsType, ILibraryView> map;
	protected MainLibrary main;
	protected TrashLibrary trash;

	public ViewsCatalog(MainLibrary mainLib, TrashLibrary trashLib) {
		this.main = mainLib;
		this.trash = trashLib;
		Predicate<IPhoto> recent = (photo -> photo.capturedDate().isAfter(LocalDateTime.now().minusMonths(12)));
		Predicate<IPhoto> p = photo -> true;		
		map = new HashMap<>();
		map.put(ViewsType.ALL_MAIN, new MainLibraryView(p, main));
		map.put(ViewsType.ALL_TRASH,new TrashLibraryView(p, trash));
		map.put(ViewsType.FAVOURITES_MAIN, new MainLibraryView((IPhoto::isFavourite), main));
		map.put(ViewsType.MOST_RECENT,new MainLibraryView(recent, main));
	}

	@Override
	public ILibraryView getView(ViewsType g) {
		return map.get(g);
		
	}

	@Override
	public String toString() {
		ILibraryView atualLib = getView(ViewsType.ALL_MAIN);
		StringBuilder sb = new StringBuilder("***** VIEW ALL_MAIN: "  + atualLib.numberOfPhotos()  + " photos *****" + "\n");
		sb.append(toStringAux(sb.toString(), atualLib));

		atualLib = getView(ViewsType.ALL_TRASH);
		sb.append("***** VIEW ALL_TRASH: " +  atualLib.numberOfPhotos()  + " photos *****" + "\n");
		sb.append(toStringAux(sb.toString(), atualLib));

		atualLib = getView(ViewsType.FAVOURITES_MAIN);
		sb.append("***** VIEW FAVOURITES_MAIN: " +  atualLib.numberOfPhotos()  + " photos *****" + "\n");
		sb.append(toStringAux(sb.toString(), atualLib));
		

		atualLib = getView(ViewsType.MOST_RECENT);
		sb.append("***** VIEW MOST_RECENT: " +  atualLib.numberOfPhotos()  + " photos *****" + "\n");
		sb.append(toStringAux(sb.toString(), atualLib));

		return sb.toString();
    }
	
	private String toStringAux(String s, ILibraryView lib) {
		StringBuilder sb = new StringBuilder();
		int atualNumberOfPhotos = lib.numberOfPhotos();
		if(atualNumberOfPhotos != 0){
			for(IPhoto photo :  lib.getPhotos()){
				sb.append(photo.file().getPath() + "\n");
			}
		}
		return sb.toString();		
	}
	
	

}
