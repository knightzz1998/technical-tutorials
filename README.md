## 项目介绍

整理一些平常用到的技术

## 技术目录

- websocket
- [SPI机制的实现](docs/SPI机制的实现.md)


- Elasticsearch&.x

  - SpringBoot3.5 + ES7.x JavaAPI + 集成


### SpringSecurity

```sql
create table user
(
    id              int auto_increment comment '主键id'
        primary key,
    username        varchar(50)  null comment '用户名',
    password        varchar(50)  null comment '用户密码',
    salt            varchar(50)  null comment '加盐',
    email           varchar(100) null comment '用户邮箱',
    type            int          null comment '用户类型 : 0-普通用户; 1-超级管理员; 2-版主;',
    status          int          null comment '用户状态 : 0-未激活; 1-已激活;',
    activation_code varchar(100) null comment '激活码',
    header_url      varchar(200) null,
    create_time     timestamp    null
)
    charset = utf8;
```
插入数据
```sql
INSERT INTO user (username, password, salt, email, type, status, activation_code, header_url, create_time) VALUES
('john123', 'password123', 'salt123', 'john@example.com', 0, 1, 'abc123', 'https://example.com/john.jpg', '2023-05-19 10:30:00'),
('admin', 'admin123', 'salt456', 'admin@example.com', 1, 1, 'def456', 'https://example.com/admin.jpg', '2023-05-19 11:15:00'),
('moderator', 'moderator123', 'salt789', 'moderator@example.com', 2, 1, 'ghi789', 'https://example.com/moderator.jpg', '2023-05-19 12:00:00');

```