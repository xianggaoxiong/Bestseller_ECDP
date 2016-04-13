<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/jquery/jquery-1.9.1.min.js"></script>
<title>POI导入模块</title>
</head>
<body>
	
	<form action="${pageContext.request.contextPath }/upload" method="post" enctype="multipart/form-data">
		<fieldset style="text-align:center">
			请&nbsp;输&nbsp;入&nbsp;p4p&nbsp;的&nbsp;sheet&nbsp;工&nbsp;作&nbsp;表&nbsp;名:
			<select name="p4pName">
				<option value="">请选择...</option>
				<option value="sample-p4p-day">sample-p4p-day</option>
				<option value="sample-p4p-week">sample-p4p-week</option>
				<option value="sample-p4p-month">sample-p4p-month</option>
			</select><br><br>
			请输入sampleDiamond的sheet工作表名:
			<select name="diamondName">
				<option value="">请选择...</option>
				<option value="diamond-day">diamond-day</option>
				<option value="diamond-week">diamond-week</option>
				<option value="diamond-month">diamond-month</option>
			</select><br><br>
			将Excel表的数据导入数据库:
			<input type="file" name="file"/>
			<input type="submit" value="上传"/>
		</fieldset>
	</form>
	
</body>
</html>