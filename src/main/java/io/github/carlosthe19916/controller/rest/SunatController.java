package io.github.carlosthe19916.controller.rest;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@ApplicationScoped
@Path("/")
public class SunatController {

    public static final String SUNAT_QUEUE = "/jms/queue/SunatQueue";

    @Inject
    private JMSContext context;

    @Resource(lookup = SUNAT_QUEUE)
    private Queue queue;

    @GET
    @Produces("text/plain")
    public String get() {
        context.createProducer().send(queue, "1526310939809");
        return "Howdy!";
    }

}
