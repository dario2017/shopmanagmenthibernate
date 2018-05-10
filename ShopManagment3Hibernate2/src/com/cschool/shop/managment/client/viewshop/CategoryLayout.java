package com.cschool.shop.managment.client.viewshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cschool.shop.managment.client.service.CategoryServiceRPC;
import com.cschool.shop.managment.client.service.CategoryServiceRPCAsync;
import com.cschool.shop.managment.client.service.ProductServiceRPC;
import com.cschool.shop.managment.client.service.ProductServiceRPCAsync;
import com.cschool.shop.managment.shared.model.Category;
import com.cschool.shop.managment.shared.model.Product;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class CategoryLayout extends VerticalPanel {
	
	private final CategoryServiceRPCAsync categoryService = GWT.create(CategoryServiceRPC.class);
	private final ProductServiceRPCAsync productService = GWT.create(ProductServiceRPC.class);
	
	private AddCategoryLayout addCategoryLayout;
	
	private List<Product> productsList;
	private Set<Category> categoriesSet;
	
	private SingleSelectionModel<Category> selectionModel;
	private Category selected;
	
	private HorizontalPanel hpButtons;
	private CellTable<Category> categoryTable;
	private TextColumn<Category> idTable, nameTable;
	private Button addButton, removeButton;
	
	public CategoryLayout() {
		initialize();
		this.add(hpButtons);
		this.add(categoryTable);
		
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addCategoryLayout = new AddCategoryLayout();
				add(addCategoryLayout);
				addCategoryLayout.show();
				addCategoryLayout.saveButton.addClickHandler(new ClickHandler() {		
					@Override
					public void onClick(ClickEvent event) {
						String name = addCategoryLayout.nameBox.getText();
						if (name.length() == 0) {
							Window.alert("Cannot add category with empty name");
						} else if (isCategoryAlreadyExist(name)) {
							Window.alert("Category with that name already exist in list");
						} else {
							categoryService.addCategory(new Category(name), new AsyncCallback<Boolean>() {
								@Override
								public void onFailure(Throwable caught) {
									System.out.println("Failure: " + caught.getMessage());			
								}

								@Override
								public void onSuccess(Boolean result) {
									refreshCategoryTable();
									addCategoryLayout.hide();
								}							
							});
						}
						
					}
				});
			}
		});
		
		removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selected = selectionModel.getSelectedObject();
				if (isCategoryHasProduct(selected)) {
					Window.alert("Category is in product. You can't delete it");
				} else {
					categoryService.removeCategory(selected, new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							System.out.println("Failure: " + caught.getMessage());	
						}
						@Override
						public void onSuccess(Boolean result) {
							Window.alert("Category has been removed");
							refreshCategoryTable();
						}
					});
				}
			}
		});
	}
	
	private void initialize() {
		addButton = new Button("Add category");
		removeButton = new Button("Remove category");
		hpButtons = new HorizontalPanel();
		hpButtons.add(addButton);
		hpButtons.add(removeButton);
		
		categoryTable = new CellTable<>();
		
		idTable = new TextColumn<Category>() {
			@Override
			public String getValue(Category category) {
				return String.valueOf(category.getCategoryId());
			}
		};
		nameTable = new TextColumn<Category>() {
			@Override
			public String getValue(Category category) {
				return category.getCategoryName();
			}
		};
		
		categoryTable.addColumn(idTable, "ID");
		categoryTable.addColumn(nameTable, "Category");
		
		categoryTable.setWidth("100%", true);
		categoryTable.setColumnWidth(1, "90%");
		
		refreshCategoryTable();
		getAllProducts();
		selectionModel();
	}
	
	private boolean isCategoryAlreadyExist(String name) {
		for (Category eachCategory: categoriesSet) {
			if (eachCategory.getCategoryName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isCategoryHasProduct(Category selected) {
		productsList = StaticFields.getProductsList();
		for (Product eachProduct: productsList) {
			if (eachProduct.getCategorySet().contains(selected)) {
				return true;
			}
		}
		return false;
	}
	
	private void getAllProducts() {
		productService.getAllProducts(new AsyncCallback<List<Product>>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failure: " + caught.getMessage());				
			}

			@Override
			public void onSuccess(List<Product> result) {
				productsList = result;				
			}
		});
	}
	
	private void selectionModel() {
		selectionModel = new SingleSelectionModel();
		categoryTable.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				selected = selectionModel.getSelectedObject();
			}
		});
	}
	
	private void refreshCategoryTable() {
		categoryService.getAllCategories(new AsyncCallback<Set<Category>>() {
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failure: " + caught.getMessage());	
			}

			@Override
			public void onSuccess(Set<Category> result) {
				categoriesSet = result;
				categoryTable.setRowData(new ArrayList<Category>(result));
				
				StaticFields.setCategorySet(result);
			}	
		});	
	}
}
