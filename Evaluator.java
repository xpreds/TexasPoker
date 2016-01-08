//Ali Kaan Duranyildiz
//H. Atacan DEMIR
import java.util.ArrayList;

public class Evaluator {

  public int[] suit = new int[6];
  public int[] val = new int[6];

  public int[] playersHand = new int[2];
  public int[] board = new int[5];

  public ArrayList<int[]> allHands = new ArrayList<int[]>();

  //Constructs the Evaluator
  public Evaluator() {}

//*********************************
//source: http://hmkcode.com/calculate-find-all-possible-combinations-of-an-array-using-java/
// the algorithm found on this source was edited and re coded for our purposes

    public void arrayMerger(int[] a, int[] b){
  int[] allCards = new int[7];

        allCards[0] = a[0];
        allCards[1] = a[1];
        allCards[2] = b[0];
        allCards[3] = b[1];
        allCards[4] = b[2];
        allCards[5] = b[3];
        allCards[6] = b[4];

  combinator(allCards,5);
 }

    public void combinator(int[]  allCards, int K){

  // get the length of the array
  // e.g. for {'A','B','C','D'} => N = 4
  int N = allCards.length;

  // get the combination by index
  // e.g. 01 --> AB , 23 --> CD
  int combination[] = new int[K];

  // position of current index
  //  if (r = 1)    r*
  // index ==>  0 | 1 | 2
  // element ==>  A | B | C
  int r = 0;
  int index = 0;

  while(r >= 0){
   // possible indexes for 1st position "r=0" are "0,1,2" --> "A,B,C"
   // possible indexes for 2nd position "r=1" are "1,2,3" --> "B,C,D"

   // for r = 0 ==> index < (4+ (0 - 2)) = 2
   if(index <= (N + (r - K))){
     combination[r] = index;

    // if we are at the last position print and increase the index
    if(r == K-1){

     //do something with the combination e.g. add to list or print
     append(combination, allCards);
     index++;
    }
    else{
     // select index for next position
     index = combination[r]+1;
     r++;
    }
   }
   else{
    r--;
    if(r > 0)
     index = combination[r]+1;
    else
     index = combination[0]+1;
   }
  }
 }

    public void append(int[] combination, int[] allCards){

  int[] cardCombinations = new int[5];//
  for(int z = 0; z < combination.length; z++){
   cardCombinations[z] = allCards[combination[z]];
  }
  allHands.add(cardCombinations);
 }
//***************************************
// end of citation


//Defines the value and the suit of the cards for further processing
    public void valueSuit(int[] a) {
    for(int i = 1; i <= 5; i++) {
      suit[i] = ((a[i-1]-1) / 13) + 1;
      val[i] = ((a[i-1]-1) % 13);
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
              int sSuit = suit[i];

              suit[i] = suit[j];
              suit[j] = sSuit;
              val[i] = val[j];
              val[j] = sval;

            }
          }
          System.out.println("suit of "+i+" "+val[i-1]);
          System.out.println("suit of "+i+" "+suit[i-1]);
        }
      }

    public int evaluate(int[] playersHand,int[] board) {
int[] combinationsEvaluations = new int[21];

    arrayMerger(playersHand,board);
    for(int i = 0; i < allHands.size(); i++){

    valueSuit(allHands.get(i));
    sort();

    

    //Checks condition for Flush
    if(suit[1] == suit[2] && suit[2] == suit[3] && suit[3] == suit[4] && suit[4] == suit[5]) {
        //Royal Flush
      if(val[1] == 13 && val[2] == 12 && val[3] == 11 && val[4] == 10 && val[5] == 1) {

        combinationsEvaluations[i] = 0;
      }
      //Straight Flush
      if(val[5] == val[4]+1 && val[4] == val[3]+1 && val[3] == val[2]+1 && val[2] == val[1]+1 ||
         val[5] == val[4]+1 && val[4] == val[3]+1 && val[3] == val[2]+1 && val[1] == 1) {

        combinationsEvaluations[i] = 1;
      }
      //Flush
      combinationsEvaluations[i] = 2;
    }
    //Straight
    if(val[5] == val[4]+1 && val[4] == val[3]+1 && val[3] == val[2]+1 && val[2] == val[1]+1 ||
       val[5] == val[4]+1 && val[4] == val[3]+1 && val[3] == val[2]+1 && val[1] == 1)  {

      combinationsEvaluations[i] = 3;
    }
    //Four of a suit
    if((val[1] == val[2] && val[2] == val[3] && val[3] == val[4]) ||
       (val[2] == val[3] && val[3] == val[4] && val[4] == val[5])) {

      combinationsEvaluations[i] = 4;
    }
    //Full House
    if((val[1] == val[2] && val[2] == val[3] && val[4] == val[5]) ||
       (val[1] == val[2] && val[3] == val[4] && val[4] == val[5])) {

      combinationsEvaluations[i] = 5;
    }
    //Three of a suit
    if((val[1] == val[2] && val[2] == val[3]) ||
       (val[2] == val[3] && val[3] == val[4]) ||
       (val[3] == val[4] && val[4] == val[5])) {

      combinationsEvaluations[i] = 6;
    }
    //Checks condition for Two pairs
    if((val[1] == val[2] && val[3] == val[4]) ||
       (val[1] == val[2] && val[4] == val[5]) ||
       (val[2] == val[3] && val[4] == val[5])) {

      combinationsEvaluations[i] = 7;
    }
    //Checks condition for one pair
    if((val[1] == val[2]) || (val[2] == val[3]) ||
       (val[3] == val[4]) || (val[4] == val[5])) {

      combinationsEvaluations[i] = 8;
    }
    //You couldn't win anything
    combinationsEvaluations[i] = 9;
  }
  int temp = 0;
  for (int i = 0; i < ( 21 - 1 ); i++) {
      for (int j = 0; j < 21 - i - 1; j++) {
        if (combinationsEvaluations[j] > combinationsEvaluations[j+1]){
           temp = combinationsEvaluations[j];
           combinationsEvaluations[j] = combinationsEvaluations[j+1];
           combinationsEvaluations[j+1] = temp;
        }
      }
    }
  return combinationsEvaluations[0];
  }
}