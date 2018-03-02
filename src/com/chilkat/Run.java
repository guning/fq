package com.chilkat;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("get configs ...");
		Config config = new Config("config.txt");
		try {
			CrossWall crossWall = new CrossWall(config);
			System.out.println("connectting ...");
			crossWall.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
