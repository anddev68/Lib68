/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 遺伝子配列
 * 遺伝子座の型、通常はbyteなのだが汎用性を持たせた
 * @param <T>
 */
public abstract class Gene<T extends Object> implements Comparable{
    
    /* 遺伝子配列 */
    public ArrayList<T> chromosome;
    
    /* 遺伝子の評価値 */
    protected double fitness;
    
    /**
     * 長さnで初期化
     * @param n
     */
    public Gene(int n){
        this.chromosome = new ArrayList(n);
        initChromosome(n);
    }
    
    /**
     * まったく同じ遺伝子を生成する
     * @param g
     */
    public Gene(Gene g){
        this.chromosome = new ArrayList(g.chromosome);
    }
    
    /**
     * 何もしないコンストラクタ
     */
    protected Gene(){
    }
    
    /**
     * 初期化関数
     */
    private void initChromosome(int n){
         for(int i=0; i<n; i++){
            this.chromosome.add(this.getRandomChromosome());
        }       
    }
    
    
    /**
     * ランダムに遺伝子を初期化する
     * オーバライドして遺伝子を変えること。
     * @return 0-255
     */
    protected abstract T getRandomChromosome();
    
    /**
     * 評価関数
     * オーバライドして定義すること
     */
    public abstract double evaluate();
    
    
    /**
     * 適応度を取得する
     */
    public double getFitness(){
        return this.fitness;
    }
    
   
    public abstract Gene copy();
    
    
    /**
     * 遺伝子を表示する
     */
    public void println(){
        for(int i=0; i<this.chromosome.size(); i++){
            System.out.print(this.chromosome.get(i));
            System.out.print(" ");
        }
        System.out.println();
    }
    
    /**
     * 突然変異する
     * @param n 染色体n本をgetRandomChromosome()で初期化する
     */
    public void mutate(int n){
        for(int i=0; i<n; i++){
            this.chromosome.set((int)(i*Math.random()), this.getRandomChromosome());
        }
    }
  
    
    
    /**
     * 一点交叉
     * @param g
     * ランダムに1点で交叉する
     * 長い方をはみ出した部分は変化しない
     */
    public void onepointCrossover(Gene g){
        int len = this.chromosome.size();
        if(len>g.chromosome.size()) len = g.chromosome.size();
        int index = (int)(Math.random()*len);   //交叉点
        for(int i=index; i<len; i++){
             T t = this.chromosome.get(i);
             this.chromosome.set(i, (T)g.chromosome.get(i));
             g.chromosome.set(i, t);   
        }
    }

    
    @Override
    public int compareTo(Object o) {
        Gene g = (Gene) o;
        return Double.compare(this.fitness, g.fitness);
    }
    
    
}
