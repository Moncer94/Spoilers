package mobile.proj.spoilers.app;

/**
 * Created by jihen on 20/11/2017.
 */

public class AppConfig {
    // Server user login url
    public static String URL_LOGIN = "http://41.226.11.243:10080/spoilers/mobileAndroid/Login.php";

    // Server user register url
    public static String URL_REGISTER = "http://41.226.11.243:10080/spoilers/mobileAndroid/Register.php";

    //favorites add
    public static String URL_ADDFAVORITES = "http://41.226.11.243:10080/spoilers/mobileAndroid/addFavorites.php";

    //get all  favorites  by User
    public static String URL_GETFAVORITES = "http://41.226.11.243:10080/spoilers/mobileAndroid/selectMyLists.php";

    //update image url and user infos
    public static String URL_UPDATEIMAGE = "http://41.226.11.243:10080/spoilers/mobileAndroid/updateUser.php";

    public static String URL_GETIMG_PREFIX = "http://41.226.11.243:10080/spoilers/mobileAndroid/";

    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
}
