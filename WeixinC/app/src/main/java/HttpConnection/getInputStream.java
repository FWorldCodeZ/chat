package HttpConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class getInputStream {
	static String out;
	public static String getJsonByInternet(InputStream is){
        try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while(-1 != (len = is.read(buffer))){
                    baos.write(buffer,0,len);
                  
                }
                out= baos.toString("utf-8");
               
            
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return out;
    }

	
}
