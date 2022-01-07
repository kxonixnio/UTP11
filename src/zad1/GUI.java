package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GUI {

    private TravelData travelData;

    public GUI(TravelData travelData) {

        this.travelData = travelData;

        JFrame frame = new JFrame("Travel App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setLayout(new BorderLayout());

        String[] column = {"idTravel", "countryCode", "countryName", "dateFrom", "dateTo", "location", "price", "currency"};
        String[] data = {"test", "test", "test", "test", "test", "test", "test", "test"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        JTable table = new JTable(model);

        //Tu trzeba dodać dane z bazy danych
        model.addRow(data);
        model.addRow(new String[]{"test2", "test2", "test2", "test2", "test2", "test2", "test2", "test2"});

        JScrollPane jsp = new JScrollPane(table);
        frame.getContentPane().add(jsp, BorderLayout.CENTER);

        JPanel pageEndPanel = new JPanel();
        pageEndPanel.setLayout(new FlowLayout());
        frame.getContentPane().add(pageEndPanel, BorderLayout.PAGE_END);

        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");

        pageEndPanel.add(button1);
        pageEndPanel.add(button2);

        frame.setVisible(true);
    }
}
