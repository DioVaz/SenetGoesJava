/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package senet;

import java.io.Serializable;

/**
 *
 * @author SnowBlade
 */
public class Peca implements Serializable {
    private char id;
    private boolean cor; //true para branco e false para preto
    private int casa;   //posicao da peca no tabuleiro
    private boolean par; //true se a peca estiver num bloco de 2 pecas
    private boolean triple; //true se a peca estiver num bloco de 3 pecas
    private boolean safe; //true se a peca estiver num bloco ou numa casa protegida
    private boolean fora; //true se a peca estiver fora do tabuleiro
    
    public Peca(char id, boolean cor, int pos){
        this.id=id;
        this.cor=cor;
        this.casa=pos;
        this.par=false;
        this.triple=false;
        this.safe=false;
        this.fora=false;
    }

    
    
    /**
     *
     * @return
     */
    public char getID(){return id;}
    
    public void youresafe(boolean safe){
        this.safe=safe;
    }
    
    public void jaGanhou(){this.fora=true;}
    
    public boolean fora(){return fora;}
    
    public boolean issafe(){return safe;}
    
    public boolean inDoubleBlock(){
        return par;
    }
    
    public boolean inTripleBlock(){
        return triple;
    }
    
    public void par(){
        this.safe=true;
        this.par=true;
        this.triple=false;
    }
    
    public void notpar(){
        this.safe=false;
        this.par=false;
        this.triple=false;
    }
    
    /**
     *
     */
    public void triple(){
        this.safe=true;
        this.par=true;
        this.triple=true;
    }
    
    /**
     *
     */
    public void nottriple(){
        this.triple=false;
    }
    
    public int getPos(){return casa;}
    
    public void setPos(int i){this.casa=i;}
    
    public boolean getCor(){return cor;}
    
    
            
}
