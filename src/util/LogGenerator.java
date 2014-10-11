package util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LogGenerator {
	public static void addComponent(WebDriver wd, WebElement we, String name) {
		JavascriptExecutor execute = (JavascriptExecutor) wd;
		execute.executeScript("addComponent('"+name+"');");

	}
	
	public static void addErrorCase(WebDriver wd,  String name, String errorcase){
		JavascriptExecutor execute = (JavascriptExecutor) wd;
		execute.executeScript("addErrorCase('"+name+"','"+errorcase+"');");
	}
	
	public static void changeCaseStatus(WebDriver wd,  String name, String status){
		JavascriptExecutor execute = (JavascriptExecutor) wd;
		execute.executeScript("changeCaseStatus('"+name+"','"+status+"');");
	}
	
	public static void changeWriteStatus(WebDriver wd,  String name, String status){
		JavascriptExecutor execute = (JavascriptExecutor) wd;
		execute.executeScript("changeWriteStatus('"+name+"','"+status+"');");
	}
	
	public static void changeBuildNum(WebDriver wd,  String name, String num){
		JavascriptExecutor execute = (JavascriptExecutor) wd;
		execute.executeScript("changeBuildNum('"+name+"','"+num+"');");
	}
}
