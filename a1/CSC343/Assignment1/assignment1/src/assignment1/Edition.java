package assignment1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Edition {
	private int course_code;
	private String faculty_code, time, start_date;
	
	public boolean getEdition(Connection conn) throws SQLException {
		if (!this.validate()) {
			System.out.println("Edition fields not set.");
			return false;
		}
		
		Statement st = null;
		
		String query = "select * from edition where course_code=\""+this.course_code+"\"and faculty_code=\""+this.faculty_code+"\"and time=\""+this.time+"\" and start_date=\""+this.start_date+"\"";
		
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (!rs.next()) {return false;}
			return true;
		} catch (SQLException e) {
			SQLError.show(e);
		} finally {
			if (st != null) {st.close();}
		}
		return false;
	}
	
	public List<String> getTopic(Connection conn) throws SQLException {
		List<String> topics = new ArrayList<String>();
		
		Statement st = null;
		String query = "select topic_name from has_topic where course_code=\"" + this.course_code + "\" and faculty_code=\"" + this.faculty_code +"\"";
		
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			int cols = rs.getMetaData().getColumnCount();
			
			while (rs.next()) {
				for (int i=0; i<cols; i++) {
					topics.add((String) rs.getObject(i+1));
				}
			}
		}  catch (SQLException e ) {
			SQLError.show(e);
		       // JDBCTutorialUtilities.printSQLException(e);
		} finally {
		        if (st != null) { st.close(); }
		}
		return topics;
	}
	
	public List<String> getSkill(Connection conn) throws SQLException {
		List<String> skills = new ArrayList<String>();
		
		Statement st = null;
		String query = "select skill_name from teaches where course_code=\""+this.course_code+"\" and faculty_code=\"" + this.faculty_code +"\"";
		
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			int cols = rs.getMetaData().getColumnCount();
			
			while (rs.next()) {
				for (int i=0; i< cols; i++)
					skills.add((String) rs.getObject(i+1));
			}
		} catch (SQLException e ) {
			SQLError.show(e);
		       // JDBCTutorialUtilities.printSQLException(e);
		} finally {
		        if (st != null) { st.close(); }
		}
		return skills;
	}
	
	
	private boolean validate(){
		final Set<String> TIME = new HashSet<String>(Arrays.asList(new String[] {"morning", "day", "evening"}));
		if (course_code < 100)
			return false;
		if (!faculty_code.matches("([A-Z]{3})"))
			return false;
		if (!TIME.contains(this.time))
			return false;
		if (!start_date.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))
			return false;
		return true;
	}
	

	public int getCourse_code() {
		return course_code;
	}

	public void setCourse_code(int course_code) {
		this.course_code = course_code;
	}

	public String getFaculty_code() {
		return faculty_code;
	}

	public void setFaculty_code(String faculty_code) {
		this.faculty_code = faculty_code;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	
}
