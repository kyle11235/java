/*
 *  ===============================================================================
 *  MovingSquare.java : A shape that is a square.
 *  ===============================================================================
 */
import java.awt.*;
public class MovingSquare extends MovingRectangle {

	/** constructor to create a rectangle with default values
	 */
	public MovingSquare() {
		super();
	}

	/** constructor to create a rectangle shape
	 */
	public MovingSquare(int x, int y, int s, int mw, int mh, Color fc, Color bc, int typeOfPath) {
		super(x,y,s,s,mw,mh,fc,bc, typeOfPath);
	}

	/** Set the width of the shape.
		 * @param w 	the width value
		 */
	public void setWidth(int w) {
		super.setWidth(w);
		super.setHeight(w);
	}

	/** Set the height of the shape.
	 * @param h 	the height value
	 */
	public void setHeight(int h) {
		super.setWidth(h);
		super.setHeight(h);
	}

}