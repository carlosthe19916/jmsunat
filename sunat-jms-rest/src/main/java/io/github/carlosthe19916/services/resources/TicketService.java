package io.github.carlosthe19916.services.resources;

import io.github.carlosthe19916.model.SendConfig;
import service.SunatService;
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
    private SunatService sunatService;

    @POST
    @Produces("text/plain")
    public void checkTicket(TicketRepresentation rep) throws JMSException {
        SendConfig sendConfig = new SendConfig.Builder()
                .endpoint(rep.getEndpoint())
                .username(rep.getUsername())
                .password(rep.getPassword())
                .build();

        sunatService.checkTicket(sendConfig, rep.getRuc(), rep.getTicket());
    }

}