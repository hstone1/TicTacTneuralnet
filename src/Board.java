/**
 * Created by henry on 4/SIZE0/2017.
 */
class Board {
  public static final int SIZE = 3;

  boolean xturn;
  int[][] board;
  public Board(){
    board = new int[SIZE][SIZE];
    for(int i = 0;i < SIZE * SIZE;i++){
      board[i/SIZE][i%SIZE] = 0;
    }
    xturn = true;
  }

  public Board(Board b) {
    board = new int[SIZE][SIZE];
    for(int i = 0;i < SIZE*SIZE;i++){
      board[i/SIZE][i%SIZE] = b.board[i/SIZE][i%SIZE];
    }
    this.xturn = b.xturn;
  }

  public boolean gameOver(){
    for (int i = 0;i < SIZE * SIZE;i++) {
      if (board[i/SIZE][i%SIZE] == 0) {
        break;
      }
      if (i == SIZE * SIZE - 1) {
        return true;
      }
    }
    if(won(1) || won(-1)) {
      return true;
    }
    return false;
  }

  public boolean won(int player){
    for(int i = 0;i < SIZE;i++){
      for(int j = 0;j < SIZE;j++){
        if(board[i][j] != player){
          break;
        }
        if (j == SIZE - 1) {
          return true;
        }
      }
      for(int j = 0;j < SIZE;j++){
        if(board[j][i] != player){
          break;
        }
        if (j == SIZE - 1) {
          return true;
        }
      }
    }
    for(int j = 0;j < SIZE;j++){
      if(board[j][j] != player){
        break;
      }
      if (j == SIZE - 1) {
        return true;
      }
    }
    for(int j = 0;j < SIZE;j++){
      if(board[j][SIZE-j-1] != player){
        break;
      }
      if (j == SIZE - 1) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean equals(Object obj){
    if (obj instanceof Board) {
      Board b = (Board) obj;
      for(int i = 0;i < SIZE*SIZE;i++){
        if(board[i/SIZE][i%SIZE] != b.board[i/SIZE][i%SIZE]){
          return false;
        }
      }
      return xturn == b.xturn;
    }
    return false;
  }

  @Override
  public int hashCode(){
    int hash = 0;
    for(int i = 0;i < SIZE*SIZE;i++){
      hash |= (board[i%SIZE][i/SIZE] + 1) << (2*i + 1);
    }
    return hash | (xturn ? 0 : 1);
  }

  @Override
  public String toString(){
    String s = "";
    for(int y = 0;y < SIZE;y++){
      for(int x = 0;x < SIZE;x++){
        s += "\t" + board[x][y];
      }
      s += '\n';
    }
    return s + (xturn ? "x" : "o") + '\n';
  }
}
