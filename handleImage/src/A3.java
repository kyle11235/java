import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import javax.swing.filechooser.FileFilter;

import com.jhlabs.image.ChromeFilter;
import com.jhlabs.image.CrystallizeFilter;

import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.InvertFilter;
import com.jhlabs.image.ScaleFilter;
import com.jhlabs.image.TileImageFilter;
import com.jhlabs.image.TileMultiImagesFilter;
import com.jhlabs.image.WeaveFilter;

public class A3 extends JFrame implements ActionListener {

	private JTextField txtInputFile = new JTextField(System.getProperty("user.home") + System.getProperty("file.separator") + "workspace/cutPic/in");
	private JTextField txtOutputFolder = new JTextField(System.getProperty("user.home") + System.getProperty("file.separator") + "workspace/cutPic/out");
	private JComboBox comboFilters = null;

	private JButton btnSequential = new JButton("Start sequential job");
	private JButton btnConcurrent = new JButton("Start concurrent job");
	private JButton btnParallel = new JButton("Start parallel job");
	private int count = 1;
	private JButton btnCounter = new JButton("Responsive? " + count);
	private JButton btnEmptyOutputFolder = new JButton("Empty output folder");

	private int nextJobNumber = 1;
	// progress panel
	private JPanel southPanel;
	private int height = 0;

	private enum Filter {
		Thumbnail("Thumbnail"), HalfSize("Half-size"), Grayscale("Grayscale"), Chrome("Chrome"), Crystallise("Stained glass"), Weave("Weave"), Invert("Invert"), TileImage("Tile one Image"), TileMultiImages("Tile multiple images");

		private final String name;

		Filter(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	private Filter[] filters = null;

	public A3() {
		super("Assignment 3 - by upi123");
		filters = Filter.values();
		createGUI();
	}

	private void createGUI() {
		// -- Input file/folder
		JPanel inputPanel = new JPanel(new GridLayout(13, 1));
		inputPanel.add(new JLabel("Input file/folder: "));
		txtInputFile.setEditable(false);
		inputPanel.add(txtInputFile);
		JButton btnInputFile = new JButton("Choose the input folder");
		btnInputFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser(txtInputFile.getText());
				fc.setAcceptAllFileFilterUsed(false);
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.addChoosableFileFilter(new FileFilter() {
					public String getDescription() {
						return "Folder or image";
					}

					public boolean accept(File f) {
						return f.isDirectory() || isImage(f);
					}
				});
				int choice = fc.showOpenDialog(A3.this);
				if (choice == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					txtInputFile.setText(file.getPath());
				}
			}
		});
		inputPanel.add(btnInputFile);

		// -- Output folder
		inputPanel.add(new JLabel("Output folder: "));
		txtOutputFolder.setEditable(false);
		inputPanel.add(txtOutputFolder);
		JButton btnOutputFile = new JButton("Choose the output folder");

		btnOutputFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser(txtOutputFolder.getText());
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				fc.addChoosableFileFilter(new FileFilter() {
					public String getDescription() {
						return "Folder";
					}

					public boolean accept(File f) {
						return f.isDirectory();
					}
				});
				int choice = fc.showOpenDialog(A3.this);
				if (choice == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					txtOutputFolder.setText(file.getPath());
				}
			}
		});
		inputPanel.add(btnOutputFile);
		btnEmptyOutputFolder.addActionListener(this);
		inputPanel.add(btnEmptyOutputFolder);
		// -- Filter
		inputPanel.add(new JLabel("Desired filter: "));
		comboFilters = new JComboBox(filters);
		comboFilters.setMaximumRowCount(15);

		inputPanel.add(comboFilters);

		// -- Start buttons
		btnSequential.setBackground(Color.RED);
		btnSequential.addActionListener(this);
		inputPanel.add(btnSequential);

		btnConcurrent.setBackground(Color.ORANGE);
		btnConcurrent.addActionListener(this);
		inputPanel.add(btnConcurrent);

		btnParallel.setBackground(Color.GREEN);
		btnParallel.addActionListener(this);
		inputPanel.add(btnParallel);

		btnCounter.addActionListener(this);
		inputPanel.add(btnCounter);

		add(inputPanel, BorderLayout.NORTH);

		// -- add job progress panel
		southPanel = new JPanel();
		southPanel.setLayout(null);
		southPanel.setBackground(Color.pink);
		add(southPanel, BorderLayout.CENTER);

		// -- position the frame
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		setPreferredSize(new Dimension(500, 600));
		setLocation(screenWidth / 4, screenHeight / 8);
	}

	public void actionPerformed(ActionEvent e) {
		File fileInput = new File(txtInputFile.getText());
		File fileOutput = new File(txtOutputFolder.getText());
		ArrayList<File> listFiles = new ArrayList<File>();
		Filter selectedFilter = (Filter) comboFilters.getSelectedItem();

		if (fileInput.isDirectory()) {
			File[] innerFiles = fileInput.listFiles();
			for (File f : innerFiles) {
				if (isImage(f))
					listFiles.add(f);
			}
		} else {
			listFiles.add(fileInput);
		}

		// clear output folder
		if (e.getSource() == btnEmptyOutputFolder) {
			File[] files = fileOutput.listFiles();
			int count = 0;
			for (File f : files) {
				if (isImage(f)) {
					count++;
				}
			}
			if (count > 0) {
				int answer = JOptionPane.showConfirmDialog(A3.this, "Are you sure you want to delete all " + count + " images?", "Confirm delete", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					for (File f : files) {
						if (isImage(f)) {
							f.delete();
						}
					}
				}
			}
			return;
		}

		// use this button to test UI is responsive or not while job is running
		if (e.getSource() == btnCounter) {
			count++;
			btnCounter.setText("Responsive? " + count);
			Color bg = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
			btnCounter.setBackground(bg);
			return;
		}

		// set thread manner
		int manner = 1;
		if (e.getSource() == btnSequential) {
			// use main thread, UI is not responsive until finish, but this is fast because UI is only updated for 1 time
			manner = 1;
		} else if (e.getSource() == btnConcurrent) {
			// use another thread
			manner = 2;
		} else if (e.getSource() == btnParallel) {
			// use a thread pool
			manner = 3;
		}

		// filter an image
		Job job = new Job(nextJobNumber, listFiles.size(), System.currentTimeMillis(), manner);
		this.filter(selectedFilter, listFiles, fileOutput, job);
		nextJobNumber++;

	}

	private void filter(Filter selectedFilter, ArrayList<File> listFiles, File fileOutput, Job job) {

		switch (selectedFilter) {

		case Thumbnail:
			filter(new ScaleFilter(), listFiles, fileOutput, job);
			break;

		case HalfSize:
			filter(new ScaleFilter(0.5), listFiles, fileOutput, job);
			break;

		case Grayscale:
			filter(new GrayscaleFilter(), listFiles, fileOutput, job);
			break;

		case Chrome:
			filter(new ChromeFilter(), listFiles, fileOutput, job);
			break;

		case Crystallise:
			filter(new CrystallizeFilter(), listFiles, fileOutput, job);
			break;

		case Weave:
			filter(new WeaveFilter(), listFiles, fileOutput, job);
			break;

		case Invert:
			filter(new InvertFilter(), listFiles, fileOutput, job);
			break;

		case TileImage:
			filter(new TileImageFilter(), listFiles, fileOutput, job);
			break;

		case TileMultiImages:
			filter(new TileMultiImagesFilter(), listFiles, fileOutput, job);
			break;

		}
	}

	private void filter(BufferedImageOp filter, ArrayList<File> inputImages, File fileOutput, Job job) {
		JPanel panel = this.addPannel();
		if (job.getManner() == 1) {
			for (File f : inputImages) {
				try {
					BufferedImage src = ImageIO.read(f);
					BufferedImage bufOut = filter.filter(src, null);
					File dstFile = new File(fileOutput, job.getJobNumber() + "_" + f.getName());
					ImageIO.write(bufOut, "jpg", dstFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			job.setTotalTime((System.currentTimeMillis() - job.getStartTime()) / 1000.0);
			job.setFinishedCount(inputImages.size());
			updateGUI(panel, job);
		} else if (job.getManner() == 2) {
			new Worker(this, panel, inputImages, filter, fileOutput, job).start();
		} else if (job.getManner() == 3) {
			new Worker(this, panel, inputImages, filter, fileOutput, job).start();
		}
	}

	private void filter(TileMultiImagesFilter filter, ArrayList<File> inputImages, File fileOutput, Job job) {
		JPanel panel = this.addPannel();
		new TileMultiImagesWorker(this, panel, inputImages, filter, fileOutput, job).start();
	}

	private boolean isImage(File f) {
		int index = f.getName().lastIndexOf('.');
		if (index == -1)
			return false;
		String extension = f.getName().substring(index);
		if (extension != null) {
			if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".jpe") || extension.equalsIgnoreCase(".gif") || extension.equalsIgnoreCase(".png"))
				return true;
		}
		return false;
	}

	private JPanel addPannel() {
		JPanel panel = new JPanel();
		Color bg = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
		panel.setBackground(bg);
		panel.setBounds(0, height, 500, 50);
		height += 50;
		southPanel.add(panel);
		return panel;
	}

	public void updateGUI(JPanel panel, Job job) {

		System.out.println("jobID: " + job.getJobNumber() + " finished/total: " + job.getFinishedCount() + "/" + job.getTotal());
		panel.removeAll();

		JProgressBar progressBar = new JProgressBar();
		progressBar.setMaximum(job.getTotal());
		progressBar.setValue(job.getFinishedCount());
		panel.add(progressBar);

		JLabel label = new JLabel("jobID: " + job.getJobNumber() + " finished/total: " + job.getFinishedCount() + "/" + job.getTotal());
		label.setBounds(0, 0, 500, 50);
		panel.add(label);

		if (job.getFinishedCount() == job.getTotal()) {
			job.setTotalTime((System.currentTimeMillis() - job.getStartTime()) / 1000.0);
			System.out.println("Job " + job.getJobNumber() + " took " + job.getTotalTime() + " seconds.");
			JLabel lastlabel = new JLabel("Job " + job.getJobNumber() + " took " + job.getTotalTime() + " seconds.");
			lastlabel.setBounds(0, 0, 500, 50);
			panel.add(lastlabel);
		}
		panel.validate();
		southPanel.validate();
		this.validate();

	}

	public static void main(String[] args) {
		A3 app = new A3();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setResizable(true);
		app.pack();
		app.setVisible(true);
	}
}
