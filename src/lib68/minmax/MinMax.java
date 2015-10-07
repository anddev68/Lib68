/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.minmax;

import java.util.ArrayList;

/**
 * MinMaxアルゴリズムで最適解を求める
 * @param <SolutionType> 解の型
 * @author @anddev68
 */
public class MinMax<SolutionType>{
    
    private Evaluator evaluator;    //  外部の評価関数
    private boolean isDebugging;    //  printfを有効にするか
    private int maxDepth;   //  最大深さ
    private SolutionType selectedSolution;    //  最適解
    
    public MinMax(boolean isdebug){
        isDebugging = isdebug;
    }
    
    
    /**
     * 公開用publicメソッドrunAlgorism()
     * ノードが評価関数を持っている場合はこっちを呼ぶ
     * @param <T> Evaluatableを実装しているノード
     * @param depth
     * @param root 
     * @return Object 最適解
     * @throws java.lang.Exception
     */
    public <T extends Evaluatable&MinMaxNode> SolutionType runAlgorism(int depth,T root) throws Exception{
        //  ノード自身の評価関数を利用する
        this.evaluator = null;
        //  最大深さを保存
        this.maxDepth = depth;
        

        //  実行
        double score = __runAlgorism(depth,root);
        
        //  デバッグがONなら表示
        if(this.isDebugging){
            System.out.println();
            System.out.print("MinMax#runAlgorism(),");
            System.out.print(selectedSolution);
            System.out.println(" "+score);
            
        }
        
        return selectedSolution;   //  最大深さのものが次に打つべき解である
    }
    
    
    /**
     * 公開用publicメソッドrunAlgorism()
     * ノードが評価関数を持っていない場合は外部の評価関数を使う
     * @param depth
     * @param root
     * @param evaluator
     * @return Object 最適解
     * @throws java.lang.Exception
     */
    public SolutionType runAlgorism(int depth,MinMaxNode root,Evaluator evaluator) throws Exception{
        //  外部の評価関数を利用するため代入しておく
        this.evaluator = evaluator;
        //  最大深さを保存
        this.maxDepth = depth;
        
        double score = __runAlgorism(depth,root);
        
        //  デバッグがONなら表示
        if(this.isDebugging){
            System.out.println();
            System.out.print("MinMax#runAlgorism(),");
            System.out.print(selectedSolution);
            System.out.println(" "+score);
        }
        
        return this.selectedSolution;
    }
   
    
    /**
     * 内部用runAlgorism()
     * MinMaxを再帰的に行う実体
     * @param depth
     * @param root
     * @return
     * @throws Exception 
     */
    private double __runAlgorism(int depth,MinMaxNode root) throws Exception{
        
        //  一番最下層までもぐったら静的評価します
        if(depth==0){
            return evaluate(root);
        }
        
        //  展開フェイズ
        ArrayList<MinMaxNode> children = new ArrayList<>();   //  展開された子ノード
        ArrayList<SolutionType> solutions = new ArrayList();  //  打った手
        
        root.expand(children, solutions);
        
        int w = children.size();
        
        //  展開したノード数が0の場合は終局
        if(w==0){
            return evaluate(root);
        }
        
        if( root.isPlayerTurn() ){
            double max = Double.NEGATIVE_INFINITY;
            int progress = 0;
            for(int i=0; i<w; i++){
                MinMaxNode child = children.get(i);
                double score = __runAlgorism(depth-1,child);    //  再帰的に検索
                if(score>max){  //  いい手があったらそれを選択
                    max = score;
                    if(maxDepth==depth){
                        //  再帰の頭の場合は最適手であると考えられる
                        selectedSolution = solutions.get(i);
                    }
                    
                }
                //  進捗表示
                if(maxDepth==depth){
                    //  最大深度のときは進捗表示
                    //  最大数を10としたスケールで表示
                    printProgress(progress,w);
                    progress++;
                }
            }
            return max;
        }
        
        throw new Exception("MinMax#__runAlgorism(),敵のターンを探索するメソッドが未実装です");
        
    }

    
    /**
     * 評価関数
     * Evaluatableを実装しているクラスでであるかどうかによって処理が分かれます
     * @param root 評価対象のノード
     * @throws new Exception
     */
    private double evaluate(MinMaxNode root) throws Exception{
        if(evaluator!=null)
            return evaluator.evaluate(root);
        else if(root instanceof Evaluatable)
            return ((Evaluatable)root).evaluate();

        
        throw new Exception("MinMax#evaluate(),評価関数が指定されていません");    
    }
    
    
    
     /**
     * 進捗表示パターン1
     * 
     * 100% |====================>(20個)|
     */
    private void printProgress(int current,int max){
        System.out.printf("%3d%%|", current*100/max);
        for(int i=0;i<current*30/max; i++){  //  max30個で表示
            System.out.print("=");
        }
        System.out.print(">|");
        System.out.printf("%d/%d",current,max);
        System.out.print("\r");
        
        /*
        if(current==max){
            System.out.println();
            System.out.println("Complete!!");
        }
        */
    }
    
    
    
    
}
