package it.angelomassaro.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import it.angelomassaro.Listener;



public class TopicConsumer {
	
	private ConnectionFactory connectionFactory = null;
	private String username = "admin";
	private String password = "admin";
	private String brokerUrl = "tcp://192.168.1.98:61616";
	private String queueName = "topic001";
	private Connection connection = null;
	private Session session = null;
	private MessageProducer messageProducer = null;
	
	
	/*
	 * Classe crea un numero di Subscriber anche molto ampio (vedi ciclo for) sul topic001 e ne consuma i messaggi
	 */
	public static void main(String[] args) {
		TopicConsumer topicConsumer = new TopicConsumer();
		String messaggio = topicConsumer.readMessage();
		System.out.println(messaggio);
	}
	
	public String readMessage() {
		
		String messaggio = null;
		
		try {
			
			connectionFactory = new ActiveMQConnectionFactory(this.username,this.password,this.brokerUrl);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			for (int i = 0; i < 100; i++) {
				Topic topic = session.createTopic(this.queueName);
				MessageConsumer messageConsumer = session.createConsumer(topic);
				Listener myListener = new Listener();
				messageConsumer.setMessageListener(myListener);
			}
			
			System.out.println("Consumer FINITO!");
			
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
