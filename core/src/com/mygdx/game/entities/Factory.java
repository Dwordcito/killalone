package com.mygdx.game.entities;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Factory {

    private AssetManager manager;
    private int bulletIndex = 0;
    private int zombieIndex = 0;
    public Factory(AssetManager manager) {
        this.manager = manager;
    }

    public Player createPlayer(World world, Vector2 position, int iPlayerType) {
        TextureAtlas textureMove;
        TextureAtlas textureJump;
        TextureAtlas textureCrouch;
        TextureAtlas textureShoot;
        if(iPlayerType == 1){
            textureMove = manager.get("textures/terrorist/terroristRun.atlas");
            textureJump = manager.get("textures/terrorist/terroristJump.atlas");
            textureCrouch = manager.get("textures/terrorist/terroristBend.atlas");
            textureShoot= manager.get("textures/terrorist/terroristShoot.atlas");
        } else {
            textureMove = manager.get("textures/soldier/soldierRun.atlas");
            textureJump = manager.get("textures/soldier/soldierJump.atlas");
            textureCrouch = manager.get("textures/soldier/soldierBend.atlas");
            textureShoot= manager.get("textures/soldier/soldierShoot.atlas");
        }
        return new Player(world, textureMove, textureJump, textureCrouch, textureShoot, position);
    }
    public Bullet createBullet(World world, boolean isFrontDirection, Vector2 position) {
        Texture textureBullet = manager.get("textures/bullet.png");
        bulletIndex++;
        return new Bullet(world, textureBullet, isFrontDirection, position, bulletIndex);
    }

    public Floor createFloor(World world, float x, float width, float y) {
        Texture floorTexture = manager.get("textures/floor.png");
        return new Floor(world, floorTexture, x, width, y);
    }

    public Zombie createZombie(World world, Vector2 position) {
        TextureAtlas textureMove = manager.get("textures/zombie/zombie.atlas");
        zombieIndex++;
        return new Zombie(world, textureMove, position, zombieIndex);
    }

}