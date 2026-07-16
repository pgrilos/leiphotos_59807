package leiphotos.domain.albums;

import java.util.List;
import java.util.Set;

import leiphotos.domain.facade.IPhoto;

public interface IAlbumsCatalog {

	/**
     * Cria um novo álbum com o nome dado e o junta ao catálogo, se não existir nenhum com esse nome.
     * 
     * @param albumName o nome do álbum a ser criado
     * @return true se o álbum foi criado e adicionado ao catálogo, false caso contrário
     */
    boolean createAlbum(String albumName);
    
    /**
     * Remove um álbum com o nome dado do catálogo, se existir algum com esse nome.
     * 
     * @param albumName o nome do álbum a ser removido
     * @return true se o álbum foi removido do catálogo, false caso contrário
     */
    boolean deleteAlbum(String albumName);
    
    /**
     * Indica se existe no catálogo um álbum com o nome dado.
     * 
     * @param albumName o nome do álbum a ser verificado
     * @return true se o álbum existe no catálogo, false caso contrário
     */
    boolean containsAlbum(String albumName);
    
    /**
    * Adiciona ao álbum com o nome dado as fotos dadas, se existir algum álbum com esse nome.
    * 
    * @param albumName o nome do álbum ao qual as fotos serão adicionadas
    * @param selectedPhotos as fotos a serem adicionadas ao álbum
    * @return true se as fotos foram adicionadas com sucesso ao álbum, false caso contrário
    */
    boolean addPhotos(String albumName, Set<IPhoto> selectedPhotos);
    
    /**
     * Remove do álbum com o nome dado as fotos dadas, se existir algum álbum com esse nome.
     * 
     * @param albumName o nome do álbum do qual as fotos serão removidas
     * @param selectedPhotos as fotos a serem removidas do álbum
     * @return true se as fotos foram removidas com sucesso do álbum, false caso contrário
     */
    boolean removePhotos(String albumName, Set<IPhoto> selectedPhotos);
    
    /**
     * Obtém as fotos do álbum com o nome dado.
     * 
     * @param albumName o nome do álbum do qual se deseja obter as fotos
     * @return uma lista de fotos do álbum, ou uma lista vazia se o álbum não existir
     */
    List<IPhoto> getPhotos(String albumName);
    
    /**
    * Obtém os nomes dos álbuns existentes no catálogo.
    * 
    * @return um conjunto contendo os nomes dos álbuns no catálogo
    */
   Set<String> getAlbumsNames();

    
}
