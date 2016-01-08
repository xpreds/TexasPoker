import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.*;

public class Game extends JComponent {

    public double currentBet;
    public double pot;
    public double blind;

    public int potNumber;
    public int playerCount;
    public int raiseLimit;
    public int blindCounter;

    public int[] table = new int[5];

    public boolean everyoneCheck;
    public boolean humanTurn;

    public String roundName = "";

    public ArrayList<Double> playerBoardMoney;

    public ArrayList<Player> playerlist;
    public ArrayList<Player> generalList;

    public Deck deck = new Deck();

    public Player human;

    public Random generator = new Random();

    public Evaluator evaluator = new Evaluator();

    public JButton callCheck = new JButton("Call / Check");
    public JButton raise = new JButton("Raise");
    public JButton fold = new JButton("Fold");

    public void paintComponent(Graphics g) {
      setup();
        Graphics2D g2 = (Graphics2D) g;
        Image image = Toolkit.getDefaultToolkit().getImage("poker.png");
        humanDraw(g2, image);
        aiDraw(g2, image);
        boardDraw(g2, image);
    }
    public void humanDraw(Graphics2D g2, Image image) {
        int width = 73;
        int height = 98;
        for(int i = 0; i < 5; i++) {
            int xPos = 150 + (i*75);
            int cardno = human.getCard()[i]-1;
            if(cardno < 0) {
                cardno = 0;
            }
            g2.drawImage(image, xPos, 175, xPos+width, 175+height,
                (cardno % 13)*width, (cardno / 13)*height,
                (cardno % 13)*width + width, (cardno / 13)*height + height, null);
        }
        //g2.drawString("Name: " + human.getName(), 20, 320);
        //g2.drawString("Your bet= " + human.getMoneyOB(), 320, 340);
        //g2.drawString("Your money= " + human.getMoney(), 320, 360);
    }
    public void boardDraw(Graphics2D g2, Image image) {
        int width = 73;
        int height = 98;
        g2.drawString("Pot = " + pot, 10, 5);
        g2.drawString("Current bet = " + currentBet, 10, 15);
        g2.drawString("Pot no: " + potNumber, 5, 25);
        g2.drawString(roundName, 5, 35);
        /*g2.drawImage(image, 150, 175, 150+width, 175+height,
            ((table[0]-1) % 13)*width, ((table[0]-1) / 13)*height,
            ((table[0]-1) % 13)*width + width, ((table[0]-1) / 13)*height + height);
        g2.drawImage(image, 225, 175, 225+width, 175+height,
            ((table[1]-1) % 13)*width, ((table[1]-1) / 13)*height,
            ((table[1]-1) % 13)*width + width, ((table[1]-1) / 13)*height + height);
        g2.drawImage(image, 300, 175, 300+width, 175+height,
            ((table[2]-1) % 13)*width, ((table[2]-1) / 13)*height,
            ((table[2]-1) % 13)*width + width, ((table[2]-1) / 13)*height + height);
        g2.drawImage(image, 375, 175, 375+width, 175+height,
            ((table[3]-1) % 13)*width, ((table[3]-1) / 13)*height,
            ((table[3]-1) % 13)*width + width, ((table[3]-1) / 13)*height + height);
        g2.drawImage(image, 450, 175, 450+width, 175+height,
            ((table[4]-1) % 13)*width, ((table[4]-1) / 13)*height,
            ((table[4]-1) % 13)*width + width, ((table[4]-1) / 13)*height + height);*/
        for(int i = 0; i < 5; i++) {
            int xPos = 150 + (i*75);
            int cardno = table[i]-1;
            if(cardno < 0) {
                cardno = 0;
            }
            g2.drawImage(image, xPos, 175, xPos+width, 175+height,
                (cardno % 13)*width, (cardno / 13)*height,
                (cardno % 13)*width + width, (cardno / 13)*height + height, null);
        }


    }
    public void aiDraw(Graphics2D g2, Image image) {

    }

    public void Poker() {
        setup();
        while(!(generalList.size() == 1)) {
            preFlop();
            flop();
            turn();
            river();
            showdown();
        }
    }

