package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.virtualbox_4_3.CleanupMode;
import org.virtualbox_4_3.IMachine;
import org.virtualbox_4_3.IMedium;

import com.vbox.view.UserCloud;

/**
 * Servlet implementation class DeleteVM
 */
@WebServlet("/deletevm")
public class DeleteVM extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteVM() {
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
		response.sendRedirect(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("dopostVMdelete");
		HttpSession session = request.getSession(false);
		IMachine currMachine = null;

		if (session != null) {
			String action = request.getParameter("action");
			String vmName = request.getParameter("vm");
			if (session.getAttribute("cloud") == null)
				System.out.println("gaya!");
			UserCloud cloud = (UserCloud) session.getAttribute("cloud");
			if (cloud == null)
				System.out.println("gaya gaya!");
			if (cloud.getVbox() == null)
				System.out.println("vbox gaya gaya!");
			System.out.println("Posted " + action + " " + vmName);
			
			currMachine = cloud.getVbox().findMachine(vmName);
			
			
			List<IMedium> media = currMachine.unregister(CleanupMode.DetachAllReturnHardDisksOnly);
			currMachine.deleteConfig(media);
			System.out.println("Delete vm");
			
		} else {
			System.out.println("session is null");
		}
		doGet(request, response);

	}

}
