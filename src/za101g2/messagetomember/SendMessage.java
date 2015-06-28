package za101g2.messagetomember;

public class SendMessage {

	public void sendMessage(String tel, String message) {

		try {
			String server = "203.66.172.131";
			int port = 8000;

			String user = "85559671";
			String passwd = "2irioiai";
			String messageUTF8 = new String(message.getBytes(), "BIG5");

			sock2air mysms = new sock2air();
			int ret_code = mysms.create_conn(server, port, user, passwd);
			if (ret_code == 0) {
				System.out.println("Login OK!");
			} else {
				System.out.println("Login Fail!");
				System.out.println("ret_code=" + ret_code + ",ret_content="
						+ mysms.get_message());
				mysms.close_conn();
				return;
			}

			ret_code = mysms.send_message(tel, messageUTF8);
			if (ret_code == 0) {
				System.out.println("簡訊已送到簡訊中心!");
				System.out.println("MessageID=" + mysms.get_message());
			} else {
				System.out.println("簡訊傳送發生錯誤!");
				System.out.print("ret_code=" + ret_code + ",");
				System.out.println("ret_content=" + mysms.get_message());
				mysms.close_conn();
				return;
			}

			mysms.close_conn();

		} catch (Exception e) {

			System.out.println("I/O Exception : " + e);
		}
	}

}
