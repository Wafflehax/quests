package com.comp_3004.quest_cards.cards;

public class CardSpawner {
	
	//methods
	public StoryCard spawnStoryCard(String name) {		//spawns a story card
		StoryCard c = null;
		
		//tournament spawners
		if(name == "camelot") { return new TournamentCard("Tournament at Camelot", 3); }
		else if(name == "orkney") { return new TournamentCard("Tournament at Orkney", 2); }
		else if(name == "tintagel") { return new TournamentCard("Tournament at Tintagel", 1); }
		else if(name == "york") { return new TournamentCard("Tournament at York", 0); }
		
		//event spawners
		else if(name == "kingsRecognition") { return new EventCard("King's Recognition"); }
		else if(name == "queensFavor") { return new EventCard("Queen's Favor"); }
		else if(name == "courtCalledToCamelot") { return new EventCard("Court Called to Camelot"); }
		else if(name == "pox") { return new EventCard("Pox"); }
		else if(name == "plauge") { return new EventCard("Plague"); }
		else if(name == "chivalrousDeed") { return new EventCard("Chivalrous Deed"); }
		else if(name == "prosperityThroughoutTheRealms") { return new EventCard("Prosperity Throughout the Realms"); }
		else if(name =="kingsCallToArms") { return new EventCard("King's Call to Arms"); }
		
		//quest spawners
		else if(name == "searchForTheHolyGrail") {return new QuestCard("Search for the Holy Grail", 5, "all"); }
		else if(name == "testOfTheGreenKnight") { return new QuestCard("Test of the Green Knight", 4, "Green Knight"); }
		else if(name == "searchForTheQuestingBeast") { return new QuestCard("Search for the Questing Beast", 4); }
		else if(name == "defendTheQueensHonor") { return new QuestCard("Defend the Queen's Honor", 4, "all"); }
		else if(name == "rescueTheFairMaiden") { return new QuestCard("Rescue the Fair Maiden", 3, "Black Knight");}
		else if(name == "journeyThroughTheEnchantedForest") { return new QuestCard("Journey Through the Enchanted Forest", 3, "Evil Knight"); }
		else if(name == "slayTheDragon") { return new QuestCard("Slay the Dragon", 3, "Dragon"); }
		else if(name == "vanquishKingArthursEnemies") { return new QuestCard("Vanquish King Arthur's Enemies", 3);}
		else if(name == "boarHunt") { return new QuestCard("Boar Hunt", 2, "Boar"); }
		else if(name == "repelTheSaxonInvaders") { return new QuestCard("Repel the Saxon Invaders", 2, "allSaxons"); }
		
		return c;
	}
	
	public AdventureCard spawnAdventureCard(String name) {
		AdventureCard c = null;
		
		//foe spawners
		if(name == "thieves") { return new FoeCard("Thieves", 5);}
		else if(name == "saxonKnight") { return new FoeCard("Saxon Knight", 15, 25); }
		else if(name == "robberKnight") { return new FoeCard("Robber Knight", 15); }
		else if(name =="evilKnight") { return new FoeCard("Evil Knight", 20, 30); }
		else if(name == "saxons") { return new FoeCard("Saxons", 10, 20); }
		else if(name == "boar") {return new FoeCard("Boar", 5, 15); }
		else if(name == "mordred") { return new FoeCard("Mordred", 30); }
		else if(name == "blackKnight") {return new FoeCard("Black Knight", 25, 35); }
		else if(name == "giant") { return new FoeCard("Giant", 40); }
		else if(name == "greenKnight") { return new FoeCard("Green Knight", 25, 40); }
		else if(name == "dragon") { return new FoeCard("Dragon", 50, 70); }
		
		//weapon spawners
		else if(name == "horse") { return new WeaponCard("Horse", 10); }
		else if(name == "sword") { return new WeaponCard("Sword", 10); }
		else if(name == "excalibur") { return new WeaponCard("Excalibur", 30); }
		else if(name == "lance") { return new WeaponCard("Lance", 20); }
		else if(name == "dagger") { return new WeaponCard("Dagger", 5); }
		else if(name == "battleAx") { return new WeaponCard("Battle-Ax", 15); }
		
		//ally spawners
		else if(name == "gawain") { return new AllyCard("Sir Gawain", 10, 0); }
		else if(name == "pellinore") { return new AllyCard("King Pellinore", 10, 0); }
		else if(name == "percival") { return new  AllyCard("Sir Percival", 5, 0); }
		else if(name == "tristan") { return new AllySubjectObserver("Sir Tristan", 10, 0, 20, 0, new String[] {"Queen Iseult"}); }
		else if(name == "arthur") { return new AllyCard("King Arthur", 10,2); }
		else if(name == "guinevere") { return new AllyCard("Queen Guinevere", 0, 3); }
		else if(name == "merlin") { return new AllyCard("Merlin", 0, 0); }
		else if(name == "iseult") { return new AllySubjectObserver("Queen Iseult", 0, 2, 0, 4, new String[] {"Sir Tristan"}); }
		else if(name == "lancelot") { return new AllyCard("Sir Lancelot", 15, 0); }
		else if(name == "galahad" ) { return new AllyCard("Sir Galahad", 15, 0); }
		
		//test spawners
		else if(name == "questingBeast") { return new TestCard("Test of the Questing Beast"); }
		else if(name == "temptation") { return new TestCard("Test of Temptation"); }
		else if(name == "valor") { return new TestCard("Test of Valor"); }
		else if(name == "morganLeFey") { return new TestCard("Test of Morgan Le Fey"); }
		
		//amour spawner
		else if(name =="amour") { return new AmourCard(); }
		
		return c;
	}

}
