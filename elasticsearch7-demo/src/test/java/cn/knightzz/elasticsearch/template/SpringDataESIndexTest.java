package cn.knightzz.elasticsearch.template;

import cn.knightzz.elasticsearch.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 王天赐
 * @title: SpringDataESIndexTest
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-05-17 11:38
 */
@SpringBootTest
@RunWith(SpringRunner.class)

public class SpringDataESIndexTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    //创建索引并增加映射配置
    @Test
    public void createIndex() {
        //创建索引，系统初始化会自动创建索引
        //即使删除了, 再次执行索引操作时也会自动创建
        System.out.println("创建索引");
    }

    @Test
    public void catIndex() {
        //创建索引，系统初始化会自动创建索引
        boolean flg = elasticsearchRestTemplate.deleteIndex(Product.class);
        System.out.println("删除索引 = " + flg);
    }

    @Test
    public void addIndex() {
        // 创建索引
        elasticsearchRestTemplate.createIndex("");
        //boolean create = elasticsearchRestTemplate.indexOps(Product.class).create();
        //System.out.println("创建索引 => " + create);
    }

    @Test
    public void deleteIndex() {
        boolean flg = elasticsearchRestTemplate.deleteIndex(Product.class);
        // 新版 7.15.2
        // boolean delete = elasticsearchRestTemplate.indexOps(Product.class).delete();
        System.out.println("删除索引 = " + flg);
    }
}
