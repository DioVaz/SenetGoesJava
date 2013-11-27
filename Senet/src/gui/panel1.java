/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Label;
import senet.Jogador;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import senet.Dado;
import senet.Jogo;
import senet.Tabuleiro;

/**
 *
 * @author SnowBlade
 */
public final class panel1 extends javax.swing.JPanel {

    public Jogo jogo;
    public Jogo jogoBackup;
    public Dado dado;
    public int modo;
    public char casa;
    public boolean nextPlayer;
    public String mensagem="******* Bem-Vindo! *******";
    public String player1;
    public String player2;
    public int skin=0; //0 - para classica, 1 - para modo One piece; 
    
    //coordenadas do rato
    public static int cordX;
    public static int cordY;
    
    
    private BufferedImage empty;
    private BufferedImage brancas;
    private BufferedImage pretas;
    private BufferedImage O;
    private BufferedImage K;
    private BufferedImage A;
    private BufferedImage B;
    private BufferedImage C;
    private BufferedImage ultima;
    private BufferedImage fundo;
    private BufferedImage wall;
    private BufferedImage options;
    private BufferedImage um;
    private BufferedImage dois;
    private BufferedImage tres;
    private BufferedImage quatro;
    private BufferedImage seis;
    private BufferedImage tela1;
    private BufferedImage tela2;
    private BufferedImage avatar1;
    private BufferedImage avatar2;
    
    //tabuleiro
    int width;
    int height;
    int margin;
    int bottom;
    int top;
    //dado
    int dyi;
    int dxi;
    //botao de opcoes
    int oxi;
    int oyi;
    //banner jogador
    int j1x;
    int j1y;
    int j2x;
    int j2y;
    private int ctela;
    private int xTela1;
    private int xTela2;
           
    
    /**
     * Creates new form Board
     */
    public panel1() {
        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocus();
        this.dado=new Dado();
        modo=3;
        nextPlayer=true;
        
        //initialize();
        
        initComponents();
        readClassicBoard();
        
 
        this.jogo=novoJogovsPC();
        jogoBackup=jogo;
        this.repaint();
        
        
       
        
        
        
        
    }
    
    public Jogo novoJogo(){
        Jogador jogador1;
        jogador1 = new Jogador("Jogador1",true);
        Jogador jogador2;
        jogador2 = new Jogador("Jogador2", false);
        Tabuleiro tab=new Tabuleiro();
        boolean jaGanhou=false;
        Jogo novo=new Jogo(tab,jogador1,jogador2,dado);
        return novo;
    }
    
    public String getJogador1(){
        return jogo.getHuman().getNome();
    }
    
    public String getJogador2(){
        return jogo.getRobot().getNome();
    }
    
     /**
     *
     * @return
     */
    public Jogo novoJogovsPC(){
        Jogador jogador1;
        jogador1 = new Jogador("Jogador1",true);
        Jogador jogador2;
        jogador2 = new Jogador("Computador", false);
        Tabuleiro tab=new Tabuleiro();
        boolean jaGanhou=false;
        Jogo novo=new Jogo(tab,jogador1,jogador2,dado);
        return novo;
    }
   
