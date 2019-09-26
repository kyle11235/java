import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.jhlabs.image.TileMultiImagesFilter;


public class TileMultiImagesWorker extends Thread {

	public static ConcurrentLinkedQueue<Job> jobQueue = new ConcurrentLinkedQueue<Job>();
	private ArrayList<File> inputImages;
	private TileMultiImagesFilter filter;
	private File fileOutput;
	private A3 a3;
	private JPanel panel;
	private Job job;
	
	public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
			new ThreadPoolExecutor.CallerRunsPolicy());
	
	public TileMultiImagesWorker(A3 a3,JPanel panel , ArrayList<File>  inputImages,TileMultiImagesFilter filter,File fileOutput, Job job){
		this.a3 =a3;
		this.panel = panel;
		this.inputImages = inputImages;
		this.filter = filter;
		this.fileOutput = fileOutput;
		this.job = job;
	}

	public void run() {
		jobQueue.add(job);
		while(jobQueue.peek().getJobNumber()!=job.getJobNumber()){
			//wait
		}
		try {
			
			BufferedImage bufOut = filter.filter(inputImages, null);
			File dstFile = new File(fileOutput, job.getJobNumber()+"_"+inputImages.get(0).getName());
			ImageIO.write(bufOut, "jpg", dstFile);
			job.setFinishedCount(job.getTotal());

			a3.updateGUI(panel,job);

		} catch (IOException e) {
			e.printStackTrace();
		}
		jobQueue.poll();
	}

	
	
	
	
	
	
}
