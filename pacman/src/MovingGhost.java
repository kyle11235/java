import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;




//task5\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//新建一个类
public class MovingGhost extends MovingShape implements Monster {


	//task5\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	//实现闪烁的计数
	public static  int VISIBILITY_THRESHOLD = 100;
	private int counter = VISIBILITY_THRESHOLD;
	
	
	public MovingGhost() {
		// TODO Auto-generated constructor stub
	}

	public MovingGhost(int x, int y, int w, int h, int mw, int mh, Color fc,
			Color bc, int pathType) {
		super(x, y, w, h, mw, mh, fc, bc, pathType);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public boolean contains(Point mousePt) {
		return (p.x <= mousePt.x && mousePt.x <= (p.x + width + 1)  &&  p.y <= mousePt.y && mousePt.y <= (p.y + height + 1));

	}

	@Override
	public void draw(Graphics g) {

		//task5\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		//闪烁计数
		counter--;
		if(counter==-VISIBILITY_THRESHOLD){
			counter = VISIBILITY_THRESHOLD;	
		}
		if(counter<=0){
			return;//不画
		}
		
		
		
		//task5\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		//fill是填充，draw是画边框
		//head
		g.setColor(fillColor);
		g.fillOval(p.x, p.y, width, height);
		//eyes
		g.setColor(borderColor);
		switch( this.path.getDirection()){
		case MovingPath.RIGHT:
			g.fillOval(p.x+width*2/4, p.y+height/4, 5, 5);
			g.fillOval(p.x+width*3/4, p.y+height/4, 5, 5);
			break;
		case MovingPath.UP:
			g.fillOval(p.x+width*1/4, p.y+height/8, 5, 5);
			g.fillOval(p.x+width*3/4, p.y+height/8, 5, 5);
		break;
		case MovingPath.LEFT:
			g.fillOval(p.x+width*1/4, p.y+height/4, 5, 5);
			g.fillOval(p.x+width*2/4, p.y+height/4, 5, 5);
			break;
		case MovingPath.DOWN:
			g.fillOval(p.x+width*1/4, p.y+height/3, 5, 5);
			g.fillOval(p.x+width*3/4, p.y+height/3, 5, 5);
			break;
		}
		
		//body
		g.setColor(fillColor);
		g.fillRect(p.x, p.y+height/2, width, height-height/2);
		
		//border
		g.setColor(borderColor);
		g.drawRect(p.x, p.y, width, height);
		drawHandles(g);
	}

	@Override
	public void capture(MovingShape victim) {
		//task6\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		victim.paused  =true;
		A1.frightenCount++;
	}

}
