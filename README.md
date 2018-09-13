# 一、工程简介
(mysql、redis、rabbitmq、支付参数以及短信模板参数已经全部清空)
## 1、searched-base
主要包含数据库中所有的表转换成的实体和持久化工具类以及映射文件

### 1.1 searched-base\src\main\java\com\songpo\searched\entity
这个目录下是跟表对应的实体

### 1.2 searched-base\src\main\java\com\songpo\searched\mapper
这个目录下是完成实体对应的增删改查操作的接口

### 1.3 searched-base\src\main\resources\com\songpo\searched\mapper
这个目录下是持久化接口所对应的XML文件

## 2、searched-common
用于放置通用实现或缓存等

## 3、searched-api
提供所有的api接口和授权验证接口

# 二、API文档
- 本地地址: http://localhost:8080/sl/swagger-ui.html
- 线上地址: http://api1.搜了.com/sl/swagger-ui.html

## OAuth2授权

### 1、获取授权

### 2、检测令牌是否过期

token是我们获取的授权令牌，可以通过获取当前时间戳和过期时间进行比对
判断当前时间是否大于或等于过期时间戳

```
请求地址：http://api1.搜了.com/sl/oauth/check_token?token=1cfc3404-056d-4ce3-a882-377eacf82e61
响应结果：
{
     "scope": [
         "read"
     ], // 权限范围
     "exp": 1521428341, // 过期时间
     "client_id": "18701272322" // 客户端标识
 }

```
