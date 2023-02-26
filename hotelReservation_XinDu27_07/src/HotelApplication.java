
import api.HotelResource;
import api.MainMenu;


/**
 * HotelReservationApplication
 *
 * @author Xin Du
 */

public class HotelApplication {

    public static void main(String[] args) {
        HotelResource.loadData();      //load Data before application run from file
        MainMenu.mainMenu();
        HotelResource.storeData();     //store Data after application terminate from file
    }

}




