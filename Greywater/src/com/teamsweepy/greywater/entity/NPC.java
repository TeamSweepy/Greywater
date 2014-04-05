
package com.teamsweepy.greywater.entity;

import com.teamsweepy.greywater.effect.quest.Quest;
import com.teamsweepy.greywater.entity.component.Hitbox;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.ui.gui.GUIComponent;
import com.teamsweepy.greywater.ui.gui.subgui.Dialog;

import java.util.ArrayList;


public abstract class NPC extends Mob {


	protected ArrayList<Quest> possibleQuests;
	protected Dialog welcomeDialog;
	protected GUIComponent mainMenu;
	protected String mainMenuTitle;

	protected Dialog talkDialog;
	protected GUIComponent questMenu;
	protected String questMenuTitle;

	public NPC() {}

	public NPC(String name, String ident, float x, float y, Level level) {
		super();
		this.name = name;
		physicsComponent = new Hitbox(x * 50 + 25, y * 50 + 25, 35, 35, 0 * 50, true);
		this.graphicsComponent = new Sprite(name, ident, true);
		graphicsComponent.setImage(.7f, ident, Sprite.LOOP_PINGPONG);
		world = level;
		friendly = true;
		possibleQuests = new ArrayList<Quest>();
		initDialogs();
	}

	public abstract void initDialogs();

	public void tick(float deltaTime) {
		graphicsComponent.tick(deltaTime);
	}

	@Override
	protected void attack(Mob enemy) {} //NPCs don't attack

	@Override
	public void executeAttack() {} //NPCs don't attack

	@Override
	protected void getInput() {} //NPCs don't think

	@Override
	public boolean sendInteract() { //NPCs initiate conversation
		return false;
	}
	
	@Override
	public void changeHP(float dmg){}


}
