package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private Image logo;
    private TextButton play;

    public MenuScreen(final Main game) {
        super(game);

        //Stage que crea la escena ajustandolo a la resolucion 640/360
        stage = new Stage(new FitViewport(640, 360));

        // Skin de letras
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        //boton play del menu, para iniciar el juego
        play = new TextButton("Play", skin);

        // logo del juego
        //logo = new Image(game.getManager().get("logo.png", Texture.class));

        //se capturan eventos, por si se pulso el boton play
        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //se va a la pantalla de inicio del juego
                game.setScreen(game.playerSelectScreen);
            }
        });

        //Ubicacion de los objetos
        play.setSize(200, 80);
        play.setPosition(40, 140);

        //se agregan los objetos al stage para que se visualisen
        stage.addActor(play);
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