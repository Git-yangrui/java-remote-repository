package com.newtouch.util;


public class ArrayReturnIndex<P> {
	
	public static <P> int returnIndexLocation(P[] P,P P1){
		P p2=null;
		int indexLocation=0;
		for (int i = 0; i < P.length; i++) {
			indexLocation=i;
			p2=P[i];
			if(P1==p2){
			   break;
			}
		}
		return indexLocation;
		
	}
}
