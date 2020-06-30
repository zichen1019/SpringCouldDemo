# 分布式Demo

## [nacos 文档](https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html)

### nacos-server

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