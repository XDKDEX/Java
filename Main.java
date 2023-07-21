import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
        private static String root = "/www";

        public static String path(String root,String path){
                File file = new File(root+path+"/index.html");
                if(file.exists()){
                        return root+path+"/index.html";
                }else{
                        return root+path;
                }
        }

        public static String data(String path) {
                try {
                        BufferedReader Reader = new BufferedReader(new FileReader((path(root,path))));
                        String str1;
                        String str2 = "";
                        while ((str1 = Reader.readLine()) != null) {
                                str2 = str2 + str1;
                        }
                        return "HTTP/1.1 200\n\n\n " + str2;
                } catch (IOException e) {
                }
                return "HTTP/1.1 404";
        }

        public static void main(String[] args) {
                try {
                        ServerSocket SSocket = new ServerSocket(8090);
                        System.out.println("启动服务器....");
                        while (true) {
                                Socket sSocket  = SSocket.accept();
                                BufferedReader br = new BufferedReader(new InputStreamReader(sSocket.getInputStream()));
                                // 读取客户端发送来的消息
                                String mess = br.readLine();
                                System.out.println("客户端：" + mess);
                                String[] buff = mess.split(" ");
                                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sSocket.getOutputStream()));
                                bw.write(data(buff[1]));
                                bw.flush();
                                bw.close();
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
