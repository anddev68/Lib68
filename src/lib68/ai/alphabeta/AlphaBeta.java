 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.alphabeta;


/**
 * アルファベータ法
 */
public abstract class AlphaBeta{
    
    
    /**
     * 保存値
     */
    private GameNode _bestNode;
    private double _bestScore;
    
    private GameNode _root;
    
    
    /**
     * コンストラクタ
     */
    public AlphaBeta(GameNode root){
        _root = root;
    }
    
    
    /**
     * 探索が終了したrootの子ノードの数を返す
     * @param current 現在終了した個数
     * @param max 最大数
     */
    protected abstract void onProgress(int current,int max);
    

    
    /**
     * 探索する前の前処理
     */
    protected void onPreExecute(){
        
    }
    
    /**
     * 探索した後の後処理
     */
    protected void onPostExecute(){
        
    }
    

   
    
    
    
    /**
     * alpha-beta法による探索をする
     */
    public void ALPHA_BETA(int N){
        
        //  前処理
        onPreExecute();
        
        _bestNode = null;
        _bestScore = Double.NEGATIVE_INFINITY;
        
        __ALPHA_BETA(_root,N,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,N);
        

        //  後処理
        onPostExecute();
        
        
    }
    
    
    private double __ALPHA_BETA(GameNode node,int depth,double alpha,double beta,int N){
    
        //  深さ読みに達したかどうかのチェック
        if( depth == 0 ){
            System.out.println("読み深さに達した");
            double d = node.getScore();
            //setBestNode(node,d);
            return d;
        }
        
        //  ノードを拡張
        node.expand();
        int w = node.getChildrenSize();
        
        
        //  子ノードがなければそれ以上手がない
        if( w==0 ){
            System.out.println("終局");
            double d = node.getScore();
            //setBestNode(node,d);
            return d;
        }
        

        if(node.isPlayerTurn()){
            
            for(int i=0; i<w; i++){
                alpha = Math.max(alpha,__ALPHA_BETA((GameNode)node.getChild(i),depth-1,alpha,beta,N));
                if(alpha>=beta){
                    return beta;    //  カット
                }
                
                //remainNode = children.get(i);
                
                if(depth==N){
                    onProgress(i,w);  //  ノード番号iが終了した
                    
                    //  スコアがよければ入れ替える
                    if( _bestScore < alpha){
                        _bestScore = alpha;
                        _bestNode = (GameNode) node.getChild(i);
                    }
                    
                    
                }
            }
            
            //  ノードを破棄する
            //children.clear();
            
            
            //  一番いいのだけを残す
            //if(remainNode!=null)
                //children.add(remainNode);
                
            
            return alpha;
        }
        else{
            for(int i=0; i<w; i++){
                beta = Math.min(beta, __ALPHA_BETA((GameNode)node.getChild(i),depth-1,alpha,beta,N));
                if(alpha>=beta){
                    return alpha;   //カット
                }
            }
            
            //  ノードを破棄する
            //children.clear();
            //  一番いいのだけを残す
            //if(remainNode!=null)
                //children.add(remainNode);
                
            
            return beta;
        }
        
        
        
        
    }
    
    
    
    public GameNode getBestNode(){
        return _bestNode;
    }
    

    
 
    
    
}
