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
    Object optimizedMoving;
    GameTreeNode bestNode;
    
    
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
     * @param refOptimizedMove 最適行動
     */
    public double execute(int depth,GameTreeNode root){
    
        /* 最大先読み深さまで達したため終了 */
        if (depth == 0) 
            return root.STATIC_VALUE();    //  静的評価値を返す
        
        int w = 0;
        root.expandChildren();
        w = root.getChildrenSize();
       
        /* 終局あるいは展開するノードがない */
        if(w==0) return root.STATIC_VALUE();
        
        /* 
            今自分の局面であるとき
            考えられるうち静的評価関数が最も高いものを選択＝プレイヤー（自分）にとって良いもの
        */
        if(root.isPlayerTurn()){
            double max = Double.NEGATIVE_INFINITY;
            for(int i=0; i<w; i++){
                double score = execute(depth-1,root.getChild(i));
                if(score>max){
                    max = score;
                    //  ここで最善手を代入
                    //  最後に動かした手が最善手
                    optimizedMoving = root.getLastMoving();
                    bestNode = root.getChild(i);
                    System.out.println();
                }
                //  ここで盤面を戻す
                root.restoreMoving();
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
                double score = execute(depth-1,root.getChild(i));
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
    public Object getOptimizedMoving(){
        return this.optimizedMoving;
    }
    
    
}
