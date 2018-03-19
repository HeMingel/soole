# 说明
为了方便复制粘贴的需要，特意将Java API开发整理成了脚手架。
持久化框架分别准备了Spring Data Jpa和MyBatis，开发语言使用Java和Kotlin。
构建工具则使用了Maven和Gradle。
其实Web项目中是可以使用java和kotlin进行混编的，而构建工具也是可以放在一个工程里
方便不同的人选择不同的构建工具

## OAuth2授权

### 1、获取授权

### 2、检测令牌是否过期

token是我们获取的授权令牌，可以通过获取当前时间戳和过期时间进行比对
判断当前时间是否大于或等于过期时间戳

```
请求地址：http://localhost:8080/oauth/check_token?token=1cfc3404-056d-4ce3-a882-377eacf82e61
响应结果：
{
     "scope": [
         "read"
     ], // 权限范围
     "exp": 1521428341, // 过期时间
     "client_id": "18701272322" // 客户端标识
 }

```
