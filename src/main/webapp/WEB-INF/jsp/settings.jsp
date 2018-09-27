<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.2.1.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    <title>Sparrows ›设置</title>
</head>
<body>
<!-- 引入header文件 -->
<%@ include file="header.jsp" %>

<div style="width: 70%;margin:1% 2% 1% 5%;float: left;">
    <div class="panel panel-default" id="main" style="">
        <div class="panel-heading" style="background-color: white">
            <a href="/index">Sparrows</a> › 设置
        </div>
        <div class="panel-body form-horizontal">
            <div class="row">
                <div class="col-md-2">
                    <ul class="nav nav-pills nav-stacked">
                        <li class="active"><a href="#tab1" data-toggle="tab">修改信息</a></li>
                        <li><a href="#tab2" data-toggle="tab">修改头像</a></li>
                        <li><a href="#tab3" data-toggle="tab">修改密码</a></li>
                    </ul>
                </div>
                <div class="tab-content col-md-10">
                    <div class="tab-pane col-md-6 active" id="tab1">
                        <%--修改用户信息表单--%>
                        <form action="/user/update" enctype="multipart/form-data" method="post" class="form-horizontal"
                              role="form">
                            <div style="margin-left: 17%">
                                <div class="form-group">
                                    <div class="form-group">
                                        <label>手机号</label>
                                        <input name="phoneNum" type="text" class="form-control" value="${user.phoneNum}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="form-group">
                                        <label>Email</label>
                                        <input name="email" id="mail" type="text" class="form-control" value="${user.email}">
                               			<span id="mailerror" style="color:red"></span>
           
                                        <input name="id" type="hidden" class="form-control" value="${user.id}">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                	
                                    <input class="btn btn-default" type="submit" value="修改">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="tab-pane col-md-6" id="tab2">
                        <div style="margin-left: 17%">
                            <form action="/settings/avatar/update"
                                  enctype="multipart/form-data" method="post" class="form-horizontal" role="form">
                                <div class="form-group">
                                    <img width="200px" height="200px"
                                         src="${pageContext.request.contextPath}/static/${user.avatar}"
                                         class="img-rounded">
                                    <input type="file" name="avatar" accept="image/png,image/jpeg,image/jpg">
                                     <input name="id" type="hidden" class="form-control" value="${user.id}">
                                    <br/>
                                    <input class="btn btn-default" type="submit" value="上传头像">
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="tab-pane col-md-6" id="tab3">
                        <%--修改密码表单--%>
                            <div style="margin-left: 17%">
                                <div class="form-group">
                                    <label>旧密码</label>
                                    <input name="oldPassword" type="password" class="form-control" id="oldpass">
                                    <span id="error" style="color: red"></span>
                                </div>
                                <div class="form-group">
                                    <label>新密码</label>
                                    <input name="newPassword" type="password" class="form-control" id="newpass">
                                </div>
                                <div class="form-group">
                                    <label>确认密码</label>
                                    <input name="verifyPassword" type="password" class="form-control" id="newpass2">
                                    <input name="id" type="hidden" class="form-control" value="${user.id}" id="userid">
                                </div>
                                <div class="form-group">
                                    <input class="btn btn-default" type="submit" value="修改" id="submit">
                                </div>
                            </div>                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
		$(function () {
		    $("#tab3").on("click","#submit",doSubPassWord);
		    $("#tab1").on("blur","#mail",checkEmail);
		});
		function doSubPassWord(){
			if ($("#oldpass").val() == '') {
	            alert("请填写旧密码");
	            return;
	        } 
			if ($("#newpass").val() == '') {
	            alert("请填写新密码");
	            return;
	        } 
			if ($("#newpass2").val() == '') {
	            alert("请填写确认新密码");
	            return;
	        }
			if($("#newpass2").val()!=$("#newpass").val()){
				 alert("新密码和确认密码不一致，请重新填写");
		         return;
			}else {
	            var ifSubmit = confirm("确定修改密码吗?");
	            if (ifSubmit == false) {
	                return;
	            }
	        }
	        var url = "${pageContext.request.contextPath}/user/updatePassward";
	        var params = doGetParams();
			$.post(url,params,function(result){
				if(result.state == 0){ 
					//alert(result.message);
					console.log(result.message);
					$("#error").html(result.message);
					
	            }
	            if(result.state == 1){
	               // alert(result.message);
	            	window.location = "/settings";
	            }
			})
		}
		function doGetParams(){
			var params = {
					oldPassword: $("#oldpass").val(),
					newPassword: $("#newpass").val(),
					id:$("#userid").val(),
					verifyPassword: $("#newpass2").val()
		        }
		        return params;
		}
		
		//lxj加的邮箱格式验证
		function checkEmail(){
			 var str=$("#mail").val();
			 var re=/^[A-Za-z\d]+([-_.][A-za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
			 if (!re.test(str)) {
				 $("#mailerror").html("邮箱的格式不正确哦");
				//alert("邮箱的格式不正确哦");
			}
			 else{
				 $("#mailerror").html("邮箱格式没有问题");
			 }
		}
</script>
<!-- 引入侧边栏文件 -->
<%@ include file="side.jsp" %>

<!-- 引入footer文件 -->
<%@ include file="footer.jsp" %>

</body>
</html>