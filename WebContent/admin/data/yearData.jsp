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
console.log(cate.catelist);
console.log(cate.title);
function chart(){
$('#containnn').highcharts({
title:cate.title
,
xAxis: {
//      categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
		categories:cate.catelist
},
plotOptions: {
  series: {
      enableMouseTracking: true,
      lineColor:'#303030'
  }
},

// series: [{
// 	type:"column",
//   data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6,]
//  },
//  { 
//  type:"column",
//  data:  [23, 22.3, 44, 55, 66, 88, 99, 99, 22, 33, 95.6, 54.4]
 	
//  }
// ]
legend: {
    layout: 'vertical',
    align: 'right',
    verticalAlign: 'middle',
    borderWidth: 0
},
series:cate.series


});
}

chart();
</script>
</head>
<body>
<div class="pageHeader">
<!-- 	<div class="searchBar">	 -->
	
<!-- 		<form action="dataTest" method="post" onsubmit="return validateCallback(this, navTabAjaxDone)"> -->
<!-- 			<table class="searchContent"> -->
<!-- 				<tr> -->
<!-- 					<td>商品搜索<td> -->
<!-- 					<td><input type="text"/></td> -->
<!-- 					<td><button class="button" type="submit">搜索</button></td> -->
<!-- 				</tr>		 -->
			
<!-- 			</table> -->
<!-- 		</form> -->
<!-- 	</div> -->

	<div class="panelBar">
		<ul class="toolBar">
				<li><a class="edit" href="seasonData?${requestScope.path }" target="navTab"><span>季度数据</span></a></li>
				<li><a class="delete" href="monthData?${requestScope.path }" target="navTab"><span>月度数据</span></a></li>
				<li class="line"></li>	

			</ul>
	</div>
</div>	
<div class="pageContent"><div class="tabs">

      <div class="tabsHeader">

            <div class="tabsHeaderContent">

                  <ul>

                        <li class="selected"><a href="#"><span>图表</span></a></li>

                        <li><a href="#"><span>报表</span></a></li>

                  </ul>

            </div>

      </div>

      <div class="tabsContent">

           	<div id="containnn"></div>

            <div>
            	<table  class="table" width="100%" layoutH="150">
            		<thead>
						
							<tr>
							<th>商品\年份</th>
							
							<s:iterator var="year" value="#request.yearlist" status="st">
							
							<th><s:property value="#year"/></th>	
							</s:iterator>
							<th>最低点击量</th>
            				<th>最高点击量</th>
            				<th>平均点击量</th>
            				<th>点击总量</th>
            				</tr>
						
					</thead>
					<tbody>
						<s:iterator var="yclist" value="#request.yclist" status='st'> 
						<tr>
							<td>${yclist[0].product.info.name}</td>
						
							<s:iterator var="yc" value="yclist">
								<td>${yc.count}</td>
							
							</s:iterator>
						
							<td>${requestScope.c[st.index][0] }</td>
							<td>${requestScope.c[st.index][1] }</td>
							<td>${requestScope.c[st.index][2] }</td>
							<td>${requestScope.c[st.index][3] }</td>	
						</tr>
						</s:iterator>
					</tbody>
            	</table>
            </div>

      </div>

      <div class="tabsFooter">

            <div class="tabsFooterContent"></div>

      </div>

</div>


</div>
</body>
</html>