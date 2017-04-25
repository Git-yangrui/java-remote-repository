package com.newtouch.serial.thread.confinement;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

public class ReusableCodeExzample {
    public static void main(String[] args) throws Exception {
	   SomeService ss=new SomeService();
	   ss.init();
	   Future<String> result=ss.doSomething("serial Thread", 1);
	   Thread.sleep(50);
	   System.out.println(result.get());
	   ss.shutdown();
	}
	
	 static class Task{
		public final String message;
		public final int id;
		public Task(String message,int id){
		   this.id=id;
		   this.message=
				   message;
		}
	}
	
	private static class SomeService extends AbstractSerilizer<Task, String>{
		public SomeService(){
			super(new ArrayBlockingQueue<Runnable>(100), new TaskProcessor<Task, String>() {
				public String doProcess(Task task) throws Exception {
					System.out.println("["+task.id+"]"+task.message);
					return task.message+"accepted";
				}
			});
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Task makeTask(Object... args) {
			String message=(String)args[0];
			int id=(Integer) args[1];
			return new Task(message, id);
			//return null;
		}
		public Future<String> doSomething(String message,int id) throws Exception{
			Future<String> result=null;
			result=service(message,id);
			return result;
			
		}
	}
	
	
}
