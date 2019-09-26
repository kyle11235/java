package org.apache.lucene.demo;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/** Index all text files under a directory. */
public class IndexFiles {

	private IndexFiles() {
	}

	// 索引文件存放位置
	static final File INDEX_DIR = new File("d://3000//index");

	/** Index all text files under a directory. */
	public static void main(String[] args) {
		String usage = "java org.apache.lucene.demo.IndexFiles <root_directory>";

		// if (INDEX_DIR.exists()) {
		// System.out.println("Cannot save index to '" +INDEX_DIR+
		// "' directory, please delete it first");
		// System.exit(1);
		// }

		// 被索引文件位置
		final File docDir = new File("d://3000//data");
		if (!docDir.exists() || !docDir.canRead()) {
			System.out
					.println("Document directory '"
							+ docDir.getAbsolutePath()
							+ "' does not exist or is not readable, please check the path");
			System.exit(1);
		}

		Date start = new Date();
		try {

			// 使用FSDirectory（代表建立索引的位置）和Analyzer准备IndexWriter
			//3.5已经禁用了此方法，用IndexWriterConfigure作为参数
			IndexWriter writer = new IndexWriter(FSDirectory.open(INDEX_DIR),
					new StandardAnalyzer(Version.LUCENE_30), true,
					IndexWriter.MaxFieldLength.LIMITED);
			System.out.println("Indexing to directory '" + INDEX_DIR + "'...");

			// 开始建立索引
			indexDocs(writer, docDir);
			// System.out.println("Optimizing...");//优化
			// writer.optimize();
			writer.close();

			Date end = new Date();
			System.out.println(end.getTime() - start.getTime()
					+ " total milliseconds");

		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass()
					+ "\n with message: " + e.getMessage());
		}
	}

	static void indexDocs(IndexWriter writer, File file) throws IOException {
		// do not try to index files that cannot be read
		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] files = file.list();
				// an IO error could occur
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						// 递归
						indexDocs(writer, new File(file, files[i]));
					}
				}
			} else {
				System.out.println("adding " + file);
				try {
					// 利用FileDocument进行索引文件的添加，在其中设置field等
					Document doc = FileDocument.Document(file);
					//根据情况加权
					
					if(file.getName().equals("fruits.txt")){
						doc.setBoost(10.0f);
					}
					
					writer.addDocument(doc);
				}
				// at least on windows, some temporary files raise this
				// exception with an "access denied" message
				// checking if the file can be read doesn't help
				catch (FileNotFoundException fnfe) {
					;
				}
			}
		}
	}

}
