package project;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class MemberMain {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		
		Member reserv = new Member();
		//reserv.getSeat();
		ArrayList<String> a=new ArrayList<String>();
		reserv.reserveMovie();
		//System.out.println(reserv.reserveList(a));

	}
	

}

class Member{
	File reservation = new File("src/project/reservaion.txt");
	File movielists = new File("src/project/movies.txt");
	ArrayList<String> rsv=new ArrayList<String>();
	String data;
	/*
	private void setSeat()
	{
		System.out.println("�¼��� �������ּ���.");
		char a=' ';
		int sum=1;
		Scanner sc=new Scanner(System.in);
		int row = sc.nextInt();
		int column =sc.nextInt();
		
		int[][] seat=new int[row][column];
		for(int i=0; i<seat.length; i++)
		{
			int j;
			for(j=0; j<seat[i].length; j++)
			{
				seat[i][j]=j+sum;
			}
			char seatrow=(char)(i+65);
			
			System.out.println("===============");
			System.out.println(seatrow+Arrays.toString(seat[i]));
		}
	}
	*/
	
	
	void readMovies() throws IOException
	{
		
		FileReader fr = new FileReader(movielists);
		BufferedReader br = new BufferedReader(fr);
		while((data=br.readLine())!=null)
		{
			rsv.add(data);
			System.out.println(data);
		}
		
		br.close();
		fr.close();
	}
	
	private ArrayList <String> reserveList(ArrayList <String> rsv) throws IOException
	{
		this.rsv=rsv;
		FileReader fr = new FileReader(movielists);
		BufferedReader br = new BufferedReader(fr);
		int i=0;
		int j=0;
		while((data=br.readLine())!=null)
		{
			rsv.add(data);
			System.out.println("["+(++j)+"]"+this.rsv.get(i++));
			
		}
		return  rsv;
		
	}
	
	void reserveMovie() throws IOException
	{
		this.rsv=rsv;
		FileReader fr = new FileReader(movielists);
		BufferedReader br = new BufferedReader(fr);
		int i=0;
		int j=0;
		//System.out.println(br);
		
		/*
		System.out.println("��ȭ ���");
		while((data=br.readLine())!=null)
		{
			rsv.add(data);
			System.out.println("["+(++j)+"]"+this.rsv.get(i++));	
		}
		*/
		System.out.println("��ȭ�� ����Ͻðڽ��ϱ�? ����� [1]");
		Scanner sc = new Scanner(System.in);
		
		int num=sc.nextInt();
		if(num==1)
		{
			System.out.println("��ȭ ���");
			while((data=br.readLine())!=null)
			{
				rsv.add(data);
				System.out.println("["+(++j)+"]"+this.rsv.get(i++));	
			}
			System.out.println("===============================");
			System.out.println("��ȭ ��ȣ�� �������ּ���.");
			FileWriter fw=new FileWriter(reservation);
			BufferedWriter bw=new BufferedWriter(fw);
			
			int list=sc.nextInt();
			while(br.readLine()!=null)
			{
				
				bw.write(rsv.get(list));
				bw.newLine();
				System.out.println("�߰� �����Ͻðڽ��ϱ�? [1]�� [0]�ƴϿ�");
				
				int select = sc.nextInt();
				
				if(select==1)
				{}
				
				else if(select==0)
					System.out.println("���Ÿ� �����մϴ�.");
				break;
			}
			bw.close();
			fw.close();
		}
		else;
			
	}
	
}