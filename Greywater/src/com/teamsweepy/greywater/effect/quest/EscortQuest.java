
package com.teamsweepy.greywater.effect.quest;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.component.events.EscortEvent;
import com.teamsweepy.greywater.entity.component.events.GameEvent;
import com.teamsweepy.greywater.math.Point2I;

import java.util.HashMap;


public class EscortQuest extends Quest {

	public HashMap<Class, Point2I> winConditions;

	public EscortQuest() {
		super();
		winConditions = new HashMap<Class, Point2I>();
	}

	public EscortQuest(String intro, String wait, String complete, String title) {
		super(intro, wait, complete, title);
		winConditions = new HashMap<Class, Point2I>();
	}

	@Override
	public void handleGameEvent(GameEvent ge) {
		if (ge.getClass() == EscortEvent.class && ((EscortEvent) ge).escort != null && winConditions.containsKey(((EscortEvent) ge).escort.getClass())) {
			EscortEvent ee = (EscortEvent) ge;

			if (ee.escort.getTileLocation().distance(winConditions.get(ee.escort.getClass())) < 20) {
				winConditions.remove(ee.escort.getClass());
				super.completeObjective();
			}
		}
	}

	@Override
	public boolean isQuestActionOver() {
		return winConditions.isEmpty();
	}

	public void addWinCondition(Class<? extends Mob> objective, Point2I goalTile) {
		winConditions.put(objective, goalTile);
	}


}
