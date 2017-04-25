package com.newtouh.threadGuardedSuspend.test;

import java.util.regex.Pattern;

public class PatternTest {
    public static void main(String[] args) {
		Pattern pattern=Pattern.compile("\\|");
		String s="12313 13131 23:23:1231|yydysds|12131323|wqewqe";
		String[] split = pattern.split(s,0);
		System.out.println(split);
    }
}
