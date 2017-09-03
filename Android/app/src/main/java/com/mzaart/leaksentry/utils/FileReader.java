package com.mzaart.leaksentry.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class contains methods useful for operations on files stored in the application's
 * private internal storage.
 */

public class FileReader {

    private Context context;

    public FileReader(Context context) {
        this.context = context;
    }

    /**
     * Retrieves all numbers stores in a file (IDs for example)
     * separated by a newline character ("\n")
     * @param fileName: File name
     * @return an array list holding the ids
     */
    public ArrayList<Short> readNumbers(String fileName) {
        ArrayList<Short> nums = new ArrayList<>();
        BufferedReader reader = getBufferReader(fileName);

        if(reader != null) {
            String numStr;
            try {
                while ((numStr = reader.readLine()) != null) {
                    nums.add(Short.parseShort(numStr));
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return nums;
    }

    /**
     * Appends data to a file in internal storage
     * @param data: the data to write
     * @param fileName: File name
     */
    public boolean appendToFile(String data, String fileName) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            fos.write((data).getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Writes data to a file in internal storage overwriting original file
     * @param data: the data to write
     * @param fileName: File name
     */
    public boolean writeToFile(String data, String fileName) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write((data).getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Gets the BufferReader object of a file in internal storage
     * @param fileName: File name
     * @return BufferReader object for file
     */
    private BufferedReader getBufferReader(String fileName) {
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fis != null) // i.e. the file exists
            return new BufferedReader(new InputStreamReader(fis));

        return null;
    }
}
