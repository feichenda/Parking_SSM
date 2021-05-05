<%--
  Created by IntelliJ IDEA.
  User: c1376
  Date: 03/01/2021 001
  Time: 9:30:55 PM
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
</head>
<body class="layui-layout-body">
<div class="my-body">
    <div class="layui-form">
        <table class="layui-table">
            <colgroup>
                <col width="200">
                <col width="500">
            </colgroup>
            <thead>
            <tr>
                <th>标题</th>
                <th>结果</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>ID</td>
                <td>${merchantdetailedinfo.id}</td>
            </tr>
            <tr>
                <td>商家名称</td>
                <td>${merchantdetailedinfo.merchantname}</td>
            </tr>
            <tr>
                <td>商家地址</td>
                <td>${merchantdetailedinfo.merchantaddress}<br>经度:${locationdetailedinfo.longitude}&nbsp;&nbsp;&nbsp;&nbsp;纬度:${locationdetailedinfo.latitude}
                </td>
            </tr>
            <tr>
                <td>车位总数量</td>
                <td>${parkingnumberdetailedinfo.allnumber}</td>
            </tr>
            <tr>
                <td>营业执照</td>
                <td>
                    <div id="layer-photos-license" class="layer-photos-demo">
                        <c:forEach var="license_path" items="${license_paths}">
                            <%--<img layer-src="http://localhost:8080/${license_path}"
                                 src="http://localhost:8080/${license_path}" alt="营业执照">--%>
<%--                            <img layer-src="http://39.108.48.82:8080/${license_path}"--%>
<%--                                 src="http://39.108.48.82:8080/${license_path}" alt="营业执照" width="100px" height="75px">--%>
                            <img layer-src="http://120.78.208.177:8080/${license_path}"
                                 src="http://120.78.208.177:8080/${license_path}" alt="营业执照" width="100px" height="75px">
                        </c:forEach>
                    </div>
                </td>
            </tr>
            <tr>
                <td>停车场照片</td>
                <td>
                    <div id="layer-photos-image" class="layer-photos-demo">
                        <c:forEach var="images_path" items="${images_paths}">
                            <%--<img layer-src="http://localhost:8080/${images_path}"
                                 src="http://localhost:8080/${images_path}" alt="停车场照片">--%>
<%--                            <img layer-src="http://39.108.48.82:8080/${images_path}"--%>
<%--                                 src="http://39.108.48.82:8080/${images_path}" alt="停车场照片" width="100px" height="75px">--%>
                            <img layer-src="http://120.78.208.177:8080/${images_path}"
                                 src="http://120.78.208.177:8080/${images_path}" alt="停车场照片" width="100px" height="75px">
                        </c:forEach>
                    </div>
                </td>
            </tr>
            <tr>
                <td>收费标准</td>
                <td>
                    <div><span>前一小时:</span><span>${ratedetailedinfo.onehour}元</span></div>
                    <div><span>超过一小时:</span><span>${ratedetailedinfo.otherone}元/30分钟</span></div>
                </td>
            </tr>
            <tr>
                <td>联系电话</td>
                <td>${merchantdetailedinfo.phone}</td>
            </tr>
            <tr>
                <td>联系QQ</td>
                <td>${merchantdetailedinfo.QQ}</td>
            </tr>
            <tr>
                <td>联系人</td>
                <td>${merchantdetailedinfo.linkman}</td>
            </tr>
            <tr>
                <td>营业状态</td>
                <td>${merchantstatedetailedinfo.operatingstate}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<script src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use(['element', 'layer', 'jquery'], function () {
        var element = layui.element;
        var layer = layui.layer;
        $ = layui.jquery;

        layer.photos({
            photos: '#layer-photos-license'
            , anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        });

        layer.photos({
            photos: '#layer-photos-image'
            , anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        });
    });
</script>
</body>
</html>
