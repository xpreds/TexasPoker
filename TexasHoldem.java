import javax.swing.JFrame;

public class TexasHoldem {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game Atacan = new Game();
        frame.add(Atacan);
        frame.setVisible(true);
        Atacan.Poker();
    }
}