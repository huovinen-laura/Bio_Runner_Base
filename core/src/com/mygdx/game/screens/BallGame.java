package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.*;
import com.mygdx.game.collectibles.CollectibleSquare;
import com.mygdx.game.obstacles.ObstacleRectangle;
import com.badlogic.gdx.graphics.profiling.GLProfiler;

import java.util.ArrayList;

public class BallGame extends ScreenAdapter {
	BioRunnerGame game;
    private SpriteBatch gameBatch;
    private BitmapFont font;
    private boolean leaveForRecycleScreen;

	public static float worldSpeed = -1f;
	public ScrollingBackground scrollingBackground;

	public static float WORLD_WIDTH = 8;
	public static float WORLD_HEIGHT = 4;
	private ArrayList<GameObject> collectables;
	private ArrayList<GameObject> obstacles;
	private Player ball;
	private Waypoint waypoint;
	private boolean lostGame;
    public float volume;

	OrthographicCamera camera = new OrthographicCamera();

	private B2dContactListener contactListener;
	private B2dContactFilter contactFilter;
	private RecycleCenter recycleCenter;
	private boolean recycleCenterVisible;
	private Sound hurt, collect;

	public BallGame (BioRunnerGame game) {
		hurt = Gdx.audio.newSound(Gdx.files.internal("hurt.wav"));
		collect = Gdx.audio.newSound(Gdx.files.internal("collect.wav"));
		this.game = game;
		ball = new Player(this.game.getCurrentAnimation(),game);
		volume = 0.1f;

		this.font = game.getFont();
		collectables = new ArrayList<>();
		obstacles = new ArrayList<>();

		this.contactFilter = new B2dContactFilter();
		this.contactListener = new B2dContactListener();

		scrollingBackground = new ScrollingBackground(worldSpeed, game);
		createGround();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
	}

