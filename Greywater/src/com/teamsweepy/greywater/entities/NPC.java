
package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.effect.KillQuest;
import com.teamsweepy.greywater.effect.Quest;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.components.ai.PathfinderMotor;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.subgui.Dialog;

import java.util.ArrayList;


public class NPC extends Mob {
	
	ArrayList<Quest> possibleQuests;

	public NPC(float x, float y, Level level) {
		super();
		name = "NPC_South";
		physicsComponent = new Hitbox(x * 50 + 25, y * 50 + 25, 35, 35, 0 * 50);
		this.graphicsComponent = new Sprite(getName());
		graphicsComponent.addAnimListener(this);
		world = level;
		pather = new PathfinderMotor(PathfinderMotor.Method.POTENTIAL_FIELD);
		friendly = true;
		pather.updateMap(level);
		
		
		//shitty quest test code
		possibleQuests = new ArrayList<Quest>();
		KillQuest watch1 = new KillQuest();
		watch1.addWinCondition(Watchman.class, 2);
		KillQuest watch2 = new KillQuest();
		watch2.addPrereq(watch1);
		possibleQuests.add(watch1);
		possibleQuests.add(watch2);
		
	}
	
	public void tick(float deltaTime){
		
	}

	@Override
	protected void getInput() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void attack(Mob enemy) {
		// TODO Auto-generated method stub

	}

	public void interact(Mob interlocutor) {
		
		if(interlocutor.getClass() == Player.class){

			Dialog d = new Dialog(500, 500, 600, 300);
			d.setText(possibleQuests.get(0).getText(interlocutor));
			d.setTitle("SAMPLE QUEST");
			d.setVisible(true);
			GUI.addGUIComponent(d);
			possibleQuests.get(0).startQuest(interlocutor);
			if(possibleQuests.get(0).getQuestState(interlocutor) == Quest.ASSIGNEE_STATUS_TURNIN){
				possibleQuests.get(0).turnIn(interlocutor);
			}
			
		}
		
	}

	@Override
	public boolean interact() {
		// TODO Auto-generated method stub
		return false;
	}

}