    private void readClassicBoard(){
       try{ 
        empty=ImageIO.read(new File("images/empty.png"));
        brancas=ImageIO.read(new File("images/brancas.png"));
        pretas=ImageIO.read(new File("images/pretas.png"));
        O=ImageIO.read(new File("images/16.png"));
        K=ImageIO.read(new File("images/27.png"));
        A=ImageIO.read(new File("images/26.png"));
        B=ImageIO.read(new File("images/28.png"));
        C=ImageIO.read(new File("images/29.png"));
        ultima=ImageIO.read(new File("images/30.png"));
        fundo=ImageIO.read(new File("images/fundo.png"));
        wall=ImageIO.read(new File("images/wall.png"));
        options=ImageIO.read(new File("images/options.png"));
        um=ImageIO.read(new File("images/um.png"));
        dois=ImageIO.read(new File("images/dois.png"));
        tres=ImageIO.read(new File("images/tres.png"));
        quatro=ImageIO.read(new File("images/quatro.png"));
        seis=ImageIO.read(new File("images/seis.png"));
        tela1=ImageIO.read(new File("images/tela1.png"));
        tela2=ImageIO.read(new File("images/tela2.png"));
       }
       catch (IOException e){
           
       }
    }
    
    
    @Override
    protected void paintComponent(Graphics g){
        if (skin==0){
            boardClassico(g);
        }
        //***não esquecer de tirar isto
        else if(modo==1){
            
        }
        else{
           // boardOnePiece();
        }
       
       
        
    }
    
    
    /**
     *
     */
    public void vsComputador(){
        //restantes rondas
        if(jogo.isOn()){
            //Computador a jogar
            if(!nextPlayer&& casa=='D'){
                 jogo.setultimoLance(dado.gerVal());
                 if(jogo.getultimoLance()==1||jogo.getultimoLance()==4||jogo.getultimoLance()==6){modo=7;}
                 if(jogo.checkValidade(nextPlayer,jogo.getultimoLance())){
                    int resto=jogo.move(jogo.computerMove( jogo.getultimoLance()), jogo.getultimoLance()); 
                      if(modo==7){
                         mensagem= "Vez do Computador, clique no dado";
                         modo=2;
                      }
                      else{
                        mensagem= "É a sua vez, clique no dado";
                        nextPlayer=!nextPlayer;
                        jogo.vezdoJogado1(nextPlayer);
                        modo=9;
                        } 
                    
             }
                 else{
                     mensagem= "Não há movimentos validos. "+jogo.proximo().getNome() +" é a sua vez de jogar";
                     nextPlayer=!nextPlayer;
                     jogo.vezdoJogado1(nextPlayer);
                     modo=9;
                 }
            }
            
            //vez do jogador
            //lancar novo dado
            else if (modo==9 && casa=='D'){
                modo=2;
                jogo.setultimoLance(dado.gerVal());
                if(jogo.checkValidade(nextPlayer,jogo.getultimoLance())){
                    mensagem= jogo.actual().getNome()+" escolha uma peca";
                    if(jogo.getultimoLance()==1||jogo.getultimoLance()==4||jogo.getultimoLance()==6){modo=7;}
                  }
                else{
                     mensagem= "Não há movimentos validos. Vez do computador jogar.";
                      nextPlayer=!nextPlayer;
                      jogo.vezdoJogado1(nextPlayer);
                }
            }
            //mover peca
            else if (jogo.checkifValidoMove(casa, jogo.getultimoLance())&&jogo.getInputaux(casa, jogo.actual().getCor())&&modo==2||
                    jogo.checkifValidoMove(casa, jogo.getultimoLance())&&jogo.getInputaux(casa, jogo.actual().getCor())&&modo==7) {
                int resto=jogo.move(casa, jogo.getultimoLance());
                if( resto==0){
                    if(modo==7){
                         mensagem= jogo.actual().getNome()+" jogue novamente.";
                         modo=9;
                    }
                    else{
                         mensagem= "Vez do Computador, clique no dado";
                         nextPlayer=!nextPlayer;
                         jogo.vezdoJogado1(nextPlayer);
                    }
                    
                }
             }
        }
        //Primeira  ronda
        else if (casa=='D'){
            
            if(jogo.getultimoLance()==1){
                 mensagem= "Jogada automática. Vez de "+jogo.proximo().getNome()+" jogar.";
                jogo.updatePlayers(nextPlayer);
                jogo.move('5', 1);
                nextPlayer=!nextPlayer;
                jogo.vezdoJogado1(nextPlayer);
                modo=9;
            }
            else if(jogo.getultimoLance()==0){
                mensagem= "O primeiro a obter '1' joga de Brancas "+jogo.Brancas().getNome()+" lance o dado";
                jogo.setultimoLance(7);
                nextPlayer=false;
                jogo.vezdoJogado1(false);
            }
            else{
                mensagem= jogo.actual().getNome()+" lance o dado";
                nextPlayer=!nextPlayer;
                jogo.vezdoJogado1(nextPlayer);
                jogo.setultimoLance(dado.gerVal());
                if(jogo.getultimoLance()==1){mensagem= jogo.actual().getNome()+" joga de brancas. Volte a clicar no dado.";}
            }
            
        }
        
        //Verifica se jogo acabou
        if(jogo.jaGanhou(jogo.actual())){
            modo=3;
            mensagem= jogo.actual().getNome()+" venceu esta partida";
        }
        else if(jogo.jaGanhou(jogo.proximo())){
            modo=3;
            mensagem= jogo.proximo().getNome()+" venceu esta partida";
        }
    }
    
