package leiphotos.domain.core.views;
import leiphotos.domain.facade.ViewsType;
//Class automatically generated so the code compiles
//CHANGE ME

public interface IViewsCatalog {

    /**
     * 
     * @param g tipo de vista
     * @return  vista no catálogo com um determinado tipo 
     */
    ILibraryView getView(ViewsType g);
}
