## MYSQL

### 键  

- 主键   唯一标识 只存在一个 不能缺失 不能为空
- 超键 表示元组的数据集 可以是一个 也可以是多个组成  超键包含候选键和主键
- 候选键 没有冗余的超键
- 外键 一个表中存在的另一个表中的主键  
___  

### 事务四个特性
ACID atomicity correspondence  isolation durability 原则性 一致性 隔离性 持久性  
Atomicity 每个操作都是最小单位 有始有终 无始无终 否则回滚到初始的状态  
Correspondence 事务开始和事务结束之后  数据库完整性约束没有被破坏
Isolation 串行化或者是序列化同一时间同一个操作行为
Durability 事务完成之后 数据真实有效存储在数据库中

### 事务隔离级别
read-uncommitted 读取未提交---- 脏读 不可重读 幻读  
read-committed 读取已提交------ 不可重复读 幻读  
repeatable-read 可重复读------- 幻读  
serializable  可串行化
___  
  

### 视图
- 视图是什么
    - 虚拟的表 只包含使用时需要查询检索的数据 可以简化查询语句 隐藏具体方式
- 主要用于数据的检索  大部分不支持更新操作

___  
  
### 相关Sql语句
- DATABASE
    - CREATE DATABASE 数据库名称; 创建数据库
    ```code
        create database sakura;
    ```
    - USER 数据库名称; 使用数据库
    ```code
        user sakura;
    ```
    - mysqladmin -u root -p create 数据库; 创建最高权限可见的数据
    - DROP DATABASE 数据库; 删除数据库
    - mysqladmin -u root -p drop 数据库; 使用最高权限进行删除操作
    - create table sakura
        ```mysql
              create table if not exists `sakura` (
                `sakura_id` int UNSIGNED AUTO_INCREMENT,
                `sakura_name` varbinary(128) not null, 
                primary key(`sakura_id`)
              ) ENGINE=InnoDb DEFAULT CHARSET=utf8
        ```
    - DROP TABLE sakura 删除表
    - show databases; 展示所有数据库
    - show tables; 展示所有的数据表
    - desc table_name; 描述表结构
    - select语句
    ```code
        SELECT column_name,column_name
        FROM table_name
        [WHERE Clause]
        [LIMIT N][ OFFSET M]
        
        select `sakura_id`, `sakura_name` 
        from sakura
        where `sakura_id` = 123 and `sakura_name` = 'sakura_rain'
        limit 3 // 查询结果个数限制
        offset 3 // 结果截取开始位置
    ```
    - update语句
    ```code
        UPDATE table_name SET field1=new-value1, field2=new-value2
        [WHERE Clause]
    
        update salura set sakura_name='sakura_park'
        where sakura_id = 1
    ```
    - delete
    ```code
        DELETE FROM TABLE_NAME 
        [WHERE CLAUSE]
        
        delete from sakura where sakura_id=1
    ```
    - LIKE
    ```code
        select * from sakura 
        where sakura_name like condition
        
        condition :
        '%a'     //以a结尾的数据
        'a%'     //以a开头的数据
        '%a%'    //含有a的数据
        '_a_'    //三位且中间字母是a的
        '_a'     //两位且结尾字母是a的
        'a_'     //两位且开头字母是a的
        
        % can replace one more 
        _ just replace one
    ```
    - UNION
    ```code
        SELECT expression1, expression2, ... expression_n
        FROM tables
        [WHERE conditions]
        UNION [ALL | DISTINCT]
        SELECT expression1, expression2, ... expression_n
        FROM tables
        [WHERE conditions];
        
        
        all   
                 select * from sakura union all
                  select * from plum;
                 +-----------+----------------------+
                 | sakura_id | sakura_name          |
                 +-----------+----------------------+
                 |         1 | sakura               |
                 |         2 | sakura_demo          |
                 |         3 | sakura_park          |
                 |         4 | sakura_bow           |
                 |         5 | dream_on_sakura_rain |
                 |         1 | plum                 |
                 |         2 | plum_park            |
                 |         3 | plum_rain            |
                 |         4 | plum_bow             |
                 |         5 | dream_on_sakura_rain |
                 +-----------+----------------------+
        dintinct
        
                select * from sakura union distinct select * from plum;
                +-----------+----------------------+
                | sakura_id | sakura_name          |
                +-----------+----------------------+
                |         1 | sakura               |
                |         2 | sakura_demo          |
                |         3 | sakura_park          |
                |         4 | sakura_bow           |
                |         5 | dream_on_sakura_rain |
                |         1 | plum                 |
                |         2 | plum_park            |
                |         3 | plum_rain            |
                |         4 | plum_bow             |
                +-----------+----------------------+
    ```
    - order by 
    ```code
        SELECT field1, field2,...fieldN table_name1, table_name2...
        ORDER BY field1, [field2...] [ASC [DESC]]
        
        desc
                 select * from sakura order by sakura_id desc;
                +-----------+----------------------+
                | sakura_id | sakura_name          |
                +-----------+----------------------+
                |         5 | dream_on_sakura_rain |
                |         4 | sakura_bow           |
                |         3 | sakura_park          |
                |         2 | sakura_demo          |
                |         1 | sakura               |
                +-----------+----------------------+
                5 rows in set (0.00 sec)
        
        asc
                 select * from sakura order by sakura_id asc;
                +-----------+----------------------+
                | sakura_id | sakura_name          |
                +-----------+----------------------+
                |         1 | sakura               |
                |         2 | sakura_demo          |
                |         3 | sakura_park          |
                |         4 | sakura_bow           |
                |         5 | dream_on_sakura_rain |
                +-----------+----------------------+
                5 rows in set (0.00 sec)

        
    ```
    - group by 
    ```code
        SELECT column_name, function(column_name)
        FROM table_name
        WHERE column_name operator value
        GROUP BY column_name;
        
        分组操作可以使用函数
        但是需要注意 group by 和 order by的先后性顺序问题
        select * from sakura 
        group by sakura_id        
    ```
    - [inner|left|right] join
    ```code
        INNER JOIN（内连接,或等值连接）：获取两个表中字段匹配关系的记录。
        LEFT JOIN（左连接）：获取左表所有记录，即使右表没有对应匹配的记录。
        RIGHT JOIN（右连接）： 与 LEFT JOIN 相反，用于获取右表所有记录，即使左表没有对应匹配的记录。
        
        select * from sakura left join plum on plum.plum_id=sakura.sakura_id;
        +-----------+----------------------+---------+----------------------+
        | sakura_id | sakura_name          | plum_id | plum_name            |
        +-----------+----------------------+---------+----------------------+
        |         1 | sakura               |       1 | plum                 |
        |         2 | sakura_demo          |       2 | plum_park            |
        |         3 | sakura_park          |       3 | plum_rain            |
        |         4 | sakura_bow           |       4 | plum_bow             |
        |         5 | dream_on_sakura_rain |       5 | dream_on_sakura_rain |
        +-----------+----------------------+---------+----------------------+
        5 rows in set (0.06 sec)
    ```
    - null 处理
    ```code
        IS NULL: 当列的值是 NULL,此运算符返回 true。-------------------------------
        IS NOT NULL: 当列的值不为 NULL, 运算符返回 true。                         |
        <=>: 比较操作符（不同于=运算符），当比较的的两个值为 NULL 时返回 true。----------
        
    ```
    - regexp 使用较少正则
    <pre>
        <table class="reference">
        <tbody><tr>
        <th style="width:30%">模式</th><th style="width:70%">描述</th>
        </tr>
        <tr>
        <td style="width:30%">^</td><td style="width:70%">匹配输入字符串的开始位置。如果设置了 RegExp 对象的 Multiline 属性，^ 也匹配 '\n' 或 '\r' 之后的位置。</td>
        </tr>
        <tr>
        <td style="width:30%">$</td><td style="width:70%">匹配输入字符串的结束位置。如果设置了RegExp 对象的 Multiline 属性，$ 也匹配 '\n' 或 '\r' 之前的位置。</td>
        </tr>
        <tr>
        <td style="width:30%">.</td><td style="width:70%">匹配除 "\n" 之外的任何单个字符。要匹配包括 '\n' 在内的任何字符，请使用象 '[.\n]' 的模式。</td>
        </tr>
        <tr>
        <td style="width:30%">[...]</td><td style="width:70%">字符集合。匹配所包含的任意一个字符。例如， '[abc]' 可以匹配 "plain" 中的 'a'。</td>
        </tr>
        <tr>
        <td style="width:30%">[^...]</td><td style="width:70%">负值字符集合。匹配未包含的任意字符。例如， '[^abc]' 可以匹配 "plain" 中的'p'。</td>
        </tr>
        <tr>
        <td style="width:30%">p1|p2|p3</td><td style="width:70%">匹配 p1 或 p2 或 p3。例如，'z|food' 能匹配 "z" 或 "food"。'(z|f)ood' 则匹配 "zood" 或 "food"。</td>
        </tr>
        <tr>
        <td style="width:30%">*</td><td style="width:70%">匹配前面的子表达式零次或多次。例如，zo* 能匹配 "z" 以及 "zoo"。* 等价于{0,}。</td>
        </tr>
        <tr>
        <td style="width:30%">+</td><td style="width:70%">匹配前面的子表达式一次或多次。例如，'zo+' 能匹配 "zo" 以及 "zoo"，但不能匹配 "z"。+ 等价于 {1,}。</td>
        </tr>
        <tr>
        <td style="width:30%">{n}</td><td style="width:70%">n 是一个非负整数。匹配确定的 n 次。例如，'o{2}' 不能匹配 "Bob" 中的 'o'，但是能匹配 "food" 中的两个 o。</td>
        </tr>
        <tr>
        <td style="width:30%">{n,m}</td><td style="width:70%">m 和 n 均为非负整数，其中n &lt;= m。最少匹配 n 次且最多匹配 m 次。</td>
        </tr>
        </tbody></table>
    </pre>
    
    - 事务
    ```code
        BEGIN 开始一个事务
        ROLLBACK 事务回滚
        COMMIT 事务确认
        
        
    ```
    
    - alter 表级别的修改
    ```mysql
       ALTER TABLE TABLE_NAME ADD column name type
       
       sakura add column
       
        alter table sakura add i int;
        
        desc sakura;
        +-------------+------------------+------+-----+---------+----------------+
        | Field       | Type             | Null | Key | Default | Extra          |
        +-------------+------------------+------+-----+---------+----------------+
        | sakura_id   | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
        | sakura_name | varchar(128)     | NO   |     | NULL    |                |
        | i           | int(11)          | YES  |     | NULL    |                |
        +-------------+------------------+------+-----+---------+----------------+

       sakura delete column
       
       alter table sakura drop i;
       Query OK, 5 rows affected (0.20 sec)
       Records: 5  Duplicates: 0  Warnings: 0
       
       mysql> desc sakura;
       +-------------+------------------+------+-----+---------+----------------+
       | Field       | Type             | Null | Key | Default | Extra          |
       +-------------+------------------+------+-----+---------+----------------+
       | sakura_id   | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
       | sakura_name | varchar(128)     | NO   |     | NULL    |                |
       +-------------+------------------+------+-----+---------+----------------+
       2 rows in set (0.01 sec)


      设置location
      alter table sakura add first int first 
      alter table sakura add after int after first 
  
      +-------------+------------------+------+-----+---------+----------------+
      | Field       | Type             | Null | Key | Default | Extra          |
      +-------------+------------------+------+-----+---------+----------------+
      | first       | int(11)          | YES  |     | NULL    |                |
      | after       | int(11)          | YES  |     | NULL    |                |
      | sakura_id   | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
      | sakura_name | varchar(128)     | NO   |     | NULL    |                |
      +-------------+------------------+------+-----+---------+----------------+

    ```
    - modify 属性类型的修改
    ```mysql
      alter table sakura modify sakura_name varchar(129);
      
      +-------------+------------------+------+-----+---------+----------------+
      | Field       | Type             | Null | Key | Default | Extra          |
      +-------------+------------------+------+-----+---------+----------------+
      | sakura_id   | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
      | sakura_name | varchar(129)     | YES  |     | NULL    |                |
      +-------------+------------------+------+-----+---------+----------------+

    ```
    
    - change 属性名加类型都可以
    ```mysql
       alter table sakura change sakura_name sakura_rain_name varchar(128);
     
        desc sakura;
       +------------------+------------------+------+-----+---------+----------------+
       | Field            | Type             | Null | Key | Default | Extra          |
       +------------------+------------------+------+-----+---------+----------------+
       | sakura_id        | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
       | sakura_rain_name | varchar(128)     | YES  |     | NULL    |                |
       +------------------+------------------+------+-----+---------+----------------+

    ```
    - alter set default 默认值的修改
    ```mysql
      alter table sakura alter sakura_rain_name set default 'xiaomeimei';
      
        +------------------+------------------+------+-----+------------+----------------+
        | Field            | Type             | Null | Key | Default    | Extra          |
        +------------------+------------------+------+-----+------------+----------------+
        | sakura_id        | int(10) unsigned | NO   | PRI | NULL       | auto_increment |
        | sakura_rain_name | varchar(128)     | YES  |     | xiaomeimei |                |
        +------------------+------------------+------+-----+------------+----------------+

      another way 
      
      alter table sakura modify j varchar(128) not null set default 'sakura_rain';
    ```
    - rename 修改表名
    ```mysql
      alter table sakura rename sakura_db;
    
        mysql> show tables;
        +------------------+
        | Tables_in_sakura |
        +------------------+
        | plum             |
        | sakura_db        |
        +------------------+
        2 rows in set (0.00 sec)
      
    为了后面的操作格式化   这里再次还原表名
      alter table sakura_db rename sakura
    ```
