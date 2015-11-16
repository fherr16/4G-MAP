package application;

public class Website {
	
	private String name;
	private String pass;
	
	public Website(String webName, String webPass){
		name = webName;
		pass = webPass;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPass(){
		return pass;
	}
}
