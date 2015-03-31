package Protocols;
import java.util.ArrayList;

public class FileManager {
	//array of files
	private ArrayList<NewFile> files = new ArrayList<NewFile>();
	//modified_files tracks the indexes of files modified in the array above
	//será que é a melhor implementação????????
	private ArrayList<Integer> modified_files = new ArrayList<Integer>();
	
	//...
	//se ficheiro for modificado, mudar a versão dele (ex: 1.0 -> 2.0)
	
	//tem que interpretar headers de mensagens e invocar a classe apropriada para esse protocolo
	//ex: lê "backup" e chama uma função da classe Backup
	
}
