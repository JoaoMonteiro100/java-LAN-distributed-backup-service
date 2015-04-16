package protocols;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
	private String newline = "\r\n";
	
	public FileManager() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(backupDir), "utf-8"))) {
		   writer.write("NEW SERVICE STARTED AT " + dateFormat.format(date) + newline);
		}
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(restoreDir), "utf-8"))) {
		   writer.write("NEW SERVICE STARTED AT " + dateFormat.format(date) + newline);
		}
	}
	
	public FileManager(String bkpDir, String rstDir) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		backupDir = bkpDir;
		restoreDir = rstDir;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(backupDir), "utf-8"))) {
		   writer.write("NEW SERVICE STARTED AT " + dateFormat.format(date) + newline);
		}
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(restoreDir), "utf-8"))) {
		   writer.write("NEW SERVICE STARTED AT " + dateFormat.format(date) + newline);
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
			        if(components[0].trim().equals(file)) {
			        	return true;
			        }
			    }
			}
		}
		return false;
	}

	public void addBackup(String filename, char[] fileId, int repDegree, int chunkNo) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		if(!doesBackupExist(filename)) {
			Writer output;
			output = new BufferedWriter(new FileWriter(backupDir, true));
			output.append(filename + space + fileId.toString() + space + Integer.toString(repDegree) + space + Integer.toString(chunkNo) + newline);
			output.close();
		}
	}
	
	public void addRestore(char[] fileId, int actualReplication, int repDegree, int chunkNo) throws FileNotFoundException, UnsupportedEncodingException, IOException {
		if(!doesRestoreExist(fileId)) {
			Writer output;
			output = new BufferedWriter(new FileWriter(backupDir, true));
			output.append(fileId.toString() + space + Integer.toString(actualReplication) + space + Integer.toString(repDegree) + space + Integer.toString(chunkNo) + newline);
			output.close();
		}
	}
	
	public void removeBackup(String filename) throws FileNotFoundException, IOException {
		if(doesBackupExist(filename)) {
			File inputFile = new File(backupDir);
			File tempFile = new File("myTempFile.txt");

			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while((currentLine = reader.readLine()) != null) {
			    String[] splitLine = currentLine.split(space);
			    if(splitLine[0].trim().equals(filename)) continue;
			    writer.write(currentLine + "\n");
			}
			
			writer.close(); 
			reader.close(); 
			tempFile.renameTo(inputFile);
		}
	}
	
	public void removeRestore(char[] fileId) throws FileNotFoundException, IOException {
		if(doesRestoreExist(fileId)) {
			File inputFile = new File(restoreDir);
			File tempFile = new File("myTempFile.txt");

			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while((currentLine = reader.readLine()) != null) {
			    String[] splitLine = currentLine.split(space);
			    if(splitLine[0].trim().equals(fileId.toString())) continue;
			    writer.write(currentLine + "\n");
			}
			
			writer.close(); 
			reader.close(); 
			tempFile.renameTo(inputFile);
		}
	}
	
	public void changeRepDegree(char[] fileId, int repDegree) throws FileNotFoundException, IOException {
		if(doesRestoreExist(fileId)) {
			File file = new File(restoreDir);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			String verify, putData;
			
			while((verify = br.readLine()) != null ){
				if(verify != null){
					String[] components = verify.split(space);
					if(components[0].trim().equals(fileId.toString())) {
						putData = verify.replaceAll(components[2], Integer.toString(repDegree));
						bw.write(putData);
					}
				}
			}
			br.close();
		}
	}
	
	public void changeActualRepDegree(char[] fileId, int actualRepDegree) throws FileNotFoundException, IOException {
		if(doesRestoreExist(fileId)) {
			File file = new File(restoreDir);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			String verify, putData;
			
			while((verify = br.readLine()) != null ){
				if(verify != null){
					String[] components = verify.split(space);
					if(components[0].equals(fileId.toString())) {
						putData = verify.replaceAll(components[1], Integer.toString(actualRepDegree));
						bw.write(putData);
					}
				}
			}
			br.close();
		}
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
