/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.connection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AIの分散処理を行うホスト（親マシン）です
 * 
 */
public class HostMachine extends Machine{

    /* TCP接続用 */
    private ServerSocket _socket;
    
    /* 接続待ちスレッド */
    private Thread _thread;
    
    /* ソケットごとに受信待機を行うスレッド */
    private ArrayList<RecieveThread> _recieve_thread;
    
    /* 受信したときのリスナー */
    private OnRecieveListener _listener;
    
    
    /**
     * コンストラクタでソケットを初期化しバインドを行う
     * @param port
     * @throws SocketException 
     */
    public void HostMachine(int port) throws SocketException, IOException{
        _socket = new ServerSocket(port);
        _recieve_thread = new ArrayList<>();
        _thread = new Thread(new AcceptThread());
    }
    
    
    public void setOnRecieveListener(OnRecieveListener l){
        this._listener = l;
    }
    
    /**
     * 受信スレッドを開始する
     */
    public void start(){
        _thread.start();
    }
    
    
    /**
     * 受信スレッドを停止する
     */
    public void stop(){
        _thread = null;
    }
    
    
    
    /**
     * 現在接続が生きているマシン数を取得する
     * @return 生きているマシン数
     */
    public int getSurviveMachineNum(){
        int num = 0;
        for(RecieveThread t : _recieve_thread){
            if(t.socket!=null){
                num++;
            }
        }
        return num;
    }
    
    
    /**
     * 生きているソケットのリストを取得する
     * @return 生きているソケット
     */
    public ArrayList<Socket> getSurviveSocketList(){
        ArrayList<Socket> list = new ArrayList<>();
        
        for(RecieveThread t : _recieve_thread){
            if(t.socket!=null){
                list.add(t.socket);
            }
        }
        return list;
    }
    
    
     
    /**
     * 受信したときの処理
     * @param socket 受信したソケット
     * @param line 受信したデータ
     */
    private void onRecieveEvent(Socket socket,String line){
        if(_listener!=null){
            _listener.onReceive(socket, line);
        }
    }
    
    
    
    /**
     * TCPの接続用スレッド
     */
    private class AcceptThread implements Runnable{

        @Override
        public void run() {
            while(true){
                try {
                    //  接続待ち
                    Socket socket = _socket.accept();
                    //  接続されたソケットは別スレッドで監視
                    RecieveThread thread = new RecieveThread(socket);
                    _recieve_thread.add(thread);
                    thread.run();
                   
                } catch (IOException ex) {
                    Logger.getLogger(HostMachine.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }
        }

        
    }
    
   
    /**
     * TCPで受信を監視するスレッド
     */
    private class RecieveThread implements Runnable{

        String ipAdress;
        Socket socket;
        
        
        RecieveThread(Socket socket){
            ipAdress = socket.getInetAddress().getHostAddress();
            System.out.println("Thread created:"+ipAdress);
            this.socket = socket;
        }
        
        
        @Override
        public void run() {
            try{
                while(socket!=null){
                    InputStream is = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line=null;
                    while( (line=br.readLine())!=null){
                        onRecieveEvent(socket,line);
                    }
                    br.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
            
            //  ソケット終了処理
            if(socket!=null){
                try {
                    socket.close();
                    socket = null;
                } catch (IOException ex) {
                    Logger.getLogger(HostMachine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    

    /**
     * 受信リスナ
     */
    public interface OnRecieveListener{
        public void onReceive(Socket socket,String line);
    }

    
    
    
    
    
}
