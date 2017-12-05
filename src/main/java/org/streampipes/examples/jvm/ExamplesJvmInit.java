package org.streampipes.examples.jvm;

import org.streampipes.container.init.DeclarersSingleton;
import org.streampipes.container.standalone.init.StandaloneModelSubmitter;
import org.streampipes.dataformat.json.JsonDataFormatFactory;
import org.streampipes.examples.jvm.config.PeJvmConfig;
import org.streampipes.examples.jvm.processor.numericalfilter.NumericalFilterController;
import org.streampipes.examples.jvm.processor.projection.ProjectionController;
import org.streampipes.examples.jvm.processor.textfilter.TextFilterController;
import org.streampipes.messaging.kafka.SpKafkaProtocolFactory;

public class ExamplesJvmInit extends StandaloneModelSubmitter {

  public static void main(String[] args) {
    DeclarersSingleton
            .getInstance()
            .add(new ProjectionController())
            .add(new NumericalFilterController())
            .add(new TextFilterController());

    DeclarersSingleton.getInstance().registerDataFormat(new JsonDataFormatFactory());
    DeclarersSingleton.getInstance().registerProtocol(new SpKafkaProtocolFactory());

    new ExamplesJvmInit().init(PeJvmConfig.INSTANCE);
  }
}
