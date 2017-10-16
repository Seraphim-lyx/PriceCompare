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
var json='${sexchart}';
var cate=eval("("+json+")");

// console.log(cate);
// console.log(cate.series.name);
$(function () {
    $('#cussexcontain').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: cate.title,
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
<div style="margin-top:20px;"><label style="font-size:20px;">${product.info.name }-用户统计</label></div>
<div class="anaTable">
<div class="sex">
<div><a href="customerEduAnalysis?point=1&productid=${product.id }" target="navTab">男性:${requestScope.maleNum }</a></div>
<div><a href="customerEduAnalysis?point=2&productid=${product.id }" target="navTab">女性:${requestScope.femaleNum }</a></div>
</div>
</div>
</div>

</div>
<div style="float:right;width:60%;">
<div id="cussexcontain"></div>

</div>
</div>
</body>
</html>