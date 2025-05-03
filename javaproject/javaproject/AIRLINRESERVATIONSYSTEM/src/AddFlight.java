import java.sql.*;
import javax.swing.*;

public class AddFlight extends JFrame {
    JTextField tfFlightNo, tfSource, tfDestination, tfDepart, tfArrive, tfPrice;
    JButton btnAdd;

    public AddFlight() {
        setTitle("Add Flight");
        setSize(400, 300);
        setLayout(null);

        JLabel lblFlightNo = new JLabel("Flight No:");
        lblFlightNo.setBounds(20, 20, 100, 20);
        add(lblFlightNo);
        tfFlightNo = new JTextField();
        tfFlightNo.setBounds(150, 20, 200, 20);
        add(tfFlightNo);

        JLabel lblSource = new JLabel("Source:");
        lblSource.setBounds(20, 50, 100, 20);
        add(lblSource);
        tfSource = new JTextField();
        tfSource.setBounds(150, 50, 200, 20);
        add(tfSource);

        JLabel lblDestination = new JLabel("Destination:");
        lblDestination.setBounds(20, 80, 100, 20);
        add(lblDestination);
        tfDestination = new JTextField();
        tfDestination.setBounds(150, 80, 200, 20);
        add(tfDestination);

        JLabel lblDepart = new JLabel("Departure:");
        lblDepart.setBounds(20, 110, 100, 20);
        add(lblDepart);
        tfDepart = new JTextField();
        tfDepart.setBounds(150, 110, 200, 20);
        add(tfDepart);

        JLabel lblArrive = new JLabel("Arrival:");
        lblArrive.setBounds(20, 140, 100, 20);
        add(lblArrive);
        tfArrive = new JTextField();
        tfArrive.setBounds(150, 140, 200, 20);
        add(tfArrive);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(20, 170, 100, 20);
        add(lblPrice);
        tfPrice = new JTextField();
        tfPrice.setBounds(150, 170, 200, 20);
        add(tfPrice);

        btnAdd = new JButton("Add Flight");
        btnAdd.setBounds(150, 210, 150, 30);
        add(btnAdd);

        btnAdd.addActionListener(e -> addFlight());

        setVisible(true);
    }

    private void addFlight() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO flight (flightNo, Source, Destination, Departure, Arrival, Price) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tfFlightNo.getText());
            ps.setString(2, tfSource.getText());
            ps.setString(3, tfDestination.getText());
            ps.setString(4, tfDepart.getText());
            ps.setString(5, tfArrive.getText());
            ps.setDouble(6, Double.parseDouble(tfPrice.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Flight added successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
