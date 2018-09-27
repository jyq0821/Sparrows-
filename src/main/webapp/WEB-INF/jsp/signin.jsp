<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>登录 - Sparrows</title>
<link
	href="${pageContext.request.contextPath}/static/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/static/js/jquery-3.2.1.js"></script>
<script
	src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/js.cookie.js"></script>

<style>
li {
	list-style-type: none;
}

html, body {
	height: 100%;
	font-size: 14px;
	color: #525252;
	font-family: NotoSansHans-Regular, AvenirNext-Regular, arial,
		Hiragino Sans GB, "Microsoft Yahei", "Hiragino Sans GB",
		"WenQuanYi Micro Hei", sans-serif;
	background: #f0f2f5;
}

.footer {
	background-color: #fff;
	margin-top: 22px;
	margin-bottom: 22px;
	width: 100%;
	padding-top: 22px;
	color: #8A8A8A;
	display: block;
	height: 200px;
	border: 1px;
	clear: both
}

.container {
	margin-right: 5%;
	margin-left: 5%;
	padding-left: 15px;
	padding-right: 15px;
	width: 40%;
	float: left;
}

.info {
	margin-right: 5%;
	width: 10%;
	float: left;
}

a {
	color: #8A8A8A;
	cursor: pointer;
}
</style>
</head>
<body
	style="">
	<!-- 引入header文件 -->
	<%@ include file="header.jsp"%>
	<div style="padding-top: 1%;height: 500px;background-image: url(${pageContext.request.contextPath}/static/img/background/back03.jpg); background-repeat:repeat;" >
	<div class="row">
		<div class="col-md-7 col-md-offset-1" style="margin-top:6%" >
		 <div data-ride="carousel" class="carousel slide" id="carouselcontainer">
        <!-- 图片容器 -->
        <div class="carousel-inner" >
            <div class="item"><img alt="第一张图" calss="img-responsive center-block" src="${pageContext.request.contextPath}/static/img/background/tedu1.jpg" /></div>
            <div class="item"><img alt="第二张图" src="${pageContext.request.contextPath}/static/img/background/tedu2.jpg" /> </div>
            <div class="item active"><img alt="第三张图" src="${pageContext.request.contextPath}/static/img/background/tedu3.jpg" /> </div>
        </div>
        <!-- 圆圈指示符 -->
        <ol class="carousel-indicators">
            <li data-slide-to="0" data-target="#carousel-container"></li>
            <li data-slide-to="1" data-target="#carousel-container"></li>
            <li data-slide-to="2" data-target="#carouselcontainer"
                class="active"></li>
        </ol>
        <!-- 左右控制按钮 -->
        <a data-slide="prev" href="#carouselcontainer"
           class="left carousel-control">
            <span class="glyphicon glyphicon-chevron-left"></span>
        </a>
        <a data-slide="next" href="#carouselcontainer"
           class="right carousel-control">
            <span class="glyphicon glyphicon-chevron-right"></span>
        </a>
    </div>
		<!-- <img src="" style="margin-top:46px"> -->
		</div>
		<div class="panel panel-default col-md-4" id="login"
			style="width: 20%; margin-left: 20px; margin-top: 5%; margin-bottom: 5%;background-color: rgba(55,194,249,0.3);border-color:rgba(55,194,249,0.3)">
			<div class="panel-heading" align="center" style="background-color: rgba(55,194,249,0.3);border-color:rgba(55,194,249,0.3)">
				<h3 class="panel-title">登录</h3>
			</div>
			<div class="panel-body"">

				<div class="form-group">
					<label for="username">用户名</label> <input type="text"
						class="form-control" id="username" name="username"
						placeholder="请输入用户名" required="required">
				</div>
				<div class="form-group">
					<label for="password">密码</label> <input type="password"
						class="form-control" id="passwd" name="password"
						placeholder="请输入密码" required="required">
				</div>
				<div class="checkbox text-left">
					<label> <input type="checkbox" id="remember">记住密码</label> 
					<!-- <a style="margin-left: 30%" href="#">忘记密码?</a> -->
				</div>
				<p style="text-align: right; color: red; position: absolute"
					id="info"></p>
				<br />
				<button id="loginButton" class="btn btn-success btn-block">登录</button>
				</input>

			</div>
		</div>
	</div>
	</div>
	<script>
		$("#id").keyup(function() {
			if (isNaN($("#id").val())) {
				$("#info").text("提示:账号只能为数字");
			} else {
				$("#info").text("");
			}
		})
		// 记住登录信息
		function rememberLogin(username, password, checked) {
			Cookies.set('loginStatus', {
				username : username,
				password : password,
				remember : checked
			}, {
				expires : 30,
				path : ''
			})
		}
		// 若选择记住登录信息，则进入页面时设置登录信息
		function setLoginStatus() {
			var loginStatusText = Cookies.get('loginStatus')
			if (loginStatusText) {
				var loginStatus
				try {
					loginStatus = JSON.parse(loginStatusText);
					$('#username').val(loginStatus.username);
					$('#passwd').val(loginStatus.password);
					$("#remember").prop('checked', true);
				} catch (__) {
				}
			}
		}
		// 设置登录信息
		setLoginStatus();
		$("#loginButton")
				.click(
						function() {
							var id = $("#username").val();
							var passwd = $("#passwd").val();
							var remember = $("#remember").prop('checked');
							if (id == '' && passwd == '') {
								$("#info").text("提示:账号和密码不能为空");
							} else if (id == '') {
								$("#info").text("提示:账号不能为空");
							} else if (passwd == '') {
								$("#info").text("提示:密码不能为空");
							} else {
								$
										.ajax({
											type : "POST",
											url : "${pageContext.request.contextPath}/api/loginCheck2",
											data : {
												username : id,
												password : passwd
											},
											dataType : "json",
											success : function(data) {
												if (data.state == 1) {
													if (remember) {
														rememberLogin(id,
																passwd,
																remember);
													} else {
														Cookies
																.remove('loginStatus');
													}
													$("#info").text(
															"提示:登陆成功，跳转中...");
													window.location.href = "${pageContext.request.contextPath}/index";
												} else {
													$("#info").text(
															data.message);
												}
											}
										});
							}
						})
	</script>
	<!-- 引入footer文件 -->
	<%@ include file="footer.jsp"%>
</body>
</html>