<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="updateDescription" onsubmit="return validateCallback(this, dialogAjaxDone)">
<textarea class="editor" name="description" rows="15" cols="80">${requestScope.product.description.description }</textarea>
<input type="hidden" name="descriptionid" value="${requestScope.product.description.id }"/>

	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent" style="margin-right:50px;">
						<button type="submit">保存</button>
					</div>
				</div>
			</li>
		</ul>
		
	</div>
</form>
</body>
</html>