import java.util.List;

import cs3500.hw02.PileType;

/**
 * Do not modify this file. This file should compile correctly with your code!
 */
public class Hw02TypeChecks {

  public static void main(String[] args) {
    helper(new cs3500.hw02.FreecellModel());
  }

  private static <T> void helper(cs3500.hw02.FreecellOperations<T> model) {
    List<T> deck = model.getDeck();
    model.startGame(deck, 8, 4, false);
    model.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 2);
  }
}
