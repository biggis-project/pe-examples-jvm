package org.streampipes.examples.jvm.processor.numericalfilter;

import org.streampipes.examples.jvm.config.PeJvmConfig;
import org.streampipes.model.DataProcessorType;
import org.streampipes.model.graph.DataProcessorDescription;
import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.model.schema.PropertyScope;
import org.streampipes.model.util.SepaUtils;
import org.streampipes.sdk.builder.ProcessingElementBuilder;
import org.streampipes.sdk.builder.StreamRequirementsBuilder;
import org.streampipes.sdk.extractor.ProcessingElementParameterExtractor;
import org.streampipes.sdk.helpers.EpRequirements;
import org.streampipes.sdk.helpers.Labels;
import org.streampipes.sdk.helpers.Options;
import org.streampipes.sdk.helpers.OutputStrategies;
import org.streampipes.sdk.helpers.SupportedFormats;
import org.streampipes.sdk.helpers.SupportedProtocols;
import org.streampipes.wrapper.ConfiguredEventProcessor;
import org.streampipes.wrapper.runtime.EventProcessor;
import org.streampipes.wrapper.standalone.declarer.StandaloneEventProcessingDeclarer;

public class NumericalFilterController extends StandaloneEventProcessingDeclarer<NumericalFilterParameters> {

  @Override
  public DataProcessorDescription declareModel() {
    return ProcessingElementBuilder.create("numericalfilter", "Numerical Filter", "Numerical Filter Description")
            .category(DataProcessorType.FILTER)
            .iconUrl(PeJvmConfig.getIconUrl("Numerical_Filter_Icon_HQ"))
            .requiredStream(StreamRequirementsBuilder.create().requiredPropertyWithUnaryMapping(EpRequirements
                    .numberReq(), Labels.from("number", "Specifies the field name where the filter operation should" +
                    " be applied " +
                    "on.", ""), PropertyScope.NONE).build())
            .outputStrategy(OutputStrategies.keep())
            .requiredSingleValueSelection("operation", "Filter Operation", "Specifies the filter " +
                    "operation that should be applied on the field", Options.from("<", "<=", ">", ">=", "=="))
            .requiredFloatParameter("value", "Threshold value", "Specifies a threshold value.", "number")
            .supportedProtocols(SupportedProtocols.kafka())
            .supportedFormats(SupportedFormats.jsonFormat())
            .build();

  }

  @Override
  public ConfiguredEventProcessor<NumericalFilterParameters, EventProcessor<NumericalFilterParameters>> onInvocation
          (DataProcessorInvocation sepa) {
    ProcessingElementParameterExtractor extractor = ProcessingElementParameterExtractor.from(sepa);

    Double threshold = extractor.singleValueParameter("value", Double.class);
    String stringOperation = extractor.selectedSingleValue("operation", String.class);

    String operation = "GT";

    if (stringOperation.equals("<=")) {
      operation = "LT";
    } else if (stringOperation.equals("<")) {
      operation = "LE";
    } else if (stringOperation.equals(">=")) {
      operation = "GE";
    } else if (stringOperation.equals("==")) {
      operation = "EQ";
    }

    String filterProperty = SepaUtils.getMappingPropertyName(sepa,
            "number", true);

    NumericalFilterParameters staticParam = new NumericalFilterParameters(sepa, threshold, NumericalOperator.valueOf
            (operation)
            , filterProperty);

    return new ConfiguredEventProcessor<>(staticParam, NumericalFilter::new);
  }
}
