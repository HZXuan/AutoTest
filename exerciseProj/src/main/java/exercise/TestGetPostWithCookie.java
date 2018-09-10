package exercise;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TestGetPostWithCookie {

    private String url;
    private ResourceBundle bundle;
    private CookieStore store;

    @BeforeTest
    public void beforeTest(){
        this.bundle = ResourceBundle.getBundle("exercise",Locale.CHINA);
        this.url = bundle.getString("test.url");
    }
    @Test
    public void getReturnCookie() throws IOException {
        String testUrl = this.url+this.bundle.getString("test.returnCookie");
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(testUrl);
        HttpResponse response = client.execute(get);
        this.store = client.getCookieStore();
//        List<Cookie> list = store.getCookies();
//        for (Cookie cookie : list){
//            System.out.println("Cookie Name = " + cookie.getName());
//            System.out.println("Cookie Value = " + cookie.getValue());
//        }
        System.out.println("***************************");
        JSONObject jsonResult = new JSONObject(EntityUtils.toString(response.getEntity()));
        System.out.println(jsonResult.get("name"));
        System.out.println(jsonResult.get("age"));
    }
    @Test(dependsOnMethods = {"getReturnCookie"})
    public void getWithCookie() throws IOException {
        String testUrl = this.url+this.bundle.getString("test.getWithCookie");
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(testUrl);
        client.setCookieStore(this.store);
        HttpResponse response = client.execute(get);
        System.out.println(response);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
//        JSONObject jsonResult = new JSONObject(result);
//        System.out.println((String)jsonResult.get("msg"));
    }
    @Test(dependsOnMethods = {"getReturnCookie"})
    public void postWithCookie() throws IOException {
        String testUrl = this.url + bundle.getString("test.postWithCookie");
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(testUrl);
        client.setCookieStore(this.store);
        JSONObject param = new JSONObject();
        param.put("fuck","you");
        System.out.println(param);
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        System.out.println(entity);
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        System.out.println(response);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
//        JSONObject jsonObject = new JSONObject(result);
//        System.out.println(jsonObject);
//        System.out.println(jsonObject.get("succeed"));
    }

}

















