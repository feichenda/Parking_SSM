<%--
  Created by IntelliJ IDEA.
  User: c1376
  Date: 03/01/2021 001
  Time: 4:21:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/body.css">
    <script charset="UTF-8"
            src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
</head>
<body class="layui-layout-body">
<form action="${pageContext.request.contextPath}/api/merchant/selectMerchantByKey?page=1" method="post">
    <div class="demoTable">
        搜索用户：
        <div class="layui-inline">
            <input class="layui-input" name="keyword" id="keyword" autocomplete="off">
        </div>
        <input class="layui-btn" type="submit" value="搜索"/>
    </div>
</form>

<div class="layui-form">
    <table class="layui-table">
        <colgroup>
            <col width="100">
            <col width="200">
            <col width="300">
            <col width="100">
            <col width="100">
            <col width="400">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>ID</th>
            <th>商家名称</th>
            <th>商家地点</th>
            <th>营业状态</th>
            <th>审核状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="merchantinfo" items="${merchantinfo}">
            <tr>
                <td>${merchantinfo.id}</td>
                <td>${merchantinfo.merchantname}</td>
                <td>${merchantinfo.merchantaddress}</td>
                <td>${merchantinfo.operatingstate}</td>
                <td>${merchantinfo.auditstate}</td>
                <td>
                    <a class="layui-btn layui-btn-xs"
                       href="${pageContext.request.contextPath}/api/merchant/selectMerchantByMerchantname?name=${merchantinfo.merchantname}">查看详情</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<nav aria-label="Page navigation" class="pager" style="padding: 0px 20px;">
    <ul class="pagination">
        <li><span>当前是第${pagemsg.pageNum}/${pagemsg.navigateLastPage}页</span></li>
        <li>
            <a href="${pageContext.request.contextPath}/api/merchant/selectAllMerchant?page=${pagemsg.navigateFirstPage}">首页</a>
        </li>
        <c:if test="${pagemsg.pageNum>=2}">
            <li>
                <a href="${pageContext.request.contextPath}/api/merchant/selectAllMerchant?page=${pagemsg.prePage}"
                   aria-label="Previous" title="上一页">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
        </c:if>
        <c:choose>
            <c:when test="${pagemsg.pageNum<=1}">
                <li class="disabled">
                    <a href="${pageContext.request.contextPath}/api/merchant/selectAllMerchant?page=${pagemsg.prePage}"
                       aria-label="Previous" title="上一页" onclick="return false;">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:when>
        </c:choose>
        <c:forEach var="s" begin="1" end="${pagemsg.pages}">
            <li>
                <a href="${pageContext.request.contextPath}/api/merchant/selectAllMerchant?page=${s}">${s}</a>
            </li>
        </c:forEach>
        <c:if test="${pagemsg.pageNum<pagemsg.navigateLastPage}">
            <li>
                <a href="${pageContext.request.contextPath}/api/merchant/selectAllMerchant?page=${pagemsg.nextPage}"
                   aria-label="Next" title="下一页">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </c:if>
        <c:choose>
            <c:when test="${pagemsg.pageNum>=pagemsg.navigateLastPage}">
                <li class="disabled">
                    <a href="${pageContext.request.contextPath}/api/merchant/selectAllMerchant?page=${pagemsg.nextPage}"
                       aria-label="Next" title="下一页" onclick="return false;">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:when>
        </c:choose>
        <li>
            <a href="${pageContext.request.contextPath}/api/merchant/selectAllMerchant?page=${pagemsg.navigateLastPage}">尾页</a>
        </li>
        <li><span>当前页显示${pagemsg.size}条</span></li>
        <li><span>共${getTotal}条数据</span></li>
    </ul>
</nav>

<script>
    //JavaScript代码区域
    layui.use(['form', 'layer', 'element'], function () {
        var element = layui.element;
        var form = layui.form;
        form.render('radio');
    });
</script>
</body>
</html>
