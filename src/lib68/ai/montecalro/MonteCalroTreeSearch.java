/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.montecalro;

/**
 *
 * @author kano
 */
public class MonteCalroTreeSearch {
    
    //  ルートデータ
    private MonteCalroNode _root;
    
    //  選択した葉
    private MonteCalroNode _selectedNode;
    
    //  UCBの最大値
    private double _ucbMax;
    
    //  プレイアウト合計数
    private int _playoutNum;
    
    //  UCB1の定数c,通常は√2
    private static final double C = 2.0;
    
    //  プレイアウトの合計回数の最大値
    private static final int MAX_TOTAL_PLAYOUT_NUM = 10000;
    
    //  各ノードの拡張するプレイアウトの回数の閾値
    private static final int MAX_PLAYOUT_NUM = 30; //30
    
    
    public MonteCalroTreeSearch(MonteCalroNode root){
        _root = root;
        _ucbMax = Double.NEGATIVE_INFINITY;
    }
    
    
    public MonteCalroNode MCT() throws Exception{
    
        //  最初だけ展開
        _root.expand();
        
        for(int i=0; i<MAX_TOTAL_PLAYOUT_NUM; i++){  //  MAX回数だけ実行
            
            //  プレイアウト候補をリセット
            _ucbMax = Double.NEGATIVE_INFINITY;
            _selectedNode = null;
        
            //  プレイアウトする葉を選択する
            _selectedNode = _root;
            while(_selectedNode.getChildrenSize()!=0){
                select(_selectedNode);
                _ucbMax = Double.NEGATIVE_INFINITY;
            }
            
            
            
            
            //  葉の選択チェック
            if(_selectedNode==null){
                throw new Exception("No Selection leaf.");
            }
            
            //  プレイアウト回数追加
            _selectedNode.addPlayNumber();
            _playoutNum++;
            
            //  閾値を超えたらノードを拡張し、子の0個目をプレイアウトする
            if( _selectedNode.getPlayNumber() > MAX_PLAYOUT_NUM ){
                //System.out.println("expanded node.");
                _selectedNode.expand();
                _selectedNode = (MonteCalroNode) _selectedNode.getChild(0);
            }
            
            //  プレイアウトする
            boolean isWin = _selectedNode.doPlayout();
            if(isWin) _selectedNode.addWinNumber();    //  勝利回数の追加
            
            //  値を更新する
            update(_selectedNode,isWin);
            
            //  デバッグ用
            if(i%1000==0)
              System.out.println("passed:"+i+"/"+MAX_TOTAL_PLAYOUT_NUM);
        }
        
        //  ソートする
        _root.sortChildren();
        
        
        //  一番良いのを返す
        return (MonteCalroNode) _root.getChild(0);
        
    }
    
    
    /**
     * UCB1で葉を選択
     */
    private void select(MonteCalroNode root){
        
        //  子ノードの数を取得する
        int w = root.getChildrenSize();
        
        
        /**
         * プレイする子ノードを選択
         */
        for(int j=0; j<w; j++){
            
            MonteCalroNode nodej = (MonteCalroNode) root.getChild(j);
            double xj = nodej.getScore();
            double n = this._playoutNum;    //プレイアウトした数
            double nj = nodej.getPlayNumber();   //そのノードをプレイアウトした数

            double ucb1 = nj==0? Double.POSITIVE_INFINITY : xj + C* Math.sqrt( 2*Math.log(n) / nj );
            
                        
            //  UCB1値が既存のより大きいならそのノードをプレイアウト候補とする
            if(ucb1 > _ucbMax){
                _selectedNode = nodej;
                _ucbMax = ucb1;
            }
            
        }
        
 
        
    }
    
    
    /**
     * プレイアウト回数の更新
     * 最下位のノードから上位のノードへ渡す
     * この時各ノードの評価値は各子ノードの平均値となる
     */
    private void update(MonteCalroNode leaf,boolean isWin){
        
        //  ノードでなければ終了
        if(leaf==null) return;
             
        //  親ノードを取得する
        MonteCalroNode node = (MonteCalroNode)leaf.getParent();
       
        //  親ノードが取得できなければ終了
        if(node==null) return;
        
        //  親ノードの実行回数を増やす
        node.addPlayNumber();
        
        //  勝利なら勝利回数を増やす
        if(isWin)
            node.addWinNumber();
        
        
        //  再起的に親ノードもチェックする
        update(node,isWin);
        
        
    }
    

    
}
