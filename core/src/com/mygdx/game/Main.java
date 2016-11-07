package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Vector;


public class Main extends Game {
	private AssetManager manager;
	public BaseScreen loadingScreen, menuScreen, gameScreen, playerSelectScreen, scoreScreen;
	public Vector<Label> vectorScore = new Vector<Label>();

	@Override
	public void create() {
		manager = new AssetManager();
		//Cargamos los archivos atlas(formato de mapa de imagenes)
		manager.load("terrorist.png", TextureAtlas.class);
		manager.load("soldier.png", TextureAtlas.class);
		manager.load("logo.png", Texture.class);
		//carga de resultados de un archivo csv

		//Instanciamos la pantalla de carga
		loadingScreen = new LoadingScreen(this);
		//hacemos foco en la escena de carga.
		setScreen(loadingScreen);
	}

	public void finishLoading() {
		//Cuando termina de cargar todo, instanciamos todas las pantallas y hacemos foco en la escena principal
		menuScreen = new MenuScreen(this);
		//gameScreen = new GameScreen(this);
		playerSelectScreen = new PlayerSelectScreen(this);
		scoreScreen = new ScoreScreen(this);
		setScreen(menuScreen);
	}

	public AssetManager getManager() {
		return manager;
	}
}