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
    private static final double C = 1.4142135;
    
    //  プレイアウトの合計回数の最大値
    private static final int MAX_TOTAL_PLAYOUT_NUM = 1000;
    
    //  各ノードの拡張するプレイアウトの回数の閾値
    private static final int MAX_PLAYOUT_NUM = 100;
    
    
    public MonteCalroTreeSearch(MonteCalroNode root){
        _root = root;
    }
    
    
    public MonteCalroNode MCT() throws Exception{
    
        for(int i=0; i<MAX_TOTAL_PLAYOUT_NUM; i++){  //  MAX回数だけ実行
            
            //  プレイアウト候補をリセット
            _ucbMax = Double.NEGATIVE_INFINITY;
            _selectedNode = null;
        
            //  プレイアウトする葉を選択する
            select(_root);
            
            //  葉の選択チェック
            if(_selectedNode==null){
                throw new Exception("No Selection leaf.");
            }
            
            //  プレイアウト回数追加
            _selectedNode.addPlayNumber();
            _playoutNum++;
            
            //  閾値を超えたらノードを拡張し、子の0個目をプレイアウトする
            if( _selectedNode.getPlayNumber() > MAX_PLAYOUT_NUM ){
                _selectedNode.expand();
                _selectedNode = (MonteCalroNode) _selectedNode.getChild(0);
            }
            
            //  プレイアウトする
            boolean isWin = _selectedNode.doPlayout();
            
            //  値を更新する
            update(_selectedNode,isWin);
            
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
         * 終端ノードであればそのノードをプレイアウト候補とし、
         * UCB1法により値を計算
         */
        if( w==0 ){
            
            double xj = root.getScore();
            double n = this._playoutNum;    //プレイアウトした数
            double nj = root.getPlayNumber();   //そのノードをプレイアウトした数
            
            double ucb1 = xj + C* Math.sqrt( 2*Math.log(n) / nj );
            
            //  UCB1値が既存のより大きいならそのノードをプレイアウト候補とする
            if(ucb1 > _ucbMax){
                _selectedNode = root;
                _ucbMax = ucb1;
            }

            return;
        }
        
        
        //  展開できる子ノードの数だけ再起的にチェックする
        for(int j=0; j<w; j++){
            MonteCalroNode nodej = (MonteCalroNode) root.getChild(j);
            select(nodej);       
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
       
        //  親ノードの実行回数を増やす
        node.addPlayNumber();
        
        //  勝利なら勝利回数を増やす
        if(isWin)
            node.addWinNumber();
        
        
        //  再起的に親ノードもチェックする
        update(node,isWin);
        
        
    }
    

    
}
