package cn.knightzz.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author 王天赐
 * @title: Product
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-05-17 11:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "shopping", shards = 3, replicas = 1)
public class Product {

    /**
     * 必须有 id,这里的 id 是全局唯一的标识，等同于 es 中的"_id"
     * 商品唯一标识
     */
    private Long id;
    /**
     * 商品名称
     * type : 字段数据类型
     * analyzer : 分词器类型
     * index : 是否索引 (默认:true)
     * Keyword : 短语,不进行分词
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    /**
     * 分类名称
     */
    @Field(type = FieldType.Keyword)
    private String category;
    /**
     * 商品价格
     */
    @Field(type = FieldType.Double)
    private Double price;
    /**
     * 商品图片
     */
    @Field(type = FieldType.Keyword, index = false)
    private String images;
}
