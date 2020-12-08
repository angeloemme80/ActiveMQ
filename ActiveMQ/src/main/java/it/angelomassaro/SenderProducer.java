package it.angelomassaro;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SenderProducer {

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
		SenderProducer senderProducer = new SenderProducer();
		boolean inviato = senderProducer.sendMessage(" - Ecco il mio messaggio verso la coda!!!");
		//System.out.println(inviato);
		
		//String messaggio = senderProducer.readMessage();
		//System.out.println(messaggio);
	}
	
	
	public boolean sendMessage(String testoDelMessaggio) {
		
		try {
			
			connectionFactory = new ActiveMQConnectionFactory(this.username,this.password,this.brokerUrl);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(this.queueName);
			//destination = session.createTopic(this.queueName);
			messageProducer = session.createProducer(this.destination);
			
			for (int i = 0; i < 10; i++) {
				TextMessage message = session.createTextMessage();
				message.setText(testoDelMessaggio + " numero: " + i);
				messageProducer.send(message);
			}
			System.out.println("FINE!");
			
		} catch (JMSException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				session.close();
				connection.stop();
				connection.close();
				connectionFactory = null;
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
		}
		
		return true;
		
		
	}
	
	public String readMessage() {
		
		String messaggio = null;
		
		try {
			
			connectionFactory = new ActiveMQConnectionFactory(this.username,this.password,this.brokerUrl);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(this.queueName);
			MessageConsumer messageConsumer = session.createConsumer(this.destination);
			TextMessage message = (TextMessage) messageConsumer.receive();
			messaggio = message.getText();
			
		} catch (JMSException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				session.close();
				connection.stop();
				connection.close();
				connectionFactory = null;
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
		}
		
		return messaggio;
		
		
	}

}
