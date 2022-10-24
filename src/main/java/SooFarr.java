import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SooFarr {
    static File fard = new File(System.getProperty("user.home") + File.separator + "StupiderFarter");
    static File toaddfile = new File(fard.toString() + File.separator + "ToAdd");
    static File themal =  new File(fard + File.separator + "Internal" + File.separator + "Themal");
    static File log =  new File(fard + File.separator + "Internal" + File.separator + "Log");
    static File cffp =  new File(fard + File.separator + "Internal" + File.separator + "CFFP");
    static HashMap<Integer, String> catalog = new HashMap<Integer, String>();

    static String currentUser = "";

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    static LocalDateTime now = LocalDateTime.now();
    public SooFarr() throws IOException {
    }


    public static void instagramInit() throws IOException, AWTException {
        List<String> ts = Files.readAllLines(cffp.toPath());
        String pass = ts.get(0);
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\clive\\Downloads\\geckodriver-v0.32.0-win32\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.instagram.com");
        //enter username
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement l = driver
                . findElement(By.name("username"));
        l.sendKeys("CastleForFools");
        //enter password
        WebElement p = driver
                .findElement(By.name("password"));
        p.sendKeys(pass);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        //"Log In" button
        List<WebElement> lwewwenj = driver.findElements(By.className("Igw0E"));
        lwewwenj.addAll(driver.findElements(By.className("_abb8")));
        for (WebElement webElement : lwewwenj) {
            System.out.println(webElement.getText());
        }
        WebElement b = lwewwenj.stream().filter(pp -> pp.getText().equalsIgnoreCase("Log In")).findFirst().get();
        System.out.println(b.getText());
        b.click();
        System.out.println(driver.getCurrentUrl());
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        new WebDriverWait(driver, 60).until(ExpectedConditions.urlContains("accounts"));
        System.out.println(driver.getCurrentUrl());

        //dont save login info button
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("_acao")));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement n = driver
                .findElement(By.className("_acao"));
        n.click();

        //deny notification
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement no = driver
                .findElement(By.className("_a9--"));
        no.click();

        //go to requests inbox
        driver.get("https://www.instagram.com/direct/requests/");

        //get all requests
        boolean neededreqs = true;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlMatches("https://www.instagram.com/direct/requests/"));
        try {
            new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(@href,'/direct/t/')]")));
            new WebDriverWait(driver, 1).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("_ab9q")));
        }
        catch (TimeoutException t){
            neededreqs = false;
        }
        //if there are no requests skip over this
        if(neededreqs) {
            List<WebElement> lreqs = driver.findElements(By.className("_a6hd"));
            List<WebElement> dms = new ArrayList<>();
            for (WebElement webElement : lreqs) {
                if (webElement.getAttribute("href").contains("/direct/t/")) {
                    dms.add(webElement);
                    //System.out.println(webElement.getAttribute("href"));
                }
                //System.out.println(webElement.getAttribute("class"));
                //System.out.println(webElement.getAttribute("href"));
            }
            List<String> dmsurls = new ArrayList<>();
            for (WebElement e : dms) {
                dmsurls.add(e.getAttribute("href"));
            }

            //go back to dm requests home
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("https://www.instagram.com/direct/requests/");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            for (String urls : dmsurls) {
                //go to the specific dm
                driver.get(urls);
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                //accept the dm
                new WebDriverWait(driver, 10).until(ExpectedConditions.urlMatches(urls));
                new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("_acat")));
                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                List<WebElement> acceptbutt = driver.findElements(By.className("_acat"));
                for (WebElement webElement : acceptbutt) {
                    System.out.println(webElement.getText());
                }
                WebElement accep = acceptbutt.stream().filter(pp -> pp.getText().equalsIgnoreCase("Accept")).findFirst().get();
                accep.click();

                //deny notification
                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                WebElement nonono = driver
                        .findElement(By.className("_a9--"));
                nonono.click();

                //go back to dm requests home
                driver.get("https://www.instagram.com/direct/requests/");
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            }

        }

        //go to regular inbox
        driver.get("https://www.instagram.com/direct/inbox/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //TODO clear requests or not
        //iterate through every normal inbox dm
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlMatches("https://www.instagram.com/direct/inbox/"));
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(@href,'/direct/t/')]")));
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("_ab9q")));
        List<WebElement> ldms = driver.findElements(By.className("_a6hd"));
        List<WebElement> nrdms = new ArrayList<>();
        for (WebElement webElement : ldms) {
            if(webElement.getAttribute("href").contains("/direct/t/")){
                nrdms.add(webElement);
                System.out.println(webElement.getAttribute("href"));
            }
            System.out.println(webElement.getAttribute("class"));
            System.out.println(webElement.getAttribute("href"));
        }
        List<String> nrdmsurls = new ArrayList<>();
        for (WebElement e : nrdms ) {
            nrdmsurls.add(e.getAttribute("href"));
        }

        //iterate through the inboxes that were aquired earlier and fuffil the commands for each of them then clear
        //TODO clear after every command?
        for(String urls : nrdmsurls) {
            //go to the specific dm
            driver.get(urls);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            //deny notification
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement nono = driver
                    .findElement(By.className("_a9--"));
            nono.click();

            //get all the texts
            new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@role,'dialog')]")));
            new WebDriverWait(driver, 10).until(ExpectedConditions.urlMatches(urls));
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("_aad6")));
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            List<WebElement> texts = driver.findElements(By.className("_aad6"));
            for (WebElement webElement : texts) {

                //process and access the catalog or whatever, the files
                String out = processCommand(webElement.getText());
                if (!out.equals("")) {

                    //click the photo button
                    //new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf(nonono));
                    new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("_ab6-")));
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    List<WebElement> imgs = driver.findElements(By.className("_ab6-"));
                    for (WebElement webElement2 : imgs) {
                        try {
                            System.out.println(webElement2.getAttribute("aria-label"));
                            if (webElement2.getAttribute("aria-label").equals("Add Photo or Video")){
                                webElement2.click();
                                break;
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    //send requested file path
                    Robot r = new Robot();
                    StringSelection stringSelection = new StringSelection(out);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, stringSelection);
                    r.keyPress(KeyEvent.VK_CONTROL);
                    r.delay(100);
                    r.keyPress(KeyEvent.VK_V);
                    r.delay(100);
                    r.keyRelease(KeyEvent.VK_V);
                    r.delay(100);
                    r.keyRelease(KeyEvent.VK_CONTROL);
                    r.delay(1000);
                    System.out.println("time to enter");
                    r.keyPress(KeyEvent.VK_ENTER);
                    r.delay(100);
                    r.keyRelease(KeyEvent.VK_ENTER);

                    //clear the chats
                    //click the info button
                    for (WebElement webElement2 : imgs) {
                        try {
                            System.out.println(webElement2.getAttribute("aria-label"));
                            if (webElement2.getAttribute("aria-label").equals("View Thread Details")){
                                webElement2.click();
                                break;
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    //click the delete button
                    new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("_acao")));
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    List<WebElement> deletes = driver.findElements(By.className("_acao"));
                    for (WebElement webElement2 : deletes) {
                        try {
                            System.out.println(webElement2.getAttribute("aria-label"));
                            if (webElement2.getText().equals("Delete Chat")){
                                webElement2.click();
                                break;
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    //go back to dm requests home
                    driver.get("https://www.instagram.com/direct/inbox/");
                    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                }
            }
        }

    }

    public static String processCommand(String tin) throws IOException {
        String c = "";
        String i = "";
        boolean intotrip = false;
        for (char ch : tin.toCharArray()) {
            if (ch == ' ') {
                intotrip = true;
            }
            if (!intotrip) {
                c += ch;
            } else {
                if (ch != ' ') {
                    i += ch;
                }
            }

        }

        if(c.equalsIgnoreCase("get")) {
            System.out.println(catalog.get(Integer.valueOf(i)));
            String imageToGet = catalog.get(Integer.valueOf(i));
            try {
                if (Files.exists(Path.of(imageToGet))) {
                    logString("\nA get request for entry number " + i + " was received and sent. " + dtf.format(now) + "," + getPublicIpAddress());
                    return imageToGet;
                }
            }

            catch (NullPointerException ignored){}
        }
        logString("Phrase \"" + tin + "\" was received but not understood. " + dtf.format(now) + "," + getPublicIpAddress());
        return "";
    }
    public static void logString(String stl) throws IOException {
        Files.write(log.toPath(), ("\n"+stl).getBytes(), StandardOpenOption.APPEND);
    }

    public static void onLoad() throws IOException {
        List<Path> txtFiles = null;
        txtFiles = Files.walk(toaddfile.toPath()).filter(p -> !p.toString().equals(toaddfile.toString())).toList();
        for (Path e : txtFiles) {
            System.out.println(addFile(new File(String.valueOf(e))));
        }
        catalog.keySet().forEach(System.out::println);
        reloadCatalog();
    }

    public static void reloadCatalog() throws IOException {
        System.out.println("REloading CAtalog");
        catalog.clear();
        List<String> fies = Files.readAllLines(themal.toPath());
        for(String s : fies){
            System.out.println(s.substring(0, s.indexOf(',')));
            System.out.println((s.substring(s.indexOf(',')+1)));
            catalog.put(Integer.valueOf(s.substring(0, s.indexOf(','))),(s.substring(s.indexOf(',')+1)));
        }
    }

    public static String addFile(File p, int id) throws IOException {
        if(catalog.get(id) == null){
            byte[] bytesofp = Files.readAllBytes(p.toPath());

            String newpath = fard.toString() + File.separator + p.getName();
            Files.move(p.toPath(), Path.of(newpath));
            catalog.put(id, newpath);
            String lineta = id+ "," + p.getName() + "," + newpath + "," + Arrays.hashCode(bytesofp) + "," + dtf.format(now)
                    + "," + getPublicIpAddress() + "," + System.getProperties().getProperty("user.name") + "\n";
            Files.write(log.toPath(), lineta.getBytes(), StandardOpenOption.APPEND);
            String ftaa =  id + "," +  newpath;
            Files.write(themal.toPath(), ftaa.getBytes(), StandardOpenOption.APPEND);
            return "File: " + p.getName() + " successfully added";
        }
        addFile(p);
        return "ID taken defaulting to latest open";
    }
    public static String addFile(File p) throws IOException {
        byte[] bytesofp = Files.readAllBytes(p.toPath());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        System.out.println("this is fard");
        System.out.println(fard.toString());
        String newpath = fard.toString() + File.separator + p.getName();
        Files.move(p.toPath(), Path.of(newpath));
        int laoide =getLatestOpenID();
        catalog.put(laoide, newpath);
        String lineta = laoide+ "," + p.getName() + "," + newpath + "," + Arrays.hashCode(bytesofp) + "," + dtf.format(now)
                + "," + getPublicIpAddress() + "," + System.getProperties().getProperty("user.name") + "\n";
        Files.write(log.toPath(), lineta.getBytes(), StandardOpenOption.APPEND);
        String ftaa =  laoide + "," +  newpath + "\n";
        Files.write(themal.toPath(), ftaa.getBytes(), StandardOpenOption.APPEND);
        return "File: " + p.getName() + " successfully added";
    }

    //credit to https://www.baeldung.com/java-get-ip-address, baeldung
    private static String getPublicIpAddress() throws IOException {

        String urlString = "http://checkip.amazonaws.com/";
        URL url = new URL(urlString);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.readLine();
        }
        catch (Exception exeption) {
            exeption.printStackTrace();
            return "Unknown";
        }
    }

    public static int getLatestOpenID() throws IOException {
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(themal))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    i = Integer.parseInt(line.substring(0, line.indexOf(","))) + 1;
                }
                catch (StringIndexOutOfBoundsException ignored){

                }
            }
        }
        return i;

    }

    public static void main(String[] args) throws IOException, AWTException {
        onLoad();
        instagramInit();

    }
}
