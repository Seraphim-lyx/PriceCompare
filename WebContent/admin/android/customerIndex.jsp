<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<head>
<script type="text/javascript">
function postRole(){
	
}

</script>
</head>
<form id="pagerForm" method="post" action="${requestScope.action }">
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
			<li><a class="add" href="customerAddPage" target="dialog"><span>添加</span></a></li>
			<li><a class="delete" href="customerDelete?id={sid_customer}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="customerUpdatePage?id={sid_customer}" target="dialog"><span>修改</span></a></li>
			<li class="line">line</li>
			
			<div class="searchBar">
				<form action="customerSearch" method="post" onsubmit="return navTabSearch(this)">
				<table class="searchContent">
					<tr>
						<td>名称查询:<input type="text" name="name"></td>
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
				<th>客户名称</th>
				<th>用户号码</th>
				<th>用户性别</th>
				<th>用户学历</th>
				<th>用户关键字</th>
				<th>用户关注商品</th>
				<th>最后登录时间</th>
				<th>用户状态</th>
				<th>用户操作</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator var="user" value="#request.list" status='st'> 
			<tr target="sid_customer" rel="${user.id}">
					<td>${st.index+1 }</td>
				<td>${user.name}</td>
				<td>${user.phone }</td>
				<s:if test="#user.sex==1">
				<td>男</td>
				</s:if>
				<s:else>
				<td>女</td>
				</s:else>
				<s:if test="#user.education==1">
				<td>小学</td>
				</s:if>
				<s:elseif test="#user.education==2">
				<td>初中</td>
				</s:elseif>
				<s:elseif test="#user.education==3">
				<td>高中</td>
				</s:elseif>
				<s:elseif test="#user.education==4">
				<td>大学或以上</td>
				</s:elseif>
				<td><a href="customerKeyword?id=${user.id}" class="button" target="dialog"><span>点击查看</span></a></td>
				<td><a href="customerProduct?id=${user.id }" class="button" target="dialog" width="800" height="480"><span>点击查看</span></a></td>
				<td>${user.editTime }</td>
				<s:if test="#user.locked==0">
				<td><span style="color:green">正常</span></td>
				<td><a href="customerLock?lock=1&id=${user.id }" class="button" target="ajaxTodo"><span>封禁</span></a></td>
				</s:if>
				<s:else>
				<td><span style="color:red">封锁</span></td>
				<td><a href="customerLock?lock=0&id=${user.id }" class="button" target="ajaxTodo"><span>解禁</span></a></td>
				</s:else>
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
