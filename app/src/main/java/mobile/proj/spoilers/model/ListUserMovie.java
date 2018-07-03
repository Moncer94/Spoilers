package mobile.proj.spoilers.model;

/**
 * Created by jihen on 28/11/2017.
 */

public class ListUserMovie {
    private String idListUserMovie;
    private String listName;
    private String listDescription;
    int idMovieList;
    int idUser;
public ListUserMovie(){

}
    public ListUserMovie(String idListUserMovie, String listName, String listDescription, int idMovieList, int idUser) {
        this.idListUserMovie = idListUserMovie;
        this.listName = listName;
        this.listDescription = listDescription;
        this.idMovieList = idMovieList;
        this.idUser = idUser;
    }

    public String getIdListUserMovie() {
        return idListUserMovie;
    }

    public void setIdListUserMovie(String idListUserMovie) {
        this.idListUserMovie = idListUserMovie;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public int getIdMovieList() {
        return idMovieList;
    }

    public void setIdMovieList(int idMovieList) {
        this.idMovieList = idMovieList;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
