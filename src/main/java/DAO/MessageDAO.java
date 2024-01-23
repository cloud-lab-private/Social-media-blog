package DAO;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MessageDAO {
    
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql= "SELECT * from message;";
            PreparedStatement sm = connection.prepareStatement(sql);
            ResultSet rs = sm.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text")
                ,rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }


    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{

            String sql = "SELECT * from message WHERE posted_by = ?; ";
            PreparedStatement sm = connection.prepareStatement(sql);

            sm.setInt(2, id);

            ResultSet rs = sm.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text")
                ,rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message createMessage(Message message){
        Connection connection= ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (message_id, posted_by, message_text,time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement sm = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            sm.setInt(1,message.message_id);
            sm.setInt(2, message.posted_by);
            sm.setString(3, message.message_text);
            sm.setLong(4,message.time_posted_epoch);

            sm.executeUpdate();
            ResultSet primaryKeyResultSet = sm.getGeneratedKeys();
            if(primaryKeyResultSet.next()){
                int auto_message_id = (int) primaryKeyResultSet.getLong(1);
                return new Message(auto_message_id,message.posted_by,message.message_text,message.time_posted_epoch);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
/*
 * update message
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
