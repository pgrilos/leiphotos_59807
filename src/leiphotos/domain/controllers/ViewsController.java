package leiphotos.domain.controllers;


import java.util.Comparator;
import java.util.List;

import leiphotos.domain.core.views.IViewsCatalog;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.IViewsController;
import leiphotos.domain.facade.ViewsType;


/**
 * Classe ViewsController que implementa IViewsController que representa controladores das interações com as vistas
 */
public class ViewsController implements IViewsController {
	private IViewsCatalog v;

	/**
	 * Construtor do controlador das views
	 * @param views catalogo de views
	 */
	public ViewsController(IViewsCatalog views) {
		this.v = views;
	}

	@Override
	public List<IPhoto> getPhotos(ViewsType viewType) {
		return v.getView(viewType).getPhotos();
	}

	@Override
	public List<IPhoto> getMatches(ViewsType viewType, String regexp) {
		return v.getView(viewType).getMatches(regexp);
	}

	@Override
	public void setSortingCriteria(ViewsType v, Comparator<IPhoto> criteria) {
		this.v.getView(v).setComparator(criteria);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("***** VIEWS *****"
				+ "\n");
		sb.append(v.toString());
		return sb.toString();
	}
	

}
