package ru.sayakhov.songs_of_the_space.objects;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class XMLparse {
	
	private Array<Star> stars = new Array<Star>();
	private Array<Note> notes = new Array<Note>();
	private Map<String, Array<String>> starsPos = new HashMap<String, Array<String>>();
	
	public HashMap<String, String> XMLparseLangs(String lang) {
		HashMap<String, String> langs = new HashMap<String, String>();
		try {
			Element root = new XmlReader().parse(Gdx.files.internal("xml/langs.xml"));
			Array<Element> xml_langs = root.getChildrenByName("lang");
			
			for (Element el : xml_langs) {
				if (el.getAttribute("key").equals(lang)) {
					Array<Element> xml_strings = el.getChildrenByName("string");
					for (Element e : xml_strings) {
						langs.put(e.getAttribute("key"), e.getText());
					}
				} else if (el.getAttribute("key").equals("en")) {
					Array<Element> xml_strings = el.getChildrenByName("string");
					for (Element e : xml_strings) {
						langs.put(e.getAttribute("key"), e.getText());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return langs;
	}
	
	public Array<Star> XMLparseStars() {
		try {
			Element root = new XmlReader().parse(Gdx.files.internal("xml/stars.xml"));
			Array<Element> xml_stars = root.getChildrenByName("star");
			
			for (Element el : xml_stars) {
				Star star = new Star(
					el.getAttribute("files"),
					el.getAttribute("files")
				);
				
				stars.add(star);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.stars;
	}
	
	public Array<String> XMLparseLevels() {
		Array<String> levels = new Array<String>();
		Array<Integer> int_levels = new Array<Integer>();
		
		FileHandle dirHandle;
		if (Gdx.app.getType() == ApplicationType.Android) {
			dirHandle = Gdx.files.internal("xml/levels");
		} else {
			dirHandle = Gdx.files.internal(System.getProperty("user.dir") + "/assets/xml/levels");
		}
		for (FileHandle entry : dirHandle.list()) {
			levels.add(entry.name().split(".xml")[0]);
		}
		
		for (int i = 0; i < levels.size; i++) {
			int_levels.add(Integer.parseInt(levels.get(i)));
		}
		int_levels.sort();
		levels.clear();
		
		for (int i = 0; i < int_levels.size; i++) {
			levels.add(String.valueOf(int_levels.get(i)));
		}
		return levels;
	}
	
	public Array<Note> XMLparseNotes(String strLevel) {
		try {
			Element root = new XmlReader().parse(Gdx.files.internal("xml/levels/" + strLevel + ".xml")).getChildByName("notes");
			Array<Element> xml_notes = root.getChildrenByName("note");
			
			for (Element el : xml_notes) {
				Note note = new Note();
				
				note.setNote(el.getText());
				note.setDelay(el.getAttribute("delay"));
				
				this.notes.add(note);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.notes;
	}
	
	public Map<String, Array<String>> getPos(String strLevel) {
		try {
			Element root = new XmlReader().parse(Gdx.files.internal("xml/levels/" + strLevel + ".xml")).getChildByName("positions");
			
			Array<Element> xml_pos = root.getChildrenByName("position");
			for (Element el : xml_pos) {
				Array<String> xy = new Array<String>();
				xy.add(el.getAttribute("x"));
				xy.add(el.getAttribute("y"));
				this.starsPos.put(el.getAttribute("note"), xy);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.starsPos;
	}
}
