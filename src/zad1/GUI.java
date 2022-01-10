package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.*;

public class GUI {

    private ArrayList<Record> travelData;

    public GUI(ArrayList<Record> travelData) {

        this.travelData = travelData;

        JFrame frame = new JFrame("Travel App s22472");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,300);
        frame.setLayout(new BorderLayout());

        String[] column = {"idTravel", "countryCode", "countryName", "dateFrom", "dateTo", "location", "price", "currency"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        JTable table = new JTable(model);

        int id = 1;
        for (Record record : travelData) {
            String[] recordData = {
                    String.valueOf(id),
                    String.valueOf(record.getCountryCode()),
                    record.getCountryName(),
                    String.valueOf(record.getDateFrom()),
                    String.valueOf(record.getDateTo()),
                    record.getLocation(),
                    String.valueOf(record.getPrice()),
                    record.getCurrency()
            };

            model.addRow(recordData);

            id += 1;
        }

        JScrollPane jsp = new JScrollPane(table);
        frame.getContentPane().add(jsp, BorderLayout.CENTER);

        JPanel pageEndPanel = new JPanel();
        pageEndPanel.setLayout(new FlowLayout());
        frame.getContentPane().add(pageEndPanel, BorderLayout.PAGE_END);

        JButton button2 = new JButton("Ustawienia regionalne");

        JRadioButton polishLanguageButton = new JRadioButton("Polish");
        JRadioButton englishLanguageButton = new JRadioButton("English");
        polishLanguageButton.setBounds(75, 50, 100, 30);
        englishLanguageButton.setBounds(75, 50, 100, 30);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(polishLanguageButton);
        buttonGroup.add(englishLanguageButton);

        Box verticalBoxForJRadioButtons = Box.createVerticalBox();
        verticalBoxForJRadioButtons.add(polishLanguageButton);
        verticalBoxForJRadioButtons.add(englishLanguageButton);

        pageEndPanel.add(verticalBoxForJRadioButtons);
        pageEndPanel.add(button2);

        frame.setVisible(true);

        polishLanguageButton.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                model.setRowCount(0);

                int id2 = 1;
                for (Record record : travelData) {
                    String[] recordData = {
                            String.valueOf(id2),
                            String.valueOf(record.getCountryCode()),
                            TravelData.translateCountry(record.getCountryCode(), Locale.forLanguageTag("pl-PL"), record.getCountryName()),
                            String.valueOf(record.getDateFrom()),
                            String.valueOf(record.getDateTo()),
                            TravelData.translateLocation(Locale.forLanguageTag("pl-PL"), record.getLocation()),
                            String.valueOf(record.getPrice()),
                            record.getCurrency()
                    };

                    model.addRow(recordData);

                    id2 += 1;
                }
            }
        });

        buttonGroup.setSelected(polishLanguageButton.getModel(), true);

        englishLanguageButton.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                model.setRowCount(0);

                int id2 = 1;
                for (Record record : travelData) {

                    String[] recordData = {
                            String.valueOf(id2),
                            String.valueOf(record.getCountryCode()),
                            TravelData.translateCountry(record.getCountryCode(), Locale.forLanguageTag("en-GB"), record.getCountryName()),
                            String.valueOf(record.getDateFrom()),
                            String.valueOf(record.getDateTo()),
                            TravelData.translateLocation(Locale.forLanguageTag("en-GB"), record.getLocation()),
                            String.valueOf(record.getPrice()),
                            record.getCurrency()
                    };

                    model.addRow(recordData);

                    id2 += 1;
                }
            }
        });

        button2.addActionListener(
                e -> {
                    System.out.println("Nie mam pojęcia czym mają być 'ustawienia regionalne', a deadline goni, więc zostawiam to tak jak jest");
                }
        );
    }
}

//https://www.javatpoint.com/java-jtable
//https://stackoverflow.com/questions/21135452/how-to-add-row-of-data-to-jtable-from-values-received-from-jtextfield-and-combob
//https://stackoverflow.com/questions/20431719/how-to-set-jradiobuttons-vertically
//https://stackoverflow.com/questions/1424738/detecting-a-jradiobutton-state-change