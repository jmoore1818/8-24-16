package com.project9;

import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onWidget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;

public class TopLevelPage extends AbstractPage {

  @Override
  public void createContent( final Composite parent, PageData data ) {
    parent.setLayout( new GridLayout( 1, false ) );
    parent.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_DARK_GRAY ) );
    
    // Create a button
    final Button helloButton = new Button( parent, SWT.PUSH );
    onWidget( helloButton ).useAnimation();
    helloButton.setText( "Say Hello!" );
    helloButton.setBackground( parent.getDisplay().getSystemColor( SWT.COLOR_DARK_RED ) );
    helloButton.setForeground( parent.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    helloButton.setLayoutData( new GridData( SWT.CENTER, SWT.BOTTOM, true, true ) );

    // Create a label
    final Label hintLabel = new Label( parent, SWT.NONE );
    hintLabel.setForeground( parent.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    onWidget( hintLabel ).useAnimation();
    hintLabel.setLayoutData( new GridData( SWT.CENTER, SWT.TOP, true, true ) );
    
    // Add an action to the button
    helloButton.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        hintLabel.setText( "Hello my Friend!\n\nFollow the white Rabbit..." );
        helloButton.setVisible( false );
    	parent.layout( true );
      }
	} );
  }

}
