import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.hw02.FreecellModel;
import cs3500.hw02.PileType;
import cs3500.hw03.FreecellController;
import cs3500.hw02.Card;
import cs3500.hw04.MultiCardMoveModel;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for freecellController.
 */
public class FreecellControllerTest {

  /**
   * Helper testing method that creates a string for the initial game state.
   *
   * @return the string of the initial game start with 12 cascade piles and 4 open
   */
  public String initialGameState() {
    FreecellModel game = new FreecellModel();
    game.startGame(game.getDeck(), 12,4, false);
    return game.getGameState() + "\n";
  }



  // test null deck exception
  @Test(expected = IllegalStateException.class)
  public void nullRd() {
    FreecellModel game = new FreecellModel();
    FreecellController controller = new FreecellController(null, new StringBuilder());
    controller.playGame(null, game, 12, 4, false);
  }

  // test null deck exception
  @Test(expected = IllegalStateException.class)
  public void nullAp() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader(" ");
    FreecellController controller = new FreecellController(r,null);
    controller.playGame(null, game, 12, 4, false);
  }

  // test null deck exception
  @Test(expected = IllegalArgumentException.class)
  public void nullDeck() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 5 O1 q");
    FreecellController controller = new FreecellController(r, new StringBuilder());
    controller.playGame(null, game, 12, 4, false);
  }

  // test null model exception
  @Test(expected = IllegalArgumentException.class)
  public void nullModel() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 5 O1 q");
    FreecellController controller = new FreecellController(r, new StringBuilder());
    controller.playGame(game.getDeck(), null, 12, 4, false);
  }

  // test invalid parameter: empty deck. Game can not start
  @Test
  public void testEmptyDeck() {
    FreecellModel game = new FreecellModel();
    List<Card> empty = new ArrayList<>();
    StringReader r = new StringReader("C1 5 O1 q");
    FreecellController controller = new FreecellController(r, new StringBuilder());

    controller.playGame(empty, game, 12, 4, false);
    assertEquals("Could not start game.", controller.ap.toString());
  }

  // test invalid parameter: wrong cascade number Game can not start
  @Test
  public void testWrongCasacdeNum1() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 5 O1 q");
    FreecellController controller = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 1, 4, false);
    assertEquals("Could not start game.", controller.ap.toString());
  }

  // test invalid parameter: wrong cascade number Game can not start
  @Test
  public void testWrongCasacdeNum2() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader(" ");
    FreecellController controller = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, -1, 4, false);
    assertEquals("Could not start game.", controller.ap.toString());
  }

  // test invalid parameter: wrong open number Game can not start
  @Test
  public void testWrongOpenNum1() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader(" ");
    FreecellController controller = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 4, 0, false);
    assertEquals("Could not start game.", controller.ap.toString());
  }

  // test invalid parameter: wrong open number Game can not start
  @Test
  public void testWrongOpenNum2() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader(" ");
    FreecellController controller = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 4, -10, false);
    assertEquals("Could not start game.", controller.ap.toString());
  }

  // test invalid parameter: empty deck
  @Test
  public void emptyDeck() {
    FreecellModel game = new FreecellModel();
    List<Card> empty = new ArrayList<>();
    StringReader r = new StringReader(" ");
    FreecellController controller = new FreecellController(r, new StringBuilder());

    controller.playGame(empty, game, 52, 4, false);
    assertEquals("Could not start game.", controller.ap.toString());
  }

  // test invalid parameter: invalid deck
  @Test
  public void invalidDeck() {
    FreecellModel game = new FreecellModel();
    List<Card> invalid = game.getDeck();
    invalid.add(new Card(5,"♥"));
    StringReader r = new StringReader(" ");
    FreecellController controller = new FreecellController(r, new StringBuilder());

    controller.playGame(invalid, game, 52, 4, false);
    assertEquals("Could not start game.", controller.ap.toString());
  }

  // check if this the controller restarts the model
  @Test
  public void controllerRestartModel() {
    FreecellModel game = new FreecellModel();
    List<Card> invalid = game.getDeck();
    StringReader r = new StringReader(" ");
    FreecellController controller = new FreecellController(r, new StringBuilder());

    game.startGame(game.getDeck(), 12, 4, false);
    game.move(PileType.CASCADE, 0, 4, PileType.OPEN, 0);
    String firstMove = game.getGameState();
    assertNotEquals(this.initialGameState(), firstMove);

    controller.playGame(invalid, game, 12, 4, false);
    assertEquals(this.initialGameState(), controller.ap.toString());
  }

  // check if this deck is the deck passed in the controller
  // pass in a shuffled deck and have shuffle equals false and check to see if it is different
  // than getDeck's deck in the by  printing out the initial gameState
  @Test
  public void deckPassedIntoController() {
    FreecellModel game = new FreecellModel();
    List<Card> deck1 = game.getDeck();
    Collections.shuffle(deck1);
    StringReader r = new StringReader(" ");
    FreecellController controller = new FreecellController(r, new StringBuilder());

    controller.playGame(deck1, game, 12, 4, false);
    assertNotEquals(this.initialGameState(), controller.ap.toString());
  }

  // test when shuffle is true passed to controller, the deck shuffles
  @Test
  public void shuffleTrue() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader(" ");
    FreecellController controller = new FreecellController(r, new StringBuilder());
    game.startGame(game.getDeck(), 4, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n"
            + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠", game.getGameState());

    controller.playGame(game.getDeck(), game, 4, 4, true);
    assertNotEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n"
            + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠", game.getGameState());
  }

  // test when shuffle is true passed to controller, the deck shuffles
  @Test
  public void shuffleFalse() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader(" ");
    FreecellController controller = new FreecellController(r, new StringBuilder());
    game.startGame(game.getDeck(), 4, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n"
            + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠", game.getGameState());

    controller.playGame(game.getDeck(), game, 4, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n"
            + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠", game.getGameState());
  }

  // test using q to quit at the end
  @Test
  public void quitUsingqAttheEnd() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 5 O1 q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals( this.initialGameState() + game.getGameState()
                    + "\nGame quit prematurely.", controller.ap.toString());
  }

  // test using Q to quit at the end
  @Test
  public void quitUsingQAtTheEnd() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 5 O1 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + game.getGameState()
            + "\nGame quit prematurely.", controller.ap.toString());
  }

  // test using q to quit mid way
  @Test
  public void quitUsingQMidWay() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 5 O1 q C3 5 O2");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + game.getGameState() + "\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test using just q
  @Test
  public void justQ() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Game quit prematurely.",
            controller.ap.toString());
  }

  // test using just q
  @Test
  public void justq() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Game quit prematurely.",
            controller.ap.toString());
  }

  // test a bad input, starting with a wrong letter
  @Test
  public void invalidIputUnexpectedStringFirst() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("X Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input."
            + "\nGame quit prematurely.", controller.ap.toString());
  }

  // test a bad input, starting with a wrong letter
  @Test
  public void invalidIputUnexpectedStringFirst1() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("X1 10 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    game.startGame(game.getDeck(), 12,4, false);
    String initialGameState = game.getGameState();

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(initialGameState + "\n" + "Please enter a valid input."
            + "\nPlease enter a valid input." + "\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test a bad input, unexpected string
  @Test
  public void invalidIputUnexpectedStringAfter() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1a 5 O1 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    game.startGame(game.getDeck(), 12,4, false);
    String initialGameState = game.getGameState();

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(initialGameState + "\n" + "Please enter a valid input."
                    + "\nPlease enter a valid input.\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test a bad move, pile number does not exist
  @Test
  public void invalidMoveWrongPileNum() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C50 5 O1 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState()
                    + "Invalid move. Try again.\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test a bad move, pile number does not exist
  @Test
  public void invalidMoveWrongPileNumNegative() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C-10 5 O1 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState()
                    + "Invalid move. Try again.\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test a bad input, starting with a number
  @Test
  public void invalidIputUnexpectedNumber() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("5 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input."
            + "\nGame quit prematurely.", controller.ap.toString());
  }

  // test a bad input, starting with a negative number
  @Test
  public void invalidIputUnexpectedNumberNegative() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("-5 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input."
            + "\nGame quit prematurely.", controller.ap.toString());
  }

  // test a bad input, wrong card index
  @Test
  public void invalidInputWrongIndex() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 100 O1 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Invalid move. Try again."
            + "\nGame quit prematurely.", controller.ap.toString());
  }

  // test a bad input, wrong card index
  @Test
  public void invalidInputWrongIndexNegative() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 -1 O1 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Invalid move. Try again."
            + "\nGame quit prematurely.", controller.ap.toString());
  }

  // test a bad input, wrong card index
  @Test
  public void invalidInputWrongIndex0() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 0 O1 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Invalid move. Try again."
            + "\nGame quit prematurely.", controller.ap.toString());
  }

  // supposed to enter a number but enters letters
  @Test
  public void invalidIputUnexpectedString2() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 Bac Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input."
            + "\nGame quit prematurely.", controller.ap.toString());
  }

  // supposed to enter a number but enters letters
  @Test
  public void invalidIputUnexpectedString3() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 Bac 10 r Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input."
            + "\nPlease enter a valid input.\nGame quit prematurely.", controller.ap.toString());
  }

  // invalid dest pile letter
  @Test
  public void invalidIputUnexpectedString4() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 2 E3 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState()
                    + "Please enter a valid input.\nGame quit prematurely.",
            controller.ap.toString());
  }


  // invalid dest pile number
  @Test
  public void invalidIputUnexpectedNumberDest1() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 2 O-13 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState()
                    + "Invalid move. Try again.\nGame quit prematurely.",
            controller.ap.toString());
  }

  // invalid dest pile number
  @Test
  public void invalidIputUnexpectedNumberDest2() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 2 O13 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState()
                    + "Invalid move. Try again.\nGame quit prematurely.",
            controller.ap.toString());
  }

  // invalid dest string
  @Test
  public void invalidIputUnexpectedStringDest() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 2 O1acd Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState()
                    + "Please enter a valid input.\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test a valid double move, see if it prints out a state every valid move
  @Test
  public void validIputdoubleMove() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 5 O1 C2 5 O2 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    game.startGame(game.getDeck(), 12, 4, false);
    game.move(PileType.CASCADE, 0, 4, PileType.OPEN, 0);
    String temp = game.getGameState();

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + temp + "\n" + game.getGameState()
                    + "\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test a valid move
  @Test
  public void validCommand1() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 5 O1 wes Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + game.getGameState()
                    + "\nPlease enter a valid input.\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test a wrong input
  @Test
  public void invalidinput() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("c2 x O2 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input.\n"
                    + "Please enter a valid input.\n" + "Game quit prematurely.",
            controller.ap.toString());
  }

  // test a wrong index, but continue to as for index not start over
  @Test
  public void validCommandWrongIndex() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("c2 f 5 O2 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input.\n"
                    + game.getGameState() + "\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test a destination, but continue to as for destination not start over
  @Test
  public void validCommandWrongDestPile() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 5 10 O1 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input.\n"
                    + game.getGameState() + "\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test a destination, but continue to as for destination not start over
  @Test
  public void validCommandWrongDestPile2() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 5 O10a O1 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input.\n"
                    + game.getGameState() + "\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test a destination, but continue to as for destination not start over
  @Test
  public void validCommandWrongDestPile3() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 5 Z10 O1 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input.\n"
                    + game.getGameState() + "\nGame quit prematurely.",
            controller.ap.toString());
  }

  // test a valid command has new line and spaxes
  @Test
  public void validCommandWithNewLine() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("C1 \n \n     5 \n O1 Q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + game.getGameState()
                    + "\nGame quit prematurely.", controller.ap.toString());
  }

  // test a valid command with all lower cases
  @Test
  public void validCommandWithLowerCase() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("c1 1 o1 c2 1 f2  q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    game.startGame(game.getDeck(), 52, 4, false);
    String initial = game.getGameState();
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    String firstMove = game.getGameState();

    controller.playGame(game.getDeck(), game, 52, 4, false);
    assertEquals(initial + "\n" +  firstMove + "\n" + game.getGameState()
            + "\nGame quit prematurely.", controller.ap.toString());
  }

  // test using the controller finishes the game and prints game over
  // last input is the last move
  @Test
  public void finishGame() {
    FreecellModel game = new FreecellModel();
    StringBuilder club = new StringBuilder();
    for (int i = 1; i <= 52; i += 4) {
      club = club.append(" C" + Integer.toString(i) + " 1 F1");
    }
    StringBuilder square = new StringBuilder();
    for (int i = 2; i <= 52; i += 4) {
      square = square.append(" C" + Integer.toString(i) + " 1 F2");
    }
    StringBuilder heart = new StringBuilder();
    for (int i = 3; i <= 52; i += 4) {
      heart = heart.append(" C" + Integer.toString(i) + " 1 F3");
    }
    StringBuilder spade = new StringBuilder();
    for (int i = 4; i <= 52; i += 4) {
      spade = spade.append(" C" + Integer.toString(i) + " 1 F4");
    }
    StringReader r = new StringReader(club.toString() + square.toString() + heart.toString()
            + spade.toString());
    FreecellController controller = new FreecellController(r, new StringBuilder());
    controller.playGame(game.getDeck(), game, 52, 4, false);

    assertEquals(true, game.isGameOver());
    assertEquals("Game over.", controller.ap.toString().substring(
            controller.ap.toString().length() - 10));
  }

  // test using the controller finishes the game and prints game over
  // there is more inputs after the last wining move, check if the game over is the last thing
  // and if the game is over it breaks out the loop
  @Test
  public void finishGameMoreCommands() {
    FreecellModel game = new FreecellModel();
    StringBuilder club = new StringBuilder();
    for (int i = 1; i <= 52; i += 4) {
      club = club.append(" C" + Integer.toString(i) + " 1 F1");
    }
    StringBuilder square = new StringBuilder();
    for (int i = 2; i <= 52; i += 4) {
      square = square.append(" C" + Integer.toString(i) + " 1 F2");
    }
    StringBuilder heart = new StringBuilder();
    for (int i = 3; i <= 52; i += 4) {
      heart = heart.append(" C" + Integer.toString(i) + " 1 F3");
    }
    StringBuilder spade = new StringBuilder();
    for (int i = 4; i <= 52; i += 4) {
      spade = spade.append(" C" + Integer.toString(i) + " 1 F4");
    }
    StringReader r = new StringReader(club.toString() + square.toString() + heart.toString()
            + spade.toString() + " C5 2 F1 more jiberish");
    FreecellController controller = new FreecellController(r, new StringBuilder());
    controller.playGame(game.getDeck(), game, 52, 4, false);

    assertEquals(true, game.isGameOver());
    assertEquals("Game over.", controller.ap.toString().substring(
            controller.ap.toString().length() - 10));
  }

  // extra test cases for different valid moves
  // test a valid command with all lower cases
  @Test
  public void invalidCommandO() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("c1 1 ow q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input.\n"
            + "Game quit prematurely.", controller.ap.toString());
  }

  // extra test cases for different valid moves
  // test a valid command with all lower cases
  @Test
  public void invalidCommandF() {
    FreecellModel game = new FreecellModel();
    StringReader r = new StringReader("c1 1 f2w q");
    FreecellController controller
            = new FreecellController(r, new StringBuilder());

    controller.playGame(game.getDeck(), game, 12, 4, false);
    assertEquals(this.initialGameState() + "Please enter a valid input.\n"
            + "Game quit prematurely.", controller.ap.toString());
  }

  // playing the game with multimove Model
  @Test
  public void finishGameWithMultiMove() {
    MultiCardMoveModel game = new MultiCardMoveModel();
    StringBuilder club = new StringBuilder();
    for (int i = 1; i <= 52; i += 4) {
      club = club.append(" C" + Integer.toString(i) + " 1 F1");
    }
    StringBuilder square = new StringBuilder();
    for (int i = 2; i <= 52; i += 4) {
      square = square.append(" C" + Integer.toString(i) + " 1 F2");
    }
    StringBuilder heart = new StringBuilder();
    for (int i = 3; i <= 52; i += 4) {
      heart = heart.append(" C" + Integer.toString(i) + " 1 F3");
    }
    StringBuilder spade = new StringBuilder();
    for (int i = 4; i <= 52; i += 4) {
      spade = spade.append(" C" + Integer.toString(i) + " 1 F4");
    }
    StringReader r = new StringReader(club.toString() + square.toString() + heart.toString()
            + spade.toString());
    FreecellController controller = new FreecellController(r, new StringBuilder());
    controller.playGame(game.getDeck(), game, 52, 4, false);

    assertEquals(true, game.isGameOver());
    assertEquals("Game over.", controller.ap.toString().substring(
            controller.ap.toString().length() - 10));
  }

}
