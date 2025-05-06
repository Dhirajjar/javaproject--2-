import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddFlight extends JFrame {

    private JTextField tfFlightNo, tfSource, tfDestination, tfDepart, tfArrive, tfPrice;
    private JButton btnAdd;

    public AddFlight() {
        setTitle("Add Flight");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Form layout with padding and spacing
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        tfFlightNo = createLabeledField(panel, "Flight No:");
        tfSource = createLabeledField(panel, "Source:");
        tfDestination = createLabeledField(panel, "Destination:");
        tfDepart = createLabeledField(panel, "Departure:");
        tfArrive = createLabeledField(panel, "Arrival:");
        tfPrice = createLabeledField(panel, "Price:");

        btnAdd = new JButton("Add Flight");
        btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAdd.setPreferredSize(new Dimension(120, 35));
        btnAdd.addActionListener(e -> addFlight());

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnAdd);

        add(panel);
        setVisible(true);
    }

    private JTextField createLabeledField(JPanel panel, String label) {
        JTextField field = new JTextField(20);
        addLabeledComponent(panel, label, field);
        return field;
    }

    private void addLabeledComponent(JPanel panel, String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(label);
        panel.add(component);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addFlight() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO flights (flight_no, source, destination, departure, arrival, price) VALUES (?, ?, ?, ?, ?, ?)";
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
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new AddFlight();
    }
}
