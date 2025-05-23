package main.java.csye6200.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.IntStream;

import javax.swing.text.TabableView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.java.csye6200.dao.BudgetDAOImpl;
import main.java.csye6200.dao.CategoryDAOImpl;
import main.java.csye6200.dao.DatabaseConnect;
import main.java.csye6200.models.Budget;
import main.java.csye6200.models.Category;
import main.java.csye6200.models.Transaction;
import main.java.csye6200.utils.*;

public class ManageBudgetController implements Initializable {

	@FXML
	private TextField amountId;
	private Double amount;

	@FXML
	private ComboBox<String> monthId;

	@FXML
	private ComboBox<Integer> yearId;

	@FXML
	private ComboBox<String> categoryId;
	private String category;

	@FXML
	private Button backButtonId;

	@FXML
	private Button budgetSubmitId;
	private BudgetDAOImpl budgetDAO;
	private CategoryDAOImpl categoryDAO;
	private int result;
	private ResultSet rs;
	private String catId;
	
	@FXML
	private Label labelId;
	
	@FXML
	private Tab addBudgetTab;

	@FXML
	private Tab editBudgetTab;
	
	@FXML
	private TextField editamountId;
	private Double editamount;

	@FXML
	private ComboBox<String> editmonthId;

	@FXML
	private ComboBox<Integer> edityearId;

	@FXML
	private ComboBox<String> editcategoryId;
	private String editcategory;
	
	@FXML
	private Button editbudgetSubmitId;
	
	@FXML
	private TableView<Budget> tabViewId;
	
	@FXML
	private TableColumn<Budget, Double> amountTabId;

	@FXML
	private TableColumn<Budget, String> monthTabId;

	@FXML
	private TableColumn<Budget, Integer> yearTabId;

	@FXML
	private TableColumn<Budget, String> categoryTabId;
	

	@FXML
	private ComboBox<String> findCatId;
	
	@FXML
	private ComboBox<String> findMonthId;
	
	@FXML
	private ComboBox<Integer> findYearId;
    private ObservableList<Budget> budgetRows = FXCollections.observableArrayList();

    @FXML
    private Button editBudget;
    private Budget selectedBudget;
    private String userID;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		SessionManager session =SessionManager.getInstance();
		if (session !=null) {
			userID = session.getUserId();
		}
		try {
			this.budgetDAO = new BudgetDAOImpl();
			this.categoryDAO = new CategoryDAOImpl();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		monthId.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December");

		IntStream.rangeClosed(2023, 2030).forEach(yearId.getItems()::add);

		categoryId.getItems().addAll("Groceries", "Transport", "Rent", "Utilities", "Entertainment");

		monthId.setPromptText("Select Month");
		yearId.setPromptText("Select Year");
		categoryId.setPromptText("Select Category");
		amountTabId.setCellValueFactory(new PropertyValueFactory<>("amount"));
		monthTabId.setCellValueFactory(new PropertyValueFactory<>("month"));
		yearTabId.setCellValueFactory(new PropertyValueFactory<>("year"));		
		
		editmonthId.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December");

		IntStream.rangeClosed(2023, 2030).forEach(edityearId.getItems()::add);

		editcategoryId.getItems().addAll("Groceries", "Transport", "Rent", "Utilities", "Entertainment");

		editmonthId.setPromptText("Select Month");
		edityearId.setPromptText("Select Year");
		editcategoryId.setPromptText("Select Category");
	}

