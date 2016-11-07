package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;

/**
 * Created by octavio on 06/11/16.
 */
public class Zombie extends Actor {
    private Animation animationMove;
    private Animation animationMoveLeft;

    private Animation currentAnimation;

    private World world;
    public Body body;
    private Fixture fixture;

    private String zombieData;

    private float elapsedTime = 0;

    public Zombie(World world, TextureAtlas textureMove, Vector2 position, int index){
        this.world = world;
        this.animationMove = new Animation(1/6f, textureMove.getRegions());

        this.animationMoveLeft = new Animation(1/6f, invertRegions(textureMove));

        this.currentAnimation = this.animationMove;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(box, 3);
        fixture.setUserData("zombie"+index);
        zombieData = "zombie"+index;
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

    @Override
    public void draw(Batch batch, float parentAlpha) {

        elapsedTime += Gdx.graphics.getDeltaTime();

        setPosition((body.getPosition().x - 0.5f) * Constants.PIXELS_IN_METER,
                (body.getPosition().y - 0.5f) * Constants.PIXELS_IN_METER);

        batch.draw(currentAnimation.getKeyFrame(elapsedTime,true), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        float speedY = body.getLinearVelocity().y;
        body.setLinearVelocity(-Constants.PLAYER_SPEED/8, speedY);
    }
    public String getZombieData(){
        return this.zombieData;
    }

}
