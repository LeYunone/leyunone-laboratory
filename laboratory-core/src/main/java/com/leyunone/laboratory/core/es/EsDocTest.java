package com.leyunone.laboratory.core.es;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
 
import java.util.ArrayList;
import java.util.List;

@Service
public class EsDocTest {

    @Autowired
    private ElasticSearchSpringApiTest.ProductDao productDao;
 
    /**
     * 新增
     */
    @Test
    public void save() {
        ElasticSearchSpringApiTest.Product product = new ElasticSearchSpringApiTest.Product();
        product.setId(2L);
        product.setTitle("ipad mini");
        product.setCategory("ipad");
        product.setPrice(1998.0);
        product.setImages("http://ipad.jpg");
        productDao.save(product);
    }
 
 
    //修改
    @Test
    public void update(){
        ElasticSearchSpringApiTest.Product product = new ElasticSearchSpringApiTest.Product();
        product.setId(2L);
        product.setTitle("iphone");
        product.setCategory("mobile");
        product.setPrice(6999.0);
        product.setImages("http://www.phone.jpg");
        productDao.save(product);
    }
 
    //根据 id 查询
    @Test
    public void findById(){
        ElasticSearchSpringApiTest.Product product = productDao.findById(2L).get();
        System.out.println(product);
    }
 
    //查询所有
    @Test
    public void findAll(){
        Iterable<ElasticSearchSpringApiTest.Product> products = productDao.findAll();
        for (ElasticSearchSpringApiTest.Product product : products) {
            System.out.println(product);
        }
    }
 
    //删除
    @Test
    public void delete(){
        ElasticSearchSpringApiTest.Product product = new ElasticSearchSpringApiTest.Product();
        product.setId(2L);
        productDao.delete(product);
    }
 
    //批量新增
    @Test
    public void saveAll(){
        List<ElasticSearchSpringApiTest.Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ElasticSearchSpringApiTest.Product product = new ElasticSearchSpringApiTest.Product();
            product.setId(Long.valueOf(i));
            product.setTitle("iphone" + i);
            product.setCategory("mobile");
            product.setPrice(5999.0 + i);
            product.setImages("http://www.phone.jpg");
            productList.add(product);
        }
        productDao.saveAll(productList);
    }
 
    //分页查询
    @Test
    public void findByPageable(){
        //设置排序(排序方式，正序还是倒序，排序的 id)
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        int currentPage=0;//当前页，第一页从 0 开始， 1 表示第二页
        int pageSize = 5;//每页显示多少条
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize,sort);
        //分页查询
        Page<ElasticSearchSpringApiTest.Product> productPage = productDao.findAll(pageRequest);
        for (ElasticSearchSpringApiTest.Product Product : productPage.getContent()) {
            System.out.println(Product);
        }
    }
 
    /**
     * term 查询
     * search(termQueryBuilder) 调用搜索方法，参数查询构建器对象
     */
    @Test
    public void termQuery(){
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "iphone");
        Iterable<ElasticSearchSpringApiTest.Product> products = productDao.search(termQueryBuilder);
        for (ElasticSearchSpringApiTest.Product product : products) {
            System.out.println(product);
        }
    }
 
    /**
     * term 查询加分页
     */
    @Test
    public void termQueryByPage(){
        int currentPage= 0 ;
        int pageSize = 5;
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "phone");
        Iterable<ElasticSearchSpringApiTest.Product> products =
                productDao.search(termQueryBuilder,pageRequest);
        for (ElasticSearchSpringApiTest.Product product : products) {
            System.out.println(product);
        }
    }
 
}