<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<form id="pagerForm" method="post" action="${requestScope.action }">
	<input type="hidden" name="pageNum" value="${requestScope.pageNum}" />
	<input type="hidden" name="numPerPage" value="${requestScope.numPerPage}" />
	<!-- 自定义变量 -->
<%-- 	<input type="hidden" name="roleid" value="${requestScope.roleid}"/> --%>
<%-- 	<input type="hidden" name="account" value="${requestScope.account}"/> --%>
	<input type="hidden" name="productname" value="${requestScope.productname }"/>
	<input type="hidden" name="website" value="${requestScope.website }"/>
	<input type="hidden" name="minprice" value="${requestScope.minprice }"/>
	<input type="hidden" name="maxprice" value="${requestScope.maxprice }"/>
	<input type="hidden" name="brandid" value="${requestScope.brandid }"/>
	<input type="hidden" name="typeid" value="${requestScope.typeid }"/>
	<input type="hidden" name="action" value="${requestScope.action }"/>
	<s:iterator var="pn" value="#request.productNames">
		<input type="hidden" name="productNames" value="${pn }"/>
	</s:iterator>
</form>
<div class="pageHeader">
	
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="addUser" target="dialog"><span>添加</span></a></li>
		
			<li><a class="edit" href="editProductPage?id={sid_product}" target="navTab"><span>修改</span></a></li>
			
			<li><a class="delete" href="productDelete?id={sid_product}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li>
				
				<form action="productTypeSearch" method="post" onsubmit="return navTabSearch(this)">
					<label>选择商品类型</label>
					<select id="typeSelect" name="typeid" >
						<option value=""></option>
						<s:iterator var="type" value="#request.typelist">
							<option value="${type.id }">${type.name }</option>
						</s:iterator>
					</select>
					<button type="submit">类型查询</button>
				</form>
					
			</li>
			<li class="line">line</li>
			<li>
			<div class="searchBar">
				<form action="productSearch" method="post" onsubmit="return navTabSearch(this)">
				<table class="searchContent">
					<tr>
						<td>名称查询:<input type="text" name="productname" alt="商品，品牌或类型名称"></td>
						<td><button class="button" type="submit">查询</button></td>
					</tr>
					
				</table>
				</form>
			</div>
			</li>
			<li class="line">line</li>

		</ul>
		<a href="productSearchPage" class="button" target="dialog"><span>条件搜索</span></a>
	</div>

</div>
	
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th>编号</th>
				<th>商品名称</th>
				<th>商品类型</th>
				<th>商品品牌</th>
				<th>商品来源</th>
				<th>商品价格</th>
				<th>详细信息</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator var="product" value="#request.list" status='st'> 
			<tr target="sid_product" rel="${product.id}">
				<td>${st.index+1 }</td>
				<td>${product.info.name }</td>
				<td>${product.brand.type.name }</td>
				<td>${product.brand.name }</td>
				<td>${product.info.website }</td>
				<td>${product.info.price }</td>
				<td><a class="button" href="productInfo?id=${product.id }" target="navTab"><span>信息</span></a></td>
				
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
