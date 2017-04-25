package com.newtouch.thread.specific.storage;

import java.lang.ref.WeakReference;
import java.security.Provider;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ManagedThreadLocal<T> extends ThreadLocal<T>{
	/**
	 * 使用弱引用 ，以防止内存泄漏
	 * 使用volatile 修饰保证内存可见性
	 * 
	 */
	private static volatile Queue<WeakReference<ManagedThreadLocal<?>>> instance=
	          new ConcurrentLinkedQueue<WeakReference<ManagedThreadLocal<?>>>();
	private ThreadLocal<T> threadLocal;
	
	private ManagedThreadLocal(final InitValueProvider<T> ibp){
		
		threadLocal=new ThreadLocal<T>(){
		    @Override
		    protected T initialValue() {
		    	return ibp.initialValue();
		    }
		};
	}
	public static <T> ManagedThreadLocal<T> newInstance(final
		InitValueProvider<T> ivp){
		ManagedThreadLocal<T> mtl=new ManagedThreadLocal<T>(ivp);
		instance.add(new WeakReference<ManagedThreadLocal<?>>(mtl));
		return mtl;
	}
	
	
	public static <T> ManagedThreadLocal<T> newInstance(){
		return newInstance(new ManagedThreadLocal.InitValueProvider<T>());
	}
	
	public T get(){
		return threadLocal.get();
		
	}
	
	public void set(T value){
		threadLocal.set(value);
	}
	
	public void remove(){
		if(null!=threadLocal){
			threadLocal.remove();
			threadLocal=null;
		}
	}
	
	public static void removeAll(){
		WeakReference<ManagedThreadLocal<?>>  wr;
		ManagedThreadLocal<?> mtl;
		while(null!=(wr=instance.poll())){
			mtl=wr.get();
			if(null!=mtl){
				mtl.remove();	
			}
		}
	}
	public static class InitValueProvider<T>{
		
		protected  T initialValue(){
			return null;
		}
	}

}
