/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.alphabeta;

import ai.connection.HostMachine;
import lib68.ai.Node;
import ai.connection.GuestMachine;
import ai.connection.HostMachine.OnRecieveListener;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 分散処理を可能にしたアルファベータ法
 */
public class DistributedAlphaBeta implements OnRecieveListener{
    
    /* 分散ホスト */
    HostMachine _machine;
    
    /* 一番良かった子 */
    Node _bestChild;
        
    /* 現在生きているソケットのリスト */
    private ArrayList<Socket> _socketList;
    
    /* 処理予定の子ノード */
    private LinkedList<Node> _nodeQueue;
    
    /* ソケットに割り当てたノード情報 */
    private HashMap<Socket,Node> _socketHash;
    
    
    public DistributedAlphaBeta(HostMachine machine){
        this._machine = machine;
        this._machine.setOnRecieveListener(this);
    }
    
    
    /**
     * 接続されているゲストPCに対して適切に指示を割り振り
     * すべての結果が出そろうまで待機する
     * ノードは自分のターン固定、次のターンは
     * 
     */
    public double alphaBeta(Node node,int depth,int alpha,double beta,int N){
        

        node.expand();
        int w = node.getChildrenSize();
        
        if( w==0 ){
            //  終局
            System.out.println("終局しました");
            return node.getScore();
        }

        //  生きているソケットのリストを取得する
        _socketList = _machine.getSurviveSocketList();
        
        //  キューに突っ込む
        _nodeQueue = new LinkedList<>();
        for(int i=0; i<w; i++){
            _nodeQueue.addLast(node.getChild(i));
        }
        
        //  ハッシュマップを作成
        _socketHash = new HashMap<>();
        
        
        //  ソケットにデータを送信する
        for(Socket socket : _socketList){
            _socketHash.put(socket,_nodeQueue.getFirst());  //  キューから取り出す
            //  TODO: データの送信処理
        }
        
        
        //  キューが空になるまで処理を行う
        while(!_nodeQueue.isEmpty()){
            
        }
        
        
        return _bestChild.getScore();
        
    }

    
    
    @Override
    public void onReceive(Socket socket, String line) {
        //  受信したデータは点数のはずなので点数に変換する
        
        //  点数が高ければ良いノードとして保存する
        
        //  ノードをキューから削除
        
        //  ソケットに次のノードを割り当てる
        
        
    }
    


    
}
