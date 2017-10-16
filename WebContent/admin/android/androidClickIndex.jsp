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
	<div class="panelBar">
		<div>
		<form method="post" action="androidClickPriceOrder?order=0" onsubmit="return navTabSearch(this)">
			<input type="hidden" name="pageNum" value="${requestScope.pageNum}" />
		<input type="hidden" name="numPerPage" value="${requestScope.numPerPage}" />
			<button type="submit" class="button"><span>按价格升序</span></button>
		</form>
		</div>
		<div>
		<form method="post" action="androidClickPriceOrder?order=1" onsubmit="return navTabSearch(this)">
			<input type="hidden" name="pageNum" value="${requestScope.pageNum}" />
			<input type="hidden" name="numPerPage" value="${requestScope.numPerPage}" />
			<button type="submit" class="button"><span>按价格降序</span></button>
		</form>
		</div>
		<div>
		<form method="post" action="androidClickCountOrder?order=0" onsubmit="return navTabSearch(this)">
			<input type="hidden" name="pageNum" value="${requestScope.pageNum}" />
			<input type="hidden" name="numPerPage" value="${requestScope.numPerPage}" />
			<button type="submit" class="button"><span>按点击量升序</span></button>
		</form>
		</div>
		<div>
		<form method="post" action="androidClickCountOrder?order=1" onsubmit="return navTabSearch(this)">
			<input type="hidden" name="pageNum" value="${requestScope.pageNum}" />
			<input type="hidden" name="numPerPage" value="${requestScope.numPerPage}" />
			<button type="submit" class="button"><span>按点击量降序</span></button>
		</form>
		</div>
		
	</div>
</div>
<div class="pageContent">
	<form method="post" action="androidPushChoose" onsubmit="return validateCallback(this, navTabAjaxDone)" name="chooseForm">
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th>商品选择</th>
				<th>商品名称</th>
				<th>商品类型</th>
				<th>商品价格</th>
				<th>商品月点击量</th>
				<th>商品详情</th>
<!-- 				<th>商品移除</th> -->
			</tr>
		</thead>
		<tbody>
		<s:iterator var="p" value="#request.list" status='st'> 
			<tr target="sid_product">
				<td><input type="checkbox" name="productsid" value="${p.yearclick.product.id }"/></td>
				<td>${p.yearclick.product.info.name }</td>
				<td>${p.yearclick.product.brand.name }</td>
				<td>${p.yearclick.product.info.price }</td>
				<td>${p.count }</td>
				<td><a class="button" href="AndroidProductData?productid=${p.yearclick.product.id }" target="dialog" width="800" height="480"><span>商品详情</span></a></td>
<%-- 				<td><a class="button" href="removePush?id=${p.id }" target="ajaxTodo"><span>移除</span></a></td> --%>
			</tr>
		</s:iterator>
		</tbody>
	</table>
	<div class="panelBar">
		<button type="submit">提交</a>
	</div>
	</form>
	
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
