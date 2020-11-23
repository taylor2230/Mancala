package edu.metrostate.ics425.p5.st3306.servlet;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import edu.metrostate.ics425.p5.st3306.model.Game;

/**
 * Application Lifecycle Listener implementation class GameSession
 *
 */
@WebListener
public class GameSession implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public GameSession() {
        // TODO Auto-generated constructor stub
    	super();
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  {
         se.getSession().setAttribute("game", new Game());
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    }
	
}
