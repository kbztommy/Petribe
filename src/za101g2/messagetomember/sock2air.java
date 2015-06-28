package za101g2.messagetomember;

import java.io.*;
import java.net.*;

public class sock2air {

	private Socket sock;
	private DataInputStream din;
	private DataOutputStream dout;
	private String ret_message = "";

	public sock2air() {
	};

	public int create_conn(String host, int port, String user, String passwd) {

		byte out_buffer[] = new byte[258];

		byte ret_code = 99;
		byte ret_content[] = new byte[128];

		try {
			this.sock = new Socket(host, port);

			this.din = new DataInputStream(this.sock.getInputStream());
			this.dout = new DataOutputStream(this.sock.getOutputStream());

			int i;

			for (i = 0; i < 258; i++)
				out_buffer[i] = 0;
			for (i = 0; i < 128; i++)
				ret_content[i] = 0;

			String acc_pwd_str = user.trim() + "\0" + passwd.trim() + "\0";
			byte acc_pwd_byte[] = acc_pwd_str.getBytes();
			byte acc_pwd_size = (byte) acc_pwd_byte.length;

			out_buffer[0] = 0;
			out_buffer[1] = 0;
			out_buffer[2] = acc_pwd_size;
			for (i = 0; i < acc_pwd_size; i++)
				out_buffer[i + 3] = acc_pwd_byte[i];

			this.dout.write(out_buffer);

			ret_code = this.din.readByte();

			this.din.read(ret_content, 0, 128);
			this.ret_message = new String(ret_content);
			return ret_code;

		} catch (UnknownHostException e) {
			this.ret_message = "Cannot find the host!";
			return 70;
		} catch (IOException ex) {
			this.ret_message = "Socket Error: " + ex.getMessage();
			return 71;
		}

	}

	public void close_conn() {
		try {
			if (this.din != null)
				this.din.close();
			if (this.dout != null)
				this.dout.close();
			if (this.sock != null)
				this.sock.close();

			this.din = null;
			this.dout = null;
			this.sock = null;

		} catch (UnknownHostException e) {
			this.ret_message = "Cannot find the host!";
		} catch (IOException ex) {
			this.ret_message = "Socket Error: " + ex.getMessage();
		}

	}

	public int send_message(String sms_tel, String message) {

		byte out_buffer[] = new byte[258];

		byte ret_code = 99;
		byte ret_content[] = new byte[128];

		if (message.length() > 160) {
			this.ret_message = "msg_content > max limit!";
			return 80;
		}

		try {

			int i;
			for (i = 0; i < 258; i++)
				out_buffer[i] = 0;
			for (i = 0; i < 128; i++)
				ret_content[i] = 0;

			String msg_content = sms_tel.trim() + "\0" + message.trim() + "\0";
			byte msg_content_byte[] = msg_content.getBytes("Big5");
			int msg_content_size = msg_content_byte.length;

			out_buffer[0] = 0;
			out_buffer[1] = 1;
			out_buffer[2] = (byte) (msg_content_size + 2);

			for (i = 0; i < msg_content_size; i++)
				out_buffer[i + 3] = msg_content_byte[i];

			out_buffer[msg_content_size + 3] = 100;
			out_buffer[msg_content_size + 4] = 0;

			this.dout.write(out_buffer);

			ret_code = this.din.readByte();

			this.din.read(ret_content, 0, 128);
			this.ret_message = new String(ret_content);
			this.ret_message = this.ret_message.trim();
			return ret_code;

		} catch (UnknownHostException eu) {
			this.ret_message = "Cannot find the host!";
			return 70;
		} catch (IOException ex) {
			this.ret_message = " Socket Error: " + ex.getMessage();
			return 71;
		}
	}

