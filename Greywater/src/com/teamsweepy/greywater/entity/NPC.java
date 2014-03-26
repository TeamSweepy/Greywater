
package com.teamsweepy.greywater.entity;

import java.util.ArrayList;

import com.teamsweepy.greywater.effect.quest.KillQuest;
import com.teamsweepy.greywater.effect.quest.Quest;
import com.teamsweepy.greywater.entity.component.Hitbox;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.ui.gui.AIInventory;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.GUIComponent;
import com.teamsweepy.greywater.ui.gui.subgui.Button;
import com.teamsweepy.greywater.ui.gui.subgui.Dialog;
import com.teamsweepy.greywater.entity.Player;


public class NPC extends Mob {

	ArrayList<Quest> possibleQuests;
	Dialog welcomeDialog;
	Dialog talkDialog;

	GUIComponent mainMenu;
	String mainMenuTitle;


	GUIComponent questMenu;
	String questMenuTitle;


	public NPC(float x, float y, Level level) {
		super();
		name = "NPC_South";
		physicsComponent = new Hitbox(x * 50 + 25, y * 50 + 25, 35, 35, 0 * 50);
		welcomeDialog = new Dialog(800, 600, 300, 300, true);
		welcomeDialog.setTitle(getName());
		talkDialog = new Dialog(800, 600, 500, 500, true);
		this.graphicsComponent = new Sprite(getName());
		world = level;
		friendly = true;
		mainMenu = new GUIComponent();
		mainMenuTitle = getName();
		questMenuTitle = "Quests";
		inventory = new AIInventory(this);

		Button talkButton = new Button(800, 650, 300, 50, "Talk") {

			@Override
			protected void clicked() {
				welcomeDialog.setVisible(false);
			}
		};

		Button questButton = new Button(800, 600, 300, 50, "Quests") {

			@Override
			protected void clicked() {
				welcomeDialog.setTitle(questMenuTitle);
				welcomeDialog.removeGUIComponent(mainMenu);
				welcomeDialog.addGUIComponent(questMenu);
				questMenu.setVisible(true);
			}
		};

		mainMenu.addGUIComponent(talkButton);
		mainMenu.addGUIComponent(questButton);

		questMenu = new GUIComponent();


		//shitty quest test code
		possibleQuests = new ArrayList<Quest>();
		KillQuest watch1 = new KillQuest("HI DO THIS", "WAITING", "NO MORE WAITING", "STUPID BULLSHIT");
		watch1.addWinCondition(Watchman.class, 2);
		KillQuest watch2 = new KillQuest();
		watch2.addPrereq(watch1);
		possibleQuests.add(watch1);
		possibleQuests.add(watch2);

		GUI.addGUIComponent(welcomeDialog);
		GUI.addGUIComponent(talkDialog);

	}

	public void tick(float deltaTime) {

	}

	@Override
	protected void getInput() {} //NPCs don't think

	@Override
	protected void attack(Mob enemy) {} //NPCs don't attack

	public void interact(Mob interlocutor) {
		final Mob interloc = interlocutor;
		if (interlocutor.getClass() == Player.class) {

			//			Dialog d = new Dialog(500, 500, 600, 300);
			//			d.setText(possibleQuests.get(0).getText(interlocutor));
			//			d.setTitle("SAMPLE QUEST");
			//			d.setVisible(true);

			//			GUI.addGUIComponent();

			questMenu.removeAllGUIComponent();
			int i = 0;
			for (Quest q : possibleQuests) {
				final Quest fq = q;
				if (q.isAvailableForStarting(interlocutor) || q.isAssignee(interlocutor)) {
					Button b = new Button(800, 650 - (50 * i), 300, 50, q.getTitle()) {

						@Override
						protected void clicked() {
							welcomeDialog.setVisible(false);
							talkDialog.setText(fq.getText(interloc));
							talkDialog.setTitle(fq.getTitle());
							talkDialog.setVisible(true);
							int state = fq.getQuestState(interloc);
							if(state == Quest.ASSIGNEE_STATUS_UNSTARTED)
							fq.startQuest(interloc);
							if(state == Quest.ASSIGNEE_STATUS_TURNIN)
								fq.turnIn(interloc);
						}
					};
					i++;
					questMenu.addGUIComponent(b);
				}
			}

			welcomeDialog.removeGUIComponent(mainMenu);
			welcomeDialog.removeGUIComponent(questMenu);
			welcomeDialog.addGUIComponent(mainMenu);
			welcomeDialog.setTitle(mainMenuTitle);
			welcomeDialog.setVisible(true);

		}

	}

	@Override
	public boolean interact() {
		return false;
	}

	@Override
	public void executeAttack() {} //NPCs don't attack

}
