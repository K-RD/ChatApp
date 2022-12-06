import java.net.*;
import java.io.*;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter pr;

    public Client()
    {
        try {
            System.out.println("Sending request to server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("Connection established");

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            pr=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();


            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading()
    {
        //  This Thread will read the data 

        Runnable reader=()->{
            System.out.println("Reader started");

            try 
            { 
                while(!socket.isClosed())
                {

                                   
                    String msg=br.readLine();
                    if(msg.equals("exit"))
                    {
                        System.out.println("Server Terminated the chat.");
                        socket.close();
                        break;
                    }

                    System.out.println("Server: "+msg);
                
                }   
            } catch (Exception e) 
            {
                // e.printStackTrace();
                System.out.println("Connection is closed");
            }
        };
        new Thread(reader).start();

    }

     
    public void startWriting()
    {
        //  This Thread will write the data 

        Runnable writer=()->{
            System.out.println("Writer started...");

            try {
                while(!socket.isClosed())
                {
                
                    
                    BufferedReader brr1=new BufferedReader(new InputStreamReader(System.in));
                    String content=brr1.readLine();

                    pr.println(content);
                    pr.flush();

                    if(content.equals("exit"))
                    {
                        socket.close();
                        break;
                    }
                
                }
                System.out.println("Connection is closed");
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
        };
        new Thread(writer).start();

    }

    public static void main(String[] args) {
        System.out.println("This is Client...");
        new Client();
    }
}
