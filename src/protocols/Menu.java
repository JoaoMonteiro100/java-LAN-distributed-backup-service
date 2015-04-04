package protocols;

import java.util.Scanner;

public class Menu {
	private String backupDir;
	private String ip; //has to be a string because of '.'
	private int port;
	private double space;
		
	//function to acquire knowledge on connection specificities
	public void connectionData() {
	    Scanner answer = new Scanner( System.in );
	    System.out.print("What is the IP?");
	    ip = answer.nextLine();

	    Scanner answer2 = new Scanner( System.in );
	    System.out.print("What is the port?");
	    port = answer2.nextInt();
	}
	
	//function to know what to backup and how much space to reserve for chunks
	public void fileData() {
	    Scanner answer = new Scanner( System.in );
	    System.out.print("What is the directory of files to backup?");
	    backupDir = answer.nextLine();
	    
	    Scanner answer2 = new Scanner( System.in );
	    System.out.print("How much space do you want to allocate in memory (in GB) for chunks?");
	    space = answer2.nextDouble();
	}

	//function to change space allocated for chunks
	public void updateMemory() {
	    Scanner answer = new Scanner( System.in );
	    System.out.print("Do you want to update the space allocated for backup chunks (Y/N)?");
	    char choice = answer.next().charAt(0);
	    
	    if (choice == 'Y' || choice == 'y') {
		    Scanner answer2 = new Scanner( System.in );
		    System.out.print("How much space do you want to allocate for memory (in GB)?");
		    space = answer2.nextDouble();
	    }
	}
	
	//function to run when the program starts
	public void start() {
		connectionData();
		fileData();
	}

	//Gets & Sets
	public String getBackupDir() {
		return backupDir;
	}

	public void setBackupDir(String backupDir) {
		this.backupDir = backupDir;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public double getSpace() {
		return space;
	}

	public void setSpace(double space) {
		this.space = space;
	}
}
