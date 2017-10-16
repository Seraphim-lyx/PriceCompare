<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<form id="pagerForm" method="post" action="${requestScope.action }">
	<input type="hidden" name="pageNum" value="${requestScope.pageNum}" />
	<input type="hidden" name="numPerPage" value="${requestScope.numPerPage}" />
	<!-- 自定义变量 -->
	<input type="hidden" name="search" value="${requestScope.search}" />

</form>
<div class="pageHeader">
	
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="addRecordPage" target="dialog" width="600" height="480"><span>添加</span></a></li>
		
			<li><a class="edit" href="editRecordPage?id={sid_record}" target="navTab" rel="editRecordPage" height="480"><span>修改</span></a></li>
			
			<li><a class="delete" href="deleteRecord?id={sid_record}" target="ajaxTodo" title="确定要删除吗?" ><span>删除</span></a></li>
			<li class="line">line</li>
			<li>
			<div class="searchBar">
				<form action="recordSearch" method="post" onsubmit="return navTabSearch(this)">
				<table class="searchContent">
					<tr>
						<td>名称查询:<input type="text" name="search" alt="作者名称，类型或者标题"></td>
						<td><button class="button" type="submit">查询</button></td>
					</tr>
					
				</table>
				</form>
			</div>
			</li>
			<li class="line">line</li>

		</ul>
	</div>

</div>
	
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th>编号</th>
				<th>日志作者</th>
				<th>作者权限</th>
				<th>日志类型</th>
				<th>日志标题</th>
				<th>修改时间</th>
				<th>查看日志</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator var="record" value="#request.list" status='st'> 
			<tr target="sid_record" rel="${record.id}">
				<td>${st.index+1 }</td>
				<td>${record.user.name }</td>
				<td>${record.user.role.name }</td>
				<td>${record.type }</td>
				<td>${record.title }</td>
				<td>${record.editTime }</td>
				<td><a class="button" href="recordInfo?id=${record.id }" target="dialog"  width="500" height="480"><span>查看日志</span></a></td>
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
