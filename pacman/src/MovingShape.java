/*
 *  ===============================================================================
 *  MovingShape.java : The superclass of all shapes.
 *  A shape has a point (top-left corner).
 *  A shape defines various properties, including selected, colour, width and height.
 *  ===============================================================================
 */

import java.awt.*;
public abstract class MovingShape {

	public int marginWidth, marginHeight; // the margin of the animation panel area
	protected Point p; 					// the top left coner of shapes
	protected int width, height;			// the width and height of shapes
	protected Color fillColor; 			// the fill colour of shapes
	protected MovingPath path;			// the moving path of shapes
	protected boolean selected = false;  // draw handles if selected
	protected Color borderColor; 			// the border colour of shapes

	 //task4\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	 //增加下边这个语句
	protected boolean paused = false; 

	//task6\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	 //增加下边这个语句
	protected boolean swallowed = false; 
	/** constructor to create a shape with default values
	 */
	public MovingShape() {
		this(0, 0, 20, 20, 500, 500, Color.blue, Color.black, 0); // the default properties
	}

	/** constructor to create a shape
		* @param x 		the x-coordinate of the new shape
	 * @param y		the y-coordinate of the new shape
	 * @param w 		the width of the new shape
	 * @param h 		the height of the new shape
	 * @param mw 		the margin width of the animation panel
	 * @param mh		the margin height of the animation panel
	 * @param c		the colour of the new shape
	 # @param a		the transpency degree
	 * @param typeOfPath 		the path of the new shape
	 */
	public MovingShape(int x, int y, int w, int h, int mw, int mh, Color fc, Color bc, int pathType) {
		p = new Point(x,y);
		marginWidth = mw;
		marginHeight = mh;
		fillColor = fc;
		borderColor = bc;
		width = w;
		height = h;
		setPath (pathType);
	}

	/** Return the x-coordinate of the shape.
	 * @return the x coordinate
	 */
	public int getX() { return p.x; }

	/** Return the y-coordinate of the shape.
	 * @return the y coordinate
	 */
	public int getY() { return p.y;}

	/** Return the selected property of the shape.
	 * @return the selected property
	 */
	public boolean isSelected() { return selected; }

	/** Set the selected property of the shape.
	 *  When the shape is selected, its handles are shown.
	 * @param s 	the selected value
	 */
	public void setSelected(boolean s) { selected = s; }

	/** Set the width of the shape.
	 * @param w 	the width value
	 */
	public void setWidth(int w) { width = w; }

	/** Set the height of the shape.
	 * @param h 	the height value
	 */
	public void setHeight(int h) { height = h; }

	/**
	 * Return a string representation of the shape, containing
	 * the String representation of each element.
	 */
	public String toString() {
		return "[" + this.getClass().getName() + "," + p.x + "," + p.y + "," + width + "," + height + "]";
	}

	/** Draw the handles of the shape
	 * @param g 	the Graphics control
	 */
	protected void drawHandles(Graphics g) {
		// if the shape is selected, then draw the handles
		if (isSelected()) {
			g.setColor(Color.black);
			g.fillRect(p.x -2, p.y-2, 4, 4);
			g.fillRect(p.x + width -2, p.y + height -2, 4, 4);
			g.fillRect(p.x -2, p.y + height -2, 4, 4);
			g.fillRect(p.x + width -2, p.y-2, 4, 4);
		}
	}
	
	protected void drawFilledTriangle(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3) {
	    int[] x = new int[3];
	    int[] y = new int[3];
	    x[0]=x1;
	    y[0]=y1;
	    x[1]=x2;
	    y[1]=y2;
	    x[2]=x3;
	    y[2]=y3;
	    g.fillPolygon(x,y,3);
	  }

