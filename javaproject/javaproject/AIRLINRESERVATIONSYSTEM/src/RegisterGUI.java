import java.sql.*;
import javax.swing.*;

public class RegisterGUI extends JFrame {

    private JTextField tfName, tfEmail, tfMobile, tfPassport, tfNationality;
    private JPasswordField pfPassword;
    private JButton btnRegister;
    private JComboBox<String> cbRole; // ComboBox for Role

    public RegisterGUI() {
        setTitle("User Registration");
        setSize(400, 500); // Increased size to accommodate the role field
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Name Field
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 30, 100, 25);
        add(lblName);
        tfName = new JTextField();
        tfName.setBounds(150, 30, 200, 25);
        add(tfName);

        // Password Field
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(30, 70, 100, 25);
        add(lblPassword);
        pfPassword = new JPasswordField();
        pfPassword.setBounds(150, 70, 200, 25);
        add(pfPassword);

        // Email Field
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 110, 100, 25);
        add(lblEmail);
        tfEmail = new JTextField();
        tfEmail.setBounds(150, 110, 200, 25);
        add(tfEmail);

        // Mobile Field
        JLabel lblMobile = new JLabel("Mobile No:");
        lblMobile.setBounds(30, 150, 100, 25);
        add(lblMobile);
        tfMobile = new JTextField();
        tfMobile.setBounds(150, 150, 200, 25);
        add(tfMobile);

        // Passport Field
        JLabel lblPassport = new JLabel("Passport:");
        lblPassport.setBounds(30, 190, 100, 25);
        add(lblPassport);
        tfPassport = new JTextField();
        tfPassport.setBounds(150, 190, 200, 25);
        add(tfPassport);

        // Nationality Field
        JLabel lblNationality = new JLabel("Nationality:");
        lblNationality.setBounds(30, 230, 100, 25);
        add(lblNationality);
        tfNationality = new JTextField();
        tfNationality.setBounds(150, 230, 200, 25);
        add(tfNationality);

        // Role ComboBox
        JLabel lblRole = new JLabel("Role:");
        lblRole.setBounds(30, 270, 100, 25);
        add(lblRole);
        String[] roles = {"User", "Admin"}; // Define roles
        cbRole = new JComboBox<>(roles);
        cbRole.setBounds(150, 270, 200, 25);
        add(cbRole);

        // Register Button
        btnRegister = new JButton("Register");
        btnRegister.setBounds(130, 320, 120, 35);
        add(btnRegister);

        // Action Listener for Register Button
        btnRegister.addActionListener(e -> {
            String name = tfName.getText();
            String password = new String(pfPassword.getPassword());
            String email = tfEmail.getText();
            String mobile = tfMobile.getText();
            String passport = tfPassport.getText();
            String nationality = tfNationality.getText();
            String role = cbRole.getSelectedItem().toString(); // Get selected role

            try {
                Connection con = DBConnection.getConnection();  // Use your connection method
                String sql = "INSERT INTO users (name, password, email, mobile, passport, nationality, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, password);
                ps.setString(3, email);
                ps.setString(4, mobile);
                ps.setString(5, passport);
                ps.setString(6, nationality);
                ps.setString(7, role);  // Insert role into database

                int result = ps.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                    dispose(); // Close the registration window
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to register. Try again.");
                }

                ps.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterGUI();
    }
}
