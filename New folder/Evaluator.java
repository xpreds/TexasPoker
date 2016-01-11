/*//Ali Kaan Duranyildiz
//H. Atacan DEMIR
  possible use of evaluator class with the combinator class
  int[] allCards = evaluator.mergeAndSort(playerHand,board);
  ArrayList<int[]> allHands = combination.getArrayList(allCards);
  int[] scores = new int[21];
  for(int i = 0; i < 21; i++){
      scores[i] = evaluator.evaluate(allHands.get(i));
      //evaluated 21 times --> all probabilities
  }
  Arrays.sort(scores);
  scores[0] --> winner hand for each player
  bu alg i dört kere yaparız playerlar için ayrı ayrı
  sonra scores[0] ları playerların içine bir winning hand diye int atarız ona eşitleriz
  sonra player.getWinningHand den sıralarız
  yani
  player.winningHand = scores[0];
  eşitlik var sa benim yazacağım high card alg e bakar
  ama high kard alg
  hayır bu game de çalışacak
  sadece playerlerin içine bir int koyak ki kimin eli ne derece görelim
  direk showdown da hatta
  hadi yazak
  
  // Yani o zaman bu üstteki algoritmayı Player class ına sokuyoruz
  // Hmm direk game classsında bir array açıp sort edebilirzi
*/
//Defines the value and the suit of the cards for further processing 

public class Evaluator {
	int[] kind = new int[6];
	int[] val = new int[6];
	String payout = "";
	
	int[] hand;
	//Constructs the Evaluator object and initialise instant field variables
	public Evaluator()  {}
	
	//Defines the value and the suit of the cards for further processing
	public void valueKind() {
		for(int i = 1; i <= 5; i++) {
			kind[i] = ((hand[i-1]-1) / 13) + 1;
			val[i] = hand[i-1] % 13;
			if(val[i] == 0) {
				val[i] = 13;
			}
		}
	}
	//Sorts the deck in ascending order for further processing
	public void sort() {
		for(int i = 1; i <= 5; i++) {
			for(int j = 1; j <= 5; j++) {
				if(val[i] > val[j]) {
					int sval = val[i];
					int skin = kind[i];
					kind[i] = kind[j];
					kind[j] = skin;
					val[i] = val[j];
					val[j] = sval;
				}
			}
		}
	}
	
	public int evaluate(int[] h) {
	    hand = h;
	    valueKind();
	    sort();
	    
		//Checks condition for Flush
		if(kind[1] == kind[2] && kind[2] == kind[3] && kind[3] == kind[4] && kind[4] == kind[5]) {
			if(val[5] == 13 && val[4] == 12 && val[3] == 11 && val[2] == 10 && val[1] == 1) {
				payout = "Flush Royale payout 250";
				return 0;
			}
			if(val[5] == val[4]+1 && val[4] == val[3]+1 && val[3] == val[2]+1 && val[2] == val[1]+1) {
				payout = "Straight Flush payout 50";
				return 1;
			}
			payout = "Flush payout 5";
			return 2;
		}
		//Checks condition for Straight
		if(val[5] == val[4]+1 && val[4] == val[3]+1 && val[3] == val[2]+1 && val[2] == val[1]+1) {
			payout = "Straight payout 4";
			return 3;
		}
		//Checks condition for Four of a Kind
		if((val[1] == val[2] && val[2] == val[3] && val[3] == val[4]) ||
			(val[2] == val[3] && val[3] == val[4] && val[4] == val[5])) {
			payout = "Four of a Kind payout 25";
			return 4;
		}
		//Checks condition for Three of a Kind
		if((val[1] == val[2] && val[2] == val[3]) || 
			(val[2] == val[3] && val[3] == val[4]) ||
			(val[3] == val[4] && val[4] == val[5])) {
			payout = "Three of a Kind payout 3";
			return 5;
		}
		//Checks condition for Full House
		if((val[1] == val[2] && val[2] == val[3] && val[4] == val[5]) ||
			(val[1] == val[2] && val[3] == val[4] && val[4] == val[5])) {
			payout = "Full House payout 6";
			return 6;
		}
		//Checks condition for Two pairs
		if((val[1] == val[2] && val[3] == val[4]) ||
			(val[1] == val[2] && val[4] == val[5]) ||
			(val[2] == val[3] && val[4] == val[5])) {
			payout = "Two pairs payout 2";
			return 7;
		}
		//Checks condition for one pair
		if((val[1] == val[2]) || (val[2] == val[3]) ||
			(val[3] == val[4]) || (val[4] == val[5])) {
			payout = "One Pair payout 1";
			return 8;
		}
		payout = "LOSER!!!! No match payout null";
		return 9;
	}
}
