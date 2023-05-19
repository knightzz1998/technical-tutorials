package cn.knightzz.community.mapper;

import cn.knightzz.community.entity.User;

/**
 * @author 王天赐
 * @title: UserMapper
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-05-19 11:43
 */
public interface UserMapper {
    User selectByName(String username);
}
