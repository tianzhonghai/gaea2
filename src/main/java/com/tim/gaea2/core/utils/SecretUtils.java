package com.tim.gaea2.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tianzhonghai on 2017/4/28.
 */
public class SecretUtils {
    public static String MD5(String str) throws  RuntimeException
    {
        String result = "";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            try {
                byte[] b = md.digest(str.getBytes("UTF-8"));

                int i;
                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
                result = buf.toString();

            }catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }catch (NoSuchAlgorithmException ex){
            throw new RuntimeException(ex);
        }

        return  result;
    }

    public static String MD5_16(String str){
        String md5_16= str.substring(8, 24);
        return md5_16;
    }
}
