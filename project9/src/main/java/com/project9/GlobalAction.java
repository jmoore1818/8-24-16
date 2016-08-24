package com.project9;

import com.eclipsesource.tabris.ui.AbstractAction;
import com.eclipsesource.tabris.ui.PageData;

public class GlobalAction extends AbstractAction {

  @Override
  public void execute() {
    // Create a count object for the pages
    Integer count = getCurrentPageData().get( "count" , Integer.class );
    if( count != null ) {
      count = Integer.valueOf( count.intValue() + 1 );
    } else {
      count = Integer.valueOf( 1 );
    }
    
    // open the page
    PageData pageData = new PageData();
    pageData.set( "count", count );
    openPage( "page", pageData );
  }

}
