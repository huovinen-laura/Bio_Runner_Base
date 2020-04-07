package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.*;
import com.mygdx.game.collectibles.CollectibleSquare;
import com.mygdx.game.gamestate.LifeCounter;
import com.mygdx.game.obstacles.ObstacleRectangle;

import java.util.ArrayList;

public class BallGame extends ScreenAdapter {
	BioRunnerGame game;
    SpriteBatch gameBatch;
    BitmapFont font;
    public static boolean isMusicOn = true;

	public static float worldSpeed = -1f;
	public ScrollingBackground scrollingBackground;
	public World world;

	public static float WORLD_WIDTH = 8;
	public static float WORLD_HEIGHT = 4;
	private ArrayList<GameObject> collectables;
	private ArrayList<GameObject> obstacles;
	private Player ball;
	private Waypoint waypoint;
	private boolean lostGame;
    private static int point = 1;
    public float volume;

	OrthographicCamera camera = new OrthographicCamera();
	private Box2DDebugRenderer debugRenderer;
	private boolean reachedCheckpoint;

	public BallGame (BioRunnerGame game) {
		this.world = new World(new Vector2(0, -5f), true);
		this.game = game;
		Gdx.app.log("sf","Ballgame constructor");
		volume = 0.5f;

		game.batch = new SpriteBatch();
		this.font = game.getFont();
		collectables = new ArrayList<>();
		obstacles = new ArrayList<>();

		scrollingBackground = new ScrollingBackground(worldSpeed, game);
		createGround();
		debugRenderer = new Box2DDebugRenderer();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
	}

