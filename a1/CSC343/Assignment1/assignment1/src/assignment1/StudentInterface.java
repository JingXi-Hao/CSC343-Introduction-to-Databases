package assignment1;
import java.util.*;
import java.io.*;
import java.sql.*;


public class StudentInterface {
	public static final String PROGRAM_NAME = "StudentInterface";
	
	
	
	public static void main(String[] args) throws IOException, SQLException{
		System.out.println("Please select the database you want to connect:");
		System.out.println("1.SQLite | 2.PostGRE");
		
		Scanner scanner = new Scanner(System.in);
		
		int con = scanner.nextInt();
		
		Properties props = new Properties();
		java.sql.Connection conn = DBConnection.getConnection(props, con);
		
		System.out.println("===============================");
    	System.out.println("Welcome to the Student Interface app");
    	System.out.println("===============================");
    	
    	Student p = null;
    	int lgin = 0;
    	while (lgin == 0){
    		System.out.println("Please select:\n" + "1: login | 2: create a new account");
    		//System.out.println();
    		int choice_0 = scanner.nextInt();
    		
    		
    		switch(choice_0) {
    			case 1:
    				System.out.println("Please enter your username:");
    				p = new Student();
    				p.setUsername(scanner.next());
    				String s = p.printInfo(conn);
    				if (s == "") {
    					System.out.println("User not exists, please check your username or crete a new account.");
    				} else {
    					System.out.println(s);
    					System.out.println("You are now logged in.");
    					lgin = 1;
    				}
    				break;
    				
    			case 2:
    				System.out.println("Create a new account: ");
    				p = new Student();
    				System.out.print("username: ");
    				p.setUsername(scanner.next());
    				System.out.print("year_of_birth: ");
    				p.setYear_of_birth(scanner.nextInt());
    				System.out.print("month_of_birth: ");
    				p.setMonth_of_birth(scanner.nextInt());
    				System.out.print("start_year: ");
    				p.setStart_year(scanner.nextInt());
    				System.out.print("start_month: ");
    				p.setStart_month(scanner.nextInt());
    				System.out.print("country_of_birth");
    				p.setCountry_of_birth(scanner.next());
    				System.out.print("age: ");
    				p.setAge(scanner.nextInt());
    				System.out.print("gender: ");
    				p.setGender(scanner.next());
    				
    				p.addto(conn);
    				
    				System.out.println(p.printInfo(conn));
    				System.out.println("You are now logged in.");
    				lgin = 1;
    				break;
    				
    			default:
    				System.out.print("Invalid input.");
    				break;
    		}
    	} 
    	
    	while (true) {
    		System.out.println("Please select: ");
    		System.out.println("1. list avaliable courses | 2. enter course expereieces");
    		int choice_1 = scanner.nextInt();
    		Edition ed = null;
    		Enrols en = null;
    		Rank r = null;
    		Topic t = null;
    		Skill s = null;
    		
    		switch(choice_1) {
    			case 1:
    				PrintEditions.print(conn);
    				break;
    				
    			case 2:
    				ed = new Edition();
    				
    				System.out.println("Please select an edition.");
    				System.out.println("course_code: ");
    				ed.setCourse_code(scanner.nextInt());
    				System.out.println("faculty_code: ");
    				ed.setFaculty_code(scanner.next());
    				System.out.println("time: (morning, day, evening)");
    				ed.setTime(scanner.next());
    				System.out.println("start_date: (yyyy-mm-dd)");
    				ed.setStart_date(scanner.next());
    				
    				if (!ed.getEdition(conn)) {
    					System.out.println("Edition not in database");
    					break;
    				}
    				
    				en = new Enrols();
    				en.setUsername(p.getUsername());
    				en.setCourse_code(ed.getCourse_code());
    				en.setFaculty_code(ed.getFaculty_code());
    				en.setTime(ed.getTime());
    				en.setStart_date(ed.getStart_date());
    				
    				System.out.println("Your final grade: ");
    				en.setGrade((long) scanner.nextDouble());
    				System.out.println("Your over all satisfaction: ");
    				en.setSatisfaction(scanner.nextInt());
    				
    				en.enterExperience(conn);
    				
    				List<String> instructors = en.getInstructor(conn);
    				int numIns = (instructors.toArray()).length;
    				System.out.println("Your instructors for this course are:");
    				System.out.println(Arrays.toString(instructors.toArray()));
    				
    				for (int i=0; i<numIns; i++) {
    					System.out.println("Please rate for Porfessor: " + (instructors.get(i)));
    					r = new Rank();
    					r.setProf(instructors.get(i));
    					r.setStudent(en.getUsername());
    					r.setMark(scanner.nextInt());
    					r.enterRank(conn);
    				}
    				
    				System.out.println("Please rate the tpoics of this course");
    				List<String> topics = ed.getTopic(conn);
    				int numTps = (topics.toArray()).length;
    				
    				for(int i=0; i<numTps; i++) {
    					System.out.println("Please rate for topic: " + topics.get(i));
    					t = new Topic();
    					t.setName(topics.get(i));
    					t.setUsername(r.getStudent());
    					System.out.println("Your interest before taking the course: ");
    					t.setBf(scanner.nextInt());
    					System.out.println("Your interest after taking the course: ");
    					t.setAf(scanner.nextInt());
    					t.enterTopic(conn);
    				}
    				
    				System.out.println("Please rate skills of this course.");
    				List<String> skills = ed.getSkill(conn);
    				int numSks = (skills.toArray()).length;
    				
    				for (int i=0; i<numSks; i++) {
    					System.out.println("Please rate for this skill: " + skills.get(i));
    					s = new Skill();
    					s.setName(skills.get(i));
    					s.setUsername(en.getUsername());
       					System.out.println("Your skill before taking the course: ");
    					s.setBf(scanner.nextInt());
    					System.out.println("Your skill after taking the course: ");
    					s.setAf(scanner.nextInt());
    					s.enterSkill(conn);
    				}
    				
    				break;
    				
    			default:
    				System.out.println("Invalid input.");
    				break;
    		}
    	}

	}
}
