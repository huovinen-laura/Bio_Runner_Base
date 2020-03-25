package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.*;
import com.mygdx.game.collectibles.TestCollectible;
import com.mygdx.game.gamestate.LifeCounter;

import java.util.ArrayList;

public class BallGame extends ScreenAdapter {
	BioRunnerGame game;
    SpriteBatch gameBatch;
    BitmapFont font;

	public static float worldSpeed = -1f;
	public static ShitCollection collectedStuffList = new ShitCollection();
	public ScrollingBackground scrollingBackground;
	public static World world = new World(new Vector2(0, -5f), true);
	private LifeCounter lifeCounter;
	public static int playerScore;

	public static float WORLD_WIDTH = 8;
	public static float WORLD_HEIGHT = 4;
	private ArrayList<GameObject> collectables;
	private ArrayList<GameObject> obstacles;
	private Player ball;
	private Waypoint waypoint;
	private boolean lostGame;
	private boolean reachedCheckpoint;
    String str1;

	OrthographicCamera camera = new OrthographicCamera();
	private Box2DDebugRenderer debugRenderer;

	public BallGame (BioRunnerGame game) {
		this.game = game;
		Gdx.app.log("sf","Ballgame constructor");

		game.batch = new SpriteBatch();
		this.font = game.font;
		collectables = new ArrayList<GameObject>();
		obstacles = new ArrayList<>();
		this.lifeCounter = new LifeCounter();
		ball = new Player(world);
		scrollingBackground = new ScrollingBackground(worldSpeed);
		createGround();
		debugRenderer = new Box2DDebugRenderer();
		moveCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
	}

	@Override
	public void show() {
        game.batch = new SpriteBatch();
        this.gameBatch = new SpriteBatch();
		waypoint = new Waypoint(20f);
		this.reachedCheckpoint = false;
		this.lostGame = false;
		this.ball.setJustChangedScreen(true);
		Gdx.app.log("sdf","ballgame show");
		this.world.setContactListener(new B2dContactListener());
		this.world.setContactFilter(new B2dContactFilter());
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keyCode) {
				if (keyCode == Input.Keys.ENTER) {
					game.setEndScreen();
				}
				return true;
			}
		});
	}

	@Override
	public void render (float delta) {
		this.gameBatch.setProjectionMatrix(camera.combined);
		clearScreen();

		if(!this.ball.Move()) { // checks if lives is zero
			this.lostGame = true;
			LifeCounter.setLives(3);
		}

        this.gameBatch.begin();
		scrollingBackground.updateAndRender(Gdx.graphics.getDeltaTime(), this.gameBatch);
		doPhysicsStep(Gdx.graphics.getDeltaTime());
		ball.Draw(this.gameBatch);

		for (int i =0 ; i < this.collectables.size();i++) {
			this.collectables.get(i).Draw(this.gameBatch);
			if( !this.collectables.get(i).Move()) {
				this.collectables.get(i).dispose();
				this.collectables.remove(i);
			}
		}


		if(BallGame.collectedStuffList.isNextCollectibleComing(this.collectables.size())) {
			Gdx.app.log("Ballgame","added new collectible");
				this.collectables.add(BallGame.collectedStuffList.getRandomCollectible());
		}

		for (int i =0 ; i < this.obstacles.size();i++) {
			this.obstacles.get(i).Draw(this.gameBatch);
			if( !this.obstacles.get(i).Move()) {
				this.obstacles.get(i).dispose();
				this.obstacles.remove(i);
			}
		}

		if(this.obstacles.size() < 1) {
			this.obstacles.add(
					new FishObstacle(
							8f,2f));
		}

		this.lifeCounter.draw(this.gameBatch);
		this.waypoint.draw(this.gameBatch);
		waypoint.move();

        this.gameBatch.end();

        //Draws fonts
        String score = Integer.toString(playerScore);
        game.batch.begin();
        game.font.draw(game.batch, score, Gdx.graphics.getWidth() * .95f,
                Gdx.graphics.getHeight() * .90f);
        game.batch.end();

		debugRenderer.render(world, camera.combined);

		// checks if the game has ended somehow
		if (waypoint.isFinished()) {
			this.reachedCheckpoint = true;
		}

		if (this.lostGame) {
			if (this.ball.isGrounded()) {
				this.game.setEndScreen();
			}
		} else if (reachedCheckpoint) {
			if (this.ball.isGrounded()) {
				this.game.setRecycleScreen();
			}
		}

		if (this.ball.isJustChangedScreen()) {
			this.ball.setJustChangedScreen(false);
		}

		ball.moveAnimation();

	}

	private void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private void moveCamera() {
		camera.position.set(ball.getObjectBody().getPosition().x + 3,
				2, 0);
	}

	private double accumulator = 0;
	private float TIME_STEP = 1 / 60f;

	private void doPhysicsStep(float deltaTime) {
		float frameTime = deltaTime;

		if (deltaTime > 1 / 4f) {
			frameTime = 1 / 4f;
		}

		accumulator += frameTime;
		while (accumulator >= TIME_STEP) {
			world.step(TIME_STEP, 8, 3);
			accumulator -= TIME_STEP;
		}
	}

	public void createGround() {
		Body groundBody = world.createBody(getGroundBodyDef());
		groundBody.setUserData(new GameObjectAdapter() {
			@Override
			public String Collide() {
				return ("wall");
			}
		});
		groundBody.createFixture(getGroundShape(), 0.0f);
	}

	private BodyDef getGroundBodyDef() {
		BodyDef myBodyDef = new BodyDef();
		myBodyDef.type = BodyDef.BodyType.StaticBody;

		myBodyDef.position.set(WORLD_WIDTH, 0.25f);
		return myBodyDef;
	}

	private PolygonShape getGroundShape() {
		PolygonShape groundBox = new PolygonShape();

		groundBox.setAsBox(WORLD_WIDTH * 100, 0.25f);
		return groundBox;
	}

	public static void setPlayerScore() {
	    playerScore += 1;
    }

    public static int getPlayerScore() {
		return playerScore;
	}

    public static void clearScore() {
	    playerScore = 0;
    }

    public void createAnimation() {
	    final int FRAME_COLS = 3;
	    final int FRAME_ROWS = 1;
    }

	@Override
	public void dispose () {
		Gdx.app.log("asd","ballgame.dispose");
		ball.dispose();
		this.scrollingBackground.dispose();

		for(int i = 0; i < this.obstacles.size(); i++) {
			this.obstacles.get(i).dispose();
		}

		for(int i = 0; i < this.collectables.size(); i++) {
			this.collectables.get(i).dispose();
		}

		game.batch.dispose();
	}

	@Override
	public void hide() {

		Gdx.input.setInputProcessor(null);
	}
}
