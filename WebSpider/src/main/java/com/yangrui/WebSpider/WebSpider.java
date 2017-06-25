package com.yangrui.WebSpider;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSpider {
	public static void main(String[] args) throws Exception {
		URL url = null;
		URLConnection urlconn = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String regex = "http://[\\w+\\.?/?]+\\.[A-Za-z]+";
		Pattern p = Pattern.compile(regex);
		try {
			url = new URL("http://www.sina.com.cn/");
			urlconn = url.openConnection();
			pw = new PrintWriter(new FileWriter("e:/url.txt"), true);// 这里我们把收集到的链接存储在了E盘底下的一个叫做url的txt文件中
			br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			String buf = null;
			while ((buf = br.readLine()) != null) {
				Matcher buf_m = p.matcher(buf);
				while (buf_m.find()) {
					pw.println(buf_m.group());
				}
			}
			System.out.println("获取成功！");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pw.close();
		}
        //foreach
		List<String> readAllLines = Files.readAllLines(Paths.get("e:/url.txt"), Charset.defaultCharset());
		System.out.println(readAllLines.size());
		URL urlSub = null;
		pw = new PrintWriter(new FileWriter("e:/urlSon.txt"), true);
		int count=0;
		for (String urlline : readAllLines) {
			count++;
			System.out.println("第"+count+"次"+"URL is"+urlline);
			urlSub = new URL(urlline);
			URLConnection openConnection = urlSub.openConnection();
			try {
				br = new BufferedReader(new InputStreamReader(openConnection.getInputStream()));
				String buffer = null;
				while ((buffer = br.readLine()) != null) {
					Matcher buf_m = p.matcher(buffer);
					while (buf_m.find()) {
						//注释了此处 会生成大文件
						//pw.println(buf_m.group());
					}
				}
			} catch (Exception e) {
				continue;
			}
		}

	}
}