	public int send_message(String sms_tel, String message, String order_time) {

		byte out_buffer[] = new byte[258];

		byte ret_code = 99;
		byte ret_content[] = new byte[128];

		if (message.length() > 160) {
			this.ret_message = "msg_content > max limit!";
			return 80;
		}

		try {
			int i;
			for (i = 0; i < 258; i++)
				out_buffer[i] = 0;
			for (i = 0; i < 128; i++)
				ret_content[i] = 0;

			String msg_content = sms_tel.trim() + "\0" + message.trim() + "\0";
			byte msg_content_byte[] = msg_content.getBytes("Big5");
			int msg_content_size = msg_content_byte.length;

			String send_time = order_time.trim() + "\0";
			byte send_time_byte[] = send_time.getBytes();
			int send_time_size = send_time_byte.length;

			out_buffer[0] = 0;
			out_buffer[1] = 1;
			out_buffer[2] = (byte) (msg_content_size + 2 + send_time_size);
			for (i = 0; i < msg_content_size; i++)
				out_buffer[i + 3] = msg_content_byte[i];

			out_buffer[msg_content_size + 3] = 101;
			out_buffer[msg_content_size + 4] = 0;

			for (i = 0; i < send_time_size; i++)
				out_buffer[msg_content_size + 5 + i] = send_time_byte[i];

			this.dout.write(out_buffer);

			ret_code = this.din.readByte();

			this.din.read(ret_content, 0, 128);
			this.ret_message = new String(ret_content);
			this.ret_message = this.ret_message.trim();
			return ret_code;

		} catch (UnknownHostException eu) {
			this.ret_message = "Cannot find the host!";
			return 70;
		} catch (IOException ex) {
			this.ret_message = " Socket Error: " + ex.getMessage();
			return 71;
		}
	}

	public int query_message(String sms_tel, String messageid) {

		byte out_buffer[] = new byte[258];

		byte ret_code = 99;
		byte ret_content[] = new byte[128];

		try {
			int i;
			for (i = 0; i < 258; i++)
				out_buffer[i] = 0;
			for (i = 0; i < 128; i++)
				ret_content[i] = 0;

			String msg_content = sms_tel.trim() + "\0" + messageid.trim()
					+ "\0";
			byte msg_content_byte[] = msg_content.getBytes();
			int msg_content_size = msg_content_byte.length;

			out_buffer[0] = 0;
			out_buffer[1] = 2;
			out_buffer[2] = (byte) (msg_content_size);

			for (i = 0; i < msg_content_size; i++)
				out_buffer[i + 3] = msg_content_byte[i];

			this.dout.write(out_buffer);

			ret_code = this.din.readByte();

			this.din.read(ret_content, 0, 128);
			this.ret_message = new String(ret_content);
			this.ret_message = this.ret_message.trim();
			return ret_code;

		} catch (UnknownHostException eu) {
			this.ret_message = "Cannot find the host!";
			return 70;
		} catch (IOException ex) {
			this.ret_message = " Socket Error: " + ex.getMessage();
			return 71;
		}

	}

	public String get_message() {

		return ret_message;
	}

	public static void main(String[] args) throws Exception {

		try {
			String server = "203.66.172.131";
			int port = 8000;

			if (args.length < 4) {
				System.out
						.println("Use: java sock2air id passwd tel url message");
				System.out
						.println(" Ex: java sock2air test test123 0910123xxx HiNet²�T!");
				return;
			}
			String user = args[0];
			String passwd = args[1];
			String tel = args[2];
			String message = new String(args[3].getBytes(), "big5");

			sock2air mysms = new sock2air();
			int ret_code = mysms.create_conn(server, port, user, passwd);
			if (ret_code == 0) {
				System.out.println("�b���K�XLogin OK!");
			} else {
				System.out.println("�b���K�XLogin Fail!");
				System.out.println("ret_code=" + ret_code + ",ret_content="
						+ mysms.get_message());

				mysms.close_conn();
				return;
			}

			ret_code = mysms.send_message(tel, message);
			if (ret_code == 0) {
				System.out.println("²�T�w�e��²�T����!");
				System.out.println("MessageID=" + mysms.get_message());
			} else {
				System.out.println("²�T�ǰe�o�Ϳ�~!");
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
