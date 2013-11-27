/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package senet;

import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.util.Random;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.io.Serializable;
/**
 *
 * @author SnowBlade
 */
public class Jogo implements Serializable{
    
    public Tabuleiro tab;
    private Jogador jogador1;
    private Jogador jogador2;
    private Dado dado;
    private boolean gameOn;
    private static char[] pecas={'1','2','3','4','5','Q','W','E','R','T'};
    private boolean jogador1primeiro; //true se o jogador1 estiver a jogar de pretas
    private boolean vezdojogador1;
    private int ultimoLance; //guarda o ultimo lance para verificar se um jogador deve jogar novamente
    
    public int modoBackup=0;
    public String mensagemBackup="*****";
    
    /*
     * Funcoes
     */
    public Jogo( Tabuleiro tab, Jogador jogador1, Jogador jogador2, Dado dado){
        this.tab=tab;
        this.jogador1=jogador1;
        this.jogador2=jogador2;
        this.dado=dado;
        ultimoLance=0;
        this.jogador1primeiro=true;
        this.vezdojogador1=true;
    }
    
    
    public void setultimoLance(int ultimo){
        ultimoLance=ultimo;
    }
    
    public boolean isOn(){
        return gameOn;
    }
    
   
    
    public boolean getPrimeiro(){
        return jogador1primeiro;
    }
    

    
    public boolean jaGanhou(Jogador jogador){
        if(jogador.getRestantes()==0){
            return true;
        }
        else {return false;}
    }
    
    public void updatePlayers(boolean primeiro){
        jogador1.updatePlayers(primeiro);
        jogador2.updatePlayers(!primeiro);
        this.gameOn=true;
        this.jogador1primeiro=primeiro;
        System.out.println("Jogador " + Brancas().getNome()+" primeiro");
    }
    

  
    public int getultimoLance(){return ultimoLance;}
    
    
    
    public boolean primeiro_lancamento(){
        int valor;
        System.out.println();
        System.out.println("O primeiro jogador a obter 1 no dado joga de pretas");
        System.out.println();
        while(true){
            //jogador1
          System.out.println("Vez do jogador 1");
          System.out.println();
          valor=dado.gerVal();
          if(valor==1){
          
          return true;}
          
          //jogador2
          System.out.println("Vez do jogador 2");
          System.out.println();
          valor=dado.gerVal();
          if(valor==1)
          {return false;}
        }
    }
    
    public Jogador Brancas(){
        if(jogador1primeiro==true) {return jogador1;}
        else {return jogador2;}
    }
    
    public Jogador Pretas(){
        if(jogador1primeiro==false) {return jogador1;}
        else {return jogador2;}
    }
    
    public Peca chartoPeca(char peca){
        for(int i=0;i<5;i++){
            if(jogador1.getPeca(i).getID()==peca){return jogador1.getPeca(i);}
        }
        for(int i=0;i<5;i++){
            if(jogador2.getPeca(i).getID()==peca){return jogador2.getPeca(i);}
        }
        return null;
    }
    
    /**
     *
     * @param pos
     * @return
     */
    public boolean posLivre (int pos){
        char casa=tab.tabuleiro[getY(pos)][getX(pos)];
        if (casa==' ' || casa=='O' ||casa=='K' ||casa=='>'){return true;}
        else{
        return false;
        }
    }
    
    /**
     *
     * @param pos
     * @return
     */
    public Peca findPeca (int pos){
        for(int i=0;i<5;i++){
            Peca pecaS=jogador1.getPeca(i);
            if(pecaS.getPos()==pos){return pecaS;}
        }
        for(int i=0;i<5;i++){
            Peca pecaS=jogador2.getPeca(i);
            if(pecaS.getPos()==pos){return pecaS;}
        }
        return null;
    }
       
    /**
     *
     * @param casa
     * @return
     */
    public int getX(int casa){
        casa--;
        if(casa>28){
            return 9;
        }
        if(casa<20 && casa>9){
            casa=29-casa;
        }
        int unidades=casa;
        while(unidades>9){
            unidades=unidades-10;
        }
        return unidades;
    }
    
