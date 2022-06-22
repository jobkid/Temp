package product;

import java.util.Properties;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
	
	public void setNum() {
		
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