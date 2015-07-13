package checkout;

/**
 *
 * @author Anestis
 */
public class CheckOut {

    /**
     * Starts the Check out application used by the user of the system 
     * @param args 
     */
    public static void main(String[] args) {
        CheckOutWin win1 = new CheckOutWin();
        win1.setVisible(true);
        AvailableServices a1=new AvailableServices();
        while (true) {
            ServerInput a = new ServerInput();
        }
    }
}
