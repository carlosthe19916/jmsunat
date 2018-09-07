package io.github.carlosthe19916.sunatjms.consumer.javaee.service;

import io.github.carlosthe19916.sunatjms.producer.javaee.qualifiers.JMSunatContext;
import io.github.carlosthe19916.sunatjms.producer.javaee.qualifiers.JMSunatQueue;
import io.github.carlosthe19916.sunatjms.producer.model.JMSBillMessageBean;
import io.github.carlosthe19916.sunatjms.producer.model.JMSSenderConfig;
import io.github.carlosthe19916.sunatjms.producer.service.JMSBillService;
import io.github.carlosthe19916.util.SenderConstants;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
public class DefaultBillService implements JMSBillService {

    @Inject
    @JMSunatContext
    private JMSContext context;

    @Inject
    @JMSunatQueue
    private Queue queue;

    @Override
    public void sendBill(JMSSenderConfig config, JMSBillMessageBean bean) throws JMSException {
        sendFile(config, bean, SenderConstants.SEND_BILL);
    }

    @Override
    public void getStatus(JMSSenderConfig config, String ticket) throws JMSException {
        TextMessage message = context.createTextMessage();
        message.setText(ticket);
        message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);

        message.setStringProperty(SenderConstants.WS_URL, config.getEndpoint());
        message.setStringProperty(SenderConstants.WS_OPERATION, SenderConstants.GET_STATUS);

        message.setStringProperty(SenderConstants.SUNAT_USERNAME, config.getUsername());
        message.setStringProperty(SenderConstants.SUNAT_PASSWORD, config.getPassword());

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }

    @Override
    public void sendSummary(JMSSenderConfig config, JMSBillMessageBean bean) throws JMSException {
        sendFile(config, bean, SenderConstants.SEND_SUMMARY);
    }

    @Override
    public void sendPack(JMSSenderConfig config, JMSBillMessageBean bean) throws JMSException {
        sendFile(config, bean, SenderConstants.SEND_PACK);
    }

    private void sendFile(JMSSenderConfig config, JMSBillMessageBean bean, String operation) throws JMSException {
        BytesMessage message = context.createBytesMessage();
        message.writeBytes(bean.getContentFile());
        message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);

        message.setStringProperty(SenderConstants.WS_URL, config.getEndpoint());
        message.setStringProperty(SenderConstants.WS_OPERATION, operation);

        message.setStringProperty(SenderConstants.SUNAT_USERNAME, config.getUsername());
        message.setStringProperty(SenderConstants.SUNAT_PASSWORD, config.getPassword());

        message.setStringProperty(SenderConstants.FILE_NAME, bean.getFileName());
        message.setStringProperty(SenderConstants.PARTY_TYPE, bean.getPartyType());

        JMSProducer producer = context.createProducer();
        producer.send(queue, message);
    }

//    @PostConstruct
//    public void runExample() {
//        Connection connection = null;
//        InitialContext initialContext = null;
//        try {
//            // Step 1. Create an initial context to perform the JNDI lookup.
//            Properties p = new Properties();
//            p.put("java.naming.factory.initial", "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
//            p.put("connectionFactory.ConnectionFactory", "tcp://localhost:61616");
//            p.put("queue.queue/exampleQueue", "exampleQueue");
//
//
//            initialContext = new InitialContext(p);
//
//
//            // lookup on the queue
//            Queue queue = (Queue) initialContext.lookup("queue/exampleQueue");
//
//            // lookup on the Connection Factory
//            ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
//
//            // Create an authenticated JMS Connection
//            connection = cf.createConnection("admin", "admin");
//
//            // Create a JMS Session
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            // Create a JMS Message Producer
//            MessageProducer producer = session.createProducer(queue);
//
//            // Create a Text Message
//            TextMessage message = session.createTextMessage("This is a text message");
//
//            System.out.println("Sent message: " + message.getText());
//
//            // Send the Message
//            producer.send(message);
//
//            // Create a JMS Message Consumer
//            MessageConsumer messageConsumer = session.createConsumer(queue);
//
//            // Start the Connection
//            connection.start();
//
//            // Receive the message
//            TextMessage messageReceived = (TextMessage) messageConsumer.receive(5000);
//
//            System.out.println("Received message: " + messageReceived.getText());
//
////            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // Be sure to close our JMS resources!
//                if (initialContext != null) {
//                    initialContext.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (NamingException | JMSException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