    /**
     *
     * @param casa
     * @return
     */
    public int getY(int casa){
       casa--;
        if(casa>29){
            return 2;
        }
        if(casa<20 && casa>9){
            casa=29-casa;
        }
        int dezenas=casa/10;
        return dezenas;
    }
    
    
    
    
    //Funcao que verifica se um movimento e valido
    /**
     *
     * @param peca
     * @param casas
     * @return
     */
    public boolean checkifValidoMove(char peca,int casas){
        Peca novaPeca= chartoPeca(peca);
        if(novaPeca==null||novaPeca.getPos()+casas>30){
            System.out.println("Empty house or posDestino>30");
            return false;
        }
        else if(getInputaux(peca, novaPeca.getCor())==false ||
                bloconoCaminho(novaPeca,casas)|| novaPeca.fora()){
            System.out.println("erro");
            return false;
        }
        else if(peca=='X'){return false;}
        int posOrigem = novaPeca.getPos();
        int posDestino = novaPeca.getPos()+casas;
        
       if(posDestino<30){
            Peca pecaDestino = chartoPeca(tab.getConteudo(getY(posDestino), getX(posDestino)));
            if(pecaDestino==null){return true;}
            //se a casa destino estiver ocupada por uma peca 
            //da mesma cor ou uma peca num bloco ou ja esta fora do tabuleiro
            boolean corDestino = tab.getColor(getY(posDestino),getX(posDestino));
            if(corDestino == novaPeca.getCor() || pecaDestino.issafe()){
                return false;
            }
        }
       
        
        return true;
    }
    
    /**
     *
     * @param peca
     * @param casas
     * @return
     */
    public boolean bloconoCaminho(Peca peca, int casas){
        int posInicial= peca.getPos();
        for(int i=1;i<casas;i++){
            if(!posLivre(posInicial+i)){
                Peca pecaDestino = chartoPeca(tab.getConteudo(getY(posInicial+i),getX(posInicial+i)));
                if(pecaDestino.inTripleBlock() && peca.getCor()!= pecaDestino.getCor()){
                    System.out.println("Block in the way");
                    return true;}
            }  
        }
        return false;
    }
    
    //Funcao responsavel por realizar o movimento da peca presumindo que o movimento e valido
    //Devolve o resto do lance do Dado caso posicao>30
    /**
     *
     * @param peca
     * @param casas
     * @return
     */
    public int move(char peca, int casas){
        int resto;
        Peca nPeca;
        nPeca = chartoPeca(peca);
        int origem = nPeca.getPos();
        //se estava em casa protegida 
        if(origem==26 || origem==28 ||origem==29){
            nPeca.youresafe(false);
        }
        int destino = origem+casas;
        boolean livre = posLivre(destino);
        tab.move(getY(origem),getX(origem),getY(destino),getX(destino));
        
        if(livre==false){
            Peca pdestino=findPeca(destino);
            pdestino.setPos(origem);
            nPeca.setPos(destino);
            if(pdestino.getCor()){
                Brancas().savePeca(pdestino);
                Pretas().savePeca(nPeca);
                Brancas().checkBlocos();
                Pretas().checkBlocos();
            }
            else{
                Brancas().savePeca(nPeca);
                Pretas().savePeca(pdestino);
                Brancas().checkBlocos();
                Pretas().checkBlocos();
            }
        }
        else if(destino>=30){
            resto=destino-30;
            System.out.println();
            System.out.println("Peca "+ peca+" e sobram " + resto+ " casas para mover outra peca.");
            System.out.println();
            nPeca.setPos(destino);
            nPeca.jaGanhou();
            if(nPeca.getCor()){
                Brancas().savePeca(nPeca);
                Brancas().tiraPeca();
                Brancas().checkBlocos();
                
            }
            else{
                Pretas().savePeca(nPeca);
                Pretas().tiraPeca();
                Pretas().checkBlocos();
                
            }
            
        }
        else{
            nPeca.setPos(destino);
            nPeca=tab.inatrap(nPeca);
            if(nPeca.getCor()){
                Brancas().savePeca(nPeca);
                Brancas().checkBlocos();
            }
            else{
                Pretas().savePeca(nPeca);
                Pretas().checkBlocos();
            }
        }
       
        return 0;
    }
    
