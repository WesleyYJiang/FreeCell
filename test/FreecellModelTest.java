import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cs3500.hw02.FreecellModel;
import cs3500.hw02.Card;
import cs3500.hw02.PileType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Tests the methods if FreecellModel, private methods are tested through public methods.
 */
public class FreecellModelTest {
  FreecellModel game = new FreecellModel();
  List<Card> valid = this.game.getDeck();
  List<Card> invalid1 = new ArrayList<>(Arrays.asList(new Card(1, "♥")));
  List<Card> invalid2 = new ArrayList<>();

  // test get deck, print out the deck and compare by strings instead of constructing
  // a whole deck by hand
  @Test
  public void testGetDeck() {
    FreecellModel game = new FreecellModel();
    StringBuilder deckPrint = new StringBuilder();
    List<Card> deck1 = this.game.getDeck();

    for (Card c: deck1) {
      deckPrint.append(c.toString() + ", ");
    }
    assertEquals("A♣, A♦, A♥, A♠, 2♣, 2♦, 2♥, 2♠, "
            + "3♣, 3♦, 3♥, 3♠, 4♣, 4♦, 4♥, 4♠, 5♣, 5♦, 5♥, 5♠, "
            + "6♣, 6♦, 6♥, 6♠, 7♣, 7♦, 7♥, 7♠, 8♣, 8♦, 8♥, 8♠, "
            + "9♣, 9♦, 9♥, 9♠, 10♣, 10♦, 10♥, 10♠, J♣, J♦, J♥, J♠, "
            + "Q♣, Q♦, Q♥, Q♠, K♣, K♦, K♥, K♠, ", deckPrint.toString());
  }

