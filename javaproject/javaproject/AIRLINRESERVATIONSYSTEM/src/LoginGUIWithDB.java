import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginGUIWithDB extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;

    public LoginGUIWithDB() {
        setTitle("Login");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        setLocationRelativeTo(null);

        JLabel lblUsername = new JLabel("Username:");
        tfUsername = new JTextField(20);
        tfUsername.setPreferredSize(new Dimension(250, 30));

        JLabel lblPassword = new JLabel("Password:");
        pfPassword = new JPasswordField(20);
        pfPassword.setPreferredSize(new Dimension(250, 30));

        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(new Dimension(150, 40));
        btnLogin.addActionListener(e -> login());

        // Add components to frame
        add(lblUsername);
        add(tfUsername);
        add(lblPassword);
        add(pfPassword);
        add(btnLogin);

        setVisible(true);
    }

    private void login() {
        String username = tfUsername.getText();
        String password = String.valueOf(pfPassword.getPassword());

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                Session.currentUser = username;
                JOptionPane.showMessageDialog(this, "Login successful as " + role);
                this.dispose();

                if (role.equalsIgnoreCase("admin")) {
                    showAdminPanel();
                } else {
                    showUserPanel();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAdminPanel() {
        JFrame adminFrame = new JFrame("Admin Panel");
        adminFrame.setSize(300, 200);
        adminFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        adminFrame.setLocationRelativeTo(null);

        JButton btnViewCustomers = new JButton("View Customers");
        JButton btnAddFlight = new JButton("Add Flight");
        JButton btnLogout = new JButton("Logout");

        btnViewCustomers.setPreferredSize(new Dimension(200, 30));
        btnAddFlight.setPreferredSize(new Dimension(200, 30));
        btnLogout.setPreferredSize(new Dimension(200, 30));

        btnViewCustomers.addActionListener(e -> new ViewCustomers());
        btnAddFlight.addActionListener(e -> new AddFlight());
        btnLogout.addActionListener(e -> {
            Session.currentUser = null;
            adminFrame.dispose();
            new LoginGUIWithDB();
        });

        adminFrame.add(btnViewCustomers);
        adminFrame.add(btnAddFlight);
        adminFrame.add(btnLogout);

        adminFrame.setVisible(true);
    }

    private void showUserPanel() {
        JFrame userFrame = new JFrame("User Panel");
        userFrame.setSize(300, 200);
        userFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        userFrame.setLocationRelativeTo(null);

        JButton btnBookFlight = new JButton("Book Flight");
        JButton btnSearchFlights = new JButton("Search Flights");
        JButton btnLogout = new JButton("Logout");

        btnBookFlight.setPreferredSize(new Dimension(200, 30));
        btnSearchFlights.setPreferredSize(new Dimension(200, 30));
        btnLogout.setPreferredSize(new Dimension(200, 30));

        btnBookFlight.addActionListener(e -> new Booking());
        btnSearchFlights.addActionListener(e -> new SearchFlight());
        btnLogout.addActionListener(e -> {
            Session.currentUser = null;
            userFrame.dispose();
            new LoginGUIWithDB();
        });

        userFrame.add(btnBookFlight);
        userFrame.add(btnSearchFlights);
        userFrame.add(btnLogout);

        userFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginGUIWithDB();
    }
}
