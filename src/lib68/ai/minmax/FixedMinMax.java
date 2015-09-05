/*
 * MinMaxがうまく動かないので修正しました
 */
package lib68.ai.minmax;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * MinMax修正版
 * 通常のMinMaxの使用はやめてください
 * こっちを使用してください
 * 
 */
public class FixedMinMax {
   
    /**
     * AIがn手先まで読んだ、その推移手順表
     */
    LinkedList<GameTreeNode> nodeList;
    
    /**
     * AIが最終的に出した最適解
     * 自分が打ち込んだときのノード
     */
    GameTreeNode optimizedNode;
    
    
    boolean isDebug = false;
    public void setDebugMode(boolean flag){ isDebug = flag; }
    

    public FixedMinMax(){
        nodeList = new LinkedList();
    }
    
    /**
     * execute()を呼ぶ前に必ず初期化を行います
     * ここでは積み上げリストのクリアを行います
     */
    public void prepare(){
        nodeList.clear();
    }
    
    /**
     * 最適解を見つけます
     * @param depth 先読みする手数
     * @param root 現在の盤面
     */
    public double execute(int depth,GameTreeNode root){
    
        int w = 0;
        GameTreeNode tmp;
        ArrayList<GameTreeNode> children = root.expandChildren();
        w = children.size();
        
        /* 読み終了 */
        if(depth==0) return root.STATIC_VALUE();    //  静的評価値を返す
        
        /* 終局あるいは展開するノードがない */
        if(w==0) return root.STATIC_VALUE();
        
        /* 
            今自分の局面であるとき
            考えられるうち静的評価関数が最も高いものを選択＝プレイヤー（自分）にとって良いもの
        */
        if(root.isPlayerTurn()){
            double max = Double.NEGATIVE_INFINITY;
            for(int i=0; i<w; i++){
                double score = execute(depth-1,children.get(i));
                if(score>max){
                    max = score;
                    optimizedNode = children.get(i);
                }
            }
            //  ルートに評価値をセット
            root.setScore(max);
            return max;
            
         /*
          今敵の局面であるとき
          考えられるうち評価が最も低いものを選択
          */            
            
        }else{
            double min = Double.POSITIVE_INFINITY;
            for(int i=0; i<w; i++){
                double score = execute(depth-1,children.get(i));
                if(min>score) min = score;
            }
            //  ルートに評価値をセット
            root.setScore(min);
            return min;
        }
        
    }
    
    
    /**
     * 次の１手を返す
     * @return 最適解
     */
    public GameTreeNode getOptimizedNode(){
        return this.optimizedNode;
    }
    
    
}
