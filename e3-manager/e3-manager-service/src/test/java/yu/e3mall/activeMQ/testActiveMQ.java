package yu.e3mall.activeMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class testActiveMQ {
	@Test
	public void testQueueProducer() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("test-queue");
		MessageProducer messageProducer = session.createProducer(queue);
		TextMessage textMessage = session.createTextMessage("hello activema");
		messageProducer.send(textMessage);
		messageProducer.close();
		session.close();
		connection.close();
	}
	@Test
	public void testQueueConsumer() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("test-queue");
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				TextMessage textMessage = (TextMessage) message;
				String text = "";
				try {
					text = textMessage.getText();
					System.out.println(text);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		System.in.read();
		consumer.close();
		session.close();
		connection.close();
	}
	@Test
	public void testTopicProducer() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("topic-test");
		MessageProducer messageProducer = session.createProducer(topic);
		TextMessage textMessage = session.createTextMessage("hello activema topic");
		messageProducer.send(textMessage);
		messageProducer.close();
		session.close();
		connection.close();
	}
	@Test
	public void testTopicConsumer() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("topic-test");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				TextMessage textMessage = (TextMessage) message;
				String text = "";
				try {
					text = textMessage.getText();
					System.out.println(text);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		System.out.println("消费者3启动。。。。");
		System.in.read();
		consumer.close();
		session.close();
		connection.close();
	}
}
