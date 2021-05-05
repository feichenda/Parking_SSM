<%--
  Created by IntelliJ IDEA.
  User: c1376
  Date: 12/21/2020 021
  Time: 2:08:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>停车场后台管理</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">停车场后台管理</div>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="${pageContext.request.contextPath}/api/jump/tomain">
                    <img src="${pageContext.request.contextPath}/image/touxiang.jpg" class="layui-nav-img">
                    超级管理员
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="${pageContext.request.contextPath}/api/jump/tochangepassword">修改密码</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/api/user/jsplogout?name=admin">退出</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;">商家管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="${pageContext.request.contextPath}/api/merchant/selectUncheckMerchant?page=1">添加审核</a></dd>
                        <dd><a href="${pageContext.request.contextPath}/api/merchant/selectUncheckChangeMerchant?page=1">修改审核</a></dd>
                        <dd><a href="${pageContext.request.contextPath}/api/merchant/selectAllMerchant?page=1">信息查询</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">用户管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="${pageContext.request.contextPath}/api/customer/selectAllUser?page=1">信息查询</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="height:100%;width:100%;">
            <iframe id="demoAdmin" frameborder="0" scrolling="yes" width="100%" height="100%" src=""></iframe>
        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © 停车场后台管理
    </div>
</div>
<script src="${pageContext.request.contextPath}/layui/layui.all.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;

    });
    $(document).ready(function (){
       $("dd>a").click(function (e) {
           e.preventDefault();
           $("#demoAdmin").attr("src",$(this).attr("href"));
       })
    });
</script>
</body>
</html>
