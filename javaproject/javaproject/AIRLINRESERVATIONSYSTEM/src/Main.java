public class Main {
    public static void main(String[] args) {
        AirlineSystemGUI gui = new AirlineSystemGUI();

        // Wait until user clicks one of the buttons
        while (gui.getClickedButton().isEmpty()) {
            try {
                Thread.sleep(100); // wait for user to click
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Now check what was clicked
        String action = gui.getClickedButton();
        if (action.equals("login")) {
            // Call your login window here
            new LoginGUIWithDB();  // Replace with your actual class name
        } else if (action.equals("register")) {
            // Call your register window here
            new RegisterGUI();  // Replace with your actual class name
        }

        // Close the initial window
        gui.dispose();
    }
}
