<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<style>
input {
	width: 200px;
}
#r1,#r2{
	width:50px;
}
</style>
<script type="text/javascript">
	function changeRole(){
		var role=document.getElementsByName("role");
		var sel=document.getElementById("roleid");
		$(".role").val(sel.options[sel.selectedIndex].text);
	}
</script>
<div class="pageContent">
	<form action="doEditUser?userid=${user.id }" method="post"  onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>账号：</label>
				<input type="text" name="account" maxlength="12" value="${user.name}"/>
			</div>
			<div class="unit">
				<label>密码：</label>
				<input type="password" name="password" maxlength="16" value="${user.password }"/>
			</div>
			<div class="unit">
				<label>用户角色：</label>
				<input type="text" name="role" class="role" value="${user.role.name}" maxlength="12"/>
				<select name="roleid" id="roleid" onchange="changeRole()">
					<option value="${user.id }">请选择</option>
					<s:iterator var="role" value="#request.list">
						<option value="${role.id }">${role.name }</option>
					</s:iterator>
				</select>
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