package product;

import java.util.Properties;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Iterator;

public class ProductMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProductProcess pp = new ProductProcess();
		pp.process();
	}

}

class Product{
	private int num;
	private String name;
	private int price;
	
	public Product(int num, String name, int price) {
		super();
		this.num = num;
		this.name = name;
		this.price = price;
	}
	
	Product(String name, int price){
		this.name=name;
		this.price=price;
	}
	
	public String toString() {
		return num+"/"+name+"/"+price;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price=price;
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num=num;
	}
}

class ProductProcess
{
	Scanner sc = new Scanner(System.in);
	boolean bFlag = true;
	ServiceImpl service = new ServiceImpl();
	
	void process()
	{
		System.out.println("============================");
		System.out.println("=======제품관리 프로그램=========");
		System.out.println("============================");
		while(bFlag){
			System.out.println("메뉴를 선택해주세요.");
			System.out.println("1. 종료 2. 제품등록 3. 제품수정 4. 제품삭제 5. 제품검색 6. 전체 제품 목록");
			int menu = sc.nextInt();
			
			switch(menu)
			{
			case 1: //종료
				bFlag = false;
				service.close();
				System.out.println("프로그램종료");
				
			case 2: //추가
				addProduct();
				break;
				
			case 3://갱신
				modifyProduct();
				break;
				
			case 4://삭제
				deleteProduct();
				break;
			
			case 5://검색
				searchProduct();
				break;
			
			case 6:
				searchAll();
				break;
			}
		}
	}
	
	void searchAll(){
		System.out.println("--------------------------");
		System.out.println("전체 제품 목록");
		ArrayList<Product> al = service.selectAll();
		
		Iterator<Product> iter = al.iterator();
		while(iter.hasNext())
		{
			Product p = iter.next();
			System.out.println(p);
		}
		System.out.println("--------------------------");
	}
	
	void searchProduct() {
		searchAll();
		System.out.print("검색하고자 하는 제품의 번호는");
		int num = sc.nextInt();
		Product p = service.select(num);
		System.out.println("검색결과는 다음과 같다.");
		if(p==null)
		{
			System.out.println("존재하지 않는 제품번호입니다.");
		}
		else {
			System.out.println(p);
		}
	}
	
	void deleteProduct() {
		searchAll();
		System.out.println("삭제하고자 하는 제품 번호는");
		int num = sc.nextInt();
		if(service.select(num)==null) {
			System.out.println("존재하지 않는 제품번호입니다.");
			return;
		}
		if(service.delete(num))
		{
			System.out.println("삭제성공");
		}
		else {
			System.out.println("삭제실패");
		}
	}
	
	void modifyProduct() {
		searchAll();
		System.out.print("수정하고 싶은 제품 번호는");
		int num = sc.nextInt();
		if(service.select(num)==null) {
			System.out.println("존재하지 않는 제품 번호입니다.");
		}
		
		System.out.print("제품명");
		String name = sc.next();
		System.out.println("가격은");
		int price = sc.nextInt();
		
		if(service.update(new Product(num, name, price))) {
			System.out.println("수정 성공");
		}
		else {
			System.out.println("수정 실패");
		}
	}
	
	void addProduct() {
		System.out.println("추가할 제품의 이름은");
		String name = sc.next();
		System.out.println("추가할 제품의 가격은");
		int price = sc.nextInt();
		
		Product p = new Product(name, price);
		
		if(service.insert(p)) {
			System.out.println("제품을 추가하였습니다.");
		}
		else {
			System.out.println("제품 추가 실패");
		}
	}
	
}

class DBConnect{
	
	String jdbc_driver="com.mysql.cj.jdbc.Driver";
	String jdbc_url="jdbc:mysql://localhost:3306";
	private static DBConnect db = new DBConnect();
	private Connection conn=null;
	
	private DBConnect() {
		try {
		Class.forName(jdbc_driver);
		}catch (ClassNotFoundException e) {
			System.out.println("jdbc 드라이버 로드 실패");
			e.printStackTrace();
		}
		
		Properties properties = new Properties();
		properties.setProperty("user", "root");
		properties.setProperty("password", "1234");
		properties.setProperty("autoReconnect", "true");
		properties.setProperty("severTimezone", "UTC");
		properties.setProperty("useSSL", "false");
		properties.setProperty("allowPublicKeyRetrieval", "true");
		
		try {
			conn=DriverManager.getConnection(jdbc_url, properties);
			System.out.println("connection 연결 성공");
		}catch(SQLException e) {
			System.out.println("connection 실패");
			e.printStackTrace();
		}

		try {

			Statement stmt = conn.createStatement();
			stmt.execute("create database exists addrdb;");
			stmt.execute("use addrdb;");
			stmt.execute("create table if not exists product (num int auto_increment promary key, name varchar(10), price int);");
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("statement 성공");
		}
		
	}
	
	public static DBConnect getInstance() {
		return db;
	}
	
	public Connection getConnection() {
		return conn;
	}
}

interface Dao{
	boolean insert(Product p);
	boolean update(Product p);
	boolean delete(int num);
	Product select(int num);
	ArrayList<Product> selectAll();
	void close();
}

class DaoImpl implements Dao{
	private Connection conn;
	String sql;
	
	public boolean insert(Product p) {
		sql = "insert into product (name, price) values(?, ?)";
		PreparedStatement pstmt=null;
		int num=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p.getName());
			pstmt.setInt(2, p.getPrice());
			num=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(num<=0) {
			return false;
		}
		return true;
	}
	
	public boolean update(Product p) {
		sql = "update product set name=?, price=?, where num=?";
		PreparedStatement pstmt;
		int num=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p.getName());
			pstmt.setInt(2, p.getPrice());
			pstmt.setInt(3, p.getNum());
			
			num=pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(num<=0)
			return false;
		else
			return true;
	}
	
	public boolean delete(int num) {
		int result = 0;
		sql = "delete from product where num=?";
		
		try {
			PreparedStatement pstmt =conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(result<=0) {
			return false;
		}
		else
			return true;
	}
	
	public Product select(int num) {
		ResultSet rs=null;
		Product p=null;
		sql="select *from product where num=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				int numData = rs.getInt("num");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				
				p=new Product(numData, name, price);
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return p;
		
	}
	
	public ArrayList<Product> selectAll(){
		ArrayList<Product> al=new ArrayList<Product>();
		ResultSet rs;
		sql = "select *from product";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				int num=rs.getInt("num");
				String name=rs.getString("name");
				int price = rs.getInt("price");
				
				Product p = new Product(num, name, price);
				al.add(p);
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return al;
	}
	
	public void close()
	{
		try {
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}

interface Service{
	boolean insert(Product p);
	boolean delete(int num);
	boolean update(Product p);
	ArrayList<Product> selectAll();
	Product select(int num);
	void close();
}

class ServiceImpl implements Service{
	DaoImpl dao = new DaoImpl();
	
	public void close() {
		dao.close();
	}
	
	public boolean insert(Product p) {
		return dao.insert(p);
	}
	
	public boolean delete(int num) {
		return dao.delete(num);
	}
	
	public boolean update(Product p) {
		return dao.update(p);
	}
	 
	public ArrayList<Product> selectAll(){
		return dao.selectAll();
	}
	
	public Product select(int num) {
		return dao.select(num);
	}
}