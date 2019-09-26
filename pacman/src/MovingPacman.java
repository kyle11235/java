/*
 *  ===============================================================================
 *  MovingPacman.java : A shape that is Pacman.
 *  ===============================================================================
 */
import java.awt.*;
import java.awt.geom.Arc2D;
public class MovingPacman extends MovingCircle  implements Monster{

    private static final int CLOSING    = 0;
    private static final int OPENING     = 1;

    private int mouthAngle = 90;
  
    private int mouth = CLOSING;

	/** constructor to create an oval with default values
	 */
	public MovingPacman() {
		super();

	}

	/** constructor to create an oval shape
	 */
	public MovingPacman(int x, int y, int w, int h, int mw, int mh, Color fc, Color bc, int pathType) {
		super(x ,y ,w ,h ,mw ,mh ,fc ,bc , pathType);

	}

	/** draw the pacman with the fill colour
	 *  If it is selected, draw the handles
	 *  @param g	the Graphics control
	 */
	public void draw(Graphics g) {
		
		if (mouth == CLOSING) {
			mouthAngle -= 2;
        } else if (mouth == OPENING) {
			mouthAngle += 2;
		}
		if (mouthAngle == 0)
			mouth = OPENING;
		else if (mouthAngle > 60)
			mouth = CLOSING;
		
		Graphics2D g2d = (Graphics2D) g;
		
		int angleStart = mouthAngle/2;
		
		//task3\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	    //增加以下几行代码，在这里获得父类的direction，并设定张嘴的方向
		//java 2d画扇形，也就是画face，是把3点方向当0度，逆时针一圈是360度，
		//如果想让嘴向上就给angleStart加90度
		
		switch( this.path.getDirection()){
		case MovingPath.RIGHT:angleStart =mouthAngle/2;break;
		case MovingPath.UP:angleStart = mouthAngle/2+90;break;
		case MovingPath.LEFT:angleStart = mouthAngle/2+180;break;
		case MovingPath.DOWN:angleStart = mouthAngle/2+270;break;
		}
		
		
		
		
		int angleExtent = 360 - mouthAngle;
		
		// Draw Arcs
		g.setColor(borderColor);
		g2d.draw(new Arc2D.Float(p.x, p.y,width, height, angleStart, angleExtent, Arc2D.PIE));
		g.setColor(fillColor);
		g2d.fill(new Arc2D.Float(p.x, p.y,width, height, angleStart, angleExtent, Arc2D.PIE));
		drawHandles(g);
	}

	@Override
	public void capture(MovingShape victim) {
		//task6\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		victim.swallowed  =true;
		
		
		A1.eatenCount++;
	}
	
}