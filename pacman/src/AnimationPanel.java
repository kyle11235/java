/*
 *  ======================================================================
 *  AnimationPanel.java : Moves shapes around on the screen according to different paths.
 *  It is the main drawing area where shapes are added and manipulated.
 *  It also contains a popup menu to clear all shapes.
 *  ======================================================================
 */

import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class AnimationPanel extends JComponent implements Runnable {

	A1 a1 ;
	
	
	private Thread animationThread = null;  // the thread for animation
	private Vector<MovingShape> shapes;		// the vector to store all shapes
	private int defaultShapeType, // the default shape type
		defaultPath, 				// the default path type
		defaultWidth = 100,			// the default width of a shape
		defaultHeight = 100;			// the default height of a shape
	private Color defaultFillColor = Color.blue;  // the default fill color of a shape
	private Color defaultBorderColor = Color.black;  // the default border colour of a shape
	private int delay = 30; 		// the default animation speed
	JPopupMenu popup;				// popup menu

	 /** Constructor of the AnimationPanel
		*/
	 public AnimationPanel(A1 a1) {
		 
		this.a1 = a1; 
		 
		 
		 
		shapes = new Vector <MovingShape>(); //create the vector to store shapes
		popup = new JPopupMenu(); //create the popup menu
		makePopupMenu();

		// add the mouse event to handle popup menu and create new shape
		addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			public void mouseClicked( MouseEvent e ) {
				if (animationThread != null) {  // if the animation has started, then
					boolean found = false;
					MovingShape currentShape = null;
					for (int i = 0; i < shapes.size(); i++) {
						currentShape = (MovingShape) shapes.elementAt(i);
						if ( currentShape.contains( e.getPoint()) ) { // if the mousepoint is within a shape, then set the shape to be selected/deselected
							found = true;
							currentShape.setSelected( ! currentShape.isSelected() );
						}
					}
					if (! found) createNewShape(e.getX(), e.getY()); // if the mousepoint is not within a shape, then create a new one according to the mouse position
				}
			}
		});
	}

	/** get default fill color
	*/
	public Color getDefaultFillColor() {
		return defaultFillColor;
	}

	/** get default border color
	*/
	public Color getDefaultBorderColor() {
		return defaultBorderColor;
	}

	/** create a new shape
	 * @param x 	the x-coordinate of the mouse position
	 * @param y	the y-coordinate of the mouse position
	 */
	protected void createNewShape(int x, int y) {
		// get the margin of the frame
		Insets insets = getInsets();
		int marginWidth = getWidth() - insets.left - insets.right;
		int marginHeight = getHeight() - insets.top - insets.bottom;
		// create a new shape dependent on all current properties and the mouse position
		switch (defaultShapeType) {
			case 0: {
				shapes.add( new MovingCircle(x, y, defaultWidth, defaultHeight, marginWidth, marginHeight, defaultFillColor, defaultBorderColor, defaultPath));
				break;
			}
			case 1: {
				shapes.add( new MovingSquare(x, y, defaultWidth, marginWidth, marginHeight, defaultFillColor, defaultBorderColor, defaultPath));
				break;
			}
			case 2: {
				shapes.add( new MovingPacman(x, y, defaultWidth, defaultHeight, marginWidth, marginHeight, defaultFillColor, defaultBorderColor, defaultPath));
				break;
			}
			//task5\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			//增加画创建ghost的case
			case 3: {
				shapes.add( new MovingGhost(x, y, defaultWidth, defaultHeight, marginWidth, marginHeight, defaultFillColor, defaultBorderColor, defaultPath));
				break;
			}
		}
	}

	/** set the default shape type
	 * @param s	the new shape type
	 */
	public void setDefaultShapeType(int s) {
		defaultShapeType = s;
	}

	/** set the default path type and the path type for all currently selected shapes
	 * @param t	the new path type
	 */
	public void setDefaultPathType(int t) {
		defaultPath = t;
		MovingShape currentShape = null;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = (MovingShape) shapes.get(i);
			if ( currentShape.isSelected()) {
				currentShape.setPath(defaultPath);
			}
		}
	}

	/** set the default width and the width for all currently selected shapes
	 * @param w	the new width value
	 */
	public void setDefaultWidth(int w) {
		MovingShape currentShape = null;
		defaultWidth = w;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = (MovingShape) shapes.get(i);
			if ( currentShape.isSelected()) {
				currentShape.setWidth(defaultWidth);
			}
		}
	}

	/** get the default width
	 * @return defaultWidth - the width value
	 */
	public int getDefaultWidth() {
		return defaultWidth;
	}

	/** set the default height and the height for all currently selected shapes
	 * @param h	the new height value
	 */
	public void setDefaultHeight(int h) {
		MovingShape currentShape = null;
		defaultHeight = h;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = (MovingShape) shapes.elementAt(i);
			if ( currentShape.isSelected()) {
				currentShape.setHeight(defaultHeight);
			}
		}
	}

	/** get the default height
	 * @return defaultHeight - the height value
	 */
	public int getDefaultHeight() {
		return defaultHeight;
	}


	/** set the default border colour and the border colour for all currently selected shapes
	 * @param bc	the new border colour value
	 */
	public void setDefaultBorderColor(Color bc) {
		defaultBorderColor = bc;
		MovingShape currentShape = null;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = (MovingShape) shapes.elementAt(i);
			if ( currentShape.isSelected()) {
				currentShape.setBorderColor(defaultBorderColor);
			}
		}
	}

 /** set the default fill colour and the fill colour for all currently selected shapes
	 * @param bc	the new fill colour value
	 */
	public void setDefaultFillColor(Color bc) {
		defaultFillColor = bc;
		MovingShape currentShape = null;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = (MovingShape) shapes.elementAt(i);
			if ( currentShape.isSelected()) {
				currentShape.setFillColor(defaultFillColor);
			}
		}
	}
	
	/** remove all shapes from our vector
	 */
	public void clearAllShapes() {
		shapes.clear();
	}
	
	/* \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	 * Task 6, check if monsters collide with victim shapes.
	 */
	private void checkForCollisions() {
		MovingShape currentShape = null;
		MovingShape nextShape = null;
		for (int i = 0; i < shapes.size(); i++) {
			
			currentShape = (MovingShape) shapes.elementAt(i);
			
			//补充task6，添加if语句\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			if(currentShape.swallowed){
				continue;
			}

			for (int j = i+1; j < shapes.size(); j++) {
				nextShape = (MovingShape) shapes.elementAt(j);
		
				//补充task6，添加if语句\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
				if(nextShape.swallowed||(currentShape.paused && (nextShape instanceof MovingGhost))||(nextShape.paused && (currentShape instanceof MovingGhost))){
					continue;
				}

				
				if(currentShape.intersects(nextShape))
				{
					if(currentShape instanceof Monster && !(nextShape instanceof Monster)){
						Monster monster = (Monster)currentShape;
						monster.capture(nextShape);
						//System.out.println("collision1");
					}
					if(nextShape instanceof Monster && !(currentShape instanceof Monster)){
						Monster monster = (Monster)nextShape;
						monster.capture(currentShape);
						//System.out.println("collision2");
					}
				}
			}
		}
	}

	// you don't need to make any changes after this line ______________

	/** create the popup menu for our animation program
	 */
	protected void makePopupMenu() {
		JMenuItem menuItem;
	 // clear all
		menuItem = new JMenuItem("Clear All");
		menuItem.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAllShapes();
			}
		});
		popup.add(menuItem);
	 }

	/** reset the margin size of all shapes from our vector
	 */
	public void resetMarginSize() {
		Insets insets = getInsets();
		int marginWidth = getWidth() - insets.left - insets.right;
		int marginHeight = getHeight() - insets.top - insets.bottom ;
		 for (int i = 0; i < shapes.size(); i++)
			((MovingShape) shapes.elementAt(i)).setMarginSize(marginWidth, marginHeight);
	}

	/**  update the painting area
	 * @param g	the graphics control
	 */
	public void update(Graphics g){
		paint(g);
	}

	/**  move and paint all shapes within the animation area
	 * @param g	the Graphics control
	 */
	public void paintComponent(Graphics g) {
		MovingShape currentShape;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = (MovingShape) shapes.elementAt(i);
			
			//task4\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			//在currentShape.move();外面包裹一个if语句，paused属性是自己加上去的，它的值在点击pause时被改变
			if(!currentShape.paused){
				currentShape.move();
			}
			//task6\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			//增加一个形状的属性，表示是否被Pacman吃掉
			if(!currentShape.swallowed){
				currentShape.draw(g);
			}
			
		}
		
		checkForCollisions();
		
		//count777777777777777777777777777777777777777777777777777777777777777777777
	   this.a1.updateCount();
		
		
		
		
		
	}
	//task4\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	//增加以下方法 
	/** pause or continue the Selected graphics
     * @param Pause_or_Continue  	pause the selected if Pause,continue if Continue
	 */
	public void pauseOrContinueSelected(String Pause_or_Continue){
		MovingShape currentShape;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = (MovingShape) shapes.elementAt(i);
			if(currentShape.selected){
				if(Pause_or_Continue.equals("Pause")){
					currentShape.paused = true;
				}
				else if(Pause_or_Continue.equals("Continue")){
					currentShape.paused = false;
				}
			}
		}
	}
	/** change the speed of the animation
	 * @param newValue 	the speed of the animation in ms
	 */
	public void adjustSpeed(int newValue) {
		if (animationThread != null) {
			delay = newValue;
			start();
		}
	}

	public void start() {
		animationThread = new Thread(this);
		animationThread.start();
	}

	/** run the animation
	 */
	public void run() {
		Thread myThread = Thread.currentThread();
		while(animationThread==myThread) {
			repaint();
			pause(delay);
		}
	}

	/** Sleep for the specified amount of time
	 */
	private void pause(int milliseconds) {
		try {
			Thread.sleep((long)milliseconds);
		} catch(InterruptedException ie) {}
	}
}
