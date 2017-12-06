package org.streampipes.examples.jvm.sink.couchdb;

import org.streampipes.examples.jvm.config.PeJvmConfig;
import org.streampipes.model.DataSinkType;
import org.streampipes.model.graph.DataSinkDescription;
import org.streampipes.model.graph.DataSinkInvocation;
import org.streampipes.sdk.builder.DataSinkBuilder;
import org.streampipes.sdk.extractor.DataSinkParameterExtractor;
import org.streampipes.sdk.helpers.EpRequirements;
import org.streampipes.sdk.helpers.SupportedFormats;
import org.streampipes.sdk.helpers.SupportedProtocols;
import org.streampipes.wrapper.ConfiguredEventSink;
import org.streampipes.wrapper.runtime.EventSink;
import org.streampipes.wrapper.standalone.declarer.StandaloneEventSinkDeclarer;

public class CouchDbController  extends StandaloneEventSinkDeclarer<CouchDbParameters> {

  private static final String DATABASE_HOST_KEY = "db_host";
  private static final String DATABASE_PORT_KEY = "db_port";
  private static final String DATABASE_NAME_KEY = "db_name";
  private static final String DATABASE_USER_KEY = "db_user";
  private static final String DATABASE_PASSORD_KEY = "db_password";

  @Override
  public DataSinkDescription declareModel() {
    return DataSinkBuilder.create("couchdb", "CouchDB", "Stores events in a couchdb database.")
            .category(DataSinkType.STORAGE)
            .iconUrl(PeJvmConfig.getIconUrl("couchdb_icon"))
            .requiredPropertyStream1(EpRequirements.anyProperty())
            .supportedFormats(SupportedFormats.jsonFormat())
            .supportedProtocols(SupportedProtocols.kafka())
            .requiredTextParameter(DATABASE_HOST_KEY, "Hostname", "The hostname of the CouchDB instance")
            .requiredIntegerParameter(DATABASE_PORT_KEY, "Port", "The port of the CouchDB instance")
            .requiredTextParameter(DATABASE_NAME_KEY, "Database Name", "The name of the database where events will " +
                    "be stored")
            .build();
  }


  @Override
  public ConfiguredEventSink<CouchDbParameters, EventSink<CouchDbParameters>> onInvocation(DataSinkInvocation graph) {
    DataSinkParameterExtractor extractor = getExtractor(graph);

    String hostname = extractor.singleValueParameter(DATABASE_HOST_KEY, String.class);
    Integer port = extractor.singleValueParameter(DATABASE_PORT_KEY, Integer.class);
    String dbName = extractor.singleValueParameter(DATABASE_NAME_KEY, String.class);

    //TODO: Use this after optional parameters implementation
    //String user = extractor.singleValueParameter(DATABASE_USER_KEY, String.class);
    //String password = extractor.singleValueParameter(DATABASE_PASSORD_KEY, String.class);

    String user = null;
    String password = null;

    CouchDbParameters params = new CouchDbParameters(graph, hostname, port, dbName, user, password);

    return new ConfiguredEventSink<>(params, CouchDb::new);
  }

}
