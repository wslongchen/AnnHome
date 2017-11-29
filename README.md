### 前言

今天推荐一个比较简单又好玩的模块 ```qrcode-terminal``` ，这是一个在控制端生成二维码的模块，在我们编写某些控制台应用的时候可以很方便的进行某些操作。

### 使用方法

```js
const qrcode = require('qrcode-terminal')

qrcode.generate('来来来，大家一起入门到放弃!');

//我们也可以这样写
qrcode.generate('来来来，大家一起入门到放弃!', {
    small: false
})

```



我们可以通过传入一个字符串生成一个很普通的二维码。

![clipboard.png](/img/bVZpnc)

### 结语

```qrcode-terminal```支持windows和linux机器，所以无论是终端还是dos窗口都能够使用，还在犹豫啥，赶紧动手玩玩看吧！

