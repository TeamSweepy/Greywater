
package com.teamsweepy.greywater.entity;

import com.teamsweepy.greywater.effect.quest.EscortQuest;
import com.teamsweepy.greywater.effect.quest.FetchQuest;
import com.teamsweepy.greywater.effect.quest.Quest;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.item.weapons.TazerWrench;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.ui.gui.AIInventory;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.GUIComponent;
import com.teamsweepy.greywater.ui.gui.subgui.Button;
import com.teamsweepy.greywater.ui.gui.subgui.Dialog;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;


public class Tinkerer extends NPC {


	public Tinkerer(float x, float y, Level level) {
		super("Tinkerer", "Stand_Southeast", x, y, level);

	}

	@Override
	public void initDialogs() {
		welcomeDialog = new Dialog(800, 600, 300, 300, true);
		welcomeDialog.setTitle(getName());
		talkDialog = new Dialog(800, 600, 500, 500, true);
		mainMenu = new GUIComponent();
		mainMenuTitle = getName();
		questMenuTitle = "Quests";
		inventory = new AIInventory(this);

		Button talkButton = new Button(800, 650, 300, 50, "Talk") {

			@Override
			protected void clicked(boolean rightClick) {
				welcomeDialog.setVisible(false);
				talkDialog.setTitle("Chit-Chat");
				talkDialog.setVisible(true);
				talkDialog.setText(Gdx.files.internal("data/dialogue_text" + Globals.rand.nextInt(10) + ".txt").readString());
			}
		};

		Button questButton = new Button(800, 600, 300, 50, "Quests") {

			@Override
			protected void clicked(boolean rightClick) {
				welcomeDialog.setTitle(questMenuTitle);
				welcomeDialog.removeGUIComponent(mainMenu);
				welcomeDialog.addGUIComponent(questMenu);
				questMenu.setVisible(true);
			}
		};

		mainMenu.addGUIComponent(talkButton);
		mainMenu.addGUIComponent(questButton);
		welcomeDialog.addGUIComponent(mainMenu);
		questMenu = new GUIComponent();

		possibleQuests = new ArrayList<Quest>();

		FetchQuest craftQuest =
			new FetchQuest(Gdx.files.internal("data/craft_intro_text.txt").readString(), Gdx.files.internal("data/craft_waiting_text.txt").readString(), Gdx.files.internal(
				"data/craft_success_text.txt").readString(), "Junk");

		craftQuest.addWinCondition(TazerWrench.class, 1);


		EscortQuest sweepyQuest =
			new EscortQuest(Gdx.files.internal("data/intro_text.txt").readString(), Gdx.files.internal("data/waiting_text.txt").readString(), Gdx.files.internal("data/success_text.txt")
				.readString(), "Wandered Off");
		sweepyQuest.addWinCondition(Sweepy.class, this.getTileLocation());

		sweepyQuest.addWinCondition(Sweepy.class, this.getTileLocation());
		sweepyQuest.addPrereq(craftQuest);
		possibleQuests.add(sweepyQuest);
		possibleQuests.add(craftQuest);


		GUI.addGUIComponent(welcomeDialog);
		GUI.addGUIComponent(talkDialog);
	}

	public void receiveInteract(Mob interlocutor) {
		final Mob interloc = interlocutor;
		if (interlocutor.getClass() == Player.class) {

			questMenu.removeAllGUIComponent();
			int i = 0;
			for (Quest q : possibleQuests) {
				final Quest fq = q;
				if (q.isAvailableForStarting(interlocutor) || q.isAssignee(interlocutor)) {
					Button b = new Button(800, 650 - (50 * i), 300, 50, q.getTitle()) {

						@Override
						protected void clicked(boolean rightClick) {
							welcomeDialog.setVisible(false);
							talkDialog.setText(fq.getText(interloc));
							talkDialog.setTitle(fq.getTitle());
							talkDialog.setVisible(true);
							int state = fq.getQuestState(interloc);
							if (state == Quest.ASSIGNEE_STATUS_UNSTARTED)
								fq.startQuest(interloc);
							if (state == Quest.ASSIGNEE_STATUS_TURNIN)
								fq.turnIn(interloc);
						}
					};
					i++;
					questMenu.addGUIComponent(b);
				}
			}


			welcomeDialog.setTitle(mainMenuTitle);
			welcomeDialog.setVisible(true);

		}
	}


}
