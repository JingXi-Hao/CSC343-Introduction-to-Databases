package assignment1;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class Enrols {
	private String username;
	private int course_code;
	private String faculty_code, time, start_date;
	private long grade;
	private int satisfaction;
	
	private String insert = "insert into enrols_in values(?,?,?,?,?,?,?)";
	private String update = "update enrols_in set grade=?, satisfation=? where student=? and course_number=? and faculty_code=? and time=? and start_date=?";
	
	PreparedStatement stmt = null;
	
	public boolean isEnroled(Connection conn) throws SQLException {
		Statement st = null;
		String query = "select * from enrols_in where student=\""+this.username+"\" and course_number="+this.course_code+" and faculty_code=\""+this.faculty_code+"\" and start_date=\""+this.start_date+"\" and time=\"" + this.time + "\"";
		
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
		return true;
	}
	
	public void enterExperience(Connection conn) throws SQLException {
		if (!this.validate()){
			System.out.println("Fields not set.");
			System.exit(1);
		}
		
		if (this.isEnroled(conn)){
			try {
				stmt = conn.prepareStatement(update);
				stmt.setLong(1, this.grade);
				stmt.setInt(2, this.satisfaction);
				stmt.setString(3, this.username);
				stmt.setInt(4,  this.course_code);
				stmt.setString(5, this.faculty_code);
				stmt.setString(6, this.start_date);
				stmt.setString(7, this.time);
				stmt.execute();
			} catch (SQLException e) {
				SQLError.show(e);
			} finally {
				if (stmt != null) {stmt.close();}
			}
		} else {
			try {
				stmt = conn.prepareStatement(insert);
				stmt.setString(1, this.username);
				stmt.setInt(2, this.course_code);
				stmt.setString(3, this.faculty_code);
				stmt.setString(4, this.start_date);
				stmt.setString(5, this.time);
				stmt.setLong(6, this.grade);
				stmt.setInt(7, this.satisfaction);
			} catch (SQLException e) {
				SQLError.show(e);
			} finally {
				if (stmt != null) {stmt.close();}
			}
		}
	}
	
	public List<String> getInstructor(Connection conn) throws SQLException {
		List<String> instructors = new ArrayList<String>();
		
		Statement st = null;
		String query = "select instructor_name from instructs where course_code=" + this.course_code + " and faculty_code=\"" + this.faculty_code + "\" and time=\"" + this.time + "\" and start_date=\"" + this.start_date + "\"";
		
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			int cols = rs.getMetaData().getColumnCount();
			
			while (rs.next()) {
				for (int i=0; i<cols; i++) {
					instructors.add(((String) rs.getObject(i+1)));
					//System.out.println(rs.getObject(i+1));
				}
			}
			//return instructors;
			} catch (SQLException e ) {
				SQLError.show(e);
			       // JDBCTutorialUtilities.printSQLException(e);
			} finally {
			        if (st != null) { st.close(); }
			}
		return instructors;
	}
	

	private boolean validate(){
		if (this.grade < 0 || this.grade > 100)
			return false;
		if (this.satisfaction < 1 || this.satisfaction > 5)
			return false;
		return true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public long getGrade() {
		return grade;
	}

	public void setGrade(long grade) {
		this.grade = grade;
	}

	public int getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	
	
	

}
