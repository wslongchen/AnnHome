### 前言
本文首发公众号【一名打字员】
上一次相信大家都基本了解node的用法了，有做功课的童鞋肯定回去温习了一下js的语法。这些年来js发展很快，出了很多类似许多vue、react、node等等众所周知的玩意儿，对应的社区配套也越来越完善。好的，接下来我们补充一下node的内置网络模块，顺带复习一下网络编程的相关知识，毕竟“技多不压身”。

### API概览
其实nodejs一开始就是帮助编写高性能的web服务器，在这里简单介绍一下相关的API，好让大家知道什么情况下该使用哪个API，具体使用的方法还是去[官网][1]上瞄一下，毕竟官网已经解释的不能再详细了。我们将会介绍

- HTTP
- HTTPS
- URL
- QUERY STRING
- ZLIB
- NET

今天我们先主要介绍一下HTTP与HTTPS。

- HTTP与HTTPS

HTTP与HTTPS模块基本上都一样，唯一不同的地方就是HTTPS访问我们通常是需要证书的嘛，所以需要单独处理一下SSL证书。在这里先介绍一下HTTP模块，使用HTTP模块有两种用途，一是作为服务端，创建一个HTTP服务器，监听客户端并响应。二是作为客户端，进行HTTP请求，获取服务端信息。

说到HTTP请求，不得不插一句，相信很多人对其既陌生又熟悉。它大致上分为请求头和请求体，也就是我们常说的Request hearders和Request Body。就从访问某搜索引擎的请求来说，他的访问静态资源时请求内容如下：
```
host:ss0.bdstatic.com
method:GET
path:/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png
scheme:https
version:HTTP/1.1
accept:image/webp,image/*,*/*;q=0.8
accept-encoding:gzip, deflate, sdch
accept-language:zh-CN,zh;q=0.8
cache-control:max-age=0
if-modified-since:Tue, 06 Jun 2017 06:04:45 GMT
if-none-match:"593645fd-e7a"
referer:https://www.baidu.com/?tn=57095150_6_oem_dg
user-agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36
```
   由于这里是一个GET方法，所以上面都是请求头部分，当我们使用POST请求时，则会加上body。当客户端发送请求到我们服务端时，我们接收到对应的请求，就会调用相应的回调函数。在回调函数中我们就可以使用request对象来读取请求头的数据以及请求体的数据。
贴出新建一个HTTP服务器的示例代码，示例中创建了一个服务端应用并监控8080端口，当接收到客户端请求时，将会打印出请求头部内容以及请求体内容：

```js
//创建一个服务
http.createServer(function (request, response) {
    var body = []
    console.log(request.headers)
    request.on('data', function (chunk) {
        body.push(chunk)
    })

    request.on('end', function () {
        body = Buffer.concat(body)
        console.log(body.toString())
    })
}).listen(8080)//监听8080端口
```
HTTP响应与请求一样，也分为响应头和响应体。我们可以使用response对象来操作响应数据。我们将上面的例子改一下，将客户端的请求数据再返给客户端。
```js
//创建一个服务
http.createServer(function (request, response) {
    var body = []
    //写入响应数据
    response.writeHead(200, { 'Content-Type': 'text/plain' })
    request.on('data', function (chunk) {
        response.write(chunk)
        body.push(chunk)
    })

    request.on('end', function () {
        body = Buffer.concat(body)
        response.end()
    })
}).listen(8080)//监听8080端口
```
以上就是服务端所操作的过程，那么有人问了，客户端该如何操作呢。下面我们介绍一下客户端的流程，下面使用的是node中网络请求比较方便的模块```request```，当然node也提供了一个便捷的API供我们使用，各取所需嘛。
```js
//HTTP内置API
http.get('http://www.example.com/', function (response) {});
```
```js
//request模块请求
//构建请求头信息
var options = {  
    hostname: 'www.mrpann.cn',  
    port: 8880,  
    path: '/api/v1/user/login',  
    method: 'POST',  
    headers: {  
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'  
    },
}
//发起服务端请求
var request = http.request(options, function(result) {})
  	request.on('error', function (e) {})
    //写入请求体内容
	request.write(qs.stringify({username:userName,password:pwd,captcha:cap}))
	request.end()
```
这样一个完整的客户端发起请求到服务端响应返回数据的流程就结束了，最后我们介绍一下HTTPS中使用证书的步骤，因为HTTPS提供一个安全的加密的环境，保证了互联网请求的安全性。与上面的代码类似，我们只需要在创建服务时增加俩个参数即可。
```js
var options = {
        key: fs.readFileSync('./ssl/default.key'),
        cert: fs.readFileSync('./ssl/default.cer')
    }
//创建一个HTTPS服务
http.createServer(options,(request, response) =>{
    //请求处理
})
```
有的童鞋在这里肯定自己动手搭建了起来，会发现客户端https在用自建证书时客户端访问不了被拒绝了，尝试着在请求的头部中加上这一句，```rejectUnauthorized: false```是不是发现可以了呢，赶紧试一试吧。

### 结语
今天介绍了nodejs中利用HTTP以及HTTPS模块建立一个简单的服务端程序以及客户端的请求。下次我们会介绍node同样在网络编程中比较有用的模块。

### 彩蛋
细心的同学就会发现了，打字员大大，你的程序里咋都没有结束符号```;```呀。最后一个函数咋变成了```(res)=>{}```了,是不是BUG写多了手抖了。答案当然不是，这是ES6的语法，out了吧。ES6是JavaScript语言的一个标准，已经在2015发布，ES是ECMAScript，简单来说ECMAScript是JavaScript语言的国际标准，JavaScript是ECMAScript的实现。在这里你会看到一个不一样的js，对一个面向对象编程的打字员来说也很友好，有兴趣的同学可以自己去研究一下。（逃

  [1]: http://nodejs.org/api/http.html
