/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.montecalro;



/**
 * 原子モンテカルロによる検索
 * これは弱いのでMonteCalroTreeSeachを利用してください
 * ただし相当早い
 */
public class TraditionalMonteCalro {
    
    //  1個の子ノードをプレイアウトする回数
    private static int PLAYOUT_NUM = 10;
    
    
    /**
     * 原子モンテカルロで探索を行う
     * @param root 親ノード
     * @return 一番良い子ノードを返す
     */
    public static MonteCalroNode MC(MonteCalroNode root){
        
        //  親ノードを拡張し子ノードを作成
        root.expand();
        
        //  子ノードの数を取得
        int w = root.getChildrenSize();
        
        //  終局チェック
        if(w==0){
            System.out.println("終局");
            return null;
        }
        
        //  すべての子に対しプレイアウトする
        for(int j=0; j<w; j++){
            for(int i=0; i<PLAYOUT_NUM; i++){
                
                //  子ノードを取得する
                MonteCalroNode leaf = (MonteCalroNode) root.getChild(i);
                
                //  プレイアウトを実行
                leaf.doPlayout();
            
                //  はさんでみた
                System.out.println(i+"/"+PLAYOUT_NUM);
            }
            
            
        }
        
        //  勝率順に並び替え
        root.sortChildren();
        
        //  一番いいものを返す
        return (MonteCalroNode) root.getChild(0);
    }
    
    
}