  // test private method validDeck() through public method start game and game state
  // a deck is valid when the start game does not throw exception and the deck can also be
  // checked through getGameState()
  // a deck is invalid when exception is thrown for statGame()
  @Test
  public void testValidDeckTrue() {
    this.game.startGame(this.valid,4,1,false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n"
            + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠", this.game.getGameState());
  }

  // deck is not long enough
  @Test(expected = IllegalArgumentException.class)
  public void deckNotLongEnough() {
    this.game.startGame(invalid1, 8, 4, true);
  }

  // deck has repetitive cards
  @Test(expected = IllegalArgumentException.class)
  public void deckRepetitive() {
    this.invalid2 = this.valid;
    this.invalid2.add(new Card(1, "♥"));
    this.game.startGame(invalid2, 8, -1, true);
  }

  // test private method initialize() through public method start game and game state
  // Initialize the piles by calling startGame, and check if the piles updated correctly
  // by calling startGame multiple times with different parameters
  @Test
  public void testInitialize() {
    this.game.startGame(this.valid,4,1,false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\n"
            + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠", this.game.getGameState());
    this.game.startGame(this.valid,6,3,false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\n"
            + "C1: A♣, 2♥, 4♣, 5♥, 7♣, 8♥, 10♣, J♥, K♣\n"
            + "C2: A♦, 2♠, 4♦, 5♠, 7♦, 8♠, 10♦, J♠, K♦\n"
            + "C3: A♥, 3♣, 4♥, 6♣, 7♥, 9♣, 10♥, Q♣, K♥\n"
            + "C4: A♠, 3♦, 4♠, 6♦, 7♠, 9♦, 10♠, Q♦, K♠\n"
            + "C5: 2♣, 3♥, 5♣, 6♥, 8♣, 9♥, J♣, Q♥\n"
            + "C6: 2♦, 3♠, 5♦, 6♠, 8♦, 9♠, J♦, Q♠", this.game.getGameState());
  }

  // test private method deal() through public method start game and game state
  // check by calling startGame and gameState to see if the cards are dealt correctly
  @Test
  public void testDeal() {
    this.game.startGame(this.valid, 4, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n"
            + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠", this.game.getGameState());
    this.game.startGame(this.valid, 7, 8, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\nO5:\nO6:\nO7:\nO8:\n"
            + "C1: A♣, 2♠, 4♥, 6♦, 8♣, 9♠, J♥, K♦\n"
            + "C2: A♦, 3♣, 4♠, 6♥, 8♦, 10♣, J♠, K♥\n"
            + "C3: A♥, 3♦, 5♣, 6♠, 8♥, 10♦, Q♣, K♠\n"
            + "C4: A♠, 3♥, 5♦, 7♣, 8♠, 10♥, Q♦\n"
            + "C5: 2♣, 3♠, 5♥, 7♦, 9♣, 10♠, Q♥\n"
            + "C6: 2♦, 4♣, 5♠, 7♥, 9♦, J♣, Q♠\n"
            + "C7: 2♥, 4♦, 6♣, 7♠, 9♥, J♦, K♣", this.game.getGameState());
  }

  // startGame has been tested every time while testing the private methods
  // however here we can test it again with a shuffled deck and an un shuffled deck they should be
  // different
  @Test
  public void testStartGameShuffledAndNotShuffled() {
    this.game.startGame(this.valid, 4, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n"
            + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
            + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
            + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠", this.game.getGameState());
    String unshuffledStart = this.game.getGameState();
    this.game.startGame(this.valid, 4, 4, true);
    assertNotEquals(unshuffledStart, this.game.getGameState());
  }

  // test the invalid parameters that would make startGame throw exception
  // invalid deck is already tested, so only the numpiles need to be tested
  @Test(expected = IllegalArgumentException.class)
  public void startGameExceptionInvalidParameter1() {
    this.game.startGame(this.valid, 8, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameExceptionInvalidParameter2() {
    this.game.startGame(this.valid, 2, 4, true);
  }

  // test private methods validPileNumber and validIndex through public method move
  // expects exceptions if the pilNumber or the index does not exists when giving the
  // parameters for the move method. When they are valid, move will not throw exception and
  // that will be tested when move is being tested
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidIndex1() {
    this.game.startGame(this.valid, 2, 4, true);
    this.game.move(PileType.OPEN, 0,2, PileType.CASCADE,0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidIndex2() {
    this.game.startGame(this.valid, 2, 4, true);
    this.game.move(PileType.CASCADE, 0,-70, PileType.CASCADE,0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPileNumber1() {
    this.game.startGame(this.valid, 2, 4, true);
    this.game.move(PileType.CASCADE, 8,0, PileType.CASCADE,0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPileNumber2() {
    this.game.startGame(this.valid, 2, 4, true);
    this.game.move(PileType.CASCADE, 0,0, PileType.CASCADE,10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPileNumber3() {
    this.game.startGame(this.valid, 2, 4, true);
    this.game.move(PileType.CASCADE, 0,0, PileType.CASCADE,-5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPileNumber4() {
    this.game.startGame(this.valid, 2, 4, true);
    this.game.move(PileType.CASCADE, -18,0, PileType.CASCADE,0);
  }

  // test private method takeCard through public method move
  // here we test the invalid parameters that would make the method throw exceptions, some of these
  // like invalid index ard pilenumber are already tested above from other private methods
  // in the case it would be valid, move will not throw exception and will be tested in move() tests
  @Test(expected = IllegalArgumentException.class)
  public void testTakeCard1() {
    this.game.startGame(this.valid, 2, 4, true);
    this.game.move(PileType.FOUNDATION, 0,0, PileType.CASCADE,0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTakeCard2() {
    this.game.startGame(this.valid, 2, 4, true);
    this.game.move(PileType.OPEN, 0,100, PileType.CASCADE,0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTakeCard3() {
    this.game.startGame(this.valid, 2, 4, true);
    this.game.move(PileType.CASCADE, 0,100, PileType.CASCADE,0);
  }

  // Test public method move, the cases that when exceptions are thrown are already tested above:
  // when pile number, index is invalid, or a card can't be taken out. Here we test all other
  // scenarios when a card is able to move and the exceptions when a card can not be added
  // to a pile.
  @Test
  public void testMoveValid() {
    this.game.startGame(this.game.getDeck(), 12, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n"
            + "C1: A♣, 4♣, 7♣, 10♣, K♣\n"
            + "C2: A♦, 4♦, 7♦, 10♦, K♦\n"
            + "C3: A♥, 4♥, 7♥, 10♥, K♥\n"
            + "C4: A♠, 4♠, 7♠, 10♠, K♠\n"
            + "C5: 2♣, 5♣, 8♣, J♣\n"
            + "C6: 2♦, 5♦, 8♦, J♦\n"
            + "C7: 2♥, 5♥, 8♥, J♥\n"
            + "C8: 2♠, 5♠, 8♠, J♠\n"
            + "C9: 3♣, 6♣, 9♣, Q♣\n"
            + "C10: 3♦, 6♦, 9♦, Q♦\n"
            + "C11: 3♥, 6♥, 9♥, Q♥\n"
            + "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
    this.game.move(PileType.CASCADE, 0, 4, PileType.OPEN, 0);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1: K♣\nO2:\nO3:\nO4:\n" +
            "C1: A♣, 4♣, 7♣, 10♣\n" +
            "C2: A♦, 4♦, 7♦, 10♦, K♦\n" +
            "C3: A♥, 4♥, 7♥, 10♥, K♥\n" +
            "C4: A♠, 4♠, 7♠, 10♠, K♠\n" +
            "C5: 2♣, 5♣, 8♣, J♣\n" +
            "C6: 2♦, 5♦, 8♦, J♦\n" +
            "C7: 2♥, 5♥, 8♥, J♥\n" +
            "C8: 2♠, 5♠, 8♠, J♠\n" +
            "C9: 3♣, 6♣, 9♣, Q♣\n" +
            "C10: 3♦, 6♦, 9♦, Q♦\n" +
            "C11: 3♥, 6♥, 9♥, Q♥\n" +
            "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
    this.game.move(PileType.CASCADE, 0, 3, PileType.OPEN, 1);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1: K♣\nO2: 10♣\nO3:\nO4:\n" +
            "C1: A♣, 4♣, 7♣\n" +
            "C2: A♦, 4♦, 7♦, 10♦, K♦\n" +
            "C3: A♥, 4♥, 7♥, 10♥, K♥\n" +
            "C4: A♠, 4♠, 7♠, 10♠, K♠\n" +
            "C5: 2♣, 5♣, 8♣, J♣\n" +
            "C6: 2♦, 5♦, 8♦, J♦\n" +
            "C7: 2♥, 5♥, 8♥, J♥\n" +
            "C8: 2♠, 5♠, 8♠, J♠\n" +
            "C9: 3♣, 6♣, 9♣, Q♣\n" +
            "C10: 3♦, 6♦, 9♦, Q♦\n" +
            "C11: 3♥, 6♥, 9♥, Q♥\n" +
            "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
    this.game.move(PileType.CASCADE, 0, 2, PileType.OPEN, 2);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1: K♣\nO2: 10♣\nO3: 7♣\nO4:\n" +
            "C1: A♣, 4♣\n" +
            "C2: A♦, 4♦, 7♦, 10♦, K♦\n" +
            "C3: A♥, 4♥, 7♥, 10♥, K♥\n" +
            "C4: A♠, 4♠, 7♠, 10♠, K♠\n" +
            "C5: 2♣, 5♣, 8♣, J♣\n" +
            "C6: 2♦, 5♦, 8♦, J♦\n" +
            "C7: 2♥, 5♥, 8♥, J♥\n" +
            "C8: 2♠, 5♠, 8♠, J♠\n" +
            "C9: 3♣, 6♣, 9♣, Q♣\n" +
            "C10: 3♦, 6♦, 9♦, Q♦\n" +
            "C11: 3♥, 6♥, 9♥, Q♥\n" +
            "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
    this.game.move(PileType.CASCADE, 0, 1, PileType.OPEN, 3);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1: K♣\nO2: 10♣\nO3: 7♣\nO4: 4♣\n" +
            "C1: A♣\n" +
            "C2: A♦, 4♦, 7♦, 10♦, K♦\n" +
            "C3: A♥, 4♥, 7♥, 10♥, K♥\n" +
            "C4: A♠, 4♠, 7♠, 10♠, K♠\n" +
            "C5: 2♣, 5♣, 8♣, J♣\n" +
            "C6: 2♦, 5♦, 8♦, J♦\n" +
            "C7: 2♥, 5♥, 8♥, J♥\n" +
            "C8: 2♠, 5♠, 8♠, J♠\n" +
            "C9: 3♣, 6♣, 9♣, Q♣\n" +
            "C10: 3♦, 6♦, 9♦, Q♦\n" +
            "C11: 3♥, 6♥, 9♥, Q♥\n" +
            "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
    this.game.move(PileType.CASCADE, 0, 0,
            PileType.FOUNDATION, 0);
    assertEquals("F1: A♣\nF2:\nF3:\nF4:\nO1: K♣\nO2: 10♣\nO3: 7♣\nO4: 4♣\n" +
            "C1:\n" +
            "C2: A♦, 4♦, 7♦, 10♦, K♦\n" +
            "C3: A♥, 4♥, 7♥, 10♥, K♥\n" +
            "C4: A♠, 4♠, 7♠, 10♠, K♠\n" +
            "C5: 2♣, 5♣, 8♣, J♣\n" +
            "C6: 2♦, 5♦, 8♦, J♦\n" +
            "C7: 2♥, 5♥, 8♥, J♥\n" +
            "C8: 2♠, 5♠, 8♠, J♠\n" +
            "C9: 3♣, 6♣, 9♣, Q♣\n" +
            "C10: 3♦, 6♦, 9♦, Q♦\n" +
            "C11: 3♥, 6♥, 9♥, Q♥\n" +
            "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
    this.game.move(PileType.OPEN, 1, 0, PileType.CASCADE, 6);
    assertEquals("F1: A♣\nF2:\nF3:\nF4:\nO1: K♣\nO2:\nO3: 7♣\nO4: 4♣\n" +
            "C1:\n" +
            "C2: A♦, 4♦, 7♦, 10♦, K♦\n" +
            "C3: A♥, 4♥, 7♥, 10♥, K♥\n" +
            "C4: A♠, 4♠, 7♠, 10♠, K♠\n" +
            "C5: 2♣, 5♣, 8♣, J♣\n" +
            "C6: 2♦, 5♦, 8♦, J♦\n" +
            "C7: 2♥, 5♥, 8♥, J♥, 10♣\n" +
            "C8: 2♠, 5♠, 8♠, J♠\n" +
            "C9: 3♣, 6♣, 9♣, Q♣\n" +
            "C10: 3♦, 6♦, 9♦, Q♦\n" +
            "C11: 3♥, 6♥, 9♥, Q♥\n" +
            "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void canNotMoveToFoundation() {
    this.game.startGame(this.game.getDeck(), 12, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n" +
            "C1: A♣, 4♣, 7♣, 10♣, K♣\n" +
            "C2: A♦, 4♦, 7♦, 10♦, K♦\n" +
            "C3: A♥, 4♥, 7♥, 10♥, K♥\n" +
            "C4: A♠, 4♠, 7♠, 10♠, K♠\n" +
            "C5: 2♣, 5♣, 8♣, J♣\n" +
            "C6: 2♦, 5♦, 8♦, J♦\n" +
            "C7: 2♥, 5♥, 8♥, J♥\n" +
            "C8: 2♠, 5♠, 8♠, J♠\n" +
            "C9: 3♣, 6♣, 9♣, Q♣\n" +
            "C10: 3♦, 6♦, 9♦, Q♦\n" +
            "C11: 3♥, 6♥, 9♥, Q♥\n" +
            "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
    this.game.move(PileType.CASCADE, 0, 4,
            PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void canNotMoveToOPEN() {
    this.game.startGame(this.game.getDeck(), 12, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n" +
            "C1: A♣, 4♣, 7♣, 10♣, K♣\n" +
            "C2: A♦, 4♦, 7♦, 10♦, K♦\n" +
            "C3: A♥, 4♥, 7♥, 10♥, K♥\n" +
            "C4: A♠, 4♠, 7♠, 10♠, K♠\n" +
            "C5: 2♣, 5♣, 8♣, J♣\n" +
            "C6: 2♦, 5♦, 8♦, J♦\n" +
            "C7: 2♥, 5♥, 8♥, J♥\n" +
            "C8: 2♠, 5♠, 8♠, J♠\n" +
            "C9: 3♣, 6♣, 9♣, Q♣\n" +
            "C10: 3♦, 6♦, 9♦, Q♦\n" +
            "C11: 3♥, 6♥, 9♥, Q♥\n" +
            "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
    this.game.move(PileType.CASCADE, 0, 4, PileType.OPEN, 0);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1: K♣\nO2:\nO3:\nO4:\n" +
            "C1: A♣, 4♣, 7♣, 10♣\n" +
            "C2: A♦, 4♦, 7♦, 10♦, K♦\n" +
            "C3: A♥, 4♥, 7♥, 10♥, K♥\n" +
            "C4: A♠, 4♠, 7♠, 10♠, K♠\n" +
            "C5: 2♣, 5♣, 8♣, J♣\n" +
            "C6: 2♦, 5♦, 8♦, J♦\n" +
            "C7: 2♥, 5♥, 8♥, J♥\n" +
            "C8: 2♠, 5♠, 8♠, J♠\n" +
            "C9: 3♣, 6♣, 9♣, Q♣\n" +
            "C10: 3♦, 6♦, 9♦, Q♦\n" +
            "C11: 3♥, 6♥, 9♥, Q♥\n" +
            "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
    this.game.move(PileType.CASCADE, 0, 3, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void canNotMoveToCascade() {
    this.game.startGame(this.game.getDeck(), 12, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nO3:\nO4:\n" +
            "C1: A♣, 4♣, 7♣, 10♣, K♣\n" +
            "C2: A♦, 4♦, 7♦, 10♦, K♦\n" +
            "C3: A♥, 4♥, 7♥, 10♥, K♥\n" +
            "C4: A♠, 4♠, 7♠, 10♠, K♠\n" +
            "C5: 2♣, 5♣, 8♣, J♣\n" +
            "C6: 2♦, 5♦, 8♦, J♦\n" +
            "C7: 2♥, 5♥, 8♥, J♥\n" +
            "C8: 2♠, 5♠, 8♠, J♠\n" +
            "C9: 3♣, 6♣, 9♣, Q♣\n" +
            "C10: 3♦, 6♦, 9♦, Q♦\n" +
            "C11: 3♥, 6♥, 9♥, Q♥\n" +
            "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
    this.game.move(PileType.CASCADE, 0, 4, PileType.OPEN, 0);
    assertEquals("F1:\nF2:\nF3:\nF4:\nO1: K♣\nO2:\nO3:\nO4:\n" +
            "C1: A♣, 4♣, 7♣, 10♣\n" +
            "C2: A♦, 4♦, 7♦, 10♦, K♦\n" +
            "C3: A♥, 4♥, 7♥, 10♥, K♥\n" +
            "C4: A♠, 4♠, 7♠, 10♠, K♠\n" +
            "C5: 2♣, 5♣, 8♣, J♣\n" +
            "C6: 2♦, 5♦, 8♦, J♦\n" +
            "C7: 2♥, 5♥, 8♥, J♥\n" +
            "C8: 2♠, 5♠, 8♠, J♠\n" +
            "C9: 3♣, 6♣, 9♣, Q♣\n" +
            "C10: 3♦, 6♦, 9♦, Q♦\n" +
            "C11: 3♥, 6♥, 9♥, Q♥\n" +
            "C12: 3♠, 6♠, 9♠, Q♠", this.game.getGameState());
    this.game.move(PileType.OPEN, 0, 0, PileType.CASCADE, 0);
  }

  // test get game state, test at game initially started and before game started
  @Test
  public void testIsGameOver() {
    this.game = new FreecellModel();
    this.game.startGame(this.game.getDeck(), 6, 4, false);
    assertEquals(false, this.game.isGameOver());
  }

  // test get game state, test at game initially started and before game started
  // game state is an empty string before the game started
  @Test
  public void testGameStateBegin() {
    this.game = new FreecellModel();
    assertEquals("", this.game.getGameState());
    this.game.startGame(this.game.getDeck(), 6, 4, false);
    assertEquals("F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n" + "O2:\n" + "O3:\n"
            + "O4:\n"
            + "C1: A♣, 2♥, 4♣, 5♥, 7♣, 8♥, 10♣, J♥, K♣\n"
            + "C2: A♦, 2♠, 4♦, 5♠, 7♦, 8♠, 10♦, J♠, K♦\n"
            + "C3: A♥, 3♣, 4♥, 6♣, 7♥, 9♣, 10♥, Q♣, K♥\n"
            + "C4: A♠, 3♦, 4♠, 6♦, 7♠, 9♦, 10♠, Q♦, K♠\n"
            + "C5: 2♣, 3♥, 5♣, 6♥, 8♣, 9♥, J♣, Q♥\n"
            + "C6: 2♦, 3♠, 5♦, 6♠, 8♦, 9♠, J♦, Q♠", this.game.getGameState());
  }
}