    /**
     *
     * @param cor
     * @return
     */
    public boolean nextJogador(boolean cor){
        if(ultimoLance==3 ||ultimoLance==2){
            return !cor;
        }
        else {
            return cor;
        }
    }
    
    
    /**
     *
     * @return proximo jogador
     */
    public Jogador proximo(){
       if(vezdojogador1){return jogador2;}
       return jogador1;
    }
    
    /**
     *
     * @return
     */
    public Jogador actual(){
       if(!vezdojogador1){return jogador2;}
       return jogador1;
    }
    
    /**
     *
     * @param cor
     */
    public void vezdoJogado1( boolean cor){
        if(cor){System.out.println("Change to White");}
        else{System.out.println("Change to Black");}
        vezdojogador1=cor;
    }
    
    /**
     *
     * @param cor
     * @param casas
     * @return
     */
    public boolean checkValidade(boolean cor,int casas){
        if(cor){
            for(int i=0;i<5;i++){
                if(checkifValidoMove(jogador1.getPeca(i).getID(),casas)){
                    return true;
                }
            }
        }
        else{
            for(int i=0;i<5;i++){
                if(checkifValidoMove(jogador2.getPeca(i).getID(),casas)){
                    return true;
                }
            }
        }
        System.out.println("Wrong color");
        return false;
    }
    
    /**
     *
     * @param casas
     * @param jogador
     * @return
     */
    public char[] jogadasValidas(int casas, Jogador jogador){
        char[] validas = {'X','X','X','X','X'};
        int acum=0;
        for(int i=0;i<5;i++){
            if(checkifValidoMove(jogador.getPecas()[i].getID(),casas)){
                validas[acum]=jogador.getPecas()[i].getID();
                acum++;
            }
        }
        
        return validas;
    }
    
    /**
     *
     * @param casas
     * @return
     */
    public char computerMove(int casas){
        char[] validas=jogadasValidas(casas, jogador2);
        int maior=0;
        int imaior=0;
        int posMaior=999;
        Peca peca;  
        for(int i=0;i<jogador2.getRestantes();i++){
            if(validas[i]=='X'){
                i=jogador2.getRestantes();
            }
            else{
                peca=chartoPeca(validas[i]);
                int contador=0;
                //desfazer blocos
                if(peca.inTripleBlock()){
                    contador-=4;
                }
                else if(peca.inDoubleBlock()){
                    contador-=3;
                }
                //Fazer novos blocos
                contador+=checkCriablocos(peca,casas);
                
                //capturar
                if(podeCapturar(casas,peca)){
                    //verifica se gera blocos para o adeversário
                    contador+=checkCriamausblocos(peca,casas);
                }
                //casa destino é armadilha
                else if(peca.getPos()+casas==27){
                    contador-=30;
                }
                //a unica peca a um lance de distancia fica a 5 casas de distância
                //+1
                if(checkAdversario(peca,casas)){
                    contador+=2;
                }
                if(peca.getPos()+casas==26||peca.getPos()+casas==28||peca.getPos()+casas==29){
                    //casa protegida 
                    contador+=6;
                }
                if(peca.getPos()==26||peca.getPos()==28||peca.getPos()==29){
                    //ocupar as casas protegidas é uma boa estratégia para avancar pecas atrasadas 
                    //e empatar o jogador adeversário
                    contador-=3;
                }
                if(contador>maior){
                    maior=contador;
                    imaior=i;
                    posMaior=peca.getPos();
                }
                else if(contador==maior&&posMaior>peca.getPos()){
                    maior=contador;
                    imaior=i;
                    posMaior=peca.getPos();
                }
               
                
            }
        }
        return validas[imaior];
    }
    
