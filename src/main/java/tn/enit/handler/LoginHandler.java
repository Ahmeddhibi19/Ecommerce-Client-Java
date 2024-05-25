package tn.enit.handler;

import java.util.Map;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class LoginHandler implements JobHandler {

	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {
		// Retrieve variables from the job
		final Map<String, Object> inputVariables = job.getVariablesAsMap();

		// Extract required data from the variables
		final String userName = (String) inputVariables.get("textfield_NomComplet");
		final String password = (String) inputVariables.get("textfield_password");

		// Perform data registration logic
		// For demonstration purposes, we'll simply print the data
		System.out.println("Afficher les données importantes du client:");
		System.out.println("Nom: " + userName);
		System.out.println("mot de pass: " + password);


		// Complete the job
		client.newCompleteCommand(job.getKey()).send().join();
	}
}
