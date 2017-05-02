import java.util.*;

/**
 * Created by henry on 4/30/2017.
 */
public class Learner<S extends State> {
  private static final double ALPHA = 0.7;
  private LearnableSystem<S> system;
  private Network network = new Network(9, 100, 9);

  public Learner(LearnableSystem<S> system) {
    this.system = system;
  }

  public S playBest (S startingState) {
    Action<S>[] actions = system.getActions(startingState);
    float[] output = network.activate(startingState.state());
    float max = -100000000;
    int index = -1;
    for (int i = 0; i < output.length; i++) {
      if (actions[i] != null && output[i] > max) {
        max = output[i];
        index = i;
      }
    }

    if(index != -1) {
      System.out.println(actions[index].getEnd());
      //playBest(actions.get(0).getEnd());
      return actions[index].getEnd();
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
    Action<S>[] actions = system.getActions(startingState);

    boolean empty = true;
    for (int i = 0; i < actions.length; i++) {
      if (actions[i] != null) {
        empty = false;
      }
    }

    if (!empty) {
      Action<S> todo = null;
      int rand = 0;
      while (todo == null) {
        rand = (int)(Math.random() * actions.length);
        todo = actions[rand];
      }

      double reward = system.doAction(todo);

      float[] output = network.activate(todo.getEnd().state());
      Action<S>[] arr = system.getActions(todo.getEnd());
      float max = -(1 << 30);
      for(int i = 0;i < 9;i++){
        if (arr[i] != null) {
          if(output[i] > max){
            max = output[i];
          }
        }
      }
      if (max == -(1 << 30)) {
        max = 0;
      }

      double newVal = reward - max;
      float[] target = network.activate(todo.getStart().state());
      for (int i = 0; i < target.length; i++) {
        if (i == rand) {
          target[i] = (float) newVal;
        } else {
          target[i] = (float) newVal;
        }
      }
      network.train(todo.getStart().state(), target);

      learn(todo.getEnd());
    }
  }

//  public void print(){
//    for(Map.Entry<Action<S>, Double> weight : actionValues.entrySet()){
//      System.out.println(weight.getKey().getStart() + "      " + weight.getKey()
//          .getEnd() + "     " + weight.getValue());
//    }
//  }
//  double getValue(Action<S> a) {
//    if (actionValues.containsKey(a)) {
//      return actionValues.get(a);
//    } else {
//      actionValues.put(a, 0.0);
//      return 0;
//    }
//  }

  private <T> List<T> removeNull(T[] t){
    List<T> l = new LinkedList<>();
    for (int i = 0;i < t.length;i++) {
      if (t[i] != null) {
        l.add(t[i]);
      }
    }
    return l;
  }
}
