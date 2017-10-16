<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<head>
<script type="text/javascript">
function postRole(){
	
}

</script>
</head>
<form id="pagerForm" method="post" action="userList">
	<input type="hidden" name="pageNum" value="${requestScope.pageNum}" />
	<input type="hidden" name="numPerPage" value="${requestScope.numPerPage}" />
	<!-- 自定义变量 -->
<%-- 	<input type="hidden" name="roleid" value="${requestScope.roleid}"/> --%>
<%-- 	<input type="hidden" name="account" value="${requestScope.account}"/> --%>
</form>
<s:debug></s:debug>
<div class="pageHeader">
	
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="addUser" target="dialog"><span>添加</span></a></li>
			<li><a class="delete" href="deleteUser?userid={sid_user}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="editUser?userid={sid_user}" target="dialog"><span>修改</span></a></li>
			<li class="line">line</li>
			<li>
			<form method="post" action="roleSearch" onsubmit="return navTabSearch(this)">
				
				<select name="roleid" style="float:left">
					<option value="">用户类型</option>
					<s:iterator var="role" value="#request.rolelist">
					<option value="${role.id }">
						${role.name }
					</option>
					</s:iterator>
				</select>
				<button type="submit" class="button">查询</button>
			</form>
			
				</li>
			<li class="line">line</li>
			
			<div class="searchBar">
				<form action="userSearch" method="post" onsubmit="return navTabSearch(this)">
				<table class="searchContent">
					<tr>
						<td>名称查询:<input type="text" name="username"></td>
						<td><button class="button" type="submit">查询</button></td>
					</tr>
					
				</table>
				</form>
			</div>
			
			
		</ul>
	</div>
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th>编号</th>
				<th>用户名称</th>
				<th>用户权限</th>
				<th>注册时间</th>
				<th>最后修改时间</th>
				<th>最后登录时间</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator var="user" value="#request.list" status='st'> 
			<tr target="sid_user" rel="${user.id}">
				<td>${st.index+1 }</td>
				<td>${user.name}</td>
				<td>${user.role.name }</td>
				<td>${user.regitTime }</td>
				<td>${user.editTime }</td>
				<td>${user.loginTime }</td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<s:bean name="org.apache.struts2.util.Counter">
					<s:param name="first" value="5" />
					<s:param name="last" value="20" />
					<s:param name="interval" value="5" />
					<s:iterator>
						<option value="<s:property/>" <s:if test="%{numPerPage==(current-5)}">selected="selected"</s:if> ><s:property/></option>
					</s:iterator>
				</s:bean>
			</select>
			<span>条，共${requestScope.totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${requestScope.totalCount}" numPerPage="${requestScope.numPerPage}" pageNumShown="10" currentPage="${requestScope.pageNum}"></div>

	</div>
</div>
