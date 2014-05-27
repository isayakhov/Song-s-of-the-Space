package ru.sayakhov.songs_of_the_space.managers;

import ru.sayakhov.songs_of_the_space.MyGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
	
	final MyGame game;

    private Stage stage;
    private TextButton play, exit;
    private Table table;
    private LabelStyle labelStyle;
	
	public MainMenuScreen(final MyGame gam) {
        game = gam;
		
		stage = new Stage(new ScreenViewport());
		
		stage.addActor(game.background);
		
		game.getHandler().showAds(true);
		
		Skin skin = new Skin();
		TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("images/game/images.pack"));
        skin.addRegions(buttonAtlas);
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = game.font;
        textButtonStyle.up = skin.getDrawable("button-up");
        textButtonStyle.down = skin.getDrawable("button-down");
        textButtonStyle.checked = skin.getDrawable("button-up");

        labelStyle = new LabelStyle();
        labelStyle.font = game.font;
        table = new Table();
        table.setFillParent(true);

        play = new TextButton(game.langStr.get("Play"), textButtonStyle);
        play.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
    			Gdx.input.vibrate(20);
    			return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
    			game.setScreen(new LevelScreen(game));
    			dispose();
            };
        });
        exit = new TextButton(game.langStr.get("Exit"), textButtonStyle);
        exit.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
    			Gdx.input.vibrate(20);
    			return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            	Gdx.app.exit();
    			dispose();
            };
        });
        table.add(play);
        table.row();
        table.add(exit);
        stage.addActor(table);
 
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
    }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		stage.dispose();
		game.dispose();
	}
}
