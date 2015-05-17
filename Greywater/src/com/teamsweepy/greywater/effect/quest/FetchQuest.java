
package com.teamsweepy.greywater.effect.quest;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.component.events.FetchEvent;
import com.teamsweepy.greywater.entity.component.events.GameEvent;
import com.teamsweepy.greywater.entity.item.Item;

import java.util.HashMap;
import java.util.Map.Entry;


public class FetchQuest extends Quest {

	public HashMap<Class<? extends Item>, Integer> winConditions;
	public HashMap<Class<? extends Item>, Integer> currentConditions;

	public FetchQuest(String intro, String wait, String complete, String title) {
		super(intro, wait, complete, title);
		winConditions = new HashMap<Class<? extends Item>, Integer>();
		currentConditions = new HashMap<Class<? extends Item>, Integer>();
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

	public void addWinCondition(Class<? extends Item> c, int count) {
		winConditions.put(c, count);
	}

	@Override
	public boolean isQuestActionOver() {
		boolean finished = true;
		for (Entry<Class<? extends Item>, Integer> condition : winConditions.entrySet()) {
			Integer count = currentConditions.get(condition);
			if (count != null && count < winConditions.get(condition)) {
				finished = false;
			}
		}
		System.out.println("This quest is done = " + finished);
		return finished;
	}

	@Override
	public boolean isQuestActionOverAlready() {
		for (Mob quester : assignees.keySet()) {
			currentConditions = quester.getInventory().updateItemCount(currentConditions, winConditions);
		}
		return isQuestActionOver();
	}



}
