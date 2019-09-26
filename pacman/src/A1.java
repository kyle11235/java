/*
 *  =============================================================
 *  A1.java : Extends JApplet and contains a panel where
 *  shapes move around on the screen. Also contains start and stop
 *  buttons that starts animation and stops animation respectively.
 *  ==============================================================
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class A1 extends JApplet {
	
	
	//count777777777777777777777777777777777777777777777777777777777777777777777	
	public static int eatenCount =0;
	public static int frightenCount =0;
	JLabel countLabel = new JLabel();
	
	
	AnimationPanel panel;  // panel for bouncing area

	/** main method for A1
	 */
	public static void main(String[] args) {
		A1 applet = new A1();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(applet, BorderLayout.CENTER);
		frame.setTitle("Bouncing Application by upi123");
		applet.init();
		applet.start();
		frame.setSize(800, 500);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		frame.setLocation((d.width - frameSize.width) / 2, (d.height - frameSize.height) / 2);
		frame.setVisible(true);
	}

	/** init method to initialise components
	 */
	public void init() {
		panel = new AnimationPanel(this);
		add(panel, BorderLayout.CENTER);
		add(setUpToolsPanel(), BorderLayout.NORTH);
		add(setUpButtons(), BorderLayout.SOUTH);
		addComponentListener(
			new ComponentAdapter() { // resize the frame and reset all margins for all shapes
				public void componentResized(ComponentEvent componentEvent) {
					panel.resetMarginSize();
			 }
		 });
		panel.start();
	}

	/** Set up the tools panel
	* @return toolsPanel		the Panel
	 */
	public JPanel setUpToolsPanel() {
		//Set up the shape combo box
		ImageIcon circleButtonIcon = createImageIcon("circle.gif");
		ImageIcon rectangleButtonIcon = createImageIcon("rectangle.gif");
		ImageIcon pacmanButtonIcon = createImageIcon("pacman.gif");
		//task5\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		//修改此处，增加画ghost的图标，并在动画面板类的创建图形方法中增加创建ghost的case
		ImageIcon ghostButtonIcon = createImageIcon("ghost.gif");
		JComboBox shapesComboBox = new JComboBox(new Object[] {circleButtonIcon,rectangleButtonIcon,pacmanButtonIcon,ghostButtonIcon } );
		shapesComboBox.setToolTipText("Set shape");
		shapesComboBox.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				//set the default shape type based on the selection: 0 for Circle, 1 for Rectangle etc
				panel.setDefaultShapeType(cb.getSelectedIndex());
			}
		});
		//Set up the path combo box
		ImageIcon boundaryButtonIcon = createImageIcon("boundary.gif");
    	ImageIcon fallingButtonIcon = createImageIcon("falling.gif");
    	ImageIcon bouncingButtonIcon = createImageIcon("bounce.gif");
		JComboBox pathComboBox = new JComboBox(new Object[] {boundaryButtonIcon, fallingButtonIcon,bouncingButtonIcon  });
		pathComboBox.setToolTipText("Set Path");
		pathComboBox.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				//set the default path type based on the selection from combo box: 0 for Boundary Path, 1 for bouncing Path
				panel.setDefaultPathType(cb.getSelectedIndex());
			}
		});
		//Set up the height TextField
		JTextField heightTxt = new JTextField("100");
		heightTxt.setToolTipText("Set Height");
		heightTxt.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					int newValue = Integer.parseInt(tf.getText());
					if (newValue > 0) // if the value is valid, then change the default height
					 	panel.setDefaultHeight(newValue);
					else
						tf.setText(panel.getDefaultHeight()+"");
				} catch (Exception ex) {
					tf.setText(panel.getDefaultHeight()+""); //if the number entered is invalid, reset it
				}
			}
		});
		//Set up the width TextField
		JTextField widthTxt = new JTextField("100");
		widthTxt.setToolTipText("Set Width");
		widthTxt.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					int newValue = Integer.parseInt(tf.getText());
					if (newValue > 0)
					 	panel.setDefaultWidth(newValue);
					else
						tf.setText(panel.getDefaultWidth()+"");
				} catch (Exception ex) {
					tf.setText(panel.getDefaultWidth()+"");
				}
			}
		});
		 //task4\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		 //增加pasuse按钮，增加下边几行语句
		// button for pause
		JButton pauseButton = new JButton("Pause");
		pauseButton.setToolTipText("Pause the selected");
		pauseButton.setForeground(Color.blue);
		pauseButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				panel.pauseOrContinueSelected(e.getActionCommand().toString());
			}
		});
		//task4\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		 //增加continue按钮，增加下边几行语句
		// button for pause
		JButton continueButton = new JButton("Continue");
		continueButton.setToolTipText("Continue the selected");
		continueButton.setForeground(Color.blue);
		continueButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				panel.pauseOrContinueSelected(e.getActionCommand().toString());
			}
		});
		
		
		// button for fillColor
		JButton fillButton = new JButton("Fill");
		fillButton.setToolTipText("Set Fill Color");
		fillButton.setForeground(Color.blue);
		fillButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				Color newColor = JColorChooser.showDialog(panel, "Fill Color", panel.getDefaultFillColor());
				if (newColor != null) {
					JButton btn = (JButton) e.getSource();
					btn.setForeground(newColor);
					panel.setDefaultFillColor(newColor);
				}
			}
		});
		// button for borderColor
		JButton borderButton = new JButton("Border");
		borderButton.setToolTipText("Set Border Color");
		borderButton.setForeground(Color.black);
		borderButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				Color newColor = JColorChooser.showDialog(panel, "Border Color", panel.getDefaultBorderColor());
				if ( newColor != null) {
					JButton btn = (JButton) e.getSource();
					btn.setForeground(newColor);
					panel.setDefaultBorderColor(newColor);
				}
			}
		});

		JPanel toolsPanel = new JPanel();
		toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.X_AXIS));
		toolsPanel.add(new JLabel(" Shape: ", JLabel.RIGHT));
		toolsPanel.add(shapesComboBox);
		toolsPanel.add(new JLabel(" Path: ", JLabel.RIGHT));
		toolsPanel.add(pathComboBox);
		//count777777777777777777777777777777777777777777777777777777777777777777777
		toolsPanel.add(countLabel);
		
		
		
		//task4\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		//在工具面板上添加前面创建的两个按钮
		
		
		toolsPanel.add(pauseButton);
		toolsPanel.add(continueButton);
		toolsPanel.add( new JLabel(" Height: ", JLabel.RIGHT));
		toolsPanel.add(heightTxt);
		toolsPanel.add(new JLabel(" Width: ", JLabel.RIGHT));
		toolsPanel.add(widthTxt);
		toolsPanel.add(fillButton);
		toolsPanel.add(borderButton);
		return toolsPanel;
	}

	/** Set up the buttons panel
		 * @return buttonPanel		the Panel
	 */
	public JPanel setUpButtons() {
		JPanel buttonPanel= new JPanel(new FlowLayout());
		// Slider to adjust the speed of the animation
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 30);
		slider.setToolTipText("Adjust Speed");
		slider.addChangeListener(new ChangeListener() {
		 public void stateChanged(ChangeEvent e) {
			 JSlider source = (JSlider)e.getSource();
			 if (!source.getValueIsAdjusting()) {
				 int value = (int) (source.getValue());  // get the value from slider
				 TitledBorder tb = (TitledBorder) source.getBorder();
				 tb.setTitle("Anim delay = " + String.valueOf(value) + " ms"); //adjust the tilted border to indicate the speed of the animation
				 panel.adjustSpeed(value); //set the speed
				 source.repaint();
			 }
			}
		});
		TitledBorder title = BorderFactory.createTitledBorder("Anim delay = 30 ms");
		slider.setBorder(title);
		// Add slider control
		buttonPanel.add(slider);
		return buttonPanel;
	}

	/** create the imageIcon
	 * @param  filename		the filename of the image
	 * @return ImageIcon		the imageIcon
	 */
	protected static ImageIcon createImageIcon(String filename) {
		java.net.URL imgURL = A1.class.getResource(filename);
		return new ImageIcon(imgURL);
	}
	/** create the imageIcon
	 * @param  filename		the filename of the image
	 * @return ImageIcon		the imageIcon
	 */
	public  void updateCount() {
		this.countLabel.setText(eatenCount+"::"+frightenCount);
	}


}

