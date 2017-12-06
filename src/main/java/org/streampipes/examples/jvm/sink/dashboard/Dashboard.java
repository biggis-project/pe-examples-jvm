package org.streampipes.examples.jvm.sink.dashboard;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.streampipes.commons.exceptions.SpRuntimeException;
import org.streampipes.dataformat.json.JsonDataFormatDefinition;
import org.streampipes.examples.jvm.config.PeJvmConfig;
import org.streampipes.messaging.jms.ActiveMQPublisher;
import org.streampipes.model.graph.DataSinkInvocation;
import org.streampipes.serializers.json.GsonSerializer;
import org.streampipes.wrapper.runtime.EventSink;

import java.util.Map;

public class Dashboard implements EventSink<DashboardParameters> {

    private ActiveMQPublisher publisher;
    private JsonDataFormatDefinition jsonDataFormatDefinition;

    private static String DB_NAME = "visualizablepipeline";
    private static int DB_PORT = PeJvmConfig.INSTANCE.getCouchDbPort();
    private static String DB_HOST = PeJvmConfig.INSTANCE.getCouchDbHost();
    private static String DB_PROTOCOL = "http";


    private String visualizationId;
    private String visualizationRev;
    private String pipelineId;


    public Dashboard() {
        this.jsonDataFormatDefinition = new JsonDataFormatDefinition();

    }

    @Override
    public void bind(DashboardParameters parameters) throws SpRuntimeException {
        if (!saveToCouchDB(parameters.getGraph())) {
            throw new SpRuntimeException("The schema couldn't be stored in the couchDB");
        }
        this.publisher = new ActiveMQPublisher(PeJvmConfig.INSTANCE.getJmsUrl(), parameters.getGraph().getCorrespondingPipeline
                ());
        this.pipelineId = parameters.getPipelineId();
    }

    @Override
    public void onEvent(Map<String, Object> event, String sourceInfo) {
        try {
            publisher.publish(jsonDataFormatDefinition.fromMap(event));
        } catch (SpRuntimeException e) {
            e.printStackTrace();
        }
    }

    private boolean saveToCouchDB(DataSinkInvocation invocationGraph) {
        CouchDbClient dbClient = new CouchDbClient(new CouchDbProperties(DB_NAME, true, DB_PROTOCOL, DB_HOST, DB_PORT, null, null));
        DataSinkInvocation inv = new DataSinkInvocation(invocationGraph);
        dbClient.setGsonBuilder(GsonSerializer.getGsonBuilder());
        org.lightcouch.Response res = dbClient.save(DashboardModel.from(new DashboardParameters(inv)));

        if (res.getError() == null) {
            visualizationId = res.getId();
            visualizationRev = res.getRev();
        }

        return res.getError() == null;
    }

    private boolean removeFromCouchDB() {
        CouchDbClient dbClient = new CouchDbClient(new CouchDbProperties(DB_NAME, true, DB_PROTOCOL, DB_HOST, DB_PORT, null, null));
        org.lightcouch.Response res = dbClient.remove(visualizationId, visualizationRev);

        return res.getError() == null;
    }

    @Override
    public void discard() throws SpRuntimeException {
        this.publisher.disconnect();
        if (!removeFromCouchDB()) {
            throw new SpRuntimeException("There was an error while deleting pipeline: '" + pipelineId + "'");
        }
    }
}
