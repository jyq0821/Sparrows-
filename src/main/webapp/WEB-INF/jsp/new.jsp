<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.2.1.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    <title>创作新主题 › Sparrows </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
    <style>
        div textarea image{
            width: 50%;
            height:50%;
        }
    </style>
</head>
<body>
<!-- 引入header文件 -->
<%@ include file="header.jsp" %>
<div style="width: 70%;margin:1% 2% 1% 5%;float: left;">
    <div class="panel panel-default" id="main" style="">
        <div class="panel-heading" style="background-color: white">
            <a href="${pageContext.request.contextPath}/index">Sparrows</a> › 创作新主题
        </div>
        <div class="panel-body">
            <div class="form-group">
                <label for="title">主题标题</label>
                <input type="text" class="form-control" id="title" name="title"
                       placeholder="请输入主题标题，如果标题能够表达完整内容，则正文可以为空" required="required">
            </div>
            <div class="form-group">
                   <textarea class="layui-textarea" id="content" name="content" style="display: none">
                   </textarea>
            </div>
            <div class="form-group">
                <div class="row">
                    <div class="col-sm-2">
                        <label for="tab">板块</label><br/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-10" style="width: 40%">
                        <select class="form-control" id="tab" name="tab">
                            <c:forEach items="${tabs}" var="tab">
                                <option value="${tab.id}">${tab.tabName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-2">
                        <button type="button" class="btn btn-default btn-sm" id="submitBut">发布主题</button>
                    </div>
                </div>
            </div>
        </div>

    </div>


</div>


<div class="panel panel-default" id="sidebar2" style="width: 20%;margin:1% 2% 1% 0%;float: right">
    <div class="panel-heading" style="background-color: white;text-align: center">
        发帖提示
    </div>
    <ul class="list-group" style="width: 100%">
        <li class="list-group-item">
            <h5>主题标题</h5>
            <p>
                请在标题中描述内容要点。如果一件事情在标题的长度内就已经可以说清楚，那就没有必要写正文了。
            </p>
        </li>

        <li class="list-group-item">
            <h5>正文</h5>
            <p>
                请清楚地表达所要说明的内容。
            </p>
        </li>
    </ul>
</div>


<div class="panel panel-default" id="sidebar1" style="width: 20%;margin:1% 2% 1% 0%;float: right">
    <div class="panel-heading" style="background-color: white;text-align: center">
        社区指导原则
    </div>
    <ul class="list-group" style="width: 100%">
        <li class="list-group-item">
            <h5>尊重原创</h5>
            <p>
                请不要发布任何盗版下载链接，包括软件、音乐、电影等等。Genesis是创意工作者的社区，我们尊重原创。
            </p>
        </li>

        <li class="list-group-item">
            <h5>友好互助</h5>
            <p>
                保持对陌生人的友善。用知识去帮助别人。
            </p>
        </li>
    </ul>
</div>


<!-- 引入footer文件 -->
<%@ include file="footer.jsp" %>

<script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>
<script>
    var layedit;
    var index;
    //构建文编编译器
    layui.use(['layedit','form','upload'], function () {
        layedit = layui.layedit,
              $ = layui.jquery;
        var up_url="${pageContext.request.contextPath}/upload/doUploadNew";//上传图片url
        layedit.set({
            uploadImage: {
                url:up_url //接口url
                ,type: 'post' //默认post
            }
        });
        //构建一个默认的编辑器
        index = layedit.build('content');
    });





    //为提交按钮绑定参数
    $(function () {
        $("#submitBut").click(sendMessage);
    });

    //异步提交文章
    function sendMessage() {
        if ($("#title").val() == '') {
            alert("请填写标题！");
            return;
        } else if (layedit.getContent(index) == "") {
            alert("请填写内容！");
            return;
        } else {
            var ifSubmit = confirm("确定发表该主题吗?");
            if (ifSubmit == false) {
                return;
            }
        }
        var url = "${pageContext.request.contextPath}/topic/add";
        var params = doGetParams();
        $.post(url, params, function (result) {
            if(result.state == 0){ //未登录
                alert(result.message);
                window.location = "/signin";
            }

            if(result.state == 1){ //发表失败
                alert(result.message);
            }

            if(result.state == 2){ //发表成功
                alert(result.message);
                window.location = "/index";
            }
        });
    }

    //获取参数数据
    function doGetParams() {
        //1.获取角色自身信息
        var params = {
            title: $("#title").val(),
            content: layedit.getContent(index),
            tab: $("#tab").val()
        }
        return params;
    }
</script>
</body>
</html>