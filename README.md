### 前言
本文首发【一名打字员】
上一节我们刚刚介绍完node的HTTP和HTTPS模块，相信我们也对nodejs有了更深层次的理解，接下来紧接着上一节的内容继续继续。

### 模块概述
- URL
在我们处理HTTP请求时，URL模块是我们使用频率最高的，因为它可以帮助我们更快的解析、生成和拼接URL。
URL提供了一个```parse```方法，可以将url变成一个对象。
```js
let url = require('url')
url.parse('http://www.mrpann.cn:8080/p/a/t/h?query=string#hash');
 =>
{ protocol: 'http:',
  host: 'www.mrpann.cn:8080',
  port: '8080',
  hostname: 'www.mrpann.cn',
  hash: '#hash',
  search: '?query=string',
  query: 'query=string',
  pathname: '/p/a/t/h',
  path: '/p/a/t/h?query=string',
  href: 'http://www.mrpann.cn:8080/p/a/t/h?query=string#hash' }
```
通过上面的方法，我们可以很轻松的拿到请求地址中有关的信息，同时，我们也能通过使用```format```方法，将一个URL对象格式化成一个请求地址。这个时候有的童鞋问了，打字员大大，我想拼接url怎么办呢，除了原始的```+```来拼接之外，我们还可以用```resolve```。示例如下：
```js
url.resolve('http://www.mrpann.cn/api', '../user');
=>
/* http://www.mrpann.cn/api/user */
```

- QUERY STRING
在实际运用中，当我们客户端发送请求的时候，我们往往URL参数字符串与参数对象的互相转换。querystring就是为此而生的，举个例子，当我们使用get方法请求一张图片的时候，url地址为```www.mpann.cn/resourse/img/user?type=img&isHistory=2```。我就可以使用```querystring.parse```方法，转换为```{ type: 'img', isHistory: 2 }```。
同理，我们也能够使用```querystring.stringify```将参数对象转换为url字符串。
- ZLIB
当我们开发一个稍微庞大的系统的时候，往往某个请求中需要发送比较大的数据给服务端或者返回稍大的数据给客户端，但是往往因为数据量过大，导致请求失败或者后台无法接收。这个时候我们就可以使用zlib这个模块，它提供了数据压缩和解压的功能，通过zlib我们能很方便的压缩HTTP响应体数据。在下面这个示例中我们展示了如何使用zlib这个模块。
```js
//服务端
http.createServer(function (request, response) {
    var body = []
    var headers = request.headers
    //判断是否支持gzip
    if(headers['accept-encoding'] || '').indexOf('gzip') != -1){
        zlib.gzip(data, function (err, data) {
            response.writeHead(200, {
                'Content-Type': 'text/plain',
                'Content-Encoding': 'gzip'
            });
            response.end(data);
        });
    }else {
        response.writeHead(200, {
            'Content-Type': 'text/plain'
        });
        response.end(data);
    }
    console.log(request.headers)
    request.on('data', function (chunk) {
        body.push(chunk)
    })

    request.on('end', function () {
        body = Buffer.concat(body)
        console.log(body.toString())
    })
}).listen(8080)//监听8080端口

//客户端
//request模块请求
//构建请求头信息
var options = {  
    hostname: 'www.mrpann.cn',  
    port: 8880,  
    path: '/api/v1/user/login',  
    method: 'POST',  
    headers: {  
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        'Accept-Encoding': 'gzip, deflate'  
    },
}
//发起服务端请求
var request = http.request(options, function(result) {
        var body = []
        result.on('data', function (chunk) {
            body.push(chunk)
        });
        result.on('end', function () {
            body = Buffer.concat(body)
            var headers = result.headers
            //判断是否支持gzip压缩
            if (headers['content-encoding'] === 'gzip') {
                //进行解压响应体
                zlib.gunzip(body, function (err, data) {
                    console.log(data.toString());
                });
            } else {
                console.log(data.toString());
            }
        });
    })
    request.on('error', function (e) {})
    //写入请求体内容
    request.write(qs.stringify({username:userName,password:pwd,captcha:cap}))
    request.end()
```
- NET
接触过其它语言网络编程这一块的童鞋应该比较熟悉socket。nodejs里面有没有呢，答案是肯定的。我们利用net模块可以创建一个socket服务端或者是客户端。但是由于前端的局限性，据本猿所知，使用socket的应用场景还很少，在这里仅仅展示一下利用net来创建一个简单的客户端和服务端应用。
```js
//服务端应用
net.createServer(function (conn) {
    conn.on('data', function (data) {
        conn.write([
            'HTTP/1.1 200 OK',
            'Content-Type: text/plain',
            'Content-Length: 11',
            '',
            'Hello World'
        ].join('\n'));
    });
}).listen(8080);
//客户端应用
var options = {
        port: 8080,
        host: 'www.example.com'
    };

var client = net.connect(options, function () {
        client.write([
            'GET / HTTP/1.1',
            'User-Agent: curl/7.26.0',
            'Host: www.baidu.com',
            'Accept: */*',
            '',
            ''
        ].join('\n'));
    });

client.on('data', function (data) {
    console.log(data.toString());
    client.end();
});
```

### 结语
到这里我们的网络模块基本上就结束了。在这一模块里面我们知道了以下几点内容
- 我们能够很方便的通过http和https进行一个客户端的请求与搭建一个服务端应用
- 处理http请求时往往回家上url.parse方法
- 可以通过request和response对象来读写对应的数据
- node中支持压缩和解压数据以及支持socket编程
- 人生无奈，我用node

下一次我们的入门到放弃node系列分享点什么呢，请发送到公众号后台一起投票吧！
