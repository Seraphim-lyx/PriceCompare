<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="pageContent" style="height:100%;">
	<table class="table" width="100%">
		<thead>
			<tr>
				<th>编号</th>
				<th>关键字名称</th>
				<th>统计</th>
			</tr>
		</thead>
		<tbody>
		<s:iterator var="keyword" value="#request.list" status='st'> 
			<tr>
				<td>${st.index+1 }</td>
				<td>${keyword.word}</td>
				<td>有其余<span style="color:red">${requestScope.countlist[st.index]-1 }</span>名用户曾搜索该关键字</td>
			</tr>
		</s:iterator>
		</tbody>
	</table>

</div>