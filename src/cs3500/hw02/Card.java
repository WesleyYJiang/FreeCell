package cs3500.hw02;
/**
 *  Represents a card.
 *  A card can have one of the thirteen values:
 *  ace (written  A), two through ten (written 2 through 10), jack (J), queen (Q) and king (K).
 *  aces are considered “low”
 *  A card also needs to be one of the four suits:
 *  clubs (♣), diamonds (♦), hearts (♥), and spades (♠)
 */

public class Card {
  final int value;
  final String suit;

  /**
   * Constructs an card.
   *
   * @param value the value of the card
   * @param suit  the suit of the card
   * @throws IllegalArgumentException if the value or the suit is not valid
   */
  public Card(int value, String suit) {
    if (value >= 1 && value <= 13 && (suit.equals("♣") || suit.equals("♦")
        || suit.equals("♥") || suit.equals("♠"))) {
      this.value = value;
      this.suit = suit;
    }
    else {
      throw new IllegalArgumentException("Invalid card!");
    }
  }

  @Override
  public String toString() {
    switch (value) {
      case 1:
        return "A" + suit;
      case 11:
        return "J" + suit;
      case 12:
        return "Q" + suit;
      case 13:
        return "K" + suit;
      default:
        return value + suit;
    }
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Card)) {
      return false;
    }

    return ((Card) that).value == this.value && ((Card) that).suit.equals(this.suit);
  }

  @Override
  public int hashCode() {
    return this.suit.hashCode() + this.value;
  }

  /**
   * Get the color of the card,
   * ♠ and ♣ are black, ♥ and ♦ red.
   *
   * @return string the color of the card
   */
  public String color() {
    if (this.suit.equals("♠") || this.suit.equals("♣")) {
      return "Black";
    }
    else {
      return "Red";
    }
  }
}
