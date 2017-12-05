package org.streampipes.examples.jvm.processor.projection;

import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.wrapper.params.binding.EventProcessorBindingParams;

import java.util.List;

public class ProjectionParameters extends EventProcessorBindingParams {

  private List<String> outputKeys;

  public ProjectionParameters(DataProcessorInvocation graph, List<String> outputKeys) {
    super(graph);
    this.outputKeys = outputKeys;
  }

  public List<String> getOutputKeys() {
    return outputKeys;
  }
}
