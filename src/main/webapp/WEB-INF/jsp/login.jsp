<%--
  Created by IntelliJ IDEA.
  User: c1376
  Date: 12/21/2020 021
  Time: 2:08:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录界面</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/md5.js"></script>
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <%--    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">--%>
</head>
<body>
<div class="wrap">
    <div class="loginForm">
        <form class="layui-form" action="">
            <div class="logoHead">
                <h2 style="margin-top: 15px">城市停车场预约管理系统</h2>
            </div>
            <div class="usernameWrapDiv">
                <div class="usernameLabel">
                    <label>用户名:</label>
                </div>
                <div class="usernameDiv">
                    <i class="layui-icon layui-icon-username adminIcon"></i>
                    <input id="username" class="layui-input adminInput" type="text" name="username" placeholder="输入用户名">
                </div>
            </div>
            <div class="usernameWrapDiv">
                <div class="usernameLabel">
                    <label>密码:</label>
                </div>
                <div class="passwordDiv">
                    <i class="layui-icon layui-icon-password adminIcon"></i>
                    <input id="password" class="layui-input adminInput" type="password" name="password"
                           placeholder="输入密码">
                </div>
            </div>
            <div class="usernameWrapDiv">
                <div class="submitDiv">
                    <input type="hidden" name="role" id="role" value="管理员"/>
                    <input id="loginBtn" class="submit layui-btn layui-btn-primary" type="button" value="登录" lay-submit
                           lay-filter="login"></input>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    //JavaScript代码区域
    layui.use(['form', 'element', 'layer'], function () {
        var element = layui.element;
        var layer = layui.layer;
        var form = layui.form;
        form.on('submit(login)', function (data) {
            var username = data.field.username;
            var password = data.field.password;
            var role = data.field.role;
            if (username.length > 0) {
                if (password.length > 0) {
                    var psd = stringtomd5(password);
                    $.ajax({
                        url: "${pageContext.request.contextPath}/api/user/login",
                        type: "post",
                        data: JSON.stringify({id: 0, username: username, password: psd, role: role, isLogin: 1}),
                        contentType: "application/json;charset=utf-8",
                        dataType: "json",
                        success: function (data) {
                            if (data != null) {
                                switch (data.code) {
                                    case 200:
                                        layer.msg("登录成功",{time: 4000})
                                        window.location.href = "${pageContext.request.contextPath}/api/jump/tomain";
                                        break;
                                    default:
                                        alert(data.message);
                                        break;
                                }
                            }
                        }
                    });
                } else {
                    alert("请输入密码");
                    window.location.reload();
                }
            } else {
                alert("请输入账号");
                window.location.reload();
            }
            return false;
        });
    });
</script>

</body>
</html>
