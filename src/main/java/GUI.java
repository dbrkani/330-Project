import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private PlayerSelect playerSelect;
    private Casino casino;

    public GUI() {
        frame = new JFrame("Main Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        frame.getContentPane().add(cardPanel);

        initializePanels();
        frame.setVisible(true);
    }

    private void initializePanels() {
        // Main menu panel
        JPanel menuPanel = new JPanel(new FlowLayout());
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> cardLayout.show(cardPanel, "PlayerSelection"));
        menuPanel.add(startButton);

        // Player create panel
        playerSelect = new PlayerSelect();
        playerSelect.setPlayerSubmissionListener(this::initializeCasino);
        cardPanel.add(playerSelect, "PlayerSelection");

        // Add the main menu the cardPanel
        cardPanel.add(menuPanel, "MainMenu");

        // Show main menu first
        cardLayout.show(cardPanel, "MainMenu");
    }
// initialize a casino. probably not a good idea to do it in the UI class.
    private void initializeCasino(ArrayList<Player> players) {
        casino = new Casino(players);
        JPanel casinoPanel = casino.getMainPanel();
     //   cardPanel.add(casinoPanel, "Casino");
        cardLayout.show(cardPanel, "Casino");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
