<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style type="text/css">
	#picPanel2{
	width:300px;
	float:left;
	}
	#productInfo2 .unit{
	height:60px;
	} 
	#productInfo2 .unit label{
 	font-size:25px;
	}
	#productInfo2 .unit span{
	font-size:25px;
	}
	#productInfo2 .unit input,select{
 	height:20px;
 	position:relative;
 	top:-5px;
/* 	width:200px; */
	}
</style>
<script type="text/javascript">
var productid=${requestScope.product.id};
var NowFrame = 1; //初始化显示第几张
var MaxFrame = ${requestScope.product.picture.size()}; //最大显示几张
function show2(num) {
    for (var i = 1; i < (MaxFrame + 1); i++) {
        if (i == num)
            document.getElementById(productid+'editdiv' + num).style.display = ''; //当前图片显示
        else
            document.getElementById(productid+'editdiv' + i).style.display = 'none'; //其他的图片隐藏
    }
//     {
//         if (NowFrame == MaxFrame) //定义下一张显示的图片
//             NowFrame = 1;
//         else
//             NowFrame++;
//     }
 
}
show2(1);
</script>
</head>
<body>
	
	<form method="post" action="productUpdate?id=${requestScope.product.id }" onsubmit="return validateCallback(this, navTabAjaxDone)">
	<div class="pageHeader">
		<div class="panelBar">
			<ul class="toolBar">
				<li><button type="submit">保存</button></li>
			</ul>
		</div>
	</div>
	
	<div class="pageContent"  layoutH="60" style="overflow:auto;" >
		<div style="margin-top:3px;margin-left:10px;">
		<div style="position:relative;top:10px;left:10px;background-color:white;width:90px;"><label style="font-size:20px;font-weight:bold;">商品图片</label></div>
		<div id="picPanel2"  style="border:2px grey solid; width:30%;">
				
			<div>
				<s:iterator var='p' value="#request.product.picture" status='st'>
					<img src="${p.address }" style="display:none" id="${requestScope.product.id }editdiv${st.index+1 }" width="90%" height="370px;"/>
				</s:iterator>
				
			</div>
			<div>
<%-- 			.<a href="editProductPage?id=${requestScope.product.id }" rel="editProductPage?id=${requestScope.product.id }"><span>下一张</span></a> --%>
			<div style="width:100%;height:50px;">
			<s:iterator var='p' value="#request.product.picture" status='st'>
				<div style="float:left;width:${94/requestScope.product.picture.size()}%;">
				<a href="deletePic?picid=${p.id }&id=${requestScope.product.id}" target="ajaxTodo" title="确定要删除吗?"><img src="${p.address }" style="width:100%;height:50px;" onmouseover="show2('${st.index+1 }')"/></a>
				</div>
			</s:iterator>
			</div>
			</div>
			
		</div>
		
		<div style="float:right;width:65%;margin-right:15px;">
			<div id="productInfo2" style="border:2px grey solid;height:422px;">
			<div style="position:relative;top:-10px;left:10px;background-color:white;width:90px;"><label style="font-size:22px;font-weight:bold;">商品信息</label></div>
			<div class="unit">
				<label>商品名称:</label>
				<span><input type="text" value="${requestScope.product.info.name }" name="productname" style = "width:400px;"/></span>
			</div>
			<div class="unit">
				<label>商品品牌:</label>
				<span>
					<select name="brandid">
						<option value="${requestScope.product.brand.id }"></option>
						<s:iterator var="b" value="#request.brandlist">
							<option value="${b.id}">${b.name }</option>
						</s:iterator> 
					</select>
				</span>
			</div>
			<div class="unit">
				<label>商品价格:</label>
				<span><input type="text" value="${requestScope.product.info.price }" name="price"/></span>
			</div>
			<div class="unit">
				<label>链接地址:</label>
				<span><input type="text" value="${requestScope.product.info.webaddress }" name="webaddress" style="width:400px;"/></span>
			</div>
			<div class="unit">
				<label style="float:left;">商品图片:</label>
				<a href="addPicPage?id=${requestScope.product.id }" class="button" style="margin-left:20px;" target="dialog" width="400" height="80"><span style="font-size:10px;">添加图片</span></a>
			</div>
			<div class="unit">
				<label style="float:left;">商品描述:</label>
				<a href="editDescriptionPage?id=${requestScope.product.id }" class="button" style="margin-left:20px;" target="dialog"><span style="font-size:10px;">点击编辑</span></a>
			</div>
			
			
		</div>
		</div>
		</div>
	</div>
	
	</form>
</body>
</html>