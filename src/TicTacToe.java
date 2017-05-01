import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by henry on 4/Board.SIZE0/2017.
 */
public class TicTacToe implements LearnableSystem<Board> {
  @Override
  public List<Action<Board>> getActions(Board board) {
    List<Action<Board>> reachable = new ArrayList<>(9);
    if (board.gameOver()) {
      return reachable;
    }
    for (int i = 0;i < Board.SIZE * Board.SIZE;i++) {
      if (board.board[i % Board.SIZE][i / Board.SIZE] == 0) {
        Board b = new Board(board);
        b.board[i % Board.SIZE][i / Board.SIZE] = (b.xturn ? 1 : -1);
        b.xturn = !b.xturn;
        reachable.add(new Action<>(board, b));
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

