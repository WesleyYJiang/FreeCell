import cs3500.hw02.Card;
import cs3500.hw02.Pile;
import cs3500.hw02.PileType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Tests the methods in the Pile class.
 */
public class PileTest {
  private Pile pile1 = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
          new Card(2, "♠"), new Card(4, "♦"), new Card(5, "♠"),
          new Card(7, "♦"), new Card(8, "♠"), new Card(10, "♦"),
          new Card(11, "♠"), new Card(13, "♦"))), PileType.CASCADE);
  private Pile pileOne = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
          new Card(2, "♠"), new Card(4, "♦"), new Card(5, "♠"),
          new Card(7, "♦"), new Card(8, "♠"), new Card(10, "♦"),
          new Card(11, "♠"), new Card(13, "♦"))), PileType.CASCADE);
  private Pile pile2 = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"))),
          PileType.OPEN);
  private Pile empty = new Pile(new ArrayList<>(), PileType.CASCADE);

  // test if pile to strings prints correctly and empty scenario
  @Test
  public void toStringTest1() {
    assertEquals(" A♦, 2♠, 4♦, 5♠, 7♦, 8♠, 10♦, J♠, K♦",
            this.pile1.toString());
    assertEquals("",
            this.empty.toString());
  }

  // test if 2 piles are equal
  // test other cases like different object or different values
  @Test
  public void TestEquals() {
    assertEquals(true, this.pile1.equals(pile1));
    assertEquals(true, this.pile1.equals(pileOne));
    assertEquals(false, this.empty.equals(10));
    assertEquals(false, this.empty.equals(pile2));
  }

  // test if takeCardAndRemove works for all cases
  @Test
  public void TestTakeCardAndRemoveFromOpen() {
    Pile open = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"))),
            PileType.OPEN);
    Pile empty = new Pile(new ArrayList<>(), PileType.OPEN);
    assertEquals(new Card(1, "♦"), open.takeCardAndRemove(0));
    assertEquals(empty, open);
  }

  @Test
  public void TestTakeCardAndRemoveFromCascade() {
    Pile cascade = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(13, "♦"))), PileType.CASCADE);

    assertEquals(new Card(13, "♦"), cascade.takeCardAndRemove(2));
    assertEquals(new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"))), PileType.CASCADE), cascade);
  }

  // throw exception when cards cant be moved
  @Test(expected = IllegalArgumentException.class)
  public void cantMoveCardFromCascade() {
    Pile cascade = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(13, "♦"))), PileType.CASCADE);
    cascade.takeCardAndRemove(8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void cantMoveCardFromFoundation() {
    Pile foundation = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"))),
            PileType.FOUNDATION);
    foundation.takeCardAndRemove(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void cantMoveCardFromOpen() {
    Pile open = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"))),
            PileType.OPEN);
    open.takeCardAndRemove(1);
  }

  // test the foundation rule, if a card satisfy the foundation pile rule
  @Test
  public void testFoundationRule() {
    Pile foundation = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(12, "♦"))), PileType.FOUNDATION);
    Pile empty = new Pile(new ArrayList<>(Arrays.asList()), PileType.FOUNDATION);

    assertEquals(true, empty.foundationRule(new Card(1, "♦")));
    assertEquals(true, foundation.foundationRule(new Card(13, "♦")));
    assertEquals(false, empty.foundationRule(new Card(5, "♦")));
    assertEquals(false, foundation.foundationRule(new Card(13, "♠")));
    assertEquals(false, foundation.foundationRule(new Card(11, "♦")));
  }

  // test the cascadeNotEmptyRule rule, if a card satisfy the cascade pile rule
  @Test
  public void testCascadeNotEmptyRule() {
    Pile cascade = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(10, "♦"))), PileType.CASCADE);
    Pile open = new Pile(new ArrayList<>(Arrays.asList()), PileType.OPEN);

    assertEquals(false, open.cascadeNotEmptyRule(new Card(5, "♦")));
    assertEquals(false, cascade.cascadeNotEmptyRule(new Card(9, "♦")));
    assertEquals(false, cascade.cascadeNotEmptyRule(new Card(9, "♥")));
    assertEquals(false, cascade.cascadeNotEmptyRule(new Card(13, "♦")));
    assertEquals(true, cascade.cascadeNotEmptyRule(new Card(9, "♠")));
    assertEquals(true, cascade.cascadeNotEmptyRule(new Card(9, "♣")));
  }

  // test addCard, alot of the scenarios were tested from testing the helper methods already
  @Test
  public void testAddCard() {
    Pile cascade = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(10, "♦"))), PileType.CASCADE);
    Pile openEmpty = new Pile(new ArrayList<>(Arrays.asList()), PileType.OPEN);
    Pile cascadeEmpty = new Pile(new ArrayList<>(Arrays.asList()), PileType.CASCADE);
    Pile foundationEmpty = new Pile(new ArrayList<>(Arrays.asList()), PileType.FOUNDATION);

    openEmpty.addCard(new Card(5, "♦"));
    assertEquals(new Pile(new ArrayList<>(Arrays.asList(new Card(5, "♦"))),
            PileType.OPEN), openEmpty);

    cascadeEmpty.addCard(new Card(5, "♦"));
    assertEquals(new Pile(new ArrayList<>(Arrays.asList(new Card(5, "♦"))),
            PileType.CASCADE), cascadeEmpty);

    cascade.addCard(new Card(9, "♠"));
    assertEquals(new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(10, "♦"),
            new Card(9, "♠"))), PileType.CASCADE), cascade);

    cascade.addCard(new Card(8, "♥"));
    assertEquals(new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(10, "♦"),
            new Card(9, "♠"), new Card(8, "♥"))),
            PileType.CASCADE), cascade);

    cascade.addCard(new Card(7, "♣"));
    assertEquals(new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(10, "♦"),
            new Card(9, "♠"), new Card(8, "♥"),
            new Card(7, "♣"))), PileType.CASCADE), cascade);

    foundationEmpty.addCard(new Card(1, "♣"));
    assertEquals(new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♣"))),
            PileType.FOUNDATION), foundationEmpty);
    foundationEmpty.addCard(new Card(2, "♣"));
    assertEquals(new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♣"),
            new Card(2, "♣"))),
            PileType.FOUNDATION), foundationEmpty);
  }

  // test the exceptions thrown when adding card
  @Test(expected = IllegalArgumentException.class)
  public void addCardException1() {
    Pile foundation = new Pile(new ArrayList<>(), PileType.FOUNDATION);
    foundation.addCard(new Card(2, "♦"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardException2() {
    Pile cascade = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♣"))),
            PileType.CASCADE);
    cascade.addCard(new Card(2, "♦"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardException3() {
    Pile open = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♣"))),
            PileType.OPEN);
    open.addCard(new Card(2, "♦"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardException4() {
    Pile cascade = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♣"))),
            PileType.CASCADE);
    cascade.addCard(new Card(10, "♦"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardException5() {
    Pile foundation = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♣"))),
            PileType.FOUNDATION);
    foundation.addCard(new Card(2, "♦"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addCardException6() {
    Pile foundation = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♣"))),
            PileType.FOUNDATION);
    foundation.addCard(new Card(1, "♣"));
  }

  // TEST FOR HW04

  // test if this isEmpty method in the pile class works
  @Test
  public void testIsEmptyMethod() {
    Pile nonEmpty = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"))),
            PileType.CASCADE);
    Pile empty = new Pile(new ArrayList<>(), PileType.CASCADE);

    assertEquals(false, nonEmpty.isEmpty());
    assertEquals(true, empty.isEmpty());
  }

  @Test
  public void testIsValidBuild() {
    Pile open = new Pile(new ArrayList<>(Arrays.asList(new Card(10, "♦"),
            new Card(9, "♠"), new Card(8, "♦"))), PileType.OPEN);
    Pile validCascade = new Pile(new ArrayList<>(Arrays.asList(new Card(10, "♦"),
            new Card(9, "♠"), new Card(8, "♦"),
            new Card(7, "♣"))), PileType.CASCADE);
    Pile inValidCascade1 = new Pile(new ArrayList<>(Arrays.asList(new Card(10, "♦"),
            new Card(8, "♠"), new Card(8, "♦"),
            new Card(7, "♣"))), PileType.CASCADE);
    Pile inValidCascade2 = new Pile(new ArrayList<>(Arrays.asList(new Card(10, "♦"),
            new Card(9, "♠"), new Card(8, "♣"),
            new Card(7, "♦"))), PileType.CASCADE);
    Pile empty = new Pile(new ArrayList<>(), PileType.CASCADE);

    assertEquals(true, empty.validBuild());
    assertEquals(false, open.validBuild());
    assertEquals(true, validCascade.validBuild());
    assertEquals(false, inValidCascade1.validBuild());
    assertEquals(false, inValidCascade2.validBuild());
  }

  // test to see if a valid build of cards can be added to a cascade pile correctly
  @Test
  public void addPileTest() {
    Pile valid = new Pile(new ArrayList<>(Arrays.asList(new Card(8, "♦"),
            new Card(9, "♠"), new Card(10, "♦"))), PileType.CASCADE);
    Pile cascade = new Pile(new ArrayList<>(Arrays.asList(new Card(11, "♠"))),
            PileType.CASCADE);
    Pile empty = new Pile(new ArrayList<>(), PileType.CASCADE);

    assertEquals(" J♠", cascade.toString());
    cascade.addPile(valid);
    assertEquals(" J♠, 10♦, 9♠, 8♦",cascade.toString());

    assertEquals("", empty.toString());
    empty.addPile(valid);
    assertEquals(" 10♦, 9♠, 8♦",empty.toString());
  }

  // piles that can not be added to an existing pile
  @Test(expected = IllegalArgumentException.class)
  public void addPileExceptions1() {
    Pile cascade = new Pile(new ArrayList<>(Arrays.asList(new Card(11, "♠"))),
            PileType.CASCADE);
    Pile invalid = new Pile(new ArrayList<>(Arrays.asList(new Card(12, "♦"))),
            PileType.CASCADE);
    cascade.addPile(invalid);
  }

  // piles that can not be added to an existing pile
  @Test(expected = IllegalArgumentException.class)
  public void addPileExceptions2() {
    Pile invalid = new Pile(new ArrayList<>(), PileType.FOUNDATION);
    Pile valid = new Pile(new ArrayList<>(Arrays.asList(new Card(10, "♦"),
            new Card(9, "♠"), new Card(8, "♦"))), PileType.CASCADE);
    invalid.addPile(valid);
  }

  // testing a private method through public method
  @Test(expected = IllegalArgumentException.class)
  public void validStartingIndexTest1() {
    Pile cascade = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(10, "♦"),
            new Card(9, "♠"), new Card(8, "♦"))), PileType.CASCADE);

    cascade.getSubPile(-1);
  }

  // testing a private method through public method
  @Test(expected = IllegalArgumentException.class)
  public void validStartingIndexTest2() {
    Pile cascade = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(10, "♦"),
            new Card(9, "♠"), new Card(8, "♦"))), PileType.CASCADE);

    cascade.getSubPile(4);
  }

  // testing if the method can get sub pile from a cascade pile
  @Test
  public void getSubPileTest() {
    Pile cascade = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(10, "♦"),
            new Card(9, "♠"), new Card(8, "♦"))), PileType.CASCADE);

    assertEquals(" 9♠, 8♦",
            cascade.getSubPile(3).toString());
    assertEquals(" 10♦, 9♠, 8♦",
            cascade.getSubPile(2).toString());
    assertEquals(" J♠, 10♦, 9♠, 8♦",
            cascade.getSubPile(1).toString());
  }


  // test to see if the cards are removed, when a sub pile is being taken
  @Test
  public void takeAndRemoveSubPile() {
    Pile cascade1 = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(11, "♠"), new Card(10, "♦"),
            new Card(9, "♠"), new Card(8, "♦"))), PileType.CASCADE);
    Pile cascade2 = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(4, "♦"), new Card(3, "♣"),
            new Card(2, "♥"), new Card(1, "♠"))), PileType.CASCADE);

    assertEquals(" A♦, J♠, 10♦, 9♠, 8♦", cascade1.toString());
    assertEquals(" 8♦, 9♠, 10♦, J♠", cascade1.takeAndRemoveSubPile(1).toString());
    assertEquals(" A♦", cascade1.toString());

    assertEquals(" A♦, 4♦, 3♣, 2♥, A♠", cascade2.toString());
    assertEquals(" A♠, 2♥", cascade2.takeAndRemoveSubPile(3).toString());
    assertEquals(" A♦, 4♦, 3♣", cascade2.toString());
  }

  // testing exceptions when the start index is wrong
  @Test(expected = IllegalArgumentException.class)
  public void takeAndRemoveSubPileException2() {
    Pile foundation = new Pile(new ArrayList<>(Arrays.asList(new Card(1, "♦"),
            new Card(2, "♦"), new Card(3, "♦"))), PileType.FOUNDATION);
    foundation.getSubPile(2);
  }
}
