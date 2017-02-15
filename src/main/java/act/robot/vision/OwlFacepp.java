package act.robot.vision;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wave on 17-2-8.
 */

class OwlFacepp {

    private static final Logger log = LoggerFactory.getLogger(OwlFacepp.class);

    private String api_key = "xfqvIViKTxq4c3qmER8d6L9rpnv2FGl5";
    private String api_secret = "nKpmFqDaRSRegbPe9iYHLcOGSJ6k6c8y";
    private String faceset_token = "a01b57290d1cda13199590e3a30871df";


    //从图片中抽取人脸
    private String detect(String path) throws IOException{

        String url = "https://api-cn.faceplusplus.com/facepp/v3/detect";

        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = HttpClientBuilder.create().build();
        FileBody bin = new FileBody(new File(path));
        StringBody api_key_local = new StringBody(api_key, ContentType.TEXT_PLAIN);
        StringBody api_secret_local = new StringBody(api_secret, ContentType.TEXT_PLAIN);
        HttpEntity mutiEntity = MultipartEntityBuilder.create()
                .addPart("image_file", bin)
                .addPart("api_key", api_key_local)
                .addPart("api_secret", api_secret_local)
                .build();
        httpPost.setEntity(mutiEntity);
        HttpResponse  httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity =  httpResponse.getEntity();
        String responseString = EntityUtils.toString(httpEntity);
        log.info(responseString);
        JsonArray faces = new JsonParser().parse(responseString).getAsJsonObject().get("faces").getAsJsonArray();
        if (faces.size() == 0){
            return null;
        }else{
            return faces.get(0).getAsJsonObject().get("face_token").getAsString();
        }
    }


    //人脸搜索
    String search(String path) throws IOException{

        String url = "https://api-cn.faceplusplus.com/facepp/v3/search";
        double Threshold = 65.3;

        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = HttpClientBuilder.create().build();
        FileBody bin = new FileBody(new File(path));
        StringBody api_key_local = new StringBody(api_key, ContentType.TEXT_PLAIN);
        StringBody api_secret_local = new StringBody(api_secret, ContentType.TEXT_PLAIN);
        StringBody faceset_token_local = new StringBody(faceset_token, ContentType.TEXT_PLAIN);
        HttpEntity mutiEntity = MultipartEntityBuilder.create()
                .addPart("image_file", bin)
                .addPart("api_key", api_key_local)
                .addPart("api_secret", api_secret_local)
                .addPart("faceset_token", faceset_token_local)
                .build();
        httpPost.setEntity(mutiEntity);
        HttpResponse  httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity =  httpResponse.getEntity();
        String responseString = EntityUtils.toString(httpEntity);
        log.info(responseString);
        JsonArray results = new JsonParser().parse(responseString).getAsJsonObject().get("results").getAsJsonArray();
        if (results.size() == 0){
            return null;
        }else{
            JsonObject result = results.get(0).getAsJsonObject();
            double conf = result.get("confidence").getAsDouble();
            String name = result.get("user_id").getAsString();
            if (conf > Threshold){
                return name;
            }else{
                return "";
            }
        }
    }


    //向人脸库中添加人脸
    boolean face_add(String path, String face_name) throws IOException{

        String url = "https://api-cn.faceplusplus.com/facepp/v3/faceset/addface";

        String face_token = detect(path);
        if (face_token == null) return false;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpclient = HttpClientBuilder.create().build();
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("api_key", api_key));
        params.add(new BasicNameValuePair("api_secret", api_secret));
        params.add(new BasicNameValuePair("faceset_token", faceset_token));
        params.add(new BasicNameValuePair("face_tokens", face_token));
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse httpResponse = httpclient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String responseString = EntityUtils.toString(httpEntity);
        log.info(responseString);
        face_naming(face_name, face_token);
        return true;
    }


    //给人脸添加名字标识
    private void face_naming(String face_name, String face_token) throws IOException{

        String url = "https://api-cn.faceplusplus.com/facepp/v3/face/setuserid";

        HttpPost httpPost = new HttpPost(url);
        HttpClient httpclient = HttpClientBuilder.create().build();
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("api_key", api_key));
        params.add(new BasicNameValuePair("api_secret", api_secret));
        params.add(new BasicNameValuePair("face_token", face_token));
        params.add(new BasicNameValuePair("user_id", face_name));
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse httpResponse = httpclient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String responseString = EntityUtils.toString(httpEntity);
        log.info(responseString);
    }



    //创建人脸库
    void faceset_create() throws IOException{

        String url = "https://api-cn.faceplusplus.com/facepp/v3/faceset/create";

        HttpPost httpPost = new HttpPost(url);
        HttpClient httpclient = HttpClientBuilder.create().build();
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("api_key", api_key));
        params.add(new BasicNameValuePair("api_secret", api_secret));
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse httpResponse = httpclient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String responseString = EntityUtils.toString(httpEntity);
        log.info(responseString);
    }
}
