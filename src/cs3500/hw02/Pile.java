package cs3500.hw02;

import java.util.ArrayList;

/**
 * Represents a pile, a pile has a List of cards and the pile type.
 */
public class Pile {
  final ArrayList<Card> cards;
  private PileType type;

  /**
   * Constructs an pile.
   *
   * @param cards the title of the article
   * @param type  the author of the article
   */
  public Pile(ArrayList<Card> cards, PileType type) {
    this.cards = cards;
    this.type = type;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    if (this.cards.isEmpty()) {
      return "";
    } else {
      for (Card c : cards) {
        result.append(" " + c.toString() + ",");
      }
    }
    return result.toString().substring(0, result.length() - 1);
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Pile)) {
      return false;
    }
    return ((Pile) that).type == this.type && ((Pile) that).cards.size() == this.cards.size()
            && this.cards.containsAll(((Pile) that).cards);
  }

  @Override
  public int hashCode() {
    return type.hashCode() * this.cards.hashCode();
  }

  /**
   * Checks if a card can be moved from a pile.
   * if yes, return the card.
   *
   * @param cardIndex the index of the card
   * @return boolean: true if it is able to move to the foundation pile
   * @throws IllegalArgumentException if the card can not be moved
   */
  public Card takeCardAndRemove(int cardIndex) {
    Card temp;

    if (this.type == PileType.OPEN && cardIndex == 0) {
      temp = this.cards.get(cardIndex);
      this.cards.remove(cardIndex);
    } else if (this.type == PileType.CASCADE && cardIndex == this.cards.size() - 1) {
      temp = this.cards.get(cardIndex);
      this.cards.remove(cardIndex);
    } else {
      throw new IllegalArgumentException("Invalid Card to move.");
    }
    return temp;
  }

  /**
   * Checks if a card can be add to a pile and add it to the pile.
   *
   * @throws IllegalArgumentException if the card can not be moved
   */
  public void addCard(Card c) {
    if (this.type == PileType.OPEN && !this.cards.isEmpty()) {
      throw new IllegalArgumentException();
    }
    else if (this.type == PileType.OPEN && this.cards.isEmpty()
            || this.type == PileType.CASCADE && this.cards.isEmpty()
            || this.cascadeNotEmptyRule(c)
            || this.foundationRule(c)) {
      this.cards.add(c);
    }
  }

  /**
   * Checks if the pile is not empty and a card is smaller
   * than the last one in the pile by 1 and has the different color.
   *
   * @param c the card to be moved
   * @return boolean: true if it the card satisfy the rule
   */
  public boolean cascadeNotEmptyRule(Card c) {
    return this.type == PileType.CASCADE && !this.cards.isEmpty()
            && !this.cards.get(this.cards.size() - 1).color().equals(c.color())
            && this.cards.get(this.cards.size() - 1).value - 1 == c.value;
  }

  /**
   * Checks if the pile is not empty and a card is bigger
   * than the last one in the pile by 1 and has the same suit.
   *
   * @param c the card to be moved
   * @return boolean: true if it the card satisfy the rule
   */
  public boolean foundationRule(Card c) {
    return !this.cards.isEmpty() && this.cards.get(this.cards.size() - 1).suit.equals(c.suit)
            && this.type == PileType.FOUNDATION
            && this.cards.get(this.cards.size() - 1).value + 1 == c.value
            || this.cards.isEmpty() && c.value == 1 && this.type == PileType.FOUNDATION;
  }

  // ************************************
  //        ADDED METHOD FOR HW04
  // ************************************
  /**
   * Determines if this pile is empty.
   *
   * @return boolean: returns true if this pile is empty
   */
  public boolean isEmpty() {
    return this.cards.isEmpty();
  }

  /**
   * Gets the number of the cards in this pile.
   *
   * @return integer: returns the number of cards in this pile
   */
  public int pileLength() {
    return this.cards.size();
  }

  /**
   * Validate if this pile is a valid cascade build. The cards has to be consecutive
   * with a descending order and alternating color.
   *
   * @return boolean: returns true if this pile has a valid cascade build, else false
   */
  public boolean validBuild() {
    boolean result = true;
    for (int i = 1; i < this.cards.size(); i++) {
      result = result && this.cards.get(i - 1).value - 1 == this.cards.get(i).value;
      result = result && !this.cards.get(i - 1).color().equals(this.cards.get(i).color());
    }
    return result && this.type == PileType.CASCADE;
  }

  /**
   * Checks if a sub pile is a valid build or empty, add them to the pile if it is.
   *
   * @param that the sub pile that is being added to this pile for a multi card move
   * @throws IllegalArgumentException if the sub pile can not be added
   */
  public void addPile(Pile that) {
    for (int i = that.pileLength() - 1; i >= 0; i--) {
      this.addCard(that.cards.get(i));
    }
  }

  /**
   * Checks an index is valid as the starting index of a multi card move.
   *
   * @param startIndex starting index for a multi card move
   * @return boolean: true if the starting index is valid
   */
  private boolean validStartingIndex(int startIndex) {
    return startIndex >= 0 && startIndex < this.cards.size() - 1;
  }

  /**
   * Gets a sub Pile from a cascade pile with the provided start index and end index.
   *
   * @param start the first index of the sub pile
   * @return Pile: the sub pile
   * @throws IllegalArgumentException if the parameters are not valid
   */
  public Pile getSubPile(int start) {
    Pile temp = new Pile(new ArrayList<>(), this.type);

    if (this.validStartingIndex(start)) {
      for (int i = start; i < this.cards.size(); i++) {
        temp.cards.add(this.cards.get(i));
      }
      return temp;
    } else {
      throw new IllegalArgumentException("Invalid move.");
    }
  }

  /**
   * Takes a sub pile that is a valid build of cards and remove it from this pile.
   *
   * @param start the first index of the sub pile
   * @return pile: the sub pile that is a valid build for multi card
   * @throws IllegalArgumentException the sub pile is not a valid build of cards
   */
  public Pile takeAndRemoveSubPile(int start) {
    Pile temp = new Pile(new ArrayList<>(), PileType.CASCADE);
    for (int i = this.cards.size() - 1; i >= start; i--) {
      temp.cards.add(this.takeCardAndRemove(i));
    }
    return temp;
  }
}
