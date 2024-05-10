import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Casino {
    private final ArrayList<Player> players;
//ui stuff
    private JFrame frame;
    private JPanel mainPanel;

    public Casino(ArrayList<Player> playerList) {
        this.players = playerList;
        initUI();
    }

    public void initUI() {
        frame = new JFrame("Casino");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        mainPanel = new JPanel(new BorderLayout());
        JTextArea playerInfo = new JTextArea();
        playerInfo.setEditable(false);

        StringBuilder info = new StringBuilder();
        for (Player player : players) {
            info.append("Player ").append(player.getID()).append(": ")
                    .append(player.getName()).append("\nChips: ").append(player.getChips())
                    .append("\n");
        }
        playerInfo.setText(info.toString());
        mainPanel.add(new JScrollPane(playerInfo), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton slotsButton = new JButton("Slots");
        JButton blackjackButton = new JButton("Blackjack");
        JButton rouletteButton = new JButton("Roulette");
        JButton exitButton = new JButton("Exit");

        slotsButton.addActionListener(e -> playGame(new Slots(players)));
        blackjackButton.addActionListener(e -> playGame(new Blackjack(players)));
        rouletteButton.addActionListener(e -> playGame(new Roulette(players)));
        exitButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(slotsButton);
        buttonPanel.add(blackjackButton);
        buttonPanel.add(rouletteButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;  // Return the JPanel directly
    }

    private void playGame(Game game) {

        boolean keepPlaying = true;
        while (keepPlaying) {
            game.play();
            System.out.println("Continue?");
            String response = " ";

            if (response.equals("no")) {
                keepPlaying = false;
                System.out.println("Exiting");
            }
        }
    }
}
