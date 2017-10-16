<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%-- <jsp:include page="../../include.jsp" /> --%>
<html>
<head>
<script type="text/javascript">
function addInput(){
	
	$("#pageContent").append("<div class='unit'>");
	$("#pageContent").append("<label>名称：</label>");
	$("#pageContent").append("<input type='text' name='name'/>");
	$("#pageContent").append("</div>");
}


</script>
<style>
input {
	width: 200px;
}
#r1,#r2{
	width:50px;
}
</style>
</head>
<body>
<div class="pageContent">
	<form action="${requestScope.action }" method="post"  onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="58" id="pageContent">
			<div class="unit">
				<label>名称：</label>
				<input type="text" name="name" class="required" />
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="addbutton"><div class="buttonContent"><button type="button" id="add" onclick="addInput()">添加</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
</body>
</html>