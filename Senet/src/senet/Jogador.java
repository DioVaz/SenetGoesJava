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
public class Jogador implements Serializable{
    Peca nova=new Peca('X',true,0);
    private String nome;
    private boolean cor; //cor das pecas true para branco e false para preto
    private Peca[] pecas={nova,nova,nova,nova,nova} ;
    private int restantes;
    private int jogadasValidas=0;
    public char personagem;
    
    /**
     *
     */
    public Jogador(){}
    
        /**
     *
     * @param nome
     * @param cor
     */
    public Jogador(String nome, boolean cor){
        this.nome=nome;
        this.cor=cor;
        this.restantes=5;
        if (cor){
            personagem=1;
            char[] simbolos= {'1','2','3','4','5'};
            for(int i=0;i<5;i++){
                Peca novo;
                novo = new Peca(simbolos[i],cor,i*2+2);
                pecas[i]=novo;
                
            }
        }
        else{
            personagem=2;
            char[] simbolos= {'Q','W','E','R','T'};
            for(int i=0;i<5;i++){
                
                Peca novo;
                novo = new Peca(simbolos[i],cor,i*2+1);
                pecas[i]=novo;
                
            }
        }
    }
    
    /**
     *
     * @param cor
     */
    public void updatePlayers(boolean cor){
       if (cor==true){
            char[] simbolos= {'1','2','3','4','5'};
            for(int i=0;i<5;i++){
                Peca novo=new Peca(simbolos[i],cor,i*2+2);
                pecas[i]=novo;
                this.cor=cor;
                personagem=1;
            }
        }
        else{
            for(int i=0;i<5;i++){
                char[] simbolos= {'Q','W','E','R','T'};
                Peca novo=new Peca(simbolos[i],cor,i*2+1);
                pecas[i]=novo;
                this.cor=cor;
                personagem=2;
            }
        } 
    }
    
    /**
     *
     * @return 
     */
    public boolean taGanho(){
        if (restantes==0){return true;}
        return false;
    }
    
   
    

    
    /**
     *
     * @return Nr de jogadas Validas
     */
    public int getValidas(){return jogadasValidas;}
    
    /**
     *
     * @param validas
     */
    public void setValidas(int validas){jogadasValidas=validas;}
    
    /**
     *
     * @return restantes
     */
    public int getRestantes(){return restantes;}

    /**
     *
     * @return nome
     */
    public String getNome(){return nome;}
    
    /**
     *
     * @return cor
     */
    public boolean getCor(){return cor;}
    
    /**
     *
     * @param i
     * @return
     */
    public Peca getPeca(int i) {return pecas[i];}
    
    /**
     *
     * @return
     */
    public Peca[] getPecas() {return pecas;}
    
    /**
     *
     * @param id
     * @return
     */
    public int getposPeca(char id){
        for(int i=0;i<restantes;i++){
            if(pecas[i].getID()==id){return i;}
        }
        return 0;
    }
    
    /**
     *
     * @param pos
     * @return
     */
    public boolean pecaemPos(int pos){
        for(int i=0;i<restantes;i++){
            if(pecas[i].getPos()==pos){return true;}
        }
        return false;
    }
   
    /**
     *
     * @param peca
     */
    public void savePeca(Peca peca){
        for(int i=0;i<5;i++){
            if(pecas[i].getID()==peca.getID()){pecas[i]=peca;}
        }
    }
    
    public void tiraPeca(){this.restantes=restantes-1;}

    /**
     * Verifica se existem novos blocos no tabuleiro, chamar no fim de todas as jogadas
     */
    public void checkBlocos(){
        int pos;
        int aux=0;
        //ver pecas uma a uma para verificar se esta em bloco
        for(int i=0;i<5;i++){
            aux=0;
          int referencia= pecas[i].getPos();
          for(int j=0;j<5;j++){
            if(j==i){}
            else{
                pos=getPecas()[j].getPos();
                if(pos==referencia-1){
                    aux++;
                    if(pecaemPos(referencia-2)){aux++;}
                }
                else if(pos==referencia+1){
                    aux++;
                    if(pecaemPos(referencia+2)){aux++;}
                }
            }
        }
       
       if(aux==0){
           pecas[i].notpar();
       } 
       else if(aux==1){
           if(pecas[i].getCor()){System.out.println("White double block");}
           else{System.out.println("Black double block");}
           pecas[i].par();
       }
       else{
           pecas[i].triple();
           if(pecas[i].getCor()){System.out.println("White triple block");}
           else{System.out.println("Black triple block");}
       }
       
       if(pecas[i].getPos()==26 || pecas[i].getPos()==28 ||pecas[i].getPos()==29){
           pecas[i].youresafe(true);
       }
    }
  }
}
