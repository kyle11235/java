package com.jhlabs.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
public class TileMultiImagesFilter extends AbstractBufferedImageOp implements Runnable{

	private int width;
	private int height;

	public static ConcurrentLinkedQueue<File> imageQueue = new ConcurrentLinkedQueue<File>();
	public static ConcurrentLinkedQueue<BufferedImage> queue = new ConcurrentLinkedQueue<BufferedImage>();
	public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
			new ThreadPoolExecutor.CallerRunsPolicy());

	private  ScaleFilter filter = new ScaleFilter();
	private BufferedImage src,dst;
	/**
     * Construct a TileImageFilter.
     */
    public TileMultiImagesFilter() {
		this(64, 64);
	}

	/**
     * Construct a TileImageFilter.
     * @param width the output image width
     * @param height the output image height
     */
	public TileMultiImagesFilter(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
     * Set the output image width.
     * @param width the width
     * @see #getWidth
     */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
     * Get the output image width.
     * @return the width
     * @see #setWidth
     */
	public int getWidth() {
		return width;
	}

	/**
     * Set the output image height.
     * @param height the height
     * @see #getHeight
     */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
     * Get the output image height.
     * @return the height
     * @see #setHeight
     */
	public int getHeight() {
		return height;
	}
	 public BufferedImage filter(  ArrayList<File>  inputImages, BufferedImage dst ) {
		
		
		 for(int i=0;i<inputImages.size();i++){
			imageQueue.add(inputImages.get(i));
			threadPool.execute(this);
		 }
		 while(!imageQueue.isEmpty()){
			 
		 }
		try {
			src = ImageIO.read(inputImages.get(0));
			ColorModel dstCM = src.getColorModel();
			dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(width*10, height*10), dstCM.isAlphaPremultiplied(), null);
			Graphics2D g = dst.createGraphics();
			
			Iterator it = queue.iterator();
			int size = queue.size();
			int count = 100/size;
			
			int counter = 0;
			BufferedImage img = (BufferedImage)(it.next());
			for ( int y = 0; y <  height*10; y += height) {
				for ( int x = 0; x < width*10; x += width ) {
					
					
					if(counter == count){
						if(it.hasNext()){
							img = (BufferedImage)(it.next());
						}
						else{
							it = queue.iterator();
							img = (BufferedImage)(it.next());
						}
						counter = 0;
					}
					g.drawImage( img, null, x, y );
					counter++;
				}
			}
			g.dispose();
			queue.clear();
			imageQueue.clear();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        return dst;
	    }

		public String toString() {
			return "Tile";
		}

		@Override
		public BufferedImage filter(BufferedImage src, BufferedImage dst) {
			
	
		        return dst;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				src = ImageIO.read(imageQueue.poll());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			queue.add(filter.filter(src, dst));
		
		}
}
