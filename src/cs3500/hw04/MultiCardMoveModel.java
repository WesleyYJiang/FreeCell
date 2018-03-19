package cs3500.hw04;

import java.util.ArrayList;

import cs3500.hw02.FreecellModel;
import cs3500.hw02.Pile;
import cs3500.hw02.PileType;

/**
 * This is a free cell model for a multi card move extending the Frecellmodel.
 * This model allows for multiple card moves as well as single moves.
 */
public final class MultiCardMoveModel extends FreecellModel {

  @Override
  public void move(PileType source, int sourceNum, int index, PileType dest, int destNum) {
    if (canMultiMove(sourceNum, index, source, dest)) {
      Pile sourcePile = this.cascadePiles.get(sourceNum);
      Pile destPile = this.cascadePiles.get(destNum);

      destPile.addPile(sourcePile.takeAndRemoveSubPile(index));
    }
    else {
      super.move(source, sourceNum, index, dest, destNum);
    }
  }

  /**
   * Check if this move is a multi card move and satisfy the intermediate slots formula.
   *
   * @param pileNumber     the source pile of the multi card move
   * @param start the index of the first card of the multi card move
   * @return boolean: true if the cards can be a multi card move
   */
  private boolean canMultiMove(int pileNumber, int start, PileType source, PileType dest) {
    int numOfEmptyCascade = this.numEmpty(this.cascadePiles);
    int numOfEmptyOpen = this.numEmpty(this.openPiles);
    double maxNumOfMove = (numOfEmptyOpen + 1) * Math.pow(2, numOfEmptyCascade);

    if (source == PileType.CASCADE && dest == PileType.CASCADE) {
      int numCardsToMove = this.cascadePiles.get(pileNumber).pileLength() - start;
      return numCardsToMove <= maxNumOfMove && numCardsToMove > 1;
    }
    else {
      return false;
    }
  }

  /**
   * Count how many empty piles are in the array list.
   *
   * @param piles the list of piles that are being checked for empty piles.
   * @return int: the number of empty piles
   */
  private int numEmpty(ArrayList<Pile> piles) {
    int counter = 0;
    for (Pile p : piles) {
      if (p.isEmpty()) {
        counter++;
      }
    }
    return counter;
  }
}








