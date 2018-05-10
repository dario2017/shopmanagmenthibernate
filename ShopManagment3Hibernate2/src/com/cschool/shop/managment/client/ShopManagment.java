package com.cschool.shop.managment.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShopManagment implements EntryPoint {

	public void onModuleLoad() {
		VerticalPanel mainPanel = new VerticalPanel();
	
		mainPanel.getElement().setAttribute("align", "center");
		
		LoginLayout loginLayout = new LoginLayout(mainPanel);
		mainPanel.add(loginLayout);

		RootPanel.get("mainLayout").add(mainPanel);
	
	}
	
}
