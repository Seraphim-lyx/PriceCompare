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
var sexjson='${sexchart}';
var sexcate=eval("("+sexjson+")");
var edujson='${educhart}';
var educate=eval("("+edujson+")");
// console.log(cate);
// console.log(cate.series.name);
$(function () {
    $('#cuscontain').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '用户性别商品关注图'
        },
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
//         series: [
//          {
//             type: 'pie',
//             name: '人数',
//             data: [
//                 ['Firefox',   45.0],
//                 ['IE',       26.8],
//                 {
//                     name: 'Chrome',
//                     y: 12.8,
//                     sliced: true,
//                     selected: true
//                 },
//                 ['Safari',    8.5],
//                 ['Opera',     6.2],
//                 ['Others',   0.7]
//             ]
//         }
      
        
//         ]
        series:sexcate.series
    });
});
$(function () {
    $('#cuscontain2').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '用户学历商品关注图'
        },
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
//         series: [
//          {
//             type: 'pie',
//             name: '人数',
//             data: [
              
//                 ['Safari',    8.5],
//                 ['Opera',     6.2],
//                 ['Others',   0.7]
//             ]
//         }
      
        
//         ]
        series:educate.series
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
<div id="cuscontain"></div>
<div id="cuscontain2"></div>
</div>
</div>
</body>
</html>