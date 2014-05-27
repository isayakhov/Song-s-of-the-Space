package ru.sayakhov.songs_of_the_space.managers;

import ru.sayakhov.songs_of_the_space.MyGame;
import ru.sayakhov.songs_of_the_space.objects.GamePreferences;
import ru.sayakhov.songs_of_the_space.objects.Level;
import ru.sayakhov.songs_of_the_space.objects.PlayStage;
import ru.sayakhov.songs_of_the_space.objects.PlayStage.OnHardKeyListener;
import ru.sayakhov.songs_of_the_space.objects.Star;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayScreen implements Screen {
	
	final MyGame game;
	
	private GamePreferences pref;
    
	private Level level;
	private String sL, nL;
	private Array<Star> stars;

    private PlayStage stage;
    private Table table, table2;
	
	public PlayScreen(final MyGame gam, String strLevel, String strNextLevel) {
        game = gam;
		this.sL = strLevel;
		this.nL = strNextLevel;
		
		stage = new PlayStage(new ScreenViewport());
		
		game.getHandler().showAds(false);
		
		stage.addActor(game.background);
		
		pref = new GamePreferences();
		
		level = new Level(strLevel);
		stars = level.getStars();
		
		level.setCurrentNote(0);
		
		for (final Star s : stars) {
			stage.addActor(s);
		}
		
		LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = game.font;
        
        Skin skin = new Skin();
		TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("images/game/images.pack"));
        skin.addRegions(buttonAtlas);
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = game.font;
        textButtonStyle.up = skin.getDrawable("button-up");
        textButtonStyle.down = skin.getDrawable("button-down");
        textButtonStyle.checked = skin.getDrawable("button-up");
		
		table = new Table();
		table.padTop(20);
		table.center().top();
		table.setFillParent(true);
		
		Label label = new Label(game.langStr.get("Constellation"), labelStyle);
		table.add(label);
		table.row().padBottom(30);
		label = new Label(game.langStr.get("level_" + strLevel), labelStyle);
		table.add(label);
		
		table.setVisible(false);
		
		stage.addActor(table);
		
        table2 = new Table();
        table2.center().bottom();
        table2.setFillParent(true);
        table2.row().colspan(2).padBottom(30);
        label = new Label(game.langStr.get("YouWin"), labelStyle);
        table2.add(label).bottom();
        table2.row().padBottom(20);
        TextButton button = new TextButton(game.langStr.get("Again"), textButtonStyle);
        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
    			Gdx.input.vibrate(20);
    			return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            	game.setScreen(new PlayScreen(game, sL, nL));
    			dispose();
            };
        });
        table2.add(button);
        button = new TextButton(game.langStr.get("Levels"), textButtonStyle);
        button.addListener(new ClickListener() {
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
		table2.add(button);
        table2.setVisible(false);
        
        stage.addActor(table2);
		
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);
		stage.setHardKeyListener(new OnHardKeyListener() {          
	        @Override
	        public void onHardKey(int keyCode, int state) {
	            if(keyCode==Keys.BACK && state==1){
	            	game.setScreen(new LevelScreen(game));    
	            }       
	        }
	    });
    }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
		level.playStars();
		
		if (level.isWin()) {
			table.setVisible(true);
			table2.setVisible(true);
			pref.setLevel(nL);
			for (Star s : stars) {
				s.setTouchable(Touchable.disabled);
			}
		}
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