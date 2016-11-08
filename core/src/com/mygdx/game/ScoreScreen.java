package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.data.Score;

import java.util.Vector;

public class ScoreScreen extends BaseScreen {
    private Stage stage;
    private Main game;
    private Vector<Score> scoreList;
    private TextButton play;
    private TextButton back;
    private Skin skin;
    private VerticalGroup vg;

    public ScoreScreen(final Main game) {
        super(game);
        this.game = game;

        //Stage que crea la escena ajustandolo a la resolucion 640/360
        stage = new Stage(new FitViewport(640, 360));

        // Skin de letras
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        play = new TextButton("Play", skin);
        back = new TextButton("Back", skin);

        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //se va a la pantalla de inicio del juego
                game.setScreen(game.playerSelectScreen);
            }
        });

        back.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //se va a la pantalla de inicio del juego
                game.setScreen(game.menuScreen);
            }
        });

        play.setSize(200, 60);
        play.setPosition(290, 60);

        back.setSize(200, 60);
        back.setPosition(40, 60);

        stage.addActor(play);
        stage.addActor(back);


    }

    @Override
    public void show() {
        if(vg!=null)vg.remove();
        vg = new VerticalGroup();
        scoreList = game.getCSV().loadScore();
        Gdx.input.setInputProcessor(stage);
        for(Score score : scoreList) {
            vg.addActor(new Label(score.getName() + " - " + score.getScore(), skin));
        }
        vg.pack();
        vg.setPosition((640/2)-(vg.getWidth()/2),360-vg.getHeight());
        stage.addActor(vg);

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.33f, 0.33f, 0.33f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

}