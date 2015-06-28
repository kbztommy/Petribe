<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Integer onesmemberid = Integer.parseInt(request
			.getParameter("onesmemberid"));
	pageContext.setAttribute("onesmemberid", onesmemberid);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
input#NotifyChat {
	width: 410px
}

#console-container {
	width: 400px;
}

#console {
	border: 1px solid #CCCCCC;
	border-right-color: #999999;
	border-bottom-color: #999999;
	height: 170px;
	overflow-y: scroll;
	padding: 5px;
	width: 100%;
}

#console p {
	padding: 0;
	margin: 0;
}
</style>
<script type="application/javascript">
	
        var NotifyChat = {};

        NotifyChat.socket = null;

        NotifyChat.connect = (
        	function(host) {
            if ('WebSocket' in window) {
                NotifyChat.socket = new WebSocket(host);
            } else if ('MozWebSocket' in window) {
                NotifyChat.socket = new MozWebSocket(host);
            } else {
                Console.log('Error: WebSocket is not supported by this browser.');
                return;
            }

            NotifyChat.socket.onmessage = function (message) {
                Console.log(message.data);
            };
        });

        NotifyChat.initialize = function() {
            if (window.location.protocol == 'http:') {
                NotifyChat.connect('ws://' + window.location.host + '<%= request.getContextPath()%>/za101g2/front/message/NotifyChat/' + ${onesmemberid});
            } else {
            	  NotifyChat.connect('wss://' + window.location.host + '<%= request.getContextPath()%>/za101g2/front/message/NotifyChat/' + ${onesmemberid});
            }
        };

        

        var Console = {};

        Console.log = (function(message) {
            var console = document.getElementById('console');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.innerHTML = message;
            console.appendChild(p);
            while (console.childNodes.length > 25) {
                console.removeChild(console.firstChild);
            }
            console.scrollTop = console.scrollHeight;
        });

        NotifyChat.initialize();
    
</script>
</head>
<body>
	<div class="noscript">
		<h2 style="color: #ff0000">Seems your browser doesn't support
			Javascript! Websockets rely on Javascript being enabled. Please
			enable Javascript and reload this page!</h2>
	</div>
	<div>
		<p>
			<input type="text" placeholder="傳送訊息" id="NotifyChat" />
		</p>
		<div id="console-container">
			<div id="console" />
		</div>
	</div>
</body>
</html>