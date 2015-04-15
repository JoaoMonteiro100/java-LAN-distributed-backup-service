package protocols;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	
	public void addBackup(String filename, char[] fileId, int repDegree, int chunkNo) {
		
	}
	
	public void addRestore(char[] fileId, int actualReplication, int repDegree, int chunkNo) {
		
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
