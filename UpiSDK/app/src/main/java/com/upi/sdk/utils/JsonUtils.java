package com.upi.sdk.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.reflect.TypeToken;
import com.rssoftware.upiint.schema.TransactionHistory;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by SwapanP on 22-04-2016.
 */
public class JsonUtils {

    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(BigInteger.class, new JsonDeserializer<BigInteger>() {
                public BigInteger deserialize(JsonElement element, Type type,
                        JsonDeserializationContext context) throws JsonParseException {
                    Number num = null;
                    try {
                        num = NumberFormat.getInstance().parse(element.getAsJsonPrimitive().getAsString());
                    } catch (Exception e) {
                        //ignore
                    }
                    if (num == null) {
                        return context.deserialize(element, type);
                    } else {
                        return BigInteger.valueOf(num.longValue());
                    }
                }
            })
            .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement element, Type type,
                                              JsonDeserializationContext context) throws JsonParseException {
                    Number num = null;
                    try {
                        num = NumberFormat.getInstance().parse(element.getAsJsonPrimitive().getAsString());
                    } catch (Exception e) {
                        //ignore
                    }
                    if (num == null) {
                        return context.deserialize(element, type);
                    } else {
                        return new Date(num.longValue());
                    }
                }
            }).create();


    public static <T> List<T> getList(String json, Class<T> clazz) {
        Gson gson = new Gson();
        TypeToken<List<T>> token = new TypeToken<List<T>>() {
        };
        List<T> objectList = gson.fromJson(json,
                token.getType());
        return objectList;
    }

    //    public static <O> O get(String json, Class<O> outputClazz) {
//        Gson gson = new Gson();
//        boolean isList = List.class.isAssignableFrom(outputClazz);
//        if (isList) {
//            TypeToken<O> token = new TypeToken<O>(){};
//            return gson.fromJson(json, token.getType());
//        } else {
//            return gson.fromJson(json, outputClazz);
//        }
//    }
    public static <O> O get(Object o, Class<O> outputClazz) {
        String json = gson.toJson(o);
        Log.d("Json Utils", "Converting json String: " + json);



        if (outputClazz == null)
            return null;

        Log.d("Json Utils", "Converting to: " + outputClazz.getName());
        if(String.class.equals(outputClazz)) {
            String val = (String)json;
            val = val.replace("\"", "");
    //        Log.d("Json Utils", "Updated String: " + val);
            return (O)val;
        }

        return gson.fromJson(json, outputClazz);
    }
}
