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
    
    private boolean debugFlag;
    
    public MonteCalroTreeSearch(MonteCalroNode root){
        _root = root;
        _ucbMax = Double.NEGATIVE_INFINITY;
    }
    
    /**
     * デバッグモードを有効にします
     * 途中のログを吐き出します
     */
    public void setDebug(boolean d){
        this.debugFlag = d;
    }
    
    /**
     * MCT
     * @param max 1個あたりのプレイアウト回数
     * @param totalMax 全体のプレイアウト回数
     * @return
     * @throws Exception 
     */
    public MonteCalroNode MCT(int max,int totalMax) throws Exception{
    
        //  最初だけ展開
        _root.expand();
        
        for(int i=0; i<totalMax; i++){  //  MAX回数だけ実行
            
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
                System.out.println("No Selection leaf.");
                        //  ソートする
                 _root.sortChildren();
        
                if(debugFlag){
                    for(int k=0; k<_root.getChildrenSize(); k++){
                        _root.getChild(k).print();
                    }
                }
        
        
                //  一番良いのを返す
                return (MonteCalroNode) _root.getChild(0);
                
            }
            
            //  プレイアウト回数追加
            _selectedNode.addPlayNumber();
            _playoutNum++;
            
            //  閾値を超えたらノードを拡張し、子の0個目をプレイアウトする
            if( _selectedNode.getPlayNumber() > max ){
                //System.out.println("expanded node.");
                _selectedNode.expand();
                //  暫定措置
                //  子ノードがある場合のみ子ノードを選択
                if(_selectedNode.getChildrenSize()>0){
                    _selectedNode = (MonteCalroNode) _selectedNode.getChild(0);
                }
            }
            
            //  プレイアウトする
            boolean isWin = _selectedNode.doPlayout();
            if(isWin) _selectedNode.addWinNumber();    //  勝利回数の追加
            
            //  値を更新する
            update(_selectedNode,isWin);
            
            //  デバッグ用
            if(debugFlag && i%1000==0)
              System.out.println("passed:"+i+"/"+totalMax);
        }
        
        //  ソートする
        _root.sortChildren();
        
        if(debugFlag){
            for(int i=0; i<_root.getChildrenSize(); i++){
                _root.getChild(i).print();
            }
        }
        
        
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
