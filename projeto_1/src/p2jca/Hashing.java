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
	
	public static void main(String[] args) {	
		if(args.length == 0){
			print_help();
		}
		
		else {
			switch(args[0]) {
			case ("--hash"):
				hash_command(args);
				break;
			case ("--verify"):
				verify_command(args);
				break;
			
			default:
				print_help();
				break;
			}
		}
	}
	
	//Get file content
	private static String read_file(String file_name) throws IOException {
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
	private static String compute_hash(String file_name) throws NoSuchAlgorithmException, 
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
		      return resultado_hex;
			}
		catch (IOException e) {
			System.out.println("Arquivo nao encontrado");
			e.printStackTrace();;
			return null;
		}
		
		
	}
	
	private static void print_help() {
		System.out.println("Avaiable commands:");
		System.out.println("--hash <file> Computes a file hash in SHA256");
		System.out.println("--verify <file> <hash> Checks file integrity in relation to the reported hash");
	}
	
	private static void hash_command(String[] args) {
		if(args.length == 2) {
			try{
				String resultado_hex = compute_hash(args[1]);
				System.out.println("Nome do arquivo: " + args[1].toString());
				System.out.println("Hash: " + resultado_hex.toString());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Correct usage: --hash <file>");
		}
		
	}
	private static void verify_command(String[] args) {
		if(args.length == 3) {
			try{
				String resultado_hex = compute_hash(args[1]);
				if(resultado_hex.equals(args[2])) {
					System.out.println("Integrity assured");
				}
				else {
					System.out.println("File not whole");
					System.out.println("Provided hash: " + args[2]);
					System.out.println("Correct hash: " + resultado_hex);
				}
			}
			catch(NoSuchAlgorithmException e) {
				System.out.println("Algoritmo Inexistente");
				
			}
			
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else{
				System.out.println("Correct usage: --verify <file> <hash>");
		}	
		
	}
	
	

}
