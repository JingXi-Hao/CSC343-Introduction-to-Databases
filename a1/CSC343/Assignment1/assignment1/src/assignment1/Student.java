package assignment1;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class Student {
	private static final String TABLE_NAME = "Person";
	
	private String username, gender, country_of_birth;
	private int year_of_birth, month_of_birth, start_year, start_month, age;
	
	private String insert = "insert into student " + "values(?,?,?,?,?,?,?,?)";
	//private String update = "update student set year_of_birth=?, month_of_birth=?, start_year=?, start_month=?, country_of_birth=?, age=?, gender=? where username=?";
	//private String delete = "delete from student where name=?";
	
	PreparedStatement stmt = null;
	
	public void addto (Connection conn) throws SQLException {
		if (!this.validate()) {
			System.out.println("Student fields not set");
			System.exit(1);
		}
		
		try {
			stmt = conn.prepareStatement(insert);
			stmt.setString(1, this.username);
			stmt.setInt(2, this.year_of_birth);
			stmt.setInt(3, this.month_of_birth);
			stmt.setInt(4, this.start_year);
			stmt.setInt(5, this.start_month);
			stmt.setString(6, this.country_of_birth);
			stmt.setInt(7, this.age);
			stmt.setString(8, this.gender);
			stmt.execute();
		} catch (SQLException e) {
			SQLError.show(e);
		} finally {
			if (stmt != null) {stmt.close();}
		}
	}
	
	public String printInfo (Connection conn) throws SQLException {
		Statement statement = null;
		String query = "select * from student where \"username\" = " + "\"" + this.username + "\"";
		String s = "";
		
		try {
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			int cols = rs.getMetaData().getColumnCount();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			
			while (rs.next()) {
				for (int i=0; i < cols; i++) {
					s = s.concat(rsmd.getColumnName(i+1) + ": " + rs.getObject(i+1) + "\n");					
				}
			}
		} catch (SQLException e) {
			SQLError.show(e);
		} finally {
			if (statement != null) {statement.close();}
		}
		return s;
	}
	
	private boolean validate () {
		final Set<String> GENDERS = new HashSet<String>(Arrays.asList(new String[] {"male", "female"}));
		if (this.username == null)
			return false;
		if (this.age <= 0)
			return false;
		if (this.gender == null || !GENDERS.contains(this.gender))
			return false;
		if (this.country_of_birth == null)
			return false;
		if (this.year_of_birth <= 0 || this.year_of_birth < 1000 || this.month_of_birth <=0 || this.month_of_birth > 12)
			return false;;
		if (this.start_year <= 0 || this.start_year < 1000 || this.start_month <= 0 || this.start_month > 12)
			return false;
		return true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry_of_birth() {
		return country_of_birth;
	}

	public void setCountry_of_birth(String country_of_birth) {
		this.country_of_birth = country_of_birth;
	}

	public int getYear_of_birth() {
		return year_of_birth;
	}

	public void setYear_of_birth(int year_of_birth) {
		this.year_of_birth = year_of_birth;
	}

	public int getMonth_of_birth() {
		return month_of_birth;
	}

	public void setMonth_of_birth(int moth_of_birth) {
		this.month_of_birth = moth_of_birth;
	}

	public int getStart_year() {
		return start_year;
	}

	public void setStart_year(int start_year) {
		this.start_year = start_year;
	}

	public int getStart_month() {
		return start_month;
	}

	public void setStart_month(int start_month) {
		this.start_month = start_month;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}
