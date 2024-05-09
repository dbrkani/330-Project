import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        //main used as a launch pad for the project, regulating handling the project to the casino class.
        //Casino casino = new Casino();
        //casino.chooseGame();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });

    }

}