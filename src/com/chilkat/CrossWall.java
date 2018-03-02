package com.chilkat;

import java.io.*;

import com.chilkatsoft.*;

public class CrossWall {

	static {
		try {
			System.loadLibrary("chilkat");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}
	// remote configure
	String remoteHost;
	int remotePort;
	String remoteUserName;
	String remotePwd;
	// localhost
	int localPort;

	public CrossWall(Config config) {
		try {
			this.remoteHost = config.getRemoteHost();
			this.remotePort = config.getRemotePort();
			this.remoteUserName = config.getRemoteUserName();
			this.remotePwd = config.getRemotePwd();
			this.localPort = 1080;
			if (config.getLocalPort() != -1) {
				this.localPort = config.getLocalPort();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		// Starting in v9.5.0.49, all Chilkat classes can be unlocked at once at the
		// beginning of a program
		// by calling UnlockBundle. It requires a Bundle unlock code.
		CkGlobal chilkatGlob = new CkGlobal();
		boolean success = chilkatGlob.UnlockBundle("Anything for 30-day trial.");
		if (success != true) {
			System.out.println(chilkatGlob.lastErrorText());
			return;
		}

		// This example requires Chilkat version 9.5.0.50 or greater.
		CkSshTunnel tunnel = new CkSshTunnel();

		// Connect to an SSH server and establish the SSH tunnel:
		success = tunnel.Connect(this.remoteHost, this.remotePort);
		if (success != true) {
			System.out.println(tunnel.lastErrorText());
			return;
		}

		// Authenticate with the SSH server via a login/password
		// or with a public key.
		// This example demonstrates SSH password authentication.
		success = tunnel.AuthenticatePw(this.remoteUserName, this.remotePwd);
		if (success != true) {
			System.out.println(tunnel.lastErrorText());
			return;
		}

		// Indicate that the background SSH tunnel thread will behave as a SOCKS proxy
		// server
		// with dynamic port forwarding:
		tunnel.put_DynamicPortForwarding(true);

		// We may optionally require that connecting clients authenticate with our SOCKS
		// proxy server.
		// To do this, set an inbound username/password. Any connecting clients would be
		// required to
		// use SOCKS5 with the correct username/password.
		// If no inbound username/password is set, then our SOCKS proxy server will
		// accept both
		// SOCKS4 and SOCKS5 unauthenticated connections.

		// tunnel.put_InboundSocksUsername("guning");
		// tunnel.put_InboundSocksPassword("8023loveLOVE");

		// Start the listen/accept thread to begin accepting SOCKS proxy client
		// connections.
		// Listen on port 1080.
		success = tunnel.BeginAccepting(this.localPort);
		if (success != true) {
			System.out.println(tunnel.lastErrorText());
			return;
		}
		while (true) {
			try {
				InputStreamReader is_reader = new InputStreamReader(System.in);
				String str = new BufferedReader(is_reader).readLine();
				if (str.equals("exit")) {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Stop the background listen/accept thread:
		boolean waitForThreadExit = true;
		success = tunnel.StopAccepting(waitForThreadExit);
		if (success != true) {
			System.out.println(tunnel.lastErrorText());
			return;
		}

		// Close the SSH tunnel (would also kick any remaining connected clients).
		success = tunnel.CloseTunnel(waitForThreadExit);
		if (success != true) {
			System.out.println(tunnel.lastErrorText());
			return;
		}
		System.out.println("end");

	}
}
