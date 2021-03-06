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
<div class="pageContent">
	<form action="customerUpdate?id=${customer.id }" method="post"  onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>账号：</label>
				<input type="text" name="name" maxlength="12" value="${customer.name}" />
			</div>
			<div class="unit">
				<label>密码：</label>
				<input type="password" name="password" maxlength="16" value="${customer.password }"/>
			</div>
			<div class="unit">
				<label>用户电话：</label>
				<input type="text" name="phone" maxlength="16" value="${customer.phone }"/>
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
			<div class="unit">
				<label>用户状态：</label>
				<span></span>
				<select name='lock' class="combox">
					<option value="">状态</option>
					<option value="0">正常</option>
					<option value="1">锁定</option>
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