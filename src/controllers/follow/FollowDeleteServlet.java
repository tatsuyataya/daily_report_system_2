package controllers.follow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow_list;
import utils.DBUtil;

/**
 * Servlet implementation class FollowDeleteServlet
 */
@WebServlet("/followdelete")
public class FollowDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String report_id = (String) request.getSession().getAttribute("report_id");
		int follower_id = Integer.parseInt(request.getParameter("follower_id"));
		int followed_id = Integer.parseInt(request.getParameter("followed_id"));
        EntityManager em = DBUtil.createEntityManager();;
       
            List<Follow_list> fl = new ArrayList<Follow_list>();
            
            fl = em.createNamedQuery("getFL", Follow_list.class)
            	.setParameter("fr",follower_id)			
        		.setParameter("fd", followed_id)
        		.getResultList();

        em.getTransaction().begin();
        for (Follow_list e : fl) em.remove(e);
        em.getTransaction().commit();
        em.close();
        
        request.getSession().removeAttribute("report_id");
            
        response.sendRedirect(request.getContextPath() + "/reports/show?id=" + report_id);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
