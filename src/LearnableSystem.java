import java.util.List;

/**
 * Created by henry on 4/30/2017.
 */
public interface LearnableSystem<S> {
  List<Action<S>> getActions(S s);
  double doAction(Action<S> a);
}
