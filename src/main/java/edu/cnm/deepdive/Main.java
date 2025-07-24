package edu.cnm.deepdive;

import edu.cnm.deepdive.model.Round;
import edu.cnm.deepdive.model.Round.State;
import java.util.random.RandomGenerator;

public class Main {

  private static final int MAX_ROUNDS = 1_000_000_000;
  private static final int TALLY_INTERVAL = 1_000_000;
  private static final double PERCENTAGE_SCALE = 100.0;
  private static final String TALLY_FORMAT = "Rounds = %1$,d; wins = %2$,d; winning percentage = %3$.2f%%%n";

  public static void main(String[] args) {
    Round round = new Round(RandomGenerator.getDefault());
    int wins = 0;
    for (int rounds = 0; rounds < MAX_ROUNDS; rounds++) {
      if (rounds > 0 && rounds % TALLY_INTERVAL == 0) {
        System.out.printf(TALLY_FORMAT,
            rounds, wins, PERCENTAGE_SCALE * wins / rounds);
      }
      State state = round.play();
      if (state.isWin()) {
        wins++;
      }
    }
    System.out.printf(TALLY_FORMAT, MAX_ROUNDS, wins, PERCENTAGE_SCALE * wins / MAX_ROUNDS);
  }

}
