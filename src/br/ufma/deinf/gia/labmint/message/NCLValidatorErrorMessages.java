package br.ufma.deinf.gia.labmint.message;

import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class NCLValidatorErrorMessages extends Properties {

	private static String RESOURCE_BUNDLE = "br.ufma.deinf.gia.labmint.message.NCLValidatorErrorMessages"; //$NON-NLS-1$
	private static ResourceBundle fgResourceBundle = ResourceBundle
			.getBundle(RESOURCE_BUNDLE);

	NCLValidatorErrorMessages() {
	}

	public static String getString(String key) {
		try {
			return fgResourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return "!" + key + "!";//$NON-NLS-2$ //$NON-NLS-1$
		}
	}

	public static ResourceBundle getResourceBundle() {
		return fgResourceBundle;
	}

	public String getProperty(String prop) {
		return NCLValidatorErrorMessages.getString(prop);
	}
}