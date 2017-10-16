<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

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
			<li><a class="icon" href="combineBrand?brandid={sid_brand}&id=${requestScope.typeid }&${requestScope.bd }" target="ajaxTodo" title="确定合并?"><span>合并</span></a></li>
			

			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>

				<th>编号</th>
				<th>商品类型名称</th>
<!-- 				<th>用户权限</th> -->
<!-- 				<th>注册时间</th> -->


<!-- 				<th>最后登录时间</th> -->
			</tr>
		</thead>
		<tbody>
		
		<s:iterator var="brand" value="#request.list" status='st'> 
			<tr target="sid_brand" rel="${brand.id}">
			
				<td>${st.index+1 }</td>
				<td>${brand.name}</td>
			
			
			</tr>
		</s:iterator>
		
		</tbody>
	</table>
<!-- 	<div class="panelBar"> -->
<!-- 		<div class="pages"> -->
<%-- 			<span>显示</span> --%>
<%-- 			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})"> --%>
<%-- 				<s:bean name="org.apache.struts2.util.Counter"> --%>
<%-- 					<s:param name="first" value="5" /> --%>
<%-- 					<s:param name="last" value="20" /> --%>
<%-- 					<s:param name="interval" value="5" /> --%>
<%-- 					<s:iterator> --%>
<%-- 						<option value="<s:property/>" <s:if test="%{numPerPage==(current-5)}">selected="selected"</s:if> ><s:property/></option> --%>
<%-- 					</s:iterator> --%>
<%-- 				</s:bean> --%>
<%-- 			</select> --%>
<%-- 			<span>条，共${requestScope.totalCount}条</span> --%>
<!-- 		</div> -->
		
<%-- 		<div class="pagination" targetType="navTab" totalCount="${requestScope.totalCount}" numPerPage="${requestScope.numPerPage}" pageNumShown="10" currentPage="${requestScope.pageNum}"></div> --%>

<!-- 	</div> -->
</div>