	@Override
	public void show() {
		this.reachedCheckpoint = false;
		game.collectedStuffList = new ShitCollection(this.game);
		ball = new Player(this.game.getCurrentAnimation(),game);
        game.batch = new SpriteBatch();
        this.gameBatch = new SpriteBatch();
		waypoint = new Waypoint(20f, game);
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

        this.gameBatch.begin();

		scrollingBackground.updateAndRender(Gdx.graphics.getDeltaTime(), this.gameBatch);
		ball.Draw(this.gameBatch);
		doPhysicsStep(Gdx.graphics.getDeltaTime());

		if(!this.ball.Move()) { // checks if lives is zero
			this.lostGame = true;
			Gdx.app.log("BallGame","Lost game");
		}


		this.manageCollectablesAndObstacles();

		if(game.allObstaclesCollection.isNextCollectibleComing(this.obstacles.size())) {
			this.obstacles.add(game.allObstaclesCollection.getRandomCollectible());
		}

		game.lifeCounter.draw(this.gameBatch);
		this.waypoint.draw(this.gameBatch);
		waypoint.move();

        this.gameBatch.end();

        //Draws the player's score
        String score = Integer.toString(game.playerScore);
        game.batch.begin();
        this.font.draw(game.batch, score, Gdx.graphics.getWidth() * .92f,
                Gdx.graphics.getHeight() * .90f);
        this.font.draw(game.batch, ""+game.worldSpeed,
				Gdx.graphics.getWidth() * 0.80f,Gdx.graphics.getHeight() * 0.90f);
        game.batch.end();

		debugRenderer.render(world, camera.combined);

		if (this.ball.isGrounded()) {
			if (this.lostGame) {
				this.game.setEndScreen();
				game.lifeCounter.setLives(3);
			} else if (this.waypoint.isFinished()) {
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

	/**
	 * Draws collectables and obstacles and removes collectables and obstacles that need to be removed
	 *
	 */
	private void manageCollectablesAndObstacles() {
		ArrayList<Integer> toRemove = new ArrayList();
		for (int i =0 ; i < this.collectables.size();i++) {
			this.collectables.get(i).Draw(this.gameBatch);
			if( !this.collectables.get(i).Move()) {
				this.collectables.get(i).dispose();
				toRemove.add(i);
			}
		}

		for (int i = 0; i < toRemove.size(); i++) {
			this.collectables.remove(toRemove.get(i)-i);
		}
		toRemove.clear();


		if(game.collectedStuffList.isNextCollectibleComing(this.collectables.size())) {
			this.collectables.add(game.collectedStuffList.getRandomCollectible());
		}

		for (int i =0 ; i < this.obstacles.size();i++) {
			this.obstacles.get(i).Draw(this.gameBatch);
			if( !this.obstacles.get(i).Move()) {
				this.obstacles.get(i).dispose();
				toRemove.add(i);
			}
		}
		for (int i = 0; i < toRemove.size(); i++) {
			this.obstacles.remove(toRemove.get(i)-i);
		}
		toRemove.clear();

	}


	private double accumulator = 0;
	private float TIME_STEP = 1 / 60f;

	private void doPhysicsStep(float deltaTime) {
		float frameTime = deltaTime;

		if (deltaTime > 1 / 2f) {
			frameTime = 1 / 2f;
		}

		accumulator += frameTime;
		if (frameTime > 2 * TIME_STEP) {
			Gdx.app.log("lag", "Frametime: " + frameTime/TIME_STEP );
		}

		while (accumulator >= TIME_STEP) {
			world.step(TIME_STEP, 8, 3);
			accumulator -= TIME_STEP;
		}
	}

	public void createGround() {
		Body groundBody = world.createBody(getGroundBodyDef());


		groundBody.createFixture(getGroundShape(), 0.0f);
	}

	private BodyDef getGroundBodyDef() {
		BodyDef myBodyDef = new BodyDef();
		myBodyDef.type = BodyDef.BodyType.StaticBody;

		myBodyDef.position.set(WORLD_WIDTH, 0.15f);
		return myBodyDef;
	}

	private PolygonShape getGroundShape() {
		PolygonShape groundBox = new PolygonShape();

		groundBox.setAsBox(WORLD_WIDTH * 100, 0.25f);
		return groundBox;
	}


    public int getPlayerScore() {
		return (this.game.playerScore);
	}

	public void setPoint(int newPoint) {
		point = newPoint;
	}

    public void clearScore() {
	    this.game.lifeCounter.setLives(0);
    }

    public static void setMusicOff() {
		isMusicOn = false;
	}

	public static void setMusicOn() {
		isMusicOn = true;
	}

	public static boolean isMusicOn() {
		return isMusicOn;
	}

	@Override
	public void dispose () {
		Gdx.app.log("asd","ballgame.dispose");
		ball.dispose();

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
		Gdx.app.log("", "hide game");
		this.ball.dispose();
		for(int i = 0; i < this.obstacles.size(); i++) {
			this.obstacles.get(i).dispose();
		}

		for(int i = 0; i < this.collectables.size(); i++) {
			this.collectables.get(i).dispose();
		}

		this.obstacles.clear();
		this.collectables.clear();
		Gdx.input.setInputProcessor(null);
	}

	public class B2dContactListener implements ContactListener {
		Sound hurt = Gdx.audio.newSound(Gdx.files.internal("hurt.wav"));
		Sound collect = Gdx.audio.newSound(Gdx.files.internal("collect.wav"));

		@Override
		public void beginContact(Contact contact) {
			Object a = contact.getFixtureA().getBody().getUserData();
			Object b = contact.getFixtureB().getBody().getUserData();

			if ((a instanceof Player && b instanceof CollectibleSquare)) {
				CollectibleSquare collectibleSquareB = (CollectibleSquare) b;
				collectibleSquareB.collect();
				game.collectedStuffList.addStuff(collectibleSquareB);
				game.playerScore += game.getPointsPerCollectable();
				if (game.getPrefs().getBoolean("soundOn", true)) {
				    collect.play(volume);
                }

			} else if ((b instanceof Player && a instanceof CollectibleSquare)) {
				CollectibleSquare collectibleSquareA = (CollectibleSquare) a;
				collectibleSquareA.collect();
				game.collectedStuffList.addStuff(collectibleSquareA);
				game.playerScore += game.getPointsPerCollectable();
                if (game.getPrefs().getBoolean("soundOn", true)) {
                    collect.play(volume);
                }
			}

			if (a instanceof ObstacleRectangle && b instanceof Player) {
				ObstacleRectangle obstacle = (ObstacleRectangle) a;
				if(!obstacle.isDeleted()) {
                    if(game.getPrefs().getBoolean("soundOn", true)) {
                        hurt.play(volume);
                    }
					game.allObstaclesCollection.addStuff(obstacle);
					obstacle.delete();
					game.lifeCounter.loseLife();
				}

			} else if ( a instanceof Player && b instanceof ObstacleRectangle) {
				ObstacleRectangle obstacle = (ObstacleRectangle) b;
				if(!obstacle.isDeleted()) {
                    if(game.getPrefs().getBoolean("soundOn", true)) {
                        hurt.play(volume);
                    }
					game.allObstaclesCollection.addStuff(obstacle);
					obstacle.delete();
					game.lifeCounter.loseLife();
				}
			}

		}

		@Override
		public void endContact(Contact contact) {

		}

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {

		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {

		}
	}

}
