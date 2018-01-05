/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2017 by Hitachi Vantara : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.step.mqtt;

import com.google.common.base.Preconditions;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.streaming.common.BaseStreamStep;
import org.pentaho.di.trans.streaming.common.FixedTimeStreamWindow;

public class MQTTServer extends BaseStreamStep implements StepInterface {

  private static Class<?> PKG = MQTTServer.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

  public MQTTServer( StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta,
                     Trans trans ) {
    super( stepMeta, stepDataInterface, copyNr, transMeta, trans );
  }

  public boolean init( StepMetaInterface stepMetaInterface, StepDataInterface stepDataInterface ) {
    boolean init = super.init( stepMetaInterface, stepDataInterface );
    Preconditions.checkNotNull( stepMetaInterface );
    MQTTServerMeta mqttServerMeta = (MQTTServerMeta) stepMetaInterface;

    RowMeta rowMeta = new RowMeta();
    mqttServerMeta.getFields(
      rowMeta, getStepname(), null, null, this, repository, metaStore );
    window = new FixedTimeStreamWindow<>( subtransExecutor, rowMeta, getDuration(), getBatchSize() );
    source = new MQTTServerSource( this, mqttServerMeta );
    return init;
  }
}
