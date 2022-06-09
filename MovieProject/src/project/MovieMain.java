package project;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class MovieMain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Manager admin = new Manager();
		
		admin.getSeat();
	}

}

class Manager{
	
	File fileName=new File("src/project/movies.txt");
	File theaterseat=new File("src/project/theaterseat.txt");
	String dataStr;
	Manager(){
		
	}
	
	void managerMenu() throws IOException
	{
		System.out.println("������ �޴��Դϴ�. [1]��ȭ ��� [2]��ȭ �߰� [3]��ȭ ��� [4]��ȭ ���� [5]��ü ���� [6]�¼����� [7] �޴��� ���ư���");
		Scanner sc=new Scanner(System.in);
		int number=sc.nextInt();
		switch(number)
		{
		case 1: getMovie();
		break;
		
		case 2: addMovie();
		break;
		
		case 3: seeList();
		break;
		
		case 4: delMovie();
		break;
		
		case 5: delAll();
		break;
		
		case 6: getSeat();
		break;
		
		case 7: managerMenu();
		break;
		
		default:
		break;
		}
		
	}
	
	private void setMovie() throws IOException{
		
		FileWriter movie = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(movie);
		
		int i=5;
		while (i>0){
			System.out.println("��ȭ ����, �帣, ���ɴ븦 5�� �ۼ����ּ���. ���� "+i+"�� ���ҽ��ϴ�. �ۼ��� '1' ����� '0'");
			
			Scanner sc = new Scanner(System.in);
			int tmp=sc.nextInt();
			if(tmp==1)
			{
				Scanner moviesc = new Scanner(System.in);
				
				System.out.print("��ȭ�� ����մϴ�.");
				String title=moviesc.nextLine();
				
				System.out.print("�帣�� �Է����ּ���.");
				String genre=moviesc.nextLine();
				
				System.out.print("���ɴ븦 �Է����ּ���.");
				String age=moviesc.nextLine();
			
				System.out.println("========================================");
				
				bw.write(System.currentTimeMillis()+", "+title+", "+genre+", "+age);
				bw.newLine();
			}
			else if(tmp==0)
			{
				System.out.println("��ȭ����� ��ҵǾ����ϴ�.");
			}
			i--;
		}
		bw.close();
		movie.close();
		
		System.out.println("��ȭ ��� ����\n==================");
		managerMenu();
		

	}
	
	void getMovie() throws IOException
	{
		setMovie();
	}
	
	void seeList() throws IOException
	{
		try
		{
			FileReader fr=new FileReader(fileName);
			
			int data=0;
			while((data=fr.read())!=-1)
				System.out.print((char)data);
				fr.close();
		}catch(FileNotFoundException e) {e.printStackTrace();}
		
		System.out.println("��ȭ ��� ���� ����\n============================");
		managerMenu();
	}
	
	void addMovie() throws IOException
	{
		int data=0;
//		movieList();
		File fileName = new File("src/project/movies.txt");
		System.out.println("===================");
		System.out.println("��ȭ�� �߰��Ͻðڽ��ϱ�?");
		
		//String fileName="src/project/movie.txt";
		FileReader fr=new FileReader(fileName);
		BufferedReader br=new BufferedReader(fr);
		
		FileWriter fw=new FileWriter(fileName, true);
		BufferedWriter bw=new BufferedWriter(fw);
		int i=2;
		while(i>0)
		{	
			Scanner sc=new Scanner(System.in);
			
			System.out.print("��ȭ�� ����մϴ�.");
			String movie=sc.nextLine();
			
			System.out.print("�帣�� �Է����ּ���.");
			String genre=sc.nextLine();
			
			System.out.print("���ɴ븦 �Է��մϴ�.");
			String age=sc.nextLine();
			
			bw.write(System.currentTimeMillis()+", "+movie+", "+genre+", "+age);
			bw.newLine();
			//System.out.println();
			
			
			i--;
			
		}
		bw.close();
		fw.close();
		
		System.out.println("��ȭ �߰� ����\n===========================");
		managerMenu();
	}
	
	void delMovie() throws IOException
	{
		System.out.println("������ ��ȭ�� �������ּ���.\n================");
		ArrayList<String> movielists=new ArrayList<String>();
		
		FileReader fr=new FileReader(fileName);
		BufferedReader br=new BufferedReader(fr);
		
		int j=0;
		int num=1;
		while((dataStr=br.readLine())!=null)
		{	
			
			movielists.add(dataStr);
			
			System.out.println("["+(num++)+"]"+"�� "+movielists.get(j++));
			
		}
		
		System.out.println("��ȣ�� �Է����ּ���.\n===========");
	
		Scanner sc=new Scanner(System.in);
		int movenum=sc.nextInt();
		
		
		
		movielists.remove(movenum-1);
		
		FileWriter fw=new FileWriter(fileName);
		BufferedWriter bw=new BufferedWriter(fw);
		
		for(int i=0; i<movielists.size(); i++)
		{
			if(fileName.canWrite())
			{
				bw.write(movielists.get(i));
				bw.newLine();
			}
		}
		bw.flush();
		bw.close();
		fw.close();
		System.out.println("��ȭ ���� ����\n================================");
		managerMenu();
		
	}
	
	void delAll() throws IOException
	{
		fileName.delete();
		managerMenu();
	}
	
	void getSeat()throws IOException
	{
		setSeat();
	}
	
	private void setSeat() throws IOException
	{
		//2�� �迭�� ���� �¼��� �����. ���� ���ĸ�, ���� ���ڷ� �����.
		System.out.println("�¼��� ��� ���� �������ּ���.");
		char seatrow=' ';
		int sum=1;
		Scanner sc = new Scanner(System.in);
		
		int row=sc.nextInt();
		int column=sc.nextInt();
		
		int[][] seat=new int[row][column];
		
		FileWriter fw = new FileWriter(theaterseat);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for(int i=0; i<seat.length; i++)
		{
			int j;
			for(j=0; j<seat[i].length; j++)
			{
				seat[i][j]=sum+j;
			}
			seatrow=(char)(i+65);
			System.out.println(seatrow+Arrays.toString(seat[i]));
			
			bw.write(seatrow+Arrays.toString(seat[i]));
			bw.newLine();
			
		}
		bw.close();
		fw.close();
		
		
	}
	
}

class Login{
	
	Scanner sc = new Scanner(System.in);
	
	String id ="manager";
	String pwd="1234";
	
	void setLogin()
	{
		
		
		
		
		
		
		
	}
	
}
