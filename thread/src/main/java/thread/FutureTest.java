package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTest {
   public static void main(String[] args) {
	   FutureTest futureTest=new FutureTest();
	  Future<String>  sh=futureTest.get();
	  String string=null;
	  try {
		 System.out.println("----");
		 
		string = sh.get();
		
		 System.out.println("====");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println(string);  
	  
}
   public Future<String> get(){
	   
	   FutureTask<String> target=new FutureTask<String>(new Callable<String>() {
		   @Override
		public String call() throws Exception {
			Thread.sleep(2000);
			return "wolaile";
		}
	});
	 new Thread(target).start();
	   
	 
	 return target;
	   
   }
}
