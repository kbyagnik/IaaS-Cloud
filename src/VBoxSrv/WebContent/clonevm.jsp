<%-- 
    Document   : index
    Created on : 7 Feb, 2015, 11:08:26 PM
    Author     : master
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Clone VM</title>
<link rel="stylesheet" type="text/css" href="style.css">

</head>
<body>
	<div id="header">
		<h1>Welcome to MyCloud IaaS Cloud Computing Service!</h1>
	</div>
	<div name="section_div" id="section">

		<div id="clonevm" name="clone_div" style="display: block;">
			<h2>Cloning selected VM</h2>
			<form name="from_existing_form" action="clonevm" method="POST">
				<table border="0" width="40%" align="center">
					<thead>
						<tr align="left">
							<th width=50%>Parameter</th>
							<th width=50%>Value</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Name of the VM :</td>
							<td><input type="text" name="vmname" value="" /></td>
						</tr>
						<tr>
							<td width="50%">Reinitialize the MAC address:</td>
							<td width="50%"><input type="checkbox" name="reinit_mac"
								value="ON" checked="checked" /></td>
						</tr>
						<tr>
							<td>Clone type :</td>
							<td><input type="radio" name="clone_type" value="full"
								checked="checked" />Full Clone<br> <input type="radio"
								name="clone_type" value="linked" />Linked Clone</td>
						</tr>
					</tbody>
				</table>
				<input type="hidden" name="vmtoclone"
					value="<%out.print(request.getParameter("vm"));%>" /> <br> <br>
				<div align="center">
					<input type="submit" value="Create VM" name="submit" />
				</div>
			</form>

		</div>
	</div>

	<div id="footer">Created and managed by Kaushal and Gaurav</div>

</body>
</html>
