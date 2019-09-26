/*
 *  ===============================================================================
 *  MovingCircle.java : A shape that is an oval.
 *  An oval/circle has 4 handles shown when it is selected (by clicking on it).
 *  ===============================================================================
 */
import java.awt.*;
public class MovingCircle extends MovingShape {

	/** constructor to create an oval with default values
	 */
	public MovingCircle() {
	 super();
	}
	
	/** constructor to create an oval shape
	 */
	public MovingCircle(int x, int y, int w, int h, int mw, int mh, Color fc, Color bc, int pathType) {
		super(x ,y ,w ,h ,mw ,mh ,fc ,bc , pathType);
	}
	
	/** draw the oval with the fill colour
	 *  If it is selected, draw the handles
	 *  @param g	the Graphics control
	 */
	public void draw(Graphics g) {
		g.setColor(fillColor);
		g.fillOval(p.x, p.y, width, height);
		g.setColor(borderColor);
		g.drawOval(p.x, p.y, width, height);
		drawHandles(g);
	}

	/** Returns whether the point is in the oval or not
	 * @return true if and only if the point is in the oval, false otherwise.
	 */
	public boolean contains(Point mousePt) {
		double dx, dy;
		Point EndPt = new Point(p.x + width, p.y + height);
		dx = (2 * mousePt.x - p.x - EndPt.x) / (double) width;
		dy = (2 * mousePt.y - p.y - EndPt.y) / (double) height;
		return dx * dx + dy * dy < 1.0;
	}
}