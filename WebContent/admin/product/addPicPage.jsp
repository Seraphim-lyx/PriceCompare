<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="pageContent">
	<form action="addPic?id=${requestScope.id }" method="post"  onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="text" name="webaddress" style="width:300px;float:left;" alt="输入商品图片链接"/><input value="提交" type="submit" style="margin-left:10px;">
	</form>
</div>
</body>
</html>