/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68;

import lib68.ai.ga.Gene;
import java.lang.Integer;
import lib68.ai.ga.GA;

/**
 *
 * @author Administrator
 */
public class Main {
    
    /**
     * テスト用遺伝子
     * @param <Integer> 
     */
    private class TestGene extends Gene<Integer>{

        public TestGene(Gene g) {
            super(g);
        }

        public TestGene(int n) {
            super(n);
        }

        @Override
        protected Integer getRandomChromosome() {
            return (int)(255*Math.random());
        }

        @Override
        public double evaluate() {
            //  評価関数はとりあえず合計値が大きいものとする
            double total = 0;
            for(int i=0; i<this.chromosome.size(); i++){
                total+= this.chromosome.get(i);
            }
            return total;
        }
        
    }
    
    
    
    private void method(){
       /* 
            初期個体の生成
            ここでは100個体、染色体40本で作成
        */
        Gene[] gene = new TestGene[100];
        for(int i=0; i<100; i++){
            gene[i] = new TestGene(4);
        }
        
        GA ga = new GA(gene);
        
        /*
            指定した世代まで進化する
        */
        for(int i=0; i<100; i++){
            
            ga.evaluate();  //  評価する
            ga.select(4);   //  エリート保存＋4者選択トーナメント
            ga.mutate(2,1); //  2個体、1本を突然変異させる
            ga.onepointCrossover(); //  交叉する
            
        }
        
        
        /*
            一番よかったものを表示
        */
        ga.getElite().println();
        
    }
    
    
    /**
     * GAのテスト用関数
     * @param args 
     */
    public static void main(String... args){
        Main main = new Main();
        main.method();
        
        
    }
    
}
