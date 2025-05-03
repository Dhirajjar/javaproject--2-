import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirlineSystemGUI extends JFrame {

    // Variable to store the clicked button's action
    private String clickedButton = "";

    public AirlineSystemGUI() {
        setTitle("Airline Reservation System");
        setSize(400, 200);
        setLayout(new FlowLayout());

        // Create Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(new Dimension(150, 40));
        btnLogin.addActionListener(new ButtonController("login"));

        // Create Register Button
        JButton btnRegister = new JButton("Register");
        btnRegister.setPreferredSize(new Dimension(150, 40));
        btnRegister.addActionListener(new ButtonController("register"));

        // Add buttons to the frame
        add(btnLogin);
        add(btnRegister);

        // Basic window settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
        setVisible(true);
    }

    // Controller for the buttons
    private class ButtonController implements ActionListener {
        private String buttonName;

        public ButtonController(String buttonName) {
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Set the clicked button's name
            clickedButton = buttonName;
            // Print the result
            System.out.println("User clicked: " + clickedButton);
        }
    }

    // Method to return the clicked button's name
    public String getClickedButton() {
        return clickedButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AirlineSystemGUI gui = new AirlineSystemGUI();

            // Example: After user clicks, you can check which button was clicked
            // For testing, you can call this method after interaction
            // System.out.println("Button clicked: " + gui.getClickedButton());
        });
    }
}
