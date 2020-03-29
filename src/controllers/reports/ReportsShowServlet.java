package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        String report_id = request.getParameter("id");
        Report r = em.find(Report.class, Integer.parseInt(report_id));
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        int x = login_employee.getId();
        int writer = r.getEmployee().getId();
        
        long following_count = (long)em.createNamedQuery("getFollowingCount", Long.class)
                .setParameter("fr", x)
                .setParameter("fd", writer)
                .getSingleResult(); 
        
        em.close();

        request.setAttribute("report", r);
        request.setAttribute("_token", request.getSession().getId());
        request.getSession().setAttribute("report_id", report_id);
        request.setAttribute("following_count", following_count);
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
