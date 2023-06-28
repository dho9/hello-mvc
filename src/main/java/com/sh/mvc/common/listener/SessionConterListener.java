package com.sh.mvc.common.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionConterListener
 *
 */
@WebListener
public class SessionConterListener implements HttpSessionListener {
	private int cnt;
    /**
     * Default constructor. 
     */
    public SessionConterListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	cnt++;
//    	System.out.println("[Session Created!] "+ cnt);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	if(cnt>0) {
    		
    		cnt--;
//    		System.out.println("[Session Destroyed!] "+ cnt);
    	}
    	
    }
	
}
