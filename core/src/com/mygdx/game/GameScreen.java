package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.entities.*;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private Vector3 position;
    private Player player;
    private Factory factory;
    private Skin skin;
    private Label score;

    private List<Floor> floorList = new ArrayList<Floor>();
    private List<Bullet> bulletList = new ArrayList<Bullet>();

    private List<Zombie> zombieList = new ArrayList<Zombie>();

    private List<Body> bodyDeleteList = new ArrayList<Body>();

    public GameScreen(Main game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        position = new Vector3(stage.getCamera().position);

        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new GameContactListener());
    }

    @Override
    public void show() {
        factory = new Factory(game.getManager());

        player = factory.createPlayer(world, new Vector2(1.5f, 1.5f), game.getPlayerType());
        floorList.add(factory.createFloor(world, 0, 1000, 1));

        for(int i = 1;i<50;i++)
            zombieList.add(factory.createZombie(world, new Vector2(12.5f*i, 1.5f)));

        for (Floor floor : floorList)
            stage.addActor(floor);

        for (Zombie zombie : zombieList)
            stage.addActor(zombie);

        stage.addActor(player);
        stage.getCamera().position.set(position);
        stage.getCamera().update();

    }

    @Override
    public void hide() {
        stage.clear();
        player.detach();

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.4f, 0.5f, 0.6f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        world.step(delta, 6, 2);

        if (player.isAlive()) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.moveLeft();
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.moveRight();
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)){
                player.jump();
            } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                player.crouch();
            } else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                player.shoot();
                Bullet bullet = factory.createBullet(world, player.isFromDirection(), player.getPosition());
                bulletList.add(bullet);
                stage.addActor(bullet);
            } else if(!player.isJumping()){
                player.quiet();
            }

        } else {
            player.die();
        }

        if (player.isAlive() && player.isMoving()) {
            float speed = player.getLinearVelocity().x * delta * (Constants.PIXELS_IN_METER);
            stage.getCamera().translate(speed-speed/100, 0, 0);
        }

        stage.draw();
        for(int i = 0; i<bodyDeleteList.size();i++){
            Body body = bodyDeleteList.get(i);
            if(!world.isLocked() && body != null){
                world.destroyBody(body);
                bodyDeleteList.remove(body);
            }
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

    private class GameContactListener implements ContactListener {
        private boolean isColisioned(Contact contact, Object userA, Object userB) {
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            if (userDataA == null || userDataB == null) {
                return false;
            }
            return (userDataA.equals(userA) && userDataB.equals(userB)) ||
                    (userDataA.equals(userB) && userDataB.equals(userA));
        }
        private String isColisionedBullet(Contact contact) {
            String userDataA = (String)contact.getFixtureA().getUserData();
            String userDataB = (String)contact.getFixtureB().getUserData();
            String bullet;

            if (userDataA == null || userDataB == null) {
                return "0;0;0";
            }

            if(userDataA.length() >=6 && userDataA.substring(0,6).equals("bullet")){
                bullet = userDataA;
                if(userDataB.equals("floor")){
                    return "1;"+bullet+";"+userDataB;
                }else if(userDataB.length() >=6 && userDataB.substring(0,6).equals("zombie")){
                    return "2;"+bullet+";"+userDataB;
                }

            }else if(userDataB.length() >=6 && userDataB.substring(0,6).equals("bullet")){
                bullet = userDataB;
                if(userDataA.equals("floor")){
                    return "1;"+bullet+";"+userDataA;
                }else if(userDataA.length() >=6 && userDataA.substring(0,6).equals("zombie")){
                    return "2;"+bullet+";"+userDataA;
                }
            }
            return "0;0;0";
        }

        public void removeBullet(String bulletData){
            for(Bullet bullet:bulletList){
                if(bullet.getBulletData().equals(bulletData)){
                    bullet.setVelocity(0,0);
                    bodyDeleteList.add(bullet.body);
                    bullet.remove();
                    bulletList.remove(bullet);
                    return;
                }
            }
        }

        public void removeZombie(String zombieData){
            for(Zombie zombie:zombieList){
                if(zombie.getZombieData().equals(zombieData)){
                    bodyDeleteList.add(zombie.body);
                    zombie.remove();
                    zombieList.remove(zombie);

                    return;
                }
            }
        }

        @Override
        public void beginContact(Contact contact) {
            // El personaje esta sobre el piso
            if (isColisioned(contact, "player", "floor")) {
                player.setJumping(false);
            }
            String bulletReturn = isColisionedBullet(contact);
            String type = bulletReturn.split(";")[0];
            String bulletIndex = bulletReturn.split(";")[1];
            String objectIndex = bulletReturn.split(";")[2];
            if(type.equals("1")){
                removeBullet(bulletIndex);
            } else if(type.equals("2")) {
                //si la bala colisiono con un zombie
                removeBullet(bulletIndex);
                removeZombie(objectIndex);
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
