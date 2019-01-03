package com.twentysecondhero.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.twentysecondhero.game.Camera.CameraShake;
import com.twentysecondhero.game.Camera.GameCamera;
import com.twentysecondhero.game.Player.Player;
import com.twentysecondhero.game.Stage.GameStage;
import com.twentysecondhero.game.UI.UserInterface;
import com.twentysecondhero.game.Utility.GameData;

public class TSH extends Game {

	private GameCamera gameCamera;
	private GameStage gameStage;
	private Player player;
	private CameraShake cameraShake;
	private UserInterface userInterface;

	// TODO add game states
	// TODO add UI
	// TODO add menu's
	// TODO add player animations
	// X TODO place player on spawn position
	// X TODO add collectible pick-up
	// TODO make collectibles do something when picked up
	// TODO add more comments
	// X TODO optimize imports
	// X TODO keep camera in bounds
	// X TODO smooth camera
	// X TODO add camera shake (FIX IT)
	// TODO show menu on win
	// TODO show menu on lose
	// TODO add sounds
	// TODO add particle effects
	//      TODO - on player death | goal portal | collectible pick up |
	// X TODO downscale player
	// TODO make the game scalable on non 16:10 devices
	// X TODO clamp player within the world bounds
	// TODO change controls to be more responsive and less trigger happy
	// TODO fix win condition (bugs out now)
	// TODO fix collision detection based on draw scale (bugs out now)

	@Override
	public void create () {
		// Create the camera and focus it on the player
		gameCamera = new GameCamera();
		cameraShake = new CameraShake();
		userInterface = new UserInterface(null);

		// Set the current stage to the stage just created
		try {
			GameData.currentStage = gameStage = new GameStage("stage2.tmx", GameData.blockSize);
			player = gameStage.getPlayer();
			gameCamera.setTarget(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render () {
		// clear the screen with transparency
		Gdx.gl.glClearColor(0.8901f, 0.8392f, 0.6039f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// get the delta time for frame-rate independent updating
		float deltaTime = Gdx.graphics.getDeltaTime();

		// If there is a player, update it otherwise print an error
		if(player != null) {
			try {
				player.update(deltaTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// update the camera, clamping it and moving towards the player
		cameraShake.update(deltaTime, gameCamera.getCamera(), player.position);
		gameCamera.update();

		// set the TiledMapRenderer view based on what the camera sees, and render the map
		gameStage.update(gameCamera.getCamera());

		// render the graphics after all of the update logic is done
		renderGraphics();
    }

	/**
	 *
	 */
	private void renderGraphics(){
		if(player != null) player.render();
		if(userInterface != null) userInterface.render();
	}
}