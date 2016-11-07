package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants;


public class Bullet extends Actor {
    private World world;
    public Body body;
    private Fixture fixture;
    private Texture textureBullet;
    private boolean isFrontDirection;
    private String bulletData;

    public Bullet(World world, Texture textureBullet, boolean isFrontDirection, Vector2 position,int index) {
        this.world = world;
        this.textureBullet = textureBullet;
        this.isFrontDirection = isFrontDirection;

        position.y +=0.17f;

        if(isFrontDirection)
            position.x += 0.5f;
        else position.x -=0.5f;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.0005f, 0.0005f);
        fixture = body.createFixture(box, 3);
        fixture.setUserData("bullet"+index);
        this.bulletData = "bullet"+index;
        box.dispose();

        setSize(Constants.PIXELS_IN_METER/20, Constants.PIXELS_IN_METER/20);
        float speedY = body.getLinearVelocity().y;
        body.setLinearVelocity(isFrontDirection?40:-40, speedY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //elapsedTime += Gdx.graphics.getDeltaTime();
        setPosition(body.getPosition().x * Constants.PIXELS_IN_METER, body.getPosition().y * Constants.PIXELS_IN_METER);
        batch.draw(this.textureBullet, getX(), getY(), getWidth(), getHeight());

    }

    public String getBulletData(){
        return this.bulletData;
    }

    @Override
    public void act(float delta) {

    }

    public void setVelocity(int x, int y){
        body.setLinearVelocity(0, 0);
    }


}
