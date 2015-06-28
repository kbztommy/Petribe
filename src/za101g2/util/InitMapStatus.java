package za101g2.util;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet(urlPatterns={"/InitMapStatus"},loadOnStartup = 1)
public class InitMapStatus extends HttpServlet {
   
	public void init() throws ServletException {
		ServletContext context = getServletContext();
		
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat dateFormater1 = new SimpleDateFormat("yyyy年MM月dd日 h:mm a");		
		context.setAttribute("dateFormater", dateFormater);	
		context.setAttribute("dateFormater1", dateFormater1);
		
		HashMap<String,String> serviceListMap = new HashMap<String,String>();
		serviceListMap.put("y","上架");
		serviceListMap.put("n", "下架");
		serviceListMap.put("perTime", "每次");
		serviceListMap.put("perDay", "每天");
		context.setAttribute("serviceListMap", serviceListMap);	
		
		HashMap<Integer,String> storeCalendarMap = new HashMap<Integer,String>();
		storeCalendarMap.put(0,"一月份");
		storeCalendarMap.put(1,"二月份");
		storeCalendarMap.put(2, "三月份");
		storeCalendarMap.put(3, "四月份");
		storeCalendarMap.put(4, "五月份");
		storeCalendarMap.put(5, "六月份");
		storeCalendarMap.put(6, "七月份");
		storeCalendarMap.put(7, "八月份");
		storeCalendarMap.put(8, "九月份");
		storeCalendarMap.put(9, "十月份");
		storeCalendarMap.put(10, "十一月份");
		storeCalendarMap.put(11, "十二月份");		
		context.setAttribute("storeCalendarMap", storeCalendarMap);	
		
		ArrayList<String> cityList = new ArrayList<String>();
		cityList.add("基隆市");
		cityList.add("台北市");
		cityList.add("新北市");
		cityList.add("桃園市");
		cityList.add("新竹縣市");
		cityList.add("苗栗縣");
		cityList.add("台中市");
		cityList.add("南投縣市");
		cityList.add("彰化縣市");
		cityList.add("雲林縣市");
		cityList.add("嘉義縣市");
		cityList.add("台南市");
		cityList.add("高雄市");
		cityList.add("屏東縣市");
		cityList.add("台東縣");
		cityList.add("花蓮縣市");
		context.setAttribute("cityList", cityList);	
		
		HashMap<String,String> orderBoardMapforC = new HashMap<String,String>();
		orderBoardMapforC.put("0","建立訂單中");
		orderBoardMapforC.put("1", "尚未寄養");
		orderBoardMapforC.put("2", "寄養中");
		orderBoardMapforC.put("3", "寄養結束，等待確認");
		orderBoardMapforC.put("4", "寄養結束，確認無誤");
		orderBoardMapforC.put("5", "寄養結束，確認無誤");
		orderBoardMapforC.put("6", "檢舉審查中");	
		orderBoardMapforC.put("7", "檢舉審核不通過");	
		orderBoardMapforC.put("8", "檢舉審核通過，客服處理中");	
		context.setAttribute("orderBoardMapforC", orderBoardMapforC);
		HashMap<String,String> orderBoardMapforS = new HashMap<String,String>();
		orderBoardMapforS.put("0","建立訂單中");
		orderBoardMapforS.put("1", "尚未代養");
		orderBoardMapforS.put("2", "代養中");
		orderBoardMapforS.put("3", "代養結束，等待付款");
		orderBoardMapforS.put("4", "代養結束，等待付款");
		orderBoardMapforS.put("5", "代養結束，已付款");
		orderBoardMapforS.put("6", "代養結束，等待付款");	
		orderBoardMapforS.put("7", "代養結束，已付款");	
		orderBoardMapforS.put("8", "訂單被檢舉，客服處理中");	
		context.setAttribute("orderBoardMapforS", orderBoardMapforS);
		
		HashMap<String,String> orderBoardMapforP = new HashMap<String,String>();
		orderBoardMapforP.put("0","建立訂單中");
		orderBoardMapforP.put("1", "尚未代養");
		orderBoardMapforP.put("2", "代養中");
		orderBoardMapforP.put("3", "代養結束，等待付款");
		orderBoardMapforP.put("4", "代養結束，等待付款");
		orderBoardMapforP.put("5", "代養結束，已付款");
		orderBoardMapforP.put("6", "未審核");	
		orderBoardMapforP.put("7", "檢舉不成立");	
		orderBoardMapforP.put("8", "檢舉成立");	
		context.setAttribute("orderBoardMapforP", orderBoardMapforP);
		
		HashMap<String,String> replyStatusMap = new HashMap<String,String>();
		replyStatusMap.put("y", "寵物主人確認找到");
		replyStatusMap.put("n", "寵物主人尚未確認");
		replyStatusMap.put("r", "回報已被檢舉");
		replyStatusMap.put("d", "已被封鎖回報功能");
		replyStatusMap.put("w", "等待發放賞金");
		replyStatusMap.put("b", "賞金已完成發放");
		context.setAttribute("replyStatusMap", replyStatusMap);
		
		HashMap<String,String> managerReplyStatusMap = new HashMap<String,String>();
		managerReplyStatusMap.put("y", "尚未填寫帳戶資訊");
		managerReplyStatusMap.put("w", "已填寫完成");
		context.setAttribute("managerReplyStatusMap", managerReplyStatusMap);
		
		HashMap<String,String> reportStatusMap = new HashMap<String,String>();
		reportStatusMap.put("r", "等待審核");
		reportStatusMap.put("d", "已封鎖");
		context.setAttribute("reportStatusMap", reportStatusMap);
		
		LinkedHashMap<String,String> cityLatlng = new LinkedHashMap<String,String>();
		cityLatlng.put("基隆市", "25.128, 121.739");
		cityLatlng.put("台北市", "25.033, 121.565");
		cityLatlng.put("新北市", "25.017, 121.463");
		cityLatlng.put("桃園市", "24.994, 121.301");
		cityLatlng.put("新竹縣市", "24.839, 121.018");
		cityLatlng.put("苗栗縣", "24.560, 120.821");
		cityLatlng.put("台中市", "24.148, 120.674");
		cityLatlng.put("南投縣市", "23.918, 120.678");
		cityLatlng.put("彰化縣市", "24.072, 120.562");
		cityLatlng.put("雲林縣市", "23.720, 120.440");
		cityLatlng.put("嘉義縣市", "23.464, 120.247");
		cityLatlng.put("台南市", "23.000, 120.227");
		cityLatlng.put("高雄市", "22.627, 120.301");
		cityLatlng.put("屏東縣市", "22.387, 120.599");
		cityLatlng.put("台東縣", "22.797, 121.071");
		cityLatlng.put("花蓮縣市", "23.991, 121.611");
		context.setAttribute("cityLatlng", cityLatlng);
		
		HashMap<String, String> storeApply = new HashMap<String, String>();
		storeApply.put("1", "未審核");
		storeApply.put("2", "複審");
		storeApply.put("3", "審核未通過");
		storeApply.put("4", "審核通過");
		context.setAttribute("storeApply", storeApply);
		
		HashMap<String, String> storeApplyButton = new HashMap<String, String>();
		storeApplyButton.put("1", "btn-info");
		storeApplyButton.put("2", "btn-warning");
		storeApplyButton.put("3", "btn-danger");
		storeApplyButton.put("4", "btn-success");
		context.setAttribute("storeApplyButton", storeApplyButton);
		
		HashMap<String, String> sex = new HashMap<String, String>();
		sex.put("1", "男性");
		sex.put("0", "女性");
		sex.put("2", "不公開");
		context.setAttribute("sex", sex);
		
		HashMap<String, String> memberStatus = new HashMap<String, String>();
		memberStatus.put("-3", "已通過商家認證（帳號已被凍結）");
		memberStatus.put("-2", "已通過手機認證（帳號已被凍結）");
		memberStatus.put("-1", "已通過信箱認證（帳號已被凍結）");
		memberStatus.put("0", "未通過認證");
		memberStatus.put("1", "已通過信箱認證");
		memberStatus.put("2", "已通過手機認證");
		memberStatus.put("3", "已通過商家認證");
		context.setAttribute("memberStatus", memberStatus);
		
		HashMap<String, String> memberStatus2Way = new HashMap<String, String>();
		memberStatus2Way.put("3", "凍結");
		memberStatus2Way.put("2", "凍結");
		memberStatus2Way.put("1", "凍結");
		memberStatus2Way.put("0", "無權");
		memberStatus2Way.put("-1", "解除");
		memberStatus2Way.put("-2", "解除");
		memberStatus2Way.put("-3", "解除");
		context.setAttribute("memberStatus2Way", memberStatus2Way);
		
		HashMap<String, String> memberStatus2WayClass = new HashMap<String, String>();
		memberStatus2WayClass.put("3", "btn-danger");
		memberStatus2WayClass.put("2", "btn-danger");
		memberStatus2WayClass.put("1", "btn-danger");
		memberStatus2WayClass.put("0", "btn-info disabled");
		memberStatus2WayClass.put("-1", "btn-success");
		memberStatus2WayClass.put("-2", "btn-success");
		memberStatus2WayClass.put("-3", "btn-success");
		context.setAttribute("memberStatus2WayClass", memberStatus2WayClass);
		
		
		HashMap<String, String> staffStatus = new HashMap<String, String>();
		staffStatus.put("0", "離職");
		staffStatus.put("1", "在職");
		staffStatus.put("2", "受訓");
		context.setAttribute("staffStatus", staffStatus);
		
		HashMap<String, String> staffStatusButton = new HashMap<String, String>();
		staffStatusButton.put("0", "btn-danger");
		staffStatusButton.put("1", "btn-success");
		staffStatusButton.put("2", "btn-warning");
		context.setAttribute("staffStatusButton", staffStatusButton);
	}	

}
