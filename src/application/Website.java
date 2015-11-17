package application;

public class Website {
	
	private String name;
	private String pass;
	
	public Website(String webName, String something){
		name = webName;
		pass = something;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPass(){
		return pass;
	}
}
