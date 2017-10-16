<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>系统管理员</title>
<jsp:include page="../include.jsp" />
<script type="text/javascript">
$(function(){
	DWZ.init("../dwz/dwz.frag.xml", {
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", roleid:"roleid"}, //【可选】
		debug:true,
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"../dwz/themes"});
		}
	});
});
</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="http://j-ui.com">标志</a>
				<ul class="nav">
					<li><a href="logout">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
			</div>
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
				<div class="accordion" fillSpace="sidebar">
					<div class="accordionContent">
						<ul class="tree treeFolder collapse" >
							<li>
								<a href="#" rel="pro_panel">用户管理</a>
									<ul>
										<li>
											<a href="userList" target="navTab"  rel="userList">用户列表</a>
										</li>
									</ul>
							</li>
							<li>
								<a href="#">商品管理</a>
									<ul>
										<li>
											<a href="productType" target="navTab" rel="productType">商品类型管理</a>
											<a href="productIndex" target="navTab" rel="productIndex">商品管理</a>
										</li>
									</ul>
							</li>
							<li>
								<a href="#">数据分析</a>
									<ul>
										<li>
											<a href="dataIndex" target="navTab" rel="dataIndex">趋势分析</a>
											<a href="dataOrder" target="navTab" rel="dataOrder">排行分析</a>
										</li>
									</ul>
								
							</li>
							<li>
								<a href="#">移动端管理</a>
									<ul>
										<li>
											<a href="androidPush" target="navTab" rel="androidPush">商品推送</a>
											<a href="customerIndex" target="navTab" rel="customerIndex">客户信息管理</a>
										</li>
									</ul>
							</li>
							<li>
								<a href="#">工作记录</a>
							
											<ul>
												<li>
													<a href="recordIndex" target="navTab" rel="recordIndex">日志列表</a>
												</li>
											</ul>
										
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<ul class="navTab-tab">
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<div class="tabsRight">right</div>
				</div>
				<div class="navTab-panel tabsPageContent layoutBox">
				</div>
			</div>
		</div>

	</div>

	<div id="footer">Copyright &copy; 2015 <a href="#">商品价格比较管理系统</a></div>
</body>
</html>