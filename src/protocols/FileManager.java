package protocols;
import java.util.ArrayList;

public class FileManager {
	//array of files
	private ArrayList<NewFile> files = new ArrayList<NewFile>();
	//modified_files tracks the indexes of files modified in the array above
	//ser� que � a melhor implementa��o????????
	private ArrayList<Integer> modified_files = new ArrayList<Integer>();
	
	//...
	//VERS�O RELACIONADA COM ENHANCEMENTS QUE FIZERMOS, 1.0 por default
	
	//tem que interpretar headers de mensagens e invocar a classe apropriada para esse protocolo
	//ex: l� "backup" e chama uma fun��o da classe Backup
	
}
