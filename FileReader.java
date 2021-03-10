package com.AITU;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileReader {

//    private static final String = "";

    public FileReader(){
        try(
                BufferedInputStream in = new BufferedInputStream(new URL("https://euc-excel.officeapps.live.com/x/_layouts/XlFileHandler.aspx?WacUserType=WOPI&usid=44fb4ad2-abed-4083-a919-be490b2cd905&NoAuth=1&waccluster=GEU2").openStream());
                FileOutputStream fileOutputStream = new FileOutputStream("schedule.xlsx")) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (
                IOException e) {
            // handle exception
        }

    }

}
