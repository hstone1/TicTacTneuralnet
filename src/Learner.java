import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by henry on 4/30/2017.
 */
public class Learner<S> {
  private static final double ALPHA = 0.7;
  private LearnableSystem<S> system;
  private Map<Action<S>, Double> actionValues;

  public Learner(LearnableSystem<S> system) {
    this.system = system;
    actionValues = new HashMap<>();
  }

  public S playBest (S startingState) {
    List<Action<S>> actions = system.getActions(startingState);
    if(!actions.isEmpty()) {
      actions.sort(Comparator.comparingDouble(a -> -getValue(a)));
      System.out.println(actions.get(0).getEnd());
      //playBest(actions.get(0).getEnd());
      return actions.get(0).getEnd();
    }
    return null;
  }

  public void playLearn(S startingState, int itterations){
    for(int i = 0;i < itterations;i++){
      learn(startingState);
      if(i % 100 == 0){
        System.out.println(i);
      }
    }
  }

  private void learn(S startingState) {
    List<Action<S>> actions = system.getActions(startingState);
    if(!actions.isEmpty()) {
      int choice = (int) (Math.random() * actions.size());
      Action<S> todo = actions.get(choice);

      double reward = system.doAction(todo);
      double oldVal = getValue(todo);

      double maxFuture = -100000000;
      List<Action<S>> possibleNext = system.getActions(todo.getEnd());
      for (int i = 0; i < possibleNext.size(); i++) {
        double val = getValue(possibleNext.get(i));
        if (val > maxFuture) {
          maxFuture = val;
        }
      }
      if(possibleNext.isEmpty()){
        maxFuture = 0;
      }

      double newVal = oldVal + ALPHA * (reward - maxFuture - oldVal);
      actionValues.put(todo, newVal);

      learn(todo.getEnd());
    }
  }

  public void print(){
    for(Map.Entry<Action<S>, Double> weight : actionValues.entrySet()){
      System.out.println(weight.getKey().getStart() + "      " + weight.getKey()
          .getEnd() + "     " + weight.getValue());
    }
  }

  double getValue(Action<S> a) {
    if (actionValues.containsKey(a)) {
      return actionValues.get(a);
    } else {
      actionValues.put(a, 0.0);
      return 0;
    }
  }
}
