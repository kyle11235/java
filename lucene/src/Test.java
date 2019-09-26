import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Test {
	private static IndexReader reader = null;

	// 通常reader设置为单例，关闭时候只关闭searcher
	//有的项目writer也只有一个，不关闭的话记得要commit
	public IndexReader getReader() {
		try {
			if (reader == null) {

				reader = IndexReader.open(FSDirectory.open(new File("d://3000//index")));

			} else {
				//这个方法似乎应该在3.5里面 ，解决一直开着reader，同时改变了索引，reader读的数据却不变的问题
				//reader.close();//先关闭原来的
				//reader = IndexReader.openIfChanged(reader));
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reader;
	}

	public static void main(String[] args) throws Exception {

		new Test().foo1();

	}

	public void foo1() throws Exception {

		IndexSearcher indexSearcher = new IndexSearcher(FSDirectory.open(new File("d://3000//index")));
		//默认搜索域是contents，可以改的
		QueryParser queryParser = new QueryParser(Version.LUCENE_30,
				"contents", new StandardAnalyzer(Version.LUCENE_30));
		
		//空格默认是or 可以改变它queryParser.setDefaultOperator(Operator.AND);
		
		Query query = queryParser.parse("football AND basketball");
		
		//改变搜索域为name，用冒号改变 
		query = queryParser.parse("name:fruits.txt");
		
		//再改变，可以用*，*默认不能放在首位 
		query = queryParser.parse("name:b*");
		
		//再改变，name不含有b*  contents含有basketball
		query = queryParser.parse("- name:b* + basketball");
		
		//query = queryParser.parse("id:[1 TO 3]");
		
		query = queryParser.parse("\"i like basketball\"");
		
		//i 和   basketball 之间有一个单词的
		query = queryParser.parse("\"i basketball\"~1");
		
		//模糊查询
		query = queryParser.parse("name:basketballss~");
		
		TopDocs hits = indexSearcher.search(query, null, 100);
		System.out.println("有" + hits.totalHits + "个结果");
		// ScoreDoc[] sds = hits.scoreDocs;
		for (ScoreDoc sd : hits.scoreDocs) {
			// System.out.println(sd);
			System.out.println("========================");
			Document d = indexSearcher.doc(sd.doc);
			System.out.println("文档序号: " + sd.doc);
			System.out.println("path: " + d.getField("path"));
			System.out.println("score: " + sd.score);

		}
		indexSearcher.close();
	}

	// 用reader建searcher
	public void foo2() throws Exception {
		
		
	
		IndexSearcher indexSearcher = new IndexSearcher(getReader());
		
		
		indexSearcher.close();

	}
}