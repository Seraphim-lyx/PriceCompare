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

<div class="pageHeader">
<div class="panelBar">
		<ul class="toolBar">
			<li>
			<div class="searchBar">
				<form action="androidProductSearch" method="post" onsubmit="return navTabSearch(this)">
				<table class="searchContent">
					<tr>
						<td>名称查询:<input type="text" name="productname"></td>
						<td><button class="button" type="submit">查询</button></td>
					</tr>
					
				</table>
				</form>
			</div>
			</li>
			<li class="line">line</li>
			<a href="androidPushOrder" class="icon" target="navTab"><span>当月人气商品</span></a>
			<li class="line">line</li>
			<a href="androidPushPointed" class="icon" target="navTab"><span>重点关注商品</span></a>
			<li class="line">line</li>
			
			
			
		</ul>
		<a href="androidSearchPage" class="button" target="dialog"><span>条件搜索</span></a>
	</div>
	
</div>
<div class="pageContent">
	
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th>编号</th>
				<th>推送商品名称</th>
				<th>商品类型</th>
				<th>商品价格</th>
				<th>商品详情</th>
				<th>商品移除</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator var="push" value="#request.list" status='st'> 
			<tr target="sid_product" rel="${push.id}">
				<td>${st.index+1 }</td>
				<td>${push.product.info.name }</td>
				<td>${push.product.brand.name }</td>
				<td>${push.product.info.price }</td>
				<td><a class="button" href="AndroidProductData?productid=${push.product.id }" target="dialog" width="800" height="480"><span>商品详情</span></a></td>
				<td><a class="button" href="removePush?id=${push.id }" target="ajaxTodo"><span>移除</span></a></td>
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
