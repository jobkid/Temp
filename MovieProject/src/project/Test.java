package project;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ArrayList<String> movie=new ArrayList<String>();
		File filename = new File("src/project/movie.txt");
		FileReader fr1 = new FileReader(filename);
		BufferedReader br1 = new BufferedReader(fr1);
		
		//String data=" ";
		int a=5;
		
		System.out.println();
		String data;
		while((data=br1.readLine())!=null)
		{
			System.out.println(data);
			movie.add(br1.readLine());
		}
		Iterator<String> iter=movie.iterator();
		String str=null;
		while(iter.hasNext())
		{
			str=iter.next();
			System.out.println(str+23);
		}
		
		for(int i=0; i<movie.size(); i++)
		{
			System.out.println(i);
		}
		//System.out.println(movie[0]);
		
//		System.out.println(movie.add(str));
		//br.close();
		
		//fr.close();
		System.out.println("'============================");
		
		
		File filename2 = new File("src/project/movie.txt");
		FileReader fr2 = new FileReader(filename);
		BufferedReader br2 = new BufferedReader(fr2);
		
		while((data=br2.readLine())!=null)
		{
			movie.add(br2.readLine());
		}
		
		
		
		System.out.print(movie.toArray());
		
		File hellof=new File("src/project/HelloWorld.txt");
		FileReader world=new FileReader(hellof);
		BufferedReader java=new BufferedReader(world);
		
		List<String> alines=new ArrayList<String>();
		
		String aline="";
		
		while((aline=java.readLine())!=null)
		{
			alines.add(aline);
		}
		//java.close();
		for(int i=0; i<alines.size();i++)
		{
			System.out.println(alines.get(i));
		}
		
		alines.remove(1);
		for(int i=0; i<alines.size(); i++)
		{
			System.out.println(alines.get(i));
		}
		File hellof2=new File("src/project/HelloWorld.txt");
		FileReader world2=new FileReader(hellof);
		BufferedReader java2=new BufferedReader(world);
		System.out.println("============2222=======");
		while((data=java2.readLine())!=null)
		{
			System.out.println(data);
		}
		//br2.close();
		//fr2.close();
		java2.close();
		world2.close();
		

	}

}
