package cs3500.hw03;

import java.util.List;

import cs3500.hw02.FreecellOperations;

/**
 * This is an interface for the Freecell controller.
 */
public interface IFreecellController<K> {

  /**
   * Plays the game. It asks the provided model to start a game with the provided parameters,
   * and then runs the game. First it transmit game state to the Appendable object
   * exactly as the model provides it. If the game is ongoing, wait for user input
   * from the Readable object.
   *<p></p>
   * A valid user input for a move is a sequence of three inputs (separated by spaces or newlines):
   * <ul>
   *   <li>The source pile as a single word (number begins at 1)</li>
   *   <li>The card index (begins at 1)</li>
   *   <li>The destination pile (begins at 1)</li>
   * </ul>
   * <p></p>
   * The controller parses these inputs and pass the information on to the model
   * to make the move. If the game has been won, transmit the final game state,
   * and a message "Game over." on a new line and return.
   * <p></p>
   *  If at any point, the input is either the letter 'q' or the letter 'Q',
   *  the controller should write the message "Game quit prematurely." If the game parameters are
   *  invalid, transmit a message "Could not start game.".
   *  <p></p>
   *  If an input is unexpected (i.e. something other than 'q' or 'Q' to quit the game;
   *  a letter other than 'C', 'F', 'O' to name a pile; anything that cannot be parsed to
   *  a valid number after the pile letter; anything that is not a number for the card index)
   *  it should ask the user to input it again. If the user entered the source pile correctly
   *  but the card index incorrectly, the controller should ask for only the card index again,
   *  not the source pile, and likewise for the destination pile.
   *  If the move was invalid as signalled by the model, the controller should transmit a message
   *  to the Appendable object "Invalid move. Try again."
   *
   * @param deck          the deck to be used in the game
   * @param model         the freecell game model to be used
   * @param numCascades   the number of open piles to be set for the game
   * @param numOpens      the number of open piles to be set for the game
   * @param shuffle       the boolean that decides if the deck should be shuffled
   * @throws IllegalArgumentException if appendable or readable is null
   */
  void playGame(List<K> deck, FreecellOperations<K> model,
                int numCascades, int numOpens, boolean shuffle);

}
