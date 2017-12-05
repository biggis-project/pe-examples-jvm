package org.streampipes.examples.jvm.processor.numericalfilter;

import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.wrapper.routing.SpOutputCollector;
import org.streampipes.wrapper.standalone.engine.StandaloneEventProcessorEngine;

import java.util.Map;

public class NumericalFilter extends StandaloneEventProcessorEngine<NumericalFilterParameters> {

  private NumericalFilterParameters params;

  @Override
  public void onInvocation(NumericalFilterParameters numericalFilterParameters, DataProcessorInvocation dataProcessorInvocation) {
    this.params = numericalFilterParameters;
  }

  @Override
  public void onEvent(Map<String, Object> in, String s, SpOutputCollector out) {
    Boolean satisfiesFilter = false;

    Double value = Double.parseDouble(String.valueOf(in.get(params.getFilterProperty())));
    Double threshold = params.getThreshold();

    if (params.getNumericalOperator() == NumericalOperator.EQ) {
      satisfiesFilter = (value == threshold);
    } else if (params.getNumericalOperator() == NumericalOperator.GE) {
      satisfiesFilter = (value >= threshold);
    } else if (params.getNumericalOperator() == NumericalOperator.GT) {
      satisfiesFilter = value > threshold;
    } else if (params.getNumericalOperator() == NumericalOperator.LE) {
      satisfiesFilter = (value <= threshold);
    } else if (params.getNumericalOperator() == NumericalOperator.LT) {
      satisfiesFilter = (value < threshold);
    }

    if (satisfiesFilter) {
      out.onEvent(in);
    }
  }

  @Override
  public void onDetach() {

  }
}
