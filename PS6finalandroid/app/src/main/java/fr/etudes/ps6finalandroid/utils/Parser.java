package fr.etudes.ps6finalandroid.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Parser {
    private String fileName;

    public Parser(String fileName){
        this.fileName = fileName;
    }

    public boolean write(byte[] bytes, Context context){
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(bytes);
            outputStream.close();
        }
        catch (Exception e){
            e.getStackTrace();
            return false;
        }
        return true;
    }

    public byte[] read(Context context, int byteArraySize){
        byte[] bytes = new byte[byteArraySize];
        try{
            FileInputStream inputStream = context.openFileInput(fileName);
            inputStream.read(bytes);
        }
        catch (Exception e){
            e.getStackTrace();
            //for (int i = 0; i != bytes.length; i++)
        }
        return bytes;
    }
}
