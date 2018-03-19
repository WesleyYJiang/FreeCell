package cs3500.hw04;


import cs3500.hw02.FreecellModel;
import cs3500.hw02.FreecellOperations;

public class FreecellModelCreator {

  /**
   * The types of the Freecell game. <br>
   *<p></p>
   * SINGLEMOVE: this is to move just one card from pile to pile <br>
   * MULTIMOVE: This is to move multiple cards as well as a single card<br>
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE;
  }

  /**
   * Constructs a new game mode based on the game type, single move returns FreecellModel,
   * and multi-move returns multiMoveModel.
   *
   * @param type the game type of the model
   * @return the game model based on the game type given
   */
  public static FreecellOperations create(GameType type) {
    if (type == GameType.SINGLEMOVE) {
      return new FreecellModel();
    }
    else {
      return new MultiCardMoveModel();
    }
  }
}

