package tn.enit.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.util.Map;

public class RegisterHandler implements JobHandler {
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        // Retrieve variables from the job
        final Map<String, Object> inputVariables = job.getVariablesAsMap();

        // Extract required data from the variables
        final String userName = (String) inputVariables.get("textfield_name");
        final String password = (String) inputVariables.get("textfield_password");
        final String email = (String) inputVariables.get("textfield_Email");
        final String confirmPassword = (String) inputVariables.get("textfield_confirm");

        // Perform data registration logic
        // For demonstration purposes, we'll simply print the data
        System.out.println("Enregister les données importantes du client:");
        System.out.println("Nom: " + userName);
        System.out.println("email: " + email);
        System.out.println("mot de pass: " + password);



        // Complete the job
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
