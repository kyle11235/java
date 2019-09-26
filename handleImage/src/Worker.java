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


public class Worker extends Thread {

	public static ConcurrentLinkedQueue<Job> jobQueue = new ConcurrentLinkedQueue<Job>();
	private boolean belongPool = false;
	private ArrayList<File> inputImages;
	private File f;
	private BufferedImageOp filter;
	private File fileOutput;
	private A3 a3;
	private JPanel panel;
	private Job job;
	public static ConcurrentLinkedQueue<File> queue = new ConcurrentLinkedQueue<File>();
	public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
			new ThreadPoolExecutor.CallerRunsPolicy());

	public Worker(A3 a3,JPanel panel , ArrayList<File>  inputImages,BufferedImageOp filter,File fileOutput, Job job){
		this.a3 =a3;
		this.panel = panel;
		this.inputImages = inputImages;
		this.filter = filter;
		this.fileOutput = fileOutput;
		this.job = job;
	}
	public Worker(A3 a3,JPanel panel ,File f,BufferedImageOp filter,File fileOutput, Job job){
		this.a3 =a3;
		this.panel = panel;
		this.f = f;
		this.filter = filter;
		this.fileOutput = fileOutput;
		this.job = job;
	}
	
	
	
	

	
	public boolean isBelongPool() {
		return belongPool;
	}
	public void setBelongPool(boolean belongPool) {
		this.belongPool = belongPool;
	}
	@Override
	public void run() {
		
		if(this.belongPool){
			doWork(queue.poll());
			return;
		}
		
		
		if(job.getManner() == 2){
			for (File f: inputImages) {
				doWork(f);
			}
		}
		else if(job.getManner() == 3){
			
			jobQueue.add(job);
			for (File f: inputImages) {
				queue.add(f);
			}
			while(jobQueue.peek().getJobNumber()!=job.getJobNumber()){
				//wait
			}
			int i =job.getTotal();
			while(i>0){
				Worker worker = new Worker(a3 ,panel ,inputImages , filter, fileOutput ,job);
				worker.setBelongPool(true);
				threadPool.execute(worker);
				i--;
			}
			while(job.getFinishedCount()!=job.getTotal()){
				//wait
			}
			jobQueue.poll();
		}
	}
	public  void doWork(File f) {
		try {
			BufferedImage src = ImageIO.read(f);
			BufferedImage bufOut = filter.filter(src, null);
			File dstFile = new File(fileOutput, job.getJobNumber()+"_"+f.getName());
			ImageIO.write(bufOut, "jpg", dstFile);
			job.addCurrent();

			a3.updateGUI(panel,job);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
