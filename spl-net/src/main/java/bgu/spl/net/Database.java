package bgu.spl.net;


import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
class Student{
	private String username;
	private String password;
	private boolean admin;
	private boolean login;
	private List<Integer> courseNumberList; // sortedlist

	public Student(String username, String password, boolean admin) {
		this.username = username;
		this.password = password;
		this.admin = admin;
		login=false;
		courseNumberList= new List<Integer>();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public boolean isLogin() {
		return login;
	}

	public List<Integer> getCourselist() {
		return courseNumberList;
	}

	public boolean isRegisteredCourse(int courseNum){
		return courseNumberList.contains(courseNum);
	}

	public void setLogin(boolean b){
		login=b;
	}

}

class Course{
	private int courseNum;
	private String courseName;
	private List<Integer> KdamCoursesList;
	private int numOfMaxStudents;
	private int seatsAvailable; // available places
	private List<String> registeredStudents; //sorted list

	public Course(int courseNum, String courseName, List<Integer> kdamCoursesList, int numOfMaxStudents) {
		this.courseNum = courseNum;
		this.courseName = courseName;
		this.KdamCoursesList = kdamCoursesList;
		this.numOfMaxStudents = numOfMaxStudents;
		this.seatsAvailable=numOfMaxStudents;
		this.registeredStudents= new List<String>(); // names of students sorted

	}

	public int getCourseNum() {
		return courseNum;
	}

	public String getCourseName() {
		return courseName;
	}

	public List<Integer> getKdamCoursesList() {
		return KdamCoursesList;
	}

	public int getNumOfMaxStudents() {
		return numOfMaxStudents;
	}

	public boolean isAvailable(){
		return seatsAvailable>0;
	}

	public void register(String studentName){  // needs to be synchronized // need to check isAvailable before use
		seatsAvailable--;
		registeredStudents.add(studentName);
	}

	public void unregister(String studentName){ // needs to check if this student registered before use
		registeredStudents.remove(studentName);
		seatsAvailable++;
	}

}

public class Database {
	private ConcurrentHashMap<String, Student> users;
	private Vector<Course> courses;

	public static class DatabaseHolder{
		private static Database instance = new Database();

	}



	//to prevent user from creating new Database
	private Database() {
		//users= new ConcurrentHashMap<String, Student>();
		//courses = new Vector<Course>();
	}

	private Database(String coursesFilePath) {
		users= new ConcurrentHashMap<String, Student>();
		courses = new Vector<Course>();
		initialize(coursesFilePath);
	}


	public static Database getInstance(String coursesFilePath){
		DatabaseHolder.instance= new Database(coursesFilePath);
		return DatabaseHolder.instance;
	}


	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return DatabaseHolder.instance;
	}
	
	/**
	 * loades the courses from the file path specified 
	 * into the Database, returns true if successful.
	 */
	boolean initialize(String coursesFilePath) {
		// TODO: implement
		return false;
	}

	public boolean isRegistered(String username){
		Student s = users.get(username);
		if(s!=null && s.getUsername().equals(username))
			return true;
		return false;
	}

	public void adminRegister(String username, String password){
		Student s= new Student(username,password,true);
		users.put(username,s);
	}

	public void studentRegister(String username, String password){
		Student s= new Student(username,password,false);
		users.put(username,s);
	}

	public boolean loginApproved(String username, String password){
		Student s = users.get(username);
		if(s!=null && !s.isLogin() && s.getUsername().equals(username) && s.getPassword().equals(password))
				return true;
		return false;
	}

	public void loginStudent(String username){
		Student s = users.get(username);
		s.setLogin(true);
	}

	public void logoutStudent(String username){
		Student s = users.get(username);
		s.setLogin(false);
	}



	public void unregisterCourse()


}

