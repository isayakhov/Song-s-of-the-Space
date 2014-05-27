package ru.sayakhov.songs_of_the_space.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePreferences {
	private Preferences pref;
	
	private static final String PREFS_NAME = "SONGS_OF_THE_SPACE";
	private static final String PREF_LEVEL = "LEVEL_";
	
	public GamePreferences() {
		pref = Gdx.app.getPreferences(PREFS_NAME);
	}
    
    public boolean getLevel(String level) {
    	pref.putBoolean(PREF_LEVEL + 1, true);
    	pref.flush();
        return pref.getBoolean(PREF_LEVEL + level, false);
    }
 
    public void setLevel(String level) {
    	pref.putBoolean(PREF_LEVEL + level, true);
    	pref.flush();
    }
}
