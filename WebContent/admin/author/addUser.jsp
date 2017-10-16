<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%-- <jsp:include page="../../include.jsp" /> --%>
<style>
input {
	width: 200px;
}
#r1,#r2{
	width:50px;
}
</style>
<div class="pageContent">
	<form action="register" method="post"  onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>角色：</label>
				<select name="roleid" class="combox">
					<s:iterator var="role" value="#request.roleList">
						<option value="${role.id}">${role.name }</option>
					</s:iterator>
				</select>
			</div>
			<div class="unit">
				<label>账号：</label>
				<input type="text" name="username" class="required" maxlength="12" />
			</div>
			<div class="unit">
				<label>密码：</label>
				<input type="password" name="password" class="required"  maxlength="16"/>
			</div>
			<div class="unit">
				<label>确认密码：</label>
				<input type="password" name="repassword" class="required"  maxlength="16"/>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>