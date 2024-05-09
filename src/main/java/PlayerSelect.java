import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerSelect extends JPanel {
    private int numberOfPlayers = 0;
    private JPanel textFieldsPanel;
    private List<JTextField> playerTextFields;
    private Consumer<ArrayList<Player>> playerSubmissionListener;

    public PlayerSelect() {
        setLayout(new BorderLayout());
        playerTextFields = new ArrayList<>();

        // Panel for player number selection buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        add(buttonPanel, BorderLayout.NORTH);

        // Panel for entering player names
        textFieldsPanel = new JPanel(new FlowLayout());
        add(textFieldsPanel, BorderLayout.CENTER);

        // Panel for control buttons like Start and Back
        JPanel controlPanel = new JPanel(new FlowLayout());
        add(controlPanel, BorderLayout.SOUTH);

        // Adding number selection buttons
        for (int i = 1; i <= 4; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener(this::handlePlayers);
            buttonPanel.add(button);
        }

        // Start game button
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> startGame());
        controlPanel.add(startButton);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> backToMainMenu());
        controlPanel.add(backButton);
    }

    private void handlePlayers(ActionEvent e) {
        numberOfPlayers = Integer.parseInt(e.getActionCommand());
        updateTextFields(numberOfPlayers);
    }

    private void updateTextFields(int numPlayers) {
        textFieldsPanel.removeAll();
        playerTextFields.clear();  // Clear existing text fields

        // Create new text fields for each player
        for (int i = 0; i < numPlayers; i++) {
            JTextField textField = new JTextField(10);
            textField.setText("Player " + (i + 1));  // Placeholder text
            textFieldsPanel.add(textField);
            playerTextFields.add(textField);
        }

        // Refresh the UI to show the new text fields
        textFieldsPanel.revalidate();
        textFieldsPanel.repaint();
    }

    private void startGame() {
        ArrayList<Player> players = new ArrayList<>();
        for (JTextField textField : playerTextFields) {
            players.add(new Player(textField.getText()));  // Create player from text field
        }
        if (playerSubmissionListener != null) {
            playerSubmissionListener.accept(players);  // Pass players to listener
        }
    }

    private void backToMainMenu() {
        System.out.println("Returning to the main menu...");  // Placeholder action
    }

    public void setPlayerSubmissionListener(Consumer<ArrayList<Player>> listener) {
        this.playerSubmissionListener = listener;
    }
}
