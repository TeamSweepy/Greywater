
package com.teamsweepy.greywater.effect;

import com.teamsweepy.greywater.entities.Mob;
import com.teamsweepy.greywater.entities.components.events.GameEvent;
import com.teamsweepy.greywater.entities.components.events.KillEvent;

import java.util.HashMap;


public class KillQuest extends Quest {

	public HashMap<Class, Integer> winConditions;
	public HashMap<Class, Integer> currentConditions;

	public KillQuest() {
		super();
		winConditions = new HashMap<Class, Integer>();
		currentConditions = new HashMap<Class, Integer>();
	}

	@Override
	public void handleGameEvent(GameEvent ge) {
		if (ge.getClass() == KillEvent.class && winConditions.containsKey(((KillEvent) ge).deadVictim.getClass())) {
			KillEvent ke = (KillEvent) ge;
			if (currentConditions.containsKey(ke.deadVictim.getClass())) {
				currentConditions.put(ke.deadVictim.getClass(), currentConditions.get(ke.deadVictim.getClass()) + 1);
			} else {
				currentConditions.put(ke.deadVictim.getClass(), 1);
			}
			super.completeObjective();
		}
	}

	public void addWinCondition( Class<? extends Mob> c, int count) {
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
