package org.streampipes.examples.jvm.sink.notification;


import com.google.gson.Gson;
import org.streampipes.commons.exceptions.SpRuntimeException;
import org.streampipes.examples.jvm.config.PeJvmConfig;
import org.streampipes.messaging.jms.ActiveMQPublisher;
import org.streampipes.model.Notification;
import org.streampipes.wrapper.runtime.EventSink;

import java.util.Date;
import java.util.Map;

public class NotificationProducer implements EventSink<NotificationParameters> {

  private String title;
  private String content;

  private ActiveMQPublisher publisher;
  private Gson gson;


  public NotificationProducer() {

  }

  @Override
  public void bind(NotificationParameters parameters) throws SpRuntimeException {
    this.publisher = new ActiveMQPublisher(PeJvmConfig.INSTANCE.getJmsHost() + ":" + PeJvmConfig.INSTANCE.getJmsPort(),
            "org.streampipes.notifications");
    this.gson = new Gson();
    this.title = parameters.getTitle();
    this.content = parameters.getContent();
  }

  @Override
  public void onEvent(Map<String, Object> event, String sourceInfo) {
    Notification notification = new Notification();
    notification.setTitle(title);
    notification.setMessage(content);
    notification.setCreatedAt(new Date());

    // TODO add targeted user to notification object

    publisher.publish(gson.toJson(notification).getBytes());
  }

  @Override
  public void discard() throws SpRuntimeException {
    this.publisher.disconnect();
  }
}
