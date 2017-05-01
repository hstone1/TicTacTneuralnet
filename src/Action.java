import java.util.Arrays;
import java.util.Objects;

/**
 * Created by henry on 4/30/2017.
 */
public class Action<S> {
  private S start;
  private S end;

  public Action(S start, S end) {
    this.start = start;
    this.end = end;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Action){
      return start.equals(((Action) o).start) && end.equals(((Action) o).end);
    }
    return false;
  }

  @Override
  public int hashCode(){
    return Objects.hash(start, end);
  }

  public S getStart(){
    return start;
  }

  public S getEnd(){
    return end;
  }
}
