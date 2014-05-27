package ru.sayakhov.songs_of_the_space;

import java.util.HashMap;

import ru.sayakhov.songs_of_the_space.managers.IActivityRequestHandler;
import ru.sayakhov.songs_of_the_space.managers.MainMenuScreen;
import ru.sayakhov.songs_of_the_space.objects.BackgroundActor;
import ru.sayakhov.songs_of_the_space.objects.XMLparse;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class MyGame extends Game {
	
	private IActivityRequestHandler myRequestHandler;

	public BitmapFont font, levels;
    private static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    
    public BackgroundActor background;
    
    public HashMap<String, String> langStr = new HashMap<String, String>();
    
    public MyGame(IActivityRequestHandler handler) {
    	myRequestHandler = handler;
    }
    
    public IActivityRequestHandler getHandler() {
    	return myRequestHandler;
    }
    
	@Override
	public void create() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/russoone.ttf"));
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = Gdx.graphics.getHeight() / 18;
		param.characters = FONT_CHARACTERS;
		font = generator.generateFont(param);
		param.size = Gdx.graphics.getHeight() / 20;
		levels = generator.generateFont(param);
        font.setColor(Color.WHITE);
        levels.setColor(Color.WHITE);
        generator.dispose();
        
        background = new BackgroundActor();
        background.setPosition(0, 0);
        
        XMLparse xmlParse = new XMLparse();
        String locale = java.util.Locale.getDefault().toString().split("_")[0];
        langStr = xmlParse.XMLparseLangs(locale);

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
}
