/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.minmax;

import java.util.ArrayList;

/**
 * MinMax専用のノード
 * @param <SolutionType> 解の型
 * @author @anddev68
 */
public interface MinMaxNode<SolutionType>{
    
    public boolean isPlayerTurn();
    
    /**
     * 展開関数
     * ノードを展開するためのもの
     * childrenとsolutionはget(i)で対応している必要がある
     * 例)children.get(0)はsolution(0)を利用したものである
     * @param children @NotNull 展開後の子ノード
     * @param solutions @NotNull 展開後の解の候補
     */
    public void expand(ArrayList<MinMaxNode> children,ArrayList<SolutionType> solutions);
    
}
