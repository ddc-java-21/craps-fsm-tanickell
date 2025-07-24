package edu.cnm.deepdive.model;

import java.util.random.RandomGenerator;

public class Round {


  private final RandomGenerator rng;
  private State state;
  private int point;

  public Round(RandomGenerator rng) {
    this.rng = rng;
    //reset();
  }

  public State getState() {
    return state;
  }



  public State play() {
    reset();
    while (!state.isTerminal()) {
      int die1 = rng.nextInt(1, 7); // rng.nextInt(6) + 1
      int die2 = rng.nextInt(1, 7);
      boolean pointIsNotSet = !state.isPointSet();
      int roll = die1 + die2;
      state = state.next(roll, point);
      if (pointIsNotSet && state.isPointSet()) {
        point = roll;
      }
    }
    return state;
  }

  private void reset() {
    state = State.initial();
    point = 0;
  }

  public enum State {

    COME_OUT(false) {
      @Override
      public State next(int roll, int point) {
        return switch (roll) {
          case 7, 11 -> WIN;
          case 2, 3, 12 -> LOSS;
          default -> POINT;
        };
      }
    },

    POINT(false) {
      @Override
      public State next(int roll, int point) {
        return (roll == point)
            ? WIN
            : (roll == 7)
                ? LOSS
                : POINT;
      }
    },

    WIN(true),

    LOSS(true);

    private final boolean terminal;

    State(boolean terminal) {
      this.terminal = terminal;
    }

    public State next(int roll, int point) {
      throw new IllegalStateException();
    }

    public static State initial() {
      return COME_OUT;
    }

    public boolean isTerminal() {
//      return switch (this) {
//        case WIN, LOSS -> true;
//        default -> false;
//      };
      return terminal;
    }

    public boolean isWin() {
      return this == WIN;
    }

    public boolean isLoss() {
      return this == LOSS;
    }

    public boolean isPointSet() {
      return this == POINT;
    }

  }

}
