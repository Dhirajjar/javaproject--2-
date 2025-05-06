import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class SearchFlight extends JFrame {
    private JTextField tfSource, tfDestination;
    private JTextArea taResults;
    private JButton btnSearch;

    public SearchFlight() {
        setTitle("Search Flight");
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel with GridBagLayout for better alignment and design
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for spacing

        // Source label and text field
        JLabel lblSource = new JLabel("Source:");
        tfSource = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblSource, gbc);
        gbc.gridx = 1;
        panel.add(tfSource, gbc);

        // Destination label and text field
        JLabel lblDest = new JLabel("Destination:");
        tfDestination = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblDest, gbc);
        gbc.gridx = 1;
        panel.add(tfDestination, gbc);

        // Search button
        btnSearch = new JButton("Search Flights");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnSearch, gbc);

        // Text area for displaying results
        taResults = new JTextArea(10, 30);
        taResults.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taResults);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        // Add panel to the frame
        add(panel);

        // Button action
        btnSearch.addActionListener(e -> searchFlights());

        setVisible(true);
    }

    private void searchFlights() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM flights WHERE source=? AND destination=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tfSource.getText());
            ps.setString(2, tfDestination.getText());
            ResultSet rs = ps.executeQuery();
    
            taResults.setText(""); // Clear previous results
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
