
package com.teamsweepy.greywater.effect;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.Mob;
import com.teamsweepy.greywater.entities.components.GameEventListener;

import java.util.ArrayList;
import java.util.List;


public abstract class Quest implements  GameEventListener{

	public final int QUEST_STATUS_UNSTARTED = 0;
	public final int QUEST_STATUS_INPROGRESS = 1;
	public final int QUEST_STATUS_TURNIN = 2;
	public final int QUEST_STATUS_COMPLETED = 3;

	protected ArrayList<String> introText;
	protected ArrayList<String> waitingText;
	protected ArrayList<String> completedText;
	protected ArrayList<Quest> subQuests;

	protected int questState = 0;
	protected int questType = 4;
	protected Mob questAssignee;
	protected Mob questGiver;

	/** Test Quest */
	public Quest(Mob doer, Mob giver) {
		introText = new ArrayList<String>();
		waitingText = new ArrayList<String>();
		completedText = new ArrayList<String>();
		introText.add("Go do some bullshit!");
		waitingText.add("You done my pointless bullshit yet?");
		completedText.add("Congratulations, you just let a computer boss you around. Chump.");
		questAssignee = doer;
		questGiver = giver;
	}

	/** Simple Quest */
	public Quest(String intro, String waiting, String completed, Mob doer, Mob giver) {
		introText = new ArrayList<String>();
		waitingText = new ArrayList<String>();
		completedText = new ArrayList<String>();
		introText.add(intro);
		waitingText.add(waiting);
		completedText.add(completed);
		questAssignee = doer;
		questGiver = giver;
	}

	/** Real Constructor, supports subquests */
	public Quest(List<String> intro, List<String> waiting, List<String> completed, List<Quest> children, Mob doer, Mob giver) {
//		introText = new ArrayList<String>();
//		waitingText = new ArrayList<String>();
//		completedText = new ArrayList<String>();
//		subQuests = new ArrayList<Quest>();
//		introText.addAll(intro);
//		waitingText.addAll(waiting);
//		completedText.addAll(completed);
//		if (children != null)
//			subQuests.addAll(children);
		questAssignee = doer;
		questGiver = giver;
	}

	/** Return integer marking quest's status, see static ints in this class - QUEST_*** */
	public int getQuestState() {
		return questState;
	}

	/** Get the next bit of dialog associated with this quest */
	public String getCurrentQuestText() {
		if (subQuests != null && subQuests.size() > 0) { //if there are subQuests
			if(subQuests.get(0).questState == QUEST_STATUS_COMPLETED){
				subQuests.remove(0);
				return getCurrentQuestText();
			}else {
				return subQuests.get(0).getCurrentQuestText();
			}
		} else { //if no subquests
			switch (questState) {
				case QUEST_STATUS_UNSTARTED:
					return introText.get(Globals.rand.nextInt(introText.size()));
				case QUEST_STATUS_INPROGRESS:
					return waitingText.get(Globals.rand.nextInt(waitingText.size()));
				case QUEST_STATUS_TURNIN:
					return completedText.get(Globals.rand.nextInt(completedText.size()));
				case QUEST_STATUS_COMPLETED:
					return null;
			}//end switch
		}
		return null;
	}
	
	public abstract boolean finished();

}
