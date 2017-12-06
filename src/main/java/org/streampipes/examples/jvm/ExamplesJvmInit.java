package org.streampipes.examples.jvm;

import org.streampipes.container.init.DeclarersSingleton;
import org.streampipes.container.standalone.init.StandaloneModelSubmitter;
import org.streampipes.dataformat.json.JsonDataFormatFactory;
import org.streampipes.examples.jvm.config.PeJvmConfig;
import org.streampipes.examples.jvm.processor.numericalfilter.NumericalFilterController;
import org.streampipes.examples.jvm.processor.projection.ProjectionController;
import org.streampipes.examples.jvm.processor.textfilter.TextFilterController;
import org.streampipes.examples.jvm.sink.couchdb.CouchDbController;
import org.streampipes.examples.jvm.sink.dashboard.DashboardController;
import org.streampipes.examples.jvm.sink.kafka.KafkaController;
import org.streampipes.examples.jvm.sink.notification.NotificationController;
import org.streampipes.messaging.kafka.SpKafkaProtocolFactory;

public class ExamplesJvmInit extends StandaloneModelSubmitter {

  public static void main(String[] args) {
    DeclarersSingleton
            .getInstance()
            .add(new ProjectionController())
            .add(new NumericalFilterController())
            .add(new TextFilterController())
            .add(new NotificationController())
            .add(new KafkaController())
            .add(new DashboardController())
            .add(new CouchDbController());

    DeclarersSingleton.getInstance().registerDataFormat(new JsonDataFormatFactory());
    DeclarersSingleton.getInstance().registerProtocol(new SpKafkaProtocolFactory());

    new ExamplesJvmInit().init(PeJvmConfig.INSTANCE);
  }
}
