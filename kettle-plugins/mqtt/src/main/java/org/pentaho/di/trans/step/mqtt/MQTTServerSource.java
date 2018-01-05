package org.pentaho.di.trans.step.mqtt;

import io.moquette.BrokerConstants;
import io.moquette.server.Server;
import io.moquette.server.config.MemoryConfig;
import org.pentaho.di.trans.streaming.common.BaseStreamStep;
import org.pentaho.di.trans.streaming.common.BlockingQueueStreamSource;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class MQTTServerSource extends BlockingQueueStreamSource<List<Object>> {
  private MQTTServerMeta mqttServerMeta;

  protected MQTTServerSource( MQTTServer streamStep, MQTTServerMeta mqttServerMeta ) {
    super( streamStep );
    this.mqttServerMeta = mqttServerMeta;
  }

  @Override public void open() {
    Server server = new Server();
    MemoryConfig memoryConfig = new MemoryConfig( new Properties() );
    memoryConfig.setProperty( BrokerConstants.PORT_PROPERTY_NAME, mqttServerMeta.getPort() );
    try {
      server.startServer( memoryConfig );
      server.addInterceptHandler( new KettleInterceptHandler( this::acceptRows ) );
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }
}
