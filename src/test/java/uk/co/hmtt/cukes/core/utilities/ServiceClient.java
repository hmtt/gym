package uk.co.hmtt.cukes.core.utilities;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public final class ServiceClient {

    public static void callService(final String endPoint, final String email, final String passCode) throws IOException {
        final CredentialsProvider provider = new BasicCredentialsProvider();
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(email, passCode);
        provider.setCredentials(AuthScope.ANY, credentials);
        final HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        client.execute(new HttpGet(endPoint));
    }

}
