package ru.sayakhov.songs_of_the_space.desktop;

import ru.sayakhov.songs_of_the_space.MyGame;
import ru.sayakhov.songs_of_the_space.managers.IActivityRequestHandler;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher implements IActivityRequestHandler {
	
	private static DesktopLauncher application;
	
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Song's of the Space";
		config.width = 800;
		config.height = 480;
		
		if (application == null) {
            application = new DesktopLauncher();
        }
		
		new LwjglApplication(new MyGame(application), config);
	}
	
	@Override
    public void showAds(boolean show) {
		// TODO Auto-generated method stub
    }
}
