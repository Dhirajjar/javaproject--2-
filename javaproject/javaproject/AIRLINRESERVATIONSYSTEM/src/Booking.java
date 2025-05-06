import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Booking extends JFrame {
    private JTextField tfName, tfPassport, tfFlightNo;
    private JButton btnBook;

    public Booking() {
        setTitle("Book Flight");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Use a panel for better structure and layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Name label and text field
        JLabel lblName = new JLabel("Name:");
        tfName = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblName, gbc);
        gbc.gridx = 1;
        panel.add(tfName, gbc);

        // Passport label and text field
        JLabel lblPassport = new JLabel("Passport No:");
        tfPassport = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassport, gbc);
        gbc.gridx = 1;
        panel.add(tfPassport, gbc);

        // Flight No label and text field
        JLabel lblFlightNo = new JLabel("Flight No:");
        tfFlightNo = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblFlightNo, gbc);
        gbc.gridx = 1;
        panel.add(tfFlightNo, gbc);

        // Book button
        btnBook = new JButton("Book Flight");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(btnBook, gbc);

        // Add the panel to the frame
        add(panel);

        // Button action
        btnBook.addActionListener(e -> book());

        setVisible(true);
    }

    private void book() {
        String name = tfName.getText().trim();
        String passport = tfPassport.getText().trim();
        String flightNo = tfFlightNo.getText().trim();

        if (name.isEmpty() || passport.isEmpty() || flightNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();

            // ✅ Check if passport exists in users table
            String checkUserSql = "SELECT * FROM users WHERE passport = ?";
            PreparedStatement checkUserPs = con.prepareStatement(checkUserSql);
            checkUserPs.setString(1, passport);
            ResultSet userRs = checkUserPs.executeQuery();

            if (!userRs.next()) {
                JOptionPane.showMessageDialog(this, "Invalid passport number. No such user found.");
                return;
            }

            // ✅ Check if flight exists in flights table
            String checkFlightSql = "SELECT * FROM flights WHERE flight_no = ?";
            PreparedStatement checkFlightPs = con.prepareStatement(checkFlightSql);
            checkFlightPs.setString(1, flightNo);
            ResultSet flightRs = checkFlightPs.executeQuery();

            if (!flightRs.next()) {
                JOptionPane.showMessageDialog(this, "Invalid flight number. No such flight found.");
                return;
            }

            // ✅ Insert into customers table
            String insertSql = "INSERT INTO customers (name, passport, flightNo) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertSql);
            ps.setString(1, name);
            ps.setString(2, passport);
            ps.setString(3, flightNo);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Flight booked successfully!");
            this.dispose();

            // Close resources
            userRs.close();
            flightRs.close();
            checkUserPs.close();
            checkFlightPs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Booking();
    }
}