___


- 索引
    - 长处
        - 提高检索速度
        - 单列索引和组合索引（一个索引中包含多列）
        - 确保创建的索引在查询条件中
        - 好是好但是不要滥用
            - 虽然能大大提高检索查询的速度，但是会降低表的更新的速度  
            不仅仅要保存插入或者是更改的数据还要保存一下索引文件
                - 索引文件：索引表 保存主键与索引字段
    - 创建
        - 普通索引
        ```mysql
          # 创建索引
          create index name on sakura(sakura_rain_name(128));
          # 如果是CHAR，VARCHAR类型，length可以小于字段实际长度；如果是BLOB和TEXT类型，必须指定 length。
          alter table sakura add index phone(phone);
          # (还有其他的方式这里不做介绍)
          
          # 查看索引
           show index from sakura;
          +--------+------------+----------+--------------+------------------+-----------+-------------+----------+--------+------+------------+---------+
          | Table  | Non_unique | Key_name | Seq_in_index | Column_name      | Collation | Cardinality | Sub_part | Packed | Null | Index_type | Comment |
          +--------+------------+----------+--------------+------------------+-----------+-------------+----------+--------+------+------------+---------+
          | sakura |          0 | PRIMARY  |            1 | sakura_id        | A         |           2 |     NULL | NULL   |      | BTREE      |         |
          | sakura |          1 | name     |            1 | sakura_rain_name | A         |           2 |     NULL | NULL   | YES  | BTREE      |         |
          +--------+------------+----------+--------------+------------------+-----------+-------------+----------+--------+------+------------+---------+
                    
          # 删除索引
          drop index name on sakura;
        ```
        - 唯一索引
        ```mysql
        # 创建索引
        create unique index name on sakura(sakura_rain_name(128));
        # 允许出现null值但是必须是唯一的
        
        # 删除的方式也是一样的
        ```        
