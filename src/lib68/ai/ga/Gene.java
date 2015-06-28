/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.ga;

import java.util.Arrays;

/**
 * @author Administrator
 */
public class Gene{
    
    /* 遺伝子配列 */
    protected int[] chromosome;
    
    /* 遺伝子の評価値 */
    protected double fitness;
    
    /**
     * 長さnで初期化
     * @param n
     */
    public Gene(int n){
        this.chromosome = new int[n];
        for(int i=0; i<n; i++){
            
        }
    }
    
    /**
     * まったく同じ遺伝子を生成する
     * @param g
     */
    public Gene(Gene g){
        this.chromosome = Arrays.copyOf(g.chromosome, g.chromosome.length);
    }
    
    
    /**
     * ランダムに遺伝子を初期化する
     * オーバライドして遺伝子を変えること。
     * @return 0-255
     */
    protected int getRandomChromosome(){
        return (int)(Math.random() * 255);
    }
    
    /**
     * 適応度を取得する
     */
    public double getFitness(){
        return this.fitness;
    }
    
    @Override
    public Gene clone(){
        return new Gene(this);
    }
    
}
