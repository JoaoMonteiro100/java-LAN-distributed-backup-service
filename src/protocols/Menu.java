package protocols;

import java.util.Scanner;

public class Menu {
	private static String backupDir;
	private static double space;
	
	private static String INET_ADDR_MC;
	private static String INET_ADDR_MDB;
	private static String INET_ADDR_MDR;
	
	private static int MC_PORT;
    private static int MDB_PORT;
    private static int MDR_PORT;
    
	//function to acquire knowledge on connection specificities
	public static void connectionData() {
	    Scanner answer = new Scanner( System.in );
	    System.out.println("What are the IP's and ports? (Order: MC, MDB, MDR)\n(Example: 224.0.0.3:8887 224.0.0.4:8888 224.0.0.5:8889)");
	    String str = answer.nextLine();
	    
	    //split by the spaces
	    String[] splitStr = str.split(" ");
	    
	    //split by the colon
	    String[] MC = splitStr[0].split(":");
	    String[] MDB = splitStr[1].split(":");
	    String[] MDR = splitStr[2].split(":");
	    
	    INET_ADDR_MC = MC[0];
	    MC_PORT = Integer.parseInt(MC[1]);
	    INET_ADDR_MDB = MDB[0];
	    MDB_PORT = Integer.parseInt(MDB[1]);
	    INET_ADDR_MDR = MDR[0];
	    MDR_PORT = Integer.parseInt(MDR[1]);
	}
	
	//function to know what to backup and how much space to reserve for chunks
	public static void spaceAllocation() {
	    Scanner answer2 = new Scanner( System.in );
	    System.out.println("How much space do you want to allocate in memory (in GB) for chunks?");
	    space = answer2.nextDouble();
	}
	
	//function to run when the program starts
	public static void start() {
		connectionData();
		spaceAllocation();
	}
	
	public static String nextAction() {
		Scanner answer = new Scanner( System.in );
	    System.out.println("What do you want to do? Backup (B), Restore (R), Delete (D), Reallocate space (S) or Exit (E)?");
	    char choice = answer.next().charAt(0);
	    
	    //if backup
	    if (choice == 'B' || choice == 'b') {
		    Scanner answer2 = new Scanner( System.in );
		    System.out.println("What file do you want to backup?");
		    String file = answer2.nextLine();
		    
		    Scanner answer3 = new Scanner( System.in );
		    System.out.println("What degree of replication do you want?");
		    String rep = answer3.nextLine();
		    
		    return("BACKUP " + file + " " + rep);
	    }
	    
	    //if restore
	    else if (choice == 'R' || choice == 'r') {
		    Scanner answer2 = new Scanner( System.in );
		    System.out.println("Which file do you want to restore?");
		    return("RESTORE " + answer2.nextLine());
	    }
	    
	    //if delete
	    else if (choice == 'D' || choice == 'd') {
		    Scanner answer2 = new Scanner( System.in );
		    System.out.println("Which file do you want to delete?");
		    return("DELETE "+answer2.nextLine());
	    }
	    
	    //if reallocate
	    else if (choice == 'S' || choice == 's') {
		    Scanner answer2 = new Scanner( System.in );
		    System.out.println("How much space do you want to allocate in memory (in GB)?");
		    return("REALLOCATE " + answer2.nextLine());
	    }
	    
	  //if reallocate
	    else if (choice == 'E' || choice == 'e') {
		    System.exit(0);
		    return("");
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
		Menu.backupDir = backupDir;
	}

	public double getSpace() {
		return space;
	}

	public void setSpace(double space) {
		Menu.space = space;
	}

	public static String getINET_ADDR_MC() {
		return INET_ADDR_MC;
	}

	public static void setINET_ADDR_MC(String iNET_ADDR_MC) {
		INET_ADDR_MC = iNET_ADDR_MC;
	}

	public static String getINET_ADDR_MDB() {
		return INET_ADDR_MDB;
	}

	public static void setINET_ADDR_MDB(String iNET_ADDR_MDB) {
		INET_ADDR_MDB = iNET_ADDR_MDB;
	}

	public static String getINET_ADDR_MDR() {
		return INET_ADDR_MDR;
	}

	public static void setINET_ADDR_MDR(String iNET_ADDR_MDR) {
		INET_ADDR_MDR = iNET_ADDR_MDR;
	}

	public static int getMC_PORT() {
		return MC_PORT;
	}

	public static void setMC_PORT(int mC_PORT) {
		MC_PORT = mC_PORT;
	}

	public static int getMDB_PORT() {
		return MDB_PORT;
	}

	public static void setMDB_PORT(int mDB_PORT) {
		MDB_PORT = mDB_PORT;
	}

	public static int getMDR_PORT() {
		return MDR_PORT;
	}

	public static void setMDR_PORT(int mDR_PORT) {
		MDR_PORT = mDR_PORT;
	}
}
