package carwashclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Sends the info to the main server which is the check out application.
 * @author Anestis
 */
public class InfoSend {

    public InfoSend(ArrayList<String> infSen) {
        try {
            //Prepei na ginei allagh gia to LAN
            Socket echoSocket = new Socket("localhost", 7896);
            PrintWriter serverOut = new PrintWriter(echoSocket.getOutputStream(), true);
            Scanner serverIn = new Scanner(echoSocket.getInputStream());
            for (int i = 0; i < infSen.size(); i++) {
                serverOut.println(infSen.get(i));
            }
            serverIn.close();
            serverOut.close();
            echoSocket.close();
        } catch (UnknownHostException e) {
            ErrorMessage error = new ErrorMessage(this,"Δεν είναι δυνατή η σύνδεση με το διακομιστή ελέγξτε τη σύνδεση σας στο δίκτυο.","Σφάλμα κατά τη σύνδεση");
            error.setVisible(true);
        } catch (IOException e) {
            ErrorMessage error = new ErrorMessage(this,"Δεν είναι δυνατή η σύνδεση με το διακομιστή ελέγξτε τη σύνδεση σας στο δίκτυο.","Σφάλμα κατά τη σύνδεση");
            error.setVisible(true);
        }
    }
}
