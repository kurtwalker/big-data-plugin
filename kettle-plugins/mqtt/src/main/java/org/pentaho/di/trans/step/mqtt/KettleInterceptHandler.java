package org.pentaho.di.trans.step.mqtt;

import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptAcknowledgedMessage;
import io.moquette.interception.messages.InterceptConnectMessage;
import io.moquette.interception.messages.InterceptConnectionLostMessage;
import io.moquette.interception.messages.InterceptDisconnectMessage;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.interception.messages.InterceptSubscribeMessage;
import io.moquette.interception.messages.InterceptUnsubscribeMessage;
import io.moquette.spi.impl.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class KettleInterceptHandler implements InterceptHandler {
  private Consumer<List<List<Object>>> acceptConsumer;

  public KettleInterceptHandler( Consumer<List<List<Object>>> acceptConsumer ) {
    this.acceptConsumer = acceptConsumer;
  }

  @Override public String getID() {
    return null;
  }

  @Override public Class<?>[] getInterceptedMessageTypes() {
    return InterceptHandler.ALL_MESSAGE_TYPES;
  }

  @Override public void onConnect( InterceptConnectMessage msg ) {

  }

  @Override public void onDisconnect( InterceptDisconnectMessage msg ) {

  }

  @Override public void onConnectionLost( InterceptConnectionLostMessage msg ) {

  }

  @Override public void onPublish( InterceptPublishMessage msg ) {
    byte[] bytes = Utils.readBytesAndRewind( msg.getPayload() );
    acceptConsumer.accept( singletonList( asList( msg.getTopicName(), new String( bytes ) ) ) );
  }

  @Override public void onSubscribe( InterceptSubscribeMessage msg ) {

  }

  @Override public void onUnsubscribe( InterceptUnsubscribeMessage msg ) {

  }

  @Override public void onMessageAcknowledged( InterceptAcknowledgedMessage msg ) {

  }
}
