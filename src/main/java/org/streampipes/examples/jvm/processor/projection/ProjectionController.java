package org.streampipes.examples.jvm.processor.projection;

import org.streampipes.model.DataProcessorType;
import org.streampipes.model.graph.DataProcessorDescription;
import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.model.schema.EventProperty;
import org.streampipes.sdk.builder.ProcessingElementBuilder;
import org.streampipes.sdk.helpers.EpRequirements;
import org.streampipes.sdk.helpers.OutputStrategies;
import org.streampipes.sdk.helpers.SupportedFormats;
import org.streampipes.sdk.helpers.SupportedProtocols;
import org.streampipes.wrapper.ConfiguredEventProcessor;
import org.streampipes.wrapper.runtime.EventProcessor;
import org.streampipes.wrapper.standalone.declarer.StandaloneEventProcessingDeclarer;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectionController extends StandaloneEventProcessingDeclarer<ProjectionParameters> {

  @Override
  public DataProcessorDescription declareModel() {
    return ProcessingElementBuilder.create("project", "Projection", "Outputs a selectable subset of an input event type")
            .category(DataProcessorType.TRANSFORM)
            .requiredPropertyStream1(EpRequirements.anyProperty())
            .outputStrategy(OutputStrategies.custom())
            .supportedFormats(SupportedFormats.jsonFormat())
            .supportedProtocols(SupportedProtocols.jms(), SupportedProtocols.kafka())
            .build();
  }

  @Override
  public ConfiguredEventProcessor<ProjectionParameters, EventProcessor<ProjectionParameters>>
  onInvocation(DataProcessorInvocation graph) {

    List<String> outputKeys = graph
            .getOutputStream()
            .getEventSchema()
            .getEventProperties()
            .stream()
            .map(EventProperty::getRuntimeName)
            .collect(Collectors.toList());

    ProjectionParameters staticParam = new ProjectionParameters(
            graph, outputKeys);

    return new ConfiguredEventProcessor<>(staticParam, Projection::new);
  }
}
