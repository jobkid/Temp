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
		System.out.println("관리자 메뉴입니다. [1]영화 등록 [2]영화 추가 [3]영화 목록 [4]영화 삭제 [5]전체 삭제 [6]좌석설정 [7] 메뉴로 돌아가기");
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
			System.out.println("영화 제목, 장르, 연령대를 5번 작성해주세요. 현재 "+i+"번 남았습니다. 작성은 '1' 종료는 '0'");
			
			Scanner sc = new Scanner(System.in);
			int tmp=sc.nextInt();
			if(tmp==1)
			{
				Scanner moviesc = new Scanner(System.in);
				
				System.out.print("영화를 등록합니다.");
				String title=moviesc.nextLine();
				
				System.out.print("장르를 입력해주세요.");
				String genre=moviesc.nextLine();
				
				System.out.print("연령대를 입력해주세요.");
				String age=moviesc.nextLine();
			
				System.out.println("========================================");
				
				bw.write(System.currentTimeMillis()+", "+title+", "+genre+", "+age);
				bw.newLine();
			}
			else if(tmp==0)
			{
				System.out.println("영화등록이 취소되었습니다.");
			}
			i--;
		}
		bw.close();
		movie.close();
		
		System.out.println("영화 등록 종료\n==================");
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
		
		System.out.println("영화 목록 보기 종료\n============================");
		managerMenu();
	}
	
	void addMovie() throws IOException
	{
		int data=0;
//		movieList();
		File fileName = new File("src/project/movies.txt");
		System.out.println("===================");
		System.out.println("영화를 추가하시겠습니까?");
		
		//String fileName="src/project/movie.txt";
		FileReader fr=new FileReader(fileName);
		BufferedReader br=new BufferedReader(fr);
		
		FileWriter fw=new FileWriter(fileName, true);
		BufferedWriter bw=new BufferedWriter(fw);
		int i=2;
		while(i>0)
		{	
			Scanner sc=new Scanner(System.in);
			
			System.out.print("영화를 등록합니다.");
			String movie=sc.nextLine();
			
			System.out.print("장르를 입력해주세요.");
			String genre=sc.nextLine();
			
			System.out.print("연령대를 입력합니다.");
			String age=sc.nextLine();
			
			bw.write(System.currentTimeMillis()+", "+movie+", "+genre+", "+age);
			bw.newLine();
			//System.out.println();
			
			
			i--;
			
		}
		bw.close();
		fw.close();
		
		System.out.println("영화 추가 종료\n===========================");
		managerMenu();
	}
	
	void delMovie() throws IOException
	{
		System.out.println("삭제할 영화를 선택해주세요.\n================");
		ArrayList<String> movielists=new ArrayList<String>();
		
		FileReader fr=new FileReader(fileName);
		BufferedReader br=new BufferedReader(fr);
		
		int j=0;
		int num=1;
		while((dataStr=br.readLine())!=null)
		{	
			
			movielists.add(dataStr);
			
			System.out.println("["+(num++)+"]"+"번 "+movielists.get(j++));
			
		}
		
		System.out.println("번호를 입력해주세요.\n===========");
	
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
		System.out.println("영화 삭제 종료\n================================");
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
		//2자 배열로 극장 좌석을 만든다. 행은 알파멧, 열은 숫자로 만든다.
		System.out.println("좌석의 행과 열을 지정해주세요.");
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
