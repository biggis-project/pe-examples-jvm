package org.streampipes.examples.jvm.processor.textfilter;

import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.wrapper.routing.SpOutputCollector;
import org.streampipes.wrapper.standalone.engine.StandaloneEventProcessorEngine;

import java.util.Map;

public class TextFilter extends StandaloneEventProcessorEngine<TextFilterParameters> {

  private TextFilterParameters params;

  @Override
  public void onInvocation(TextFilterParameters textFilterParameters, DataProcessorInvocation dataProcessorInvocation) {
    this.params = textFilterParameters;
  }

  @Override
  public void onEvent(Map<String, Object> in, String s, SpOutputCollector out) {
    Boolean satisfiesFilter = false;
    String value = String.valueOf(in.get(params.getFilterProperty()));

    if (params.getStringOperator() == StringOperator.MATCHES) {
      satisfiesFilter = (value.equals(params.getKeyword()));
    } else if (params.getStringOperator() == StringOperator.CONTAINS) {
      satisfiesFilter = (value.contains(params.getKeyword()));
    }

    if (satisfiesFilter) {
      out.onEvent(in);
    }
  }

  @Override
  public void onDetach() {

  }
}
