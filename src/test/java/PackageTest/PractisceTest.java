

package PackageTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//import com.textmagic.sms.exception.ServiceBackendException;
//import com.textmagic.sms.exception.ServiceTechnicalException;

public class PractisceTest {
	
	public String dbClass;
	public String url;
	public String userName;
	public String password;
	
	public Connection conn;
	public Statement stmt;	
	
	public String connectDB(String dbUrl, String user, String pass) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		try{
			
		dbClass = "com.mysql.jdbc.Driver";
		url = dbUrl;
		userName = user;
		password = pass;
		
		Class.forName(dbClass).newInstance(); // Load MySql JDBC Driver
		conn = DriverManager.getConnection(url,userName,password); // Get connection to DB
		stmt = conn.createStatement(); // Create Statement object (Import Statement java.sql)
		return"Successful";
		}
		
		catch (Exception e){
			
			return e.getMessage();
		}
	} 
	// end of connectDB
	
	
	public String initializeScriptExec(String script_name, String site_name) throws SQLException{
		
		try{
			
			String initialize = "INSERT INTO script_execution (sites_scripts_id, status_id) VALUES "
					+ "((SELECT sites_scripts_id FROM sites_scripts WHERE script_id = "
					+ "(SELECT script_id FROM scripts WHERE script_name = '"+script_name+"') AND site_id = "
					+ "(SELECT site_id FROM sites WHERE site_name = '"+site_name+"')), "
					+ "(SELECT status_id FROM ref_status where status_value = 'Fail'))";
			
			stmt.execute(initialize);
			return "Successful";
			}
		
		catch(Exception e){
			return e.getMessage();
		}
	} 
	// end of initializeScriptExec
	
	
	public String recordStepResponseTime (String site_name, String script_name, String step_name, String runtime_step_name, String status, String response_text, double res_time_sec) throws SQLException{
		
		try{
		
		String insert = "INSERT INTO step_execution "
				+ "(script_execution_id, step_id, step_runtime_name, status_id, response_text, response_time) "
				+ "VALUES "
				+ "((SELECT script_execution_id FROM script_execution WHERE sites_scripts_id "
				+ "=(SELECT sites_scripts_id FROM sites_scripts WHERE script_id "
				+ "=(SELECT script_id FROM scripts WHERE script_name = '"+ script_name +"')AND site_id "
				+ "=((SELECT site_id FROM sites WHERE site_name = '"+ site_name +"')))"
				+ "ORDER BY executed_on DESC LIMIT 1),(SELECT step_id FROM script_steps WHERE step_name = '"+ step_name +"' AND script_id "
				+ "= (SELECT script_id FROM scripts WHERE script_name = '"+ script_name +"')),'"+ runtime_step_name +"'"
				+ ",(SELECT status_id FROM ref_status where status_value = '"+ status +"'),'"+ response_text +"',"+res_time_sec+")";
		
		stmt.execute(insert);
		return "Successful";
		}
		
		catch (Exception e){
			
			return e.getMessage();
		}
	}
	// end of recordStepResponseTime
	
	
	public String updateScriptExecSuccess(String script_name, String site_name) throws SQLException{
		
		try{
			String updateScriptExecution = "UPDATE script_execution SET Description = 'Successful', status_id = "
					+ "(SELECT status_id FROM ref_status where status_value = 'Pass') WHERE sites_scripts_id = "
					+ "(SELECT sites_scripts_id FROM sites_scripts WHERE script_id = "
					+ "(SELECT script_id FROM scripts WHERE script_name = '"+script_name+"') AND site_id = "
					+ "((SELECT site_id FROM sites WHERE site_name = '"+site_name+"'))) ORDER BY executed_on DESC LIMIT 1";
			
			stmt.execute(updateScriptExecution);
			return "Successful";
			}
		
		catch(Exception e){
			return e.getMessage();
		}
	} 
	// end of updateScriptExecSuccess
	
	
	public String recordStepExecFailure (String site_name, String script_name, String step_name, String runtime_step_name, String status, String response_text) throws SQLException{
		
		try{
			
			double res_time_sec = 0.00;
		
			String insertFailure = "INSERT INTO step_execution "
					+ "(script_execution_id, step_id, step_runtime_name, status_id, response_text, response_time) "
					+ "VALUES ((SELECT script_execution_id FROM script_execution WHERE sites_scripts_id "
					+ "=(SELECT sites_scripts_id FROM sites_scripts WHERE script_id "
					+ "=(SELECT script_id FROM scripts WHERE script_name = '"+ script_name +"')AND site_id "
					+ "=((SELECT site_id FROM sites WHERE site_name = '"+ site_name +"')))"
					+ "ORDER BY executed_on DESC LIMIT 1),(SELECT step_id FROM script_steps WHERE step_name = '"+ step_name +"' AND script_id "
					+ "= (SELECT script_id FROM scripts WHERE script_name = '"+ script_name +"')),'"+ runtime_step_name +"'"
					+ ",(SELECT status_id FROM ref_status where status_value = '"+ status +"'),'"+ response_text +"',"+res_time_sec+")";
		
			stmt.execute(insertFailure);
			return "successful";
		}
		
		catch (Exception e){
			return e.getMessage();
		}
	}
	// end of recordStepExecFailure
	
	public String updateScriptExecutionFailure (String site_name, String script_name, String step_name) throws SQLException{
		
		try{
		String updateScriptExecFailure = "UPDATE script_execution SET Description = "
				+ "'Error on Step: "+step_name+"' WHERE sites_scripts_id = "
				+ "(SELECT sites_scripts_id FROM sites_scripts WHERE script_id = "
				+ "(SELECT script_id FROM scripts WHERE script_name = '"+script_name+"') AND site_id = "
				+ "((SELECT site_id FROM sites WHERE site_name = '"+site_name+"'))) ORDER BY executed_on DESC LIMIT 1";
		
		stmt.execute(updateScriptExecFailure);
		return "Successful";
		}
		
		catch (Exception e){
			return e.getMessage();
		}
		
	} 
	//end of updateScriptExecutionFailure
	
	public String closeConnections(){
		try {
		stmt.close();
		conn.close();
		return "Successful";
		}
		
		catch (Exception e){
			return e.getMessage();
		}
	}

}
