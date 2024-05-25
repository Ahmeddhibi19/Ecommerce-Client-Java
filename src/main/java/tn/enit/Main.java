package tn.enit;

import java.time.Duration;

import tn.enit.handler.LoginHandler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class Main {
	
	//Zeebe Client Credentials
	private static final String ZEEBE_ADDRESS="5b39cb08-5e43-490c-8361-e4628213e0ab.bru-2.zeebe.camunda.io:443";
	private static final String ZEEBE_CLIENT_ID="Cg3B1-tDSTg~T4zdhJCPygqN3VHnc81b";
	private static final String ZEEBE_CLIENT_SECRET="ju33mklPnd6-clujfcGHCt~2l-PDySP4zU1tuIK7RvGPDZYKDi7w9z_2Zbob4Lg~";
	private static final String ZEEBE_AUTHORIZATION_SERVER_URL="https://login.cloud.camunda.io/oauth/token";
	private static final String ZEEBE_TOKEN_AUDIENCE="zeebe.camunda.io";
	
	//envoiERP Application Details
	//Process Variables
	private static final String VARIABLE_START_DATE = "startDate";
	private static final String VARIABLE_END_DATE = "endDate";
	private static final String VARIABLE_COMMENT = "textfield_comment";
	private static final String VARIABLE_DECISON = "radio_decision";
	private static final long WORKER_TIMEOUT = 10;
	private static final int WORKER_TIME_TO_LIVE = 10000;

	//Process Definition Details
	private static final String ENVOI_ERP_JOB_TYPE = "envoiERP";
	private static final String BPMN_PROCESS_ID = "Process_de_conge_annuel";


	
    public static void main(String[] args){
    	
    	final OAuthCredentialsProvider credentialsProvider =
    			new OAuthCredentialsProviderBuilder()
			    	.authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
			        .audience(ZEEBE_TOKEN_AUDIENCE)
			        .clientId(ZEEBE_CLIENT_ID)
			        .clientSecret(ZEEBE_CLIENT_SECRET)
			        .build();
	    	
		try (final ZeebeClient client =
		        ZeebeClient.newClientBuilder()
		            .gatewayAddress(ZEEBE_ADDRESS)
		            .credentialsProvider(credentialsProvider)
		            .build()) {
			
			//Request the Cluster Topology
			System.out.println("Connected to: " + client.newTopologyRequest().send().join());



			//Build the Start Process Variables
			//final Map<String, Object> variables = new HashMap<String, Object>();
			//variables.put(VARIABLE_START_DATE, "2024-04-27");
			//variables.put(VARIABLE_END_DATE, "2024-04-28");
			//variables.put(VARIABLE_COMMENT, "J'ai fini maintenant!!");
			//variables.put(VARIABLE_DECISON, "accepte");
			//variables.put(VARIABLE_CARD_CVC, "123");
			
			//Launch the Process Instance
			/*client.newCreateInstanceCommand()
			    .bpmnProcessId(BPMN_PROCESS_ID)
			    .latestVersion().variables(variables)
			    .send()
			    .join();*/
			
			//Start a Job Worker
			final JobWorker envoiERP_worker =
				    client.newWorker()
				        .jobType(ENVOI_ERP_JOB_TYPE)
				        .handler(new LoginHandler())
				        .timeout(Duration.ofSeconds(WORKER_TIMEOUT).toMillis())
				        .open();
			
			//Wait for the Workers
			Thread.sleep(WORKER_TIME_TO_LIVE);
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
}
