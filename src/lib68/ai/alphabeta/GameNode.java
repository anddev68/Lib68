/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.ai.alphabeta;

import lib68.ai.Node;

/**
 * alphabeta用のゲームノード
 */
public abstract class GameNode implements Node{
    
    /**
     * プレイヤーのターンならtrueを返す
     */
    public abstract boolean isPlayerTurn();
    
    /**
     * getScoreは単純にスコアを返す関数に変わります
     */
    @Override
    public abstract double getScore();
    
    /**
     * 子ノードの点数を親ノードに反映させるため
     * （＝バックプロバケーション）の関数
     * 子ノードからのアクセスのみ可能
     */
    public abstract void setScore(double d);
    
    /**
     * 評価関数によって評価する
     * スコアを保存し、getScore()で取得できるようになります
     */
    public abstract double evaluate();
    
    /**
     * スコア順に並べる
     */
    public abstract void sort();
    
}
