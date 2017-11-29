### 续节

上回书说到，师徒四人途经狮驼国，狮驼国三位国师对唐僧心怀不轨，要与师徒四人进行斗法。咳，跑偏了跑偏了。

大家静一静，上一次最后我们介绍了Express的模板引擎，今天我们接着上次的继续。

### 庖丁解牛（续）

- 更换模板引擎

首先我们在 ```package.json``` 文件中 ```dependencies``` 节点增加包：
```json
 "ejs": "~2.5.2",
```
执行 ```npm install``` 编译过后，修改 ```app.js``` 文件中的模板渲染一行。
```js
//将jade换成ejs
app.set('view engine', 'ejs');
```
然后将 ```index.jade``` 换成 ```index.ejs``` ，因为现在我们已经将模板切换到ejs了。
下面是 ``` index.js``` 文件内容。

```ejs
<%include header.ejs %>
    <p>Wecolme to <%=title%></p>
<%include footer.ejs %>
```
现在java程序员是不是看着顺眼了很多呢，这仅仅是个人习惯，仅供参考。

- 工程目录总览

现在我们回顾一下整体工程的目录结构：
```json
- bin            # 命令文件
- node_modules   # 下载的依赖包
- public         # 静态资源目录
- routes         # 路由文件
- views          # 视图模板文件
  app.js         # 入口文件
  package.json   # 工程依赖配置文件
```
现在相信大家对整个项目已经有了基本的认识了，接下来我们就开始对微信公众号的集成了。

### 集成微信公众号

关于微信公众平台的开发文档可以去 [官网][1] 获取，里面有很详细的介绍，以下配图步骤均来自官方文档：
从文档里面我们可以知道，首先我们必须要在自己公众号的后台进行服务器的配置。

![clipboard.png](/img/bVZo2k)

然后填上我们的服务器配置，由于我们暂时还没有写，所以这里暂时先写上 ```http://www.dailyguitar.cc/wechat/index```。

接下来我们就得写对 ```/wechat/index``` 的处理了。

- 验证服务端的有效性

微信会对上面我们填写的地址进行有效性的检测，它会用请求我们的地址，我们必须对它请求过来的参数进行解密，然后返回同样的数据给它，否则将无法使用公众平台的相关接口。

首先我们在 ```index.js``` 中加入如下代码，
```js
/* 微信校验 */
var token="weixin";

router.get('/wechat/index', function(req, res, next) {
  try{
        var signature = req.query.signature;
        var timestamp = req.query.timestamp;
        var nonce = req.query.nonce;
        var echostr = req.query.echostr;
        /*  加密/校验流程如下： */
        //1. 将token、timestamp、nonce三个参数进行字典序排序
        var array = new Array(token,timestamp,nonce);
        array.sort();
        var str = array.toString().replace(/,/g,"");
        //2. 将三个参数字符串拼接成一个字符串进行sha1加密
        var sha1Code = crypto.createHash("sha1");
        var code = sha1Code.update(str,'utf-8').digest("hex");
        //3. 获得加密后的字符串可与signature对比，标识该请求来源于微信
        if(code===signature){
            res.send(echostr);
            console.log(""+echostr);
        }else{
            res.send("error");
        }
    }catch(error){
        console.log("error:"+error);
  	}
});
```
其中我们用到了一个加密模块 ```crypto``` ，我们需要手动引用一下，然后我们将项目运行起来，由于微信需要用到域名否则无法进行调试，这里本猿推荐两个内网穿透工具，一个是花生壳一个叫做ngrok，大家可以自己研究研究。

到这里其实与微信的对接已经完成了，在微信后台我们就可以配置成功了，记得别忘了我们的token哟。

+ 处理微信消息

当用户和我们的公众号发生操作的时候，微信会发送post请求到我们配置的URL中，所以我们只需要接收微信发过来的xml数据进行解析，并以xml的格式返回数据即可完成对消息的回复。
所以我们得对 ```index.js``` 再增加一个post的捕捉。
```js
/* 微信消息处理 */
router.post('/wechat/index', function(req, res, next) {
  try{
        var bodyData;
	    req.on("data",function(data){
	        /*微信服务器传过来的是xml格式的，是buffer类型，
	        	需要通过toString把xml转换为字符串*/
	        bodyData = data.toString("utf-8");

	    });
	    req.on("end",function(){
	        var ToUserName = getXMLNodeValue('ToUserName',bodyData);
	        var FromUserName = getXMLNodeValue('FromUserName',bodyData);
	        var CreateTime = getXMLNodeValue('CreateTime',bodyData);
	        var MsgType = getXMLNodeValue('MsgType',bodyData);
	        var Content = getXMLNodeValue('Content',bodyData);
	        var MsgId = getXMLNodeValue('MsgId',bodyData);
	        console.log(ToUserName);
	        console.log(FromUserName);
	        console.log(CreateTime);
	        console.log(MsgType);
	        console.log(Content);
	        console.log(MsgId);
	        var xml = '<xml><ToUserName>'+FromUserName+'</ToUserName><FromUserName>'+ToUserName+'</FromUserName><CreateTime>'+CreateTime+'</CreateTime><MsgType>'+MsgType+'</MsgType><Content>'+Content+'</Content></xml>';
	        res.send(xml);
    	});
    }catch(error){
        console.log("error:"+error);
  	}
});

/* 获取节点 */
function getXMLNodeValue(node_name,xml){
    var str = xml.split("<"+node_name+">");
    var tempStr = str[1].split("</"+node_name+">");
    return tempStr[0];
}
```
打开应用，对着自己公众号发送一条消息，很快也会收到一条内容一样的回复，大功告成！

### 结语

本次实战只为达到简单的操作效果，我们可以对其进行更深层次的处理和封装，具体可以看一看我的开源项目中对微信公众号模块的处理。另外node中有一个比较方便公众号开发的模块 ```wechat``` ，它提供了很多便捷的方法如支付以及模版消息等模块的支持，有兴趣的朋友可以自行研究一下。

  [1]: https://mp.weixin.qq.com/wiki
