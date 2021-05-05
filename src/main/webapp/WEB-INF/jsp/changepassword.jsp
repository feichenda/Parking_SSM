<%--
  Created by IntelliJ IDEA.
  User: c1376
  Date: 12/23/2020 023
  Time: 1:55:01 PM
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/changepassword.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/md5.js"></script>
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <form class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">原密码</label>
            <div class="layui-input-inline">
                <input type="password" id="oldpassword" name="oldpassword" placeholder="请输入原密码" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">新密码</label>
            <div class="layui-input-inline">
                <input type="password" id="newpassword" name="newpassword" placeholder="请输入新密码" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">确认新密码</label>
            <div class="layui-input-inline">
                <input type="password" id="renewpassword" name="renewpassword" placeholder="请再次输入新密码"
                       autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="change">确认修改</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>

</div>

<script>
    //JavaScript代码区域
    layui.use(['form', 'element', 'layer'], function () {
        var element = layui.element;
        var layer = layui.layer;
        var form = layui.form;
        form.on('submit(change)', function (data) {
            var oldpass = data.field.oldpassword;
            var newpass = data.field.newpassword;
            var renewpass = data.field.renewpassword;
            if (oldpass.length > 0) {
                if (newpass.length > 0) {
                    if (renewpass.length > 0) {
                        if (oldpass == newpass) {
                            alert("新旧密码不能相同");
                            window.location.reload();
                        } else {
                            if (newpass == renewpass) {
                                var oldpsd = stringtomd5(oldpass);
                                var newpsd = stringtomd5(newpass);
                                $.ajax({
                                    url: "${pageContext.request.contextPath}/api/user/changepassword",
                                    type: "post",
                                    data: JSON.stringify({
                                        id: 0,
                                        username: "admin",
                                        oldpassword: oldpsd,
                                        newpassword: newpsd,
                                        role: "管理员"
                                    }),
                                    contentType: "application/json;charset=utf-8",
                                    dataType: "json",
                                    success: function (result) {
                                        if (result != null) {
                                            switch (result.code) {
                                                case 200:
                                                    alert(result.message);
                                                    window.location.reload();
                                                    break;
                                                default:
                                                    alert(result.message);
                                                    window.location.reload();
                                                    break;
                                            }
                                        }
                                    }
                                });
                            } else {
                                alert("两次输入的密码不相同");
                                window.location.reload();
                            }
                        }
                    } else {
                        alert("密码不能为空");
                        window.location.reload();
                    }
                } else {
                    alert("密码不能为空");
                    window.location.reload();
                }
            } else {
                alert("密码不能为空");
                window.location.reload();
            }
            return false;
        });
    });
</script>

</body>
</html>
