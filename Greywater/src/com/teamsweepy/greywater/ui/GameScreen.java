/**
 * Used to control logic and rendering for the in-game action.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.Engine;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.entity.level.Town;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.math.Point2I;
import com.teamsweepy.greywater.ui.gui.Cursor;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.HUD;
import com.teamsweepy.greywater.ui.gui.Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.teamsweepy.greywater.utils.IO;

public class GameScreen extends EngineScreen {
	Sprite fog;


	//testvar
	Level currentLevel;
	HUD playerHUD;
	Inventory playerInventory;
	Cursor playerCursor;
	boolean ticking = true;


    // TODO: CLEANUP
//    private String vertexShader, fragmentShader;
//    private ShaderProgram shader;

	public GameScreen(Engine eng) {
        super(eng);

        // Load the vertex and fragment shaders
//        System.out.println(ShaderProgram.POSITION_ATTRIBUTE);
//        System.out.println(ShaderProgram.COLOR_ATTRIBUTE);
//        System.out.println(ShaderProgram.TEXCOORD_ATTRIBUTE);

        // TODO: Uhmmmm, what....?
		while (AssetLoader.tick() < 1f) {
			// do nothing TODO remove later
		}
		fog = new Sprite("light", true);
		Mob TestTavishMob = Player.initLocalPlayer(4f, 90f, null);


		Level town = new Town("data/map/Greywater.tmx");
//		town.addMobAtLoc(TestTavishMob, new Point2I(4, 9));
		town.addMobAtLoc(TestTavishMob, new Point2I(9, 13));
		currentLevel = town;
		Level dungeon = new Level("data/map/Dungeon.tmx");
		currentLevel.setSwapLevel(dungeon);
		dungeon.setSwapLevel(town);
		
		
		playerInventory = new Inventory(Player.getLocalPlayer());
		playerHUD = new HUD();
		playerCursor = new Cursor();

        Player.getLocalPlayer().setInventory(playerInventory);
        Player.getLocalPlayer().getInventory().dumpSlots();

		GUI.addGUIComponent(playerHUD, GUI.TOP_LAYER);
		GUI.addGUIComponent(playerInventory);
		GUI.addGUIComponent(playerCursor, GUI.TOP_LAYER);

		this.hide();
	}

	@Override
	public void render(float delta) {
		//scale from 1600x900 to whatever user screen is set to and clear graphics
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//translate sprite batch like camera
		Point2F offsetPoint = Camera.getDefault().getTranslatedMatrix();
		engine.gameBatch.setProjectionMatrix(engine.gameBatch.getProjectionMatrix().translate(offsetPoint.x, offsetPoint.y, 0));

//        engine.gameBatch.setShader(shader);

		engine.gameBatch.begin();// start render
//        shader.begin(); // Bind before the shader begins

		currentLevel.render(engine.gameBatch);

//        shader.end();
		engine.gameBatch.end();// end render
		
		engine.guiBatch.begin();
		fog.render(engine.guiBatch, 0,0);
		GUI.render(engine.guiBatch);
		engine.guiBatch.end();
	}

    @Override
	public void tick(float delta) {
		if (!ticking)
			return;
		currentLevel = currentLevel.getCurrentLevel();
		currentLevel.tick(delta);
		GUI.tick(delta);

        // Check if the ESC key is pressed
//        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
//            Engine.inGame ^= true;
//        }


	}

	@Override
	public void show() {
		playerCursor.setVisible(true);
		playerHUD.setVisible(true);
	}

	@Override
	public void hide() {
		GUI.setVisibility(false);
	}

	@Override
	public void pause() {
		ticking = false;
		playerCursor.setTicking(false);
		playerHUD.setTicking(false);
		playerInventory.setTicking(false);

	}

	@Override
	public void resume() {
		ticking = true;
		playerCursor.setTicking(true);
		playerHUD.setTicking(true);
		playerInventory.setTicking(true);
	}

	/* **************** PROBABLY USELESS METHODS ********************* */

	@Override
	public void resize(int width, int height) {
//        shader.begin();
//        shader.setUniformf("resolution", width, height);
//        shader.end();
    }

	@Override
	public void dispose() {}


    // TODO: Check for more GUI components
    public void closeGUI() {
        Player.getLocalPlayer().getInventory().setVisible(false);
    }

    public boolean canClose() {
        return !Player.getLocalPlayer().getInventory().isVisible();
    }
}
