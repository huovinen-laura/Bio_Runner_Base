package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class BallGame extends ScreenAdapter {
	BioRunnerGame game;

	public static float worldSpeed = -1f;
	public static ShitCollection collectedStuffList = new ShitCollection();
	public ScrollingBackground scrollingBackground;
	public static World world = new World(new Vector2(0, -5f), true);
	Sound soundEffect;
	private LifeCounter lifeCounter;

	static float WORLD_WIDTH = 8;
	static float WORLD_HEIGHT = 4;
	private ArrayList<GameObject> collectables;
	private ArrayList<GameObject> obstacles;

	private float radius = 0.5f;
	private Player ball;

	OrthographicCamera camera = new OrthographicCamera();
	private Box2DDebugRenderer debugRenderer;

	public BallGame (BioRunnerGame game) {
		this.game = game;

		game.batch = new SpriteBatch();
		collectables = new ArrayList<GameObject>();
		obstacles = new ArrayList<>();
		obstacles.add(new FishObstacle(8f,2f));
		this.lifeCounter = new LifeCounter();

		soundEffect = Gdx.audio.newSound(Gdx.files.internal("touch.wav"));

		scrollingBackground = new ScrollingBackground(worldSpeed);

		ball = new Player(this.world);
		this.world.setContactListener(new B2dContactListener());
		this.world.setContactFilter(new B2dContactFilter());



		createGround();

		debugRenderer = new Box2DDebugRenderer();

		moveCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keyCode) {
				if (keyCode == Input.Keys.ENTER) {
					game.setScreen(new EndScreen(game));
				}
				return true;
			}
		});
	}

	@Override
	public void render (float delta) {
		game.batch.setProjectionMatrix(camera.combined);
		clearScreen();
		this.ball.Move();




		game.batch.begin();
		scrollingBackground.updateAndRender(Gdx.graphics.getDeltaTime(), game.batch);

		for (int i =0 ; i < this.collectables.size();i++) {
			this.collectables.get(i).Draw(game.batch);
			if( !this.collectables.get(i).Move()) {
				this.collectables.get(i).dispose();
				this.collectables.remove(i);
			}
		}

		if(this.collectables.size() <= 1) {
			this.collectables.add(
					new TestCollectible(
							8f,(float) Math.random()*3));
		}

		for (int i =0 ; i < this.obstacles.size();i++) {
			this.obstacles.get(i).Draw(game.batch);
			if( !this.obstacles.get(i).Move()) {
				this.obstacles.get(i).dispose();
				this.obstacles.remove(i);
			}
		}

		if(this.obstacles.size() < 1) {
			Gdx.app.log("asdf", "creating new obstacle");
			this.obstacles.add(
					new FishObstacle(
							8f,2f));
		}

		ball.Draw(game.batch);
		this.lifeCounter.draw(game.batch);
		game.batch.end();
		debugRenderer.render(world, camera.combined);

		doPhysicsStep(Gdx.graphics.getDeltaTime());




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

	@Override
	public void dispose () {
		game.batch.dispose();
		ball.dispose();
		this.scrollingBackground.dispose();

		for(GameObject obstacle: this.obstacles) {
			obstacle.dispose();
		}
		for(GameObject collectible: this.collectables) {
			collectible.dispose();
		}
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
}
