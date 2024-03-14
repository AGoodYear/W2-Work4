# West2-Online 第四轮考核

接口文档：https://apifox.com/apidoc/shared-08aa8b68-b501-4a46-9bd4-a4ec304772b4

（部署的服务器中了挖矿病毒，已简单处理，但不知道清除成功没有。若服务不可用请联系我）

## 技术栈

Mysql、SpringBoot（2.6.15）、Mybatis-Plus、Krest（一款整合了Shiro和Jwt的安全框架）、FastJSON

## 项目结构

```
│  │  ├─java
│  │  │  └─com
│  │  │      └─ivmiku
│  │  │          └─W4R3
│  │  │              ├─config （存放相关配置文件 ）
│  │  │              ├─controller （Controller层）
│  │  │              ├─entity （实体层）
│  │  │              ├─mapper （Mybatis-Plus自动生成的Mapper）
│  │  │              ├─service （Service层）
│  │  │              └─utils （Redis小工具和密码验证类）
│  │  └─resources （SpringBoot配置文件和Dockerfile）
```



## 项目亮点

当Token达到设定的时间时，会自动生成新的Token并返回

基本完成了考核文档中的任务，详见接口文档

## 存在的问题

Redis和Mysql的数据一致性目前采用Redis项目定时写入Mysql的方案，一定程度上可能会导致额外的性能开销

Krest框架一定程度上限制了项目后续的改造以及发展

视频搜素不支持联合搜索，



