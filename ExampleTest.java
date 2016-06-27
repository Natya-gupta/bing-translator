package TestNg;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ExampleTest{

	public static final String DatamarketAccessUri = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";
	private String clientId;
	private String clientSecret;
	private String request;
	private AdmAccessToken token;
	//public String getClientId() {
		//return clientId;
	//}
//	public void setClientId(String clientId) {
//		this.clientId = clientId;
//	}
	/*public String getClientSecret() {
		return clientSecret;
	}*/
//	public void setClientSecret(String clientSecret) {
//		this.clientSecret = clientSecret;
//	}
	public String getRequest() {
	return request;
	}
public void setRequest(String request) {
		this.request = request;
}
	public AdmAccessToken getToken() {
		return token;
	}
	public void setToken(AdmAccessToken token) {
		this.token = token;
	}
	
	
	public ExampleTest(String clientId, String clientSecret) throws IOException
    {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	    this.clientId= URLEncoder.encode(this.clientId,"UTF-8");
	    this.clientSecret= URLEncoder.encode(this.clientSecret,"UTF-8");
		//If clientid or client secret has special characters, encode before sending request
	//	this.request = String.Format("grant_type=client_credentials&client_id={0}&client_secret={1}&scope=http://api.microsofttranslator.com", HttpUtility.UrlEncode(clientId), HttpUtility.UrlEncode(clientSecret));
		this.request= "grant_type=client_credentials&client_id="+ this.clientId +"&client_secret="+ this.clientSecret +"&scope=http://api.microsofttranslator.com";
		
    }
	
	public AdmAccessToken getAccessTokenUsingPost(String DatamarketAccessUri,String request) throws IOException, ParseException{
		
		URL url= new URL(DatamarketAccessUri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
	    conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(request);
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + request);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String res= response.toString();
		//print result
		System.out.println(response.toString());
		JSONParser parser = new JSONParser();
		JSONObject object= (JSONObject) parser.parse(res);
		AdmAccessToken admToken= new AdmAccessToken();
		String tokenType= (String) object.get("token_type");
		String accessToken=(String) object.get("access_token");
		String expiresIn=(String)object.get("expires_in");
		String scope= (String)object.get("scope");
		admToken.setAccessToken(accessToken);
		admToken.setTokenType(tokenType);
		admToken.setExpiresIn(expiresIn);
		admToken.setScope(scope);
		this.setToken(admToken);
		conn.disconnect();
		return this.getToken();
	}
	
	public String translateText(String from,String to,String text) throws IOException{
		String uri = "http://api.microsofttranslator.com/v2/Http.svc/Translate?";
		String request= "text="+URLEncoder.encode(text,"UTF-8")+ "&from="+from+"&to="+to;
		String authToken = "Bearer" + " " + this.getToken().getAccessToken();
		URL url= new URL(uri+request);
	//	System.out.println(authToken);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", authToken);
    //	conn.addRequestProperty("Authorization", authToken);
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	   
	   /* DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(request);
		wr.flush();
		wr.close();*/

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Post parameters : "+request);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		//print result
		System.out.println(response.toString());
		conn.disconnect();
		//return response.toString();
//		File fXmlFile = new File("http://schemas.microsoft.com/2003/10/Serialization/.xml");
//		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//		Document doc = dBuilder.parse(fXmlFile);
//		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//		NodeList nList = doc.getElementsByTagName("string");
		
	    String sss=response.toString();
		sss = sss.substring(sss.indexOf(">") + 1);
		sss = sss.substring(0, sss.indexOf("<"));		
         //System.out.println(sss);
		return sss;
	//	return null;
	}
	/*public String autoDetect(String from,String To)String sss=response.toString();
		sss = sss.substring(sss.indexOf(">") + 1);
		sss = sss.substring(0, sss.indexOf("<"));		
		System.out.println(sss);
		return sss;
	{
		String uri = "http://api.microsofttranslator.com/v2/Http.svc/Translate";
		String request
		
	}*/
	
	public static List<String> getText() throws IOException, ParseException{
		String clientId="Natya-G_123";
		String clientSecret="Sqwte0PhkDg/rqXABl4ubCg7e1iKp5OyGSyyNnOjO70=";
	
		List<String> resultsAPI= new ArrayList<String>();
		
		ExampleTest authToken= new ExampleTest(clientId, clientSecret);
			try {
				AdmAccessToken token=authToken.getAccessTokenUsingPost(DatamarketAccessUri, authToken.getRequest());
				System.out.println(token.toString());
			System.out.println(token.getAccessToken());
				/*String text = "Hello";
				String from = "en";
				String to = "de";*/
				String line="";
				  File file = new File("/home/natyagupta/workspace/TestNg1/src/Translator.csv");
				 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
  
				  while( (line = br.readLine())!= null ){
				        String [] tokens = line.split(",");
				        String from = tokens[3];
				        String to = tokens[4];
				        String word = tokens[2];
				       // Language in=Language.valueOf(from.toUpperCase());
				       // Language op=Language.valueOf(to.toUpperCase());
				      //  String text=authToken.translateText(in.toString(),op.toString(), word);
//				        BufferedReader br1 = new BufferedReader(new FileReader("/home/natyagupta/workspace/TestNg1/src/Translator.csv"));
//				        BufferedReader br2 = new BufferedReader(new FileReader("/home/natyagupta/workspace/TestNg1/src"));
//				        String line1 =  null;
//				        String[] str=null;
//				        try {
//				            while((line = br.readLine())!=null){
//				             str = line.split(",");
//				             System.out.println(str[0]);             
//				           }  				        
                        resultsAPI.add(authToken.translateText(from, to, word));
				  }
				  br.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
			
		return resultsAPI;
		}
}
	
