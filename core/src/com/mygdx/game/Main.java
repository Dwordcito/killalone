package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.CSVManager;

import java.util.Vector;


public class Main extends Game {
	private AssetManager manager;
	public BaseScreen loadingScreen, menuScreen, gameScreen, playerSelectScreen, scoreScreen;
	public Vector<Label> vectorScore = new Vector<Label>();

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	private String playerName;
	private int playerType;
	private CSVManager csv = new CSVManager();

	@Override
	public void create() {
		manager = new AssetManager();
		//Cargamos los archivos atlas(formato de mapa de imagenes)
		manager.load("textures/terrorist/terroristRun.atlas", TextureAtlas.class);
		manager.load("textures/terrorist/terroristJump.atlas", TextureAtlas.class);
		manager.load("textures/terrorist/terroristShoot.atlas", TextureAtlas.class);
		manager.load("textures/terrorist/terroristBend.atlas", TextureAtlas.class);
		manager.load("textures/soldier/soldierRun.atlas", TextureAtlas.class);
		manager.load("textures/soldier/soldierJump.atlas", TextureAtlas.class);
		manager.load("textures/soldier/soldierShoot.atlas", TextureAtlas.class);
		manager.load("textures/soldier/soldierBend.atlas", TextureAtlas.class);
		manager.load("textures/zombie/zombie.atlas", TextureAtlas.class);

		manager.load("textures/bullet.png", Texture.class);

		manager.load("textures/selectplayer/terrorist.png", Texture.class);
		manager.load("textures/selectplayer/soldier.png", Texture.class);

		manager.load("textures/floor.png", Texture.class);
		//carga de resultados de un archivo csv

		//Instanciamos la pantalla de carga
		loadingScreen = new LoadingScreen(this);
		//hacemos foco en la escena de carga.
		setScreen(loadingScreen);
	}

	public void setPlayerType(int playerType){
		this.playerType = playerType;
	}

	public int getPlayerType(){
		return this.playerType;
	}
	public void finishLoading() {
		//Cuando termina de cargar todo, instanciamos todas las pantallas y hacemos foco en la escena principal
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		playerSelectScreen = new PlayerSelectScreen(this);
		scoreScreen = new ScoreScreen(this);
		setScreen(menuScreen);
	}

	public AssetManager getManager() {
		return manager;
	}

	public CSVManager getCSV(){
		return this.csv;
	}
}