# SpringBoot集成Flume+kafka


### 此项目仅供本人学习
* [kafka中文官方文档](https://kafka.apachecn.org)
* [GitHub 代码地址](https://github.com/xiaoxigua13437/springboot-kafka)



    **此项目仅供本人学习**


    ####1.安装依赖
        本地安装jdk,maven,flume,kafka等 基础工具

    ####2.文件说明
        /src/main/java/com/sp/producer目录下以知识点分包,以main()和*test()为程序入口,Test为测试类;

        pom.xml文件中注释了maven的基本配置方法.

    ####3.目录说明
        以下为/src/main/java/com/yushu目录下的目录结构



   _目录结构_


    ├──config               //自定义配置类
    │  
    ├──controller              //controller控制层
    │
    ├──dao          //dao层,主要mybatis实现 mysql
    │
    ├──entity         //实体
    │
    ├──exception               //异常处理类 
    │
    ├──interceptor            // 自定义注解+拦截器+Redis 限流
    │
    ├──kafka                //kafka处理类
    │
    ├──service            //service层
    │
    ├──util               //工具类
    │    ├──base    
    │    ├──threadpool
    │    ├──CheckRequestTypeUtil //自定义判断请求类型
    │    ├──RedisCommonUtil        //redis 通用工具类
    │    ├──SerializableUtil     //序列化与反序列化
    │    └──SpringContextHolder       //动态获取bean
    │    └──UserSerializable       //用户序列化测试类
    │
    │──ProducerApplication        //项目启动类









"# springboot-kafka-producer" 






