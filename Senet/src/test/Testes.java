/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


import senet.*;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 *
 * @author SnowBlade
 */
public class Testes {
   
    @Test
    public void testacriarJogador(){
        Jogador j1= new Jogador("Andre", true);
        Jogador j2= new Jogador("Luis", false);
        assertEquals("Andre", j1.getNome());
        assertEquals("Luis", j2.getNome());
        assertEquals(false, j2.getCor());
        assertEquals(true, j1.getCor());
        assertEquals(0, j1.getPeca(0).getPos());
        assertEquals(3, j2.getPeca(1).getPos());
        assertEquals(false, j1.getPeca(3).inDoubleBlock());
        assertEquals(false, j2.getPeca(4).inTripleBlock());
    }
    
    @Test
    public void testacriaPeca(){
        Peca p1=new Peca('W',true,3);
        assertEquals(true, p1.getCor());
        assertEquals(3, p1.getPos());
        
    }
    
    @Test
    public void testasetPeca(){
        Peca p1=new Peca('W',true,3);
        p1.triple();
        assertEquals(true, p1.inDoubleBlock());
        assertEquals(true, p1.inTripleBlock());
        
        p1.nottriple();
        assertEquals(true, p1.inDoubleBlock());
        assertEquals(false, p1.inTripleBlock());
        
        p1.setPos(17);
        assertEquals(17, p1.getPos());
    }
    
    @Test
    public void testaTabuleiro(){
        Tabuleiro t1=new Tabuleiro();
        assertEquals(" ", t1.getTabuleiro()[1][3]);
        assertEquals("X", t1.getTabuleiro()[0][0]);
        assertEquals("Y", t1.getTabuleiro()[0][1]);
    }
}
