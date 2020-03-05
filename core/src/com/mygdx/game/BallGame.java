package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class BallGame extends ApplicationAdapter {
	SpriteBatch batch;

	Sound soundEffect;

	static float WORLD_WIDTH = 8;
	static float WORLD_HEIGHT = 4;
	private ArrayList<GameObject> collectables;

	private float radius = 0.5f;
	private Player ball;
	private World world;

	OrthographicCamera camera = new OrthographicCamera();
	private Box2DDebugRenderer debugRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();

		soundEffect = Gdx.audio.newSound(Gdx.files.internal("touch.wav"));

		world = new World(new Vector2(0, -9.8f), true);
		ball = new Player(this.world);


		createGround();

		debugRenderer = new Box2DDebugRenderer();

		moveCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
	}


	@Override
	public void render () {
		batch.setProjectionMatrix(camera.combined);
		clearScreen();
		this.ball.Move();
		debugRenderer.render(world, camera.combined);




		batch.begin();
		ball.Draw(batch);
		batch.end();

		doPhysicsStep(Gdx.graphics.getDeltaTime());

		moveCamera();
		camera.update();

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
		batch.dispose();
		ball.dispose();
	}
}
