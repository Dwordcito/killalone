package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static javax.swing.JOptionPane.showMessageDialog;


public class LoadingScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private Label loading;

    public LoadingScreen(Main game) {
        super(game);

        //Stage que crea la escena ajustandolo a la resolucion 640/360
        stage = new Stage(new FitViewport(640, 360));

        try {
            // Skin de letras
            skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
            //creamos un label utilizando el skin de letras cargado previamente, y se lo centra la pantalla.
            loading = new Label("Cargando...", skin);
            loading.setPosition(320 - loading.getWidth() / 2, 180 - loading.getHeight() / 2);

            //Se agrega el label como un actor, para que luego el metodo draw dibuje todo a la vez.
            stage.addActor(loading);
        } catch (Exception ex){
            showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public void render(float delta) {
        //Este metodo se ejecuta 30 veces por segundos aproximadamente (lo que el ojo detecta como fluido en animacion)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //El metodo Update informa si se terminaron de cargar los objetos graficos.
        if (game.getManager().update()) {
            //Llamamos al metodo finishloading para que cambie de pantalla (al menu)
            game.finishLoading();
        } else {
            //el metodo getProgress devuelte un flotante que indica el porcentaje de carga
            int progress = (int) (game.getManager().getProgress() * 100);
            loading.setText("Cargando... " + progress + "%");
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}