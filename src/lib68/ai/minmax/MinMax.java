/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.minmax;

import lib68.ai.alphabeta.GameNode;

/**
 * ネガマックス法を適用するためのもの
 */
public class MinMax{
    
    boolean isDebug = false;
    
    public MinMax(){
        
    }
    
    public double execute(GameNode node,int depth){
        //  読み深さによる終了
        if(depth==0){
            if(isDebug) System.out.println("読み深さ到達");
            return node.evaluate();
        }
        
        //  展開がなくて終了
        node.expand();
        int w = node.getChildrenSize();
        if(w==0){
            if(isDebug) System.out.println("終局");
            return node.evaluate();
        }
        
        //  通常は現在の局面でわけるが
        //  次の局面は
        
        double max = Double.NEGATIVE_INFINITY;
        for(int i=0; i<w; i++){
            double score = - execute((GameNode)node.getChild(i),depth-1);
            if(score>max) max = score;
        }
        //  一番いい手を入れておく
        node.setScore(max);
        return max;
        
    }
    
    
    
}
