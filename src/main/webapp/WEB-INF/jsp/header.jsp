<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <div class="row" style="background-color: #4a374a;height: 60px">
        <div class="col-md-1" style="margin-left:4%">
         <a  href="${pageContext.request.contextPath}/index">
            <img src="${pageContext.request.contextPath}/static/img/logo.png" style="height: 40px; margin-top: 10px">
     	</a>
        </div>
        <div class="col-md-3"">
            <img src="${pageContext.request.contextPath}/static/img/logo_2.png" style="height: 56px; margin-top: 2px">
        </div>
        <div class="col-md-3 col-md-push-4"> <%--"--%>
            <c:if test="${empty userId}">
                <!--未登陆-->
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="${pageContext.request.contextPath}/signin" TITLE='登录'>
                            <span style="color:#d2d2d2;font-size: 24px" class="glyphicon glyphicon-log-in"></span>
                        </a>
                    </li>
                    <li>
                        <p class="navbar-text"><a style="color:#d2d2d2;font-size: 20px"
                                                  href="${pageContext.request.contextPath}/signup">注册</a></p>
                    </li>
                </ul>
            </c:if>
            <c:if test="${!empty userId}">
                <!--已登陆-->
                <ul class="nav navbar-nav" style="">
                    <li>
                        <a href="${pageContext.request.contextPath}/index" TITLE="首页">
                            <span style="color:#d2d2d2;font-size: 24px" class=" glyphicon glyphicon-home"></span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/member/${sessionScope.username}"
                           TITLE="用户">
                            <span style="color:#d2d2d2;font-size: 24px" class="glyphicon glyphicon-user"></span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/settings" TITLE="设置">
                            <span style="color:#d2d2d2;font-size: 24px" class="glyphicon glyphicon-cog"></span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/signout" TITLE="退出">
                            <span style="color:#d2d2d2;font-size: 24px" class="glyphicon glyphicon-log-out"></span>
                        </a>
                    </li>
                </ul>
            </c:if>
        </div>
    </div>
    <div class="row">
        <div class="row"  style="border-bottom: 1px solid #c2c2c2;">
            <div class="container-fluid" style="margin-left: 10%">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/index">麻雀</a>
                </div>
                <div id="ul_li">
                    <!--向左对齐-->
                    <ul  class="nav navbar-nav nav-pills navbar-left">
                    
                    
                        <li <c:if test="${tabId ==1}">class="active"</c:if>><a href="${pageContext.request.contextPath}/index/?tabId=1&pageCurrent=1">技术</a></li>
                        <li <c:if test="${tabId ==2}">class="active"</c:if>><a href="${pageContext.request.contextPath}/index/?tabId=2&pageCurrent=1">好玩</a></li>
                        <li <c:if test="${tabId ==3}">class="active"</c:if>><a href="${pageContext.request.contextPath}/index/?tabId=3&pageCurrent=1">创意</a></li>
                        <li <c:if test="${tabId ==4}">class="active"</c:if>><a href="${pageContext.request.contextPath}/index/?tabId=4&pageCurrent=1">工作</a></li>
                        <li <c:if test="${tabId ==5}">class="active"</c:if>><a href="${pageContext.request.contextPath}/index/?tabId=5&pageCurrent=1">交易</a></li>

                    </ul>
                </div>
            </div>
        </div>
    </div>
</header>
<script>
    function signout_confirm() {
        var r = confirm("确定退出?")
        console.log(r)
        if (r == true) {
            debugger
            console.log("xxx");
            window.location = "/signout";
        }
        else {

        }
    }
</script>