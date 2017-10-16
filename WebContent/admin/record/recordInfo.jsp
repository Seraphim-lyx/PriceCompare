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
<div class="pageContent" style="height:100%;">
<div style="width:100%;"><div style="font-size:40px;text-align:center;">${requestScope.record.title }</div></div>
<div style="width:100%;">
<div style="width:100%;margin-top:15px;"><span style="font-size:15px;">编写人:${requestScope.record.user.role.name }-${requestScope.record.user.name }</span></div>
<div style="width:100%;margin-top:15px;"><span style="font-size:15px;">修改日期:${requestScope.record.editTime }</span></div>
</div>
<div style="margin-top:20px;">

${requestScope.record.text }

</div>
</div>
</body>
</html>