___  
  
- 临时表
    - 存储临时数据 只在当前链接可见 链接关闭自动释放

---  
- 复制表
    - 显示表的完整结构
    ```mysql
      show create table sakura \G;
    
      *************************** 1. row ***************************
             Table: sakura
      Create Table: CREATE TABLE `sakura` (
        `sakura_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
        `sakura_rain_name` varchar(128) DEFAULT 'xiaomeimei',
        `address` varchar(128) DEFAULT NULL,
        `phone` varchar(128) DEFAULT NULL,
        PRIMARY KEY (`sakura_id`),
        KEY `name_phone` (`sakura_rain_name`,`phone`)
      ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8
      1 row in set (0.04 sec)
      
      ERROR:
      No query specified
    ```
    - 修改表名称即可创建新的表
    ```mysql
      Create Table: CREATE TABLE `sakura_copy` (
              `sakura_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
              `sakura_rain_name` varchar(128) DEFAULT 'xiaomeimei',
              `address` varchar(128) DEFAULT NULL,
              `phone` varchar(128) DEFAULT NULL,
              PRIMARY KEY (`sakura_id`),
              KEY `name_phone` (`sakura_rain_name`,`phone`)
            ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8
          
       # 进行数据拷贝
     insert into sakura_copy select * from sakura;
    ```
---  
  
- 元数据
    <pre>
        <table class="reference">
        <tbody><tr><th>命令</th><th>描述</th></tr>
        <tr><td>SELECT VERSION( )</td><td>服务器版本信息</td></tr>
        <tr><td>SELECT DATABASE( )</td><td>当前数据库名 (或者返回空)</td></tr>
        <tr><td>SELECT USER( )</td><td>当前用户名</td></tr>
        <tr><td>SHOW STATUS</td><td>服务器状态</td></tr>
        <tr><td>SHOW VARIABLES</td><td>服务器配置变量</td></tr>
        </tbody></table>
    </pre>


---  

- 序列
    - 一张表只能有一个字段自增主键
    - auto_increment
    - 设置开始值 创建表sql驱动后面增加 auto_increment=100

---  
  
- 重复数据处理
    - 添加忽略
    ```mysql
      insert ignore sakura(sakura_id, sakura_rain_name) values(1231231231231, 'dream_on_sakura_rain'); # 主键id不能重复唯一索引
    ```
    - 覆盖旧值
    ```mysql
      replace into sakura(sakura_id, sakura_rain_name) values(1231231231231, 'sakura_demo_rain') # 主键重复覆盖操作
    ```
    - 重复数据统计
    ```mysql
      select count(*) as names_count from sakura group by sakura_rain_name
      having name_count > 1
    # having 是对查询之后的临时的属性的判断  可以说是查询之后进行结果筛选的类似where语句
    ```
    - 过滤重复数据 distinct
    ```mysql
      select distinct sakura_rain_name from sakura;
      
      or
      
      select * from sakura group by sakura_rain_name;
    ```

---  

- 数据导出
    - 导出数据
    ```mysql
      select * from sakura into outfile 'D:/sakura.txt';
    # 指定格式进行分割操作
      select * into outfile 'D:/sakura.txt' 
      fields terminated by ',' optionally enclosed by '"'
      lines terminated by '\n'
    ```
    - 导出表作为原始数据
    ```code
          # 包括创建信息的备份方式
          mysqldump -u root -p 123 sakura [sakura] > D:/sakura.txt
          
          # 不想有创建信息
          mysqldump -u root -p 123 --no-create-info --tab=D:/sakura.txt
    ```
    - 远程导出
    ```code
      mysqldump -h other-host.com -P port -u root -p database_name > dump.txt
    ```
    - 导入文件
    ```code
      mysql -uroot -p123 database_name < dump.txt
    ```
- 数据导入(不完全介绍  使用的时候可以在查询)
    - mysql -u root -p 123 < sakura.sql
    - source D:/sakura.txt
    - load data local infile 'sakura.txt' into table sakura(如果这个文本是你之前格式化的)
        - 参照 
        ```mysql
            mysql> LOAD DATA LOCAL INFILE 'dump.txt' INTO TABLE mytbl
            -> FIELDS TERMINATED BY ':'
            -> LINES TERMINATED BY '\r\n';
        ```
    - mysqlimport -uroot -p123 --local database_name sakura.txt

---

