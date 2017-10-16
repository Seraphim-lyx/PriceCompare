
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%-- <jsp:include page="../../include.jsp" /> --%>
<html>
<head>
<style>
input {
	width: 200px;
}
#r1,#r2{
	width:50px;
}
.unit{
	margin-top:15px;
	height:10px;
}


</style>
<script type="text/javascript">
function addInput(content){
	
	$("#"+content).append("<div class='unit'>");
	$("#"+content).append("<label>&nbsp商品名称：</label>");
	$("#"+content).append("<input type='text' name='productNames'/>");
	$("#"+content).append("</div>");
}

function validatePrice(me){
	var minprice=document.getElementById("minprice");
	var maxprice=document.getElementById("maxprice");
	var reg = new RegExp("^[0-9]*$");  
	 if(!reg.test(minprice.value)||!reg.test(maxprice.value)){  
	        alert("请输入数字!");  
	        return false;
	    }  
	 else{
		if(parseInt(minprice.value)>parseInt(maxprice.value)){
			alert("请输入正确范围");
			return false;
		}
		else{
			return navTabSearch(me);	
		}
	 }
}
</script>
</head>
<body>
<div class="pageContent">
	<div class="tabs">

      <div class="tabsHeader">

            <div class="tabsHeaderContent">

                  <ul>

                        <li class="selected"><a href="#"><span>名称搜索</span></a></li>

                        <li><a href="#"><span>商品类型搜索</span></a></li>
                        
                        <li><span>价格区间搜索</span></li>
						
						<li><span>混合条件搜索</span></li>			
                  </ul>

            </div>

      </div>

      <div class="tabsContent" style="height:190px;">

 				
            <div>
				<form method="post" action="nameProductSearch?action=nameProductSearch" name="nameForm" onsubmit="return navTabSearch(this)">
				<div style="height:140px;"  id="nameFormContent">
					<div class="unit">
						<label style="font-size:10px">商品名称：</label>
						<input type="text" name="productNames" style="font-size:10px;" />
					</div>	
				</div>
				<div class="formBar">
					<ul>
						<li><div class="addbutton"><div class="buttonContent"><button type="button" id="add" onclick="addInput('nameFormContent')">添加</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
					</ul>
				</div>
				</form>
			</div>

            <div>
				<form method="post" action="typeProductSearch?action=typeProductSearch" name="typeForm" onsubmit="return navTabSearch(this)" > 
					<div style="height:140px;" id="typeFormContent">
					<div class="unit">
						
						<select class="combox" name="typeid" ref="combox_city" refUrl="typeToBrand?code={value}">
	  						<option value="-1">所有类型</option>
							<s:iterator var="type" value="#request.list" status='st'> 
      						<option value="${type.id }">${type.name }</option>
							</s:iterator>
						</select>
					
						
						<select class="combox" name="brandid" id="combox_city">
							<option value="-1">所有品牌</option>
						</select>
     					

					</div>	
				</div>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
					</ul>
				</div>
				</form>
			</div>
			
			<div>
				<form method="post" action="priceProductSearch?action=priceProductSearch" onsubmit="return validatePrice(this)">
					<div style="height:140px;" id="priceFormContent">
						<div class="unit">
							<label>价格下限:</label>
							<input type="text" name="minprice" id="minprice"/>
						</div>
						<br/>
						<div class="unit">
							<label>价格上限:</label>
							<input type="text" name="maxprice" id="maxprice"/>
						</div>
					</div>
					<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
					</ul>
				</div>
				</form>
			</div>
			
			 <div>
				<form method="post" action="mixProductSearch?action=mixProductSearch" name="nameForm" onsubmit="return navTabSearch(this)">
				<div style="height:150px;">
					<div class="unit">
						<label style="font-size:10px">商品名称：</label>
						<input type="text" name="productname" class="required" style="font-size:10px;" />
					</div>
					<div class="unit">
						<label style="font-size:10px;float:left;padding-top:5px;">商品类型：</label>
						<select class="combox" name="typeid" ref="combox_brand" refUrl="typeToBrand?code={value}">
	  						<option value="-1">所有类型</option>
							<s:iterator var="type" value="#request.list" status='st'> 
      						<option value="${type.id }">${type.name }</option>
							</s:iterator>
						</select>
					
						
						<select class="combox" name="brandid" id="combox_brand">
							<option value="-1">所有品牌</option>
						</select>
					</div>
					<div class="unit">
							<label>价格下限:</label>
							<input type="text" name="minprice" id="minprice"/>
						</div>
						
						<div class="unit">
							<label>价格上限:</label>
							<input type="text" name="maxprice" id="maxprice"/>
						</div>	
						<div class="unit">
							<label>商品来源:</label>
							<input type="text" name="website"/>
						</div>	
					</div>
				
				
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
					</ul>
				</div>
				</form>
			</div>
      </div>

      <div class="tabsFooter">

            <div class="tabsFooterContent"></div>

      </div>

</div>
	
</div>
</body>
</html>