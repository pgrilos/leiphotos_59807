package leiphotos.domain.core.views;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


import leiphotos.domain.core.LibraryEvent;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.PhotoAddedLibraryEvent;
import leiphotos.domain.core.PhotoDeletedLibraryEvent;
import leiphotos.domain.core.PhotoChangedLibraryEvent;

import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Listener;

/**
 * Classe MainLibraryView que estende ALibraryView e cujos objetos representam vistas sobre bibliotecas
 * do tipo MainLibrary. Estas vistas usam uma cache para guardar as fotos ordenadas pelo critério de
 * ordenação corrente. A classe implementa Listener<LibraryEvent> e os seus objetos reagem aos eventos
 * lançados pela biblioteca de forma a garantir que a cache se mantém atualizada.
 */
public class MainLibraryView extends ALibraryView implements Listener<LibraryEvent>{

    private List<IPhoto> cache;


    /**
     * 
     * @param p predicado para filtrar as fotos da biblioteca
     * @param mainLib biblioteca pertencente a view
     */
    public MainLibraryView(Predicate<IPhoto> p, MainLibrary mainLib){
        super(p,mainLib);
        mainLib.registerListener(this);
        cache = new ArrayList<IPhoto>();
    }

    @Override
    public void setComparator(Comparator<IPhoto> c) {
        super.setComparator(c);
        cache.sort(c);
    }
    



    @Override
    public void processEvent(LibraryEvent e) {
        if(e instanceof PhotoAddedLibraryEvent){
        	if(p.test(e.getPhoto())) {
        		cache.add(e.getPhoto());
                cache.sort(c);      		
        }           
        }else if (e instanceof PhotoDeletedLibraryEvent) {
            cache.remove(e.getPhoto());
        }else if (e instanceof PhotoChangedLibraryEvent){  
            for (IPhoto f : cache) {
                if(f.equals(e.getPhoto())){
                    f.toggleFavourite();
                }
            }
        }
    }

	
}   

