package catchdata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import util.LogGenerator;

public class Catcher {
	static String os = null;

	public static void setOs(String os) {
		Catcher.os = os;
	}
	static WebDriver wd_c=null;
	public static String getUrl() {
		InputStream in_t = null;
		Properties p_t = new Properties();
		File file_t = new File("test.properties");
		try {
			
			in_t = new BufferedInputStream(new FileInputStream(file_t));
			p_t.load(in_t);

		} catch (IOException e) {
			e.printStackTrace();
		}
		 url = p_t.getProperty("url");
		return url;
	}
	static WebDriver wd = null;
	static WebElement we = null;
	static String component = "";
	static String componetLink = "";
	static String caseName = "";
	static String buildNum = "";
	static List<String> buildNumList = new ArrayList<String>();
	static File file;
	static WritableWorkbook wwb = null;
	static Label l = null;
	static List<Integer> componetRowList = new ArrayList<Integer>();
	static List<Integer> componetColList = new ArrayList<Integer>();
	static List<Integer> caseNameRowList = new ArrayList<Integer>();
	static List<Integer> caseNameColList = new ArrayList<Integer>();
	static List<Integer> linkRowList = new ArrayList<Integer>();
	static List<Integer> linkColList = new ArrayList<Integer>();
	static String model_b = "false";
	static String model_f = "false";
	static String model_m = "false";
	static String model_r = "false";
	static String buildNumlist_parameter = "";
	static String free_cases = "";
	static int start = 0;
	static String startName = "";
	static int end = 0;
	static String endName = "";
	static int beginLine = 0;
	static String url = "";
	static String log_url="";
	static boolean redo = true;
	static List<List<String>> redoList = new ArrayList<List<String>>();
	static int sheetNum = 0;
	

	public static void setBuildNumlist_parameter(String buildNumlist_parameter) {
		Catcher.buildNumlist_parameter = buildNumlist_parameter;
	}

	public static void setFree_cases(String free_cases) {
		Catcher.free_cases = free_cases;
	}

	public static void setUrl(String url) {
		Catcher.url = url;
	}

	public static void setBuildNum(String buildNum) {
		Catcher.buildNum = buildNum;
	}

	public static void setBuildNumList(List<String> buildNumList) {
		Catcher.buildNumList = buildNumList;
	}

	public static void setModel_b(String model_b) {
		Catcher.model_b = model_b;
	}

	public static void setModel_f(String model_f) {
		Catcher.model_f = model_f;
	}

	public static void setModel_m(String model_m) {
		Catcher.model_m = model_m;
	}

	public static void setStart(int start) {
		Catcher.start = start;
	}

	public static void setEnd(int end) {
		Catcher.end = end;
	}

	public static void setBeginLine(int beginLine) {
		Catcher.beginLine = beginLine;
	}

