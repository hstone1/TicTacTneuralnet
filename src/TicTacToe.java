import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by henry on 4/Board.SIZE0/2017.
 */
public class TicTacToe implements LearnableSystem<Board> {
  @Override
  public Action<Board>[] getActions(Board board) {
    Action<Board>[] reachable = new Action[9];
    if (board.gameOver()) {
      return reachable;
    }
    for (int i = 0;i < Board.SIZE * Board.SIZE;i++) {
      if (board.board[i % Board.SIZE][i / Board.SIZE] == 0) {
        Board b = new Board(board);
        b.board[i % Board.SIZE][i / Board.SIZE] = (b.xturn ? 1 : -1);
        b.xturn = !b.xturn;
        reachable[i] = new Action<>(board, b);
      } else {
        reachable[i] = null;
      }
    }
    return reachable;
  }

  @Override
  public double doAction(Action<Board> a) {
    if (a.getEnd().won(a.getStart().xturn ? 1 : -1)) {
      return 10;
    } else if (a.getEnd().won(a.getStart().xturn ? -1 : 1)) {
      return -10;
    } else {
      return 0;
    }
  }
}

