package it.angelomassaro;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class Consumer {

	private ConnectionFactory connectionFactory = null;
	private String username = "admin";
	private String password = "admin";
	private String brokerUrl = "tcp://192.168.1.98:61616";
	private String queueName = "HelloWorldQ";
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageProducer messageProducer = null;
	
	public static void main(String[] args) {
		Consumer consumer = new Consumer();
		String messaggio = consumer.readMessage();
		System.out.println(messaggio);
	}
	
public String readMessage() {
		
		String messaggio = null;
		
		try {
			
			connectionFactory = new ActiveMQConnectionFactory(this.username,this.password,this.brokerUrl);
			connection = connectionFactory.createConnection();
			connection.start();
			//session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			destination = session.createQueue(this.queueName);
			//destination = session.createTopic(this.queueName);
			
			MessageConsumer messageConsumer = session.createConsumer(this.destination);
			Listener myListener = new Listener();
			messageConsumer.setMessageListener(myListener);
			
			
		} catch (JMSException e) {
			e.printStackTrace();
			return null;
		} finally {
			/*try {
				session.close();
				connection.stop();
				connection.close();
				connectionFactory = null;
			} catch (JMSException e) {
				e.printStackTrace();
			}*/
			
		}
		
		return messaggio;
		
		
	}

}
