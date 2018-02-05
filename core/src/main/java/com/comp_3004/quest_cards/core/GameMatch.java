package com.comp_3004.quest_cards.core;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.core.GameModel.cardModes;

public abstract class GameMatch{
	public static enum mtype { tour,quest };
	public mtype type;
	protected volatile cardModes cardMode = cardModes.NONE;
	
	
	// getter / setter
	public cardModes getcardMode() { return cardMode;}
	
	
	// common methods in Tournaments and Quests
	public abstract void playCard(AdventureCard c);
	public abstract void discardCard(AdventureCard c);
	public abstract void run();
	public abstract void setParticipation(boolean b);
	public abstract Players getPlayers();
	
}