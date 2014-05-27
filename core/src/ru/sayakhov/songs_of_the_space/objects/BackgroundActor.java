package ru.sayakhov.songs_of_the_space.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundActor extends Actor {
	private Texture backgroundTexture;
	private Sprite backgroundSprite;
	
	public BackgroundActor() {
		backgroundTexture = new Texture("images/sky.jpg");
		backgroundSprite = new Sprite(backgroundTexture);
		backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void draw(Batch batch, float alpha) {
	    backgroundSprite.draw(batch);
	}
}
