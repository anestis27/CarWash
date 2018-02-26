package carwashclient;

import innerDSL.QuickGUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * This is a windows that opens and show to the customer what services he has
 * chosen. Its only for confirmational reasons.
 *
 * @author Anestis
 */
public class SummaryInfoSend implements ActionListener {

    private static String type, servOfVehicleInfo = "";
    private QuickGUI q1;

    public SummaryInfoSend() {
        q1 = new QuickGUI(new SummaryInfoSendGUI(), this);
        switch (SecondWinClient.getFlagVihicle()) {
            case 0:
                type = "Car";
            case 1:
                type = "Truck";
            default:
                type = "Bike";
        }
        for (int i = 0; i < SecondWinClient.getServicesArrayBox().size(); i++) {
            if (SecondWinClient.getServicesArrayBox().get(i).isSelected()) {
                servOfVehicleInfo += "•  " + SecondWinClient.getServicesArrayBox().get(i).getText() + "\n";
            }
        }
    }

    public static class SummaryInfoSendGUI extends innerDSL.QuickGUI.GUIModel {
        @Override
        public void build() {
            frame("Summary", 5, 2, new ImageIcon("carWash.gif"), new Color(153, 255, 255),
                    label(text("Type of vehicle")),
                    label(text(type)),
                    label(text("Number of Vehicle")),
                    label(text(FirstWinClient.getTextfield())),
                    label(text("List of selected services")),
                    textArea(text(servOfVehicleInfo)),
                    label(text("Total Cost")),
                    label(text(SecondWinClient.getCostField().getText())),
                    button(name("back"), text("Back")),
                    button(name("ok"), text("OK"))
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        if ("ok".equals(button)) {
            okAction();
        } else if ("back".equals(button)) {
            backAction();
        } else {
            System.err.println("Warning: unknown event " + e);
        }
    }

    private void okAction() {
        ArrayList<String> infSen = new ArrayList<>(8);
        infSen.add(FirstWinClient.getTextfield());
        infSen.add(SecondWinClient.getFlagVihicle() + "");
        for (int i = 0; i < SecondWinClient.getServicesArrayBox().size(); i++) {
            if (SecondWinClient.getServicesArrayBox().get(i).isSelected()) {
                infSen.add(i + "");
            }
        }
        InfoSend newSend = new InfoSend(infSen);
        SecondWinClient.getVihicle().clearSelection();
        SecondWinClient.getCostField().setText("0.0  €");
        SecondWinClient.getOkButton().setEnabled(false);
        for (int i = 0; i < SecondWinClient.getServicesArrayBox().size(); i++) {
            SecondWinClient.getServicesArrayBox().get(i).setSelected(false);
            SecondWinClient.getServicesArrayBox().get(i).setEnabled(false);
        }
        FirstWinClient.setTextfield("");
        CarWashClient.getWin2().setVisible(false);
        CarWashClient.getWin1().setVisible(true);
        servOfVehicleInfo = "";
        q1.closeFrame();
    }

    private void backAction() {
        servOfVehicleInfo = "";
        q1.closeFrame();
    }

}
