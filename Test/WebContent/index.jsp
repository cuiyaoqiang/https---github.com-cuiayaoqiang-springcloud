<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

<script type="text/javascript" src="jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	/*   */
	function click1() {
		$.ajax({
		     type: "GET",
		     //url: "http://localhost:8081/MySpringBoot/account/findAll",
		     url: "TestSer",
		     success: function(data){
		   	  alert(getCookie("key"));
		              $("#myDiv").html('<h2>'+data+'</h2>');
		        }
		  });
	}
	
	function getCookie(c_name) {  
		if (document.cookie.length>0)  
		{  
		c_start=document.cookie.indexOf(c_name + "=");  
		if (c_start!=-1)  
		{   
		c_start=c_start + c_name.length+1 ;  
		c_end=document.cookie.indexOf(";",c_start);  
		if (c_end==-1) c_end=document.cookie.length;  
		return unescape(document.cookie.substring(c_start,c_end));  
		}   
		}  
		return "";  
		} 
</script>
</head>
<body>

	<div id="myDiv">
		<h2>AJAX</h2>
	</div>
	<button id="b01" type="button" onclick="click1()">click</button>

</body>
</html>