package com.newtouch.serial.thread.confinement;

public class SampleClient {
    
	// �ڴ���ֻ����һ������
	private static final MessageFileDownLoader DOWNLOAD;
    
	static {
	   //ֻ��ʼ����һ��usernam �� ftpserver������    ��һ���û�����
		DOWNLOAD=new MessageFileDownLoader("", "192.168.190.190", "", "");
		//����init()���� ���ȥrun���� ������dorun��������  ��Ȼ���������
		DOWNLOAD.init();
	}
	public static void main(String[] args) {
		DOWNLOAD.download("");             
	}
}
