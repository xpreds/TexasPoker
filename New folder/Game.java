import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.*;

public class Game extends JComponent {
  
  public double currentBet;
  public double pot;
  public double blind; // Can put it into setup or preflop
  
  public int potNumber;
  public int playerCount;
  public int raiseLimit;
  public int blindCounter;
  
  public int[] table = new int[5];
  
  public boolean everyoneCheck;
  public boolean humanTurn;
  
  public String roundName = "";
  
  public ArrayList<Player> playerList = new ArrayList<Player>();
  public ArrayList<Player> generalList = new ArrayList<Player>();
  
  public Deck deck = new Deck();
  
  public Player human = new Player(JOptionPane.showInputDialog("What's your name?")); // If this doesn't work we'll put it back into setup
  
  public Random generator = new Random();
  
  public Evaluator evaluator = new Evaluator();
  public Combinator com = new Combinator();
  
  public JButton callCheck = new JButton("Call / Check");
  public JButton raise = new JButton("Raise");
  public JButton fold = new JButton("Fold");
  
  public boolean startPaint = false;
  public boolean winnerPaint = false;
  
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    if(startPaint) {
      Image image = Toolkit.getDefaultToolkit().getImage("poker.png");
      humanDraw(g2, image);
      aiDraw(g2, image);
      boardDraw(g2, image);
    }
    startPaint = true;
  }
  public void humanDraw(Graphics2D g2, Image image) {
    System.out.println("Human hand: " + deck.getName(human.getCard()[0]) + "     " + deck.getName(human.getCard()[1]));
    for(int i = 0; i < 2; i++) {
      int width = 73;
      int height = 98;
      int xPos = 325 + (i*75);
      int cardNo = human.getCard()[i]-1;
      if(cardNo == -1) {
        cardNo = 0;
        width = 0;
        height = 0;
      }
      g2.drawImage(image, xPos, 450, xPos+width, 450+height,
                   (cardNo % 13)*width, (cardNo / 13)*height,
                   (cardNo % 13)*width + width, (cardNo / 13)*height + height, null);
    }
    g2.drawString("Name: " + human.getName(), 20, 420);
    g2.drawString("Your mob = " + human.getMoneyOB(), 20, 440);
    g2.drawString("Your money = " + human.getMoney(), 20, 460);
  }
  public void boardDraw(Graphics2D g2, Image image) {
    g2.drawString("Pot = " + pot, 10, 15);
    g2.drawString("Current bet = " + currentBet, 10, 30);
    g2.drawString("Blind = " + blind, 10, 45);
    g2.drawString("Pot no: " + potNumber, 10, 60);
    g2.drawString(roundName, 10, 75);
    
    for(int i = 0; i < 5; i++) {
      int width = 73;
      int height = 98;
      int xPos = 230 + (i*75);
      int cardNo = table[i]-1;
      if(cardNo == -1) {
        cardNo = 0;
        width = 0;
        height = 0;
      }
      g2.drawImage(image, xPos, 250, xPos+width, 250+height,
                   (cardNo % 13)*width, (cardNo / 13)*height,
                   (cardNo % 13)*width + width, (cardNo / 13)*height + height, null);
    }
  }
  public void aiDraw(Graphics2D g2, Image image) {
    for(int i = 0; i < generalList.size()-1; i++) {
      int width = 73;
      int height = 98;
      
      if(roundName.equals("The Showdown")) {
        if(i == 0) {
          //System.out.println("painting index "+ i);
          for(int j = 0; j < 2; j++) {
            int cardNo = playerList.get(i).getCard()[j]-1;
            if(cardNo == -1) {
              cardNo = 0;
              width = 0;
              height = 0;
            }
            int xPos = 50 + (j*75);
            g2.drawImage(image, xPos, 250, xPos+width, 250+height, (cardNo % 13)*width, (cardNo / 13)*height, (cardNo % 13)*width + width, (cardNo / 13)*height + height, null);
          }
          g2.drawString("Name: " + "Ai no: "+ i, 20, 120);
          g2.drawString("Your mob = " + generalList.get(i).getMoneyOB(), 20, 140);
          g2.drawString("Your money = " + generalList.get(i).getMoney(), 20, 160);
        } else if(i == 1) {
          //  System.out.println("painting index "+ i);
          for(int j = 0; j < 2; j++) {
            int cardNo = playerList.get(i).getCard()[j]-1;
            if(cardNo == -1) {
              cardNo = 0;
              width = 0;
              height = 0;
            }
            int xPos = 325 + (j*75);
            g2.drawImage(image, xPos, 50, xPos+width, 50+height, (cardNo % 13)*width, (cardNo / 13)*height, (cardNo % 13)*width + width, (cardNo / 13)*height + height, null);
          }
          g2.drawString("Name: " + "Ai no: "+ i, 620, 120);
          g2.drawString("Your mob = " + generalList.get(i).getMoneyOB(), 620, 140);
          g2.drawString("Your money = " + generalList.get(i).getMoney(), 620, 160);
        } else if(i == 2) {
          //System.out.println("painting index "+ i);
          for(int j = 0; j < 2; j++) {
            int cardNo = playerList.get(i).getCard()[j]-1;
            if(cardNo == -1) {
              cardNo = 0;
              width = 0;
              height = 0;
            }
            int xPos = 625 + (j*75);
            g2.drawImage(image, xPos, 250, xPos+width, 250+height, (cardNo % 13)*width, (cardNo / 13)*height, (cardNo % 13)*width + width, (cardNo / 13)*height + height, null);
          }
          g2.drawString("Name: " + "Ai no: "+ i, 620, 420);
          g2.drawString("Your mob = " + generalList.get(i).getMoneyOB(), 620, 440);
          g2.drawString("Your money = " + generalList.get(i).getMoney(), 620, 460);
        }
        
      } else {
        Image cardBack = Toolkit.getDefaultToolkit().getImage("cardBackSide.jpg");
        Image cardBack2 = Toolkit.getDefaultToolkit().getImage("cardBack.jpg");
        if(i == 0) {
          // System.out.println("painting index "+ i);
          for(int j = 0; j < 2; j++) {
            int yPos = 225 + (j*75);
            g2.drawImage(cardBack, 50, yPos, 50+height, yPos+width, 0, 0, height, width,  null);
          }
          g2.drawString("Name: " + "Ai no: "+ i, 20, 120);
          g2.drawString("Your mob = " + generalList.get(i).getMoneyOB(), 20, 140);
          g2.drawString("Your money = " + generalList.get(i).getMoney(), 20, 160);
        } else if(i == 1) {
          // System.out.println("painting index "+ i);
          for(int j = 0; j < 2; j++) {
            int xPos = 325 + (j*75);
            g2.drawImage(cardBack2, xPos, 50, xPos+width, 50+height, 0, 0, width, height, null);
          }
          g2.drawString("Name: " + "Ai no: "+ i, 620, 120);
          g2.drawString("Your mob = " + generalList.get(i).getMoneyOB(), 620, 140);
          g2.drawString("Your money = " + generalList.get(i).getMoney(), 620, 160);
        } else if(i == 2) {
          //System.out.println("painting index "+ i);
          for(int j = 0; j < 2; j++) {
            int yPos = 225 + (j*78);
            g2.drawImage(cardBack, 625, yPos, 625+height, yPos+width, 0, 0, height, width,  null);
          }
          g2.drawString("Name: " + "Ai no: "+ i, 620, 420);
          g2.drawString("Your mob = " + generalList.get(i).getMoneyOB(), 620, 440);
          g2.drawString("Your money = " + generalList.get(i).getMoney(), 620, 460);
        }
      }
    }
  }
  
  public void Poker() {
    setup();
    while(!(generalList.size() == 1)) {
      //repaint();
      preFlop();
      //repaint();
      flop();
      //repaint();
      turn();
      //repaint();
      river();
      //repaint();
      showdown();
      //repaint();
    }
  }
  public void setup() {
    for(int i = 0; i < 3; i++) {
      generalList.add(new Player());
    }
    generalList.add(human);
    
    blindCounter = generator.nextInt(generalList.size());
    callCheck.setBounds(325, 600, 150, 50);
    raise.setBounds(325, 655, 150,50);
    fold.setBounds(325, 710, 150, 50);
    
    callCheck.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(humanTurn) {
          human.call(currentBet);
          humanTurn = false;
        }
      }
    } );
    raise.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(humanTurn) {
          if(raiseLimit == 0) {
            JOptionPane.showMessageDialog(null, "Raise Limit Reached, Please Call or Fold", "Raise Limit Warning", JOptionPane.WARNING_MESSAGE);
          } else {
            human.raise(Double.parseDouble(JOptionPane.showInputDialog("How much would you like to raise?")), currentBet);
            humanTurn = false;
          }
        }
      }
    } );
    fold.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(humanTurn) {
          human.fold();
          humanTurn = false;
        }
      }
    } );
    
    add(callCheck);
    add(raise);
    add(fold);
  }
  
  public void preFlop() {
    roundName = "Preflop";
    System.out.println(roundName);
    potNumber++;
    pot = 0;
    int round = 0;
    raiseLimit = 0;  // Zer0 for demo, change back to three
    
    table[0] = 0;
    table[1] = 0;
    table[2] = 0;
    table[3] = 0;
    table[4] = 0;
    
    
    // Atacan ≈üuraya bi algoritma bulur musun. Parasƒ± sƒ±fƒ±rlanm≈ü oyuncular √ßƒ±karƒ±lsƒ±n.
    // Belki de boolean la dayapƒ±labilir. Parasƒ± sƒ±fƒ±rlandƒ±ysa Player classƒ±nda bi boolean olur ona g√∂re general listten atabiliriz
    // Veya general listten atmayƒ±z da ba≈üka bi ≈üekilde √ß√∂zeriz, kimin atƒ±lacaƒüƒ±nƒ±n indexini belirleyip
    // her tur ba≈üƒ± onlarƒ± playerlistten √ßƒ±karƒ±rƒ±z. Sen de bunlardan yola √ßƒ±karak bi d√º≈ü√ºn olur mu.
    
    playerList = generalList;
    for(int i = 0; i < playerList.size(); i++) {
      playerList.get(i).takeCard(deck.getCard(), deck.getCard());
    }
    
    
    //blind calculator
    int cumulativeMoney = 0;
    for(int i = 0; i<playerList.size();i++){
      cumulativeMoney += playerList.get(i).getMoney();
    }
    
    currentBet = 10*(cumulativeMoney/(100*playerList.size()));
    
    blindCounter++;
    playerList.get(blindCounter % playerList.size()).call(currentBet);
    repaint();
    System.out.println("Repaint #1 in Preflop begin");
    everyoneCheck = false;
    while(!(everyoneCheck)){
      for(int i = 0; i < playerList.size(); i++) {
        int n = (i + blindCounter + 1) % playerList.size();
        System.out.println("Player index: " + n);
        if(playerList.get(n).getMoney() <= 0) {
          System.out.println("Player money 0 removing index " + n);
          playerList.remove(n);
        }
        if(!(playerList.get(n).getName().equals("AI"))) {
          System.out.println("Its human turn");
          System.out.println("Human MOB is " + human.getMoneyOB());
          humanTurn = true;
          while(humanTurn) {
            try {
              Thread.sleep(500);
            }
            catch (InterruptedException e) {}
            
          }
          System.out.println("Human turn ended");
          System.out.println("Human MOB is " + human.getMoneyOB());
        } else {
          
          playerList.get(n).selectAction(raiseLimit, currentBet, blind, round, table);
          //playerList.get(n).setMoneyOB(currentBet);  // This line is for demo purposes
          System.out.println("This player MOB: " + playerList.get(n).getMoneyOB());
        }
        // general money check should be out right?
        
        
        if(playerList.get(n).getMoneyOB() < currentBet) {
          playerList.remove(n);
        }
        
        if(playerList.get(n).getMoneyOB() > currentBet) {
          raiseLimit--;
          currentBet = playerList.get(n).getMoneyOB();
          System.out.println("Player index: " + n + " has raised.");
        }
        repaint();
        System.out.println("Repaint #2 in preflop while");
      } // Everyone acted
      for(int i = 0; i < playerList.size(); i++) {
        int previous = i-1;
        if(previous == -1) {
          previous = playerList.size()-1;
        }
        if(playerList.get(i).getMoneyOB() == playerList.get(previous).getMoneyOB()) {
          everyoneCheck = true;
          System.out.println("Everyone same changing round");
        } else {
          everyoneCheck = false;
          System.out.println("Not same");
          break;
        }
      }
    }
    System.out.println("Adding to pot and changing round to flop");
    for(int i = 0; i < playerList.size(); i++) {
      pot = pot + playerList.get(i).getMoneyOB();
      playerList.get(i).setMoneyOB(0);
    }
  }
  //*************************************************************
  
  public void flop() {
    roundName = "The Flop";
    System.out.println(roundName);
    int round = 1;  // Probably unnecessary
    raiseLimit = 3;
    everyoneCheck = false;
    currentBet = 0;
    
    table[0] = deck.getCard();
    table[1] = deck.getCard();
    table[2] = deck.getCard();
    
    for(int i = 0; i < playerList.size(); i++) {
      playerList.get(i).setMoneyOB(0);
    }
    repaint();
    System.out.println("Repaint #3 in flop begin");
    while(!(everyoneCheck)) {
      for(int i = 0; i < playerList.size(); i++) {
        int n = (i + blindCounter) % playerList.size();
        System.out.println("Player index: " + n);
        if(!(playerList.get(n).getName().equals("AI"))) {
          System.out.println("Its human turn");
          System.out.println("Human MOB is " + human.getMoneyOB());
          humanTurn = true;
          while(humanTurn) {
            try {
              Thread.sleep(500);
            }
            catch (InterruptedException e) {}
          }
          
          System.out.println("Human turn ended");
          System.out.println("Human MOB is " + human.getMoneyOB());
        } else {
          playerList.get(n).selectAction(raiseLimit, currentBet, blind, round, table);
          //playerList.get(n).setMoneyOB(currentBet);
          System.out.println("This player MOB: " + playerList.get(n).getMoneyOB());
        }
        /*if(playerList.get(n).getMoney() <= 0) {
         playerList.remove(n);
         }*/
        if(playerList.get(n).getMoneyOB() < currentBet) {
          playerList.remove(n);
          
        }
        if(playerList.get(n).getMoneyOB() > currentBet) {
          raiseLimit--;
          currentBet = playerList.get(n).getMoneyOB();
        }
        repaint();
        System.out.println("Repaint #4 in flop while");
      }  // Everyone acted
      for(int i = 0; i < playerList.size(); i++) {
        int previous = i-1;
        if(previous == -1) {
          previous = playerList.size()-1;
        }
        if(playerList.get(i).getMoneyOB() == playerList.get(previous).getMoneyOB()) {
          everyoneCheck = true;
          System.out.println("Everyone same changing round");
        } else {
          everyoneCheck = false;
          System.out.println("Not same");
          break;
        }
      }
    }
    System.out.println("Adding to pot and changing round to turn");
    for(int i = 0; i < playerList.size(); i++) {
      pot = pot + playerList.get(i).getMoneyOB();
      playerList.get(i).setMoneyOB(0);
    }
  }
  //*************************************************************
  
  public void turn() {
    roundName = "The Turn";
    System.out.println(roundName);
    int round = 2;  // Probably unnecessary
    raiseLimit = 3;
    everyoneCheck = false;
    currentBet = 0;
    
    table[3] = deck.getCard();
    
    for(int i = 0; i < playerList.size(); i++) {
      playerList.get(i).setMoneyOB(0);
    }
    repaint();
    System.out.println("Repaint #5 in turn begin");
    while(!(everyoneCheck)) {
      for(int i = 0; i < playerList.size(); i++) {
        int n = (i + blindCounter) % playerList.size();
        System.out.println("Player index: " + n);
        if(!(playerList.get(n).getName().equals("AI"))) {
          System.out.println("Its human turn");
          System.out.println("Human MOB is" + human.getMoneyOB());
          humanTurn = true;
          while(humanTurn) {
            try {
              Thread.sleep(500);
            }
            catch (InterruptedException e) {}
            
          }
          
          System.out.println("Human turn ended");
          System.out.println("Human MOB is" + human.getMoneyOB());
        } else {
          playerList.get(n).selectAction(raiseLimit, currentBet, blind, round, table);
          //playerList.get(n).setMoneyOB(currentBet);
        }
        /*if(playerList.get(i).getMoney() <= 0) {
         playerList.remove(i);
         }*/
        if(playerList.get(n).getMoneyOB() < currentBet) {
          playerList.remove(n);
          
        }
        if(playerList.get(n).getMoneyOB() > currentBet) {
          raiseLimit--;
          currentBet = playerList.get(n).getMoneyOB();
        }
        repaint();
        System.out.println("Repaint #6 in turn while");
      }  // Everyone acted
      for(int i = 0; i < playerList.size(); i++) {
        int previous = i-1;
        if(previous == -1) {
          previous = playerList.size()-1;
        }
        if(playerList.get(i).getMoneyOB() == playerList.get(previous).getMoneyOB()) {
          everyoneCheck = true;
          System.out.println("Everyone same changing round");
        } else {
          everyoneCheck = false;
          System.out.println("Not same");
          break;
        }
      }
    }
    System.out.println("Adding to pot and changing round to river");
    for(int i = 0; i < playerList.size(); i++) {
      pot = pot + playerList.get(i).getMoneyOB();
      playerList.get(i).setMoneyOB(0);
    }
  }
  //*************************************************************
  
  public void river() {
    roundName = "The River";
    System.out.println(roundName);
    int round = 3;  // Probably unnecessary
    raiseLimit = 3;
    everyoneCheck = false;
    currentBet = 0;
    
    table[4] = deck.getCard();
    
    repaint();
    System.out.println("Repaint #7 in river begin");
    
    
    while(!(everyoneCheck)) {
      for(int i = 0; i < playerList.size(); i++) {
        int n = (i + blindCounter) % playerList.size();
        System.out.println("Player index: " + n);
        if(!(playerList.get(n).getName().equals("AI"))) {
          
          System.out.println("Its human turn");
          System.out.println("Human MOB is" + human.getMoneyOB());
          humanTurn = true;
          while(humanTurn) {
            try {
              Thread.sleep(500);
            }
            catch (InterruptedException e) {}
            
          }
          
          System.out.println("Human turn ended");
          System.out.println("Human MOB is" + human.getMoneyOB());
        } else {
          playerList.get(n).selectAction(raiseLimit, currentBet, blind, round, table);
          //playerList.get(n).setMoneyOB(currentBet);
          System.out.println("This player MOB: " + playerList.get(n).getMoneyOB());
        }
        if(playerList.get(n).getMoney() <= 0) {
          playerList.remove(n);
        }
        if(playerList.get(n).getMoneyOB() < currentBet) {
          playerList.remove(n);
          
        }
        if(playerList.get(n).getMoneyOB() > currentBet) {
          raiseLimit--;
          currentBet = playerList.get(n).getMoneyOB();
        }
        repaint();
        System.out.println("Repaint #8 in river while");
      }  // Everyone acted
      for(int i = 0; i < playerList.size(); i++) {
        int previous = i-1;
        if(previous == -1) {
          previous = playerList.size()-1;
        }
        if(playerList.get(i).getMoneyOB() == playerList.get(previous).getMoneyOB()) {
          everyoneCheck = true;
          System.out.println("Everyone same changing round");
        } else {
          everyoneCheck = false;
          System.out.println("Not same");
          break;
        }
      }
    }
    System.out.println("Adding to pot and changing round to showdown");
    for(int i = 0; i < playerList.size(); i++) {
      pot = pot + playerList.get(i).getMoneyOB();
      playerList.get(i).setMoneyOB(0);
    }
  }
  //*************************************************************
  
  public void showdown() {
    roundName = "The Showdown";
    int winner = 0;
    /*
    for(int j = 0; j < playerList.size();j++){
    
    int[] allCards = com.getArrayList(playerList.get(i).getCard(), table);
    
    ArrayList<int[]> allHands = com.getArrayList(allCards);
    
    int[] scores = new int[21];
    for(int i = 0; i < 21; i++){
      scores[i] = evaluator.evaluate(allHands.get(i));
      //evaluated 21 times --> all probabilities
    }
    Arrays.sort(scores);
    playerList.get(j).setWinningHand(scores[0]);
    
    } 
    yazdım setWinningHand(int)
    ve getWinningHand -> int return ediyo
    nasıl karşılaştırıcaz bunları
    
    saat üçe kadar rahat biter gibi geliyo bana
    olm hep akşam ilham geliyo sanıyoz sonra bi bakıyoz mal mal şeyler yazmışız mk
    
    yok tamam ya sıkıntı yok galiba
    
    
    ben o sırada diğer klaslara comment atıcam nasıl çalıştığıyla ilgili sonra puan gitmesin
    */
    
    
    
    Combinator com = new Combinator();
    for(int i = 0; i < playerList.size(); i++) {
      int nmin = 10;
      ArrayList<int[]> allHands = com.getArrayList(playerList.get(i).getCard(), table);
      for(int j = 0; j < 21; j++) {
        int n = evaluator.evaluate(allHands.get(j));
        if(nmin > n) {
          nmin = n;
        }
      }
      playerList.get(i).setWinningHand(nmin);
    }
    winner = 0;
    for(int i = 0; i < playerList.size(); i++) {
      int n = playerList.get(i).getWinningHand();
      if(playerList.get(winner).getWinningHand() > n) {
        winner = n;
        System.out.println("Winner index: " + winner);
      }
    }
    
    
    winnerPaint = true;
    repaint();
    
    System.out.println("The winner of this pot is player index: " + winner);
    playerList.get(winner).addMoney(pot);
    try {
      Thread.sleep(5000);
    }
    catch (InterruptedException e) {}
    int currentSize = generalList.size();
    for(int i = 0; i < currentSize ; i++) {
      if(generalList.get(i).getMoney() <= 0) {
        System.out.println("Removing from generalList index: " + i);
        generalList.remove(i);
      }
    }
  }
}