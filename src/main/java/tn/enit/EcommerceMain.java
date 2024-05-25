package tn.enit;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.Scanner;


import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import tn.enit.handler.AfficherCompteInfoHandler;


public class EcommerceMain {
    private static final String ZEEBE_ADDRESS="5b39cb08-5e43-490c-8361-e4628213e0ab.bru-2.zeebe.camunda.io:443";
    private static final String ZEEBE_CLIENT_ID="Cg3B1-tDSTg~T4zdhJCPygqN3VHnc81b";
    private static final String ZEEBE_CLIENT_SECRET="ju33mklPnd6-clujfcGHCt~2l-PDySP4zU1tuIK7RvGPDZYKDi7w9z_2Zbob4Lg~";
    private static final String ZEEBE_AUTHORIZATION_SERVER_URL="https://login.cloud.camunda.io/oauth/token";
    private static final String ZEEBE_TOKEN_AUDIENCE="zeebe.camunda.io";
    private static final int WORKER_TIMEOUT = 10;

    public static void main(String[] args) throws IOException{


        final OAuthCredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
                        .audience(ZEEBE_TOKEN_AUDIENCE)
                        .clientId(ZEEBE_CLIENT_ID)
                        .clientSecret(ZEEBE_CLIENT_SECRET)
                        .build();

        try (final ZeebeClient client = ZeebeClient.newClientBuilder()
                .gatewayAddress(ZEEBE_ADDRESS)
                .credentialsProvider(credentialsProvider)
                .build()) {

            //Request the Cluster Topology
            System.out.println("Connected to: " + client.newTopologyRequest().send().join());


            //Send message Booking confirmation
            final JobWorker affichage =
                    client.newWorker()
                            .jobType("afficheCompteInfo")
                            .handler(new AfficherCompteInfoHandler())
                            .timeout(Duration.ofSeconds(WORKER_TIMEOUT).toMillis())
                            .open();

            //Send message Booking cancelled


            //Wait for the Workers
            Scanner sc = new Scanner(System.in);
            sc.nextInt();
            sc.close();
            affichage.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
