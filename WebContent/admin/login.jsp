<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>管理登陆</title>
<script language="javascript" src="../js/crypto/jsbn.js"></script>
<script language="javascript" src="../js/crypto/prng4.js"></script>
<script language="javascript" src="../js/crypto/rng.js"></script>
<script language="javascript" src="../js/crypto/rsa.js"></script>
<jsp:include page="../include.jsp" />
<script type="text/javascript">
var modul='<s:property value="#Modul"/>';
var expo='<s:property value="#Expo"/>';
$(function() {
	$(".checklogin").click(
			function() {
// 				console.log(modul);
// 				console.log(expo);
// 				alert("fuck");
				var cipher = new RSAKey();
				cipher.setPublic(modul,
						expo);
				var encrypted = cipher.encrypt($(".pass").val());
				var username = $(".username").val();
// 				console.log(encrypted)
// 				alert(encrypted);
				$.post('login', {
					"password" : encrypted,
					"username" : username,
					"index":'index'
				},function(data){
					 var d = eval(data);
					 if(d.result=="success"){
						 top.location.href="adminIndex";
					 }
					 else{
						 alert("账号密码错误");
					 }
					 
				})
			})
})
	
</script>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 11pt;
	font-weight: bold;
}
-->
</style>
</head>

<body style="background-image:url(../img/admin_login_bg.gif); margin:0 auto; width:500px;">

<div style="background-image:url(../img/admin_login.png); width:500px; height:200px; margin-top:130px;">
  <form id="form1" name="form1" method="post" action="login">
    <table width="500" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="250">&nbsp;</td>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td height="40"><div align="right" class="STYLE1">用户名：</div></td>
        <td height="40" colspan="2"><input type="text" name="username" class="username" style="height:25px; width:200px; font-size:15pt; font-weight:bold;" /></td>
      </tr>
      <tr>
        <td height="40"><div align="right" class="STYLE1">密&nbsp; 码：</div></td>
        <td height="40" colspan="2"><input type="password" name="password" class="pass" style="height:25px; width:200px; font-size:15pt; font-weight:bold;" /></td>
      </tr>
      <tr>
        <td height="40">&nbsp;</td>
<!--         <td><input type="button" class="checklogin"></td> -->
        <td height="40" colspan="2"><input type="button" class="checklogin" name="Submit" value="" style="width:130px; height:30px; background-image:url(../img/admin_login_button.png); border:0; background-color: transparent; "/></td>
      </tr>
    </table>
  </form>
</div>

</body>
</html>
