package org.streampipes.examples.jvm.sink.dashboard;

import org.streampipes.examples.jvm.config.PeJvmConfig;
import org.streampipes.model.graph.DataSinkInvocation;
import org.streampipes.model.schema.EventSchema;
import org.streampipes.wrapper.params.binding.EventSinkBindingParams;

public class DashboardParameters extends EventSinkBindingParams {
    private String pipelineId;
    private EventSchema schema;
    private String broker;

    public DashboardParameters(DataSinkInvocation invocationGraph) {
        super(invocationGraph);
        this.schema = invocationGraph.getInputStreams().get(0).getEventSchema();
        this.pipelineId = invocationGraph.getCorrespondingPipeline();
        this.broker = "ws://" + PeJvmConfig.INSTANCE.getNginxHost() +":" +PeJvmConfig.INSTANCE
                .getNginxPort()
                +"/streampipes/ws";
    }

    private String removeProtocol(String url) {
       return url.replaceFirst("^(tcp://|ws://)","");
    }
    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public EventSchema getSchema() {
        return schema;
    }

    public void setSchema(EventSchema schema) {
        this.schema = schema;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }
}
