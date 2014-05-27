package ru.sayakhov.songs_of_the_space.objects;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

public class Level {
	
	private XMLparse xml_parse;
	private Array<Note> notes = new Array<Note>();
	private Array<Star> stars = new Array<Star>();
	private Map<String, Array<String>> starsPos = new HashMap<String, Array<String>>();
	
	private int currentNote;
	private int endNote;
	
	private float delay;
	private boolean playMusic;
	
	private boolean win;
	
	private final Sound winner = Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3"));
	
	public Level(String level) {
		xml_parse = new XMLparse();
		Array<Star> xml_stars = xml_parse.XMLparseStars();
		notes = xml_parse.XMLparseNotes(level);
		starsPos = xml_parse.getPos(level);
		endNote = 3;
		delay = 0;
		this.win = false;
		
		setPlayMusic();
		
		for (Note n : this.notes) {
			for (Star s : xml_stars) {
				if (n.getNote().equals(s.getNote()) && !this.stars.contains(s, true)) {
					this.stars.add(s);
				}
				if (n.getNote().equals(s.getNote())) n.setStar(s);
			}
		}

		for (Star s : this.stars) {
			s.setLevel(this);
			s.setBounds(
				Gdx.graphics.getWidth() * Float.parseFloat(starsPos.get(s.getNote()).get(0)) / 100,
				Gdx.graphics.getHeight() * Float.parseFloat(starsPos.get(s.getNote()).get(1)) / 100 - s.getImg().getHeight() / 2,
				s.getImg().getWidth(),
				s.getImg().getHeight()
			);
		}
	}
	
	public boolean isWin() {
		return this.win;
	}
	
	public void setEndNote() {
		if (this.endNote < this.notes.size - 1) {
			this.endNote += 4;
		}
	}
	
	public void setEndNote(boolean begin) {
		if (begin) {
			this.endNote = 3;
		}
	}
	
	public void setCurrentNote(int note) {
		this.currentNote = note;
	}
	
	public void setCurrentNote() {
		if (this.currentNote < this.notes.size - 1) {
			this.currentNote++;
			if (currentNote - 1 == endNote) {
				currentNote = 0;
				setEndNote();
				setPlayMusic();
			}
		} else {
			this.endNote = notes.size - 1;
			this.currentNote = 0;
			this.win = true;
			this.winner.play();
		}
	}
	
	public int getCurrentNote() {
		return this.currentNote;
	}
	
	public String getCurrentNoteStr() {
		return this.notes.get(this.currentNote).getNote();
	}
	
	public Array<Note> getNotes() {
		return this.notes;
	}
	
	public Array<Star> getStars() {
		return this.stars;
	}
	
	
	public void setPlayMusic() {
		if (playMusic) {
			playMusic = false;
		} else {
			playMusic = true;
		}
	}
	
	public void playStars() {
		if (playMusic) {
			for (Star s : stars) {
				s.setTouchable(Touchable.disabled);
			}
			if (getCurrentNote() < notes.size) {
				if (getCurrentNote() <= endNote) {
					Note note = notes.get(getCurrentNote());
					
					delay += note.getDelay();
					
					if (delay >= 0.9f) note.getStar().getImg().setScale(1.2f);
					
					if (delay >= 1.0f) {
						delay = 0;
						setCurrentNote(currentNote + 1);
						note.getStar().getSound().play();
						note.getStar().getImg().setScale(1f);
					}
				} else {
					setPlayMusic();
					setCurrentNote(0);
				}
			} else {
				delay = 0;
				setCurrentNote(0);
				setPlayMusic();
			}
		} else {
			for (Star s : stars) {
				s.setTouchable(Touchable.enabled);
			}
		}
	}
}
