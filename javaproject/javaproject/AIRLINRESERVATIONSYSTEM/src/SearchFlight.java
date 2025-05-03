import java.sql.*;
import javax.swing.*;

public class SearchFlight extends JFrame {
    JTextField tfSource, tfDestination;
    JTextArea taResults;
    JButton btnSearch;

    public SearchFlight() {
        setTitle("Search Flight");
        setSize(500, 400);
        setLayout(null);

        JLabel lblSource = new JLabel("Source:");
        lblSource.setBounds(20, 20, 100, 20);
        add(lblSource);
        tfSource = new JTextField();
        tfSource.setBounds(130, 20, 150, 20);
        add(tfSource);

        JLabel lblDest = new JLabel("Destination:");
        lblDest.setBounds(20, 60, 100, 20);
        add(lblDest);
        tfDestination = new JTextField();
        tfDestination.setBounds(130, 60, 150, 20);
        add(tfDestination);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(300, 40, 100, 30);
        add(btnSearch);

        taResults = new JTextArea();
        taResults.setBounds(20, 100, 440, 240);
        add(taResults);

        btnSearch.addActionListener(e -> searchFlights());

        setVisible(true);
    }

    private void searchFlights() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM flight WHERE source=? AND destination=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tfSource.getText());
            ps.setString(2, tfDestination.getText());
            ResultSet rs = ps.executeQuery();
    
            taResults.setText("");
            while (rs.next()) {
                taResults.append("Flight No: " + rs.getString("flight_no") +
                                 ", Depart: " + rs.getString("departure_time") +
                                 ", Arrive: " + rs.getString("arrival_time") +
                                 ", Price: $" + rs.getDouble("price") + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
