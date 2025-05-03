import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginGUIWithDB extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;

    public LoginGUIWithDB() {
        setTitle("Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        tfUsername = new JTextField();
        pfPassword = new JPasswordField();
        btnLogin = new JButton("Login");

        add(new JLabel("Username:"));
        add(tfUsername);
        add(new JLabel("Password:"));
        add(pfPassword);
        add(btnLogin);

        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String username = tfUsername.getText();
        String password = String.valueOf(pfPassword.getPassword());

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showAdminPanel() {
        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 1));
        frame.setLocationRelativeTo(null);

        JButton btnViewCustomers = new JButton("View Customers");
        JButton btnAddFlight = new JButton("Add Flight");
        JButton btnLogout = new JButton("Logout");

        btnViewCustomers.addActionListener(e -> new ViewCustomers());
        btnAddFlight.addActionListener(e -> new AddFlight());
        btnLogout.addActionListener(e -> {
            Session.currentUser = null;
            frame.dispose();
            new LoginGUIWithDB();
        });

        frame.add(btnViewCustomers);
        frame.add(btnAddFlight);
        frame.add(btnLogout);

        frame.setVisible(true);
    }

    private void showUserPanel() {
        JFrame frame = new JFrame("User Panel");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 1));
        frame.setLocationRelativeTo(null);

        JButton btnBookFlight = new JButton("Book Flight");
        JButton btnViewFlights = new JButton("Search Flights");
        JButton btnLogout = new JButton("Logout");

        btnBookFlight.addActionListener(e -> new Booking());
        btnViewFlights.addActionListener(e -> new SearchFlight());
        btnLogout.addActionListener(e -> {
            Session.currentUser = null;
            frame.dispose();
            new LoginGUIWithDB();
        });

        frame.add(btnBookFlight);
        frame.add(btnViewFlights);
        frame.add(btnLogout);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginGUIWithDB();
    }
}
