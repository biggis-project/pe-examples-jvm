package org.streampipes.examples.jvm.processor.projection;

import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.wrapper.routing.SpOutputCollector;
import org.streampipes.wrapper.standalone.engine.StandaloneEventProcessorEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Projection extends StandaloneEventProcessorEngine<ProjectionParameters> {

  private List<String> outputKeys;

  @Override
  public void onInvocation(ProjectionParameters projectionParameters, DataProcessorInvocation dataProcessorInvocation) {
    this.outputKeys = projectionParameters.getOutputKeys();
  }

  @Override
  public void onEvent(Map<String, Object> in, String sourceInfo, SpOutputCollector out) {
    Map<String, Object> outEvent = new HashMap<>();
    for(String outputKey : outputKeys) {
      outEvent.put(outputKey, in.get(outputKey));
    }
    out.onEvent(outEvent);
  }

  @Override
  public void onDetach() {

  }
}
