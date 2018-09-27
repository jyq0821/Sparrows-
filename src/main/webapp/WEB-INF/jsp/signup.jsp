<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>注册 - Sparrows</title>
<link
	href="${pageContext.request.contextPath}/static/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/static/js/jquery-3.2.1.js"></script>
<script
	src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
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
<body>
	<!-- 引入header文件 -->
	<%@ include file="header.jsp"%>
	<div style="padding-top: 6%;height: 600px;background-image: url(${pageContext.request.contextPath}/static/img/background/back02.jpg); background-repeat:repeat;" >

		<div class="panel panel-default" id="login"style="width: 36%; margin-left: 60%;background-color: rgba(55,194,249,0.3);border-color:rgba(55,194,249,0.3) ">
			<div >
			<div class="panel-heading" style=""align="center">
				<h3 class="panel-title">注册</h3>
			</div>
			<div class="panel-body">
				<div id="zcform" class="form-horizontal" role="form"
					style="margin-left: 4%">
					<div class="form-group">
						<label class="col-sm-3 control-label">用户名</label>
						<div class="col-sm-8" style="width: 70%;">
							<input type="text" class="form-control" id="username"
								name="username" required="required"
								style="display: inline-block;">
							<p class="form-control-static">请使用半角的 a-z 或数字 0-9</p>
							<p id="usererror" margin-top:50%></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">密码</label>
						<div class="col-sm-8" style="width: 70%;">
							<input type="password" class="form-control" id="password"
								name="password" required="required">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">电子邮件</label>
						<div class="col-sm-8" style="width: 70%;">
							<input type="email" class="form-control" id="email" name="email"
								required="required">
						</div>
					</div>
					<div style="margin-left:40%"><p id="mailerror" margin-top:50%; margin-left:10px></p></div>
					<div class="form-group">
						<label class="col-sm-3 control-label">区号</label>
						<div class="col-sm-8" style="width: 70%;">
							<select class="form-control" id="areaCode" name="areaCode">
								<option value="86">+86</option>
								<option value="85">+85</option>
								<option value="84">+84</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">手机号</label>
						<div class="col-sm-8" style="width: 70%;">
							<input type="tel" class="form-control" id="tel" name="tel"
								required="required">
						</div>
					</div>
					<input type="submit" class="btn btn-default" id="signup"
						style="margin-left: 38%">
				</div>
			</div>
			</div>
		</div>
	</div>
	<!-- 引入footer文件 -->
	<%@ include file="footer.jsp"%>
</body>
<script>
	$(function() {
		$("#zcform").on("blur", "#username", checkName);
		$("#zcform").on("blur","#email",checkEmail);
		$("#zcform").on("click", "#signup", doSignupSubmit);
	})
	function doSignupSubmit() {
		console.log("xaaa")
		if ($("#username").val().trim()== '') {
			alert("请填写用户名");
			return;
		}
		if ($("#password").val() == '') {
			alert("请填写密码");
			return;
		}
		if ($("#email").val() == '') {
			alert("请填写邮箱");
			return;
		}
		if ($("#tel").val() == '') {
			alert("请填写手机号");
			return;
		}
		console.log("jkjkjk1");
		console.log($("#usererror").html());
		debugger;
		if ($("#usererror").html() == "用户名重复") {
			alert("用户名重复");
			console.log("jkjkjk");
			return;
		}
		if($("#mailerror").html()=="邮箱格式通过验证"){
			//alert("邮箱格式验证不通过，请重新填写");
			console.log("Emailerror");
			
		}
		var url = "${pageContext.request.contextPath}/user/add/do";
		var params = doGetParams();
		$.post(url, params, function(result) {
			if (result.state == 0) {
				alert(result.message);
				//$("#error").html(result.message);
			}
			if (result.state == 1) {
				alert(result.message);
				window.location = "/signin";
			}
		})

	}
	function doGetParams() {
		var params = {
			username : $("#username").val(),
			password : $("#password").val(),
			email : $("#email").val(),
			phoneNum : $("#areaCode").val() + $("#tel").val(),
		}
		return params;
	}

	function checkName() {
		var params = {
			username : $("#username").val()
		}
		if($("#username").val().trim()==""){
			console.log($("#username").val())
			return;
		}
		var url = "/user/checkUserName";
		$.post(url, params, function(result) {
			if (result.state == 1) {
				$("#usererror").html(result.data);
				$("#usererror").css("color","green");
			} else {
				$("#usererror").html(result.message);
				$("#usererror").css("color","red");
			}
		})
	}
	function checkEmail(){
		 var str=$("#email").val();
		 console.log(str)
		 var re=/^[\w-]+@[\w-]+(\.[\w-]+)+$/;
		 if (!re.test(str)) {
			 $("#mailerror").html("邮箱的格式不正确哦");
			 $("#mailerror").css("color","red");
		}
		 else{
			 $("#mailerror").html("邮箱格式通过验证");
			 $("#mailerror").css("color","green");
		 }
	}
</script>
</html>