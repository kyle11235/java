package org.apache.lucene.demo;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
//import org.apache.lucene.index.Term;


/** Deletes documents from an index that do not contain a term. */
public class DeleteFiles {
  
  private DeleteFiles() {}                         // singleton

  /** Deletes documents from an index that do not contain a term. */
  public static void main(String[] args) {
    String usage = "java org.apache.lucene.demo.DeleteFiles <unique_term>";
//    if (args.length == 0) {
//      System.err.println("Usage: " + usage);
//      System.exit(1);
//    }
    try {
      Directory directory = FSDirectory.open(new File("d://3000//index"));
      IndexReader reader = IndexReader.open(directory, false); // we don't want read-only because we are about to delete

      Term term = new Term("contents", "zhang");
    
      
      //一般删除使用writer来删除的 
      //这种删除方式可以马上更新索引，而且可以根据序号来删除，其实也是创建writer来删除，close才提交
      int deleted = reader.deleteDocuments(term);

      System.out.println("deleted " + deleted +
 			 " documents containing " + term);

      // one can also delete documents by their internal id:
      /*
      for (int i = 0; i < reader.maxDoc(); i++) {
        System.out.println("Deleting document with id " + i);
        reader.delete(i);
      }*/

      reader.close();
      directory.close();

    } catch (Exception e) {
      System.out.println(" caught a " + e.getClass() +
			 "\n with message: " + e.getMessage());
    }
  }
}
