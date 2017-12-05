package org.streampipes.examples.jvm.processor.numericalfilter;

import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.wrapper.params.binding.EventProcessorBindingParams;

public class NumericalFilterParameters extends EventProcessorBindingParams {

  private double threshold;
  private NumericalOperator numericalOperator;
  private String filterProperty;

  public NumericalFilterParameters(DataProcessorInvocation graph, Double threshold, NumericalOperator
          numericalOperator, String filterProperty) {
    super(graph);
    this.threshold = threshold;
    this.numericalOperator = numericalOperator;
    this.filterProperty = filterProperty;
  }

  public double getThreshold() {
    return threshold;
  }

  public NumericalOperator getNumericalOperator() {
    return numericalOperator;
  }

  public String getFilterProperty() {
    return filterProperty;
  }
}
