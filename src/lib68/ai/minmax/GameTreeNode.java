/*
 * 
 */
package lib68.ai.minmax;

import java.util.ArrayList;

/**
 *  ゲーム木用のノード
 */
public abstract class GameTreeNode {
    
    /**
     * 静的評価関数
     */
    public abstract double STATIC_VALUE();
    
    /**
     * 現在プレイヤーのターンかどうか返す
     */
    public abstract boolean isPlayerTurn();
    
    /**
     * ノードを展開する
     */
    public abstract ArrayList<GameTreeNode> expandChildren();
    
    /**
     * 展開したノードを計算した結果を使って
     * そのノードの点数をセットする
     */
    public abstract void setScore(double d);
    
}
