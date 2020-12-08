package it.angelomassaro.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicProducer {

	private ConnectionFactory connectionFactory = null;
	private String username = "admin";
	private String password = "admin";
	private String brokerUrl = "tcp://192.168.1.98:61616";
	private String queueName = "topic001";
	private Connection connection = null;
	private Session session = null;
	private MessageProducer messageProducer = null;
	
	/*
	 * Classe che invia messaggi sul topic001
	 */
	
	public static void main(String[] args) {
		TopicProducer topicProducer = new TopicProducer();
		boolean inviato = topicProducer.sendMessage("Ecco il mio messaggio verso la coda!!! : ");
	}
	
	
	public boolean sendMessage(String testoDelMessaggio) {
		
		try {
			
			connectionFactory = new ActiveMQConnectionFactory(this.username,this.password,this.brokerUrl);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(this.queueName);
			
			// Publish
            MessageProducer producer = session.createProducer(topic);
            for (int i = 0; i < 1; i++) {
            	Message msg = session.createTextMessage(testoDelMessaggio + i);
            	producer.send(msg);
			}
            
             
			
			
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

}
