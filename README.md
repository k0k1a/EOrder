# EOrder

#### 1. 介绍
本项目分为3部分，包括微信小程序、Web商户管理系统和Java服务器。

| 目录         | 说明         |
| ------------ | ------------ |
| Eorder       | Java项目代码 |
| eorder-admin | Web管理系统  |
| eorder-mini  | 微信小程序   |
| pic          | 图片         |

#### 2. 功能概览
系统总体功能下图所示。

![项目功能概览.drawio](./pic/功能.drawio.png)



#### 3. 系统环境

##### 3.1 环境

1. Java环境：JDK8、MySQL5.7、Maven、Redis、Docker。
2. 小程序环境：微信小程序。
3. Web端使用layUI开发。

##### 3.2 技术栈

Java技术栈：

1.  Spring Boot
2.  Spring MVC
3.  Mybatis/Mybatis Generator
4.  Mysql
5.  Redis
6.  Swagger
7.  Maven

#### 4. 使用说明

##### 4.1 Java端使用说明

1. MySQL数据库导入：建立db_school_eorder数据库将EOrder根目录下的db_school_eorder.sql文件导入。
2. redis数据库：修改yml文件的host和port正确即可。
3. 启动Application.java
4. 启动后，项目接口文档地址：http://localhost:8080/swagger-ui.html#/

##### 4.2 微信小程序使用说明

1. 导入项目至微信开发助手使用

##### 4.3 Web管理系统使用说明

1. 部署到Web服务器使用

#### 5. 项目展示

##### 5.1 微信小程序

###### **登录**

![](./pic/login.png)

###### **主页**

![](./pic/index.png)

###### **详情**

![](./pic/goods-info.png)

###### **加入购物车**

![](./pic/add-cart.png)



###### **订单详情**

![](./pic/order-info.png)



###### **评论**

![](./pic/comment.png)

###### **优惠券**

![](./pic/coupon.png)

###### **搜索**

![](./pic/search.png)

##### 5.2 Web管理系统

###### **主页**

![](./pic/admin-index.png)

###### **系统列表实例**

![](./pic/admin-goods-list.png)

###### **添加商品**

![](./pic/admin-goods-add.png)

#### 6. 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
