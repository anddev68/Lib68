/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib68.minmax;

/**
 * Evaluatableでないクラスに対して評価をかけるクラス
 * template T 評価対象のクラス
 */
public interface Evaluator<T> {
    public double evaluate(T object);
}
