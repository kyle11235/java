/*
 *  =============================================================
 *  Monster.java : An interface to denote a monster shape. 
 *  Monster shapes must implement the capture() method that 
 *  describes what the monster does to its victim. 
 *  ==============================================================
 */

public interface Monster {
	
	public void capture(MovingShape victim);
	
}
