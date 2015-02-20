package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.virtualbox_4_3.IConsole;
import org.virtualbox_4_3.IMachine;
import org.virtualbox_4_3.IProgress;
import org.virtualbox_4_3.ISession;
import org.virtualbox_4_3.IVirtualBox;
import org.virtualbox_4_3.VBoxException;
import org.virtualbox_4_3.VirtualBoxManager;

import com.vbox.view.UserCloud;

/**
 * Servlet implementation class CloudConnect
 */

public class CloudConnect extends HttpServlet {
	UserCloud cloud;
	VirtualBoxManager mgr;
	IVirtualBox vbox;
	List<IMachine> vmList;
	boolean status;
	ISession currSession = null;
	IMachine currMachine = null;
	IConsole currConsole = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CloudConnect() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getAttribute("status") == null
				|| !(boolean)request.getAttribute("status")){
			cloud = new UserCloud();
			status = cloud.connect();
			System.out.println("created cloud...");
			mgr = cloud.getMgr();
			vbox = cloud.getVbox();
			vmList = cloud.getVmList();
			request.setAttribute("cloud", cloud);
			request.setAttribute("vmList", vmList);
			request.setAttribute("status", status);
		}
		
		request.getRequestDispatcher("index.jsp").forward(request, response);

	}

	private HttpServletRequest initialize(HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (request.getAttribute("status") == null
				|| !(boolean)request.getAttribute("status")) {
			System.out.println("creating cloud...");
			cloud = new UserCloud();
			status = cloud.connect();
			mgr = cloud.getMgr();
			vbox = cloud.getVbox();
			vmList = cloud.getVmList();
			
			System.out.println("created cloud...");
			request.setAttribute("cloud", cloud);
			request.setAttribute("vmList", vmList);
			request.setAttribute("status", status);
		}
		return request;
	}

	private boolean connect() {
		// TODO Auto-generated method stub
		mgr = VirtualBoxManager.createInstance(null);
		System.out.println("mgr created");
		try {
			mgr.connect("http://127.0.0.1:18083", "", "");
			vbox = mgr.getVBox();
			System.out.println("VirtualBox version: " + vbox.getVersion());
			vmList = vbox.getMachines();
			return true;
		} catch (VBoxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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
		String action = request.getParameter("action");
		String vmName = request.getParameter("vm");
		cloud = (UserCloud) request.getAttribute("cloud");
		if(cloud==null) System.out.println("Baadal gaya!");
		
		System.out.println("Posted " + action + " " + vmName);
		if (action.equals("Start")) {

			currMachine = vbox.findMachine(vmName);
			if (currMachine != null) {
				currSession = mgr.getSessionObject();
				IProgress prog = currMachine.launchVMProcess(currSession,
						"gui", "");
				prog.waitForCompletion(10000);
				if (prog.getResultCode() != 0) {
					System.out.println("Cannot Launch VM!");
				} else {
					System.out.println("Launched VM!");
				}
			}

		} else if (action.equals("Stop")) {
			currConsole = currSession.getConsole();
			IProgress prog = currConsole.powerDown();
			prog.waitForCompletion(10000);

		}

		doGet(request, response);

	}

}
