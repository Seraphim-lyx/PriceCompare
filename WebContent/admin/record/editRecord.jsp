<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="editRecord?id=${requestScope.record.id }" onsubmit="return validateCallback(this, navTabAjaxDone)">
<div style="margin-left:20%;margin-top:10px;"><span style="font-size:25px;">标题:</span><input style="font-size:20px;" type="text" name="title" value="${requestScope.record.title }"/>
				<span style="margin-left:10px;"><select name="type">
					<option value="${requestScope.record.type }">选择类型</option>
					<option value="工作日志">工作记录</option>
					<option value="问题反馈">问题反馈</option>
					<option value="研究报告">研究报告</option>
					<option value="其他">其他</option>
				</select></span>
</div>
<div style="margin-top:40px;">

<textarea class="editor" name="text" style="width:100%;height:300px;">${requestScope.record.text }</textarea>

</div>
<div style="width:100%"><input type="submit" value="提交"/></div>
</form>
</body>
</html>