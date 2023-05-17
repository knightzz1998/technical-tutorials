package cn.knightzz.elasticsearch.dao;

import cn.knightzz.elasticsearch.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 王天赐
 * @title: ProductDao
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-05-17 11:27
 */
public interface ProductDao extends ElasticsearchRepository<Product, Long> {
}
