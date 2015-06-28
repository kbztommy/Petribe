package za101g2.message.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

//import org.apache.juli.logging.Log;
//import org.apache.juli.logging.LogFactory;

import za101g2.filter.HTMLFilter;
import za101g2.member.model.MemberService;
import za101g2.member.model.MemberVO;
import za101g2.message.model.MessageService;
import za101g2.message.model.MessageVO;

@ServerEndpoint(value = "/za101g2/front/message/chat/{targetMemId}", configurator = za101g2.tool.GetHttpSessionConfigurator.class)
public class ChatServlet {

	// private static final Log log = LogFactory.getLog(ChatAnnotation.class);

	private static final Map<Integer, ChatServlet> connections = new LinkedHashMap<Integer, ChatServlet>();
	private Session websocketSession;
	private HttpSession httpSession;
	private MemberVO memberVO;
	private Integer targetMemId;

	@OnOpen
	public void start(Session websocketSession, EndpointConfig config,
			@PathParam("targetMemId") String targetMemId) {
		this.websocketSession = websocketSession;
		this.httpSession = (HttpSession) config.getUserProperties().get(
				HttpSession.class.getName());
		this.memberVO = (MemberVO) httpSession.getAttribute("memberVO");
		this.targetMemId = Integer.parseInt(targetMemId);
		connections.put(memberVO.getId(), this);
		String message = String.format("* %s %s", memberVO.getId(),
				"has joined CharServlet.");

		Integer targetId = Integer.parseInt(targetMemId);
		List<MessageVO> historyList = getHistoryMessage(memberVO.getId(),
				targetId);

		 for(MessageVO mVO :historyList){
		 historyBroadcast(mVO,memberVO.getId());
		 }
		 
		 MessageService srv = new MessageService();
		 srv.messageBeRead(targetId, memberVO.getId());

		System.out.println(message);
	}

	@OnClose
	public void end() {
		connections.remove(memberVO.getId());
//		String message = String.format("* %s %s", memberVO.getId(),
//				"has disconnected CharServlet.");
		// broadcast(message);
//		System.out.println(message);
	}

	@OnMessage
	public void incoming(String message) {
		// Never trust the client

		MessageService srv = new MessageService();
		srv.insertMessage(memberVO.getId(), targetMemId, message);

		MessageVO messageVO = srv.sent(memberVO.getId());

		// String filteredMessage = String.format("%s: %s",
		// memberVO.getId(), HTMLFilter.filter(messageVO.getMsg()));

		broadcast(messageVO, targetMemId);
		if (!connections.containsKey(targetMemId)) {
			ChatNotification.connections.get(targetMemId);
			if (ChatNotification.connections.get(targetMemId) != null)
				ChatNotification.broadcast(memberVO.getId(), targetMemId);
		} 
	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		// log.error("Chat Error: " + t.toString(), t);
		System.out.println("Chat Error: " + t.toString());
	}

	// 6/16柏儒新增 讀取歷史訊息
	private List<MessageVO> getHistoryMessage(Integer memId, Integer targetId) {
		List<MessageVO> historyList = null;
		MessageService messageSrv = new MessageService();
		historyList = messageSrv.messageHistory(memId, targetId);
		Collections.reverse(historyList);
		return historyList;
	}

	private static void broadcast(MessageVO messageVO, Integer targetMemId) {
		MemberService memberSrv = new MemberService();
		DateFormat hhmmFormater = new SimpleDateFormat("hh:mm");

		String filteredMessage = String.format("<b>%s(%s) : </b>%s", memberSrv
				.getOneMember(messageVO.getMemId()).getNickname(), hhmmFormater
				.format(messageVO.getSendTime()), HTMLFilter.filter(messageVO
				.getMsg()));

		for (ChatServlet client : connections.values()) {
			try {
				if (client.memberVO.getId().equals(messageVO.getMemId())
						&& client.targetMemId.equals(targetMemId)
						|| client.memberVO.getId().equals(targetMemId)
						&& client.targetMemId.equals(messageVO.getMemId())) {
					if(client.memberVO.getId().equals(targetMemId)
							&& client.targetMemId.equals(messageVO.getMemId())){
						MessageService srv = new MessageService();
						srv.messageBeRead(client.targetMemId, client.memberVO.getId());
					}
					synchronized (client) {
						client.websocketSession.getBasicRemote().sendText(
								filteredMessage);
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
				String message = String.format("* %s %s",
						client.memberVO.getId(), "has been disconnected.");
				// broadcast(message);
				System.out.println(message);
			}
		}
	}

	// 0615柏儒新增 發送歷史訊息
	private void historyBroadcast(MessageVO messageVO, Integer targetMemId) {
		MemberService memberSrv = new MemberService();
		DateFormat hhmmFormater = new SimpleDateFormat("hh:mm");
		String filteredMessage = "";

		if (messageVO.getSendTime() == null) {
			filteredMessage = messageVO.getMsg();
		} else {
			filteredMessage = String.format("<b>%s(%s) : </b>%s",
					memberSrv.getOneMember(messageVO.getMemId()).getNickname(),
					hhmmFormater.format(messageVO.getSendTime()),
					HTMLFilter.filter(messageVO.getMsg()));
		}

		try {
			if (targetMemId.equals(messageVO.getMemId())
					|| targetMemId.equals(messageVO.getTargetMemId())) {
				synchronized (this) {
					this.websocketSession.getBasicRemote().sendText(
							filteredMessage);
				}
			}
		} catch (IOException e) {
			// log.debug("Chat Error: Failed to send message to client", e);
			connections.remove(memberVO.getId());
			try {
				this.websocketSession.close();
			} catch (IOException e1) {
				// Ignore
			}
			String message = String.format("* %s %s", this.memberVO.getId(),
					"has been disconnected.");
			// broadcast(message);
			System.out.println(message);
		}

	}
}
