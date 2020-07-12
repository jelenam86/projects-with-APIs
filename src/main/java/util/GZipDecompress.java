package main.java.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZipDecompress {

    private String nameOfOutput;
    private String pathToInput;
    private String pathToOutput;

    public GZipDecompress(String nameOfOutput, String pathToInput) {
	this.pathToInput = pathToInput;
	setNameOfOutput(nameOfOutput);
    }

    public String getNameOfOutput() {
	return nameOfOutput;
    }

    public void setNameOfOutput(String nameOfOutput) {
	this.nameOfOutput = nameOfOutput;
	this.pathToOutput = pathToInput.substring(0, this.pathToInput.lastIndexOf('/') + 1) + this.nameOfOutput;
	File file = new File(pathToOutput);
	try {
	    file.createNewFile();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public String getPathToInput() {
	return pathToInput;
    }

    public void setPathToInput(String pathToInput) {
	this.pathToInput = pathToInput;
    }

    public String getPathToOutput() {
	return pathToOutput;
    }

    public boolean decompress() {
	try (FileInputStream fis = new FileInputStream(pathToInput);
		GZIPInputStream gzis = new GZIPInputStream(fis);
		FileOutputStream fos = new FileOutputStream(this.pathToOutput)) {
	    byte[] buffer = new byte[1024];
	    int length;
	    while ((length = gzis.read(buffer)) > 0)
		fos.write(buffer, 0, length);
	    return true;
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
    }
}
