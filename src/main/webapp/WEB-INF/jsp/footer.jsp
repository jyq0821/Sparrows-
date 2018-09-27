<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <style>
        li {list-style-type:none;}
        html, body {
            height: 100%;
            font-size: 14px;
            color: #525252;
            font-family: NotoSansHans-Regular,AvenirNext-Regular,arial,Hiragino Sans GB,"Microsoft Yahei","Hiragino Sans GB","WenQuanYi Micro Hei",sans-serif;
            background: #f0f2f5;
        }
        .footer {
            background-color: #fff;
            width: 100%;
            padding-top:1%;
            color: #8A8A8A;
            display: block;
            height: 150px;
            border: 1px ;
            clear:both
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
a{
    color: #8A8A8A;
    cursor: pointer;
}
    </style>
</head>
<body>
<footer class="footer">
    <div class="container">
         本博客由1806-15-1组开发，致力于解决用户博客的发布浏览，信息的保存。
        <br/><br/><br/>
        <p>Designed by </span> Sparrows Team</p>
    </div>
    <div class="info">
        <p style="text-align: center;font-size: 16px;">统计信息</p>
        <hr>
        <p style="text-align: center;font-size: 16px;">会员数: ${usersNum}</p>
        <p style="text-align: center;font-size: 16px;">话题数: ${topicsNum}</p>
    </div>
    <div class="info">
        <p style="text-align: center;font-size: 16px;">友情链接</p>
        <hr>
        <p style="text-align: center;font-size: 16px;"><a href="http://www.tmooc.cn/">达内教育</a></p>
    </div>
    <div class="info" >
        <p style="text-align: center;font-size: 16px;">其他信息</p>
        <hr>
        <p style="text-align: center;font-size: 16px;"><a href="#">关于本站</a></p>
        <p style="text-align: center;font-size: 16px;"><a href="#">联系我们</a></p>
    </div>
</footer>
</body>
</html>