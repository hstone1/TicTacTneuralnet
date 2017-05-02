import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    TicTacToe t = new TicTacToe();
    Learner<Board> learn = new Learner<>(t);
    learn.playLearn(new Board(), 20000);
    //learn.print();
    Scanner s = new Scanner(System.in);
    while (true) {
      Board b = new Board();
      while(true){
        int n1 = s.nextInt();
        int n2 = s.nextInt();
        if (n1 < 0 || n2 < 0) {
          break;
        } else {
          b.board[n1][n2] = b.xturn ? 1 : -1;
          b.xturn = !b.xturn;
        }
        System.out.println(b);
        Board n = learn.playBest(b);
        if(n == null) {
          break;
        }else {
          b = n;
        }
      }
      //learn.playBest(b);
    }
  }
}