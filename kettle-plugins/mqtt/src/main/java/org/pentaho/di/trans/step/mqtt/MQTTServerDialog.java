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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaFactory;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.streaming.common.BaseStreamStepMeta;
import org.pentaho.di.ui.core.widget.ColumnInfo;
import org.pentaho.di.ui.core.widget.TableView;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.step.BaseStreamingDialog;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class MQTTServerDialog extends BaseStreamingDialog implements StepDialogInterface {

  private static Class<?> PKG = MQTTConsumerMeta.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

  private MQTTServerMeta mqttMeta;
  private Label wlPort;
  private TextVar wPort;

  public MQTTServerDialog( Shell parent, Object in, TransMeta tr, String sname ) {
    super( parent, in, tr, sname );
    mqttMeta = (MQTTServerMeta) in;
  }

  @Override protected void getData() {
    super.getData();
    wPort.setText( mqttMeta.getPort() );
  }

  @Override protected String getDialogTitle() {
    return BaseMessages.getString( PKG, "MQTTServerDialog.Shell.Title" );
  }

  @Override protected void buildSetup( Composite wSetupComp ) {
    props.setLook( wSetupComp );
    FormLayout setupLayout = new FormLayout();
    setupLayout.marginHeight = 15;
    setupLayout.marginWidth = 15;
    wSetupComp.setLayout( setupLayout );

    wlPort = new Label( wSetupComp, SWT.LEFT );
    props.setLook( wlPort );
    wlPort.setText( BaseMessages.getString( PKG, "MQTTServerDialog.Port" ) );
    FormData fdlConnection = new FormData();
    fdlConnection.left = new FormAttachment( 0, 0 );
    fdlConnection.top = new FormAttachment( 0, 0 );
    fdlConnection.right = new FormAttachment( 50, 0 );
    wlPort.setLayoutData( fdlConnection );

    wPort = new TextVar( transMeta, wSetupComp, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
    props.setLook( wPort );
    wPort.addModifyListener( lsMod );
    FormData fdConnection = new FormData();
    fdConnection.left = new FormAttachment( 0, 0 );
    fdConnection.right = new FormAttachment( 75, 0 );
    fdConnection.top = new FormAttachment( wlPort, 5 );
    wPort.setLayoutData( fdConnection );
  }

  @Override protected void additionalOks( BaseStreamStepMeta meta ) {
    super.additionalOks( meta );
    MQTTServerMeta mqttServerMeta = (MQTTServerMeta) meta;
    mqttServerMeta.setPort( wPort.getText() );
  }

  @Override protected int[] getFieldTypes() {
    return new int[]{
      ValueMetaInterface.TYPE_STRING, ValueMetaInterface.TYPE_STRING};
  }

  @Override protected String[] getFieldNames() {
    return new String[] {"Topic", "Message"};
  }
}
