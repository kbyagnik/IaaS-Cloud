<%-- 
    Document   : index
    Created on : 7 Feb, 2015, 11:08:26 PM
    Author     : master
--%>

<%@page import="org.virtualbox_4_3.IMediumFormat"%>
<%@page import="com.vbox.view.UserCloud"%>
<%@page import="org.virtualbox_4_3.IGuestOSType"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	UserCloud cloud = (UserCloud) session.getAttribute("cloud");
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create New VM...</title>
<link rel="stylesheet" type="text/css" href="style.css">

<script>
	function show_hdd_div() {
		if (document.getElementById("createhdd").checked == true) {
			document.getElementById("hdd_div").style.visibility = "visible";
			document.getElementById("hdd_div").style.display = "block";
			document.getElementById("createhdd").value = "ON";
		} else {
			document.getElementById("hdd_div").style.visibility = "hidden";
			document.getElementById("hdd_div").style.display = "none";
			document.getElementById("createhdd").value = "OFF";
		}
	}

	function show_from_existing() {
		document.getElementById("from_existing_form_div").style.display = "block";
		document.getElementById("createvm_form_div").style.display = "none";
		document.getElementById("create_from_existing").disabled = true;
		document.getElementById("create_newvm").disabled = false;
	}

	function show_createnew() {
		document.getElementById("from_existing_form_div").style.display = "none";
		document.getElementById("createvm_form_div").style.display = "block";
		document.getElementById("create_from_existing").disabled = false;
		document.getElementById("create_newvm").disabled = true;
	}
</script>


</head>
<body>
	<div id="header">
		<h1>Welcome to MyCloud IaaS Cloud Computing Service!</h1>
	</div>

	<div name="section_div" id="section">

		<div align="center">
			<input type="button" value="Create from existing..."
				name="create_from_existing" id="create_from_existing"
				onclick="show_from_existing()" /> <input type="button"
				value="Create new VM" name="create_newvm" id="create_newvm"
				onclick="show_createnew()" />
		</div>

		<div name="from_existing_form_div" id="from_existing_form_div"
			style="display: none;">
			<form name="from_existing_form" action="clonevm" method="POST">
				<table border="0" width="50%" align="center">
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
							<td width=50%>Pre-Created VM :</td>
							<td width=50%><select name="vmtoclone" id="vmtoclone">
									<option value="DSL">Damn Small Linux (DSL)</option>
									<option value="Puppy">Puppy Linux</option>
							</select></td>
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
				<br> <br>
				<div align="center">
					<input type="submit" value="Create VM" name="submit" />
				</div>

			</form>

		</div>

		<div id="createvm_form_div" name="createvm_form_div"
			style="display: none;">

			<form name="createvm" action="createvm" method="POST">
				<table border="0" width="50%" align="center">
					<thead>
						<tr align="left">
							<th width=50%>Parameter</th>
							<th width=50%>Value</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td width=50%>Name of the VM :</td>
							<td width=50%><input type="text" name="vmname" value="" /></td>
						</tr>


						<!-- <tr>
                                <td>OS type : </td>
                                <td><select name="vmostype" id="vmostype" onchange="listos()">
                                        <option value="Microsoft Windows" selected="selected">Microsoft Windows</option>
                                        <option value="Linux">Linux</option>
                                        <option value="OS X">OS X</option>
                                    </select></td>
                            </tr> -->


						<tr>
							<td width=50%>OS Type :</td>
							<%
								List<IGuestOSType> ls = cloud.getVbox().getGuestOSTypes();
							%>
							<td width=50%><select name="vmosname" id="vmosname">
									<%
										for (IGuestOSType g : ls) {
											out.println("<option value='" + g.getId() + "' id='"
													+ g.getId() + "'/>" + g.getDescription() + "</option>");
										}
									%>
							</select></td>
						</tr>





						<tr>
							<td width=50%>RAM :</td>
							<td width=50%><select name="vmram" id="vmram">
									<option value="512">512</option>
									<option value="1024">1024</option>
									<option value="1536">1536</option>
									<option value="2048">2048</option>
									<option value="4048">4048</option>
									<option value="8064">8064</option>
							</select>MB's</td>
						</tr>
						<tr>
							<td width=50%>Processors :</td>
							<td width=50%><input type="radio" name="vmproc" value="1"
								checked="checked" />1 <input type="radio" name="vmproc"
								value="2" />2 <input type="radio" name="vmproc" value="3" />3
								<input type="radio" name="vmproc" value="4" />4</td>
						</tr>

						<tr>
							<td width=50%>Create HDD:</td>
							<td width=50%><input type="checkbox" name="createhdd"
								id="createhdd" onchange="show_hdd_div()" value="OFF" /></td>
						</tr>
					</tbody>
				</table>
				<div name="hdd_div" id="hdd_div"
					style="visibility: hidden; display: none;">
					<table border="0" width="40%" align="center">
						<tbody>
							<tr>
								<td width="50%">HDD type :</td>
								<%
									List<IMediumFormat> mf = cloud.getVbox().getSystemProperties()
											.getMediumFormats();
								%>
								<td width="50%"><select name="vmhddtype">
										<%
											for (IMediumFormat e : mf) {
												out.print("<option value='" + e.getName() + "'>" + e.getName()
														+ "</option>");
											}
										%>

								</select></td>
							</tr>
							<tr>
								<td>HDD size :</td>
								<td><select name="vmhddsize">
										<option value="64 MB">64 MB</option>
										<option value="256 MB">256 MB</option>
										<option value="1 GB">1 GB</option>
										<option value="4 GB">4 GB</option>
										<option value="16 GB">16 GB</option>
										<option value="64 GB">64 GB</option>
								</select></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--  <table border="0" width="40%" align="center">
					<tbody>
						<tr>
							<td width="50%">Allow SSH :</td>
							<td width="50%"><input type="checkbox" name="vmssh"
								value="ON" checked="checked" /></td>
						</tr>
						<tr>
							<td>Network Adapter :</td>
							<td></td>
						</tr>
					</tbody>
				</table>-->




				<br> <br>
				<div align="center">
					<input type="submit" value="Create VM" name="submit" />
				</div>

			</form>
		</div>
	</div>


	<div id="footer">Created and managed by Kaushal and Gaurav</div>


</body>
</html>
