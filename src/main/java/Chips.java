import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chips {
    private List<Integer> chipVal;
    private int currentBet;
    private Map<Integer, JButton> chipButtons;
    private JFrame frame;
    private JLabel currentBetLabel;

    public Chips() {
        chipVal = new ArrayList<>();
        chipVal.add(1);
        chipVal.add(5);
        chipVal.add(25);
        chipVal.add(100);
        chipVal.add(500);
        currentBet = 0;
        initUI();
    }

    private void initUI() {
        frame = new JFrame("Place Your Bets");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLayout(new FlowLayout());

        chipButtons = new HashMap<>();
        for (Integer denomination : chipVal) {
            JButton button = new JButton("$" + denomination);
            button.addActionListener(this::handleChipSelection);
            chipButtons.put(denomination, button);
            frame.add(button);
        }

        JButton enterButton = new JButton("Enter Bet");
        enterButton.addActionListener(e -> enterBet());
        frame.add(enterButton);

        JButton resetButton = new JButton("Reset Bet");
        resetButton.addActionListener(e -> resetBet());
        frame.add(resetButton);

        currentBetLabel = new JLabel("Current Bet: $0");
        frame.add(currentBetLabel);

        frame.setVisible(true);
    }

    public void placeBet(Player player) {
        updateChipButtonVisibility(player);
        frame.setVisible(true);
    }

    private void handleChipSelection(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int value = Integer.parseInt(clickedButton.getText().substring(1));
        currentBet += value;
        currentBetLabel.setText("Current Bet: $" + currentBet);
    }

    private void enterBet() {
        System.out.println("Bet entered: $" + currentBet);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    private void resetBet() {
        currentBet = 0;
        currentBetLabel.setText("Current Bet: $0");
    }

    private void updateChipButtonVisibility(Player player) {
        int playerChips = player.getChips();
        for (Map.Entry<Integer, JButton> entry : chipButtons.entrySet()) {
            JButton button = entry.getValue();
            int chipValue = entry.getKey();
            button.setVisible(playerChips >= chipValue);
        }
    }
}
