package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.BallGame;

public abstract class GameObject {
    protected Texture objectTexture;
    protected Texture playerTexture;
    protected Body objectBody;
    protected Vector2 position;
    private String name;
    protected float radius;
    protected float spriteWidth;
    protected float spriteHeight;
    protected boolean flipSpriteX = false;
    protected boolean flipSpriteY = false;
    protected TextureRegion textureRegion;
    protected Animation animation;
    private static FixtureDef getFixtureDef;

    public GameObject(Texture texture, float size, float x, float y, float density,
                      float bouncines, float friction, boolean flipX, boolean flipY) {
        this(texture,size,x,y,density,bouncines,friction);
        this.flipSpriteX = flipX;
        this.flipSpriteY = flipY;
    }

    public GameObject(Texture texture, float size,float x, float y, float density,
                      float bouncines, float friction) {
        this.objectTexture = texture;
        this.radius = size;
        this.spriteWidth = (float) objectTexture.getWidth();
        this.spriteHeight = (float) objectTexture.getHeight();
        this.spriteHeight = 0.5f*radius*(this.spriteHeight/this.spriteWidth);
        this.spriteWidth = 0.5f*radius;
        Gdx.app.log("HW", "" + this.spriteWidth + " " + this.spriteHeight);
        BodyDef myBodyDef = new BodyDef();

        //What type of body? This one moves.
        myBodyDef.type = BodyDef.BodyType.DynamicBody;
        //Body's position
        myBodyDef.position.set(x, y);

        this.objectBody = BallGame.world.createBody(myBodyDef);
        FixtureDef playerFixtureDef = new FixtureDef();

        //Mass per square meter
        playerFixtureDef.density = density;

        //How bouncy is the object? 0-1
        playerFixtureDef.restitution = bouncines;

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

    public GameObject( Texture texture, float size,float x, float y, float density,
                       float bouncines, float friction, Vector2 linearVelocity, Float gravityScale) {
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


        this.objectBody = BallGame.world.createBody(myBodyDef);
        FixtureDef playerFixtureDef = new FixtureDef();

        //Mass per square meter
        playerFixtureDef.density = density;

        //How bouncy is the object? 0-1
        playerFixtureDef.restitution = bouncines;

        //How slippery the object is? 0-1
        playerFixtureDef.friction = friction;
        Gdx.app.log("shape","spriteHeight:" + this.spriteHeight + " spriteWidth: " + this.spriteWidth);
        //Create circle shape
        PolygonShape rectangleShape = new PolygonShape();
        Gdx.app.log("shape","spriteHeight:" + this.spriteHeight + " spriteWidth: " + this.spriteWidth);
        rectangleShape.set(new Vector2[]{new Vector2(0f, 0f), new Vector2(0f, this.spriteHeight),
                new Vector2(this.spriteWidth, this.spriteHeight), new Vector2(this.spriteWidth,0f)
        });

        //Add shape to the fixture
        playerFixtureDef.shape = rectangleShape;
        this.objectBody.createFixture(playerFixtureDef);
        this.objectBody.setUserData(this);

    }

    public GameObject(TextureRegion textureRegion, Texture texture, float size, float x, float y, float mass, float bounciness, float friction) {
        this.textureRegion = textureRegion;
        this.playerTexture = texture;
        this.radius = size;
        this.spriteWidth = (float) playerTexture.getWidth() / 4;
        this.spriteHeight = (float) playerTexture.getHeight() / 1.5f;
        this.spriteHeight = 0.5f*radius*(this.spriteHeight/this.spriteWidth);
        this.spriteWidth = 0.5f*radius;
        float spriteArea = spriteHeight * spriteWidth;
        Gdx.app.log("HW", "" + this.spriteWidth + " " + this.spriteHeight);
        BodyDef myBodyDef = new BodyDef();

        //What type of body? This one moves.
        myBodyDef.type = BodyDef.BodyType.DynamicBody;
        //Body's position
        myBodyDef.position.set(x, y);

        this.objectBody = BallGame.world.createBody(myBodyDef);
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

    public abstract boolean Move();
    public abstract String Collide();

    public Texture getObjectTexture() {
        return objectTexture;
    }

    public void setObjectTexture(Texture objectTexture) {
        this.objectTexture = objectTexture;
    }

    public Body getObjectBody() {
        return objectBody;
    }

    public void dispose() {
        while(this.getObjectBody().getFixtureList().size > 0) {
            this.getObjectBody().destroyFixture(this.getObjectBody().getFixtureList().get(0));
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setObjectBody(Body objectBody) {
        this.objectBody = objectBody;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerTexture(Texture playerTexture) {
        this.playerTexture = playerTexture;
    }
}
