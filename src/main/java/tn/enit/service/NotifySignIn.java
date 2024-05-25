package tn.enit.service;

import java.util.Date;

public class NotifySignIn {

    public String success(String userName) {
	    String	confirmation ="welcome "+userName;
    	//Output the Process Variables
		System.out.println(confirmation);

		return confirmation;
    }
}