package com.project9;

import static com.eclipsesource.tabris.widgets.enhancement.Widgets.onWidget;

import java.security.SecureRandom;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.eclipsesource.tabris.interaction.AppLauncher;
import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;

public class Page extends AbstractPage {

  @Override
  public void createContent( Composite parent, PageData data ) {
    // Change the current page title. Take a look at GlobalAction...
    setTitle( "Page " + data.get( "count", Integer.class ) );
    parent.setLayout( new GridLayout( 1, false ) );
    
    // Create background color
    SecureRandom random = new SecureRandom();
    int red = random.nextInt( 255 );
    int green = random.nextInt( 255 );
    int blue = random.nextInt( 255 );
    Color background = new Color( parent.getDisplay(), red, green, blue );
    parent.setBackground( background );
    
    // Create image label
    Label imageLabel = new Label( parent, SWT.NONE );
    onWidget( imageLabel ).showLocalTouch( true );
    imageLabel.setLayoutData( new GridData( SWT.CENTER, SWT.CENTER, true, true ) );
    imageLabel.setImage( new Image( parent.getDisplay(), Page.class.getResourceAsStream( "/tabris.png" ) ) );
    
    // Add an action to open a website
    imageLabel.addMouseListener( new MouseAdapter() {
      public void mouseUp( MouseEvent e ) {
        AppLauncher appLauncher = RWT.getClient().getService( AppLauncher.class );
        appLauncher.openUrl( "http://eclipsesource.com/blogs/tag/tabris/" );
      }
	} );
  }

}
