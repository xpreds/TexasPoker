//Ali Kaan duranyildiz
//H. Atacan Demir
import javax.swing.JFrame;

public class TexasHoldem {
    //this is the main class where the program is initiated
    // game class is where the source code of the game is
    // The properties of the program frame are set
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setSize(900, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();
        frame.add(game);
        frame.setVisible(true);
        game.Poker();
    }
}