 /**
     *
     * @param peca
     * @param casas
     * @return
     */ 
 private boolean podeCapturar(int casas, Peca peca) {
        int destino=peca.getPos()+casas;
        if(tab.getConteudo(getY(destino), getX(destino))==' '){
            return false;
        }
        else if(tab.getColor(getY(destino), getX(destino))!=peca.getCor()){
            return true;
        }
        return false;
 }
 /**
     *
     * @param peca
     * @param casas
     * @return
     */
 private int checkCriablocos(Peca peca, int casas){
     int w= jogador2.getposPeca(peca.getID());
     int destino= peca.getPos()+casas;
     int pos;
     int aux=0;
        //ver pecas uma a uma para verificar se esta em bloco
        for(int i=0;i<5;i++){
            if(w==i){}
            else{
                pos=jogador2.getPecas()[i].getPos();
                if(pos==destino-1||pos==destino+1){aux++;}
            }
        }
       if(aux==0){
           return 0;
       } 
       else if(aux==1){
         for(int i=0;i<5;i++){
            if(w==i){}
            else{
                pos=jogador2.getPecas()[i].getPos();
                if(pos==destino-2||pos==destino+2){aux++;}
            }
         }
         if(aux==1){return 5;}
         else{return 7;}
       }
       else{
           return 7;
       }
 }
    
    /**
     *
     * @return Computer
     */
    public Jogador getRobot(){return jogador2;}
    /**
     *
     * @return jogador1
     */
    public Jogador getHuman(){return jogador1;}
    
    private boolean checkAdversario(Peca peca, int casas) {
        int posDestino=peca.getPos()+casas;
        if(tab.getColor(getY(posDestino), getX(posDestino))!=peca.getCor()){
            return false;
        }
        else{
            if(posDestino<7){
                for(int i=1;i<posDestino;i++){
                if(i==5){}
                else if(tab.getColor(getY(posDestino-i), getX(posDestino-i))!=peca.getCor()){return false;}
                }
            }
            else{
            for(int i=1;i<7;i++){
                if(i==5){}
                else if(tab.getColor(getY(posDestino-i), getX(posDestino-i))!=peca.getCor()){return false;}
            }
            }
            return true;
        }
    }
    
    private int checkCriamausblocos(Peca peca, int casas) {
        int destino= peca.getPos()+casas;
        int origem=peca.getPos();
        int w= jogador1.getposPeca(tab.getConteudo(getY(destino), getX(destino)));
        int pos;
        int aux=0;
        //ver pecas uma a uma para verificar se esta em bloco
        for(int i=0;i<5;i++){
            if(w==i){}
            else{
                pos=jogador1.getPecas()[i].getPos();
                if(pos==origem-1||pos==origem+1){aux++;}
            }
        }
       if(aux==0){
           return 5;
       } 
       else if(aux==1){
         for(int i=0;i<5;i++){
            if(w==i){}
            else{
                pos=jogador1.getPecas()[i].getPos();
                if(pos==origem-2||pos==origem+2){aux++;}
            }
         }
         if(aux==1){return -4;}
         else{return -7;}
       }
       else{
           return -7;
       }
    }
    
    
     /*
     * 
     *****************************************************/
    
    //PVP na linha de comandos
    /**
     *
     * @param jogo
     */
    public void pvp(Jogo jogo){
        boolean cor;
        boolean jaGanhou=false;
               
       int resto;
        jogo.primeira_ronda();
        
        if(jogo.Brancas().taGanho()){
            jaGanhou=true;
        }
        cor=jogo.getPrimeiro();
        while(!jaGanhou){
            
            if(cor==true) {
                resto = jogo.nova_ronda(jogo.Brancas());
                jaGanhou=jogo.jaGanhou(jogo.Brancas());
                if(resto==404){jaGanhou=true;}
                //se resto>0, se nova jogada possivel e ainda não ganhou, jogar com o resto
                while(resto>0 && jaGanhou==false){
                    resto=jogo.ronda_extra(jogo.Brancas(), resto);
                    
                }
                cor=jogo.nextJogador(cor);
            }
            else{
                resto = jogo.nova_ronda(jogo.Pretas());
                jaGanhou=jogo.jaGanhou(jogo.Pretas());
                if(resto==404){jaGanhou=true;}
                while(resto>0 && jaGanhou==false){
                    resto=jogo.ronda_extra(jogo.Brancas(), resto);
                    
                }
                cor=jogo.nextJogador(cor);
            }
        }
    }