    /**
     *
     */
    public void pvp(){
        //restantes rondas
        if(jogo.isOn()){
            //lancar novo dado
            if (jogo.getultimoLance()==0 && casa=='D'){
                jogo.setultimoLance(dado.gerVal());
                if(jogo.checkValidade(jogo.actual().getCor(),jogo.getultimoLance())){
                    mensagem= jogo.actual().getNome()+" escolha uma peca";
                    if(jogo.getultimoLance()==1||jogo.getultimoLance()==4||jogo.getultimoLance()==6){modo=6;}
                  }
                else{
                     mensagem= "Não há movimentos validos. "+jogo.proximo().getNome() +" é a sua vez de jogar";
                     jogo.setultimoLance(0);
                      nextPlayer=!nextPlayer;
                      jogo.vezdoJogado1(nextPlayer);
                      jogo.setultimoLance(dado.gerVal());
                }
            }
            //mover peca
            else if (jogo.checkifValidoMove(casa, jogo.getultimoLance())&&jogo.getInputaux(casa, jogo.actual().getCor())) {
                int resto=jogo.move(casa, jogo.getultimoLance());
                if( resto==0){
                    if(modo==6){
                         mensagem= jogo.actual().getNome()+" jogue novamente.";
                         modo=1;
                    }
                    else{
                         mensagem= jogo.proximo().getNome()+ " lance o dado";
                         nextPlayer=!nextPlayer;
                         jogo.vezdoJogado1(nextPlayer);
                    }
                    jogo.setultimoLance(0);
                }
                else{
                    if(jogo.checkValidade(jogo.actual().getCor(),resto)){
                        mensagem= "Sobraram "+resto+" casas, pode avançar outra peca";
                        if(modo==6){}
                        jogo.setultimoLance(resto);
                    }
                    else{
                        mensagem= jogo.proximo().getNome()+" lance o dado";
                        nextPlayer=!nextPlayer;
                        jogo.setultimoLance(0);
                        jogo.vezdoJogado1(nextPlayer);
                    }
                }
             }
        }
        //Primeira  ronda
        else if (casa=='D'){
            
            if(jogo.getultimoLance()==1){
                 mensagem= "Jogada automática. Vez das pretas.";
                jogo.updatePlayers(nextPlayer);
                jogo.move('5', 1);
                nextPlayer=!nextPlayer;
                jogo.vezdoJogado1(nextPlayer);
                jogo.setultimoLance(0);
            }
            else if(jogo.getultimoLance()==0){
                mensagem= "O primeiro a obter '1' joga de Brancas "+jogo.Brancas().getNome()+" lance o dado";
                jogo.setultimoLance(7);
                nextPlayer=false;
                jogo.vezdoJogado1(false);
            }
            else{
                mensagem= jogo.actual().getNome()+" lance o dado";
                nextPlayer=!nextPlayer;
                jogo.vezdoJogado1(nextPlayer);
                jogo.setultimoLance(dado.gerVal());
                if(jogo.getultimoLance()==1){mensagem= jogo.actual().getNome()+" joga de brancas. Volte a clicar no dado.";}
            }
            
        }
        
        //Verifica se jogo acabou
        if(jogo.jaGanhou(jogo.actual())){
            modo=0;
            mensagem= jogo.actual().getNome()+" venceu esta partida";
        }
   }
    
