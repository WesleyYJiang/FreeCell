package cs3500.hw02;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;


/**
 * This is the Freecell model that implements the interface.
 */
public class FreecellModel implements FreecellOperations {

  private ArrayList<Pile> foundationPiles = new ArrayList<>();
  protected ArrayList<Pile> openPiles = new ArrayList<>();
  protected ArrayList<Pile> cascadePiles = new ArrayList<>();  // CHANGED TO PUBLIC FROM PRIVATE
  private int numCascade;
  private int numOpen;

  @Override
  public List getDeck() {
    List<Card> deck = new ArrayList<>();
    for (int i = 1; i <= 13; i++) {
      deck.add(new Card(i, "♣"));
      deck.add(new Card(i, "♦"));
      deck.add(new Card(i, "♥"));
      deck.add(new Card(i, "♠"));
    }
    return deck;
  }

  /**
   * Check if a deck is valid.
   * A deck is valid if it has 52 cards and no duplicates,
   * assuming all cards are valid because of the constraints put in the Card class.
   *
   * @return boolean: true if the deck is valid
   */
  private boolean validDeck(List<Card> deck) {
    if (deck == null) {
      return false;
    }
    boolean result = true;
    Set<Card> allCards = new HashSet<>();
    for (Card c : deck) {
      result = allCards.add(c) && result;
    }
    return result && deck.size() == 52;
  }

  /**
   * Initialize the Array lists for cascade piles, open piles, and foundation piles
   * according the number of piles given. Clear the list first to initialize.
   *
   * @param numCascadePiles the number of cascade pile
   * @param numOpenPiles the number of open pile
   *
   */
  private void initialize(int numCascadePiles, int numOpenPiles) {
    this.cascadePiles.clear();
    this.openPiles.clear();
    this.numCascade = numCascadePiles;
    this.numOpen = numOpenPiles;

    for (int i = 0; i < 4; i++) {
      this.foundationPiles.add(new Pile(new ArrayList<>(), PileType.FOUNDATION));
    }
    for (int i = 0; i < numCascade; i++) {
      this.cascadePiles.add(new Pile(new ArrayList<>(), PileType.CASCADE));
    }
    for (int i = 0; i < numOpen; i++) {
      this.openPiles.add(new Pile(new ArrayList<>(), PileType.OPEN));
    }
  }

  /**
   * Deals cards to cascade piles according to the game rule from the deck given.
   *
   * @param d the deck of card to be dealt
   */
  private void deal(List<Card> d) {
    int cardIndex;
    int counter = 0;

    for (int j = 0; j < this.numCascade; j++) {
      cardIndex = counter;

      while (cardIndex < 52) {
        this.cascadePiles.get(j).cards.add(d.get(cardIndex));
        cardIndex += this.numCascade;
      }
      counter ++;
    }
  }

  @Override
  public void startGame(List d, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    if (!(this.validDeck(d) && numCascadePiles >= 4 && numOpenPiles >= 1)) {
      throw new IllegalArgumentException("invalid parameter");
    }
    if (shuffle) {
      Collections.shuffle(d);
    }
    this.initialize(numCascadePiles, numOpenPiles);
    this.deal(d);
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) {
    if (validPileNumber(destination, destPileNumber) && validPileNumber(source, pileNumber)) {
      Card temp = takeCard(source, pileNumber, cardIndex);

      switch (destination) {
        case OPEN:
          this.openPiles.get(destPileNumber).addCard(temp);
          break;
        case FOUNDATION:
          this.foundationPiles.get(destPileNumber).addCard(temp);
          break;
        default:
          this.cascadePiles.get(destPileNumber).addCard(temp);
          break;
      }
    } else {
      throw new IllegalArgumentException("Invalid Card to move.");
    }
  }

  /**
   * Checks if a pile number is valid.
   *
   * @param destination the destination pile type
   * @param pileNumber the destination pile type
   * @return boolean: true if it the pile number is valid
   */
  private boolean validPileNumber(PileType destination, int pileNumber) {
    switch (destination) {
      case OPEN: return pileNumber < this.numOpen && pileNumber >= 0;
      case CASCADE: return pileNumber < this.numCascade && pileNumber >= 0;
      default: return pileNumber < 4 && pileNumber >= 0;
    }
  }

  /**
   * Checks if a card can be moved from a pile and delete the card and returns it.
   *
   * @param source the source pile type
   * @param pileNumber the pile number of the card
   * @param cardIndex the index of the card
   * @return boolean: true if it is able to be moved
   * @throws IllegalArgumentException if the card can not be moved
   *
   */
  private Card takeCard(PileType source, int pileNumber, int cardIndex) {
    if (source == PileType.OPEN && this.validIndex(source, pileNumber, cardIndex)) {
      return openPiles.get(pileNumber).takeCardAndRemove(cardIndex);
    }
    else if (source == PileType.CASCADE && this.validIndex(source, pileNumber, cardIndex)) {
      return cascadePiles.get(pileNumber).takeCardAndRemove(cardIndex);
    }
    else {
      throw new IllegalArgumentException("Invalid Card to move.");
    }
  }

  /**
   * Checks if the pile number exists.
   *
   * @param type the source pile type
   * @param pileNumber the pile number of the card
   * @param i the index of the card
   * @return boolean: true if the pile number exists
   *
   */
  private boolean validIndex(PileType type, int pileNumber, int i) {
    if (type == PileType.OPEN) {
      return i >= 0 && i < this.openPiles.get(pileNumber).cards.size();
    }
    else {
      return i >= 0 && i < this.cascadePiles.get(pileNumber).cards.size();
    }
  }

  @Override
  public boolean isGameOver() {
    boolean isItOver = true;
    for (Pile p: this.cascadePiles) {
      isItOver = p.cards.isEmpty() && isItOver;
    }
    for (Pile p: this.openPiles) {
      isItOver = p.cards.isEmpty() && isItOver;
    }
    return isItOver;
  }

  @Override
  public String getGameState() {
    StringBuilder gameState = new StringBuilder();

    // check if the game has started first
    if (this.cascadePiles.isEmpty()) {
      return "";
    }
    else {
      // print foundation piles
      for (int i = 0; i < 4; i++) {
        gameState.append("F" + Integer.toString(i + 1) + ":"
                + this.foundationPiles.get(i).toString() + "\n");
      }
      // print open piles
      for (int i = 0; i < this.numOpen; i++) {
        gameState.append("O" + Integer.toString(i + 1) + ":"
                + this.openPiles.get(i).toString() + "\n");
      }
      // print cascade piles
      for (int i = 0; i < this.numCascade; i++) {
        gameState.append("C" + Integer.toString(i + 1) + ":"
                + this.cascadePiles.get(i).toString() + "\n");
      }

      return gameState.toString().substring(0, gameState.length() - 1);
    }
  }
}