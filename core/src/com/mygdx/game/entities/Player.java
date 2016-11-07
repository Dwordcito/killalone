package com.mygdx.game.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.NORMAL;

public class Player extends Actor {

    private Animation animationMove;
    private Animation animationMoveLeft;
    private Animation animationJump;
    private Animation animationJumpLeft;
    private Animation animationCrouch;
    private Animation animationCrouchLeft;
    private Animation animationShoot;
    private Animation animationShootLeft;
    private Animation animationQuiet;
    private Animation animationQuietLeft;

    private Animation currentAnimation;

    private World world;
    public Body body;
    private Fixture fixture;

    private boolean alive = true;
    private boolean quiet = true;
    private boolean jumping = false;
    private boolean crouch = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean frontDirection = true;
    private boolean shooting = false;

    private float elapsedTime = 0;
    private int score = 0;


    public Player(World world, TextureAtlas textureMove, TextureAtlas textureJump, TextureAtlas textureCrouch, TextureAtlas textureShoot, Vector2 position) {
        this.world = world;
        this.animationMove = new Animation(1/6f, textureMove.getRegions());
        this.animationJump = new Animation(1/4f, textureJump.getRegions());
        this.animationCrouch = new Animation(1/6f, getArrayOneTexture(textureCrouch.getRegions(),2));
        this.animationShoot = new Animation(1/4f, textureShoot.getRegions());
        this.animationQuiet = new Animation(1, getArrayOneTexture(textureShoot.getRegions(),1));

        this.animationMoveLeft = new Animation(1/6f, invertRegions(textureMove));
        this.animationJumpLeft = new Animation(1/4f, invertRegions(textureJump));
        this.animationCrouchLeft = new Animation(1/6f, invertRegions(textureCrouch.getRegions(),2));
        this.animationShootLeft = new Animation(1/6f, invertRegions(textureShoot));
        this.animationQuietLeft = new Animation(1, invertRegions(textureShoot.getRegions(),1));


        this.currentAnimation = this.animationQuiet;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(box, 3);
        fixture.setUserData("player");
        box.dispose();

        setSize(Constants.PIXELS_IN_METER, Constants.PIXELS_IN_METER);
    }

    public Array<TextureRegion> invertRegions(TextureAtlas textures){
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for(TextureRegion keyFrame : textures.getRegions() ) {
            TextureRegion region = new TextureRegion(keyFrame);
            region.flip(true, false);
            textureArray.add(region);
        }
        return textureArray;
    }

    public Array<TextureRegion> getArrayOneTexture(Array<TextureAtlas.AtlasRegion> array, int index){
        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        TextureRegion region = new TextureRegion(array.get(index));
        textureArray.add(region);
        return textureArray;
    }

    public Array<TextureRegion> invertRegions(Array<TextureAtlas.AtlasRegion> array, int index){
        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        TextureRegion region = new TextureRegion(array.get(index));
        region.flip(true, false);
        textureArray.add(region);
        return textureArray;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        elapsedTime += Gdx.graphics.getDeltaTime();

        setPosition((body.getPosition().x - 0.5f) * Constants.PIXELS_IN_METER,
            (body.getPosition().y - 0.5f) * Constants.PIXELS_IN_METER);

        batch.draw(currentAnimation.getKeyFrame(elapsedTime,true), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        if (jumping) {
            body.applyForceToCenter(0, -Constants.IMPULSE_JUMP * 1.15f, true);
        } 
    }
    public void shoot() {
        if (!shooting && alive) {
            shooting = true;
            quiet = false;

            elapsedTime = 0;
            currentAnimation = frontDirection?animationShoot:animationShootLeft;

            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(0, speedY);
        }
    }

    public void die() {
        if (alive) {
            alive = false;

            elapsedTime = 0;
            //currentAnimation = animationQuiet;
            
            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(0, speedY);
        }
    }
    public void quiet() {
        if (!quiet && alive) {
            quiet = true;
            crouch = false;
            moveLeft = false;
            moveRight = false;
            shooting = false;
            elapsedTime = 0;

            currentAnimation = frontDirection?animationQuiet:animationQuietLeft;

            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(0, speedY);
        }
    }
    public void jump() {
        if (!jumping && alive) {
            jumping = true;
            quiet = false;

            elapsedTime = 0;
            currentAnimation = frontDirection?animationJump:animationJumpLeft;

            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, Constants.IMPULSE_JUMP, position.x, position.y, true);
        }
    }

    public void crouch() {
        if (!crouch && !jumping && alive) {
            crouch = true;
            quiet = false;


            elapsedTime = 0;
            currentAnimation = frontDirection?animationCrouch:animationCrouchLeft;

            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(0, speedY);
        }

    }

    public void moveLeft() {
        if (!moveLeft && !jumping && alive) {
            moveLeft = true;
            quiet = false;
            frontDirection = false;

            elapsedTime = 0;
            currentAnimation = animationMoveLeft;

        }

        if (moveLeft){
            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(-Constants.PLAYER_SPEED, speedY);
        }
    }


    public void moveRight() {
        if (!moveRight && !jumping && alive) {
            moveRight = true;
            quiet = false;
            frontDirection = true;
            elapsedTime = 0;
            currentAnimation = animationMove;

        }
        if (moveRight){
            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(Constants.PLAYER_SPEED, speedY);
        }
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }


    public boolean isAlive() {
        return alive;
    }

    public boolean isMoving() {
        if(isMovingRight() || isMovingLeft())
            return true;
        else
            return false;
    }
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public boolean isMovingRight(){
        return moveRight;
    }

    public boolean isFromDirection() { return frontDirection;}

    public boolean isMovingLeft(){
        return moveLeft;
    }

    public boolean isJumping() { return jumping; }

    public void setAlive(boolean alive) {
        this.alive = alive;
        if (alive == false) {
            this.score = 0;
        }
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }
}