	public static void start() {

		try {
			Catcher.openWindow();

			SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
			String currentTime = sdf.format(new Date());
			sdf = new SimpleDateFormat("yyyyMMdd");
			String currentDate = sdf.format(new Date());

			file = new File("out-put-result");
			if (file == null) {
				file.mkdirs();
			}
			
			System.out.println("The os is "+os);
			if (os != null && os.equals("Linux")) {
				file = new File("out-put-result/" + currentDate + "-"
						+ currentTime + ".xls");
			} else if (os != null && os.equals("Windows")) {
				file = new File("out-put-result\\" + currentDate + "-"
						+ currentTime + ".xls");
			}
			

			wwb = init(file);

			if (model_m != null && model_m.equals("true")) {
				System.out.println("-----Build-model: mutiple model-----");
				for (int i = 0; i < buildNumList.size(); i++) {
					componetRowList.add(0);
					componetColList.add(0);
					caseNameRowList.add(0);
					caseNameColList.add(1);
					linkRowList.add(0);
					linkColList.add(2);
				}
				Catcher.chooseComponent();
			} else {
				System.out.println("-----Build-model: sample model-----");
				componetRowList.add(0);
				componetColList.add(0);
				caseNameRowList.add(0);
				caseNameColList.add(1);
				linkRowList.add(0);
				linkColList.add(2);

				Catcher.chooseComponent();
			}
			wwb.write();
			wwb.close();

		} catch (Exception e) {
			if (e.equals(TimeoutException.class)) {
				e.printStackTrace();
				System.out
						.println("Current network-speed is slow, so close window in case of script stuck, please try again when net-work speed gets well.");
			}
		} finally {

			if (redoList.get(0).size() != 0) {
				try {

					redo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			try {
				wd.quit();

			} catch (Exception e) {
				e.printStackTrace();
				System.out
						.println("Excel file input failed. Maybe it is caused by NULL writeable object or There is no error case. Please check it. ");

			}
		}

	}

	public static void openWindow() {
		InputStream in = null;
		Properties p = new Properties();
		File file = new File("test.properties");
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			p.load(in);

		} catch (IOException e) {
			e.printStackTrace();
		}

		redoList.add(new ArrayList<String>());
		redoList.add(new ArrayList<String>());

		// url = p.getProperty("url");
		log_url=p.getProperty("log-file");
		String dir = p.getProperty("dir");
		// model_b = p.getProperty("break-model");
		// model_f = p.getProperty("free-model");
		// model_m = p.getProperty("mutiple-model");

		// free_cases = p.getProperty("free-cases");

		if (model_m != null && model_m.equals("true")) {
			buildNumList = divideBuildNumList(buildNumlist_parameter);
		}
		// else {
		// buildNum = p.getProperty("build-num");
		//
		// }

		// startName = p.getProperty("start-cname");
		// endName = p.getProperty("end-cname");
		// beginLine = Integer.parseInt(p.getProperty("begin-line"));

		System.setProperty("webdriver.firefox.bin", dir);
		wd = new FirefoxDriver();
		wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		wd.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		wd.manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
		wd.manage().window().setSize(new Dimension(1000, 1000));
		wd_c=new FirefoxDriver();
		wd_c.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		wd_c.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		wd_c.manage().window().setPosition(new org.openqa.selenium.Point(1500, 0));
		wd_c.manage().window().setSize(new Dimension(1000, 1000));
		
		wd.navigate().to(url);
		wd_c.navigate().to(log_url);

	}

	public static void setStartName(String startName) {
		Catcher.startName = startName;
	}

	public static void setEndName(String endName) {
		Catcher.endName = endName;
	}

	public static void redo() throws Exception {
		model_r = "true";
		System.out.println("##############################################");
		System.out.println("This is redo-list " + redoList.get(1).toString());
		String redoTemp = "";

		for (String s : redoList.get(1)) {
			redoTemp += s;
			redoTemp += "/";
		}
		System.out.println("This is redo-list: " + redoTemp);

		Catcher.free_toChooseBuild(redoTemp);

	}

	public static void chooseComponent() throws Exception {
		try {
			List<WebElement> list = wd.findElements(By
					.xpath("//table[@id='projectstatus']/tbody/tr"));
			if (model_b != null && model_b.equals("true")) {

				List<WebElement> table_list = new ArrayList<WebElement>();
				table_list = wd
						.findElements(By
								.xpath("//table[@id='projectstatus']/tbody/tr/td[3]/a[1]"));
				for (int i = 1; i < table_list.size(); i++) {
					String link = table_list.get(i).getText();
					component = cutComponentName(link);
					if (component.equals(startName)) {
						start = i;
					} else if (component.equals(endName)) {
						end = i;
					}
				}
				System.out.println("There are " + (end - start + 1)
						+ " components");
				System.out
						.println("-----Component model: break-point model-----");
				continuous_toChooseBuild(start + 2, end + 2);

			} else if (model_f != null && model_f.equals("true")) {

				System.out.println("-----Component model: free model-----");
				free_toChooseBuild(free_cases);

			} else {
				start = 1 + beginLine;
				end = list.size();
				System.out.println("There are " + (end - start - 1)
						+ " components");
				System.out.println("-----Component model: default model-----");
				continuous_toChooseBuild(start, end);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static void free_toChooseBuild(String free_cases) throws Exception {
		List<String> list = new ArrayList<String>();
		if (model_r != null && model_r.equals("true")) {
			list = divideFreeCases_redo(free_cases);
		} else {
			list = divideFreeCases(free_cases);
		}
		List<WebElement> table_list = new ArrayList<WebElement>();
		table_list = wd.findElements(By
				.xpath("//table[@id='projectstatus']/tbody/tr/td[3]/a[1]"));
		for (int i = 1; i < table_list.size(); i++) {
			table_list = wd.findElements(By
					.xpath("//table[@id='projectstatus']/tbody/tr/td[3]/a[1]"));
			String link = table_list.get(i).getText();
			component = cutComponentName(link);
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).equals(component)) {
					System.out.println("---");
					System.out.println("Output the detail of the " + (j + 1)
							+ "th case name:");
					System.out.println(component + "--------component-name");
					System.out.println(list.get(j) + "--------case-name");
					System.out.println("Same, so enter the link..");
					table_list.get(i).click();
					we = table_list.get(i);

					//add component table on log window
					LogGenerator.addComponent(wd_c, we,component);
					
					Catcher.chooseBuild();

				}
			}
		}

	}

	public static List<String> divideFreeCases(String fc) {
		List<String> list = new ArrayList<String>();
		String tname;
		String t_fc = fc;
		while (!(t_fc.indexOf("/") == -1)) {
			tname = t_fc.substring(0, t_fc.indexOf("/"));
			t_fc = t_fc.substring(t_fc.indexOf("/") + 1);
			list.add("portal-" + tname);
		}

		System.out.println("The free-cases list is " + list.toString());
		return list;

	}

	public static List<String> divideFreeCases_redo(String fc) {
		List<String> list = new ArrayList<String>();
		String tname;
		String t_fc = fc;
		while (!(t_fc.indexOf("/") == -1)) {
			tname = t_fc.substring(0, t_fc.indexOf("/"));
			t_fc = t_fc.substring(t_fc.indexOf("/") + 1);
			list.add(tname);
		}

		System.out.println("The free-cases-redo list is " + list.toString());
		return list;

	}

	public static List<String> divideBuildNumList(String bnl) {
		List<String> list = new ArrayList<String>();
		String tname;
		String t_fc = bnl;
		while (!(t_fc.indexOf("/") == -1)) {
			tname = t_fc.substring(0, t_fc.indexOf("/"));
			t_fc = t_fc.substring(t_fc.indexOf("/") + 1);
			list.add(tname);
		}

		System.out.println("The build-num list is " + list.toString());
		return list;
	}

	public static void continuous_toChooseBuild(int start, int end)
			throws WriteException, BiffException, IOException {

		for (int i = start; i <= end; i++) {

			try {
				we = wd.findElement(By
						.xpath("//table[@id='projectstatus']/tbody/tr[" + i
								+ "]/td[3]/a[1]"));
				String link = we.getText();
				component = cutComponentName(link);
				System.out.println("-----");
				System.out.print(component);

				if (component.equals("portal-known-issues")
						|| component.equals("portal-tools")
						|| component.equals("portal-upgrades")
						|| component.equals("portal-business-productivity-ee")) {
					System.out.println("-----this component should be passed");
					System.out.println("");
					continue;
				} else {
					System.out.println("-----this component should be checked");
					we.click();
					
					//add component table on log window
					LogGenerator.addComponent(wd_c, we,component);
					
					if (model_m != null && model_m.equals("true")) {
						Catcher.chooseMutipleBuild();
					} else {
						Catcher.chooseBuild();
					}

				}
			} catch (Exception e) {
				if (redo) {
					System.out
							.println("-----had re-do it but failed, so pass current component, the component name is "
									+ component);

					redoList.get(0).add(buildNum);
					redoList.get(1).add(component);

					wd.navigate().to(url);
					continue;
				}
			}

		}
	}

	public static String cutComponentName(String str) {
		int i = str.indexOf('[');
		int j = str.indexOf(']');
		String cut = "error-component-name";
		if (i != -1 && j != -1) {
			cut = str.substring(i + 1, j);
		}
		return cut;
	}

	public static void chooseMutipleBuild() throws Exception {

		for (int j = 0; j < buildNumList.size(); j++) {

			buildNum = buildNumList.get(j);
			System.out.println("-----#####-----This is " + buildNum
					+ " script log-----#####-----");
			sheetNum = j;

			int num = 0;
			List<WebElement> list = wd.findElements(By
					.xpath("//table[@id='buildHistory']/tbody/tr"));
			for (int i = 2; i < list.size(); i++) {
				String temp = ConvertBuildNum(i);
				if (buildNum.equals(temp)) {
					num = i;
					System.out.print("choose " + buildNum + " build");
				}
			}
			we = wd.findElement(By
					.xpath("//table[@id='buildHistory']/tbody/tr[" + num
							+ "]/td[1]/a[1]/img"));
			String flag = we.getAttribute("tooltip").substring(0, 8);
			String flag1 = we.getAttribute("tooltip").substring(0, 6);
			if (flag.equals("Unstable")) {
				System.out.println("----------build is " + flag);
				write(wwb, componetColList.get(j), componetRowList.get(j),
						component);
				we = wd.findElement(By
						.xpath("//table[@id='buildHistory']/tbody/tr[" + num
								+ "]/td[2]/a"));
				we.click();
				chooseErrorCase(j);
				if (j == (buildNumList.size() - 1)) {
					wd.navigate().back();
				}
			} else if (flag1.equals("Failed")) {
				System.out.println("----------build is " + flag1);
				write(wwb, componetColList.get(j), componetRowList.get(j),
						component);
				we = wd.findElement(By
						.xpath("//table[@id='buildHistory']/tbody/tr[" + num
								+ "]/td[2]/a"));
				we.click();
				chooseErrorCase(j);
				if (j == (buildNumList.size() - 1)) {
					wd.navigate().back();
				}
			} else if (flag.equals("Success ")) {
				System.out.println("----------build is " + flag);
				if (j == (buildNumList.size() - 1)) {
					wd.navigate().back();
				}

			}

		}

	}

	public static void chooseBuild() throws Exception {
		int num = 0;
		List<WebElement> list = wd.findElements(By
				.xpath("//table[@id='buildHistory']/tbody/tr"));
		for (int i = 2; i < list.size(); i++) {
			String temp = ConvertBuildNum(i);
			if (buildNum.equals(temp)) {
				num = i;
				System.out.print("choose " + buildNum + " build");
			}
		}
		we = wd.findElement(By.xpath("//table[@id='buildHistory']/tbody/tr["
				+ num + "]/td[1]/a[1]/img"));
		String flag = we.getAttribute("tooltip").substring(0, 8);
		String flag1 = we.getAttribute("tooltip").substring(0, 6);
		if (flag.equals("Unstable")) {
			System.out.println("----------build is " + flag);
			write(wwb, componetColList.get(0), componetRowList.get(0),
					component);
			we = wd.findElement(By
					.xpath("//table[@id='buildHistory']/tbody/tr[" + num
							+ "]/td[2]/a"));
			we.click();
			chooseErrorCase(0);
			wd.navigate().back();
		} else if (flag1.equals("Failed")) {
			System.out.println("----------build is " + flag1);
			write(wwb, componetColList.get(0), componetRowList.get(0),
					component);
			we = wd.findElement(By
					.xpath("//table[@id='buildHistory']/tbody/tr[" + num
							+ "]/td[2]/a"));
			we.click();
			chooseErrorCase(0);
			wd.navigate().back();
		} else if (flag.equals("Success ")) {
			System.out.println("----------build is " + flag);
			wd.navigate().back();

		}
	}

	public static String ConvertBuildNum(int line) {

		we = wd.findElement(By.xpath("//table[@id='buildHistory']/tbody/tr["
				+ line + "]/td[1]"));
		if (we.getText().equals("")) {
			return "empty";
		} else {
			return we.getText().substring(2, we.getText().length());

		}

	}

	public static void chooseErrorCase(int a) throws Exception {
		List<WebElement> list = wd.findElements(By
				.xpath("//div[@id='matrix']/a"));
		String flag = "";
		for (int i = 1; i <= list.size(); i++) {
			we = wd.findElement(By
					.xpath("//div[@id='matrix']/a[" + i + "]/img"));
			flag = we.getAttribute("tooltip");
			we = wd.findElement(By.xpath("//div[@id='matrix']/a[" + i + "]"));
			caseName = we.getText().substring(5, we.getText().length());
			if (caseName.equals("lt")) {
				caseName = "default";
			}

			if (flag.equals("Success ")) {
				continue;
			} else if (flag.equals("Failed ")) {
				//add new error-case entry on log window
				LogGenerator.addErrorCase(wd_c,  component, caseName);
				//change its case-status to Failed
				LogGenerator.changeCaseStatus(wd_c,  component, "Failed");
			
				
				we.click();
				System.out.print("The error case is " + caseName);
				write(wwb, caseNameColList.get(a), caseNameRowList.get(a),
						caseName);
				caseNameRowList.set(a, caseNameRowList.get(a) + 1);
				componetRowList.set(a, componetRowList.get(a) + 1);
				// componetRow++;

				componetLink = wd.getCurrentUrl();

				System.out.println("--------record case link");
				
				//change its write-status to Record
				LogGenerator.changeWriteStatus(wd_c, component, "Record");
				//change its build-num to #{num}
				LogGenerator.changeBuildNum(wd_c, component, buildNum);
				
				write(wwb, linkColList.get(a), linkRowList.get(a), componetLink);
				linkRowList.set(a, linkRowList.get(a) + 1);
				// linkRow++;

				wd.navigate().back();
			} else if (flag.equals("Unstable ")) {
				//add new error-case entry on log window
				LogGenerator.addErrorCase(wd_c,  component, caseName);
				//change its case-status to Unstable
				LogGenerator.changeCaseStatus(wd_c,  component, "Unstable");
				
				
				we.click();
				System.out.print("The error case is " + caseName);
				write(wwb, caseNameColList.get(a), caseNameRowList.get(a),
						caseName);
				caseNameRowList.set(a, caseNameRowList.get(a) + 1);
				componetRowList.set(a, componetRowList.get(a) + 1);

				componetLink = wd.getCurrentUrl();

				System.out.println("--------record case link");
				
				//change its write-status to Record
				LogGenerator.changeWriteStatus(wd_c,  component, "Record");
				//change its build-num to #{num}
				LogGenerator.changeBuildNum(wd_c, component, buildNum);
				
				write(wwb, linkColList.get(a), linkRowList.get(a), componetLink);
				linkRowList.set(a, linkRowList.get(a) + 1);

				wd.navigate().back();
			}
		}
		wd.navigate().back();
	}

	public static WebDriver getWd() {
		return wd;
	}

	public static void close(WebDriver wd) {
		wd.quit();
	}

	public static void write(WritableWorkbook wwb, int x, int y, String str)
			throws WriteException, IOException, BiffException {

		WritableSheet ws = null;
		if (wwb.getNumberOfSheets() == sheetNum) {
			ws = wwb.createSheet(buildNum, sheetNum);
		} else {
			ws = wwb.getSheet(sheetNum);
		}
		l = new Label(x, y, str);

		ws.addCell(l);
		// if (wwb.getNumberOfSheets() == 0) {
		// ws = wwb.createSheet("sheet1", 0);
		// } else {
		// ws = wwb.getSheet(0);
		// }
		// l = new Label(x, y, str);
		//
		// ws.addCell(l);

	}

	public static WritableWorkbook init(File file) throws IOException {
		WritableWorkbook wwb = Workbook.createWorkbook(file);
		return wwb;

	}
	
	public static void pause() throws InterruptedException{
		wd.wait();
	}
	
	public static void continue_(){
		wd.notify();
	}
}
