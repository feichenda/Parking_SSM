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
                <col width="200">
                <col width="500">
            </colgroup>
            <thead>
            <tr>
                <th>标题</th>
                <th>旧信息</th>
                <th>新信息</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>ID</td>
                <td colspan="2">${newmerchantdetailedinfo.id}</td>
            </tr>
            <tr>
                <td>商家名称</td>
                <td>${newmerchantdetailedinfo.oldmerchantname}</td>
                <td>${newmerchantdetailedinfo.newmerchantname}</td>
            </tr>
            <tr>
                <td>商家地址</td>
                <td>${oldmerchantdetailedinfo.merchantaddress}<br>经度:${oldlocationdetailedinfo.longitude}&nbsp;&nbsp;&nbsp;&nbsp;纬度:${oldlocationdetailedinfo.latitude}
                <td>${newmerchantdetailedinfo.merchantaddress}<br>经度:${newmerchantdetailedinfo.longitude}&nbsp;&nbsp;&nbsp;&nbsp;纬度:${newmerchantdetailedinfo.latitude}
                </td>
            </tr>
            <tr>
                <td>营业执照</td>
                <td>
                    <div id="layer-photos-license" class="layer-photos-demo">
                        <c:forEach var="oldlicense_path" items="${oldlicense_paths}">
<%--                            <img layer-src="http://39.108.48.82:8080/${oldlicense_path}"--%>
<%--                                 src="http://39.108.48.82:8080/${oldlicense_path}" alt="营业执照" width="100px" height="75px">--%>
<%--                            <img layer-src="http://120.78.208.177:8080/${oldlicense_path}"--%>
<%--                                 src="http://120.78.208.177:8080/${oldlicense_path}" alt="营业执照" width="100px" height="75px">--%>
                            <img layer-src="http://127.0.0.1:8080/${oldlicense_path}"
                                 src="http://127.0.0.1:8080/${oldlicense_path}" alt="营业执照" width="100px" height="75px">
                        </c:forEach>
                    </div>
                </td>
                <td>
                    <div id="layer-photos-image" class="layer-photos-demo">
                        <c:forEach var="newlicense_path" items="${newlicense_paths}">
<%--                            <img layer-src="http://39.108.48.82:8080/${newlicense_path}"--%>
<%--                                 src="http://39.108.48.82:8080/${newlicense_path}" alt="停车场照片" width="100px" height="75px">--%>
<%--                            <img layer-src="http://120.78.208.177:8080/${newlicense_path}"--%>
<%--                                 src="http://120.78.208.177:8080/${newlicense_path}" alt="停车场照片" width="100px" height="75px">--%>
                            <img layer-src="http://127.0.0.1:8080/${newlicense_path}"
                                 src="http://1207.0.0.1:8080/${newlicense_path}" alt="停车场照片" width="100px" height="75px">
                        </c:forEach>
                    </div>
                </td>
            </tr>
            <tr>
                <td>收费标准</td>
                <td>
                    <div><span>前一小时:</span><span>${oldratedetailedinfo.onehour}元</span></div>
                    <div><span>超过一小时:</span><span>${oldratedetailedinfo.otherone}元/30分钟</span></div>
                </td>
                <td>
                    <div><span>前一小时:</span><span>${ratedetailedinfo.onehour}元</span></div>
                    <div><span>超过一小时:</span><span>${ratedetailedinfo.otherone}元/30分钟</span></div>
                </td>
            </tr>
            <tr>
                <td>联系电话</td>
                <td colspan="2">${oldmerchantdetailedinfo.phone}</td>
            </tr>
            <tr>
                <td>联系QQ</td>
                <td colspan="2">${oldmerchantdetailedinfo.QQ}</td>
            </tr>
            <tr>
                <td>联系人</td>
                <td colspan="2">${oldmerchantdetailedinfo.linkman}</td>
            </tr>
            <tr>
                <td>当前审核状态</td>
                <td colspan="2">${newmerchantdetailedinfo.auditstate}</td>
            </tr>
            <tr>
                <td>备注</td>
                <td colspan="2">${newmerchantdetailedinfo.remark}</td>
            </tr>
            </tbody>
        </table>
        <div>
            <input id="merchant" type="hidden" value=${newmerchantdetailedinfo.oldmerchantname}>
            <input id="newmerchant" type="hidden" value=${newmerchantdetailedinfo.newmerchantname}>
            <input id="auditstate" type="hidden" value=${newmerchantstatedetailedinfo.auditstate}>
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
            , anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        });

        layer.photos({
            photos: '#layer-photos-image'
            , anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        });

        $(document).on('click', '#pass_btn', function () {
            var merchant = $("#merchant").val();
            var newmerchant = $("#newmerchant").val();
            var auditstate = $("#auditstate").val();
            if (auditstate != "已通过") {
                $.ajax({
                    url: "${pageContext.request.contextPath}/api/merchant/updateParikingInfo",
                    type: "get",
                    data: {"oldname": merchant, "auditstate": "已通过", "remark": ""},
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    success: function (data) {
                        if (data != null) {
                            switch (data.code) {
                                case 200:
                                    // window.location.reload();
                                    layer.msg("您已完成审核", {time: 4000});
                                    window.location.href = "${pageContext.request.contextPath}/api/merchant/selectMerchantByMerchantname?name=" + newmerchant;
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
            var auditstate = $("#auditstate").val();
            if (auditstate != "已通过") {
                layer.prompt({
                    formType: 2,
                    title: '输入驳回原因',
                    area: ['400px', '200px'] //自定义文本域宽高
                }, function (value, index, elem) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/api/merchant/updateParikingInfo",
                        type: "get",
                        data: {"oldname": merchant, "auditstate": "未通过", "remark": value},
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

