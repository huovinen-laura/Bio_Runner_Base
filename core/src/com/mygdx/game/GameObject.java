package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class GameObject {
    protected Texture objectTexture;
    protected Body objectBody;
    protected Vector2 position;
    private String name;
    protected float radius;
    protected float spriteWidth;
    protected float spriteHeight;
    protected boolean flipSpriteX = false;
    protected boolean flipSpriteY = false;

    public GameObject(Texture texture, float size,float x, float y, float density,
                      float bouncines, float friction, boolean flipX,boolean flipY) {
        this(texture,size,x,y,density,bouncines,friction);
        this.flipSpriteX = flipX;
        this.flipSpriteY = flipY;
    }

    public GameObject(Texture texture, float size,float x, float y, float density,
                      float bouncines, float friction) {
        this.objectTexture = texture;
        this.radius = size;
        this.spriteHeight = 0.5f*radius*(objectTexture.getHeight()/objectTexture.getWidth());
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

    public GameObject( Texture texture, float size,float x, float y, float density,
                       float bouncines, float friction, Vector2 linearVelocity, Float gravityScale) {
        this.objectTexture = texture;
        this.radius = size;
        this.spriteHeight = 0.5f*radius*(objectTexture.getHeight()/objectTexture.getWidth());
        this.spriteWidth = 0.5f*radius;
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
}
