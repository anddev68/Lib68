/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.montecalro;

import lib68.ai.Node;



/**
 * モンテカルロのノード
 * 評価値による選択を有効にしてあります
 * 
 * 勝率にする場合はこのクラスを継承し、実装してください
 */
public abstract class MonteCalroNode implements Node{

    
    private int playNum;
    private int winNum;
    
    
    protected MonteCalroNode(){
        playNum = 0;
        winNum = 0;
    }
    

    
    
    /**
     * プレイアウトする
     * ゲームが終了するまで仮想的に動かす
     * @return true 勝ち
     */
    protected abstract boolean doPlayout();
    
   
    /**
     * 試行回数を返す
     * @return 試行回数
     */
    public final int getPlayNumber(){ return playNum; }

    /**
     * 試行回数を増やす
     */
    public final void addPlayNumber(){ playNum++; }
    

    /**
     * 勝利回数を増やす
     */
    public final void addWinNumber(){ winNum++; }
    
    /**
     * 勝利回数の取得
     */
    public final int getWinNumber(){ return winNum; }
    
    
    
    
    /**
     * 子をソートする
     */
    public abstract void sortChildren();
    
    
}
