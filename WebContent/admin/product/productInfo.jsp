<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#picPanel1{
	float:left;
	width:300px;
	}

	.productInfo .unit{
	min-height:50px;
	} 
	.productInfo .unit label{
	font-size:25px;
	}
	.productInfo .unit span{
	font-size:25px;
	}
</style>
<script type="text/javascript">
var NowFrame = 1; //初始化显示第几张
var MaxFrame = ${requestScope.product.picture.size() }; //最大显示几张
function show1(num) {
    for (var i = 1; i < (MaxFrame + 1); i++) {
        if (i == num)
            document.getElementById('infodiv' + num).style.display = ''; //当前图片显示
        else
            document.getElementById('infodiv' + i).style.display = 'none'; //其他的图片隐藏
    }
//     {
//         if (NowFrame == MaxFrame) //定义下一张显示的图片
//             NowFrame = 1;
//         else
//             NowFrame++;
//     }
 
}
show1(1);
</script>
</head>
<body>
	<div class="pageHeader">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a href="editProductPage?id=${requestScope.product.id }" class="edit" target="navTab" rel="editProductPage?id=${requestScope.product.id }"><span>修改信息</span></a></li>
			</ul>
		</div>
	</div>
	<div class="pageContent" layoutH="60" style="overflow:auto;" >
		<div>
		<div style="margin-top:3px;margin-left:10px;">
			<div style="position:relative;top:10px;left:10px;background-color:white;width:90px;"><label style="font-size:20px;font-weight:bold;">商品图片</label></div>
			<div id="picPanel1" style="border:2px grey solid; width:30%;">
				<s:iterator var='p' value="#request.product.picture" status='st'>
					<img src="${p.address }" style="display:none" id="infodiv${st.index+1 }" width="90%;" height="370px;"/>
				</s:iterator>
				<div>
<%-- 			.<a class="button" onclick="show1()" href="#"><span>下一张</span></a> --%>
			<div style="width:100%;height:50px;">
			<s:iterator var='p' value="#request.product.picture" status='st'>
				<div style="float:left;width:${94/requestScope.product.picture.size()}%;">
				<img src="${p.address }" style="width:100%;height:50px;" onmouseover="show1('${st.index+1 }')"/>
				</div>
			</s:iterator>
			</div>
			</div>	
			</div>
			
			
		</div>
		<div style="float:right;width:65%;margin-right:15px;">
			<div class="productInfo" style="border:2px grey solid;min-height:422px;">
			<div style="position:relative;top:-10px;left:10px;background-color:white;width:90px;"><label style="font-size:22px;font-weight:bold;">商品信息</label></div>
			<div class="unit">
				<label>商品名称:</label>
				<span>${requestScope.product.info.name }</span>
			</div>
			<div class="unit">
				<label>商品类型:</label>
				<span>${requestScope.product.brand.type.name }<span>
				
			</div>
			<div class="unit">
				<label>商品品牌:</label>
				<span>${requestScope.product.brand.name }</span>
			</div>
			<div class="unit">
				<label>来源网站:</label>
				<span>${requestScope.product.info.website}</span>
			</div>
			<div class="unit">
				<label>链接地址:</label>
				<span><a href="${requestScope.product.info.webaddress}" target="_blank" style="font-size:20px;color:red;">商品链接</a></span>
			</div>
			<div class="unit">
				<label>商品价格:</label>
				<span>${requestScope.product.info.price }</span>
			</div>
			<div class="unit">
				<label>商品描述:</label>
				<span>${requestScope.product.description.description }</span>
			</div>
		</div>
		</div>
		</div>
	</div>

</body>
</html>