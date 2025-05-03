import java.sql.*;
import javax.swing.*;

public class ViewCustomers extends JFrame {
    JTextArea ta;

    public ViewCustomers() {
        setTitle("All Bookings");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        ta = new JTextArea();
        ta.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ta);
        scrollPane.setBounds(20, 20, 440, 320);
        add(scrollPane);

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
