import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Gui {
    private JPanel panel = new JPanel();
    private JFrame frame = new JFrame();

    public void createInitialWindow() {

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 5));

        JTextArea playerSelect = new JTextArea();
        playerSelect.setEditable(false);
        playerSelect.setText("Select player mode:");
        playerSelect.setVisible(true);
        panel.add(playerSelect, BorderLayout.CENTER);

        Button players1 = new Button("1 player");
        players1.addActionListener(this::actionPerformed);
        panel.add(players1);
        Button players2 = new Button("2 player");
        players2.addActionListener(this::actionPerformed);
        panel.add(players2);
        Button players3 = new Button("3 player");
        players3.addActionListener(this::actionPerformed);
        panel.add(players3);
        Button players4 = new Button("4 player");
        players4.addActionListener(this::actionPerformed);
        panel.add(players4);


        frame.setSize(500, 250);
        frame.setTitle("Casino Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        frame.add(panel);


    }

    private void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "1 player":
                setPlayerMode(1);
                break;
            case "2 player":
                setPlayerMode(2);
                break;
            case "3 player":
                setPlayerMode(3);
                break;
            case "4 player":
                setPlayerMode(4);
                break;
        }
    }

    private void setPlayerMode(int players){

        panel.removeAll();
        JTextArea playerInformation = new JTextArea();
        playerInformation.setEditable(false);
        playerInformation.setText("Enter Player Name(s)");
        playerInformation.setVisible(true);
        panel.add(playerInformation, BorderLayout.CENTER);

        switch(players) {
            case 4:
                JTextField Player4 = new JTextField(40);
                Player4.setEditable(true);
                Player4.setVisible(true);
                panel.add(Player4, BorderLayout.CENTER);
            case 3:
                JTextField Player3 = new JTextField(40);
                Player3.setEditable(true);
                Player3.setVisible(true);
                panel.add(Player3, BorderLayout.CENTER);
            case 2:
                JTextField Player2 = new JTextField(40);
                Player2.setEditable(true);
                Player2.setVisible(true);
                panel.add(Player2, BorderLayout.CENTER);
            case 1:
                JTextField Player1 = new JTextField(40);
                Player1.setEditable(true);
                Player1.setVisible(true);
                panel.add(Player1, BorderLayout.CENTER);
                break;
        }
        Button confirm = new Button("Confirm");
        panel.add(confirm, BorderLayout.CENTER);
        confirm.addActionListener(e -> {
            // TODO: Create new casino here and input player names
            // TODO: Casino class needs changes for inputs
        });

        frame.setVisible(true);

        frame.add(panel);
    }
}

