import java.io.BufferedReader;   
import java.io.InputStream;   
import java.io.InputStreamReader;   
import java.nio.charset.Charset;   
  
import com.jcraft.jsch.Channel;   
import com.jcraft.jsch.ChannelExec;   
import com.jcraft.jsch.JSch;   
import com.jcraft.jsch.Session;   
  
public class JSchDemo   
{   
       
    public static void main(String[] args) throws Exception   
    {   
        /*String charset = "UTF-8";   
        String user = "msx";   
        String passwd = "3";   
        String host = "192.168.2.114";   
        String command = "cd ¡£¡£ ";   
           
        JSch jsch = new JSch();   
        Session session = jsch.getSession(user, host, 22);   
        session.setPassword(passwd);   
        java.util.Properties config = new java.util.Properties();   
        config.put("StrictHostKeyChecking", "no");   
        session.setConfig(config);   
        session.connect();   
           
        Channel channel = session.openChannel("exec");   
        ((ChannelExec) channel).setCommand(command);   
        channel.setInputStream(null);   
        ((ChannelExec) channel).setErrStream(System.err);   
           
        channel.connect();   
        InputStream in = channel.getInputStream();   
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));   
        String buf = null;   
        while ((buf = reader.readLine()) != null)   
        {   
            System.out.println(buf);   
        }   
        reader.close();   
        channel.disconnect();   
        session.disconnect();   */
    	Runtime.getRuntime().exec("D:/1.bat");  

    }   
       
}  
