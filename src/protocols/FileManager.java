package protocols;
import java.util.ArrayList;

public class FileManager {
	//array of files
	private ArrayList<NewFile> files = new ArrayList<NewFile>();
	//modified_files tracks the indexes of files modified in the array above
	//ser� que � a melhor implementa��o????????
	private ArrayList<Integer> modified_files = new ArrayList<Integer>();
	
	
	public void start() {
		Menu m = new Menu();
		m.start();
		
		//info to start the protocols
		String ip = m.getIp();
		int port = m.getPort();
		String dir = m.getBackupDir();
		double space = m.getSpace();
		
		String toDo = m.whatNext();
		//TODO: reading the string and doing the appropriate command
	}
	//...
	//VERS�O DEPENDE DOS ENHANCEMENTS (default � 1.0)
	
	//tem que interpretar headers de mensagens e invocar a classe apropriada para esse protocolo
	//ex: l� "backup" e chama uma fun��o da classe Backup
	
}
