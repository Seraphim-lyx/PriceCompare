<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#picPanel{
	float:left;
	width:300px;
	}
	#info{
	}
	.unit{
	height:50px;
	} 
	.unit label{
	font-size:15px;
	}
	.unit span{
	font-size:15px;
	}
</style>
<script type="text/javascript">
var NowFrame = 1; //初始化显示第几张
var MaxFrame = ${requestScope.product.picture.size() }; //最大显示几张
function show(num) {
    for (var i = 1; i < (MaxFrame + 1); i++) {
        if (i == num)
            document.getElementById('div' + num).style.display = ''; //当前图片显示
        else
            document.getElementById('div' + i).style.display = 'none'; //其他的图片隐藏
    }
    {
        if (NowFrame == MaxFrame) //定义下一张显示的图片
            NowFrame = 1;
        else
            NowFrame++;
    }
 
}
show(1);
</script>


</head>
<body>
	<div class="pageContent layoutBox" style="height:807px;">
		
		<div style="float:left">
			<div id="picPanel">
				<div>
				<s:iterator var='p' value="#request.product.picture" status='st'>
					<img src="${p.address }" style="display:none" id="div${st.index+1 }" width="230px" height="300px"/>
					
				</s:iterator>
				
			</div>
		
				<div style="width:100%;height:50px;">
			<s:iterator var='p' value="#request.product.picture" status='st'>
				<div style="float:left;width:${94/requestScope.product.picture.size()}%;">
				<img src="${p.address }" style="width:100%;height:50px;" onmouseover="show('${st.index+1 }')"/>
				</div>
			</s:iterator>
			</div>
			</div>
		</div>
		<div style="float:right;width:60%;">
			<div>
			<div class="unit">
				<label>商品名称:</label>
				<span>${requestScope.product.info.name }</span>
			</div>
			<div class="unit">
				<label>商品类型:</label>
				<span>${requestScope.product.brand.type.name }</span>
			</div>
			<div class="unit">
				<label>商品品牌:</label>
				<span>${requestScope.product.brand.name }</span>
			</div>
			<div class="unit">
				<label>商品价格:</label>
				<span>${requestScope.product.info.price }</span>
			</div>
			<div class="unit">
				<label>商品链接:</label>
				<a href="${requestScope.product.info.webaddress }"><span style="font-size:25px;">链接</span></a>
			</div>
			<div class="unit">
				<label>商品描述:</label>
				<span>${requestScope.product.description.description }</span>
			</div>
		</div>
		</div>

	</div>

</body>
</html>