	/** Reset the margin for the shape
	 * @param w 	the margin width
	 * @param h 	the margin height
	 */
	public void setMarginSize(int w, int h) {
		marginWidth = w;
		marginHeight = h;
	}
	/** abstract contains method
	 * Returns whether the point p is inside the shape or not.
	 * @param p	the mouse point
	 */
	public abstract boolean contains(Point p);
	
	
	//task6\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	//修改以下两个方法
	/**	
	 * Checks whether this shape intersects with another shape
	 * @param s The other shape 
	 * @return	true if the shapes intersect, false otherwise
	 */
	public boolean intersects(MovingShape other) {
		//  complete this to check if the border of this shape intersects the border of the other shape
		return this.getBorder().intersects(other.getBorder());
		
	}
	//task6\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	protected Rectangle getBorder() {
		return new Rectangle(p.x, p.y, width, height);
	}

	/** abstract draw method
	 * draw the shape
	 * @param g 	the Graphics control
	 */
	public abstract void draw(Graphics g);

	/** Set the path of the shape.
	 * @param pathID 	the integer value of the path
	 *  MovingPath.BOUNDARY is the boundary path
	 *  MovingPath.FALLING is the falling path
	 *  MovingPath.Bouncing is the bouncing path
	 */
	public void setPath(int pathID) {
		switch (pathID) {
			case MovingPath.BOUNDARY : {
				path = new BoundaryPath(10);
				break;
			}
			case MovingPath.FALLING : {
				path = new FallingPath();
				break;
			}
			case MovingPath.BOUNCING : {
				path = new BouncingPath(5,10);
				break;
			}
		}
	}

	/** Set the fill colour of the shape.
	 * @param c 	the fill colour
	 */
	public void setFillColor(Color c) { fillColor = c; }

	/** Set the border colour of the shape.
	 * @param c 	the border colour
	 */
	public void setBorderColor(Color c) { borderColor =c; }

	/** move the shape by the path
	 */
	public void move() {
		path.move();
	}

	// Inner class ===================================================================== Inner class

	/*
	 *  ===============================================================================
	 *  MovingPath : The superclass of all paths. It is an inner class.
	 *  A path can change the current position of the shape.
	 *  ===============================================================================
	 */

	public abstract class MovingPath {
		public static final int BOUNDARY = 0; // The Id of the moving path
		public static final int FALLING = 1; // The Id of the moving path
		public static final int BOUNCING = 2; // The Id of the bouncing path
		
		public static final int UP = 0;
		public static final int DOWN = 1;
		public static final int LEFT = 2;
		public static final int RIGHT = 3;
		
		protected int direction;
		
		protected int delta; // moving distance
		
		/** constructor
		 */
		public MovingPath() { }

		/** abstract move method
		* move the shape according to the path
		*/
		public abstract void move();
		
		//task3\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	    //增加下边这个方法
		public int getDirection(){
			return this.direction;
		}

	}

	/*
	 *  ===============================================================================
	 *  BouncingPath : A Bouncing path.
	 *  ===============================================================================
	 */
	public class BouncingPath extends MovingPath {

		private int deltaX;
		private int deltaY;
		
		 /** constructor to initialise values for a bouncing path
		 */
		public BouncingPath(int dx, int dy) {
			deltaX = dx;
			deltaY = dy;
			
			//task3\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			//增加下边这个语句,新创建图形时默认是向右移动	 
			this.direction = RIGHT; 
	 }

		/** move the shape
		 */
		public void move() {
			 p.x = p.x + deltaX;
			 p.y = p.y + deltaY;
 

			
			
			
			 if ((p.x < 0) && (deltaX < 0)) {
				 deltaX = -deltaX;
				 p.x = 0;
				 //task3\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
				 //增加下边这个语句
				 this.direction = RIGHT; 
			 }
			 else if ((p.x + width > marginWidth) && (deltaX > 0)) {
				 deltaX = -deltaX;
				 p.x = marginWidth - width;
				 //task3\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
				 //增加下边这个语句	 
				 this.direction = LEFT; 

			 }
			 if ((p.y< 0) && (deltaY < 0)) {
				 deltaY = -deltaY;
				 p.y = 0;
				

			 }
			 else if((p.y + height > marginHeight) && (deltaY > 0)) {
				 deltaY = -deltaY;
				 p.y = marginHeight - height;
				 

			 }
		}
	}

	/*
	 *  ===============================================================================
	 *  FallingPath : A falling path.
	 *  ===============================================================================
	 */
	public class FallingPath extends MovingPath {
		private double am = 0, stx =0, sinDeltax = 0;
		
		/** constructor to initialise values for a falling path
		 */
		public FallingPath() {
			am = Math.random() * 20; //set amplitude variables
			stx = 0.5; //set step variables
			delta = 5;
			sinDeltax = 0;
			
			
		}

		/** move the shape
		 */
		public void move() {
			sinDeltax = sinDeltax + stx;
			p.x = (int) Math.round(p.x + am * Math.sin(sinDeltax));
			p.y = p.y + delta;
			if (p.y > marginHeight) // if it reaches the bottom of the frame, start again from the top
				p.y = 0;
			
			
			//task3\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		    //增加下边这个语句
			this.direction = DOWN; 
			
			}
		
	}

	/*
	 *  ===============================================================================
	 *  BoundaryPath : A boundary path which moves the shape around the boundary of the frame
	 *  ===============================================================================
	 */
	public class BoundaryPath extends MovingPath {

		/** constructor to initialise values for a boundary path
		 */
		public BoundaryPath(int speed) {
			delta = (int) (Math.random() * speed) + 1;
			direction = DOWN;
		}
		
		/** move the shape
		 */
		public void move() {
			int h = marginHeight - height;
			int w = marginWidth - width;
			switch  (direction) {
				case DOWN : { // moving downwards
					p.y += delta;
					if (p.y > h) {
						p.y = h - 1;
						direction = RIGHT;
					}
					break;
				}
				case RIGHT : { // moving to the right
					p.x += delta;
					if (p.x > w) {
						p.x = w - 1;
						direction = UP;
					}
					break;
				}
				case UP : {
					p.y -= delta; // moving upwards
					if (p.y < 0) {
						direction = LEFT;
						p.y = 0;
					}
					break;
				}
				case LEFT : { // moving to the left
					 p.x -= delta;
					 if (p.x < 0) {
							direction = DOWN;
							p.x = 0;
					 }
					 break;
				}
			}
		}
	}
 // ========================================================================================

}