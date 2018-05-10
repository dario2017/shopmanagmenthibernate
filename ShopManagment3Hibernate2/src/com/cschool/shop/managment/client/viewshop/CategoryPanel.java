package com.cschool.shop.managment.client.viewshop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.cschool.shop.managment.client.service.CategoryServiceRPC;
import com.cschool.shop.managment.client.service.CategoryServiceRPCAsync;
import com.cschool.shop.managment.shared.model.Category;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;

public class CategoryPanel extends HorizontalPanel {
	
	private final CategoryServiceRPCAsync categoryService = GWT.create(CategoryServiceRPC.class);
	
	private Set<Category> availableCategoriesSet;
	private Set<Category> chosenCategoriesSet;
	
	private VerticalPanel buttonPanel, availablePanel, chosenPanel;
	private CellTable<Category> chosenCategoriesTable;
	private CellTable<Category> availableCategoriesTable;
	private TextColumn<Category> availableCategoryColumn, chosenCategoryColumn;
	private Button addCategoryButton, removeCategoryButton;
	private Label availableLabel, chosenLabel;
	
//	Constructor for adding new product
	public CategoryPanel() {
		initialize();
		
		this.add(availablePanel);
		this.add(buttonPanel);
		this.add(chosenPanel);
		
		chosenCategoriesSet = new HashSet<>();
	}
	
//	Constructor for editing product
	public CategoryPanel(Set<Category> productCategories) {
		initialize();
		
		this.add(availablePanel);
		this.add(buttonPanel);
		this.add(chosenPanel);
		
		chosenCategoriesSet = new HashSet<>(productCategories);
		chosenCategoriesTable.setRowData(new ArrayList<Category>(chosenCategoriesSet));
	}

	private void initialize() {
		buttonPanel = new VerticalPanel();
		availablePanel = new VerticalPanel();
		chosenPanel = new VerticalPanel();
		
		addCategoryButton = new Button("==>");
		removeCategoryButton = new Button("<==");
		buttonPanel.add(addCategoryButton);
		buttonPanel.add(removeCategoryButton);
		buttonPanel.setSpacing(5);
		
		availableLabel = new Label("Available categories:");
		chosenLabel = new Label("Chosen categories:");
		
		availableCategoriesTable = new CellTable<>();
		chosenCategoriesTable = new CellTable<>();
		
		availablePanel.add(availableLabel);
		availablePanel.add(availableCategoriesTable);
		chosenPanel.add(chosenLabel);
		chosenPanel.add(chosenCategoriesTable);
		
		availableCategoryColumn = new TextColumn<Category>() {
			@Override
			public String getValue(Category category) {
				return category.getCategoryName();
			}
		};
		
		chosenCategoryColumn = new TextColumn<Category>() {
			@Override
			public String getValue(Category category) {
				return category.getCategoryName();
			}
		};
		
		availableCategoriesTable.addColumn(availableCategoryColumn);
		MultiSelectionModel<Category> availableSelectionCategory = new MultiSelectionModel<>();
		availableCategoriesTable.setSelectionModel(availableSelectionCategory); 
		
		chosenCategoriesTable.addColumn(chosenCategoryColumn);
		MultiSelectionModel<Category> chosenSelectionCategory = new MultiSelectionModel<>();
		chosenCategoriesTable.setSelectionModel(chosenSelectionCategory);
		
		refreshCategoryTable();
		
		addCategoryButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				chosenCategoriesSet.addAll(availableSelectionCategory.getSelectedSet());
				availableCategoriesSet.removeAll(availableSelectionCategory.getSelectedSet());
				
				availableSelectionCategory.clear();
				chosenSelectionCategory.clear();
				
				availableCategoriesTable.setRowData(new ArrayList<Category>(availableCategoriesSet));
				chosenCategoriesTable.setRowData(new ArrayList<Category>(chosenCategoriesSet));
			}
		});
		
		removeCategoryButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent evetn) {
				availableCategoriesSet.addAll(chosenSelectionCategory.getSelectedSet());
				chosenCategoriesSet.removeAll(chosenSelectionCategory.getSelectedSet());
				
				availableSelectionCategory.clear();
				chosenSelectionCategory.clear();
				
				availableCategoriesTable.setRowData(new ArrayList<Category>(availableCategoriesSet));
				chosenCategoriesTable.setRowData(new ArrayList<Category>(chosenCategoriesSet));
			}
		});
		
	}
	
	public void refreshCategoryTable() {
		categoryService.getAllCategories(new AsyncCallback<Set<Category>>() {		
			@Override
			public void onSuccess(Set<Category> result) {
				availableCategoriesSet = result;			
				availableCategoriesTable.setRowData(new ArrayList<Category>(availableCategoriesSet));
//  	saves actual category set
				StaticFields.setCategorySet(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failure: " + caught.getMessage());		
			}
		});
	}

	public Set<Category> getChosenCategoriesSet() {
		return chosenCategoriesSet;
	}
	
}
