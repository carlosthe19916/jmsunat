package io.github.carlosthe19916.sunatjms.consumer.javaee.service;

import io.github.carlosthe19916.sunatjms.consumer.javaee.service.qualifiers.JMSunatContext;
import io.github.carlosthe19916.util.SenderConstants;
import io.github.carlosthe19916.webservices.managers.BillServiceManager;
import io.github.carlosthe19916.webservices.providers.BillServiceModel;
import io.github.carlosthe19916.webservices.wrappers.ServiceConfig;
import org.jboss.ejb3.annotation.ResourceAdapter;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;

@ResourceAdapter("remote-artemis-connection-factory")
@MessageDriven(name = "HelloWorldQueueMDB1", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/exampleQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class Consumer implements MessageListener {

    @Inject
    @JMSunatContext
    private JMSContext context;

    @Override
    public void onMessage(Message message) {
        try {
            Destination replyTo = message.getJMSReplyTo();

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String ticket = textMessage.getText();

                String url = message.getStringProperty(SenderConstants.WS_URL);
                String username = message.getStringProperty(SenderConstants.SUNAT_USERNAME);
                String password = message.getStringProperty(SenderConstants.SUNAT_PASSWORD);

                ServiceConfig config = new ServiceConfig.Builder()
                        .url(url)
                        .username(username)
                        .password(password)
                        .build();
                BillServiceModel result = BillServiceManager.getStatus(ticket, config);


                MapMessage responseMessage = context.createMapMessage();
                responseMessage.setInt("code", result.getCode());
                responseMessage.setBytes("cdr", result.getCdr());
                responseMessage.setString("ticket", result.getTicket());
                responseMessage.setString("description", result.getDescription());
                responseMessage.setString("status", result.getStatus().toString());

                JMSProducer producer = context.createProducer();
                producer.send(replyTo, responseMessage);
            } else {
                System.out.println("Message of wrong type: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

}
