import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterGUI extends JFrame {

    private JTextField tfName, tfEmail, tfMobile, tfPassport, tfNationality;
    private JPasswordField pfPassword;
    private JButton btnRegister;
    private JComboBox<String> cbRole;

    public RegisterGUI() {
        setTitle("User Registration");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use BoxLayout for vertical form-style layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        tfName = createLabeledField(panel, "Name:");
        pfPassword = new JPasswordField(20);
        addLabeledComponent(panel, "Password:", pfPassword);

        tfEmail = createLabeledField(panel, "Email:");
        tfMobile = createLabeledField(panel, "Mobile No:");
        tfPassport = createLabeledField(panel, "Passport:");
        tfNationality = createLabeledField(panel, "Nationality:");

        cbRole = new JComboBox<>(new String[]{"User", "Admin"});
        cbRole.setPreferredSize(new Dimension(250, 30));
        addLabeledComponent(panel, "Role:", cbRole);

        btnRegister = new JButton("Register");
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setPreferredSize(new Dimension(120, 35));
        btnRegister.addActionListener(e -> registerUser());

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnRegister);

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

    private void registerUser() {
        String name = tfName.getText();
        String password = new String(pfPassword.getPassword());
        String email = tfEmail.getText();
        String mobile = tfMobile.getText();
        String passport = tfPassport.getText();
        String nationality = tfNationality.getText();
        String role = cbRole.getSelectedItem().toString();

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO users (name, password, email, mobile, passport, nationality, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setString(4, mobile);
            ps.setString(5, passport);
            ps.setString(6, nationality);
            ps.setString(7, role);

            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register. Try again.");
            }

            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RegisterGUI();
    }
}
