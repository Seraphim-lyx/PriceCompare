<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<form id="pagerForm" method="post" action="${requestScope.action }">
	<input type="hidden" name="pageNum" value="${requestScope.pageNum}" />
	<input type="hidden" name="numPerPage" value="${requestScope.numPerPage}" />
	<!-- 自定义变量 -->
	<input type="hidden" name="productname" value="${requestScope.productname }"/>
	<input type="hidden" name="order" value="${requestScope.order}"/>
	<input type="hidden" name="website" value="${requestScope.website }"/>
	<input type="hidden" name="minprice" value="${requestScope.minprice }"/>
	<input type="hidden" name="maxprice" value="${requestScope.maxprice }"/>
	<input type="hidden" name="brandid" value="${requestScope.brandid }"/>
	<input type="hidden" name="typeid" value="${requestScope.typeid }"/>
	<input type="hidden" name="action" value="${requestScope.action }"/>
	<s:iterator var="pn" value="#request.productNames">
		<input type="hidden" name="productNames" value="${pn }"/>
	</s:iterator>
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
		<s:if test="#token=='android'">
		<div style="float:left;">
		<form method="post" action="${requestScope.androidOrder }?order=0" onsubmit="return navTabSearch(this)" >
			<input type="hidden" name="productname" value="${requestScope.productname }"/>
			<button type="submit" class="button"><span>按价格升序</span></button>
		</form>
		</div>
		<div style="float:left;">
		<form method="post" action="${requestScope.androidOrder }?order=1" onsubmit="return navTabSearch(this)">
			<input type="hidden" name="productname" value="${requestScope.productname }"/>
			<button type="submit" class="button"><span>按价格降序</span></button>
		</form>
<!-- 		<form method="post" action="androidTypeOrder?order=aesc&target=" onsubmit="return navTabSearch(this)"> -->
<%-- 			<button type="submit" class="button"><span>按类型升序</span></button> --%>
<!-- 		</form> -->
<!-- 		<form method="post" action="androidTypeOrder?order=desc&target=" onsubmit="return navTabSearch(this)"> -->
<%-- 			<button type="submit" class="button"><span>按类型降序</span></button> --%>
<!-- 		</form> -->
		</div>
		</s:if>
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
				<th>商品详情</th>
				<th>商品备注</th>
<!-- 				<th>商品移除</th> -->
			</tr>
		</thead>
		<tbody>
		<s:iterator var="p" value="#request.list" status='st'> 
			<tr target="sid_product" rel="${p.id}">
				<td><input type="checkbox" name="productsid" value="${p.id }"/></td>
				<td>${p.info.name }</td>
				<td>${p.brand.name }</td>
				<td>${p.info.price }</td>
				<td><a class="button" href="AndroidProductData?productid=${p.id }" target="dialog" width="800" height="480"><span>商品详情</span></a></td>
				<td><a class="button" href="androidProductMessage?productid=${p.id }" target="dialog"><span>商品备注</span></a></td>
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
