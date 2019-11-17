package p2jca;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
	//Get file content
	public static String read_file(String file_name) throws IOException {
		String arquivo_lido = "";
		
		File file = new File(file_name);
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		
		String linha;
		while((linha = br.readLine()) != null){ 
			arquivo_lido += linha;
		}
		br.close();
		
		return arquivo_lido;
	}
	
	//Computes hash
	public static void compute_hash(String file_name) throws NoSuchAlgorithmException, 
    UnsupportedEncodingException {
		try {
			String arquivo = read_file(file_name);
			
			MessageDigest hash_md = MessageDigest.getInstance("sha-256");
			byte resultado_hash[] = hash_md.digest(arquivo.getBytes("UTF-8"));
			
			//To Hexadecimal
			StringBuilder hexString = new StringBuilder();
		       for (byte b : resultado_hash) {
		         hexString.append(String.format("%02X", 0xFF & b));
		       }
		      String resultado_hex = hexString.toString();
		       
			System.out.println("Nome do arquivo:" + file_name);
			System.out.println("Hash:" + resultado_hex.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		//Verify which command was used
		if(args[0].equals("--hash")) {
			//Verify if the command is valid
			if(args.length == 2) {
				try{
					compute_hash(args[1]);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Correct usage: --hash <file>");
			}
		}
		else if (args[1].equals("--verify")) {
			System.out.println("verify");
		}
		
		else {
			System.out.println("Command not found");
		}
		

	}

}
