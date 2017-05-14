package com.citi.thread.immutable;

import java.math.BigInteger;
import java.util.Arrays;

public class OneVlaueCache {
	private final BigInteger lastnumber;
	private final BigInteger[] lastfactors;

	public OneVlaueCache(BigInteger lastnumber, BigInteger[] lastfactors) {
		this.lastfactors = Arrays.copyOf(lastfactors, lastfactors.length);
		this.lastnumber = lastnumber;
	}
	
	public BigInteger[] getFactors(BigInteger i){
		if(lastnumber==null||!lastnumber.equals(i)){
			return null;
		}else {
			return Arrays.copyOf(lastfactors, lastfactors.length);
		}
	}
}
