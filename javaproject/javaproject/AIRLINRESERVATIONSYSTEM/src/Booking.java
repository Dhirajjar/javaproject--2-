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
        String name = tfName.getText();
        String passport = tfPassport.getText();
        String flightNo = tfFlightNo.getText();

        System.out.println("Booking flight for: " + name + ", Passport: " + passport + ", Flight No: " + flightNo); // Debugging line
    
        if (name.isEmpty() || passport.isEmpty() || flightNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }
    
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO customers (name, passport_no, flight_no) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name); // 👈 logged-in user
            ps.setString(2, passport);
            ps.setString(3, flightNo);
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Flight booked successfully!");
            this.dispose(); // close window
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
