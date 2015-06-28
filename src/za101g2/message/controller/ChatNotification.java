package za101g2.message.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//import org.apache.juli.logging.Log;
//import org.apache.juli.logging.LogFactory;


import za101g2.filter.HTMLFilter;
import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;
import za101g2.message.model.MessageVO;

@ServerEndpoint(value = "/za101g2/front/message/notify/", configurator = za101g2.tool.GetHttpSessionConfigurator.class)
public class ChatNotification {

	// private static final Log log = LogFactory.getLog(ChatAnnotation.class);

	protected static final Map<Integer,ChatNotification> connections = new HashMap<Integer,ChatNotification>();
	private Session websocketSession;
	private HttpSession httpSession;
	private MemberVO memberVO;

	@OnOpen
	public void start(Session websocketSession, EndpointConfig config) {
		this.websocketSession = websocketSession;
		this.httpSession = (HttpSession) config.getUserProperties().get(
				HttpSession.class.getName());
		this.memberVO = (MemberVO) httpSession.getAttribute("memberVO");
			
		connections.put(memberVO.getId(),this);
		String message = String.format("* %s %s", memberVO.getId(),
				"has joined ChatNotification.");
		
		System.out.println(message);
	}

	@OnClose
	public void end() {
//		String message = String.format("* %s %s", memberVO.getId(),
//				"has disconnected ChatNotification.");
//		broadcast(message);
//		System.out.println(message);
		connections.remove(memberVO.getId());		

	}

	@OnMessage
	public void incoming(String message) {
		
	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		// log.error("Chat Error: " + t.toString(), t);
		//System.out.println("Chat Error: " + t.toString());
	}
	
	
	protected static void broadcast(Integer fromId,Integer toId) {
		
		for (ChatNotification client : connections.values()) {
			try {
				if(client.memberVO.getId().equals(toId)){
					synchronized (client) {
						client.websocketSession.getBasicRemote().sendText(String.valueOf(fromId));
					}
				}
				
			} catch (IOException e) {
				// log.debug("Chat Error: Failed to send message to client", e);
				connections.remove(client.memberVO.getId());
				try {
					client.websocketSession.close();
				} catch (IOException e1) {
					// Ignore
				}
			}
		}
	}
}
