package tn.enit.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.util.Map;

public class ReclamationHandler implements JobHandler {
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();

        // Extract required data from the variables
        final String productName = (String) inputVariables.get("textfield_product_name");
        final Integer number_quantity = (Integer) inputVariables.get("number_quantity");
        final String description = (String) inputVariables.get("textarea_description");


        // Perform data registration logic
        // For demonstration purposes, we'll simply print the data
        System.out.println("Afficher les détailles de reclamation");
        System.out.println("Nom du Produit: " + productName);
        System.out.println("quantité: " + number_quantity);
        System.out.println("description: " + description);



        // Complete the job
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
