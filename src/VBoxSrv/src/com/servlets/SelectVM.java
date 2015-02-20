package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SelectVM
 */
@WebServlet("/selectvm")
public class SelectVM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectVM() {
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
		System.out.println("dopostSelectVM");
		HttpSession session = request.getSession(false);
		if (session != null) {
			String action = request.getParameter("action");
			if(action.equals("Delete")){
				request.getRequestDispatcher("deletevm").forward(request, response );
				return;
			}else if(action.equals("Manage...")){
				request.getRequestDispatcher("managevm").forward(request, response );
				return;
			}else if(action.equals("Clone...")){
				request.getRequestDispatcher("clonevm.jsp").forward(request, response );
				return;
			}else{
				request.getRequestDispatcher("vmrun").forward(request, response );
				return;
			}
		} else {
			System.out.println("session is null");
		}
	}

}
