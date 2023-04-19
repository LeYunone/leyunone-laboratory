package com.leyunone.laboratory.web.project.resultcode.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.leyunone.laboratory.web.project.resultcode.bean.Code;
import com.leyunone.laboratory.web.project.resultcode.bean.DataResponse;
import com.leyunone.laboratory.web.project.resultcode.bean.LuceneCO;
import com.leyunone.laboratory.web.project.resultcode.dao.CodeDao;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-04-17
 */
@Service
public class LuceneService {
    
    @Autowired
    private CodeDao codeDao;
    
    private static final String DIR_SAVE_PATH  ="/opt/temp/dir";

    /**
     * 情况索引库
     */
    public void clearDir(){
        File file = new File(DIR_SAVE_PATH);
        if(file.exists()){
            FileUtil.del(file);
        }
    }
    
    /**
     * 创建索引库
     * @param codes
     */
    public void createCodeDir (List<Code> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            //默认创建所有博客索引文档
            codes = codeDao.selectByCon(null);
        }
        IndexWriter indexWriter = null;
        try {
            List<Document> documents = new ArrayList<>();
            //创建索引库位置
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(DIR_SAVE_PATH));
            //自定义分词器
            Analyzer analyzer = new SpiltCharAnalyzer();
            //创建输出流 write
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(directory, indexWriterConfig);

            for (Code code : codes) {
                Document document = new Document();
                //记录标题和id即可
                Field id = new StringField("id", String.valueOf(code.getCodeId()), Field.Store.YES);
                Field title = new TextField("title", code.getCode()+":  ["+code.getMessage()+"]", Field.Store.YES);
                document.add(id);
                document.add(title);
                documents.add(document);
            }
            //一次处理
            indexWriter.addDocuments(documents);
        } catch (Exception e) {
            AssertUtil.isTrue("引擎模式加载失败");
        } finally {
            if (indexWriter != null) {
                //关闭输出流
                try {
                    indexWriter.close();
                } catch (IOException e) {
                    AssertUtil.isTrue("引擎模式加载失败");
                }
            }
        }
    }


    /**
     * 关键词搜索博客索引库
     *
     * @param key
     * @param size
     * @param index
     * @return
     */
    public DataResponse<LuceneCO> getCodeDir (String key, Integer index, Integer size) {
        String error = "";
        List<Code> result = new ArrayList<>();
        LuceneCO luceneDTO = new LuceneCO();
        IndexReader indexReader = null;
        try {
            Analyzer analyzer = new SpiltCharAnalyzer();
            //关键词
            QueryParser qp = new QueryParser("title", analyzer);
            if (StrUtil.isBlank(key)) {
                key = "";
            }
            if (key.contains("\\")) {
                error = "操作失败：系统原因，搜索关键字请别带上\\";
                throw new RuntimeException();
            }
            if (key.contains("/")) {
                key = key.replaceAll("/", "");
            }
            if(key.equals("")){
                return DataResponse.of(luceneDTO);
            }
            Query query = qp.parse(key);

            //高亮关键字
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

            //打开索引库输入流
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(DIR_SAVE_PATH));
            indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            //上一页的结果
            ScoreDoc lastScoreDoc = getLastScoreDoc(index, size, query, indexSearcher);

            //从上一页最后一条数据开始查询  达到分页的目的
            TopDocs topDocs = indexSearcher.searchAfter(lastScoreDoc, query, size);
            long totle = topDocs.totalHits;
            luceneDTO.setTotole(totle);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                //获得对应的文档
                Document doc = indexSearcher.doc(scoreDoc.doc);
                String title = doc.get("title");
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                result.add(Code.builder().codeId(Integer.parseInt(doc.get("id"))).codeHeight(highlighter.getBestFragment(tokenStream, title)).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            AssertUtil.isTrue(error);
        } finally {
            if (indexReader != null) {
                try {
                    indexReader.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        luceneDTO.setListData(result);
        return DataResponse.of(luceneDTO);
    }

    private ScoreDoc getLastScoreDoc (int pageIndex, int pageSize, Query query, IndexSearcher indexSearcher) throws IOException {
        if (pageIndex == 1) return null;
        //获取上一页的数量
        int num = pageSize * (pageIndex - 1);
        TopDocs tds = indexSearcher.search(query, num);
        return tds.scoreDocs[num - 1];
    }
}
