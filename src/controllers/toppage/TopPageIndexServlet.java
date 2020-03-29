package controllers.toppage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow_list;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }
        List<Report> reports = em.createNamedQuery("getMyAllReports", Report.class)
                                  .setParameter("employee", login_employee)
                                  .setFirstResult(15 * (page - 1))
                                  .setMaxResults(15)
                                  .getResultList();

        long reports_count = (long)em.createNamedQuery("getMyReportsCount", Long.class)
                                     .setParameter("employee", login_employee)
                                     .getSingleResult();
        
        List<Follow_list> myfollowed_list_list = em.createNamedQuery("getMyFolloweds",Follow_list.class)
        								.setParameter("fr", login_employee.getId())
        								.getResultList();

        List<Report> followeds_reports = new ArrayList<Report>();
        for(Follow_list myfollowed_list : myfollowed_list_list){
        	Employee myfollowed_employee = em.createNamedQuery("getEmployee", Employee.class)
        									.setParameter("id", myfollowed_list.getFollowed())
        									.getSingleResult();
        	List<Report> rs =  em.createNamedQuery("getAllFollowedsReports",Report.class)
        									.setParameter("myfollowed", myfollowed_employee)
        									.getResultList();
        	for(Report r : rs) followeds_reports.add(r);
         }
        
        int rs_page;
        try{
            rs_page = Integer.parseInt(request.getParameter("rs_page"));
        } catch(Exception e) {
            rs_page = 1;
        }
   
        int followed_reports_count = followeds_reports.size();
        List<Report> rs_view;
        try{
        	rs_view = followeds_reports.subList(15 * (rs_page - 1), 15 * rs_page);
        }catch(Exception e){
        	rs_view = followeds_reports.subList(15 * (rs_page - 1), followed_reports_count);
        }
        
        em.close();
        	
        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
		request.setAttribute("followeds_reports", followeds_reports);
		request.setAttribute("zenken", followed_reports_count);
		request.setAttribute("rs_view", rs_view);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}