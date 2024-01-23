package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {
    
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try{
            String sql= "SELECT * from account;";
            PreparedStatement sm = connection.prepareStatement(sql);
            ResultSet rs = sm.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),rs.getString("username")
                ,rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }


    public Account getAccountById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{

            String sql = "SELECT * from account WHERE account_id = ?; ";
            PreparedStatement sm = connection.prepareStatement(sql);

            sm.setInt(1, id);

            ResultSet rs = sm.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),rs.getString("username")
                ,rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account createAccount(Account account){
        Connection connection= ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (account_id, username, password) VALUES (?,?,?);";
            PreparedStatement sm = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            sm.setInt(1,account.getAccount_id());
            sm.setString(2, account.getUsername());
            sm.setString(3,account.getPassword());

            sm.executeUpdate();
            ResultSet primaryKeyResultSet = sm.getGeneratedKeys();
            if(primaryKeyResultSet.next()){
                int auto_account_id = (int) primaryKeyResultSet.getLong(1);
                return new Account(auto_account_id,account.getUsername(),account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
/*
 * change old username to a new username
 */

    public void updateAccountName(String name, Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql ="update account set username = ? WHERE account_id = ? ;";
            PreparedStatement sm= connection.prepareStatement(sql);

            sm.setString(1,name);
            sm.setInt(2, account.getAccount_id());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    } 
/*
 * update password of a specified username
 */

    public void updatePassword(String password, Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql ="update account set password = ? WHERE username = ? ;";
            PreparedStatement sm= connection.prepareStatement(sql);

            sm.setString(1,password);
            sm.setString(2, account.getUsername());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    } 
    

}
