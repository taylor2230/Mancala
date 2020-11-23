package edu.metrostate.ics425.p5.st3306.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.metrostate.ics425.p5.st3306.model.Game;
/**
 * Servlet implementation class ServletGame
 */
@WebServlet("/Mancala")
public class ServletGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGame() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String gameController = request.getParameter("newgame");
    	String tokenB = request.getParameter("b");
    	String tokenA = request.getParameter("a");
    	Integer tokenLocation = -1;

    	if(tokenB != null || tokenA != null) {
    		tokenLocation = Integer.parseInt(request.getParameter("location"));
    	}
    	
    	HttpSession session = request.getSession(false);
    	if(session == null) {
    		session = request.getSession(true);
    	}
		var mancala = (Game) session.getAttribute("game");
		
		if(gameController != null) {
			mancala.newGame();
		} else if(tokenLocation > 0 && tokenLocation != null)
		{
			mancala.makeMove(tokenLocation);
		} else if(tokenLocation < 1)
		{
			request.setAttribute("Error", "Player did not move or tried to move a spot with no stones");
		} else
		{
			request.setAttribute("Error", "Unable to process request; please try again");
		}
		
		System.out.println(mancala.getBoard());
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
