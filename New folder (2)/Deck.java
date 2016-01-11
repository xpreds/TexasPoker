//Ali Kaan Duranyildiz
//H. Atacan DEMIR
import java.util.ArrayList;
import java.util.Random;
import java.lang.Integer;

public class Deck {

  int[] deck = new int[209];
  int counter = 0;
  String[] cards = new String[53];

//Constructor
  public Deck() {
    deckSetup();
    shuffle();
  }
  //accessor
  public String getName(int i) {
    return cards[i];
  }
  public int getCard() {
      counter++;
      return deck[counter];
  }

//mutators
  public void shuffle() {
    Random generator = new Random();
    ArrayList<Double> selected = new ArrayList<Double>();
    for(double i = 1; i <= 52; i++) {
      selected.add(i);
    }

    for(int i = 1; i <= 52; i++) {
      int value = generator.nextInt(selected.size());

      deck[i] = (selected.get(value)).intValue();

      selected.remove(value);
    }

  }
  public void deckSetup() {
    String name = "";
    for(int i = 1; i < 14; i++) {
      name = i + " of Clubs";
      cards[i] = name;
    }

    for(int i = 14; i < 27; i++) {
      name = (i-13) + " of Spades";
      cards[i] = name;
    }

    for(int i = 27; i < 40; i++) {
      name = (i-26) + " of Hearts";
      cards[i] = name;
    }

    for(int i = 40; i < 53; i++) {
      name = (i-39) + " of Diamonds";
      cards[i] = name;
    }

    cards[1]  = "Ace of Clubs";
    cards[11] = "Jack of Clubs";
    cards[12] = "Queen of Clubs";
    cards[13] = "King of Clubs";

    cards[14] = "Ace of Spades";
    cards[24] = "Jack of Spades";
    cards[25] = "Queen of Spades";
    cards[26] = "King of Spades";

    cards[27] = "Ace of Hearts";
    cards[37] = "Jack of Hearts";
    cards[38] = "Queen of Hearts";
    cards[39] = "King of Hearts";

    cards[40] = "Ace of Diamonds";
    cards[50] = "Jack of Diamonds";
    cards[51] = "Queen of Diamonds";
    cards[52] = "King of Diamonds";
  }
}