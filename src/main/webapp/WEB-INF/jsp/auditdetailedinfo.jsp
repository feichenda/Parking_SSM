<%--
  Created by IntelliJ IDEA.
  User: c1376
  Date: 2021/4/4 0004
  Time: 下午 8:37:16
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
    <script src="${pageContext.request.contextPath}/layui/lay/modules/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/layui/lay/modules/layer.js"></script>
</head>
<body class="layui-layout-body">
<div class="my-body">
    <div class="layui-form">
        <table class="layui-table">
            <colgroup>
                <col width="100">
                <col width="400">
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
            <tr>
                <td>当前审核状态</td>
                <td>${merchantstatedetailedinfo.auditstate}</td>
            </tr>
            <tr>
                <td>备注</td>
                <td>${merchantstatedetailedinfo.remark}</td>
            </tr>
            </tbody>
        </table>
        <div>
            <input id="merchant" type="hidden" value=${merchantdetailedinfo.merchantname}>
            <input id="operatingstate" type="hidden" value=${merchantstatedetailedinfo.operatingstate}>
            <input id="auditstate" type="hidden" value=${merchantstatedetailedinfo.auditstate}>
            <button type="button" class="layui-btn pass" id="pass_btn">通过</button>
            <button type="button" class="layui-btn layui-btn-danger" id="back_btn">驳回</button>
        </div>
    </div>
</div>
<script>
    //JavaScript代码区域
    layui.use(['element', 'layer', 'jquery'], function () {
        var element = layui.element;
        var layer = layui.layer;
        $ = layui.jquery;

        layer.photos({
            photos: '#layer-photos-license'
            ,anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        });

        layer.photos({
            photos: '#layer-photos-image'
            ,anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        });

        $(document).on('click', '#pass_btn', function () {
            var merchant = $("#merchant").val();
            var operatingstate = $("#operatingstate").val();
            var auditstate = $("#auditstate").val();
            if (auditstate != "已通过") {
                $.ajax({
                    url: "${pageContext.request.contextPath}/api/merchant/updateCheckState",
                    type: "post",
                    data: JSON.stringify({
                        id: 0,
                        merchantname: merchant,
                        operatingstate: operatingstate,
                        auditstate: "已通过",
                        remark: ""
                    }),
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    success: function (data) {
                        if (data != null) {
                            switch (data.code) {
                                case 200:
                                    window.location.reload();
                                    layer.msg("您已完成审核", {time: 4000});
                                <%--window.location.href = "${pageContext.request.contextPath}/api/jump/tomain";--%>
                                    break;
                                default:
                                    layer.alert(data.message);
                                    break;
                            }
                        }
                    }
                });
            } else {
                layer.alert("该商家已审核，您无需操作");
            }

        });

        $(document).on('click', '#back_btn', function () {
            var merchant = $("#merchant").val();
            var operatingstate = $("#operatingstate").val();
            var auditstate = $("#auditstate").val();
            if (auditstate != "已通过") {
                layer.prompt({
                    formType: 2,
                    title: '输入驳回原因',
                    area: ['400px', '200px'] //自定义文本域宽高
                }, function (value, index, elem) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/api/merchant/updateCheckState",
                        type: "post",
                        data: JSON.stringify({
                            id: 0,
                            merchantname: merchant,
                            operatingstate: operatingstate,
                            auditstate: "未通过",
                            remark: value
                        }),
                        contentType: "application/json;charset=utf-8",
                        dataType: "json",
                        success: function (data) {
                            if (data != null) {
                                switch (data.code) {
                                    case 200:
                                        window.location.reload();
                                        layer.msg("您已完成审核", {time: 4000});
                                    <%--window.location.href = "${pageContext.request.contextPath}/api/jump/tomain";--%>
                                        break;
                                    default:
                                        layer.alert(data.message);
                                        break;
                                }
                            }
                        }
                    });
                    layer.close(index);
                });
            } else {
                layer.alert("该商家已审核，您无需操作");
            }
        });
    });

</script>
</body>
</html>

