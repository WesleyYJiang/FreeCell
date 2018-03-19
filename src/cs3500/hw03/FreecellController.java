package cs3500.hw03;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;

import cs3500.hw02.PileType;
import cs3500.hw02.FreecellOperations;

/**
 * This is the controller of the game.
 */
public class FreecellController implements IFreecellController {

  public Readable rd;
  public Appendable ap;
  private int sourceNum;
  private int destNum;
  private PileType sourceType;
  private PileType destType;
  private boolean sourceHasBeenSet = false;
  private boolean indexNeedsToBeSet = false;
  private int cardIndex;
  private boolean gameStarted;

  /**
   * Constructs an freecell controller.
   *
   * @param rd the readable, user's input
   * @param ap the appendable, the string to be appened
   * @throws IllegalStateException if either rd or ap is null
   */
  public FreecellController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalStateException();
    }
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * Check if the deck or the model being passed is null.
   *
   * @param deck      the list of cards, the deck being passed into the controller
   * @param model     the game model being passed into the controller
   * @throws IllegalArgumentException if the either of them is null
   */
  private void checkParameters(List deck, FreecellOperations model) {
    if (model == null || deck == null) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Start the game with the parameters passed from the controller. Catch IllegalArgumentExceptions
   * that could be thrown from startGame method in model class and IOExceptions that could be thrown
   * from appending appendable.
   *<p></p>
   * If the startGame throws exception, catch it and tell user could not start game and set the
   * gameStarted boolean to false, otherwise append the initial game state and set game
   * started to be true
   *
   * @param deck          the list of cards, the deck being passed into the controller
   * @param model         the game model being passed into the controller
   * @param numCascades   the number of cascade piles passed in
   * @param numOpens      the number of open piles passed in
   * @param shuffle       the boolean that decides if the deck should be shuffled
   */
  private void startTheGame(List deck, FreecellOperations model, int numCascades, int numOpens,
                            boolean shuffle) {
    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
      this.gameStarted = true;
      this.ap.append(model.getGameState() + "\n");
    } catch (IllegalArgumentException | IOException e) {
      this.appendCantStartGame();
    }
  }

  @Override
  public void playGame(List deck, FreecellOperations model, int numCascades, int numOpens,
                       boolean shuffle) {
    this.checkParameters(deck, model);
    this.startTheGame(deck, model, numCascades, numOpens, shuffle);
    if (gameStarted) {
      Scanner scan = new Scanner(this.rd);
      this.processScanner(scan, model);
      this.appendGameOver(model);
    }
  }

  /**
   * Process the content in the scanner, each element is separated by space or next line.
   * Checks each token and match them with the different cases.
   *
   * @param scan     the scanner from the readable
   * @param model    the game model being passed into the controller
   */
  private void processScanner(Scanner scan, FreecellOperations model) {
    while (scan.hasNext() && !model.isGameOver()) {
      String temp = scan.next();
      if (temp.equalsIgnoreCase("Q")) {
        this.appendQuit();
        break;
      }
      if (this.indexNeedsToBeSet) {
        this.storeCardIndex(temp);
      } else {
        this.storePile(temp, model);
      }
    }
  }

  /**
   * Process a command; if the game the souce pile has not been set, store the source number and
   * type and indicate that source has been set and index number needs to be set next. If the
   * source has been set and index has been set too, store the destination number and type,
   * indicate the source type needs to be set next, and execute the full command move. Correct the
   * input by subtracting 1 since index starts at 0 instead of 1 in the model.
   *
   * @param model     the game model being passed into the controller
   * {@link PileType})
   */
  private void executeMove(FreecellOperations model) {
    try {
      model.move(this.sourceType, this.sourceNum - 1, this.cardIndex - 1,
              this.destType, this.destNum - 1);
      appendGameState(model);
    } catch (IllegalArgumentException e) {
      this.appendInvalidMove();
    }
  }

  /**
   * Parse the 3 different piles, display invalid input message if it is not any of
   * the 3 pile letter.
   *
   * @param input     the next token from the scanner
   * @param model     the game model being passed into the controller
   * {@link PileType})
   */
  private void storePile(String input, FreecellOperations model) {
    switch (Character.toUpperCase(input.charAt(0))) {
      case 'C':
        this.parseNumber(input, PileType.CASCADE, model);
        break;
      case 'F':
        this.parseNumber(input, PileType.FOUNDATION, model);
        break;
      case 'O':
        this.parseNumber(input, PileType.OPEN, model);
        break;
      default:
        this.appendInvalidInput();
    }
  }

  /**
   * Parse the number after the pile letter, if it is not a number, display invalid input message.
   *
   * @param input     the next token from the scanner
   * @param model     the game model being passed into the controller
   * {@link PileType})
   */
  private void parseNumber(String input, PileType type, FreecellOperations model) {
    try {
      processCommand(Integer.parseInt(input.substring(1)), type, model);
    } catch (NumberFormatException e) {
      this.appendInvalidInput();
    }
  }

  /**
   * Process a command; if the game the souce pile has not been set, store the source number and
   * type and indicate that source has been set and index number needs to be set next. If the
   * source has been set and index has been set too, store the destination number and type,
   * indicate the source type needs to be set next, and execute the full command move.
   *
   * @param pileNum   the number of the chosen pile
   * @param model     the game model being passed into the controller
   * {@link PileType})
   */
  private void processCommand(int pileNum, PileType p, FreecellOperations model) {
    if (this.sourceHasBeenSet && !this.indexNeedsToBeSet) {
      this.destNum = pileNum;
      this.destType = p;
      this.sourceHasBeenSet = false;
      this.executeMove(model);
    } else {
      this.sourceNum = pileNum;
      this.sourceType = p;
      this.sourceHasBeenSet = true;
      this.indexNeedsToBeSet = true;
    }
  }

  /**
   * Stores the card index needed and indicate that the index has already been set after. If the
   * input is not a number, user will be informed to enter a valid input.
   *
   * @param input  the next token from the scanner
   */
  private void storeCardIndex(String input) {
    try {
      this.cardIndex = Integer.parseInt(input);
      this.indexNeedsToBeSet = false;
    } catch (NumberFormatException e) {
      this.appendInvalidInput();
    }
  }

  /**
   * A helper method to append cant start game message to appendable to avoid nested try catches.
   *
   */
  private void appendCantStartGame() {
    try {
      this.gameStarted = false;
      this.ap.append("Could not start game.");
    } catch (IOException e) {
      // caught exception, no action needed.
    }
  }

  /**
   * A helper method to append invalid move message to appendable to avoid nested try catches.
   *
   */
  private void appendInvalidMove() {
    try {
      this.ap.append("Invalid move. Try again.\n");
    } catch (IOException e) {
      // caught exception, no action needed.
    }
  }

  /**
   * A helper method to append the game state to appendable to avoid nested try catches.
   *
   * @param model the game model
   */
  private void appendGameState(FreecellOperations model) {
    try {
      this.ap.append(model.getGameState() + "\n");
    } catch (IOException e) {
      // caught exception, no action needed.
    }
  }

  /**
   * A helper method to append invalid input message to appendable to avoid nested try catches.
   *
   */
  private void appendInvalidInput() {
    try {
      this.ap.append("Please enter a valid input.\n");
    } catch (IOException a) {
      // caught exception, no action needed.
    }
  }

  /**
   * A helper method to append game quits to appendable to avoid nested try catches.
   *
   */
  private void appendQuit() {
    try {
      this.ap.append("Game quit prematurely.");
    } catch (IOException a) {
      // caught exception, no action needed.
    }
  }

  /**
   * A helper method to append game over message to appendable.
   *
   */
  private void appendGameOver(FreecellOperations model) {
    if (model.isGameOver()) {
      try {
        this.ap.append("Game over.");
      } catch (IOException e) {
        // caught exception, no action needed.
      }
    }
  }
}