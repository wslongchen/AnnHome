### 前言

本文首发公众号【一名打字员】

在工作中，我们往往需要与数据打交道，需要将一些数据如用户信息或者一些其它的资料保存下来，这个时候我们就需要使用数据库了。今天简单的介绍在node中如何使用MySQL数据库进行基本的增删改查操作。

### 步骤

- 配置初始化MySQL

首先我们需要引入 ```mysql``` 模块，然后进行初始化一些配置。
```js
var mysql = require('mysql');

var mysqlConfig = {   
		host : 'www.xxx.cn',
		user : 'root',
		password : '123456',
		database : 'xxx',
		port : 3306 ,
		dateStrings : true,
		debug : false
	}

var pool = mysql.createPool(mysqlConfig);
```
上面的代码中，我们配置了mysql的一些基本信息，如主机、用户、密码、数据库名和端口号等等，并打开了一个数据的连接池。

- SQL语句处理

```js
pool.getConnection(function(err, connection) {
    if(connection){
		connection.query("select * from TableName LIMIT ? OFFSET ?", [10,0], function(err, result) {
          if(result) {  
          	console.log(result);
          }
        	connection.release();  
         });
      }
      if(err){
          	console.log(err)
       }
});
```
我们使用 ```pool.getConnection``` 打开一个连接，并执行一条查询的SQL语句，并指定了查询10条记录。同理，我们可以通过这个命令执行 ```UPDATE``` 、 ```INSERT``` 和 ```DELETE``` 语句。
更多的参数可以在 [mysqljs][1] 中进行查看。

- 关于SQL语句

新增语句：主要用来向表中插入一条新的记录。

```SQL
INSERT INTO 表名称 VALUES (值1, 值2,....)
```
或者也可以
```SQL
INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
```
删除语句：主要用来在表中删除一条记录。
```SQL
DELETE FROM 表名称 WHERE 列名称 = 值
```
修改语句：主要用来在表中更新一条记录。
```SQL
UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
```
查询语句：主要用来在表中查询记录。
```SQL
SELECT 列名称 FROM 表名称
```


### 结语

通过上面的案例，我们应该大致知道在node中如何使用MySQL数据库了。关于MySQL还有很多需要的知识需要学习，具体可以关注隔壁好基友的公众号【andyqian】，上面分享了许多有关MySQL的小知识。



  [1]: https://github.com/mysqljs/mysql
