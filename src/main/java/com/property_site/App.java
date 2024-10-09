package com.property_site;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class App {

	public static void main(String[] args) throws InterruptedException, SQLException, IOException {
		
    
	}
	
	public static void scraping() throws InterruptedException, SQLException, IOException {
		disableSSLVerification();
		System.setProperty("webdriver.chrome.driver", ".//Browser//chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		Wait<WebDriver> wait = getWaitObject(driver);
		
//		wait.until(new Function<WebDriver, Boolean>() {
//			public Boolean apply(WebDriver driver) {
//				System.out.println("Check element");
//				WebElement element = null;
//				if (checkElementByClassNameElement(element, "results-header", driver)) {
//					System.out.println("Check element found");
//					return true;
//				}
//				return false;
//			}
//		});
		
		driver.get("https://ibapi.in/sale_info_home.aspx");
		driver.findElement(By.id("DropDownList_Property_Type")).click();
		driver.findElement(By.xpath("//*[@id=\"DropDownList_Property_Type\"]/option[6]")).click();
		driver.findElement(By.id("DropDownList_Bank")).click();
		driver.findElement(By.xpath("//*[@id=\"DropDownList_Bank\"]/option[10]")).click();
		driver.findElement(By.id("chk_term")).click();
		driver.findElement(By.id("Button_search")).click();
		
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				System.out.println("Check element");
				if (checkClickableElementById("tbl_search_filter", driver)) {
					System.out.println("Check element found");
					return true;
				}
				return false;
			}
		});
		
		List<WebElement> pageList = driver.findElements(By.className("paginate_button"));
		int size = pageList.size();
		String numOfPages = pageList.get(size-2).getText();
		int noOfPages=Integer.parseInt(numOfPages);
		
		List<Properties> objectList =  new ArrayList<Properties>();
		
		for (int j = 0; j < noOfPages; j++) { //noOfPages

			List<WebElement> elementList = driver.findElements(By.className("sorting_1"));

			for (int i = 0; i < elementList.size(); i++) {
				Properties property = new Properties();
				
				Thread.sleep(500);
				elementList.get(i).click();
				
				WebElement closePopUpElement = driver.findElement(By.id("modal_detail"));
				
				wait.until(new Function<WebDriver, Boolean>() {
					public Boolean apply(WebDriver driver) {
						System.out.println("Check element");
						if (checkElementByClassNameElement(closePopUpElement, "close", driver)) {
							System.out.println("Check element found");
							return true;
						}
						return false;
					}
				});
				
				WebElement propertyIdElement = driver.findElement(By.id("lbl_view_prop_id"));
				String propertyId = propertyIdElement.getText();
				property.setPropertyId(propertyId);
				
				WebElement bankNameElement = driver.findElement(By.id("spn_bank_name"));
				String bankName = bankNameElement.getText();
				property.setBankName(bankName);
				
				WebElement branchNameElement = driver.findElement(By.id("spn_br_name"));
				String branchName = branchNameElement.getText();
				property.setBranchName(branchName);
				
				WebElement stateElement = driver.findElement(By.id("spn_state"));
				String state = stateElement.getText();
				property.setState(state);
				
				WebElement emdRsElement = driver.findElement(By.id("spn_emd"));
				String emdRs = emdRsElement.getText();
				if(!emdRs.equals("NA")){
					int emdRsInt = Integer.parseInt(emdRs); 
					property.setEmdRs(emdRsInt);
				}
				
				
				WebElement reservePriceRsElement = driver.findElement(By.id("spn_rsrv_price"));
				String reservePriceRs = reservePriceRsElement.getText();
				if(!reservePriceRs.equals("NA")) {
					int reservePriceRsInt = Integer.parseInt(reservePriceRs); 
					property.setReservePriceRs(reservePriceRsInt);
				}
				
				 
				WebElement districtElement = driver.findElement(By.id("spn_district"));
				String district = districtElement.getText();
				property.setDistrict(district);
				
				WebElement eMDLastDateElement = driver.findElement(By.id("spn_emd_last_dt"));
				String eMDLastDate = eMDLastDateElement.getText();
				property.seteMDLastDate(eMDLastDate);
				
				WebElement cityElement = driver.findElement(By.id("spn_city"));
				String city = cityElement.getText();
				property.setCity(city);
				
				WebElement borrowerNameElement = driver.findElement(By.id("spn_borrower"));
				String borrowerName = borrowerNameElement.getText();
				property.setBorrowerName(borrowerName);
				
				WebElement ownerNameElement = driver.findElement(By.id("spn_owner"));
				String ownerName = ownerNameElement.getText();
				property.setOwnerName(ownerName);
				
				WebElement ownershipTypeElement = driver.findElement(By.id("spn_ownership"));
				String ownershipType = ownershipTypeElement.getText();
				property.setOwnershipType(ownershipType);
				
				WebElement summaryDescriptionElement = driver.findElement(By.id("spn_sumry_desc"));
				String summaryDescription = summaryDescriptionElement.getText();
				property.setSummaryDescription(summaryDescription);
				
				WebElement propertyTypeElement = driver.findElement(By.id("spn_property_type"));
				String propertyType = propertyTypeElement.getText();
				property.setPropertyType(propertyType);
				
				WebElement propertySubTypeElement = driver.findElement(By.id("spn_property_sub_type"));
				String propertySubType = propertySubTypeElement.getText();
				property.setPropertySubType(propertySubType);
				
				WebElement typeofTitleDeedElement = driver.findElement(By.id("spn_deed"));
				String typeofTitleDeed = typeofTitleDeedElement.getText();
				property.setTypeofTitleDeed(typeofTitleDeed);
				
				WebElement statusofPossessionElement = driver.findElement(By.id("spn_possession"));
				String statusofPossession = statusofPossessionElement.getText();
				property.setStatusofPossession(statusofPossession);
				
				WebElement auctionOpenDateElement = driver.findElement(By.id("spn_auctn_start_dt"));
				String auctionOpenDate = auctionOpenDateElement.getText();
				property.setAuctionOpenDate(auctionOpenDate);
				
				WebElement auctionCloseDateElement = driver.findElement(By.id("spn_auctn_end_dt"));
				String auctionCloseDate = auctionCloseDateElement.getText();
				property.setAuctionCloseDate(auctionCloseDate);
				
				WebElement sealedBidLastDateElement = driver.findElement(By.id("spn_bid_last_dt"));
				String sealedBidLastDate = sealedBidLastDateElement.getText();
				property.setSealedBidLastDate(sealedBidLastDate);
				
				WebElement sealedBidExtendedDateElement = driver.findElement(By.id("spn_bid_extd_dt"));
				String sealedBidExtendedDate = sealedBidExtendedDateElement.getText();
				property.setSealedBidExtendedDate(sealedBidExtendedDate);
				
				WebElement addressElement = driver.findElement(By.id("spn_address"));
				String address = addressElement.getText();
				property.setAddress(address);
				
				WebElement nearestAirportRailwayBusStandElement = driver.findElement(By.id("spn_dist_air_rly"));
				String nearestAirportRailwayBusStand = nearestAirportRailwayBusStandElement.getText();
				property.setNearestAirportRailwayBusStand(nearestAirportRailwayBusStand);
				
				WebElement authorisedOfficerDetailElement = driver.findElement(By.id("spn_ao_detail"));
				String authorisedOfficerDetail = authorisedOfficerDetailElement.getText();
				property.setAuthorisedOfficerDetail(authorisedOfficerDetail);
				
				WebElement propertyVisitedElement = driver.findElement(By.id("lbl_property_count"));
				String propertyVisited = propertyVisitedElement.getText();
				property.setPropertyVisited(propertyVisited);
				
				
				
				WebElement imageInner = driver.findElement(By.id("image_inner"));
				List<WebElement> imgElementsList = imageInner.findElements(By.className("img-thumbnail"));
				for(int k=0; k<imgElementsList.size(); k++) {
					String imgUrl = imgElementsList.get(k).getAttribute("src");
					String folderPath = "C:\\Users\\yashg\\Downloads\\ibapi\\"+ property.getPropertyId();
					String fileName = "img_"+(k+1)+".jpg";
					downloadImg(imgUrl, folderPath, fileName);
					
				}
				WebElement pdfInner = driver.findElement(By.id("pdf_inner"));
				List<WebElement> pdfElementsList = pdfInner.findElements(By.className("img-thumbnail"));
				for(int k=0; k<pdfElementsList.size(); k++) {
					String pdfUrl = pdfElementsList.get(k).getAttribute("src");
					String folderPath = "C:\\Users\\yashg\\Downloads\\ibapi\\"+ property.getPropertyId();
					String fileName = "doc_"+(k+1)+".pdf";
					downloadPdf(pdfUrl, folderPath, fileName);
					
				}
				
				objectList.add(property);
				
				
				closePopUpElement.findElement(By.className("close")).click();

			}

			driver.findElement(By.id("tbl_search_next")).click();
		}
         
		DatabaseOperations dbOperations = new DatabaseOperations();
        dbOperations.insertRecordIBAPI(objectList);
		
		driver.quit();
	}
	
	private static void disableSSLVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private static void downloadPdf(String pdfUrl, String folderPath, String fileName) throws IOException {
        Path folder = Paths.get(folderPath);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder)
