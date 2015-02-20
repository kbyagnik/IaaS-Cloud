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

import org.virtualbox_4_3.CloneMode;
import org.virtualbox_4_3.CloneOptions;
import org.virtualbox_4_3.IMachine;
import org.virtualbox_4_3.IProgress;

import com.vbox.view.UserCloud;

/**
 * Servlet implementation class CloneVM
 */
@WebServlet("/clonevm")
public class CloneVM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CloneVM() {
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
		System.out.println("dopostVMClone");
		HttpSession session = request.getSession(false);
		IMachine currMachine = null;
		if (session != null) {
			String vmName = request.getParameter("vmtoclone");
			UserCloud cloud = (UserCloud) session.getAttribute("cloud");
			System.out.println("Posted "  + vmName);
			currMachine = cloud.getVbox().findMachine(vmName);
			
			String cloneName = request.getParameter("vmname");
			IMachine clone = cloud.getVbox().createMachine("",cloneName,null,"",null);
			
			List<CloneOptions> clOpt = new ArrayList<CloneOptions>();
			if(request.getParameter("clone_type").equals("linked")){
				clOpt.add(CloneOptions.Link);
			}
			if(request.getParameter("reinit_mac")==null){
				clOpt.add(CloneOptions.KeepAllMACs);
			}
			IProgress prog = currMachine.cloneTo(clone, CloneMode.MachineState,clOpt);
			prog.waitForCompletion(5000);
			clone.saveSettings();
			cloud.getVbox().registerMachine(clone);
			
			response.sendRedirect(request.getContextPath());
			
		} else {
			System.out.println("session is null");
			
		}
		

	}

}
