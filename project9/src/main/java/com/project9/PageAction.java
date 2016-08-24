package com.project9;

import org.eclipse.rap.rwt.RWT;

import com.eclipsesource.tabris.interaction.AppLauncher;
import com.eclipsesource.tabris.interaction.TwitterOptions;
import com.eclipsesource.tabris.ui.AbstractAction;

public class PageAction extends AbstractAction {

  @Override
  public void execute() {
	  AppLauncher appLauncher = RWT.getClient().getService( AppLauncher.class );
	  TwitterOptions twitterOptions = new TwitterOptions( "Just created a Tabris HelloWorld App. Take a look..." );
	  twitterOptions.setUrl( "http://developer.eclipsesource.com/tabris" );
	  appLauncher.open( twitterOptions );
  }

}