    public void setup() {
        human = new Player(JOptionPane.showInputDialog("Whats your name?"));
        generalList = new ArrayList<Player>(4);
        generalList.add(human);
        for(int i = 0; i < playerlist.size()-1; i++) {
            generalList.add(new Player());
        }

        playerBoardMoney = new ArrayList<Double>(generalList.size());
        //bigBlind = human.
        blindCounter = generator.nextInt(generalList.size());
        callCheck.setBounds(310, 360, 410, 405);
        raise.setBounds(310, 310, 410, 355);
        fold.setBounds(310, 410, 410, 450);

        callCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                human.call(currentBet);
                humanTurn = false;
            }
        } );
        raise.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(raiseLimit == 0) {
                    //JOptionPane.showMessageDialog("Raise limit reached! Please call or fold");
                } else {
                    human.raise(Double.parseDouble(JOptionPane.showInputDialog("How much would you like to raise?")), currentBet);
                    humanTurn = false;
                }
            }
        } );
        fold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                human.fold();
                humanTurn = false;
            }
        } );

        add(callCheck);
        add(raise);
        add(fold);
    }
    public void preFlop() {
        roundName = "Preflop";
        potNumber++;
        pot = 0;
        int round = 0;
        blindCounter++;
        raiseLimit = 3;
        playerlist = new ArrayList<Player>();
        for(int i = 0; i < playerlist.size(); i++) {
            if(playerlist.get(i).getMoney() <= 0) {
                generalList.remove(i);
            }
        }
        for(int i = 0; i < generalList.size(); i++) {
            playerlist.add(generalList.get(i));
        }
        for(int i = 0; i < playerlist.size(); i++) {
            playerlist.get(i).takeCard(deck.getCard(), deck.getCard());
        }

        playerlist.get(blindCounter % generalList.size()).call(currentBet);

        while(!(everyoneCheck)) {
            for(int i = 0; i < playerlist.size(); i++) {
                if(!(playerlist.get(i).getName().equals("AI"))) {
                    humanTurn = true;
                    while(humanTurn) {

                    }
                } else {
                    playerlist.get(i).selectAction(raiseLimit, currentBet, blind, round, table);
                }
                if(playerlist.get(i).getMoney() <= 0) {
                    playerlist.remove(i);
                }
                if(playerlist.get(i).getMoneyOB() == 0) {
                    playerlist.remove(i);
                    playerBoardMoney.remove(i);
                }
                if(playerlist.get(i).getMoneyOB() > currentBet) {
                    raiseLimit--;
                    currentBet = playerlist.get(i).getMoneyOB();
                }
            }
            for(int i = 0; i < playerlist.size(); i++) {
                int previous = i-1;
                if(previous == -1) {
                    previous = playerlist.size()-1;
                }
                if(playerlist.get(i).getMoneyOB() == playerlist.get(previous).getMoneyOB()) {
                    everyoneCheck = true;
                    System.out.println("Everyone same changing round");
                } else {
                    everyoneCheck = false;
                    System.out.println("Not same");
                    break;
                }
            }
        }
        for(int i = 0; i < playerlist.size(); i++) {
            pot = pot + playerlist.get(i).getMoneyOB();
            playerlist.get(i).setMoneyOB(0);
        }
    }
    public void flop() {
        roundName = "The Flop";
        int round = 1;
        raiseLimit = 3;
        everyoneCheck = false;
        currentBet = 0;

        table[0] = deck.getCard();
        table[1] = deck.getCard();
        table[2] = deck.getCard();

        for(int i = 0; i < playerlist.size(); i++) {
            playerlist.get(i).setMoneyOB(0);
        }
        while(!(everyoneCheck)) {
            for(int i = 0; i < playerlist.size(); i++) {
                if(!(playerlist.get(i).getName().equals("AI"))) {
                    humanTurn = true;
                    while(humanTurn) {

                    }
                } else {
                    playerlist.get(i).selectAction(raiseLimit, currentBet, blind, round, table);
                }
                if(playerlist.get(i).getMoney() <= 0) {
                    playerlist.remove(i);
                }
                if(playerlist.get(i).getMoneyOB() == 0) {
                    playerlist.remove(i);
                    playerBoardMoney.remove(i);
                }
                if(playerlist.get(i).getMoneyOB() > currentBet) {
                    raiseLimit--;
                    currentBet = playerlist.get(i).getMoneyOB();
                }
            }
            for(int i = 0; i < playerlist.size(); i++) {
                int previous = i-1;
                if(previous == -1) {
                    previous = playerlist.size()-1;
                }
                if(playerlist.get(i).getMoneyOB() == playerlist.get(previous).getMoneyOB()) {
                    everyoneCheck = true;
                    System.out.println("Everyone same changing round");
                } else {
                    everyoneCheck = false;
                    System.out.println("Not same");
                    break;
                }
            }
        }
        for(int i = 0; i < playerlist.size(); i++) {
            pot = pot + playerlist.get(i).getMoneyOB();
            playerlist.get(i).setMoneyOB(0);
        }
    }
    public void turn() {
        roundName = "The Turn";
        int round = 2;
        raiseLimit = 3;
        everyoneCheck = false;
        currentBet = 0;

        table[3] = deck.getCard();

        for(int i = 0; i < playerlist.size(); i++) {
            playerlist.get(i).setMoneyOB(0);
        }
        while(!(everyoneCheck)) {
            for(int i = 0; i < playerlist.size(); i++) {
                if(!(playerlist.get(i).getName().equals("AI"))) {
                    humanTurn = true;
                    while(humanTurn) {

                    }
                } else {
                    playerlist.get(i).selectAction(raiseLimit, currentBet, blind, round, table);
                }
                if(playerlist.get(i).getMoney() <= 0) {
                    playerlist.remove(i);
                }
                if(playerlist.get(i).getMoneyOB() == 0) {
                    playerlist.remove(i);
                    playerBoardMoney.remove(i);
                }
                if(playerlist.get(i).getMoneyOB() > currentBet) {
                    raiseLimit--;
                    currentBet = playerlist.get(i).getMoneyOB();
                }
            }
            for(int i = 0; i < playerlist.size(); i++) {
                int previous = i-1;
                if(previous == -1) {
                    previous = playerlist.size()-1;
                }
                if(playerlist.get(i).getMoneyOB() == playerlist.get(previous).getMoneyOB()) {
                    everyoneCheck = true;
                    System.out.println("Everyone same changing round");
                } else {
                    everyoneCheck = false;
                    System.out.println("Not same");
                    break;
                }
            }
        }
        for(int i = 0; i < playerlist.size(); i++) {
            pot = pot + playerlist.get(i).getMoneyOB();
            playerlist.get(i).setMoneyOB(0);
        }
    }
    public void river() {
        roundName = "The River";
        int round = 3;
        raiseLimit = 3;
        everyoneCheck = false;
        currentBet = 0;

        table[4] = deck.getCard();

        while(!(everyoneCheck)) {
            for(int i = 0; i < playerlist.size(); i++) {
                if(!(playerlist.get(i).getName().equals("AI"))) {
                    humanTurn = true;
                    while(humanTurn) {

                    }
                } else {
                    playerlist.get(i).selectAction(raiseLimit, currentBet, blind, round, table);
                }
                if(playerlist.get(i).getMoney() <= 0) {
                    playerlist.remove(i);
                }
                if(playerlist.get(i).getMoneyOB() == 0) {
                    playerlist.remove(i);
                    playerBoardMoney.remove(i);
                }
                if(playerlist.get(i).getMoneyOB() > currentBet) {
                    raiseLimit--;
                    currentBet = playerlist.get(i).getMoneyOB();
                }
            }
            for(int i = 0; i < playerlist.size(); i++) {
                int previous = i-1;
                if(previous == -1) {
                    previous = playerlist.size()-1;
                }
                if(playerlist.get(i).getMoneyOB() == playerlist.get(previous).getMoneyOB()) {
                    everyoneCheck = true;
                    System.out.println("Everyone same changing round");
                } else {
                    everyoneCheck = false;
                    System.out.println("Not same");
                    break;
                }
            }
        }
        for(int i = 0; i < playerlist.size(); i++) {
            pot = pot + playerlist.get(i).getMoneyOB();
            playerlist.get(i).setMoneyOB(0);
        }
    }
    public void showdown() {
        roundName = "The Showdown";
        int winner = 0;
        for(int i = 0; i < playerlist.size(); i++) {
            int previous = i-1;
            if(previous == -1) {
                previous = playerlist.size()-1;
            }
            if(evaluator.evaluate(playerlist.get(i).getCard(), table) < evaluator.evaluate(playerlist.get(previous).getCard(), table)) {
                winner = i;
            }
        }
        playerlist.get(winner).addMoney(pot);
    }

}
