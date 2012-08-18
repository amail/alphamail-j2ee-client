package com.comfirm.alphamail.helloworld;
/*
 * Hello World-message with data that we've defined in our template
 */
public class HelloWorldMessage
{
	public String Message;
	/*
	 * Represents the <# payload.Message #> in our template
	 */
    private String _message;

    /*
     * Represents the <# payload.someOtherMessage #> in our template
     */
    private String _someOtherMessage;
    
    public HelloWorldMessage(){}
    
    public HelloWorldMessage(String message, String someOtherMessage){
    	this.setMessage(message);
    	this.setSomeOtherMessage(someOtherMessage);
    }
	
    public String getMessage(){
		return this._message;
	}
    
    public void setMessage(String message){
		this._message = message;
	}
    
    public String getSomeOtherMessage(){
		return this._someOtherMessage;
	}
    
    public void setSomeOtherMessage(String otherMessage){
    	this._someOtherMessage = otherMessage;
    }
}