package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.virtualbox_4_3.IConsole;
import org.virtualbox_4_3.IMachine;
import org.virtualbox_4_3.IProgress;
import org.virtualbox_4_3.ISession;
import org.virtualbox_4_3.LockType;
import org.virtualbox_4_3.SessionState;

import com.vbox.view.UserCloud;

/**
 * Servlet implementation class CloundVMRun
 */
@WebServlet("/vmrun")
public class VMRun extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VMRun() {
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
		System.out.println("dopostVMRun");
		HttpSession session = request.getSession(false);
		ISession currSession = null;
		IMachine currMachine = null;
		IConsole currConsole = null;
		if (session != null) {
			String action = request.getParameter("action");
			String vmName = request.getParameter("vm");
			
			UserCloud cloud = (UserCloud) session.getAttribute("cloud");
			
			currSession = cloud.getMgr().getSessionObject();
			currMachine = cloud.getVbox().findMachine(vmName);

			System.out.println("Posted " + action + " " + vmName);
			if (action.equals("Start")) {

				if (currMachine != null) {
					
					IProgress prog = currMachine.launchVMProcess(currSession,
							"gui", "");
					prog.waitForCompletion(10000);
					if (prog.getResultCode() != 0) {
						System.out.println("Cannot Launch VM!");
					} else {
						System.out.println("Launched VM!");
					}
					
					if (currSession.getState() == SessionState.Locked)
						currSession.unlockMachine();
				}

			} else if (action.equals("Stop")) {
				
				currMachine.lockMachine(currSession, LockType.Shared);

				currConsole = currSession.getConsole();
				IProgress prog = currConsole.powerDown();
				prog.waitForCompletion(10000);
				
				if (currSession.getState() == SessionState.Locked)
					currSession.unlockMachine();

			}
		} else {
			System.out.println("session is null");
		}
		doGet(request, response);
	}

}
