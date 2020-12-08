package it.angelomassaro;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Listener implements MessageListener {

	public void onMessage(Message message) {
		System.out.println("DENTRO onMessage" + this.getClass().hashCode());
		try {
			System.out.println( ((TextMessage)message).getText() );
			
			//Se imposto la sessione come Session.AUTO_ACKNOWLEDGE allora devo indicare al server il mio acknowledge tramite message.acknowledge();
			message.acknowledge();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
