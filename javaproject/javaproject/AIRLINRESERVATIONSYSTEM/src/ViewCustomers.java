import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ViewCustomers extends JFrame {

    private JTextField tfSource, tfDestination;
    private JButton btnView;
    private JTextArea taResult;

    public ViewCustomers() {
        setTitle("View Customers");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        tfSource = createLabeledField(panel, "Source:");
        tfDestination = createLabeledField(panel, "Destination:");

        btnView = new JButton("View Customers");
        btnView.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnView.setPreferredSize(new Dimension(150, 35));
        btnView.addActionListener(e -> fetchCustomers());

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnView);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        taResult = new JTextArea(15, 40);
        taResult.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taResult);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }

    private JTextField createLabeledField(JPanel panel, String label) {
        JTextField field = new JTextField(20);
        JLabel lbl = new JLabel(label);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(lbl);
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        return field;
    }

    private void fetchCustomers() {
        String source = tfSource.getText().trim().toLowerCase();
        String destination = tfDestination.getText().trim().toLowerCase();

        if (source.isEmpty() || destination.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both source and destination.");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT c.name, c.passport_no, c.flight_no, f.source, f.destination " +
                         "FROM customers c " +
                         "JOIN flights f ON c.flightNo = f.flight_no";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            StringBuilder result = new StringBuilder();

            System.out.println(rs.getFetchSize()); // Debugging line

            while (rs.next()) {
                String flightSrc = rs.getString("source").trim().toLowerCase();
                String flightDest = rs.getString("destination").trim().toLowerCase();
                System.out.println(flightSrc);
                System.out.println(flightDest);
                if (flightSrc.equals(source) && flightDest.equals(destination)) {
                    result.append("Name: ").append(rs.getString("name")).append("\n");
                    result.append("Passport: ").append(rs.getString("passport")).append("\n");
                    result.append("Flight No: ").append(rs.getString("flight_no")).append("\n");
                    result.append("------------------------------\n");
                }
            }

            taResult.setText(result.length() > 0 ? result.toString() : "No matching records found.");

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            taResult.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ViewCustomers();
    }
}