	@FXML
	public void addBudget() throws SQLException {
		Stage currentStage = (Stage) amountId.getScene().getWindow();
		try {
			// Validate input
			if (amountId.getText().isEmpty() || monthId.getValue() == null || yearId.getValue() == null
					|| categoryId.getValue() == null) {
				AlertUtils.showAlert(Alert.AlertType.WARNING, "Please fill all fields", currentStage);
				return;
			}

			// Parse amount
			try {
				amount = Double.parseDouble(amountId.getText());
				if (amount <= 0) {
					AlertUtils.showAlert(Alert.AlertType.ERROR, "Amount must be a positive number", currentStage);
	                return;
	            }
			} catch (NumberFormatException e) {
				AlertUtils.showAlert(Alert.AlertType.ERROR, "Amount must be a valid number", currentStage);
                return;
            }
			
			
			category = categoryId.getValue();
			rs = categoryDAO.getCategoryByName(category);
			while(rs.next()) {
				catId = rs.getString(1);
			}

			Budget budget = new Budget();		
			budget.setAmount(amount);
			budget.setBudgetId(UUID.randomUUID().toString());
			budget.setRemainingAmount(amount);
			budget.setMonth(monthId.getValue());
			budget.setYear(yearId.getValue());
			budget.setCategory(catId);
			budget.setUserID(userID);

			result = budgetDAO.createBudget(budget);
			if (result == 1) {
				AlertUtils.showAlert(Alert.AlertType.CONFIRMATION, "New budget created successfully", currentStage);
			}
			else {
				AlertUtils.showAlert(Alert.AlertType.CONFIRMATION, "Failed to create a new budget", currentStage);
            }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void goBack() {
		Stage stage = (Stage) backButtonId.getScene().getWindow();
		stage.close();

	}
	
	@FXML
	private void editTabClick() throws ClassNotFoundException, SQLException {
		
		findMonthId.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December");

		IntStream.rangeClosed(2023, 2030).forEach(findYearId.getItems()::add);

		findCatId.getItems().addAll("Groceries", "Transport", "Rent", "Utilities", "Entertainment");

		findMonthId.setPromptText("Select Month");
		findYearId.setPromptText("Select Year");
		findCatId.setPromptText("Select Category");
		findYearId.valueProperty().addListener((observable, oldValue, newValue) -> {
	        try {
				updateTable();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    });
		findMonthId.valueProperty().addListener((observable, oldValue, newValue) -> {
	        try {
				updateTable();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    });

	    findYearId.valueProperty().addListener((observable, oldValue, newValue) -> {
	        try {
				updateTable();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    });
	}
	
	private void updateTable() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {			
			if (findCatId.getValue() != null &&  findMonthId.getValue()!= null &&  findYearId.getValue()!= null) {
				String category = findCatId.getValue();
				String month = findMonthId.getValue();
				int year = findYearId.getValue();
				boolean hasRecords = populateBudgetTable(category, month, year);
				if (!hasRecords) {
					budgetRows.clear();
				}
			} else {
				budgetRows.clear();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private boolean populateBudgetTable(String category, String month, int year) throws ClassNotFoundException {
		Stage currentStage = (Stage) amountId.getScene().getWindow();
	    try {
	    	List<Budget> budgetList = new ArrayList<Budget>();
	        // Fetch data from the database
	    	rs = categoryDAO.getCategoryByName(category);
	    	while(rs.next()) {
				catId = rs.getString(1);
			}
	        rs = budgetDAO.getBudgetsByFilters(month, year, catId, userID);
	        while(rs.next()) {
	        	Budget budget = new Budget();
	        	budget.setBudgetId(rs.getString(5));
	        	budget.setAmount(rs.getDouble(1));
	        	budget.setMonth(rs.getString(2));
	        	budget.setYear(rs.getInt(3));
	        	budget.setCategory(rs.getString(4));
	        	budget.setUserID(rs.getString(6));
	        	System.out.println(rs.getDouble(1));
	        	
	        	budgetList.add(budget);
	        }
	        // Create an ObservableList for the table
	        if (!budgetList.isEmpty())
	        {
	        	budgetRows.clear();
	        	budgetRows.addAll(budgetList);
		        tabViewId.setItems(budgetRows);
		        return true;
	        }	        
	        	

	    } catch (SQLException e) {
	        e.printStackTrace();
	        AlertUtils.showAlert(Alert.AlertType.ERROR, "Failed to load budgets", currentStage);
	    }
	    return false;
	    
	    
	}
	
	@FXML
    private void handleDeleteAction(ActionEvent event) {
		Stage currentStage = (Stage) amountId.getScene().getWindow();
        selectedBudget = tabViewId.getSelectionModel().getSelectedItem();
        if (selectedBudget != null) {
            try {
                boolean deleted = budgetDAO.deleteBudget(selectedBudget.getBudgetId());
                if (deleted) {
                    budgetRows.remove(selectedBudget);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
        	AlertUtils.showAlert(Alert.AlertType.WARNING, "Select a budget to delete", currentStage);
        }
    }

	@FXML
    private void handleEditAction() throws ClassNotFoundException, SQLException {
		Stage currentStage = (Stage) amountId.getScene().getWindow();
		if (editamountId.getText().isEmpty() || editmonthId.getValue() == null || edityearId.getValue() == null
				|| editcategoryId.getValue() == null) {
			AlertUtils.showAlert(Alert.AlertType.WARNING, "Please fill all fields", currentStage);
			return;
		}

		// Parse amount
		try {
			amount = Double.parseDouble(editamountId.getText());
			if (amount <= 0) {
				AlertUtils.showAlert(Alert.AlertType.ERROR, "Amount must be a positive number", currentStage);
                return;
            }
			
			category = editcategoryId.getValue();
			rs = categoryDAO.getCategoryByName(category);
			while(rs.next()) {
				catId = rs.getString(1);
			}
			boolean edited = budgetDAO.editBudget(selectedBudget.getBudgetId(), amount, catId, editmonthId.getValue(), edityearId.getValue());
			if (edited) {
				AlertUtils.showAlert(Alert.AlertType.CONFIRMATION, "Budget edited successfully. Choose the filters again to fetch the updated budget", currentStage);
				editamountId.clear(); // Clear the text field
				editmonthId.setPromptText("Select Month");
				edityearId.setPromptText("Select Year");
				editcategoryId.setPromptText("Select Category");
            }
			
		} catch (NumberFormatException e) {
			AlertUtils.showAlert(Alert.AlertType.ERROR, "Amount must be a valid number", currentStage);
            return;
        }
    }
	
	@FXML
	private void populateEditFields() {
		Stage currentStage = (Stage) amountId.getScene().getWindow();
		selectedBudget = tabViewId.getSelectionModel().getSelectedItem();
        if (selectedBudget != null) {
        	editamountId.setText(String.valueOf(selectedBudget.getAmount()));
        }
        else {
        	AlertUtils.showAlert(Alert.AlertType.WARNING, "Select a budget to edit", currentStage);
        }
	    
	}
}
