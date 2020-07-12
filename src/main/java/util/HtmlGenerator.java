package main.java.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

public class HtmlGenerator {

    private final String pathToFolder;
    private final String templateName;
    private String template;

    public HtmlGenerator(final String pathToFolder, final String templateName) {
	this.pathToFolder = pathToFolder;
	this.templateName = templateName;
    }

    public String getPathToFolder() {
	return pathToFolder;
    }

    public String getTemplateName() {
	return templateName;
    }

    public String getPathToTemplate() {
	return pathToFolder + "/" + templateName;
    }

    public String getTemplate() {
	return template;
    }

    public void setTemplate(String template) {
	this.template = template;
    }

    public String readTemplate() throws IOException {
	File htmlTemplateFile = new File(templateName);
	this.template = FileUtils.readFileToString(htmlTemplateFile, Charset.forName("UTF-8"));
	return template;
    }

    public void replaceBody(String body) {
	this.template = template.replace("$body", body);
    }

    public File generateHtml(String title) throws IOException {
	template = template.replace("$title", title);
	File newHtmlFile = new File(pathToFolder + "/" + title + ".html");
	FileUtils.writeStringToFile(newHtmlFile, template, Charset.forName("UTF-8"));
	return newHtmlFile;
    }

}
