package com.cschool.shop.managment.client.viewshop;

import java.util.HashSet;
import java.util.Set;

import com.cschool.shop.managment.client.service.ProductServiceRPC;
import com.cschool.shop.managment.client.service.ProductServiceRPCAsync;
import com.cschool.shop.managment.shared.model.Category;
import com.cschool.shop.managment.shared.model.Product;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

public class AddProductLayout extends DialogBox {
	
	private VerticalPanel verticalDialog;
	private HorizontalPanel hpName, hpPrice, hpAvailable, hpImage, hpButtons;
	private CategoryPanel catPanel;
	
	private Label nameLabel, priceLabel, availableLabel, imageLabel, emptyNameLabel, emptyPriceLabel;
	protected TextBox nameBox, priceBox, imageBox;
	protected CheckBox availableBox;
	protected Button saveButton, cancelButton;
	
	
	public AddProductLayout() {	
		this.initialize();
	
		this.setText("Add product");
		this.setAnimationEnabled(true);

		this.setWidget(verticalDialog);

		this.setPopupPosition(150, 150);
		
		cancelButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				hide();	
			}
		});	
	}
	
	private void initialize() {
		verticalDialog = new VerticalPanel();
		
		hpName = new HorizontalPanel();
		nameLabel = new Label("Name:");
		nameBox = new TextBox();
		hpName.setWidth("100%");
		hpName.add(nameLabel);
		
		hpName.add(nameBox);
		
		nameBox.getElement().setPropertyString("placeholder", "enter product name");
		nameBox.addBlurHandler(new BlurHandler() {	
			@Override
			public void onBlur(BlurEvent event) {
				if (nameBox.getText().isEmpty()) {
					emptyNameLabel.setVisible(true);	
				} else {
					emptyNameLabel.setVisible(false);
				}
			}
		});
		
		hpPrice = new HorizontalPanel();
		priceLabel = new Label("Price: ");
		priceBox = new TextBox();
		hpPrice.add(priceLabel);
		hpPrice.add(priceBox);
		hpPrice.setWidth("100%");
		priceBox.getElement().setPropertyString("placeholder", "enter price (use '.' instead ','");
		priceBox.addBlurHandler(new BlurHandler() {	
			@Override
			public void onBlur(BlurEvent event) {
				if (priceBox.getText().isEmpty()) {
					emptyPriceLabel.setVisible(true);
				} else {
					emptyPriceLabel.setVisible(false);
				}			
			}
		});
		
		hpAvailable = new HorizontalPanel();
		availableLabel = new Label("Available:");
		availableBox = new CheckBox();
		availableBox.setValue(false);
		hpAvailable.add(availableLabel);
		hpAvailable.add(availableBox);
		hpAvailable.setWidth("100%");
	
		hpImage = new HorizontalPanel();
		imageLabel = new Label("Image:");
		imageBox = new TextBox();
		hpImage.add(imageLabel);
		hpImage.add(imageBox);
		hpImage.setWidth("100%");
		imageBox.getElement().setPropertyString("placeholder",  "enter link to product image");
		
		catPanel = new CategoryPanel();
				
		hpButtons = new HorizontalPanel();
		saveButton = new Button("Save");
		cancelButton = new Button("Cancel");
		hpButtons.add(saveButton);
		hpButtons.add(cancelButton);
		
		emptyNameLabel = new Label("You must enter product name!");
		emptyNameLabel.getElement().getStyle().setColor("red");
		emptyNameLabel.setVisible(false);
		
		emptyPriceLabel = new Label("You must enter product price! Only numbers are allowed");
		emptyPriceLabel.getElement().getStyle().setColor("red");
		emptyPriceLabel.setVisible(false);
		
		verticalDialog.add(hpName);
		verticalDialog.add(emptyNameLabel);
		verticalDialog.add(hpPrice);
		verticalDialog.add(emptyPriceLabel);
		verticalDialog.add(hpAvailable);
		verticalDialog.add(catPanel);
		verticalDialog.add(hpImage);
		verticalDialog.add(hpButtons);
		verticalDialog.setSpacing(5);
	}
	
//	checks if all required fields to create product are correct and returns new product
	public Product createProduct() {
		Set<Category> category = new HashSet<>(catPanel.getChosenCategoriesSet());
		String name = nameBox.getText();
		Double price = null;
		try {
			price = Double.parseDouble(priceBox.getText());	
		} catch (NumberFormatException ex) {
			Window.alert("Wrong number format!");
		}
		String image = imageBox.getText();
		boolean available = availableBox.getValue();
		
		if (name.length() == 0 || price == null || image.length() == 0) {
			Window.alert("Unable to create product. Some data is wrong");
			return null;
		} 
		return new Product(name, category, price, available, image);
	}

}
