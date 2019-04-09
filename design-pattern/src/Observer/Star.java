package Observer;

import java.util.Observable;

public class Star extends Observable {

	private String name;
	private String newSong;
	public Star(String name){
		this.name = name;
	}
	public String getNewSong() {
		return newSong;
	}
	public void setNewSong(String newSong) {
		this.newSong = newSong;
		this.setChanged();//本方法是受保护的
	}
	public String getName() {
		return name;
	}
	

}
