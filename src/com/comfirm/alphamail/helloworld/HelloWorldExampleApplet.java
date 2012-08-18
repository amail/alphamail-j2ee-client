/*
The MIT License

Copyright (c) 2011 Comfirm <http://www.comfirm.se/>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package com.comfirm.alphamail.helloworld;
import java.applet.Applet;
import com.comfirm.alphamail.services.client.AlphaMailAuthorizationException;
import com.comfirm.alphamail.services.client.AlphaMailInternalException;
import com.comfirm.alphamail.services.client.AlphaMailService;
import com.comfirm.alphamail.services.client.AlphaMailServiceException;
import com.comfirm.alphamail.services.client.AlphaMailValidationException;
import com.comfirm.alphamail.services.client.DefaultAlphaMailService;
import com.comfirm.alphamail.services.client.entities.EmailContact;
import com.comfirm.alphamail.services.client.entities.EmailMessagePayload;
import com.comfirm.alphamail.services.client.entities.ServiceIdentityResponse;

public class HelloWorldExampleApplet extends Applet {
	private static final long serialVersionUID = 1L;
 
	public void init() {
		// Step #1: Let's start by entering the web service URL and the API-token you've been provided
		// If you haven't gotten your API-token yet. Log into Alpha Mail or contact support at 'support@comfirm.se'.
		AlphaMailService emailService = new DefaultAlphaMailService()
			.setServiceUrl("http://api.am1.comfirm.se/v1/")
			.setApiToken("YOUR-ACCOUNT-API-TOKEN-HERE");
	
        // Step #2: Let's fill in the gaps for the variables (stuff) we've used in our template
        HelloWorldMessage message = new HelloWorldMessage();
        message.setMessage("Hello world like a boss!");
        message.setSomeOtherMessage("And to the rest of the world! Chíkmàa! مرحبا! नमस्ते! Dumelang!");

        // Step #3: Let's set up everything that is specific for delivering this email
    	EmailMessagePayload payload = new EmailMessagePayload()
            .setProjectId(2) // The id of the project your want to send with
            .setSender(new EmailContact("Sender Company Name", "your-sender-email@your-sender-domain.com"))
            .setReceiver(new EmailContact("Joe E. Receiver", "email-of-receiver@comfirm.se"))
	    	.setBodyObject(message);
            
            try
            {
                // Step #4: Haven't we waited long enough. Let's send this!
                ServiceIdentityResponse response = emailService.queue(payload);
                
                // Step #5: Pop the champagné! We got here which mean that the request was sent successfully and the email is on it's way!
                this.consoleWrite("Successfully queued message with id '%s' (you can use this ID " +
            		"to get more details about the delivery)", response.getResult());
            }
            // Oh heck. Something went wrong. But don't stop here.
            // If you haven't solved it yourself. Just contact our brilliant support and they will help you.
            catch (AlphaMailValidationException exception)
            {
                // Example: Handle request specific error code here
                if (exception.getResponse().getErrorCode() == 3)
                {
                    // Example: Print a nice message to the user.
                }
                else
                {
                    // Something in the input was wrong. Probably good to double double-check!
                	this.consoleWrite("Validation error: %s (%s)", exception.getResponse().getMessage(),
            			exception.getResponse().getErrorCode());
                }
            }
            catch (AlphaMailAuthorizationException exception)
            {
                // Ooops! You've probably just entered the wrong API-token.
            	this.consoleWrite("Authentication error: %s (%s)", exception.getResponse().getMessage(),
        			exception.getResponse().getErrorCode());
            }
            catch (AlphaMailInternalException exception)
            {
                // Not that it is going to happen.. Right :-)
            	this.consoleWrite("Internal error: %s (%s)", exception.getResponse().getMessage(),
        			exception.getResponse().getErrorCode());
            }
            catch (AlphaMailServiceException exception)
            {
                // Most likely your internet connection that is down. We are covered for most things except "multi-data-center-angry-server-bashing-monkeys" (remember who coined it) or.. nuclear bombs.
                // If one blew. Well.. It's just likely that our servers are down.
        	this.consoleWrite("An error (probably related to connection) occurred: %s",
    			exception.getMessage());
        }
    
        // Writing to console like a boss
        this.consoleWrite("\n\tIn doubt or experiencing problems?\n" +
            "\tPlease email our support at 'support@comfirm.se'");
	}
	
	/*
	 * Helper method for writing a formatted message to console
	 */
	private void consoleWrite(String format, Object... args){
		System.out.print(String.format(format, args));
	}
}