;
        }

        Path filePath = Paths.get(folderPath, fileName);

        URL url = new URL(pdfUrl);
        try (InputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile())) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("PDF downloaded: " + filePath.toString());
        }
    }
	
	private static void downloadImg(String imgUrl, String folderPath, String fileName) throws IOException {
		
		Path folder = Paths.get(folderPath);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder)
;
        }
        try {
        URL url = new URL(imgUrl);
        BufferedImage img = ImageIO.read(url);
        File file = new File(folderPath, fileName);
        ImageIO.write(img, "jpg", file);
        System.out.println("Image downloaded: " + file.getAbsolutePath());
        }
        catch(Exception e) {
        	
        }
        
	}
	
	public static Wait<WebDriver> getWaitObject(WebDriver driver)
	{
		return new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
	}
	
	public static boolean checkElementByClassNameElement(WebElement element, final String className, WebDriver driver) {
	     boolean flag = false;
	        try 
	        {
	        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
	    		
	        	WebElement webElement = element.findElement(By.className(className));
	    		if (webElement.isDisplayed() && webElement.isEnabled())
	                flag = true;
	    		
	        } catch (NoSuchElementException e) {
	            flag = false;
	        }
	        catch (StaleElementReferenceException e) {
	            flag = false;
	        }
	        finally{
	        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        }
	        return flag;
	}
	
	public static boolean checkClickableElementById(final String id, WebDriver driver) {
	     boolean flag = false;
	        try 
	        {
	        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
	    		
	        	WebElement webElement = driver.findElement(By.id(id));
	    		if (webElement.isDisplayed() && webElement.isEnabled())
	                flag = true;
	    		
	        } catch (NoSuchElementException e) {
	            flag = false;
	        } 
	        finally{
	        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        }
	        return flag;
	}
}