<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

function onsubmitForm(me,url){
	var p=document.getElementsByName("productid");
	var path="";
	for(var i=0;i<p.length;i++){
		if(p[i].checked){
			path=path+"productid="+p[i].value+"&";
		}
	}
	me.href=url+"?"+path;
}

</script>
</head>
<!-- <a href="productIndex" target="navTab" rel="productIndex">123</a> -->
<body>
<form id="pagerForm" method="post" action="${requestScope.action }">
	<input type="hidden" name="pageNum" value="${requestScope.pageNum}" />
	<input type="hidden" name="numPerPage" value="${requestScope.numPerPage}" />
	<!-- 自定义变量 -->
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
	<div class="searchBar">	
		
		<form action="dataSearch" method="post" onsubmit="return navTabSearch(this)">
			<table class="searchContent">
				<tr>
					<td>商品搜索<td>
					<td><input type="text" name="productname"/></td>
					<td><button class="button" type="submit">搜索</button></td>
					<td><a href="dataSearchPage" class="button" target="dialog"><span>条件搜索</span></a></td>
				</tr>		
			
			</table>
		</form>
		
</div>	
	<div class="panelBar">
		<ul class="toolBar">
				<li><a class="add" href="#"  target="navTab" onclick="onsubmitForm(this,'yearData')"><span>年度数据</span></a></li>
				<li><a class="edit" href="#" target="navTab" onclick="onsubmitForm(this,'seasonData')"><span>季度数据</span></a></li>
				<li><a class="delete" href="#" target="navTab" onclick="onsubmitForm(this,'monthData')"><span>月度数据</span></a></li>
				<li class="line"></li>
				<li>
					<form method="post" action="dataPointedList" onsubmit="return navTabSearch(this)">
						<button class="button" type="submit">已关注商品</button>
					</form>
				</li>
				
				<li class="line">line</li>	
				<li><label><input type="checkbox" class="checkboxCtrl" group="productid"/>全选</label></li>
<!-- 				<li><label><input type="checkbox" class="checkboxCtrl" group="productid" selectType="invert"/>反选</label></li> -->
				<li class="line">line</li>
			</ul>
	</div>
	

	


</div>
<div class="pageContent">
	
	<form method="post" id="myform" name="myForm" onsubmit="return validateCallback(this, navTabAjaxDone)">
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th style="width:40px;">选择</th>
				<th>编号</th>
				<th style="width:600px;">商品名称</th>
				<th>商品类型</th>
				<th>商品详情</th>
				<th>商品状态</th>
				<th>设为重点关注</th>
				<th>商品留言</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator var="product" value="#request.list" status='st'> 
			<tr target="sid_product" rel="${product.id}">
				<td><label><input type="checkbox" value="${product.id }" name="productid"/></label></td>
				<td>${st.index+1 }</td>
				<td>${product.info.name }</td>
				<td>${product.brand.name }</td>
				<td><a class="button" href="AndroidProductData?productid=${product.id }" target="dialog" width="800" height="480"><span>商品详情</span></a></td>
				<s:if test="#product.pointed==1">
				<td><span style="color:green">关注</span></td>
				<td><a href="productPointed?lock=0&productid=${product.id }&action=${requestScope.action}" class="button" target="ajaxTodo"><span>解除关注</span></a></td>
				</s:if>
				<s:else>
				<td><span style="color:red">未关注</span></td>
				<td><a href="productPointed?lock=1&productid=${product.id }&action=${requestScope.action}" class="button" target="ajaxTodo"><span>关注</span></a></td>
				</s:else>
				<s:if test="#product.message==null">
					<td><a href="dataMessage?productid=${product.id }&action=${requestScope.action}" class="button" target="dialog"><span style="color:red">添加备注</span></a></td>
				</s:if>
				<s:else>
					<td><a href="dataMessage?productid=${product.id }&action=${requestScope.action}" class="button" target="dialog"><span style="color:green">修改备注</span></a></td>
				</s:else>
			</tr>
		</s:iterator>
		</tbody>
	</table>
<!-- 	<input type="submit" id="gosubmit" style="display"/> -->
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


</body>
</html>