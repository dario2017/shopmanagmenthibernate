package com.cschool.shop.managment.client.viewshop;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.cschool.shop.managment.client.service.ProductServiceRPC;
import com.cschool.shop.managment.client.service.ProductServiceRPCAsync;
import com.cschool.shop.managment.shared.model.Category;
import com.cschool.shop.managment.shared.model.Product;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class ProductsLayout extends VerticalPanel {

	private final ProductServiceRPCAsync productService = GWT.create(ProductServiceRPC.class);
	
	private AddProductLayout addProductLayout;
	private EditProductLayout editProductLayout;
	
	private SingleSelectionModel<Product> selectionModel;
	private Product selected;
	
	private HorizontalPanel horizontalPanel;
	private CellTable<Product> productTable;
	private ClickableTextCell imageCell;
	private Column<Product, Boolean> availableColumn;
	private Column<Product, String> imageColumn;
	private TextColumn<Product> idColumn, nameColumn, categoryColumn, priceColumn;
	private Button addButton, removeButton, editButton;
	private CheckboxCell isAvailable;
	private Label productLabel;
	
	public ProductsLayout() {
		initialize();
		
		this.add(horizontalPanel);;
		this.add(productTable);		
	}
	
	private void initialize() {
		addButton = new Button("Add");
		productLabel = new Label();

		removeButton = new Button("Remove");
		removeButton.setEnabled(false);
		
		editButton = new Button("Edit");
		editButton.setEnabled(false);
		
		productTable = new CellTable<>();
		productTable.setPageSize(100);
		
		createTableColumns();
	
		productTable.setWidth("100%", true);

		horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(addButton);
		horizontalPanel.add(editButton);
		horizontalPanel.add(removeButton);
		horizontalPanel.add(productLabel);
		horizontalPanel.setSpacing(15);
	
		selectionModel();
//	inserts list of all products to table
		refreshProductsTable();
		
		addButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				addProductLayout = new AddProductLayout();
				add(addProductLayout);
				addProductLayout.show();

				saveButtonHandler();
			}
		});
		
		editButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selected = selectionModel.getSelectedObject();
				editProductLayout = new EditProductLayout(selected);
				add(editProductLayout);
				editProductLayout.show();
				
				editProductLayout.updateButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {		
							productService.updateProduct(editProductLayout.updateProduct(selected), new AsyncCallback<Boolean>() {
								@Override
								public void onFailure(Throwable caught) {
									System.out.println("Failure: " + caught.getMessage());	
								}
			
								@Override
								public void onSuccess(Boolean result) {
									Window.alert("Product has been updated");
									refreshProductsTable();	;
									editProductLayout.hide();
								}
							});
						}
					});
			}
		});
		
		removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selected = selectionModel.getSelectedObject();
				productService.removeProduct(selected, new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Failure: " + caught.getMessage());	
					}

					@Override
					public void onSuccess(Boolean result) {
						Window.alert("Product + " + selected.toString() + " has been deleted");
						refreshProductsTable();	
						removeButton.setEnabled(false);
						editButton.setEnabled(false);
						selectionModel.clear();
					}
				});
			}
		});
		
		
	}	
	
	private void createTableColumns() {
		idColumn = new TextColumn<Product>() {
			@Override
			public String getValue(Product product) {
				return product.getProductId()  + "";
			}
		};
		nameColumn = new TextColumn<Product>() {
			@Override
			public String getValue(Product product) {
				return product.getName();
			}
		};
		categoryColumn = new TextColumn<Product>() {
			@Override
			public String getValue(Product product) {
				return product.getCategorySet().toString();
			}
		};
		priceColumn = new TextColumn<Product>() {
			@Override
			public String getValue(Product product) {
				return String.valueOf(product.getPrice());
			}
		};
		
		isAvailable = new CheckboxCell();
		availableColumn = new Column<Product, Boolean>(isAvailable){
			@Override
			public Boolean getValue(Product product) {
				return product.isAvailable();
			}
			@Override
			public void render(Context context, Product product, SafeHtmlBuilder sb) {
				if (product.isAvailable()) {
					sb.appendHtmlConstant("<input type='checkbox' disabled=disabled checked></input>");
				} else {
					sb.appendHtmlConstant("<input type='checkbox' disabled=disabled></input>");
				}
			}
		};
		
		imageCell = new ClickableTextCell();
		imageColumn = new Column<Product, String>(imageCell){
			@Override
			public void render(Context context, Product product, SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<img width=\"100\" heigth=\"75\" src=" + product.getImage() + ">");
				super.render(context, product, sb);
			
			}
			@Override
			public String getValue(Product object) {
				return "";
			}		
		};
		
		productTable.addColumn(idColumn, "ID");
		productTable.addColumn(nameColumn, "Name");
		productTable.addColumn(categoryColumn, "Category");
		productTable.addColumn(priceColumn, "Price");
		productTable.addColumn(availableColumn, "Availablity");
		productTable.addColumn(imageColumn, "Image");
	}
	
	private void selectionModel() {
		selectionModel = new SingleSelectionModel<>();
		productTable.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				selected = selectionModel.getSelectedObject();
				if (selected != null) {
					editButton.setEnabled(true);
					removeButton.setEnabled(true);	
					productLabel.setText(selected.getName() + ", price: " + selected.getPrice()
							+ ", Category: " + selected.getCategorySet().toString());
				}	
			}		
		});
	}

	private void saveButtonHandler() {
		addProductLayout.saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				productService.addProduct(addProductLayout.createProduct(), new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Failure: " + caught.getMessage());	
					}
					@Override
					public void onSuccess(Boolean result) {
						refreshProductsTable();
						Window.alert("Product has been added");
						addProductLayout.hide();
					}
				});
			} 
		});
	}

	public void refreshProductsTable() {
		productService.getAllProducts(new AsyncCallback<List<Product>>() {	
			@Override
			public void onSuccess(List<Product> result) {
				productTable.setRowCount(result.size(), true);
				productTable.setRowData(0, result);	
//			saves actual product list 
				StaticFields.setProductsList(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failure: " + caught.getMessage());			
			}
		});
	}
}
