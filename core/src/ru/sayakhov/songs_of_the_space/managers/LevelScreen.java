package ru.sayakhov.songs_of_the_space.managers;

import ru.sayakhov.songs_of_the_space.MyGame;
import ru.sayakhov.songs_of_the_space.objects.GamePreferences;
import ru.sayakhov.songs_of_the_space.objects.PlayStage;
import ru.sayakhov.songs_of_the_space.objects.PlayStage.OnHardKeyListener;
import ru.sayakhov.songs_of_the_space.objects.XMLparse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LevelScreen implements Screen {
	
	final MyGame game;
	
	private GamePreferences pref;
	
	private PlayStage stage;
    private Table table;
    private LabelStyle labelStyle;
    private TextButton level;
    
    private Array<String> levels;
	
	public LevelScreen(MyGame gam) {
		game = gam;
		
		stage = new PlayStage(new ScreenViewport());
		
		game.getHandler().showAds(true);
		
		stage.addActor(game.background);
		
		pref = new GamePreferences();
		
		Skin skin = new Skin();
		TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("images/game/images.pack"));
        skin.addRegions(buttonAtlas);
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = game.levels;
        textButtonStyle.up = skin.getDrawable("level-up");
        textButtonStyle.down = skin.getDrawable("level-down");
        textButtonStyle.checked = skin.getDrawable("level-up");
        
        TextButtonStyle lockButtonStyle = new TextButtonStyle();
        lockButtonStyle.font = game.levels;
        lockButtonStyle.up = skin.getDrawable("level-lock");
        lockButtonStyle.down = skin.getDrawable("level-lock");
        lockButtonStyle.checked = skin.getDrawable("level-lock");
		
		XMLparse parseLevels = new XMLparse();
        levels = parseLevels.XMLparseLevels();

        labelStyle = new LabelStyle();
        labelStyle.font = game.levels;
        table = new Table();
        table.row().pad(20);
        table.center();
        table.setFillParent(true);

        for (int i = 0; i < levels.size; i++) {
        	final String cur_level = levels.get(i);
        	final String next_level;
        	if (i + 1 < levels.size) {
        		next_level = levels.get(i + 1);
        	} else {
        		next_level = "null";
        	}
        	level = new TextButton(cur_level, textButtonStyle);
        	if (pref.getLevel(cur_level)) {
        		level = new TextButton(cur_level, textButtonStyle);
        	} else {
        		level = new TextButton("", lockButtonStyle);
        		level.setTouchable(Touchable.disabled);
        	}
	        level.addListener(new ClickListener() {
	            @Override
	            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    			Gdx.input.vibrate(20);
	    			return true;
	            };
	            @Override
	            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	    			game.setScreen(new PlayScreen(game, cur_level, next_level));
	    			dispose();
	            };
	        });
	        table.add(level);
	        float indexLevel = Float.parseFloat(String.valueOf(i)) + 1;
	        if (indexLevel % 5.0f == 0) table.row().padLeft(20).padRight(20).padBottom(20);
        }
        stage.addActor(table);
 
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        stage.setHardKeyListener(new OnHardKeyListener() {          
	        @Override
	        public void onHardKey(int keyCode, int state) {
	            if(keyCode==Keys.BACK && state==1){
	            	game.setScreen(new MainMenuScreen(game));    
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
