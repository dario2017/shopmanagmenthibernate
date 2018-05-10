package com.cschool.shop.managment.client;

import com.cschool.shop.managment.client.service.LoginServiceRPC;
import com.cschool.shop.managment.client.service.LoginServiceRPCAsync;
import com.cschool.shop.managment.client.viewshop.ShopLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginLayout extends VerticalPanel {
	
	private final LoginServiceRPCAsync loginService = GWT.create(LoginServiceRPC.class);
	
	private VerticalPanel mainPanel;
	
	private VerticalPanel panelLogin;
	private VerticalPanel panelPassword;
	private HorizontalPanel panelError;
	private HorizontalPanel loginPanel;
	private Label loginLabel;
	private Label passwordLabel;
	private Label errorLabel;
	private TextBox loginBox;
	private PasswordTextBox passwordBox;
	private Button loginButton;

	public LoginLayout(VerticalPanel mainPanel) {
		this.mainPanel = mainPanel;
		
		initialize();

		this.add(panelLogin);
		this.add(panelPassword);
		this.add(errorLabel);
		this.add(loginPanel);
	}
	
	private void initialize() {		
		panelLogin = new VerticalPanel();
		loginLabel = new Label("Login: ");
		loginBox = new TextBox();
		panelLogin.add(loginLabel);
		panelLogin.add(loginBox);
		
		panelPassword= new VerticalPanel();
		passwordLabel = new Label("Password: ");
		passwordBox = new PasswordTextBox();
		panelPassword.add(passwordLabel);
		panelPassword.add(passwordBox);
		
		panelError = new HorizontalPanel();
		errorLabel = new Label("Some error");
		errorLabel.setVisible(false);
		panelError.add(errorLabel);
	
		loginPanel = new HorizontalPanel();
		loginButton = new Button("Login");
		loginPanel.add(loginButton);
		
		loginButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				tryToLogIn();
			}
		});
		
		passwordBox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					tryToLogIn();
				}
			}
		});
	}
	
	public void tryToLogIn() {
		loginService.login(loginBox.getText(), passwordBox.getText().toCharArray(), new AsyncCallback<Boolean>(){
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				System.out.println(caught.toString());
				errorLabel.setVisible(true);	
			}
			@Override
			public void onSuccess(Boolean result) {
				if (result) {				
					mainPanel.clear();
					ShopLayout shopLayout = new ShopLayout();
					mainPanel.add(shopLayout);
				} else {
					Window.alert("Wrong login or password");
				}
			}		
		});	
	}
}
