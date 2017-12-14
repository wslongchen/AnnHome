# 前言

作为一名全干打字员，干活时经常会被要求使用各种各样的语言去实现各种各样的需求，来回切换起来写的代码就会或多或少有点不规范。今天我们以JAVA为例，讲讲在代码中，我们需要注意的某些规范。（本文标准依赖于阿里巴巴的JAVA开发代码规范）

# 示例

以下举出本猿在工作中常常出现的问题，包括但不仅限于：

+ 逻辑判断语句

在 ```if/else/for/while/do``` 语句中必须使用大括号，即使只有一行代码，避免使用下面的形式：
```java
if(condition) statements;
```
+ 属性copy

很多童鞋喜欢使用 ```Apache Beanutils``` 进行属性的copy， ```Apache BeanUtils``` 性能较差，我们应该尽量避免使用,可以使用其他方案比如 ```Spring BeanUtils``` , ```Cglib BeanCopier``` 。
```java
TestObject a = new TestObject();
TestObject b = new TestObject();
a.setX(b.getX());
a.setY(b.getY());  
```

+ 覆写方法

所有的覆写方法，都必须要加上 ```@Override``` 注解。

+ 类方法命名

方法名、参数名、成员变量、局部变量都应该统一使用 ```lowerCamelCase``` ，类名使用 ```UpperCamelCase``` 风格，遵从驼峰命名的标准，尽量避免如 ```_``` ```-```等字符连接,但以下情形例外：（领域模型的相关命名）DO / BO / DTO / VO / DAO。另外，类都应该加上创建者的信息，方法名也应该加上对应的参数及用途说明。

常量命名应该全部大写，但此间使用下划线隔开，力求语义表达完整清楚，不要嫌名字长。

+ Random实例

首先 ```Random``` 示例包括 ```java.util.Random``` 或者 ```Math.random()```，我们应该避免其被多线程使用，虽然共享该实例是线程安全的，但会因竞争统一 ```seed``` 导致性能下降。
```java
ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    singleThreadPool.execute(()-> System.out.println(Thread.currentThread().getName()));
    singleThreadPool.shutdown();   
```

+字符串拼接

在循环体内，我们应当使用 ```StringBuilder``` 的 ```append``` 方法进行字符串的连接。

```JAVA
反例:
    String result;
    for (String string : tagNameList) {
        result = result + string;
    }   
正例:
    StringBuilder stringBuilder = new StringBuilder();
    for (String string : tagNameList) {
        stringBuilder.append(string);
    }
    String result = stringBuilder.toString();   
```

+ equals判断

很多人喜欢使用下面的代码进行 ```equals``` 判断是否为某个值：
```JAVA
public static final String type = "FOOD";
if(Object.equals(type)){
    //do something
}
```
对象中的equals很容易抛空指针异常，所以我们应该尽量使用常量或者确定有值的对象来调用equals。

```JAVA
public void f(String str){
        String inner = "hi";
        if(inner.equals(str)){
            System.out.println("hello world");
        }
    }
```

+ 集合初始化

我们往往在集合初始化的时候忘记指定集合的初始值大小，在高并发的情况下，这样很可能会造成内存的使用不当引起一系列的问题。所以在使用诸如 ```HashMap``` 的时候尽量指定初始值的大小。
```JAVA
反例:   
   Map<String, String> map = new HashMap<String, String>();   
正例: 
   Map<String, String> map = new HashMap<String, String>(16);   
```

+ 注释

方法内部应当使用单行注释，在被注释语句的上方另起一行，使用 ```//``` 进行注释，多行注释则使用 ```/* */``` ，强迫症下应注意与代码对齐。
```JAVA
public void method() {
        // Put single line comment above code. (Note: align '//' comment with code)
        int a = 3;
    
        /**
        * Some description about follow code. (Note: align '/**' comment with code)
        */
        int b = 4;
    } 
```

+ Switch语句

在每一个switch块内，每一个case都必须通过 ```break/return``` 来终止或者是注释说明程序继续执行到某一个case为止，并且都应该包含一个 ```default``` 语句放在最后，即便没有代码。
```JAVA
switch( x ){
        case 1 :
        break ;
        case 2 :
        break ;
        default :
    }  
```

# 结语

虽然我们往往写出的代码可能不是很高效、简洁，但是我们一定注意代码的可读性，毕竟代码除了机器看之外，也是给人看的。

# 福利

送福利送福利啦，本猿最近获得了三张 ```5QB``` 的抵用卷，本着蚊子再小也是肉的原则，把它送给在公众号上留言的前三位童鞋，留言的前三位童鞋看到后记得在后台留下QQ号联系打字员大大领取福利哟~
