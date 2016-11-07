package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayerSelectScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private Image playerTerrorist;
    private Image playerSoldier;
    private Label headTitle;

    public PlayerSelectScreen(final Main game) {
        super(game);

        //Stage que crea la escena ajustandolo a la resolucion 640/360
        stage = new Stage(new FitViewport(640, 360));

        // Skin de letras
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        // players
        playerTerrorist = new Image(game.getManager().get("textures/selectplayer/terrorist.png", Texture.class));
        playerTerrorist.setPosition((320/2) - (playerTerrorist.getWidth() / 2), (360/2) - (playerTerrorist.getHeight()/2));
        //playerTerrorist.setSize(200,200);
        playerSoldier = new Image(game.getManager().get("textures/selectplayer/soldier.png", Texture.class));
        playerSoldier.setPosition(((320/2)+320) - (playerSoldier.getWidth() / 2), (360/2) - (playerSoldier.getHeight()/2));
        //playerSoldier.setSize(200,200);

        //se capturan eventos de click
        playerTerrorist.addListener( new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setPlayerType(Constants.TERRORIST);
                game.setScreen(game.gameScreen);
            }

        });

        playerSoldier.addListener( new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setPlayerType(Constants.SOLDIER);
                game.setScreen(game.gameScreen);
            }

        });

        // Skin de letras
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        //creamos un label utilizando el skin de letras cargado previamente, y se lo centra la pantalla.
        headTitle = new Label("Seleccione un tipo de combatiente", skin);
        headTitle.setPosition(320 - headTitle.getWidth() / 2, 300 - headTitle.getHeight() / 2);

        //Se agrega el label como un actor, para que luego el metodo draw dibuje todo a la vez.
        stage.addActor(headTitle);


        //se agregan los objetos al stage para que se visualisen
        stage.addActor(playerTerrorist);
        stage.addActor(playerSoldier);

    }

    @Override
    public void show() {
        //bug 20161014 - botones dejaban de funcionar
        //se activa el input de la stage
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        //bug 20161014 - botones dejaban de funcionar
        //se desactiva el input de la stage
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.3f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}