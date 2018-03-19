import org.junit.Test;
import cs3500.hw02.Card;

import static org.junit.Assert.assertEquals;

/**
 * Tests the methods in the Card class.
 */
public class CardTest {

  Card diamond6 = new Card(6,"♦");
  Card heartAce = new Card(1,"♥");
  Card clubJack = new Card(11,"♣");
  Card spadeQueen = new Card(12,"♠");
  Card spadeKing = new Card(13,"♠");
  Card aceHeart = new Card(1,"♥");

  // test if the cards print out correctly as string
  @Test
  public void toStringTest() {
    assertEquals("A♥", heartAce.toString());
    assertEquals("6♦", diamond6.toString());
    assertEquals("J♣", clubJack.toString());
    assertEquals("Q♠", spadeQueen.toString());
    assertEquals("K♠", spadeKing.toString());
  }

  // test if the cards equality works
  @Test
  public void equalsTest1() {
    assertEquals(true, diamond6.equals(diamond6));
    assertEquals(false, diamond6.equals(1));
    assertEquals(false, diamond6.equals(aceHeart));
    assertEquals(false, diamond6.equals(aceHeart));
    assertEquals(true, aceHeart.equals(heartAce));
  }

  // if the color prints out correctly
  @Test
  public void testColor() {
    assertEquals("Red", diamond6.color());
    assertEquals("Red", aceHeart.color());
    assertEquals("Black", clubJack.color());
    assertEquals("Black", spadeKing.color());
  }

  // test if the hasCode works fine
  @Test
  public void testHashCode() {
    assertEquals(9836, diamond6.hashCode());
    assertEquals(9830, aceHeart.hashCode());
  }
}
