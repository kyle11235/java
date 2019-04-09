package Observer;

import java.util.Observable;
import java.util.Observer;

import util.P;

public class Fans implements Observer{

	private String name;
	public Fans(String name){
		this.name = name;
	}
	@Override
	public void update(Observable arg0, Object arg) {
		
		Star s = (Star)arg;
		
		P.p(name+" is listening : "+s.getName()+"'s "+s.getNewSong());
		
	}

}
