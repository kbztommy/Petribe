package za101g2.tool;

public class RandomCode {
	
	public String randomCode(Integer length){
		String randomCode = "";
		for(int i=0;i<length;i++){
		int code = (int) (Math.random()*10);
		randomCode = randomCode + code;
		}
		return randomCode;
	}

}
