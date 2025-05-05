import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirlineSystemGUI extends JFrame {

    // Variable to store the clicked button's action
    private String clickedButton = "";

    public AirlineSystemGUI() {
        setTitle("Airline Reservation System");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window

        // Use a panel with GridBagLayout for better control
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light pastel background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("Welcome to Airline Reservation System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Create Login Button
        JButton btnLogin = new JButton("Login");
        styleButton(btnLogin);
        btnLogin.addActionListener(new ButtonController("login"));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(btnLogin, gbc);

        // Create Register Button
        JButton btnRegister = new JButton("Register");
        styleButton(btnRegister);
        btnRegister.addActionListener(new ButtonController("register"));
        gbc.gridx = 1;
        mainPanel.add(btnRegister, gbc);

        // Add panel to the frame
        add(mainPanel);
        setVisible(true);
    }

    // Apply consistent styling to buttons
    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(160, 45));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBackground(new Color(70, 130, 180));  // Steel Blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Controller for the buttons
    private class ButtonController implements ActionListener {
        private String buttonName;

        public ButtonController(String buttonName) {
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clickedButton = buttonName;
            System.out.println("User clicked: " + clickedButton);
        }
    }

    // Method to return the clicked button's name
    public String getClickedButton() {
        return clickedButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AirlineSystemGUI();
        });
    }
}
