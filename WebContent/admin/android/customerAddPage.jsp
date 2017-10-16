<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%-- <jsp:include page="../../include.jsp" /> --%>
<script type="text/javascript">
function checkPassword(me){
	pass=$("#password").val();
	repass=$("#repassword").val();
	if(pass!=repass){
		alert("确认密码错误");
		return false;
	}
	else{
		return validateCallback(me, dialogAjaxDone);
	}
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
<div class="pageContent">
	<form action="customerSave" method="post"  onsubmit="return checkPassword(this)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>账号：</label>
				<input type="text" name="name" class="required" maxlength="12" />
			</div>
			<div class="unit">
				<label>密码：</label>
				<input type="password" name="password" class="required"  maxlength="16" id="password"/>
			</div>
			<div class="unit">
				<label>确认密码：</label>
				<input type="password" name="repassword" class="required"  maxlength="16" id="repassword"/>
			</div>
			<div class="unit">
				<label>用户电话：</label>
				<input type="text" name="phone"/>
			</div>
			<div class="unit">
				<label>用户性别：</label>
				<select class="combox" name="sex">
					<option value="0">性别</option>
					<option value="1">男</option>
					<option value="2">女</option>
				</select>
			</div>
			<div class="unit">
				<label>用户学历：</label>
				<select class="combox" name="edu">
					<option value="0">学历</option>
					<option value="1">小学</option>
					<option value="2">初中</option>
					<option value="3">高中</option>
					<option value="4">大学或以上</option>
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