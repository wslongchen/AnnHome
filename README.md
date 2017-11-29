### 前言

本文首发公众号【一名打字员】

相信通过之前的几篇文章，大家都对node有更深层次的了解了。node作为一个灵活性较强的工具，我们可以使用它来做很多很多好玩的东西。前几年微信公众号开发比较火，大街小巷都在招人，所以今天本着实战的目的，给大家介绍一下使用express来开发微信公众号的具体流程。

### Express

开始前，简单介绍一下express，官方对它的介绍是“基于nodejs平台，快速、开放、极简的web开发框架”。虽然近年来它的生态圈已经远远不如koa强大，很多express应用都用koa进行重写过，但是作为一个从事JAVA后台的打字员来说，还是比较依赖于使用express的。

简单来说，express可以帮助我们快速的建立一个web应用，有多快呢，接下来就可以看到了。

- 安装

首先我们需要安装Express模块，使用 ```nom install express -g``` 安装全局模块，这个过程一般会很快。

- 初始化

安装成功后，我们就可以使用 ```express``` 这个工具了。

￼![图片描述][1]

通过help列表，我们看到，它有一系列的命令提供使用，后面还有具体的用法。接下来我们初始化一个express应用，使用 ```express init wechatTest```命令express会帮我们快速的创建一个工程，这里面包含着我们基本的框架，可以直接通过 ```npm install,npm start``` 运行。

![图片描述][2]

由于默认的端口是3000，所以我们直接访问localhost:3000就能看到express的欢迎界面了。
简单的一个web站就已经建立成功了，怎么样，快不快、意不意外、惊不惊喜？

### 庖丁解牛

接下来我们一块一块的分析整个结构，对整个项目有一个清晰的了解。

- package.json

我们上次说过，通过package.json来管理整个项目的依赖以及一些信息的初始化。
```json
{
  "name": "wechattest",
  "version": "0.0.0",
  "private": true,
  "scripts": {
    "start": "node ./bin/www"
  },
  "dependencies": {
    "body-parser": "~1.16.0",
    "cookie-parser": "~1.4.3",
    "debug": "~2.6.0",
    "express": "~4.14.1",
    "jade": "~1.11.0",
    "morgan": "~1.7.0",
    "serve-favicon": "~2.3.2"
  }
}
```
可以看到，我们这个项目里面依赖了好几个包，其中每个包的具体作用我们后面再进行讲解。另外我们看到配置里为我们编写了一个 ```start```脚本，所以，我们能够通过```npm start```来快速的执行脚本。

- app.js

我们看到根目录下有一个app.js文件，这是整个应用的入口。
```js
var express = require('express');//引入express模块
var path = require('path');//url地址解析
var favicon = require('serve-favicon');//服务端图标
var logger = require('morgan');//日志
var cookieParser = require('cookie-parser');//cookie中间件
var bodyParser = require('body-parser');//请求中间件

//路由
var index = require('./routes/index');
var users = require('./routes/users');

var app = express();

//模版引擎设置
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

//图标放置于public（静态资源配置）
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
//静态资源中间件
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', index);
app.use('/users', users);

//404界面捕捉
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

//异常界面处理
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;

```
注释很清楚的解释了各个部分的作用，在构建一个web服务器的时候，我们通常最重要的工作就是路由处理，也就是响应针对某个路径的请求。在这里我们直接使用路由配置的方法，如 ```app.get``` 和 ```app.post```来进行配置。

- 中间件

上面的文件中有提到中间件的概念，在express中，通常在收到请求后和发送响应之前这个阶段执行的一些函数，这个就叫做中间件。在app.js中我们看到可以使用 ```app.use``` 来使用某个中间件。其原型如下：
```js
app.use([path,] function [, function...])
```
比如上面的 ```express.static(path.join(__dirname, 'public'))``` 也是一个中间件，通常用来处理静态文件的目录。

- 路由

在express中，提供了一个Router对象来针对GET、POST等处理的路由，通常把它传给 ```app.use``` 。我们可以看到上面引用了一个index模块，打开index.js文件。

```js
var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports = router;
```
我们拦截了```/```,所以我们访问localhost根路径的时候就会被这里拦截，然后进行相关的操作，我们可以在这里加入对相关的路径处理。

- 模版渲染

上面介绍了使用路由来对路径的解析和拦截，但是如何在拦截到请求路径的时候，将界面渲染出来呢，这个时候就需要使用模版引擎了。express初始化的时候给我们设置了使用 ```jade``` 的模版引擎进行渲染，如index.jade。
```jade
extends layout

block content
  h1= title
  p Welcome to #{title}

```
jade的语法这里不做多的介绍，这里可以去官网中查看具体的说明介绍。

### 未完待续

由于篇幅过长，这篇文章将会被分为几个小节，这里我们能够使用express搭建起基本的框架，并对其中的一些模块有了一定的了解。之前用java的时候写jsp写的比较多，后来也是使用freemarker等模版引擎渲染界面，所以对jade的语法不是很中意，所以下一章首先会介绍如何将jade换乘ejs，一个语法类似jsp的引擎。

另外，留言功能已经开通了哟，各位小哥哥小姐姐有问题的可以直接留言哟，欢迎来撩～（奸笑脸）

  [1]: /img/bVZmIQ
  [2]: /img/bVZmJt
