import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.*;
public class RabbitMQGenerador 
{
    public void GeneradorJava(String mensaje) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("paralelo");
        factory.setPassword("uaz2019");
        factory.setVirtualHost("computacion");
        factory.setHost("192.168.0.16");
        factory.setPort(5672);
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare("queue_paralelo", false, false, false, null);
        byte[] messageBodyBytes = mensaje.getBytes();
        channel.basicPublish("", "queue_paralelo", null, messageBodyBytes);
    }
}