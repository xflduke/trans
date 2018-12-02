package io.duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TransProperties extends Properties{

	private static final long serialVersionUID = 1L;
	
	private static TransProperties props = new TransProperties();
	
	private TransProperties() {
	}
	
	public static Properties getInstance(String path) throws FileNotFoundException, IOException {
		
		synchronized (TransProperties.class) {
			props.load(new FileReader(new File(path)));
		}
		
		return getInstance();
	}
	
	public static Properties getInstance() {
		return props;
	}
}
