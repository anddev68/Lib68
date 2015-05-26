/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai;



/**
 * 汎用ノード
 */
public interface Node {
    
    
    /**
     * 評価値を取得
     * @return 
     */
    public double getScore();
    
    
    /**
     * その盤面に関する情報を出力する
     */
    public void print();
    
    
    /**
     * 親ノードを取得する
     */
    public Node getParent();
    
    
    /**
     * 子ノードの数を取得する
     */
    public int getChildrenSize();
    
    
    /**
     * 子ﾉｰﾄﾞを取得する
     */
    public Node getChild(int index);
    
    
    /**
     * 子ノードを展開する
     */
    public void expand();
    

    
    
    
}
