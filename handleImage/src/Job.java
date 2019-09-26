
public class Job {

	private int jobNumber;
	private int total = 0;
	private int current = 0;
	private int finishedCount = 0;
	private long startTime;
	private double totalTime;
	private int manner;
	public Job(int jobNumber,int total,long startTime,int manner) {
		this.jobNumber = jobNumber;
		this.total = total;
		this.startTime = startTime;
		this.manner = manner;
	}
	
	
	
	public int getManner() {
		return manner;
	}



	public void setManner(int manner) {
		this.manner = manner;
	}



	public  int getFinishedCount() {
		return finishedCount;
	}



	public synchronized  void setFinishedCount(int finishedCount) {
		this.finishedCount = finishedCount;
	}



	public int getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrent() {
		return current;
	}
	public synchronized void addCurrent() {
		this.current++;
		finishedCount++;

	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public double getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}
	
}
