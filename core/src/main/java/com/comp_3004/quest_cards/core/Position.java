package com.comp_3004.quest_cards.core;

public class Position{
	
	protected Player leftOfMe;
	protected Player rightOfMe;
	
	public Position(Player left, Player right) {
		leftOfMe = left;
		rightOfMe = right;
	}
	
}