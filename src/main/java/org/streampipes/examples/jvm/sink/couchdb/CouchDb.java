package org.streampipes.examples.jvm.sink.couchdb;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.streampipes.commons.exceptions.SpRuntimeException;
import org.streampipes.wrapper.runtime.EventSink;

import java.util.Map;

public class CouchDb implements EventSink<CouchDbParameters> {

  private CouchDbClient couchDbClient;

  @Override
  public void bind(CouchDbParameters parameters) throws SpRuntimeException {
    this.couchDbClient = new CouchDbClient(new CouchDbProperties(
            parameters.getDatabaseName(),
            true,
            "http",
            parameters.getCouchDbHost(),
            parameters.getCouchDbPort(),
            parameters.getUser(),
            parameters.getPassword()
    ));
  }

  @Override
  public void onEvent(Map<String, Object> event, String sourceInfo) {
    couchDbClient.save(event);
  }

  @Override
  public void discard() throws SpRuntimeException {
  }


}
