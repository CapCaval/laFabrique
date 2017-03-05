package org.capcaval.lafabrique.file;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;

import org.capcaval.lafabrique.lang.StringMultiLine;

public interface LocalRessourceLoader {
	default public Path getLocalFilePath(String localFile) {
		// get the url
		URL url = this.getClass().getResource(localFile);

		// get the path
		Path path = null;
		try {
			path = Paths.get(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return path;
	}
	
	default public BufferedImage getLocalImage(String fileName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(this.getClass().getResource(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	default public Image getLocalJfxImage(String fileName) {
		Image image = new Image(this.getClass().getResourceAsStream(fileName));
		
		return image;
	}
	
	default public String getLocalTextFile(String fileName) {
		URL url = this.getClass().getResource(fileName);
		if(url == null){
			throw new RuntimeException("[laFabrique] Error : The file can not be found " + this.getClass().getResource("") + fileName);
		}
		
		InputStream stream = null;

		try {
			stream = new FileInputStream(url.getFile());
		} catch (FileNotFoundException e1) {
			throw new RuntimeException("[laFabrique] Error : The file can not be found " + url.getFile().toString());
		}

		StringMultiLine sm = new StringMultiLine();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		String line = null;

		try {
			do {
				line = reader.readLine();
				if (line != null) {
					sm.addLine(line);
				}
			} while (line != null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sm.toString();
	}

	

}