	@Override
	public void show() {
		recycleCenterVisible = false;
		leaveForRecycleScreen = false;
		recycleCenter = new RecycleCenter(game,11f);

		ball.update();

		game.getCollectedStuffList().clear();
        this.gameBatch = new SpriteBatch();
		waypoint = new Waypoint(20f, game);
		this.lostGame = false;
		game.getWorld().setContactListener(this.contactListener);
		game.getWorld().setContactFilter(this.contactFilter);
		this.gameBatch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void render (float delta) {

		if (this.game.getLifeCounter().getLivesAmount() <= 0) {
			this.game.setEndScreen();
			game.getLifeCounter().setLives(3);
		} else if (this.waypoint.isFinished() || this.leaveForRecycleScreen) {
			this.game.setRecycleScreen();
		} else {

			clearScreen();
			this.gameBatch.begin();

			scrollingBackground.drawSky(this.gameBatch);

			if (waypoint.move()) {

				this.recycleCenterVisible = true;

				this.recycleCenter.Move();

				this.recycleCenter.Draw(this.gameBatch);
			}

			scrollingBackground.drawGrass(this.gameBatch);

			scrollingBackground.update(Gdx.graphics.getDeltaTime());

			ball.Draw(this.gameBatch);

			doPhysicsStep(Gdx.graphics.getDeltaTime());

			if (!this.ball.Move()) { // checks if lives is zero
				this.lostGame = true;
			}


			this.manageCollectablesAndObstacles();

			if (!this.recycleCenterVisible) {
				if (game.getAllObstaclesCollection().isNextCollectibleComing(this.obstacles.size())) {
					this.obstacles.add(game.getAllObstaclesCollection().getRandomCollectible(game.getLevelNumber()));
				}
			}

			game.getLifeCounter().draw(this.gameBatch);
			this.waypoint.draw(this.gameBatch);

			this.gameBatch.end();

			//Draws the player's score
			game.getBatch().begin();
			this.font.draw(game.getBatch(), "" + game.getPlayerScore(), game.getProjected().x * .92f,
					game.getProjected().y * .90f);
			game.getBatch().end();


			if (this.ball.isJustChangedScreen()) {
				this.ball.setJustChangedScreen(false);
			}

			ball.moveAnimation();
		}
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
		GameObject collectable;

		for (int i =0 ; i < this.collectables.size();i++) {
			collectable = this.collectables.get(i);
			collectable.Draw(this.gameBatch);

			if( !collectable.Move()) {
				toRemove.add(i);
			}
		}

		for (int i = 0; i < toRemove.size(); i++) {
			this.collectables.remove(toRemove.get(i)-i);
		}
		toRemove.clear();


		if(!this.recycleCenterVisible) {
			if (game.getCollectedStuffList().isNextCollectibleComing(this.collectables.size())) {
				this.collectables.add(game.getCollectedStuffList().getRandomCollectible(game.getLevelNumber() + 1));
			}
		}

		for (int i =0 ; i < this.obstacles.size();i++) {
			collectable = obstacles.get(i);
			collectable.Draw(this.gameBatch);
			if( !collectable.Move()) {
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


		while (accumulator >= TIME_STEP) {
			game.getWorld().step(TIME_STEP, 8, 3);
			accumulator -= TIME_STEP;
		}
	}

	public void createGround() {
		Body groundBody = game.getWorld().createBody(getGroundBodyDef());
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.friction = 100f;
		fixtureDef.shape = getGroundShape();
		fixtureDef.density = 0.0f;



		groundBody.createFixture(fixtureDef);
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

	@Override
	public void dispose () {

		for(int i = 0; i < this.obstacles.size(); i++) {
			this.obstacles.get(i).dispose();
			this.obstacles.get(i).getObjectBody().getWorld().destroyBody(this.obstacles.get(i).getObjectBody());
		}

		for(int i = 0; i < this.collectables.size(); i++) {
			this.collectables.get(i).dispose();
			this.collectables.get(i).getObjectBody().getWorld().destroyBody(this.collectables.get(i).getObjectBody());
		}

		ball.dispose();
		recycleCenter.dispose();
		recycleCenter.getObjectBody().getWorld().destroyBody(recycleCenter.getObjectBody());
		this.collect.dispose();
		this.hurt.dispose();

		if(this.gameBatch != null) {
			this.gameBatch.dispose();
		}

	}

	@Override
	public void hide() {
		this.recycleCenterVisible = false;

		for(int i = 0; i < this.obstacles.size(); i++) {
			this.obstacles.get(i).dispose();
			this.obstacles.get(i).getObjectBody().getWorld().destroyBody(this.obstacles.get(i).getObjectBody());
		}

		for(int i = 0; i < this.collectables.size(); i++) {
			this.collectables.get(i).dispose();
			this.collectables.get(i).getObjectBody().getWorld().destroyBody(this.collectables.get(i).getObjectBody());
		}

		recycleCenter.dispose();
		recycleCenter.getObjectBody().getWorld().destroyBody(recycleCenter.getObjectBody());

		this.obstacles.clear();
		this.collectables.clear();
		this.gameBatch.dispose();
		Gdx.input.setInputProcessor(null);
	}

	public class B2dContactListener implements ContactListener {


		@Override
		public void beginContact(Contact contact) {
			Object a = contact.getFixtureA().getBody().getUserData();
			Object b = contact.getFixtureB().getBody().getUserData();

			if ((a instanceof Player && b instanceof CollectibleSquare)) {
				CollectibleSquare collectibleSquareB = (CollectibleSquare) b;
				collectibleSquareB.collect();
				game.getCollectedStuffList().addStuff(collectibleSquareB);
				game.setPlayerScore(game.getPlayerScore() + game.getPointsPerCollectable());
				if (game.getPrefs().getBoolean("soundOn", true)) {
				    collect.play(volume);
                }

			} else if ((b instanceof Player && a instanceof CollectibleSquare)) {
				CollectibleSquare collectibleSquareA = (CollectibleSquare) a;
				collectibleSquareA.collect();
				game.getCollectedStuffList().addStuff(collectibleSquareA);
				game.setPlayerScore(game.getPlayerScore() + game.getPointsPerCollectable());
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

					game.getAllObstaclesCollection().addStuff(obstacle);
					obstacle.delete();
					game.getLifeCounter().loseLife();
				}

			} else if ( a instanceof Player && b instanceof ObstacleRectangle) {
				ObstacleRectangle obstacle = (ObstacleRectangle) b;
				if(!obstacle.isDeleted()) {
                    if(game.getPrefs().getBoolean("soundOn", true)) {
                        hurt.play(volume);
                    }
					game.getAllObstaclesCollection().addStuff(obstacle);
					obstacle.delete();
					game.getLifeCounter().loseLife();
				}
			} else if( a instanceof Player && b instanceof RecycleCenter) {
					leaveForRecycleScreen = true;
			} else if(a instanceof RecycleCenter && b instanceof Player) {
				leaveForRecycleScreen = true;
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
