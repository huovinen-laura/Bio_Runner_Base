package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Drawable object with Box2D body, fixture and position
 */
public abstract class GameObject {
    protected Texture objectTexture;
    protected Texture playerAnimationTexture;
    protected Body objectBody;
    protected Vector2 position;
    private String name;
    protected float radius;
    protected float spriteWidth;
    protected float spriteHeight;
    protected boolean flipSpriteX = false;
    protected boolean flipSpriteY = false;
    private static FixtureDef getFixtureDef;
    private BioRunnerGame game;
    private boolean toBeDestroyed = false;

    /**
     * Creates an object.
     * @param game The base game.
     * @param texture The texture for the object.
     * @param size The size of the object.
     * @param x The x coordinate of the object.
     * @param y The y coordinate of the object.
     * @param density Density of the object.
     * @param bounciness Bounciness of the object.
     * @param friction Friction of the object.
     * @param flipX Should the object be flipped in X direction.
     * @param flipY Should the object be flipped in Y direction.
     */
    public GameObject(BioRunnerGame game,Texture texture, float size, float x, float y, float density,
                      float bounciness, float friction, boolean flipX, boolean flipY) {
        this(game,texture,size,x,y,density,bounciness,friction);
        this.flipSpriteX = flipX;
        this.flipSpriteY = flipY;
    }

    /**
     * Creates an object.
     *
     * @param game The base game.
     * @param texture The texture for the object.
     * @param size The size of the object.
     * @param x The x coordinate of the object.
     * @param y The y coordinate of the object.
     * @param density Density of the object.
     * @param bounciness Bounciness of the object.
     * @param friction Friction of the object.
     */
    public GameObject(BioRunnerGame game,Texture texture, float size,float x, float y, float density,
                      float bounciness, float friction) {
        this.game = game;
        this.objectTexture = texture;
        this.radius = size;
        this.spriteWidth = (float) objectTexture.getWidth();
        this.spriteHeight = (float) objectTexture.getHeight();
        this.spriteHeight = 0.5f*radius*(this.spriteHeight/this.spriteWidth);
        this.spriteWidth = 0.5f*radius;
        BodyDef myBodyDef = new BodyDef();

        //What type of body? This one moves.
        myBodyDef.type = BodyDef.BodyType.DynamicBody;
        //Body's position
        myBodyDef.position.set(x, y);

        this.objectBody = this.game.getWorld().createBody(myBodyDef);
        FixtureDef playerFixtureDef = new FixtureDef();

        //Mass per square meter
        playerFixtureDef.density = density;

        //How bouncy is the object? 0-1
        playerFixtureDef.restitution = bounciness;

        //How slippery the object is? 0-1
        playerFixtureDef.friction = friction;

        //Create polygon shape
        PolygonShape rectangleShape = new PolygonShape();
        rectangleShape.set(new Vector2[]{new Vector2(0f, 0f), new Vector2(0f, this.spriteHeight),
                new Vector2(this.spriteWidth, this.spriteHeight), new Vector2(this.spriteWidth,0f)
        });

        //Add shape to the fixture
        playerFixtureDef.shape = rectangleShape;
        this.objectBody.createFixture(playerFixtureDef);
        this.objectBody.setUserData(this);
    }

    /**
     * Creates an object.
     *
     * @param game The base game.
     * @param texture The texture for the object.
     * @param size The size of the object.
     * @param x The x coordinate of the object.
     * @param y The y coordinate of the object.
     * @param density Density of the object.
     * @param bounciness Bounciness of the object.
     * @param friction Friction of the object.
     * @param linearVelocity Linear Velocity of the object.
     * @param gravityScale Gravity for the object.
     */
    public GameObject(BioRunnerGame game, Texture texture, float size, float x, float y, float density,
                      float bounciness, float friction, Vector2 linearVelocity, Float gravityScale) {
        this.game = game;
        this.objectTexture = texture;
        this.radius = size;
        this.spriteWidth = (float) objectTexture.getWidth();
        this.spriteHeight = (float) objectTexture.getHeight();
        this.spriteHeight = 0.5f * radius * (this.spriteHeight/this.spriteWidth);
        this.spriteWidth = 0.5f * radius;
        BodyDef myBodyDef = new BodyDef();

        //What type of body? This one moves.
        myBodyDef.type = BodyDef.BodyType.DynamicBody;
        myBodyDef.position.set(x, y);
        myBodyDef.linearVelocity.set(linearVelocity);
        myBodyDef.gravityScale = gravityScale;
        //Body's position


        this.objectBody = this.game.getWorld().createBody(myBodyDef);
        FixtureDef playerFixtureDef = new FixtureDef();

        //Mass per square meter
        playerFixtureDef.density = density;

        //How bouncy is the object? 0-1
        playerFixtureDef.restitution = bounciness;

        //How slippery the object is? 0-1
        playerFixtureDef.friction = friction;
        //Create circle shape
        PolygonShape rectangleShape = new PolygonShape();
        rectangleShape.set(new Vector2[]{new Vector2(0f, 0f), new Vector2(0f, this.spriteHeight),
                new Vector2(this.spriteWidth, this.spriteHeight), new Vector2(this.spriteWidth,0f)
        });

        //Add shape to the fixture
        playerFixtureDef.shape = rectangleShape;
        this.objectBody.createFixture(playerFixtureDef);
        this.objectBody.setUserData(this);

    }

