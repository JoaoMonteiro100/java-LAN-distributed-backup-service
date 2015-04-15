package protocols;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//deals with .txt files that record backups and restores
public class FileManager {
	private String backupDir = "backup.txt"; //contains info about backed up files
	private String restoreDir = "restore.txt"; //contains info about chunks saved
	private String space = " ";
	
	public FileManager() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(backupDir), "utf-8"))) {
		   writer.write("NEW SERVICE STARTED AT " + dateFormat.format(date) + "\n");
		}
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(restoreDir), "utf-8"))) {
		   writer.write("NEW SERVICE STARTED AT " + dateFormat.format(date) + "\n");
		}
	}
	
	private boolean doesBackupExist(String filename) throws FileNotFoundException, IOException {
		File f = new File(backupDir);
		if(isFileRegistered(filename, f)) {
			return true;
		}
		return false;
	}
	
	private boolean doesRestoreExist(char[] fileId) throws FileNotFoundException, IOException {
		File f = new File(restoreDir);
		if(isFileRegistered(fileId.toString(), f)) {
			return true;
		}
		return false;
	}
	
	private boolean isFileRegistered(String file, File f) throws FileNotFoundException, IOException {
		if(f.exists() && !f.isDirectory()) {
			try(BufferedReader br = new BufferedReader(new FileReader(f))) {
			    for(String line; (line = br.readLine()) != null; ) {
			        String[] components = line.split(space);
			        if(components[0].equals(file)) {
			        	return true;
			        }
			    }
			}
		}
		return false;
	}

	public void addBackup(String filename, char[] fileId, int repDegree, int chunkNo) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		if(!doesBackupExist(filename)) {
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream(backupDir), "utf-8"))) {
			   writer.write(filename + space + fileId.toString() + space + Integer.toString(repDegree) + space + Integer.toString(chunkNo));
			}
		}
	}
	
	public void addRestore(char[] fileId, int actualReplication, int repDegree, int chunkNo) throws FileNotFoundException, UnsupportedEncodingException, IOException {
		if(!doesRestoreExist(fileId)) {
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream(backupDir), "utf-8"))) {
			   writer.write(fileId.toString() + space + Integer.toString(actualReplication) + space + Integer.toString(repDegree) + space + Integer.toString(chunkNo));
			}
		}
	}
	
	public void removeBackup(String filename) {
		
	}
	
	public void removeRestore(char[] fileId) {
		
	}
	
	public void changeRepDegree(char[] fileId) {
		
	}
	
	public String getBackupDir() {
		return backupDir;
	}
	public void setBackupDir(String backupDir) {
		this.backupDir = backupDir;
	}
	public String getRestoreDir() {
		return restoreDir;
	}
	public void setRestoreDir(String restoreDir) {
		this.restoreDir = restoreDir;
	}
}
