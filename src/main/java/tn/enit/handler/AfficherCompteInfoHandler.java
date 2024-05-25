package tn.enit.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import tn.enit.service.NotifySignIn;

import java.util.HashMap;
import java.util.Map;

public class AfficherCompteInfoHandler implements JobHandler {
    NotifySignIn notifySignIn=new NotifySignIn();


    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        final Map<String, Object> inputVariables = job.getVariablesAsMap();
        final String userName = (String) inputVariables.get("textfield_NomComplet");
        final String email = (String) inputVariables.get("textfield_Email");
        final String confirmation = notifySignIn.success(userName);
        final Map<String,Object> outputVariables = new HashMap<String,Object>();
        outputVariables.put("confirmation",confirmation);
        client.newCompleteCommand(job.getKey()).variables(outputVariables).send().join();

    }
}
