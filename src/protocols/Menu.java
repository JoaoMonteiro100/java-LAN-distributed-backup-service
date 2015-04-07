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
	
	//function to run when the program starts
	public void start() {
		connectionData();
		fileData();
	}
	
	public String whatNext() {
		Scanner answer = new Scanner( System.in );
	    System.out.print("What do you want to do? Backup (B), Restore (R), Delete (D) or Reallocate space (S)?");
	    char choice = answer.next().charAt(0);
	    
	    //if backup
	    if (choice == 'B' || choice == 'b') {
		    Scanner answer2 = new Scanner( System.in );
		    System.out.print("What file do you want to backup?");
		    /*
		     * THIS
		     * CANNOT
		     * HAPPEN!!!!
		     * 
		     * nao podemos pedir ao utilizador para escrever por extenso um fileID
		     * (VER ENUNCIADO)
		     * |
		     * |
		     * V
		     * programa pode mostrar dir e dar opções 1, 2, 3, etc. c fileIDs
		     */
		    return("BACKUP "+answer.nextLine());
	    }
	    
	    //if restore
	    else if (choice == 'R' || choice == 'r') {
		    Scanner answer2 = new Scanner( System.in );
		    System.out.print("Which file do you want to restore?");
		    /*
		     * THIS
		     * CANNOT
		     * HAPPEN!!!!
		     * 
		     * nao podemos pedir ao utilizador para escrever por extenso um fileID
		     * (VER ENUNCIADO)
		     * |
		     * |
		     * V
		     * programa pode mostrar dir e dar opções 1, 2, 3, etc. c fileIDs
		     */
		    return("RESTORE "+answer.nextLine());
	    }
	    
	    //if delete
	    else if (choice == 'D' || choice == 'd') {
		    Scanner answer2 = new Scanner( System.in );
		    System.out.print("Which file do you want to restore?");
		    /*
		     * THIS
		     * CANNOT
		     * HAPPEN!!!!
		     * 
		     * nao podemos pedir ao utilizador para escrever por extenso um fileID
		     * (VER ENUNCIADO)
		     * |
		     * |
		     * V
		     * programa pode mostrar dir e dar opções 1, 2, 3, etc. c fileIDs
		     */
		    return("DELETE "+answer.nextLine());
	    }
	    
	    //if reallocate
	    else if (choice == 'S' || choice == 's') {
		    Scanner answer2 = new Scanner( System.in );
		    System.out.print("How much space do you want to allocate for memory (in GB)?");
		    return("REALLOCATE "+answer.nextLine());
	    }
	    
	    //default
	    else
	    	return("");
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
