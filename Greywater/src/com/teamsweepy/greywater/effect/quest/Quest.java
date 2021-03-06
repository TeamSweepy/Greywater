
package com.teamsweepy.greywater.effect.quest;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.component.events.GameEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class Quest implements GameEventListener {

	public static final int ASSIGNEE_STATUS_UNSTARTED = 0;
	public static final int ASSIGNEE_STATUS_INPROGRESS = 1;
	public static final int ASSIGNEE_STATUS_TURNIN = 2;
	public static final int ASSIGNEE_STATUS_COMPLETED = 3;

	protected HashMap<Mob, Integer> assignees;
	private ArrayList<Quest> prereqs;
	//	private ArrayList<Quest> objectives;

	private String introText = "LOLOL DO A THING";
	private String waitText = "LOLOL WHY HAVENT YOU DONE A THING";
	private String completeText = "LOLOL YOU DID A THING";
	private String questTitle = "LDAT";

	private boolean closedQuest;
	private boolean completeQuest;

	public Quest() {
		assignees = new HashMap<Mob, Integer>();
		prereqs = new ArrayList<Quest>();
	}

	public Quest(String intro, String wait, String complete, String title) {
		introText = intro;
		waitText = wait;
		completeText = complete;
		questTitle = title;
		assignees = new HashMap<Mob, Integer>();
		prereqs = new ArrayList<Quest>();
	}

	/** Indicates if a player can START a quest. False if they are on the quest already. */
	public boolean isAvailableForStarting(Mob assignee) {
		if (closedQuest || assignees.keySet().contains(assignee))
			return false;
		boolean available = true;
		if (prereqs != null) {
			for (int i = 0; i < prereqs.size(); i++) {
				if (prereqs.get(i).getQuestState(assignee) != ASSIGNEE_STATUS_COMPLETED && !prereqs.get(i).completeQuest) {
					available = false;
					break;
				}
				if (prereqs.get(i).closedQuest) {
					prereqs.remove(i);
					i--;
				}
			}
		}
		return available;
	}

	public void turnIn(Mob finishedMob) {
		if (assignees.get(finishedMob) == ASSIGNEE_STATUS_TURNIN) {
			if (assignees.keySet().size() == 1) {
				closedQuest = true;
				completeQuest = true;
			}
			assignees.put(finishedMob, ASSIGNEE_STATUS_COMPLETED);
			//assignee.questlog.remove(this)
		}
	}

	public int getQuestState(Mob assignee) {
		if (assignees.containsKey(assignee))
			return assignees.get(assignee);
		return ASSIGNEE_STATUS_UNSTARTED;
	}

	public void addPrereq(Quest prereq) {
		prereqs.add(prereq);
	}

	public void startQuest(Mob assignee) {
		if (!assignees.keySet().contains(assignee) && isAvailableForStarting(assignee)) {
			assignee.addGameListener(this);
			assignees.put(assignee, ASSIGNEE_STATUS_INPROGRESS);
			if(isQuestActionOverAlready()){
				completeObjective();
			}
			//TODO assignee.questlog.add(this);
		}
	}

	public boolean isAssignee(Mob potential) {
		return assignees.containsKey(potential) && getQuestState(potential) != ASSIGNEE_STATUS_COMPLETED;
	}

	protected void completeObjective() {
		if (isQuestActionOver()) {
			for (Mob m : assignees.keySet()) {
				assignees.put(m, ASSIGNEE_STATUS_TURNIN);
			}
			closedQuest = true;
		}
	}

	public String getText(Mob assignee) {
		int mobStatus = getQuestState(assignee);
		switch (mobStatus) {
			case ASSIGNEE_STATUS_UNSTARTED:
				return introText;
			case ASSIGNEE_STATUS_INPROGRESS:
				return waitText;
			case ASSIGNEE_STATUS_TURNIN:
				return completeText;
		}
		return null;
	}

	public String getTitle() {
		return questTitle;
	}

	public abstract boolean isQuestActionOver();
	public abstract boolean isQuestActionOverAlready();



}
