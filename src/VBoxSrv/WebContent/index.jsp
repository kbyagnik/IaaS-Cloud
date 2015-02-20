<%@page import="com.vbox.view.UserCloud"%>
<%@page import="java.util.List"%>
<%@page import="org.virtualbox_4_3.IMachine"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IaaS Cloud</title>
<link rel="stylesheet" type="text/css" href="style.css">
<%
	UserCloud cloud = (UserCloud) session.getAttribute("cloud");
%>
</head>
<body>
<body>
	<div id="header">
		<h1>Welcome to MyCloud IaaS Cloud Computing Service!</h1>
	</div>

	<div name="section_div" id="section">

		<form name="vm" action="selectvm" method="POST" id="vm_form">
			<table border="0" width=40% cellpadding="1" align="center" size="10">
				<thead>
					<tr>
						<th width="10%">Select</th>
						<th width="50%">Name of the VM</th>
						<th width="40%">Status</th>
					</tr>
				</thead>
				<tbody>


					<%
						List<IMachine> vmList = cloud.getVmList();
									for (IMachine m : vmList) {
										out.print("<tr><td><input type='radio' name='vm' value='"
										+m.getName()
										+"'  checked/></td><td>"
										+m.getName()
										+"</td><td>"
										+m.getState()
										+"</td></tr>");
									}
					%>


				</tbody>
			</table>

			<script>
				var start = document.getElementById("start_btn");
				var stop = document.getElementById("start_btn");
				var manage = document.getElementById("start_btn");
				var clone = document.getElementById("start_btn");
				var deleteb = document.getElementById("start_btn");

				var form = document.getElementById("vm_form");
				function stop() {
					document.getElementById("start_button").disabled = true;
					document.getElementById("stop_button").disabled = false;
					if (document.getElementById("managevm_button").disabled == true)
						document.getElementById("managevm_button").disabled = false;
				}
				function start() {
					document.getElementById("start_button").disabled = false;
					document.getElementById("stop_button").disabled = true;
				}

				function vmrun() {
					form.action = "vmrun";
					form.submit();
				}

				function deletevm() {
					form.action = "deletevm";
					form.submit();
				}
			</script>

			<br>
			<div align="center">
				<input type="submit" name="action" value="Start" id="start_btn" />
				<input type="submit" name="action" value="Stop" id="stop_btn" /> <input
					type="submit" name="action" value="Manage..." id="manage_btn" /> <input
					type="submit" name="action" value="Delete" id="delete_btn" /> <input
					type="submit" name="action" value="Clone..." id="clone_btn" />
			</div>


			<!-- <form name="start_stop_form" action="index.jsp" method="POST">
				<input type="submit" value="Start" name="start" id="start_button"
					disabled="disabled" /> <input type="submit" value="Stop"
					name="stop" id="stop_button" disabled="disabled" />
			</form>
			<input type="submit" value="Manage VM" name="managevm"
				id="managevm_button" disabled="disabled" /> 
				
				<button name="hello" onclick="hello()" />-->
		</form>


		<br> <br>
		<div align="center">
			<form name="createvm_form" action="createvmnew.jsp" method="POST">
				<input type="submit" value="Create New VM..." name="action" />
			</form>
		</div>

	</div>



	<div id="footer">Created and managed by Kaushal and Gaurav</div>
</html>