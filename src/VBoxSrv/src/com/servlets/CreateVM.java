package com.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.virtualbox_4_3.AccessMode;
import org.virtualbox_4_3.DeviceType;
import org.virtualbox_4_3.IMachine;
import org.virtualbox_4_3.IMedium;
import org.virtualbox_4_3.IProgress;
import org.virtualbox_4_3.ISession;
import org.virtualbox_4_3.ISystemProperties;
import org.virtualbox_4_3.IVirtualBox;
import org.virtualbox_4_3.LockType;
import org.virtualbox_4_3.MediumVariant;
import org.virtualbox_4_3.StorageBus;
import org.virtualbox_4_3.VBoxException;
import org.virtualbox_4_3.VirtualBoxManager;

import com.vbox.view.UserCloud;

/**
 * Servlet implementation class CloudCreateVM
 */

public class CreateVM extends HttpServlet {
	UserCloud cloud;
	VirtualBoxManager mgr;
	IVirtualBox vbox;
	List<IMachine> vmList;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateVM() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String created = (String) request.getAttribute("created");
		if (created != null && created.equals("true"))
			response.sendRedirect(request.getContextPath());
		else
			request.getRequestDispatcher("createvm.jsp").forward(request,
					response);
	}

	private HttpServletRequest initialize(HttpServletRequest request) {
		// TODO Auto-generated method stub

		if (request.getAttribute("status") == null
				|| !(boolean) request.getAttribute("status")) {
			System.out.println("creating cloud...");
			cloud = new UserCloud();
			boolean status = cloud.connect();
			setParameters();

			System.out.println("created cloud...");
			request.setAttribute("cloud", cloud);
			request.setAttribute("vmList", vmList);
			request.setAttribute("status", status);
		} else {
			cloud = (UserCloud) request.getAttribute("cloud");
		}
		return request;
	}

	private void setParameters() {
		// TODO Auto-generated method stub
		mgr = cloud.getMgr();
		vbox = cloud.getVbox();
		vmList = cloud.getVmList();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * String action; request = initialize(request);
		 * if((action=request.getParameter("action"))!=null){
		 * if(action.equals("Create")){ IMachine newMach; try { if(vbox==null)
		 * System.out.println("oh no again"); newMach = vbox.createMachine("",
		 * "DSLL", null, "", null); newMach.saveSettings();
		 * vbox.registerMachine(newMach); } catch (VBoxException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * } }
		 */

		System.out.println("dopostCreateVM");
		HttpSession session = request.getSession(false);

		if (session != null) {
			UserCloud cloud = (UserCloud) session.getAttribute("cloud");
			IVirtualBox vbox = cloud.getVbox();
			IMachine newMach;
			try {
				if (vbox == null)
					System.out.println("oh no again");
				String name = request.getParameter("vmname");
				String osTypeId = request.getParameter("vmosname");
				long memorySize = Long.parseLong(request.getParameter("vmram"));
				long cpucount = Long.parseLong(request.getParameter("vmproc"));

				newMach = vbox.createMachine("", name, null, osTypeId, null);
				newMach.setMemorySize(memorySize);
				newMach.setCPUCount(cpucount);
				newMach.saveSettings();
				vbox.registerMachine(newMach);
				if (request.getParameter("createhdd").equals("ON")) {
					System.out.println("Creating hdd...");
					ISession sess = cloud.getMgr().getSessionObject();
					newMach.lockMachine(sess, LockType.Write);
					IMachine mutable = sess.getMachine();

					mutable.addStorageController("SATA", StorageBus.SATA);
					IMedium hd = vbox.createHardDisk(
							request.getParameter("vmhddtype"),
							mutable.getSettingsFilePath().substring(
									0,
									mutable.getSettingsFilePath().lastIndexOf(
											'/') + 1));
					List<MediumVariant> lss = new ArrayList<MediumVariant>();

					Long size = findHdSize(request.getParameter("vmhddsize"));
					IProgress prog = hd.createBaseStorage(size, lss);
					prog.waitForCompletion(5000);

					vbox.openMedium(hd.getLocation(), DeviceType.HardDisk,
							AccessMode.ReadWrite, Boolean.TRUE);
					mutable.attachDevice("SATA", 0, 0, DeviceType.HardDisk, hd);
					mutable.saveSettings();
					sess.unlockMachine();
				}
			} catch (VBoxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Machine created");
		} else {
			System.out.println("session is null");

		}
		request.setAttribute("created", "true");
		doGet(request, response);
	}

	private Long findHdSize(String parameter) {
		// TODO Auto-generated method stub
		String[] hd = parameter.split(" ");
		Long val = Long.parseLong(hd[0]);
		if (hd[1].equals("MB")) {
			return val * 1024 * 1024;
		} else {
			return val * 1024 * 1024 * 1024;
		}

	}

}
