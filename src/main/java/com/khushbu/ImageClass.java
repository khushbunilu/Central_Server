package com.khushbu;



import Jama.Matrix;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.print.attribute.HashDocAttributeSet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by khushbu on 31/5/16.
 */

















@Path("imageclass")

public class ImageClass {

    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }


    short data[][];



    void storeHashMap(Map<String, String> HashMap) throws IOException {

        Properties properties = new Properties();

        for (Map.Entry<String,String> entry : HashMap.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }

        properties.store(new FileOutputStream("HashMap.properties"), null);

    }

    Map<String, String> getHashMap()  {
        Map<String, String> hmap = new HashMap<String, String>();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("HashMap.properties"));
        }
        catch (Exception e)
        {
             return null;
        }
        for (String key : properties.stringPropertyNames()) {
            hmap.put(key, properties.get(key).toString());
        }
        return  hmap;
    }

    static String LastStoredname="";

    static int Server_id=0;

    @POST
    @Path("AddToTrainingSet")
    @Produces("application/json")
    public String AddToTrainingSet(@FormParam("Image")String s,@FormParam("name")String name)
    {
        JSONObject obj= new JSONObject();

        System.out.println("\nAdding to Taining Set");


        if(!LastStoredname.equals(name))
        {
            if(Server_id==4)
            {
                Server_id=0;
            }
                Server_id++;
        }
        LastStoredname=name;

        try {

            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpPost postRequest = new HttpPost("https://khush-server-"+Server_id+".herokuapp.com/api/imageclass/AddToTrainingSet");
            ArrayList<NameValuePair> postParameters;
            postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("Image", s));
            postParameters.add(new BasicNameValuePair("name", name));

            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output;


        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }



        obj.put("status", "success");
        return  String.valueOf(obj);
    }

    static int count=0;

    static Double Percentage[];

    static String Name[];


    @POST
    @Path("RecogniseImage")
    @Produces("application/json")
    public String RecogniseImage(@FormParam("Image")String s)
    {

        System.out.println("Got Image");
        JSONObject obj= new JSONObject();

        Percentage=new Double[4];
        Name=new String[4];

        Thread t1=new ReconThread(1,s);
        t1.start();

        Thread t2=new ReconThread(2,s);
        t2.start();

        Thread t3=new ReconThread(3,s);
        t3.start();

        Thread t4=new ReconThread(4,s);
        t4.start();



        while(true)
        {
            if(count>3)
            {
                break;
            }
            System.out.print(" Count is "+count);
        }

        System.out.print("\n\nname is "+Name[0]);
        System.out.print("\n\npercentage is "+Percentage[0]);
        System.out.print("\n\nname is "+Name[1]);
        System.out.print("\n\npercentage is "+Percentage[1]);
        System.out.print("\n\nname is "+Name[2]);
        System.out.print("\n\npercentage is "+Percentage[2]);
        System.out.print("\n\nname is "+Name[3]);
        System.out.print("\n\npercentage is "+Percentage[3]);

        int max=0;
        for(int i=0;i<4;i++)
        {
            if(Percentage[i]>Percentage[max])
            {
                max=i;
            }
        }
        System.out.print("\n\nname is "+Name[max]);
        System.out.print("\n\nmax percentage is "+Percentage[max]);

        obj.put("status", "success");
        obj.put("name", Name[max]);
        return  String.valueOf(obj);
    }







}
