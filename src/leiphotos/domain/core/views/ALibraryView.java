package leiphotos.domain.core.views;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import leiphotos.domain.core.Library;
import leiphotos.domain.facade.IPhoto;

/**
 * Classe abstrata ALibraryView com uma implementação esqueleto de ILibraryView baseada num
 * predicado indicando se uma foto pertence ou não à vista. Além deste predicado, os objetos têm a biblioteca
 * a que a vista se refere e o critério a usar na ordenação.
 */
public abstract class ALibraryView implements ILibraryView{
	
	protected Predicate<IPhoto> p;
	protected Library lib;
    protected Comparator<IPhoto> c;

	protected ALibraryView(Predicate<IPhoto> p, Library lib){
		this.lib = lib;
		this.c = Comparator.comparingLong(f -> f.size());    //comparar pelo tamanho das fotos
		this.p = p;
	}
	
    
    @Override
    public void setComparator(Comparator<IPhoto> c) {
    	this.c = c;
    }
    
    @Override
    public int numberOfPhotos() {
    	return this.getPhotos().size();
    }
    
    @Override
    public List<IPhoto> getPhotos(){
		List<IPhoto> temp = new ArrayList<>();
		for(IPhoto photo : lib.getPhotos()) {
			if(p.test(photo)) {
				temp.add(photo);
			}
		}
		temp.sort(c);
		return temp;
    }
    
    @Override
	public List<IPhoto> getMatches(String regexp) {
		List<IPhoto> matchedFotos = new ArrayList<>();
		for(IPhoto photo : lib.getPhotos()) {
			if(photo.matches(regexp)) {
				matchedFotos.add(photo);
			}
		}
		matchedFotos.sort(c);
		return matchedFotos;
	}
    
	

}