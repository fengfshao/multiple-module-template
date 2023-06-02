基于maven的java/scala混编多模块项目模版，可作为数据工程的主仓，也体现了某种大仓理念。
项目配置了覆盖率采集，单测报告产出的功能，可搭配流水线及代码规范质量拦截等实现CI，搭配下游调度平台可实现CD。

## 项目结构
```
└───multiple-module-template
    ├───.gitignore
    ├───pom.xml
    ├───README.md
    ├───[commons] 类库模块
    │    ├───pom.xml
    │    ├───src
    │    │   ├───main
    │    │   │   ├───java
    │    │   │   └───scala
    │    │   └───test
    │    └───resources
    │
    ├───[batch] 离线批任务
    │    ├───pom.xml
    │    ├───src
    │    |   ├───main
    │    |   │   ├───java
    │    │   │   └───scala
    │    |   └───test
    │    └───resources
    │
    ├───[stream] 实时流任务
    │    ├───pom.xml
    │    ├───src
    │    |   ├───main
    │    |   │   ├───java
    │    │   │   └───scala
    │    |   └───test
    │    └───resources
    |
    └───[zcoverage] 覆盖率采集
         └───pom.xml
```

## 构建目录
执行`mvn clean package`后，根目录的build目录中有三个目录，分别对应覆盖率，单测报告，jar包的产出目录。
+ jacoco
+ report
+ jars



## mvn打包命令
直接使用`mvn package`打包子模块会出错，如果这个子模块依赖了其他子模块的话。
使用
`mvn clean package -DskipTests --pl batch --am`可以打包编译batch模块及其依赖的commons模块。

## 单元测试

