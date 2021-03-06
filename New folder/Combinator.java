//Ali Kaan Duranyildiz
//H. Atacan DEMIR
import java.util.Arrays;
import java.util.ArrayList;
public class Combinator {

 static ArrayList<int[]> allHands = new ArrayList<int[]>();
 
 public Combinator(){}
 
 public ArrayList<int[]> getArrayList(int[] a, int[] b){//int a playerHand, int b board cards
     
     int[] allCards = new int[7];

     allCards[0] = a[0];
     allCards[1] = a[1];
     allCards[2] = b[0];
     allCards[3] = b[1];
     allCards[4] = b[2];
     allCards[5] = b[3];
     allCards[6] = b[4];

     Arrays.sort(allCards);
     // şu durumda combination almak için sadece getArraylisti çalıştırcaz
     combination(allCards);
     
     return allHands;
 }
 
 public void combination(int[] allCards){
     
  int length = allCards.length;
 
  int combination[] = new int[5];
  
  int r = 0;  
  int index = 0;
  
  while(r >= 0){

   if(index <= (length + (r - 5))){
     combination[r] = index;

    if(r == 4){

     append(combination, allCards);
     
     index++;    
    }
    else{

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
   int[] a = new int[5];
  for(int i = 0 ; i < combination.length;i++){
   a[i] = (allCards[combination[i]]);
  }
  allHands.add(a);
 }
 
}