package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.virtualbox_4_3.IMachine;
import org.virtualbox_4_3.ISession;
import org.virtualbox_4_3.LockType;

import com.vbox.view.UserCloud;

/**
 * Servlet implementation class ManageVM
 */
@WebServlet("/managevm")
public class ManageVM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageVM() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("dopostVMManage");
		HttpSession session = request.getSession(false);
	
		IMachine currMachine = null;
		if (session != null) {
			String action = request.getParameter("action");
			if (session.getAttribute("cloud") == null)
				System.out.println("gaya!");
			UserCloud cloud = (UserCloud) session.getAttribute("cloud");
			if (cloud == null)
				System.out.println("gaya gaya!");
			if (cloud.getVbox() == null)
				System.out.println("vbox gaya gaya!");
			if(action.equals("Manage...")){
				String vmName = request.getParameter("vm");
				System.out.println("lPosted " + action + " " + vmName);
				currMachine = cloud.getVbox().findMachine(vmName);
				
				request.setAttribute("vm", currMachine);
				request.getRequestDispatcher("managevm.jsp").forward(request, response);
			}else if(action.equals("Change")){
				String vmName = request.getParameter("vm");
				System.out.println("lPosted " + action + " " + vmName);
				currMachine = cloud.getVbox().findMachine(vmName);
				ISession sessionvm = cloud.getMgr().getSessionObject();
				currMachine.lockMachine(sessionvm, LockType.Write);
				IMachine mutable = sessionvm.getMachine();
				long memorySize = Long.parseLong(request.getParameter("vmram"));
				long cpucount = Long.parseLong(request.getParameter("vmproc"));
				mutable.setMemorySize(memorySize);
				mutable.setCPUCount(cpucount);
				mutable.saveSettings();
				sessionvm.unlockMachine();
				response.sendRedirect(request.getContextPath());
			}
			
			
			
		}
	}

}
