<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.anaTable div{
    margin-top:40px;
	margin-left:35px;
	
}
.anaTable div a{
	font-size:20px;
}
</style>
<script type="text/javascript">
var json='${educhart}';
var cate=eval("("+json+")");

// console.log(cate);
console.log(cate.title);
$(function () {
    $('#cuseducontain').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title:cate.title,
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series:cate.series
    });
});

</script>
</head>
<body>
<div class="pageContent" layoutH="10">
<div style="float:left;width:35%;">
<div style="margin-left:20px;">
<div style="margin-top:20px;"><label style="font-size:20px;">${product.info.name }-男性学历用户统计</label></div>
<div class="anaTable">
<div class="edu">
<div><a href="customerSexAnalysis?point=1&productid=${product.id }" target="navTab">小学:${requestScope.priNum }</a></div>
<div><a href="customerSexAnalysis?point=2&productid=${product.id }" target="navTab">初中:${requestScope.secNum }</a></div>
<div><a href="customerSexAnalysis?point=3&productid=${product.id }" target="navTab">高中:${requestScope.highNum }</a></div>
<div><a href="customerSexAnalysis?point=4&productid=${product.id }" target="navTab">大学或以上:${requestScope.uniNum }</a></div>
</div>
</div>
</div>

</div>
<div style="float:right;width:60%;">
<div id="cuseducontain"></div>

</div>
</div>
</body>
</html>