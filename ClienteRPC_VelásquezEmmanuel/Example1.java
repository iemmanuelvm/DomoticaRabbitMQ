import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.net.URL;
import java.security.KeyStore;

public class Example1 {
    //Certificado de cliente como formato de archivo pkcs # 12.
    private static final String CLIENT_CERTIFICATE = "keycert.p12";
    //Contraseña del cliente del certificado. Esta información debe almacenarse de forma segura !!!!
    private static final String CLIENT_PASSWORD_CERTIFICATE = "MySecretPassword";
    //Formato de archivo dado de certificados de cliente.
    private static final String KEYSTORE_CLIENT = "PKCS12";
    //Certificado de servidor como almacén de claves java.
    private static final String SERVER_CERTIFICATE = "rabbitstore.jks";
    //Contraseña del almacén de claves de Java. Esta información debe almacenarse de forma segura !!!!
    private static final String SERVER_CERTIFICATE_PASSWORD = "rabbitstore";
    //Tipo de almacén de claves Java.
    private static final String SERVER_CERTIFICATE_TYPE = "JKS";
    //Versión TLS que se debe utilizar.
    private static final String TLS_TYPE = "TLSv1.2";
    //Rabbitmq servidor ip.
    private static final String RABBIT_MQ_HOST = "127.0.0.1";
    //Rabbitmq puerto para escuchar.
    private static final int RABBIT_MQ_PORT = 5671;
    //Usuario Rabbitmq para iniciar sesión.
    private static final String RABBIT_MQ_USER = "admin";
    //Contraseña del usuario rabbitmq. Esta información debe almacenarse de forma segura !!!!
    private static final String RABBIT_MQ_PASSWORD = "admin";
    //Ejemplo de canal Rabbitmq para enviar y recibir un mensaje.
    private static final String RABBIT_MQ_CHANNEL = "rabbitmq-java-ssl-test";

    public static void main(String[] args) throws Exception
    {
        URL serverCertificate = ClassLoader.getSystemClassLoader().getResource(SERVER_CERTIFICATE);
        URL clientCertificate = ClassLoader.getSystemClassLoader().getResource(CLIENT_CERTIFICATE);
        char[] keyPassphrase = CLIENT_PASSWORD_CERTIFICATE.toCharArray();
        KeyStore ks = KeyStore.getInstance(KEYSTORE_CLIENT);
        assert clientCertificate != null;
        ks.load(new FileInputStream(clientCertificate.getFile()), keyPassphrase);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, keyPassphrase);
        char[] trustPassphrase = SERVER_CERTIFICATE_PASSWORD.toCharArray();
        KeyStore tks = KeyStore.getInstance(SERVER_CERTIFICATE_TYPE);
        assert serverCertificate != null;
        tks.load(new FileInputStream(serverCertificate.getFile()), trustPassphrase);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(tks);
        SSLContext c = SSLContext.getInstance(TLS_TYPE);
        c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RABBIT_MQ_HOST);
        factory.setPort(RABBIT_MQ_PORT);
        factory.setUsername(RABBIT_MQ_USER);
        factory.setPassword(RABBIT_MQ_PASSWORD);
        factory.useSslProtocol(c);
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        //non-durable, exclusive, auto-delete queue
        channel.queueDeclare(RABBIT_MQ_CHANNEL, false, true, true, null);
        channel.basicPublish("", RABBIT_MQ_CHANNEL, null, "Hello SSL World :-)".getBytes());
        GetResponse chResponse = channel.basicGet(RABBIT_MQ_CHANNEL, false);
        if(chResponse == null) {
            System.out.println("No message retrieved");
        } else {
            byte[] body = chResponse.getBody();
            System.out.println("Received: " + new String(body));
        }
        channel.close();
        conn.close();
    }
}