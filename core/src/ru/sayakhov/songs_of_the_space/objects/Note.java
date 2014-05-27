package ru.sayakhov.songs_of_the_space.objects;

public class Note {
	private String note;
	private float delay;
	private Star star;
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getNote() {
		return this.note;
	}
	
	public void setDelay(String delay) {
		this.delay = Float.parseFloat(delay);
	}
	
	public float getDelay() {
		return this.delay;
	}
	
	public void setStar(Star star) {
		this.star = star;
	}
	
	public Star getStar() {
		return this.star;
	}
}
