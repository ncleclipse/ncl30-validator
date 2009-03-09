package br.ufma.deinf.gia.labmint.message;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MessageHandler {
	private static MessageHandler instance = null;
	private static Properties properties; 
	
	private MessageHandler(){
	
	}
	
	public static MessageHandler getInstance(){
		if(instance == null){
			instance = new MessageHandler();
			// Read properties file.
			properties = new Properties();
			try {
				properties.load(new FileInputStream("messages.properties"));
			} catch (IOException e) {
			}
		}
		return instance;
	}
	
	private static String getMessageTemplate(int msg){
		return properties.getProperty((new Integer(msg)).toString());
	}
	
	public static String getMessage(int msg) {
		getInstance();
		String str = MessageHandler.getMessageTemplate(msg);
		return str;
	}

}
