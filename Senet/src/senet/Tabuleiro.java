/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package senet;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author SnowBlade
 */
public class Tabuleiro implements Serializable{          
    public char[][] tabuleiro = {{'Q','1','W','2','E','3','R','4','T','5'},
                                 {' ',' ',' ',' ',' ','O',' ',' ',' ',' '},
                                 {' ',' ',' ',' ',' ','>','K','>','>',' '}};
    
   
    
    /**
     *
     * @return
     */
    public char[][] getTabuleiro(){
        return tabuleiro;
    }
    
    public int getRows(){return 3;}
    
    public int getColumns(){return 10;}
    
    //Funcoes de impressao e alteracao do tabuleiro
    
    public void printTabuleiro(){
        /*
         *  ***********************
         *  * Q 1 W 2 E 3 R 4 T 5 *
         *  * _ _ _ _ _ O _ _ _ _ *
         *  * _ _ _ _ _ > K > > _ *
         *  ***********************
         */
        checkifempty();
        System.out.println();
        System.out.println("   ***********************");
        for(int i=0; i<3;i++){
            System.out.print("   *");
            for(int j=0; j<10;j++){
                if(tabuleiro[i][j]==' '){
                    System.out.print(" _");
                }
                else{
                System.out.print(' ');
                System.out.print(tabuleiro[i][j]);
                }
            }
            System.out.println(" *");
        }
        System.out.println("   ***********************");
    }
    
    public void checkifempty(){
        //verificar se as casas com simbolos estão vazias
        //chamar esta funcao no final de todas as jogadas
        
        //verificar casa O
        if(' '==tabuleiro[1][5]){
            tabuleiro[1][5]='O';
        }
        
        //verificar K
        if(' '==tabuleiro[2][6]){
            tabuleiro[2][6]='K';
        }
        
        //verificar >
        if(' '==tabuleiro[2][5]){ 
            tabuleiro[2][5]='>';
        }
        if(' '==tabuleiro[2][7] ){ 
            tabuleiro[2][7]='>';
        }
        if (' '==tabuleiro[2][8]){
            tabuleiro[2][8]='>';
        }  
  }
    
    
    
    public void move(int yi,int xi,int yf,int xf){
        char destino = tabuleiro[yf][xf];
        char origem = tabuleiro[yi][xi];
        if(destino=='O' ||destino=='>' ||destino=='K'){
            tabuleiro[yi][xi]=' ';
            tabuleiro[yf][xf]=origem;
        }
        else if(yf==2 && xf==9){
            tabuleiro[yi][xi]=' ';
            tabuleiro[yf][xf]=' ';
        }
        else{
            tabuleiro[yi][xi]=destino;
            tabuleiro[yf][xf]=origem;
        }
        checkifempty();
        System.out.println();
        printTabuleiro();
        System.out.println();
    }
    
    //funcoes de verificacao
    //funcao que verifica devolve a cor da peca true para white e false para black
    public boolean getColor(int y, int x){
        if(tabuleiro[y][x]=='Q'||tabuleiro[y][x]=='W'||tabuleiro[y][x]=='E'||tabuleiro[y][x]=='R'||tabuleiro[y][x]=='T'){
            return false;
        }
        else{return true;}
    }
    
    //devolve conteudo da posicao
    public char getConteudo(int y, int x){return tabuleiro[y][x];}
    
    //Verifica se a peca caiu na casa armadilhada
    public Peca inatrap(Peca peca){
        //if posicao=k mudar para o se estiver vazio, caso contrario lançar o dado para nova posição
        if(peca.getPos()==27){ 
            if('O'==tabuleiro[1][5]){
                peca.setPos(15);
                tabuleiro[1][5]=peca.getID();
                tabuleiro[2][6]='K';
                return peca;
            }
            else {
                //lançar o dado
                System.out.println("Casa 27 esta ocupada, lancar o dado para nova casa");
                Random ran = new Random();
		int val=ran.nextInt(5);
                int acum=20;
                char casa = tabuleiro[0][val];
                while (val==4||casa!=' '&&acum>0){
                val=ran.nextInt(5);
                casa = tabuleiro[0][val];
                }
                if(acum==0){for(int i=0;i<10;i++){if(tabuleiro[0][i]==' '){val=i;i=10;}}}
                val++;
                System.out.println();
                System.out.println("   * * *");
                System.out.println("   * "+val+" *");
                System.out.println("   * * *");
                System.out.println();
                System.out.println("Peca "+peca.getID()+" vai para casa "+val);
                //*******
                peca.setPos(val);
                tabuleiro[0][val-1]=peca.getID();
                tabuleiro[2][6]='K';
                return peca;
            }
        }
        return peca;
    }
    
    
}
