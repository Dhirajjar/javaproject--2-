import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Booking extends JFrame {
    private JTextField tfName, tfPassport, tfFlightNo;
    private JButton btnBook;

    public Booking() {
        setTitle("Book Flight");
        setSize(300, 250);
        setLayout(new GridLayout(5, 1));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tfName = new JTextField();
        tfPassport = new JTextField();
        tfFlightNo = new JTextField();
        btnBook = new JButton("Book");

        add(new JLabel("Name:"));
        add(tfName);
        add(new JLabel("Passport No:"));
        add(tfPassport);
        add(new JLabel("Flight No:"));
        add(tfFlightNo);
        add(btnBook);

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
