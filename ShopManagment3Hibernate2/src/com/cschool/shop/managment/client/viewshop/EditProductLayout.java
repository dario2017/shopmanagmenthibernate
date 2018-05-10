package com.cschool.shop.managment.client.viewshop;

import java.util.HashSet;
import java.util.Set;

import com.cschool.shop.managment.shared.model.Category;
import com.cschool.shop.managment.shared.model.Product;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EditProductLayout extends DialogBox {
	private Product product;
	
	private CategoryPanel catPanel;
	
	private VerticalPanel verticalDialog;
	private HorizontalPanel hpName, hpPrice, hpAvailable, hpImage, hpButtons;
	
	private Label nameLabel, priceLabel, availableLabel, imageLabel;
	protected TextBox nameBox, priceBox, imageBox;
	protected CheckBox availableBox;
	protected Button updateButton, cancelButton;
	
	public EditProductLayout(Product product) {
		this.product = product;
	
		this.initialize();
		
		this.setText("Edit product");
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
		nameBox.setText(product.getName());
		hpName.add(nameLabel);
		hpName.add(nameBox);
		hpName.setWidth("100%");
		
		hpPrice = new HorizontalPanel();
		priceLabel = new Label("Price: ");
		priceBox = new TextBox();
		priceBox.setText(String.valueOf(product.getPrice()));
		hpPrice.add(priceLabel);
		hpPrice.add(priceBox);
		hpPrice.setWidth("100%");
		
		hpAvailable = new HorizontalPanel();
		availableLabel = new Label("Available:");
		availableBox = new CheckBox();
		availableBox.setValue(product.isAvailable());
		hpAvailable.add(availableLabel);
		hpAvailable.add(availableBox);
		hpAvailable.setWidth("100%");
		
		catPanel = new CategoryPanel(product.getCategorySet());
		
		hpImage = new HorizontalPanel();
		imageLabel = new Label("Image:");
		imageBox = new TextBox();
		imageBox.setText(product.getImage());
		hpImage.add(imageLabel);
		hpImage.add(imageBox);
		hpImage.setWidth("100%");
			
		hpButtons = new HorizontalPanel();
		updateButton = new Button("Update");
		cancelButton = new Button("Cancel");
		hpButtons.add(updateButton);
		hpButtons.add(cancelButton);
		
		verticalDialog.add(hpName);
		verticalDialog.add(hpPrice);
		verticalDialog.add(hpAvailable);
		verticalDialog.add(catPanel);
		verticalDialog.add(hpImage);
		verticalDialog.add(hpButtons);
	}
	
	public Product updateProduct(Product selected) {
		String name = nameBox.getText();
		Double price = null;
		try {
			price = Double.parseDouble(priceBox.getText());	
		} catch (NumberFormatException ex) {
			Window.alert("Wrong number format!");
		}
		Set<Category> category = new HashSet<>(catPanel.getChosenCategoriesSet());
		String image = imageBox.getText();
		boolean available = availableBox.getValue();
		
		selected.setName(name);
		selected.setPrice(price);
		selected.setImage(image);
		selected.setAvailable(available);
		selected.setCategorySet(category);

		if (name.length() == 0 || price == null || image.length() == 0) {
			Window.alert("Cannot update product. Some of data is wrong");
		}
		return selected;
	}

}
