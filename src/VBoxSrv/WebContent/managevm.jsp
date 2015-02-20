<%-- 
    Document   : index
    Created on : 7 Feb, 2015, 11:08:26 PM
    Author     : master
--%>
<%@page import="org.virtualbox_4_3.MachineState"%>
<%@page import="org.virtualbox_4_3.IMachine"%>
<%@page import="com.vbox.view.UserCloud"%>
<%@page import="org.virtualbox_4_3.IPerformanceCollector"%>
<%@page import="org.virtualbox_4_3.IPerformanceMetric"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	UserCloud cloud = (UserCloud) session.getAttribute("cloud");
	IMachine vm = (IMachine) request.getAttribute("vm");
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manage VMs</title>
<link rel="stylesheet" type="text/css" href="style.css">


</head>
<body>
	<div id="header">
		<h1>Welcome to MyCloud IaaS Cloud Computing Service!</h1>
	</div>

	<div name="section_div" id="section">

		<div id="managevm_stopped" name="manage_stopped_div">
			Managing stopped VM Various Properties of stopped VMs...
			<form name="managevm" action="managevm" method="POST">
				<table border="0" align="center">
					<thead>
						<tr align="left">
							<th width=20%>Parameter</th>
							<th width=50%>New Value</th>
							<th width=30%>Current Value</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>RAM :</td>
							<td><select name="vmram" id="vmram">
									<option value="512">512</option>
									<option value="1024">1024</option>
									<option value="1536">1536</option>
									<option value="2048">2048</option>
									<option value="4048">4048</option>
									<option value="8064">8064</option>
							</select>MB's</td>
							<td>
								<%
									out.println(vm.getMemorySize());
								%>
							</td>
						</tr>
						<tr>
							<td>Processors :</td>
							<td><input type="radio" name="vmproc" value="1"
								checked="checked" />1 <input type="radio" name="vmproc"
								value="2" />2 <input type="radio" name="vmproc" value="3" />3
								<input type="radio" name="vmproc" value="4" />4</td>
							<td>
								<%
									out.println(vm.getCPUCount());
								%>
							</td>
						</tr>
					</tbody>
				</table>
				<input type="hidden" name="vm" value="<%out.print(vm.getName());%>">
				<div align="center">
					<input type="submit" name="action" value="Change" />
				</div>
			</form>
		</div>
		<div id="managevm_run" name="manage_run_div"
			style="display: none; visibility: hidden;">
			<h2>Performance Statistics Of Running VMs</h2>
			<table border="0" width="50%" align="center">
				<thead>
					<tr align="left">
						<th>Metric</th>
						<th>Value</th>

					</tr>
				</thead>
				<tbody>
					<%
						IPerformanceCollector pcollect = cloud.getVbox().getPerformanceCollector() ;
												List<IPerformanceMetric> perfmetric = pcollect.getMetrics(null, null) ;
												for(IPerformanceMetric temp : perfmetric){
					%>
					<tr>
						<td><b> <%
 	out.println(temp.getMetricName());
 %>
						</b></td>
						<td></td>
						<td>
							<%
								out.println(temp.getCount() + " " + temp.getUnit());
							%>
						</td>
					</tr>
					<%
						}
					%>
				</tbody>

			</table>
		</div>

		<script>
			
		<%if (vm.getState().equals(MachineState.Running)) {%>
			document.getElementById("managevm_run").style.display = 'block';
			document.getElementById("managevm_run").style.visibility = 'visible';
			document.getElementById("managevm_stopped").style.display = 'none';
			document.getElementById("managevm_stopped").style.visibility = 'hidden';
		<%}%>
			
		</script>




	</div>

	<div id="footer">Created and managed by Kaushal and Gaurav</div>


</body>
</html>
