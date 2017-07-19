package com.upi.sdk.core;

import com.rssoftware.upiint.schema.ChannelType;

/**
 * Created by SwapanP on 22-04-2016.
 */
public class UPPSDKConstants {

//   public static final String UPP_BASE_URL = "http://172.18.17.121:10000/upiint/";

    //RBL UAT.............
     // public static final String UPP_BASE_URL = "http://Rbluatpsp1.rssoftware.co.in/upiint/";

    //RBL PRODUCTION.............
    //public static final String UPP_BASE_URL = "https://matm.rblbank.com/rblupi/upiint/";

    //IDBI Oracle.....
    // public static final String UPP_BASE_URL = "http://172.18.17.120:10002/upiint/";

    //IDBI Mumbai.....
    //public static final String UPP_BASE_URL = "https://idbiUPI.idbibank.co.in/upiint/";

    //IDBI Upi.....
   // public static final String UPP_BASE_URL = "https://idbiUPI.idbibank.co.in/upiint/";
    //UAT
    // public static final String UPP_BASE_URL ="https://upitest.idbibank.com/upiint/";

    //LKVP Bank
     public static final String UPP_BASE_URL = "http://121.243.113.179:9080/upiint/";

    //LKVP Bank public ip..........
 //   public static final String UPP_BASE_URL = "http://121.243.113.179:9080/upiint/";
//LKVP Bank live ip..........
    //public static final String UPP_BASE_URL = "https://upaay.lvbank.in/upiint/";

    public static final ChannelType CURRENT_CHANNEL_TYPE = ChannelType.MOBILE;
    public static  String CURRENT_CHANNEL_ID="MOBILE";
    public static final long CL_TOKEN_EXPIRY_DAYS = 30;
}
