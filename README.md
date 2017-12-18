# 前言

本文首发公众号 【一名打字员】

网上墙内关于这方面的文章真的很少，本猿也是通过官网一点一点学习，希望能够帮助到大家。[express-gateway官网][1]

# 安装

+ 安装Express Gateway

```
npm install -g express-gateway
```

+ 创建一个项目

```
eg gateway create
```

+ 初始一些基本信息配置

```
eg gateway create
 ? What is the name of your Express Gateway? my-gateway
 ? Where would you like to install your Express Gateway? my-gateway
 ? What type of Express Gateway do you want to create? (Use arrow keys)
 ❯ Getting Started with Express Gateway
   Basic (default pipeline with proxy)
```

+ 启动

```
cd my-gateway && npm start
```

这样基本的一个项目就已经成型了。

# 使用方法

### 指定服务并把API暴露出来（API提供者）

+ 第一步

我们会发现，项目中已经把代理了一个现有的服务 ```https://httpbin.org/ip``` ,并对其进行管理。它提供了一个json的输出，我们可以展示一下快速网关的功能。访问 ```https://httpbin.org/ip``` ，会有以下输出：

```
{
  "origin": "218.80.1.67"  #自己的IP
}
```

+ 第二步


![clipboard.png](/img/bV0pEQ)


该服务被指定为快速网关中默认管道的服务端，网关有自己的代理策略，之前的 ```https://httpbin.org/ip``` 则是网关设置的一个服务请求。配置在config目录下的 ```gateway.config.yml``` 文件。

```
http:
  port: 8080
admin:
  port: 9876
  hostname: localhost
apiEndpoints:
  api:
    host: localhost
    paths: '/ip'
serviceEndpoints:
  httpbin:
    url: 'https://httpbin.org'
policies:
  - basic-auth
  - cors
  - expression
  - key-auth
  - log
  - oauth2
  - proxy
  - rate-limit
pipelines:
  default:
    apiEndpoints:
      - api
    policies:
    # Uncomment `key-auth:` when instructed to in the Getting Started guide.
    # - key-auth:
      - proxy:
          - action:
              serviceEndpoint: httpbin 
              changeOrigin: true
```

可以看到这里配置了一个默认的服务端点，网关会在默认代理策略中找到 ```httmbin``` 。

+ 第三步


![clipboard.png](/img/bV0pES)


接下来我们需要让 ```httpbin``` 服务作为一个API端点穿过网关，并当这个API公开时，可以被外部所访问。
在上面的 ```gateway.config.yml``` 配置文件中，我们找到 ```apiEndpoints``` 这个设置，其中有一个 ```api``` 的设置项。

```
apiEndpoints:
  api:
    host: localhost
    paths: '/ip'
```

PS:默认情况下，API请求路径将被代理策略挂在服务端点中。

+ 第四步

![clipboard.png](/img/bV0pEW)

现在我们就可以通过网关来访问API了，访问 ```http://localhost:8080/ip``` 。

### 创建API消费者

方便管理API，我们将允许使用API的授权用户称为“消费者”。如用户的创建，进入项目根目录，创建：

```
eg users create
 Enter username [required]: mrpan
 Enter firstname [required]: coder
 Enter lastname [required]: coder
 Enter email: 1049058427@qq.com
 Enter redirectUri: www.baidu.com
√ Created 892775c8-80c5-480e-b596-6cb3133691f2

 "firstname": "coder",
 "lastname": "coder",
 "email": "1049058427@qq.com",
 "redirectUri": "www.baidu.com",
 "isActive": true,
 "username": "mrpan",
 "id": "892775c8-80c5-480e-b596-6cb3133691f2",
 "createdAt": "Sat Dec 16 2017 13:21:13 GMT+0800 (中国标准时间)",
 "updatedAt": "Sat Dec 16 2017 13:21:13 GMT+0800 (中国标准时间)"

```

### API权限认证

+ 第一步

现在我们的API是公开的，没有进行权限的控制，所以任何人都可以对它进行访问。我们现在用密钥授权对它进行保护，首先必须要将这个授权策略加入在配置文件 ```gateway.config.yml``` 中。

```
pipelines:
  - name: getting-started
    apiEndpoints:
      - api
    policies:
      - key-auth:
      - proxy:
        - action:
          serviceEndpoint: httpbin
          changeOrigin: true
```

+ 第二步

![clipboard.png](/img/bV0pGZ)

将密钥分给上面的用户 ```mrpan```

```
eg credentials create -c mrpan -t key-auth -q
10b9Yaalb982DreBukZvGf:3A2bhd1xzqwAvNWX0QfjD5
```
PS：上面的-q选项是将输出限制为api的key，便于粘贴复制。

+ 第三步

![clipboard.png](/img/bV0pHl)

再次访问 ```http://localhost:8080/ip``` ，这个时候便不会打印结果了，只会打印出 ```Unauthorized``` 。

+ 第四步

![clipboard.png](/img/bV0pHE)

我们在访问的时候把key加上。这样，就能继续使用API了。

到这里，网关的基本使用也就差不多了，大家可以梳理一下在自己的系统中进行扩展。

# 结语

现在大多大型网站架构都采用了微服务的模式，把系统拆分成一个一个的微服务，服务层可能会使用java或者使用其它语言编写，毕竟有Netflix这样的先例，成功的使用Node.js API网关及其JAVA后端来支持广泛的客户端。

  [1]: http://www.express-gateway.io

