package cn.knightzz.elasticsearch.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 王天赐
 * @title: User
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-05-15 20:47
 */
@Getter
@Setter
public class User {
    private String name;
    private Integer age;
    private String sex;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
