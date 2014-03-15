
package com.teamsweepy.greywater.effect;

import com.teamsweepy.greywater.entities.Mob;
import com.teamsweepy.greywater.entities.components.GameEvent;
import com.teamsweepy.greywater.entities.components.KillEvent;

import java.util.HashMap;
import java.util.List;


public class KillQuest extends Quest {

	public HashMap<Class, Integer> winConditions;
	public HashMap<Class, Integer> currentConditions;

	public KillQuest(List<String> intro, List<String> waiting, List<String> completed, List<Quest> children, Mob doer, Mob giver) {
		super(intro, waiting, completed, children, doer, giver);
		winConditions = new HashMap<Class, Integer>();
		currentConditions = new HashMap<Class, Integer>();
	}

	public KillQuest(Mob doer, Mob giver) {
		super(doer, giver);
		winConditions = new HashMap<Class, Integer>();
		currentConditions = new HashMap<Class, Integer>();
	}

	@Override
	public boolean finished() {
		boolean isFinished = true;
		for (Class<Mob> kv : winConditions.keySet()) {
			if (!currentConditions.containsKey(kv) || winConditions.get(kv) > currentConditions.get(kv)) {
				isFinished = false;
				break;
			}
		}
		if(isFinished){
			super.questState = QUEST_STATUS_TURNIN;
		}
		return isFinished;
	}

	@Override
	public void handleGameEvent(GameEvent ge) {
		if (ge.getClass() == KillEvent.class) {
			KillEvent ke = (KillEvent) ge;
			if (currentConditions.containsKey(ke.deadVictim.getClass())) {
				currentConditions.put(ke.deadVictim.getClass(), currentConditions.get(ke.deadVictim.getClass()) + 1);
			} else {
				currentConditions.put(ke.deadVictim.getClass(), 1);
			}
		}
	}

	public void addWinCondition(Class<Mob> c, int count) {
		winConditions.put(c, count);
	}



}
