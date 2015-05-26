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
    
    
    
}
