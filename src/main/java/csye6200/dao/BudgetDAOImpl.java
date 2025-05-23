package main.java.csye6200.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import main.java.csye6200.models.Budget;
import main.java.csye6200.utils.SessionManager;

public class BudgetDAOImpl {
	
	private Connection con;
	private int result;
	private ResultSet rs;

	public BudgetDAOImpl() throws ClassNotFoundException, SQLException {
		this.con = DatabaseConnect.getInstance().getConnection();
	}
	
	
	public int createBudget(Budget budget) throws ClassNotFoundException {
		try {
			String query = "INSERT INTO BUDGET VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, budget.getBudgetId());
			st.setDouble(2, budget.getAmount());
			st.setDouble(3, budget.getRemainingAmount());
			st.setString(4, budget.getMonth());
			st.setInt(5, budget.getYear());
			st.setString(6, budget.getCategory());
			st.setString(7, budget.getUserID());
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result>0 ? 1:0;
		
	}
	
	public ResultSet getBudgetDetails(String month, int year, String userID) throws ClassNotFoundException {
		try {
			String query = "select bud.category_id, amount, remaining_amount, cat.category_name, budget_id from budget bud\r\n"
					+ "left join category cat\r\n"
					+ "on bud.category_id = cat.category_id\r\n"
					+ "where month=? and year=? and USERID=?";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, month);
			st.setInt(2, year);
			st.setString(3, userID);
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
		
	}

	public ResultSet getBudgetsByFilters(String month, int year, String categoryId, String userID) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		try {
			String query = "SELECT AMOUNT, MONTH, YEAR, CATEGORY_ID, BUDGET_ID, USERID FROM BUDGET WHERE MONTH=? AND YEAR=? AND CATEGORY_ID=? AND USERID=? ";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, month);
			st.setInt(2, year);
			st.setString(3, categoryId);
			st.setString(4, userID);
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public boolean deleteBudget(String string) throws SQLException {
        boolean isDeleted = false;
        String query = "DELETE FROM BUDGET WHERE BUDGET_ID = ?";

        try {
        	PreparedStatement st = con.prepareStatement(query);
            st.setString(1, string);
            int rowsAffected = st.executeUpdate();
            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDeleted;
    }

	public boolean editBudget(String budgetId, Double amount, String catId, String month, int year) {
		// TODO Auto-generated method stub
		boolean isEdited = false;
        String query = "UPDATE BUDGET SET AMOUNT=?, MONTH=?, YEAR=?, CATEGORY_ID=? WHERE BUDGET_ID = ?";

        try {
        	PreparedStatement st = con.prepareStatement(query);
            st.setDouble(1, amount);
            st.setString(2, month);
            st.setInt(3, year);
            st.setString(4, catId);
            st.setString(5, budgetId);
            
            int rowsAffected = st.executeUpdate();
            isEdited = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isEdited;
	}

	public ResultSet getTotalExpenseByCategory(String catId, String month, int year, String userID) {
		// TODO Auto-generated method stub
		try {
		String query = "SELECT SUM(AMOUNT) FROM TRANSACTIONS\r\n"
				+ "WHERE CATEGORY_ID=?\r\n"
				+ "AND TRIM(TO_CHAR(TRANSACTION_DATE, 'MONTH')) = ?\r\n"
				+ "AND TRIM(TO_CHAR(TRANSACTION_DATE, 'YYYY')) = ?\r\n"
				+ "AND TRANSACTION_TYPE='EXPENSE' AND USERID=?";
		PreparedStatement st = con.prepareStatement(query);
		st.setString(1, catId);	
		st.setString(2, month.toUpperCase());
		st.setInt(3, year);
		st.setString(4, userID);	
		rs = st.executeQuery();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return rs;
	}
	
	public ResultSet getAllBudgetDetails(String userID) throws SQLException, ClassNotFoundException {
		 
		try {
			String query = "SELECT amount, remaining_amount FROM budget where userid=? ";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, userID);
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return rs;
 
	}


	public boolean setRemainingBudget(String budget_id, double remainingBudget) {
		// TODO Auto-generated method stub
		boolean isUpdated = false;
		String query = "UPDATE BUDGET SET REMAINING_AMOUNT=? WHERE BUDGET_ID = ?";

        try {
        	PreparedStatement st = con.prepareStatement(query);
            st.setDouble(1, remainingBudget);
            st.setString(2, budget_id);
            int rowsAffected = st.executeUpdate();
            isUpdated = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUpdated;
		
	}
	
	

}
