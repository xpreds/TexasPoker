//H. Atacan DEMIR
//Ali Kaan Duranyildiz
import java.util.Random;
public class Player{

    Random rand = new Random();
    Evaluator evaluator = new Evaluator();

    public String name = "";
    public boolean isAI = false;

    public double money = 1000;
    public double moneyOnBoard = 0;

    public int[] hand = new int[2];

    //constructors
    //human constructor
    public Player(String n) {
        name = n;
    }
    //AI constructor
    public Player() {
        name = "AI";
        isAI = true;
    }
    //mutator
    public void addMoney(double a){
        money+=a;
    }
    public void setMoneyOB(double a){
        moneyOnBoard = a;
    }
    public void takeCard(int i,int j) {
    hand[0] = i;
    hand[1] = j;
    }
    //accesor methods
    public double getMoney() {
    return money;
    }
    public double getMoneyOB() {// getMoneyOnBoard
    return moneyOnBoard;
    }
    public String getName() {
    return name;
    }
    public int[] getCard() {
    return hand;
    }
    public boolean isRobot(){
        return isAI;
    }
    //action methods
    public void call(double a){
        moneyOnBoard+=a;
        money-=a;
    }
    public void raise(double a,double b){
        moneyOnBoard+=(a+b);
        money-=(a+b);
    }
    public void fold(){
        moneyOnBoard = 0;
    }
    //AI action selector
    public void selectAction(int raiseLimit, double a, double b,int round, int[] t) {
      // If has anything matching call or raise
      if(evaluator.evaluate(hand, t) > 0) {
        if(rand.nextInt(2) == 1 && raiseLimit > 0) {
          raise(a,b);
        } else {
          call(a);
        }
      } else {
        // Cannot fold early game
        if(round == 0 || round == 1) {
          if(rand.nextInt(2) == 1) {
            raise(a,b);
          } else {
            call(a);
          }
        } else {
          // 1/3 Chance to fold
          int n = rand.nextInt(31);
          if(n <= 10) {
            fold();
            System.out.println("AI folded");
          } else if(n <= 20) {
            call(a);
            System.out.println("AI called");
          } else {
            raise(a,b);
            System.out.println("AI raised");
          }
        }
      }
    }

}