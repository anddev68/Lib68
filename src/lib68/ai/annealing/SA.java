/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.annealing;

import java.util.Random;

/**
 *
 * @author Administrator
 */
public abstract class SA {
    
    /**
     * 実行する
     * @param s0 start state
     * @param kmax 繰り返し回数最大
     * @return final state
     */
    public static Status execute(Status s0,int kmax){
        final Random random = new Random();
        Status s = s0;
        for(int k=0; k<kmax; k++){
            double T = temperature((double)k/(double)kmax);
            Status snew = s.neighbour();    //  近傍取得
            if( probability(s.evaluate(),snew.evaluate(),T) > random.nextDouble() ){
                s = snew;   //  入れ替え
            }
        }
        return s;
    }
    
    protected static double temperature(double t){
        return t;
    }
    
    protected static double probability(double eOld,double eNew,double t){
        if( eNew >= eOld ){     //  新しいほうがエネルギーが大きくなってはいけない
            return 1;
        }
        return Math.exp(-(eNew-eOld)/t);
    }
    
    
    
    public interface Status{
        //  近傍作成
        Status neighbour();
        
        //  スコア取得
        double evaluate();
    }
    
}
