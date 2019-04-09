package Observer;

public class Foo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Star star1 = new Star("刘若英");
		Fans fans1 = new Fans("fans1");
		Fans fans2 = new Fans("fans2");
		star1.addObserver(fans1);
		star1.addObserver(fans2);
		
		star1.setNewSong("月亮代表我的心");
		star1.notifyObservers(star1);
	}

}
