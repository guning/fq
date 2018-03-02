package com.chilkat;

import java.io.*;
import java.util.LinkedList;

public class Config {
	LinkedList<String> lines = new LinkedList<String>();

	public Config(String filename) {
		try {
			InputStream f = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(f));
			String str = null;
			while ((str = br.readLine()) != null) {
				this.lines.add(str);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getRemoteHost() throws Exception {
		if (this.lines.get(0).length() != 0) {
			return this.lines.get(0);
		} else {
			throw new Exception("remote host must be config");
		}
	}

	public int getRemotePort() throws Exception {
		if (this.lines.get(1).length() != 0) {
			return Integer.parseInt(this.lines.get(1));
		} else {
			throw new Exception("remote port must be config");
		}
	}
	
	public String getRemoteUserName() throws Exception {
		if (this.lines.get(2).length() != 0) {
			return this.lines.get(2);
		} else {
			throw new Exception("remote username must be config");
		}
	}
	
	public String getRemotePwd() throws Exception {
		if (this.lines.get(3).length() != 0) {
			return this.lines.get(3);
		} else {
			throw new Exception("remote password must be config");
		}
	}
	
	public int getLocalPort() {
		return this.lines.size() >= 5 ? Integer.parseInt(this.lines.get(4)) : -1;
	}

}
