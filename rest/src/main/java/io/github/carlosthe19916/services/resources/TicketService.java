package io.github.carlosthe19916.services.resources;

import io.github.carlosthe19916.sender.model.SenderConfig;
import io.github.carlosthe19916.sender.service.BillService;
import io.github.carlosthe19916.services.idm.TicketRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/ticket")
@ApplicationScoped
public class TicketService {

    @Inject
    private BillService sunatService;

    @POST
    @Produces("text/plain")
    public void checkTicket(TicketRepresentation rep) throws JMSException {
        SenderConfig sendConfig = new SenderConfig.Builder()
                .endpoint(rep.getEndpoint())
                .username(rep.getUsername())
                .password(rep.getPassword())
                .build();

        sunatService.getStatus(sendConfig, rep.getTicket());
    }

}