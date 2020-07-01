# 分布式Demo

## [流程图](https://github.com/zhoutaoo/SpringCloud/raw/master/docs/auth.png)

![](https://github.com/zhoutaoo/SpringCloud/raw/master/docs/auth.png)

### 1. 调用zc-gateway

### 2. 调用zc-authorization

### 3. 调用zc-authentication

### 4. 调用对应的服务

## 技术点

技术名称|相关介绍
---|---
nacos | [nacos 文档](https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html)


### nacos

#### [配置方式](https://github.com/alibaba/spring-cloud-alibaba/wiki/Nacos-config)

#### nacos-server

> 默认登录用户及密码为：nacos

## 知识点

### Gradle构建的项目模块之间调用方式

> 此处可参考 zc-gateway项目调用zc-common项目

#### 1. 修改zc-common项目的build.gradle文件
```gradle
plugins {
    id 'org.springframework.boot' version '2.3.1.RELEASE'
    id 'io.spring.dependency-management' version '1.0.9.RELEASE'
    id 'java'
}
```
> 将以上配置替换为下面的配置
```gradle
buildscript {
    ext {
        springBootVersion = '2.3.1.RELEASE'
    }
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
    }
}

plugins {
    // 其他项目需要引用当前项目时，需要将id 'java'改为id 'java-library'，避免其他项目调用时报乱码错误。
    id 'java-library'
}
```
#### 2. 修改zc-gateway项目的setting.gradle文件
```gradle
// 引入其他项目
includeFlat 'zc-common'
```
#### 3. 修改zc-gateway项目的build.gradle文件
```gradle
dependencies {
  // 自己的通用包
  compile project(':zc-common')
}
```

### 采用namespace与配置文件的结合方式动态获取数据

- [Spring Cloud Alibaba Nacos（功能篇）(1)](https://zhuanlan.zhihu.com/p/68700978)
- [Spring Cloud Alibaba Nacos（功能篇）(2)](https://zhuanlan.zhihu.com/p/91827339)
- [Spring Cloud Alibaba Nacos（功能篇）(3)](https://zhuanlan.zhihu.com/p/92782024)

#### 1. 添加命名空间

- 空间名： dev
- 描述： 开发模式
- 空间id可以自动生成

#### 2. 根据项目及namespace进行添加配置文件

#### 3. 在项目的bootstrap.yml文件上添加namespace属性

> spring.cloud.nacos.discovery.namespace

> spring.cloud.nacos.config.namespace

#### 4. 调用方法和正常yml配置文件中的调用方法相同，但是需要在使用的类上添加@RefreshScope注解，如果nacos中的属性值改变了，此处的值也会改变。