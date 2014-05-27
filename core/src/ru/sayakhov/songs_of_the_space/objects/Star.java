package ru.sayakhov.songs_of_the_space.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Star extends Actor {
	
	private Sound sound, wrong;
	private String note;
	private Sprite img;
	private Texture img_texture;
	
	private Level level;
	
	public Star(String str_img, String str_sound) {
		img_texture = new Texture("images/stars/" + str_img + ".png");
		img_texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		img = new Sprite(img_texture);
		img.setSize(Gdx.graphics.getHeight() * 15 / 100, Gdx.graphics.getHeight() * 15 / 100);
		this.note = str_sound;
		this.sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bells/" + str_sound + ".mp3"));
		this.wrong = Gdx.audio.newSound(Gdx.files.internal("sounds/bells/wrong.mp3"));

		addListener(new ClickListener() {
			@Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				img.setScale(1.2f);
				if (note.equals(level.getCurrentNoteStr())) {
					level.setCurrentNote();
	            	Gdx.input.vibrate(25);
					getSound().play();
				} else {
					level.setCurrentNote(0);
					level.setEndNote(true);
					level.setPlayMusic();
					getWrongSound().play();
					Gdx.input.vibrate(80);
				}
                return true;
            }
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				img.setScale(1.0f);
			}
        });
		setTouchable(Touchable.enabled);
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	@Override
	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
		this.img.setPosition(x, y);
	}
	
	@Override
	public void act(float delta) {
		img.rotate(0.05f);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
	    this.img.draw(batch);
	}
	
	public Sound getSound() {
		return this.sound;
	}
	
	public Sound getWrongSound() {
		return this.wrong;
	}
	
	public String getNote() {
		return this.note;
	}
	
	public Sprite getImg() {
		return this.img;
	}
}
