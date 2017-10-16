<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
var json='${json}';
var cate=eval("("+json+")");
console.log(cate);
console.log(cate.title);

function chart(){
$('#containnm').highcharts({
title:{
	text:cate.title.text,
	style:{
		color: 'black',
        fontWeight: 'bold',
	}
},
xAxis: {

		categories:cate.numlist
},
plotOptions: {
  series: {
      enableMouseTracking: true,
      lineColor:'#303030'
  }
},

series:cate.series


});
}

chart();


</script>
</head>
<!-- <a href="productIndex" target="navTab" rel="productIndex">123</a> -->
<body>
<form id="pagerForm" method="post" action="${requestScope.action }">
	<input type="hidden" name="pageNum" value="${requestScope.pageNum}" />
	<input type="hidden" name="numPerPage" value="${requestScope.numPerPage}" />
	<!-- 自定义变量 -->
<%-- 	<input type="hidden" name="productname" value="${requestScope.productname}"/> --%>
		
<%-- 	<input type="hidden" name="account" value="${requestScope.account}"/> --%>
</form>
<div class="pageHeader">
	<div class="searchBar">	
		
		

	<div class="panelBar">
		<ul class="toolBar">
				<li>
					<form method="post" action="yearOrder" onsubmit="return navTabSearch(this)">
						<button type="submit" class="button">年度排行</button>
					</form>
				</li>
				<li>
					<form method="post" action="seasonOrder" onsubmit="return navTabSearch(this)">
						<button type="submit" class="button">季度排行</button>
					</form>
				</li>
				<li>
					<form method="post" action="monthOrder" onsubmit="return navTabSearch(this)">
						<button type="submit" class="button">月度排行</button>
					</form>
				</li>
				<li class="line"></li>	
				<li>
				<s:set name="type" value="#request.type"/>
				
				
				<form method="post" action="${requestScope.action }" onsubmit="return navTabSearch(this)">
				<s:if test="#type!=''">
					<select name="year">
						<option value="-1">年份</option>
						<option value="2015">2015年</option>
						<option value="2016">2016年</option>
						<option value="2017">2017年</option>
						<option value="2017">2018年</option>
						<option value="2017">2019年</option>
						<option value="2017">2020年</option>
					</select>
					
					<s:if test="#type=='month'">
						<select name="month">
							<option value="-1">月份</option>
							<s:bean name="org.apache.struts2.util.Counter">
								<s:param name="first" value="1" />
								<s:param name="last" value="12" />
								<s:param name="interval" value="1" />
								<s:iterator>
								<option value="<s:property/>"><s:property/></option>
								</s:iterator>
							</s:bean>
						</select>
					</s:if>
					<s:elseif test="#type=='season'">
						<select name="season">
							<option value="-1">季度</option>
							<option value="1">第一季度</option>
							<option value="2">第二季度</option>
							<option value="3">第三季度</option>
							<option value="4">第四季度</option>
						</select>
					</s:elseif>
					<button type="submit" class="button" style="float:right;margin-left:10px;">查询</button>
				</s:if>
				</form>

				
				</li>
				<li class="line">line</li>
				
			</ul>
	</div>

	
</div>	
</div>
<div class="pageContent">
	<div style="width:100%;">
	<div id="containnm" style="width:60%;height:430px;border-right:1px black solid;float:left;" >
	
	</div>
	<div style="float:right;width:35%;">
		<div style="margin-left:10%;"><span style="font-size:25px;">${requestScope.title}</span></div>
		<div>
			<table class="table" width="100%" >
				<thead>
				<tr>
					<th>排名</th>
					<th style="width:300px;">商品</th>
					<th>点击数量</th>
				</tr>
				</thead>
				<tbody>
				<s:iterator var="order" value="#request.orderlist" status="st">
					<tr>
						<td>${(st.index+1)+(pageNum-1)*numPerPage }</td>
						<td><a href="AndroidProductData?productid=${requestScope.idList[st.index] }" target="dialog"  width="800" height="480">${order }</a></td>
						<td><s:property value="#request.count[#st.index]"/></td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
	</div>
	<div class="panelBar" style="float:left;width:100%;">
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

<!-- 	</div> -->
</div>


</body>
</html>