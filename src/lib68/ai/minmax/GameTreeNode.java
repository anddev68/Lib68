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
    public abstract void expandChildren();
    
    /**
     * ノードを取得する
     *  newを使わなくてもいいようにこのような処理を加えてあります
     */
    public abstract GameTreeNode getChild(int index);
    
    /**
     * ノードサイズを取得する
     */
    public abstract int getChildrenSize();
    
    /**
     * 展開したノードを計算した結果を使って
     * そのノードの点数をセットする
     */
    public abstract void setScore(double d);
    
    /**
     * 展開したノードを戻す
     */
    public abstract void restoreMoving();
    
    /**
     * 最後の動きを返す
     */
    public abstract Object getLastMoving();
}
