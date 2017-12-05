package org.streampipes.examples.jvm.processor.textfilter;

import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.wrapper.params.binding.EventProcessorBindingParams;

public class TextFilterParameters extends EventProcessorBindingParams {

  private String keyword;
  private StringOperator stringOperator;
  private String filterProperty;

  public TextFilterParameters(DataProcessorInvocation graph, String keyword, StringOperator stringOperator, String
          filterProperty) {
    super(graph);
    this.keyword = keyword;
    this.stringOperator = stringOperator;
    this.filterProperty = filterProperty;
  }

  public String getKeyword() {
    return keyword;
  }

  public StringOperator getStringOperator() {
    return stringOperator;
  }

  public String getFilterProperty() {
    return filterProperty;
  }
}
