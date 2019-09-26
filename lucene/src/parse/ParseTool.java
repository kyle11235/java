package parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class ParseTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(ParseTool.parseFile(new File("d://3000//data//teacher.doc")));
		
		//第二种方式 据说效率不如自己写
		System.out.println(ParseTool.parseToString(new File("d://3000//data//teacher.doc")));
		
	}
	public static String parseToString(File file) {
			
			Tika tika = new Tika();
			
			//可以传入一个 Metadata
			//Metadata m = new Metadata();
		
			
			try {
				return tika.parseToString(file);
				//return tika.parseToString(new FileInputStream(file), m);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TikaException e) {
				e.printStackTrace();
			}
			return null;
			
	}
	public static String parseFile(File file) {
		
		
		Parser parser = new AutoDetectParser();
		InputStream is = null;

		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		BodyContentHandler ch = new BodyContentHandler();

		Metadata m = new Metadata();
		
		
		
		ParseContext pc = new ParseContext();
		pc.set(Parser.class, parser);

		try {
			parser.parse(is, ch, m , pc);
			
			m.set(Metadata.KEYWORDS, "zhang peng");
			
			for(String name: m.names()){
				System.out.println(name+" : "+m.get(name));
			}
			
			
			return ch.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
