package reptile.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reptile.util.DownloadUtil;
import reptile.util.ExcelUtil;

@RestController
@RequestMapping
public class TestController {
   public static String [] title = {"楼盘","销售状态","区域","户型","均价（元/平）"};
//   @RequestMapping("/pwd.do")
//   @ResponseBody
//   public String testEndord(){
//	   String password=new BCryptPasswordEncoder(4).encode("123");
//	   return "加密："+password;
//   }
   @RequestMapping(value="testAjax.do",method=RequestMethod.POST)
   public String testAjax(String submit){
	   return submit;
   }
	@GetMapping(value="parser")
	public String parser(HttpServletRequest  request,HttpServletResponse response,String param){
		System.out.println(param);
		Document parse =null;
		try {
			parse = Jsoup.parse(html(param));
		} catch (Exception e) {
			return  "地址错误！";
		}
		 
		StringBuffer buffer = new StringBuffer();
		htmlDocument(parse,buffer);
		try {
			 String[] split = buffer.toString().split("\t\r");
			  String [][] content=new String[split.length][title.length];
			  String[] split2=null;
			  for (int i = 0; i < split.length; i++) {
				  split2 = split[i].split("#");
				  for (int j = 0; j < split2.length; j++) {
					  content[i][j]=split2[j];
				}
			}
			  //excel文件名
	          String fileName = "楼盘信息"+System.currentTimeMillis()+".xls";
	          //sheet名
	          String sheetName = "报名信息表";
			//表格的列名
	         HSSFWorkbook hssfWorkbook = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
	         DownloadUtil.setResponse(request, response, fileName);
			 OutputStream os = response.getOutputStream();
			 hssfWorkbook.write(os);
	         os.flush();
	         os.close();
//
//			IOUtils.copy(fis, response.getOutputStream());
//			response.flushBuffer();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		/*CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(param);
		CloseableHttpResponse response =null;
		String yemian="";
		StringBuffer buffer = new StringBuffer();
		try {
			// 配置信息
						RequestConfig requestConfig = RequestConfig.custom()
								// 设置连接超时时间(单位毫秒)
								.setConnectTimeout(5000)
								// 设置请求超时时间(单位毫秒)
								.setConnectionRequestTimeout(5000)
								// socket读写超时时间(单位毫秒)
								.setSocketTimeout(5000)
								// 设置是否允许重定向(默认为true)
								.setRedirectsEnabled(true).build();

			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			yemian=EntityUtils.toString(entity);
			yemian = new String(yemian.getBytes("gb2312"),"gb2312");
			System.out.println(response.getStatusLine()+"  url:"+yemian);
			 
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} */ 
		
		
		return buffer.toString();
	}
	public static void main(String[] args) {
		
//			System.out.println(response.getStatusLine()+"  url:"+yemian);
//		html("http://www.0915home.com/build/index.php?areaid=1&page=1");
		StringBuffer buffer = new StringBuffer();
//		String html = html("http://www.0915home.com/build/index.php?areaid=1&page=1");；
//		Document document = Jsoup.parse(html);
//		Document parse = Jsoup.parse(html("http://www.0915home.com/build/index.php?price=&xsdl=&areaid=2"));
//		Element first = parse.select(".filter-mod").select("dl").first().selectFirst(".filter-item");
//		System.out.println(first.text());
		
		  htmlDocument(Jsoup.parse(html("http://www.0915home.com/build/index.php?price=&xsdl=&areaid=")),buffer);
		  System.out.println(buffer.toString());
		  String[] split = buffer.toString().split("\t\r");
		  String [][] content=new String[split.length][title.length];
		  String[] split2=null;
		  for (int i = 0; i < split.length; i++) {
			  split2 = split[i].split("#");
			  for (int j = 0; j < split2.length; j++) {
				  content[i][j]=split2[j];
//				  System.out.print(split2[j]);;
			}
			  /*
	            //excel文件名
	            fileName = "activity_sign_"+System.currentTimeMillis()+".xls";
	            //sheet名
	            String sheetName = "报名信息表";
	            String [][] content=new String[jsonArray.size()][title.length];
	            //表格的列名
	            for (int i = 0; i < jsonArray.size(); i++) {
	 
	                content[i][0] = jsonArray.getJSONObject(i).getString("nickname");
	                content[i][1] = jsonArray.getJSONObject(i).getString("phone");
	                content[i][2] = jsonArray.getJSONObject(i).getString("company");
	                content[i][3] = jsonArray.getJSONObject(i).getInteger("steps").toString();
	                content[i][4] = jsonArray.getJSONObject(i).getString("ratio");
	                content[i][5] = jsonArray.getJSONObject(i).getString("avatar");
	                content[i][6] = jsonArray.getJSONObject(i).getString("signTime");
	            }
	            wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
		*/}
		  //excel文件名
//          String fileName = "楼盘信息"+System.currentTimeMillis()+".xls";
          //sheet名
//          String sheetName = "报名信息表";
		//表格的列名
//          HSSFWorkbook hssfWorkbook = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
//		  htmlDocument.get("html");
//		  String url = htmlDocument.get("url")==null ?"":(String)htmlDocument.get("url");
//		  while (!"".equals(url)) {
//			
//			
//		  }
//			System.out.println(htmlDocument);
				//获取楼盘名称
//				System.out.println(element.select(".infos").select("a").html());;
//				download(select.get(i).html(),"F:/文档/html.txt");
		  
		
	}
	public static String html(String url){
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response =null;
		String yemian="";
		StringBuffer buffer = new StringBuffer();
		try {
			// 配置信息
						/*RequestConfig requestConfig = RequestConfig.custom()
								// 设置连接超时时间(单位毫秒)
								.setConnectTimeout(5000)
								// 设置请求超时时间(单位毫秒)
								.setConnectionRequestTimeout(5000)
								// socket读写超时时间(单位毫秒)
								.setSocketTimeout(5000)
								// 设置是否允许重定向(默认为true)
								.setRedirectsEnabled(true).build();
*/
			response = client.execute(get); 
			HttpEntity entity = response.getEntity();
			yemian=EntityUtils.toString(entity);//,"gb2312"
//			System.out.println(yemian);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}finally{
			try {
				response.close();
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return yemian;
	}
	public static String htmlDocument(Document document,StringBuffer buffer){
		Elements select = document.select(".item-mod").select(".estate-mod");
		for (int i = 0; i < select.size(); i++) {
			Elements divInfos = select.get(i).children().select("div");
			for (int j = 0; j < divInfos.size(); j++) {
				if (j==0) {
					buffer.append(divInfos.get(j).select("a").html());
					buffer.append("#");
					buffer.append(divInfos.get(j).select("i").html());
					buffer.append("#");
				}
				if (j==1) {
					Elements p = divInfos.get(j).select("p");
					for (int k = 0; k < p.size(); k++) {
						if (k==0) {
							buffer.append(p.get(k).select(".plate").text());
							buffer.append("#");
						}
						if (k==1) {
							buffer.append(p.get(k).select("a").text());
							buffer.append("#");
						}
					}
				}
				if (j==2) {
					Elements p = divInfos.get(j).select("p");
					for (int k = 0; k < p.size(); k++) {
						buffer.append(p.get(k).select(".price").text());
						buffer.append("\t\r");
					}
				}
			}
		}
		Elements select2 = document.select("a.next-page");
		for (int j = 0; j < select2.size(); j++) {
			String className = select2.get(j).className();
			if (className.contains("stat-disable")) {
				return buffer.toString();
			}
			String url = select2.get(j).attr("href").trim();
			if (url !="") {
				Document document1 = Jsoup.parse(html(url));
				htmlDocument(document1,buffer);
			};
		}
		return buffer.toString();
	}
	public static void download(String content,String path){
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(path,true);
			outputStream.write(content.getBytes());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
