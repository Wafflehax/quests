package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import utils.Pair;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class Assets {

  public static final String GAME_BACKGROUNDS = "sprites/backgrounds.atlas";
  public static final String GAME_SPRITES = "sprites/gameSprites.atlas";
  public static final String SKIN = "skins/uiskin.json";

  public static class MiscSprites {

    public static final String FLAG_ARROW = "flag_arrow";
    public static final String FLAG_BODY = "flag_body";
    public static final String DECK_ICON = "deck_icon";
    public static final String SHIELD = "shield";
  }

  public static class Cards {


    public static final String CARD_BACK = "card_back";

    //CardSpawnerMap.

    public static class Allies {
      public static final String AMOUR = "Amour";
      public static final String KING_ARTHUR = "A_King_Arthur";
      public static final String KING_PELLINORE = "A_King_Pellinore";
      public static final String MERLIN = "A_Merlin";
      public static final String QUEEN_GUINEVERE = "A_Queen_Guinevere";
      public static final String QUEEN_ISEULT = "A_Queen_Iseult";
      public static final String SIR_GALAHAD = "A_Sir_Galahad";
      public static final String SIR_GAWAIN = "A_Sir_Gawain";
      public static final String SIR_LANCELOT = "A_Sir_Lancelot";
      public static final String SIR_PERCIVAL = "A_Sir_Percival";
      public static final String SIR_TRISTAN = "A_Sir_Tristan";
    }

    public static class Story {

      public static final String CHIVALROUS_DEED = "E_Chivalrous_Deed";
      public static final String COURT_CALLED_CAMELOT = "E_Court_Called_Camelot";
      public static final String KINGS_CALL_TO_ARMS = "E_Kings_Call_to_Arms";
      public static final String KINGS_RECOGNITION = "E_Kings_Recognition";
      public static final String PLAGUE = "E_Plague";
      public static final String POX = "E_Pox";
      public static final String PROSPERITY_THROUGHOUT_THE_REALM = "E_Prosperity_Throughout_the_Realm";
      public static final String QUEENS_FAVOR = "E_Queens_Favor";

    }

    public static class Foe {

      public static final String BLACK_KNIGHT = "F_Black_Knight";
      public static final String BOAR = "F_Boar";
      public static final String DRAGON = "F_Dragon";
      public static final String EVIL_KNIGHT = "F_Evil_Knight";
      public static final String GIANT = "F_Giant";
      public static final String GREEN_KNIGHT = "F_Green_Knight";
      public static final String MORDRED = "F_Mordred";
      public static final String ROBBER_KNIGHT = "F_Robber_Knight";
      public static final String SAXON_KNIGHT = "F_Saxon_Knight";
      public static final String SAXONS = "F_Saxons";
      public static final String THIEVES = "F_Thieves";
    }

    public static class Test {
      public static final String TEST_OF_MORGAN_LE_FEY = "T_Test_of_Morgan_Le_Fey";
      public static final String TEST_OF_TEMPTATION = "T_Test_of_Temptation";
      public static final String TEST_OF_THE_QUESTING_BEAST = "T_Test_of_the_Questing_Beast";
      public static final String TEST_OF_VALOR = "T_Test_of_Valor";
    }

    public static class Hero {
      public static final String CHAMPION_KNIGHT = "R_Champion_Knight";
      public static final String KNIGHT = "R_Knight";
      public static final String SQUIRE = "R_Squire";
    }

    public static class Weapon {
      public static final String BATTLE_AX = "W_Battle_ax";
      public static final String DAGGER = "W_Dagger";
      public static final String EXCALIBUR = "W_Excalibur";
      public static final String HORSE = "W_Horse";
      public static final String LANCE = "W_Lance";
      public static final String SWORD = "W_Sword";
    }


  }

  public static class Strings {

    public static class Buttons {
      public static final String ACKNOWLEDGE = "Continue";
      public static final String TRUE = "Yes";
      public static final String FALSE = "No";
    }
  }
}
