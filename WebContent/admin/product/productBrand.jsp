<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<head>
<script type="text/javascript">
function onsubmitForm(me,url,typeid){
	var p=document.getElementsByName("ids");
	var path="";
	for(var i=0;i<p.length;i++){
		if(p[i].checked){
			path=path+"ids="+p[i].value+"&";
		}
	}
	me.href=url+"?"+path+"id="+typeid;
}

</script>
</head>
<form id="pagerForm" method="post" action="${requestScope.action }">
	<input type="hidden" name="pageNum" value="${requestScope.pageNum}" />
	<input type="hidden" name="numPerPage" value="${requestScope.numPerPage}" />
	<!-- 自定义变量 -->
	<input type="hidden" name="name" value="${requestScope.name}"/>
<%-- 	<input type="hidden" name="id" value="${requestScope.typeid }"/> --%>
<%-- 	<input type="hidden" name="account" value="${requestScope.account}"/> --%>
</form>
<s:debug></s:debug>
<div class="pageHeader">
<!-- 	<a href="productType" rel="productType">dd</a> -->
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="addPage?code=1&id=${requestScope.typeid }" target="dialog"><span>添加</span></a></li>
			<li><a class="edit" href="updatePage?code=1&brandid={sid_brand}&id=${requestScope.typeid }" target="dialog"><span>修改</span></a></li>
			<li><a class="delete" href="delBrand?brandid={sid_brand}&id=${requestScope.typeid }" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="icon" href="#" target="navTab" onclick="onsubmitForm(this,'targetBrand','${requestScope.typeid}')"><span>类型合并</span></a></li>
			<li class="line">line</li>
			<li>
			<div class="searchBar">
				<form action="brandSearch" method="post" onsubmit="return navTabSearch(this)">
				<table class="searchContent">
					<tr>
						<td>类型名称查询:<input type="text" name="name" alt="空格区分名称"></td>
						<input type="hidden" name="id" value="${requestScope.typeid }"/>
						<td><button class="button" type="submit">查询</button></td>
					</tr>
					
				</table>
				</form>
			</div>
			</li>
			
			
		</ul>
	</div>
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th>选择</th>
				<th>编号</th>
				<th>商品类型名称</th>
<!-- 				<th>用户权限</th> -->
<!-- 				<th>注册时间</th> -->
				<th>最后修改时间</th>
<!-- 				<th>最后登录时间</th> -->
			</tr>
		</thead>
		<tbody>
		<s:iterator var="brand" value="#request.list" status='st'> 
			<tr target="sid_brand" rel="${brand.id}">
				<td><input type="checkbox" value="${brand.id }" name="ids"/></td>
				<td>${st.index+1 }</td>
				<td>${brand.name}</td>
				<td>${brand.editTime }</td>
<%-- 				<td>${type.editTime }</td> --%>
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
