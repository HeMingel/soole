package com.songpo.searched.util;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author mingel
 */
public class StreamTool {
    /**
     * 从输入流中读取数据
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        //网页的二进制数据
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }
}