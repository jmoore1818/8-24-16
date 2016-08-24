package com.project9;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;

import com.eclipsesource.tabris.TabrisClientInstaller;
import com.eclipsesource.tabris.ui.ActionConfiguration;
import com.eclipsesource.tabris.ui.PageConfiguration;
import com.eclipsesource.tabris.ui.TabrisUIEntrypointFactory;
import com.eclipsesource.tabris.ui.UIConfiguration;

public class Configuration implements ApplicationConfiguration {
	
  @Override
  public void configure( Application application ) {
    // Install the Tabris Client
    TabrisClientInstaller.install( application );
    
    // Add a Tabris UI application
    application.addEntryPoint( "/app", new TabrisUIEntrypointFactory( createUIConfiguration() ), null );
  }

  private UIConfiguration createUIConfiguration() {
    UIConfiguration uiConfiguration = new UIConfiguration();
    
    // Add a top level page
    PageConfiguration topLevelPage = new PageConfiguration( "topLevelPage", TopLevelPage.class );
    topLevelPage.setTopLevel( true );
    topLevelPage.setTitle( "Top Level Page" );
    uiConfiguration.addPageConfiguration( topLevelPage );
    
    // Add a normal page
    PageConfiguration page = new PageConfiguration( "page", Page.class );
    page.setTitle( "Page" );
    
    // Add a page action
    ActionConfiguration pageAction = new ActionConfiguration( "pageAction", PageAction.class );
    pageAction.setImage( Configuration.class.getResourceAsStream( "/share.png" ) );
    page.addActionConfiguration( pageAction );
    uiConfiguration.addPageConfiguration( page );
    
    // Add a global action
    ActionConfiguration globalAction = new ActionConfiguration( "globalAction", GlobalAction.class );
    globalAction.setImage( Configuration.class.getResourceAsStream( "/page.png" ) );
    uiConfiguration.addActionConfiguration( globalAction );
    
    return uiConfiguration;
  }
}
