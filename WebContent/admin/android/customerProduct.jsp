<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="pageContent" style="height:100%;">
	<table class="table" width="100%" layoutH="150">
		<thead>
			<tr>
				<th>编号</th>
				<th>关注商品名称</th>
				<th>统计</th>
				<th>商品详情</th>
				<th>客户统计</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator var="cp" value="#request.list" status='st'> 
			<tr>
				<td>${st.index+1 }</td>
				<td>${cp.product.info.name}</td>
				<td>有其余<span style="color:red">${requestScope.countlist[st.index]-1 }</span>名用户关注该商品</td>
				<td><a class="button" href="AndroidProductData?productid=${cp.product.id }" target="dialog" ><span>商品详情</span></a></td>
				<td><a class="button" href="customerAnalysis?productid=${cp.product.id }" target="navTab"><span>客户统计</span></a></td>
			</tr>
		</s:iterator>
		</tbody>
	</table>

</div>