    /**
     * Game object constructor for player model
     *
     * @param game the game
     * @param player true or false if player object
     * @param texture texture to be used as players animation texture
     * @param size size
     * @param x position
     * @param y position
     * @param mass desired mass
     * @param bounciness
     * @param friction
     */
    public GameObject(BioRunnerGame game, Boolean player, Texture texture,
                      float size, float x, float y, float mass, float bounciness, float friction) {
        this.playerAnimationTexture = texture;
        this.game = game;
        this.radius = size;
        this.spriteWidth = 0.7f;
        this.spriteHeight = 1.4f;



        float spriteArea = spriteHeight * spriteWidth;
        BodyDef myBodyDef = new BodyDef();

        //What type of body? This one moves.
        myBodyDef.type = BodyDef.BodyType.DynamicBody;
        //Body's position
        myBodyDef.position.set(x, y);

        this.objectBody = this.game.getWorld().createBody(myBodyDef);
        FixtureDef playerFixtureDef = new FixtureDef();

        //Mass per square meter
        playerFixtureDef.density = mass / spriteArea;

        //How bouncy is the object? 0-1
        playerFixtureDef.restitution = bounciness;

        //How slippery the object is? 0-1
        playerFixtureDef.friction = friction;

        //Create polygon shape
        PolygonShape rectangleShape = new PolygonShape();
        rectangleShape.set(new Vector2[]{new Vector2(0f, 0f), new Vector2(0f, this.spriteHeight),
                new Vector2(this.spriteWidth, this.spriteHeight), new Vector2(this.spriteWidth,0f)
        });

        //Add shape to the fixture
        playerFixtureDef.shape = rectangleShape;
        this.objectBody.createFixture(playerFixtureDef);
        this.objectBody.setUserData(this);
    }

    /**
     * Draws the object with pre-determined position.
     *
     * @param batch Batch for drawing the object.
     */
    public void Draw(SpriteBatch batch) {
        batch.draw(this.getObjectTexture(),
                this.getObjectBody().getPosition().x,
                this.getObjectBody().getPosition().y,
            0f,
            0f,
            this.spriteWidth,
            this.spriteHeight,
            1.0f,
            1.0f,
            this.getObjectBody().getTransform().getRotation() * MathUtils.radiansToDegrees,
            0,
            0,
            this.objectTexture.getWidth(),
            this.objectTexture.getHeight(),
            this.flipSpriteX,
            this.flipSpriteY);
    }

    /**
     * Draw method with possibility to determine the place
     *
     * @param batch Batch for drawing the object.
     * @param posx position x
     * @param posy position y
     */
    public void draw(SpriteBatch batch,float posx, float posy) {
        batch.draw(this.getObjectTexture(),
                posx,
                posy,
                0f,
                0f,
                this.spriteWidth,
                this.spriteHeight,
                1.0f,
                1.0f,
                this.getObjectBody().getTransform().getRotation() * MathUtils.radiansToDegrees,
                0,
                0,
                this.objectTexture.getWidth(),
                this.objectTexture.getHeight(),
                this.flipSpriteX,
                this.flipSpriteY);
    }

    /**
     * Should be called every render
     *
     * @return true if destroyed
     */
    public abstract boolean Move();

    /**
     * Returns the object's texture.
     *
     * @return Returns the object's texture.
     */
    public Texture getObjectTexture() {
        return objectTexture;
    }

    /**
     * Returns the object's body.
     *
     * @return Returns the object's body.
     */
    public Body getObjectBody() {
        return objectBody;
    }

    /**
     * Disposes the object.
     */
    public void dispose() {
        while(this.getObjectBody().getFixtureList().size > 0) {
            this.getObjectBody().destroyFixture(this.getObjectBody().getFixtureList().get(0));
        }
        this.toBeDestroyed = true;
    }

    /**
     * Returns a name.
     *
     * @return Returns a name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a name.
     *
     * @param name New name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the game.
     *
     * @return Gets the game.
     */
    public BioRunnerGame getGame() {
        return this.game;
    }

}
