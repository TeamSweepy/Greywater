
package com.teamsweepy.greywater.effect.quest;

import com.teamsweepy.greywater.entity.component.events.FetchEvent;
import com.teamsweepy.greywater.entity.component.events.GameEvent;
import com.teamsweepy.greywater.entity.item.Item;

import java.util.HashMap;


public class FetchQuest extends Quest {

	public HashMap<Class, Integer> winConditions;
	public HashMap<Class, Integer> currentConditions;
	
	public FetchQuest(String intro, String wait, String complete, String title){
		super(intro, wait, complete, title);
		winConditions = new HashMap<Class, Integer>();
		currentConditions = new HashMap<Class, Integer>();
	}
	
	@Override
	public void handleGameEvent(GameEvent ge) {
		if (ge.getClass() == FetchEvent.class && winConditions.containsKey(((FetchEvent) ge).acquired.getClass())) {
			FetchEvent ke = (FetchEvent) ge;
			if (currentConditions.containsKey(ke.acquired.getClass())) {
				currentConditions.put(ke.acquired.getClass(), currentConditions.get(ke.acquired.getClass()) + 1);
			} else {
				currentConditions.put(ke.acquired.getClass(), 1);
			}
			super.completeObjective();
		}
	}

	public void addWinCondition( Class<? extends Item> c, int count) {
		winConditions.put(c, count);
	}

	@Override
	public boolean isQuestActionOver() {
		boolean finished = true;
		for (Class condition : winConditions.keySet()) {
			if (currentConditions.get(condition) != winConditions.get(condition)) {
				finished = false;
			}
		}
		System.out.println("This quest is done = " + finished );
		return finished;
	}



}
