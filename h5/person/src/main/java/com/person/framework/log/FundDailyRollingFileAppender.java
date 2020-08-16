package com.person.framework.log;

import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;

public class FundDailyRollingFileAppender extends RollingFileAppender {
 	public FundDailyRollingFileAppender(){
        maxFileSize = 10485760L;
        maxBackupIndex = 1;
    }

    public FundDailyRollingFileAppender(Layout layout, String filename, boolean append) throws IOException{   
        super(layout, filename, append);
        maxFileSize = 10485760L;
        maxBackupIndex = 1;
    }

    public FundDailyRollingFileAppender(Layout layout, String filename)throws IOException{
        super(layout, filename);
        maxFileSize = 10485760L;
        maxBackupIndex = 1;
    }
    
    public void rollOver(){   
    	String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        LogLog.debug("rolling over count=" + ((CountingQuietWriter)super.qw).getCount());
        LogLog.debug("maxBackupIndex=" + maxBackupIndex);
        if(maxBackupIndex > 0){
            File file = new File(super.fileName +date+ '.' + maxBackupIndex);
            if(file.exists())file.delete();
            File target;
            for(int i = maxBackupIndex - 1; i >= 1; i--){
                file = new File(super.fileName +date+ "." + i);
                if(file.exists()){
                    target = new File(super.fileName +date+ '.' + (i + 1));
                    LogLog.debug("Renaming file " + file + " to " + target);    
                    file.renameTo(target);
                }
            }
            target = new File(super.fileName +date+ "." + 1);
            closeFile();
            file = new File(super.fileName);
            LogLog.debug("Renaming file " + file + " to " + target); 
            file.renameTo(target);
        }
        try{
            setFile(super.fileName, false, super.bufferedIO, super.bufferSize);
        }
        catch(IOException e){
            LogLog.error("setFile(" + super.fileName + ", false) call failed.", e);    
        }
    }
}
