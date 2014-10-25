/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.interfaces;

/**
 *
 * @author claudio
 */
public interface iObservable<T> {
    public void addObserver( iObserver observer );
    public void removeObserver( iObserver observer );
    public void avisaMudancas();
}
