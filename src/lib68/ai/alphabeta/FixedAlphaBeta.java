/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.alphabeta;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

/**
 * AlphaBetaに重大なバグが見つかったので
 * こちらを使用してください
 */
public class FixedAlphaBeta {
    
    //  ノード探索数
    private int _searchCount;
    
    //  最大深さ
    private int _maxDeep;
    
    
    /**
     * 外部公開用アルファベータメソッド
     * @param node the root node
     * @param n 読み深さ
     * @return theBestNode
     */
    public GameNode ALPHA_BETA(GameNode node,int n){
        _searchCount = 0;
        _maxDeep = n;
        
        preExecute();
        __ALPHA_BETA(node,n,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        postExecute();
        
        
        return (GameNode) node.getChild(node.getChildrenSize()-1);
    }    
    
    
    private double __ALPHA_BETA(GameNode root,int n,double alpha,double beta){
        
        if(n==0){
            //  深さ上限
            _searchCount++;
            return root.evaluate();
        }
        
        //  子ノードを拡張
        root.expand();
        int w = root.getChildrenSize();
        if( w==0 ){
            //  終局
            _searchCount++;
            return root.evaluate();
        }
        
        //  プレイヤーターン
        if(root.isPlayerTurn()){
            
            for(int i=0; i<w; i++){
                alpha = Math.max(alpha,__ALPHA_BETA((GameNode)root.getChild(i),n-1,alpha,beta));
                if(n==_maxDeep){   //  rootのときのみ進捗表示
                    System.out.println(""+i+"/"+w);
                }
                if(alpha>=beta){
                    root.setScore(beta);
                    return beta;    //  カット
                }
            }
            root.setScore(alpha);
            return alpha;
        }
        else{
            for(int i=0; i<w; i++){
                beta = Math.min(beta, __ALPHA_BETA((GameNode)root.getChild(i),n-1,alpha,beta));
                if(alpha>=beta){
                    //  そのノードの評価地を変更
                    root.setScore(alpha);
                    return alpha;   //カット
                }
            }
            root.setScore(beta);
            return beta;
        }
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 実行前に行う
     */
    protected void preExecute(){
        
    }
    
    /**
     * 実行後に行う
     */
    protected void postExecute(){
        
    }
    
    
}
