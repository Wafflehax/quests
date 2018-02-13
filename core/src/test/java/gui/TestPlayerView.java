package gui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.comp_3004.quest_cards.gui.DeckView;
import com.comp_3004.quest_cards.gui.GameView;
import com.comp_3004.quest_cards.gui.PlayerView;
import junit.framework.TestCase;
import org.junit.Test;

public class TestPlayerView extends TestCase {

  @Test
  public void testSelfBounds() {

    GameView gameView = new GameView(new Skin());
    PlayerView playerView = gameView.playerView;

    assertEquals(playerView.getWidth(), 1900);
    assertEquals(playerView.getHeight(), 340);
    assertNotSame(playerView.getZIndex(), -1);
    assertEquals(playerView.getOriginX(), 10);
    assertEquals(playerView.getOriginY(), 10);
  }

  @Test
  public void testDeckViewBounds() {

    PlayerView playerView = new PlayerView();
    DeckView playerAdventureCards = playerView.playerAdventureCards;

    assertEquals(playerAdventureCards.getWidth(), 1870);
    assertEquals(playerAdventureCards.getHeight(), 300);
    assertNotSame(playerAdventureCards.getZIndex(), -1);
    assertEquals(playerAdventureCards.getOriginX(), 25);
    assertEquals(playerAdventureCards.getOriginY(), 25);
  }

  @Test
  public void testShieldsBounds(){

    PlayerView playerView = new PlayerView();
    Image playerAdventureCards = playerView.shields;

    assertEquals(playerAdventureCards.getWidth(), 200);
    assertEquals(playerAdventureCards.getHeight(), 300);
    assertNotSame(playerAdventureCards.getZIndex(), -1);
    assertEquals(playerAdventureCards.getOriginX(), 1800);
    assertEquals(playerAdventureCards.getOriginY(), 360);

  }
}