     /**
     *
     * @param jogador
     * @return
     */
    public int nova_ronda(Jogador jogador){
     //lancar o dado
        int casas = roolDice(jogador);
     //input do utilizador
        char peca = getInput(casas, jogador.getCor());
        //sair do jogo 'X'
        if (peca=='X'){return 404;}
        
        int resto = move(peca,casas);
        
        return resto;
    }
    
    /**
     *
     * @param jogador
     * @param resto
     * @return
     */
    public int ronda_extra(Jogador jogador, int resto){
        System.out.println("Escolha a peca ou C para cancelar: ");
         Scanner s = new Scanner(System.in);
         boolean cor= jogador.getCor();
	char peca = s.next().charAt(0);
        boolean um=checkifValidoMove(peca,resto);
        boolean dois=getInputaux(peca, cor);
        while(um==false||dois==false|| peca== 'X'){
            System.out.println();
            if(peca=='X'){
                return 404;
            }
            else if(peca=='C'){
                return 0;
            }
            else if(dois){
                System.out.println("Jogada invalida:");
                System.out.print("Escolher peca: ");
                peca = s.next().charAt(0);
                um=checkifValidoMove(peca,resto);
                dois=getInputaux(peca, cor);
            }
           else{
                System.out.println("Wrong input!");
                System.out.print("Escolher peca: ");
                peca = s.next().charAt(0);
                um=checkifValidoMove(peca,resto);
                dois=getInputaux(peca, cor);
            }
        }
        resto=move(peca,resto);
        return resto;
        
    }
    
    /**
     *
     */
    public void primeira_ronda(){
        System.out.println("*** NOVO JOGO ***");
        System.out.println();
        tab.printTabuleiro();
        System.out.println();
       //lançar o dado ate sair 1 para decidir quem é o 1º jogador a jogar 
        jogador1primeiro = primeiro_lancamento();
        updatePlayers(jogador1primeiro);
        System.out.println();
        System.out.println("Vez de " + Brancas().getNome()+ " jogar");
        System.out.println("Peca da casa 10 movida automaticamente para a 11");
        int resto = move('5', 1);
        int casas= roolDice(Pretas());
        System.out.println();
        char peca=getInput(casas, !jogador1primeiro);
        if(peca=='X'){
         
         
         return;
        }
        resto = move(peca,casas);
    }
   
    /**
     *
     * @param casas
     * @param cor
     * @return
     */
    public char getInput(int casas, boolean cor){
        System.out.print("Escolher peca: ");
        Scanner s = new Scanner(System.in);
	char peca = s.next().charAt(0);
        boolean um=checkifValidoMove(peca,casas);
        boolean dois=getInputaux(peca, cor);
        while(um==false||dois==false|| peca== 'X'){
            System.out.println();
            if(peca=='X'){
                return peca;
            }
            else if(dois){
                System.out.println("Jogada invalida:");
                System.out.print("Escolher peca: ");
                peca = s.next().charAt(0);
                um=checkifValidoMove(peca,casas);
                dois=getInputaux(peca, cor);
            }
           else{
                System.out.println("Wrong input!");
                System.out.print("Escolher peca: ");
                peca = s.next().charAt(0);
                um=checkifValidoMove(peca,casas);
                dois=getInputaux(peca, cor);
            }
        }
        return peca;
    }
    
    /**
     *
     * @param input
     * @param cor
     * @return
     */
    public boolean getInputaux(char input, boolean cor){
        if(cor){
            for(int i=0;i<5;i++){
                if(pecas[i]==input){return true;}
            }
        }
        else{
            for(int i=0;i<5;i++){
                if(pecas[i+5]==input){return true;}
            }
        }
        if(input=='X' || input=='C'){return true;}
        return false;
    }
    
     /**
     *
     * @param jogador
     * @return
     */
    public int roolDice(Jogador jogador){
        System.out.println();
        System.out.println("Vez de "+jogador.getNome()+" jogar:");
        System.out.println("Prima uma tecla para lancar o dado");
        ultimoLance=dado.gerVal();
        return ultimoLance;
    }

    
    
    /**
     *
     * @return
     */
    public char getHumanAvatar() {
        return jogador1.personagem;
    }
    
    
    
    /**
     *
     * @return
     */
    public char getRobotAvatar() {
        return jogador2.personagem;
    }

    

    

}
