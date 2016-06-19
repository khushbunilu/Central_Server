package com.khushbu;

import com.khushbu.imageimport.Image;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by voodoo on 19/6/16.
 */

public class  ReconThread extends Thread {

        String word;
        int Server_id;
        String s;

        public ReconThread(int Server_Id,String s){
            this.Server_id=Server_Id;
            this.s=s;
        }

        public void run(){


            try {

                HttpClient httpClient = HttpClientBuilder.create().build();

                HttpPost postRequest = new HttpPost("https://khush-server-"+Server_id+".herokuapp.com/api/imageclass/RecogniseImage");
                ArrayList<NameValuePair> postParameters;
                postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("Image", s));

                postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

                HttpResponse response = httpClient.execute(postRequest);

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
                String output;

                String s=null;
                while ((output = br.readLine()) != null) {
                    s=output;
                }
                JSONObject jsonObj = new JSONObject(s);

                ImageClass.Name[Server_id-1]=String.valueOf(jsonObj.get("name"));

                ImageClass.Percentage[Server_id-1]=Double.parseDouble(String.valueOf(jsonObj.get("percentage")));

                ImageClass.count=ImageClass.count+1;

                System.out.println("Count set " + ImageClass.count);


            } catch (ClientProtocolException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

}

