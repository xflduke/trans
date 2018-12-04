package io.duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

public class TransProperties extends Properties {

	private static final long serialVersionUID = 1L;

	private static TransProperties props = new TransProperties();

	private TransProperties() {

	}

	public static Properties getInstance(String path)
			throws FileNotFoundException, IOException, NoSuchAlgorithmException {

		synchronized (TransProperties.class) {
			props.load(new FileReader(new File(path)));

//			// challenge 任意のMD5
			props.put("challenge",
					DatatypeConverter
							.printHexBinary(
									MessageDigest.getInstance("MD5").digest(props.getProperty("ozoUser").getBytes()))
							.toLowerCase());

			String md5Pass = DatatypeConverter
					.printHexBinary(MessageDigest.getInstance("MD5").digest(props.getProperty("ozoPass").getBytes())).toLowerCase();

			// response = MD5(ozoUser:MD5(ozoPass):challenge)
			props.put("response", DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(
					(props.getProperty("ozoUser") + ":" + md5Pass + ":" + props.getProperty("challenge")).getBytes()))
					.toLowerCase());

		}

		return getInstance();
	}

	public static Properties getInstance() {

		return props;
	}
}
