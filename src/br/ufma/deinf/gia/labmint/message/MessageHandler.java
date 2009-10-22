package br.ufma.deinf.gia.labmint.message;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.sun.org.apache.xml.internal.serializer.utils.Messages;

public class MessageHandler {
	private static MessageHandler instance = null;
	private static Properties properties;
	private static String propertiesFile = "Messages_pt_br.properties";
	private MessageHandler() {

	}

	public static MessageHandler getInstance() {
		if (instance == null) {
			instance = new MessageHandler();
			// Read properties file.
			if (properties == null) {
				properties = new Properties();

				try {
					java.net.URL url = ClassLoader.getSystemResource(propertiesFile);
					properties.load(url.openStream());
				} catch (IOException e) {
					
				}
			}
		}
		return instance;
	}

	private static String getMessageTemplate(int msg) {
		return properties.getProperty((new Integer(msg)).toString());
	}

	private static void setPropertiesFile(String propFile){
		propertiesFile = propFile; 
	} 
	
	public static String getMessage(int msg) {
		getInstance();
		String str = MessageHandler.getMessageTemplate(msg);
		return str;
	}
	
	public static void setProperties(Properties prop){
		properties = prop;
	}

}
