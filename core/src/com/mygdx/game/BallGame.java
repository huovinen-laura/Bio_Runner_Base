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

public class BallGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture ball;
	Sound soundEffect;

	private float WORLD_WIDTH = 8;
	private float WORLD_HEIGHT = 4;

	private float radius = 0.5f;

	private World world;
	private Body playerBody;

	OrthographicCamera camera = new OrthographicCamera();
	private Box2DDebugRenderer debugRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		ball = new Texture("ball.png");
		soundEffect = Gdx.audio.newSound(Gdx.files.internal("touch.wav"));

		world = new World(new Vector2(0, -9.8f), true);
		playerBody = world.createBody(getDefinitionOfBody(WORLD_WIDTH / 2, WORLD_HEIGHT / 2));
		playerBody.createFixture(getFixtureDefinition());

		createGround();

		debugRenderer = new Box2DDebugRenderer();

		moveCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
	}

	private BodyDef getDefinitionOfBody(float x, float y) {
		//Body definition
		BodyDef myBodyDef = new BodyDef();

		//What type of body? This one moves.
		myBodyDef.type = BodyDef.BodyType.DynamicBody;

		//Body's position
		myBodyDef.position.set(x, y);
		return myBodyDef;
	}

	private FixtureDef getFixtureDefinition() {
		FixtureDef playerFixtureDef = new FixtureDef();

		//Mass per square meter
		playerFixtureDef.density = 1;

		//How bouncy is the object? 0-1
		playerFixtureDef.restitution = 0.1f;

		//How slippery the object is? 0-1
	    playerFixtureDef.friction = 0.5f;

	    //Create circle shape
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius);

		//Add shape to the fixture
		playerFixtureDef.shape = circleShape;
		return playerFixtureDef;
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(camera.combined);
		clearScreen();

		checkUserInput();

		moveCamera();
		camera.update();

		debugRenderer.render(world, camera.combined);

		batch.begin();
		batch.draw(ball,
				playerBody.getPosition().x - radius,
				playerBody.getPosition().y -radius,
				radius,
				radius,
				radius * 2,
				radius * 2,
				1.0f,
				1.0f,
				playerBody.getTransform().getRotation() * MathUtils.radiansToDegrees,
				0,
				0,
				ball.getWidth(),
				ball.getHeight(),
				false,
				false);
		batch.end();

		doPhysicsStep(Gdx.graphics.getDeltaTime());
	}

	private void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private void moveCamera() {
		camera.position.set(playerBody.getPosition().x + 3,
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

		groundBox.setAsBox(WORLD_WIDTH * 2, 0.25f);
		return groundBox;
	}

	private void checkUserInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			playerBody.applyForceToCenter(new Vector2(-5f, 0), true);
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			playerBody.applyForceToCenter(new Vector2(5f, 0), true);
		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			playerBody.applyLinearImpulse(new Vector2(0, 0.5f), playerBody.getWorldCenter(), true);
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		ball.dispose();
	}
}
