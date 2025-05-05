import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class ViewCustomers extends JFrame {
    private JTextArea ta;

    public ViewCustomers() {
        setTitle("All Bookings");
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel with GridBagLayout for better alignment and organization
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Text area for displaying results
        ta = new JTextArea(15, 40);
        ta.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ta);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(scrollPane, gbc);

        // Add panel to the frame
        add(panel);

        loadBookings();

        setVisible(true);
    }

    private void loadBookings() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("Loading bookings..."); // Debugging line
            while (rs.next()) {
                ta.append(" Name: " + rs.getString("name") +
                          ", Passport: " + rs.getString("passport_no") +
                          ", Flight: " + rs.getString("flight_no") + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
