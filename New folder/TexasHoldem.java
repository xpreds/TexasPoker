import javax.swing.JFrame;

public class TexasHoldem {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1200, 900);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();
        frame.add(game);
        frame.setVisible(true);
        game.Poker();
    }
}