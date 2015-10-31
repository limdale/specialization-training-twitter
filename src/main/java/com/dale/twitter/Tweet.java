package com.dale.twitter;
import java.io.*;
import java.net.*;
import android.util.Base64;

public class Tweet
{

	public static String CONSUMER_KEY = "httDJtbjpL0C4JH8qQHwktA3E";
	public static String CONSUMER_SECRET = "xbEPp4W8UlkS3qxNcTUadGLFeKLbFb6ouUJ9rDg9m8hAiYIhZV";
	public static String ACCESS_TOKEN = "3659720115-7N7bNgDZ8KWz5d30K2HhUiihTsjwFSd7BsAlWSX";
	public static String ACCESS_TOKEN_SECRET = "wtWqwv0yYO73jAJ8sSO0fRSSdQrACjzgJ99y8upvnclQQ";
	public static String UPDATE_URL = "https://api.twitter.com/1.1/statuses/update.json";
	public static String HOME_URL = "https://api.twitter.com/1.1/statuses/home_timeline.json";

	public static String post(String text) throws Exception{
		String urlString = UPDATE_URL;
		URL url = new URL(urlString);
		HttpURLConnection uc = (HttpURLConnection)url.openConnection();
		String authorizationString = generateAuthorizationString(urlString, text,
			CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		uc.setDoOutput(true);
		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		uc.setRequestProperty("Authorization", authorizationString);
		OutputStream out = uc.getOutputStream();
		out.write(("status=" + URLEncoder.encode(text, "UTF-8")).getBytes());
		InputStream is = uc.getInputStream();
		String v = null;

		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		while((v=bufferedReader.readLine())!=null) {
			stringBuffer.append(v);
		}

		return stringBuffer.toString();
	}

	public static String getHomeTimeline() throws Exception{
		String urlString = UPDATE_URL;
		URL url = new URL(urlString);
		HttpURLConnection uc = (HttpURLConnection)url.openConnection();
		//String authorizationString = generateAuthorizationString(urlString, text,
		//	CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		uc.setDoOutput(true);
		uc.setRequestProperty("Content-Type", "application/json");
		//uc.setRequestProperty("Authorization", authorizationString);
		OutputStream out = uc.getOutputStream();
		out.write(("").getBytes());
		InputStream is = uc.getInputStream();
		String v = null;

		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		while((v=bufferedReader.readLine())!=null) {
			stringBuffer.append(v);
		}

		return stringBuffer.toString();
	}

	public static String generateAuthorizationString(String url, String status, 
		String oauth_consumer_key, String oauth_consumer_secret, String oauth_token,
		String oauth_token_secret) throws Exception{
		StringBuffer sb = new StringBuffer();
		String oauth_version = "1.0", oauth_signature_method="HMAC-SHA1",
			oauth_nonce = "979059a62c66e7723379ac0dfa26f463",
			oauth_timestamp = new Long(System.currentTimeMillis() / 1000).toString();
		String parameter_string = "oauth_consumer_key=" + oauth_consumer_key +
			"&oauth_nonce=" + oauth_nonce +
			"&oauth_signature_method=" + oauth_signature_method +
			"&oauth_timestamp=" + oauth_timestamp +
			"&oauth_token=" + oauth_token +
			"&oauth_version=" + oauth_version +
			"&status=" + URLEncoder.encode(status).replace("+", "%20");
		String signature_base_string = "POST&" + URLEncoder.encode(url, "UTF-8") + "&" + 
			URLEncoder.encode(parameter_string);
		String signature_key = URLEncoder.encode(oauth_consumer_secret, "UTF-8") + "&" + 
			URLEncoder.encode(oauth_token_secret);
		String oauth_signature = URLEncoder.encode(
			hashHmac(signature_base_string, signature_key).trim(), "UTF-8");
		sb.append("OAuth");
		sb.append(" oauth_consumer_key=\""); sb.append(oauth_consumer_key); sb.append("\"");
		sb.append(", oauth_nonce=\""); sb.append(oauth_nonce); sb.append("\"");
		sb.append(", oauth_signature=\""); sb.append(oauth_signature); sb.append("\"");
		sb.append(", oauth_signature_method=\""); sb.append(oauth_signature_method); sb.append("\"");
		sb.append(", oauth_timestamp=\""); sb.append(oauth_timestamp); sb.append("\"");
		sb.append(", oauth_token=\""); sb.append(oauth_token); sb.append("\"");
		sb.append(", oauth_version=\""); sb.append(oauth_version); sb.append("\"");

		System.out.println("SBS:\n" + signature_base_string);
		System.out.println("AUTH:\n" + sb.toString());

		return(sb.toString());
	}

	static String hashHmac(String value, String key) {
		String v = null;
		try {
			javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
			mac.init(new javax.crypto.spec.SecretKeySpec(key.getBytes(),
				"HmacSHA1"));
			v = Base64.encodeToString(mac.doFinal(value.getBytes()),
				Base64.DEFAULT);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return(v);
	}
}