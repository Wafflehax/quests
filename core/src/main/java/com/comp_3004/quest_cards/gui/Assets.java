package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import utils.Pair;

import java.util.AbstractMap;
import java.util.Map;

public class Assets {

  public static String GAME_BACKGROUNDS = "sprites/backgrounds.atlas";
  public static String GAME_SPRITES = "sprites/cards.atlas";
  public static String SKIN = "skins/uiskin.json";

  public static class Cards {

    public static String CARD_BACK = "card_back";

    public static class Allies {
      public static String KING_ARTHUR = "A_King_Arthur";
      public static String KING_PELLINORE = "A_King_Pellinore";
      public static String MERLIN = "A_Merlin";
      public static String QUEEN_GUINEVERE = "A_Queen_Guinevere";
      public static String QUEEN_ISEULT = "A_Queen_Iseult";
      public static String SIR_GALAHAD = "A_Sir_Galahad";
      public static String SIR_GAWAIN = "A_Sir_Gawain";
      public static String SIR_LANCELOT = "A_Sir_Lancelot";
      public static String SIR_PERCIVAL = "A_Sir_Percival";
      public static String SIR_TRISTAN = "A_Sir_Tristan";
    }

    public static class Story {

      public static String CHIVALROUS_DEED = "E_Chivalrous_Deed";
      public static String COURT_CALLED_CAMELOT = "E_Court_Called_Camelot";
      public static String KINGS_CALL_TO_ARMS = "E_Kings_Call_to_Arms";
      public static String KINGS_RECOGNITION = "E_Kings_Recognition";
      public static String PLAGUE = "E_Plague";
      public static String POX = "E_Pox";
      public static String PROSPERITY_THROUGHOUT_THE_REALM = "E_Prosperity_Throughout_the_Realm";
      public static String QUEENS_FAVOR = "E_Queens_Favor";

    }

    public static class Foe {

      public static String BLACK_KNIGHT = "F_Black_Knight";
      public static String BOAR = "F_Boar";
      public static String DRAGON = "F_Dragon";
      public static String EVIL_KNIGHT = "F_Evil_Knight";
      public static String GIANT = "F_Giant";
      public static String GREEN_KNIGHT = "F_Green_Knight";
      public static String MORDRED = "F_Mordred";
      public static String ROBBER_KNIGHT = "F_Robber_Knight";
      public static String SAXON_KNIGHT = "F_Saxon_Knight";
      public static String SAXONS = "F_Saxons";
      public static String THIEVES = "F_Thieves";
    }

    public static class Test {
      public static String TEST_OF_MORGAN_LE_FEY = "T_Test_of_Morgan_Le_Fey";
      public static String TEST_OF_TEMPTATION = "T_Test_of_Temptation";
      public static String TEST_OF_THE_QUESTING_BEAST = "T_Test_of_the_Questing_Beast";
      public static String TEST_OF_VALOR = "T_Test_of_Valor";
    }

    public static class Hero {
      public static String CHAMPION_KNIGHT = "R_Champion_Knight";
      public static String KNIGHT = "R_Knight";
      public static String SQUIRE = "R_Squire";
    }

    public static class Weapon {
      public static String BATTLE_AX = "W_Battle_ax";
      public static String DAGGER = "W_Dagger";
      public static String EXCALIBUR = "W_Excalibur";
      public static String HORSE = "W_Horse";
      public static String LANCE = "W_Lance";
      public static String SWORD = "W_Sword";
    }
  }
}
