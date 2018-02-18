package utils;

import com.comp_3004.quest_cards.cards.QuestCardSubject;

public interface Observer {
	
	public void update();
	public void setSubject(Subject s);

}
