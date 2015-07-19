/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.ga;

import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class GA {
    
    Gene[] gene;
    Gene[] gene_tmp;
    
    public GA(Gene[] gene){
        this.gene = gene;
        gene_tmp = new Gene[gene.length];
    }
    


    /**
     * 選択
     * エリート保存＋N者トーナメント方式を利用
     * @param n
     * @return 選択後の新しい遺伝子
     */ 
    public Gene[] select(int n){
        gene_tmp[0] = getElite(); //  一番いいのをコピー
        for(int i=1;i<gene.length;i++){
            gene_tmp[i]=doTournamentSelect(n);  
        }
        Gene[] d = gene;    //  dにgeneを退避
        gene=gene_tmp;  //  geneに作っておいたtmpを使用
        gene_tmp=d;     //  退避したgeneをtmpにする
        return gene;    //  geneが最新
    }
    
    
    /**
     * 突然変異
     * @param n
     * @param m
     * 
     * n個の遺伝子を選び
     * m個の染色体をランダムに変更
     */
    public void mutate(int n,int m){
        for(int i=0; i<n; i++){
            int r = (int)(Math.random()*(gene.length-1))+1;
            gene[r].mutate(m);
        }
    }
    
    
    /**
     * Geneの一番いいものを返す
     * 線形探索
     * @return いいもの
     */
    public Gene getElite(){
        Gene elite=gene[0];
        double max=elite.getFitness();
        for(int i=1;i<gene.length;i++){
            if(gene[i].getFitness()>max){
                elite=gene[i];
                max=elite.getFitness();
            }
        }
        return elite;
    }
    
    
    /**
     * N者トーナメント方式による選択
     * ランダムにN個の遺伝子を選んで、その中で最も適応度の
     * 大きなものを複製して返します。
     */
    private Gene doTournamentSelect(int n){
        try{
            Gene max_gene=null;
            double max=Double.NEGATIVE_INFINITY;
            for(int i=0;i<n;i++){
                int r=(int)(Math.random()*gene.length);
                if(gene[r].getFitness()>max){
                    max_gene=gene[r];
                    max=max_gene.getFitness();
                }
            }
            return (Gene)max_gene.clone();
        }catch(Exception e){
            System.err.println("Error: "+e);
        }
        return null;
    }
    
    
    /**
     * 一点交叉する
     */
    public void onepointCrossover(){
        Arrays.sort(gene);  //fitnessでソート
        int n = gene.length;
        for(int i=1; i<n/2; i++){
            gene[i].onepointCrossover(gene[n-i]);
        }
        
    }
    
    /**
     * 評価プロセス
     * 順次Geneの中のevaluate()を呼ぶ
     */
    public void evaluate(){
        for(int i=0; i<this.gene.length; i++){
            double d = gene[i].evaluate();
            gene[i].fitness = d;
        }
    }
    
    
    
    
}
