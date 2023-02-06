import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;
import java.io.*;
import java.security.*;
import javax.net.ssl.*;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.net.URL;
import java.security.KeyStore;

public class ClienteJAVA implements AutoCloseable {

    private Connection conexion;
    private Channel canal;

    public ClienteJAVA() throws IOException, TimeoutException {
        try{
        //keytool -import -alias server1 -file C:/certsProyecto/server/server_certificate.pem -keystore C:/certsProyecto/store/rabbit
        char[] keyPassphrase = "uaz2019".toCharArray();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream("C:/certsProyecto/client/client_certificate.p12"), keyPassphrase);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, keyPassphrase);
        char[] trustPassphrase = "uaz2019".toCharArray();
        KeyStore tks = KeyStore.getInstance("JKS");
        tks.load(new FileInputStream("C:/certsProyecto/store/rabbitstore"), trustPassphrase);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(tks);
        SSLContext c = SSLContext.getInstance("TLSv1.2");
        c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        ConnectionFactory credencial = new ConnectionFactory();
        credencial.setHost("192.168.1.10");
        credencial.setVirtualHost("computacion");
        credencial.setUsername("paralelo");
        credencial.setPassword("uaz2019");
        credencial.setPort(5671);
        credencial.useSslProtocol(c);
        conexion = credencial.newConnection();
        canal = conexion.createChannel();
        } catch (Exception exc) {
        // TODO: handle exception
    }
}

    public String call(String mensaje, String nameQueue) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();
        String reponderANombreDeLaCola = canal.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(reponderANombreDeLaCola).build();
        canal.basicPublish("", nameQueue, props, mensaje.getBytes("UTF-8"));
        final BlockingQueue<String> respuesta = new ArrayBlockingQueue<>(1);
        String ctag = canal.basicConsume(reponderANombreDeLaCola, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                respuesta.offer(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });
        String result = respuesta.take();
        canal.basicCancel(ctag);
        return result;
    }
    public void close() throws IOException {
        conexion.close();
    }
}