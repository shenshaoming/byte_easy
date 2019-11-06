# byte_easy自动生成框架
根据数据库中的信息生成对应的后台管理页面,让编码更加简单.
<br>2019/10/2在添加和删除方法中，增加了获取当前时间的函数（局限于createTime和updateTime），
同时，在controller.java.ftl中的field.propertyName表示的是当前实体中的属性而不是数据库。
<br>数据库文件.sql再resources目录下的sql目录中,再application.properties中可以配置数据库连接就可以
<br>因为引入Lombok框架的问题，Idea必须下载Lombok插件

<br>后端开发框架SpringBoot，Spring MVC，Mybatis Plus和Mybatis；日志框架slf4j，lombok；权限管理
使用的是SpringSecurity；数据库连接池使用的druid框架。
<br>前端框架：ECharts,layui做界面优化,ueditor做富文本编辑器JQuery；
前台的form表单提交校验依靠JQuery.validate扩展插件库；
通过Thymeleaf模板引擎做数据渲染。
<br>
<br>自动生成模板在resources/generator中，采用的freemarker模板引擎
<br>
<br>由于是从许多小项目中集成做的大框架，开发起来需要认为手写的代码更少，但也有缺点，算是一个重量级框架。
<br>但该框架极适合小项目的快速开发以及诸位扩展知识面，学习新知识。其中还有少量我尚未提及的不常用的东西，
这或许让这套框架显得极为笨重。。。

<br>启动服务器后，访问localhost:8080/，登录用户名是admin,密码是123456.
