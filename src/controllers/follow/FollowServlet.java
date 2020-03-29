package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Follow_list;
import utils.DBUtil;

/**
 * Servlet implementation class FollowServlet
 */
@WebServlet("/follow")
public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();
		
		Follow_list f = new Follow_list();
		
		int fr = Integer.parseInt(request.getParameter("follower_id"));
		int fd = Integer.parseInt(request.getParameter("followed_id"));
		String report_id = (String) request.getSession().getAttribute("report_id");
		
		f.setFollower(fr);
		f.setFollowed(fd);
		
		em.getTransaction().begin();
		em.persist(f);
		em.getTransaction().commit();
		em.close();
		
		request.getSession().removeAttribute("report_id");
		
		
//		request.setAttribute("flush", "フォローしました");
		
		response.sendRedirect(request.getContextPath() + "/reports/show?id=" + report_id);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