    /**
     *
     * @param i
     * @return
     */
    public BufferedImage inttoBuff(int i){
        if(i==1){return um;}
        else if(i==2){return dois;}
        else if(i==3){return tres;}
        else if(i==4){return quatro;}
        else if(i==6){return seis;}
        else{return ultima;}
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 0, 102));
        setBorder(new javax.swing.border.MatteBorder(null));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setFont(new java.awt.Font("Segoe Print", 0, 18)); // NOI18N
        setPreferredSize(new java.awt.Dimension(600, 300));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 598, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
           int x = evt.getX();
           int y = evt.getY();
           System.out.println("x=" +width+"  y="+height);
           //dado
           if(x>dxi&&x<dxi+width&&y>dyi&&y<dyi+width){
               casa='D';
               System.out.println("Dado");
           }
           //botão
           else if(x>oxi&&x<oxi+top&&y>oyi&&y<oyi+top){
               casa='O';
               System.out.println("Opcoes");
           }
           //tabuleiro
           else if(x>margin&& x<width*jogo.tab.getColumns()+margin && y>top && y<height*jogo.tab.getRows()+top){
               
               //x-=width/2;
               int h=(y-top)/height;
               int l=(x-margin)/width;
               System.out.println("x=" +l+"  y="+h);
               System.out.println();
               casa= jogo.tab.getConteudo(h, l);
           }
           else{
               casa='X';
           }
           
       if(casa!='X'&&casa!='O'){
         //seleccionar modo de jogo
           /*
            * modo 0 = novo jogo PVP
            * modo 1 = PVP nova jogada
            * modo 2 = nova jogada vsPC
            * modo 3 = novo jogo single
            * modo 6 = nova jogada com repeticao PVP
            * modo 7 = nova jogada com repeticao Single
            * modo 9 = nova jogada com o resto (a implementar caso faça 2 variantes de jogo)
            * 
            */
           
           //************pvp****************
        if(modo==0){
            
            mensagem="Novo jogo PVP, clique no dado";
            this.jogo=novoJogo();
            this.nextPlayer=true;
            jogo.setultimoLance(0);
            if(casa=='D'){
                modo=1;
            }
            
        }
        
        else if(modo==1||modo==6){
            pvp();
        }
        
        //***************vs computer************
        else if(modo==3){
            mensagem="Novo jogo Single, clique no dado";
            this.jogo=novoJogovsPC();
            this.nextPlayer=true;
            jogo.setultimoLance(0);
            if(casa=='D'){
                modo=2;
            }
        }
        else if(modo==2||modo==7||modo==9){
            vsComputador();
        }
       
       } 
        this.repaint();
        
        }

    private void boardClassico(Graphics g) {
        int l=this.getWidth();
        int h=this.getHeight();
        
        width = this.getWidth() / (jogo.tab.getColumns()+2);
        top = width/4;
        margin = width;
        bottom = width/2;
        height=(this.getHeight()-bottom-top) / (jogo.tab.getRows()+2);
        dxi=width*(jogo.tab.getColumns()/2)-width/2+margin;
        dyi=top+height*(jogo.tab.getRows()+1)-bottom;
        oyi=top/2;
        oxi=this.getWidth()-13*top/7;
        ctela=margin*5/2;
        xTela1=margin;
        xTela2=l-margin-ctela;
        //ytelas=dyi-
        
        readClassicBoard();
        super.paintComponent(g);
        
        //desenhar o fundo
         g.drawImage(wall, 0, 0,l,h ,this);
        //desenhar o fundo do tabuleiro
        g.drawImage(fundo, margin, top, width*jogo.tab.getColumns(),height*jogo.tab.getRows(), this);
        //desenhar o tabuleiro
        for(int i=0;i<jogo.tab.getRows();i++){
            for (int j=0; j<jogo.tab.getColumns();j++){
                char conteudo=jogo.tab.getConteudo(i, j);
                boolean cor=jogo.tab.getColor(i, j);
                if(conteudo=='O'){
                    g.drawImage(O,margin+ j*width,top+ i*height, width, height, this);
                }
                else if(i==2&&j==9){
                    g.drawImage(ultima,margin+ j*width,top+ i*height, width, height, this);
                  }
                else if(conteudo==' '){
                    g.drawImage(empty,margin+ j*width,top+ i*height, width, height, this);
                }
                else if(conteudo=='K'){
                    g.drawImage(K, margin+ j*width,top+ i*height, width, height, this);
                }
                
                else if(conteudo=='>'){
                    if(i==2&&j==5){
                    g.drawImage(A, margin+ j*width,top+ i*height, width, height, this);
                    }
                    else if(i==2&&j==7){
                    g.drawImage(B, margin+ j*width,top+ i*height, width, height, this);
                    }
                    else if(i==2&&j==8){
                    g.drawImage(C, margin+ j*width,top+ i*height, width, height, this);
                    }
                    
                }
                else {
                 if(cor){   
                     //g.drawImage(empty,margin+ j*width,top+ i*height, width, height, this);
                    g.drawImage(brancas, margin+ j*width,top+ i*height, width, height, this);
                  g.drawImage(empty,margin+ j*width,top+ i*height, width, height, this);
                 }
                 else{
                     //g.drawImage(empty,margin+ j*width,top+ i*height, width, height, this);
                     g.drawImage(pretas, margin+ j*width,top+ i*height, width, height, this);
                     g.drawImage(empty,margin+ j*width,top+ i*height, width, height, this);
                 }
               }
            }
        }
       
        //desenhar dado e botao de opcoes
        
        g.drawImage(inttoBuff(jogo.getultimoLance()), dxi, dyi, width, height, this);
        g.drawImage(options, oxi, oyi, top, top, this);
        
        
       //desenhar avatares
        if(jogo.getPrimeiro()){
            g.drawImage(brancas, margin+ 15*width/12, dyi+height/8, width, height, this);
            g.drawImage(pretas,this.getWidth()-(margin+ 27*width/12), dyi+height*2/14, width, height, this);
             //desenhar salvas
           int acum=1;
           for(int i=5;i>jogo.Brancas().getRestantes();i--){
                g.drawImage(brancas,margin+ acum*width/4,top+ 22*height/7, width/4, height/4, this);
                acum++;
            }
            acum=1;
            for(int i=5;i>jogo.Pretas().getRestantes();i--){
                g.drawImage(pretas,this.getWidth()-(margin+ acum*width/4),top+ 22*height/7, width/4, height/4, this);
                acum++;
            }
        }
        else{
            g.drawImage(pretas, margin+ 15*width/12, dyi+height/8, width, height, this);
            g.drawImage(brancas,this.getWidth()-(margin+ 27*width/12), dyi+height*2/14, width, height, this);
              //desenhar salvas
           int acum=1;
           for(int i=5;i>jogo.Pretas().getRestantes();i--){
                g.drawImage(pretas,margin+ acum*width/4,top+ 22*height/7, width/4, height/4, this);
                acum++;
            }
            acum=1;
            for(int i=5;i>jogo.Brancas().getRestantes();i--){
                g.drawImage(brancas,this.getWidth()-(margin+ acum*width/4),top+ 22*height/7, width/4, height/4, this);
                acum++;
            }
        }
    }

    
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

  
