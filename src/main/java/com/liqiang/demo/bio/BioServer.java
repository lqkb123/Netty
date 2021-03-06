package com.liqiang.demo.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.liqiang.demo.Const.DEFAULT_PORT;

/**
 * bio的服务端主程序
 */
public class BioServer {
    //服务端
    private static ServerSocket server;
    //线程池，处理每个客户端的请求
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    private static void start() throws IOException {
        try {
            //创建ServerSocket
            //如果端口合法且空闲，服务端就监听成功
            server = new ServerSocket(DEFAULT_PORT);
            System.out.println("服务端已启动，端口号：" + DEFAULT_PORT);
            while (true) {
                Socket socket = server.accept();
                System.out.println("有新的客户端连接------");
                //当有新的客户端接入时，打包成一个任务，投入线程池
                executorService.execute(new BioServerHandler(socket));//
            }
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        start();
    }
}
