package com.example.admin.xmlparsing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity {
    Connection connection = new Connection();// Connection Class Contains Base URL.
    String resultData;// respons data of XML Request.
    String SOAP_ACTION;// Temp Link to method.
    String NAMESPACE;// Namespace
    String METHOD_NAME;// Name of XML method.
    String URL;// Connection String.
    SoapObject request, request1, request2, request3;// SOAP Object input string.
    SoapSerializationEnvelope envelope;//
    HttpTransportSE androidHttpTransport;
   // StringWriter output;
    //int flag = 0;
    //int progessbar_count = 0;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetUserDetails getUserDetails=new GetUserDetails();
                        getUserDetails.execute("");
    }

    private class GetUserDetails extends AsyncTask<String,Integer,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           progressDialog=ProgressDialog.show(MainActivity.this,"Please Wait","Loading Data",false,false);
           /* progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();*/
        }

        @Override
        protected String doInBackground(String... strings) {
            progressDialog.dismiss();
            SOAP_ACTION = "http://tempuri.org/NI_UL_GET_USER_DETAILS";
            NAMESPACE = "http://tempuri.org/";
            METHOD_NAME = "NI_UL_GET_USER_DETAILS";
            URL = connection.URL;
            try{
                request=new SoapObject(NAMESPACE,METHOD_NAME);//SOAP Object Request
                //Add parameter to Request SOAP Object.
                request.addProperty("StrKey","GUARD");//SOAP Object Request parameter 1
                request.addProperty("strProjectFlag","GUARD");//SOAP Object Request parameter 2
                request.addProperty("strUserId","11971");//SOAP Object Request parameter 3
                System.out.println("GetUserDetails request SOAP Object"+request);

                //Envelope
                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.debug = true;

            }catch (Exception e){
                e.printStackTrace();
               // flag = 1;
                resultData = "Error in doinBackground: " + e.getMessage();
                System.out.println("GetUserDetails webservice exception e:" + e);
            }
            parseXMLData();
            androidHttpTransport.reset();
            return "Ok";
        }

        private void parseXMLData() {
            try {
                androidHttpTransport.call(SOAP_ACTION,envelope);

                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();

                if(obj!=null){
                    obj1= (SoapObject) obj.getProperty("diffgram");
                    obj2= (SoapObject) obj1.getProperty("NewDataSet");
                    System.out.println("GetUserDetails property count "+ obj2.getPropertyCount());
                    int i1 = obj2.getPropertyCount();

                    if(i1>0){
                            for(int i=0;i<obj2.getPropertyCount();i++){
                                obj3= (SoapObject) obj2.getProperty(i);
                                System.out.println("GetUserDetails obj3 Data="+obj3);

                                //String USER_ENTITY=obj3.getProperty("USER_ENTITY").toString();
                                String USER_ENTITY=obj3.getProperty(0).toString();
                                String USER_ID=obj3.getProperty("USER_ID").toString();
                                String FULL_NAME=obj3.getProperty("FULL_NAME").toString();
                                String EMAIL_ID=obj3.getProperty("EMAIL_ID").toString();
                                String MOBILE=obj3.getProperty("MOBILE").toString();
                                String USER_TYPE=obj3.getProperty("USER_TYPE").toString();
                                String USER_PASS_SIGN=obj3.getProperty("USER_PASS_SIGN").toString();
                                String PARENT_USER_MOBILE=obj3.getProperty("PARENT_USER_MOBILE").toString();
                                String ENTITY=obj3.getProperty("ENTITY").toString();
                                String ORG_NAME=obj3.getProperty("ORG_NAME").toString();
                                String DUTY_EXPIRED_ON=obj3.getProperty("DUTY_EXPIRED_ON").toString();
                                String LOG_ID=obj3.getProperty("LOG_ID").toString();
                                String EXPIRARY_DATE=obj3.getProperty("EXPIRARY_DATE").toString();
                                String NOTIFICATION=obj3.getProperty("NOTIFICATION").toString();
                                String ALLOW=obj3.getProperty("ALLOW").toString();
                                //String AUTO_RPT_INTERVAL=obj3.getProperty("AUTO_RPT_INTERVAL").toString();
                                String AUTO_RPT_INTERVAL=obj3.getPropertyAsString("AUTO_RPT_INTERVAL");

                                System.out.println("GetUserDetails USER_ENTITY="+USER_ENTITY);
                                System.out.println("GetUserDetails USER_ID="+USER_ID);
                                System.out.println("GetUserDetails FULL_NAME="+FULL_NAME);
                                System.out.println("GetUserDetails EMAIL_ID="+EMAIL_ID);
                                System.out.println("GetUserDetails MOBILE="+MOBILE);
                                System.out.println("GetUserDetails USER_TYPE="+USER_TYPE);
                                System.out.println("GetUserDetails USER_PASS_SIGN="+USER_PASS_SIGN);
                                System.out.println("GetUserDetails PARENT_USER_MOBILE="+PARENT_USER_MOBILE);
                                System.out.println("GetUserDetails ENTITY="+ENTITY);
                                System.out.println("GetUserDetails ORG_NAME="+ORG_NAME);
                                System.out.println("GetUserDetails DUTY_EXPIRED_ON="+DUTY_EXPIRED_ON);
                                System.out.println("GetUserDetails LOG_ID="+LOG_ID);
                                System.out.println("GetUserDetails EXPIRARY_DATE="+EXPIRARY_DATE);
                                System.out.println("GetUserDetails NOTIFICATION="+NOTIFICATION);
                                System.out.println("GetUserDetails ALLOW="+ALLOW);
                                System.out.println("GetUserDetails AUTO_RPT_INTERVAL="+AUTO_RPT_INTERVAL);

                            }
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
