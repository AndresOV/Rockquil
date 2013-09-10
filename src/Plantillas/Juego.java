/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Plantillas;


import clases.Imagenes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.*;

/**
 *
 * @author diearbri
 */
public final class Juego extends javax.swing.JFrame {
    Image prevjuego;
    Image oceano;
    int estado=0;
    int cont=1;
    String jugador1;
    String jugador2;
    int TableroJug1[][]=new int[12][10];
    boolean bTableroJug1[][]=new boolean[12][10];
    int TableroJug2[][]=new int[12][10];
    boolean bTableroJug2[][]=new boolean[12][10];

    int pFila=0;
    int pCol=0;
    int pTam=5;
    int pTam2=5;
    int pHor=0;
    int estado2=0;

    public String getJugador1() {
        return jugador1;
    }

    public void setJugador1(String jugador1) {
        this.jugador1 = jugador1;
    }

    public String getJugador2() {
        return jugador2;
    }

    public void setJugador2(String jugador2) {
        this.jugador2 = jugador2;
    }

    
    


    public boolean celdaEstaEnTablero(int f, int c){
        if (f<0) return false;
        if (c<0) return false;
        if (f>=12) return false;
        if (c>=10) return false;
        return true;
    }

    public boolean puedePonerBarco(int tab[][], int tam, int f, int c, int hor){
        int df=0,dc=0;
        if (hor==1) df=1;
        else dc=1;
        for (int c2=c;c2<=c+tam*dc;c2++){
            for (int f2=f;f2<=f+tam*df;f2++){
                if (!celdaEstaEnTablero(f2, c2)){
                    return false;
                }
            }
        }
        for (int f2=f-1;f2<=f+1+df*tam;f2++){
            for (int c2=c-1;c2<=c+1+dc*tam;c2++){
                if (celdaEstaEnTablero(f2,c2)){
                    if (tab[f2][c2]!=0){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void ponerBarco(int tab[][], int tam){

        int f,c,hor;
        do{
            f=(int)(Math.random()*12);
            c=(int)(Math.random()*10);
            hor=(int)(Math.random()*2);
        }while(!puedePonerBarco(tab, tam, f, c, hor));
        int df=0,dc=0;
        if (hor==1) df=1;
        else dc=1;
        for (int f2=f;f2<=f+(tam-1)*df;f2++){/////////
            for (int c2=c;c2<=c+(tam-1)*dc;c2++){////////
                tab[f2][c2]=tam;
            }
        }
    }

    public void iniciarPartida(){
        for (int n=0;n<12;n++){
            for (int m=0;m<10;m++){
                TableroJug1[n][m]=0;
                TableroJug2[n][m]=0;
                bTableroJug1[n][m]=false;
                bTableroJug2[n][m]=false;
            }
        }
        for (int tam=5;tam>=1;tam--){
            
            if(getJugador2().equals("COM")){
                ponerBarco(TableroJug2, tam);
            }
            
        }
        pTam=5;
    }


    public void rectificarBarcoPoner(int pTam){
        int pDF=0;
        int pDC=0;
        if (pHor==1){
            pDF=1;
        }else{
            pDC=1;
        }
        if (pFila+pTam*pDF>=12){
            pFila=9-pTam*pDF;//*************************************************************************
        }
        if (pCol+pTam*pDC>=10){
            pCol=11-pTam*pDC;
        }
    }

    public boolean puedePonerBarco(int tablero[][], int pTam){
        return puedePonerBarco(tablero, pTam, pFila, pCol, pHor);
    }

    public boolean victoria(int tab[][], boolean bTab[][]){
        for (int n=0;n<12;n++){
            for (int m=0;m<10;m++){
                if (bTab[n][m]==false && tab[n][m]!=0){
                    return false;
                }
            }
        }
        return true;
    }

    public void dispararCom(){
        int f,c;
        do{
            f=(int)(Math.random()*12);
            c=(int)(Math.random()*10);
        }while(bTableroJug1[f][c]==true);
        bTableroJug1[f][c]=true;
    }
    
    /** Creates new form JNaval */
    public Juego(String jugador1, String jugador2) {
        setJugador2(jugador2);
        setJugador1(jugador1);
        System.out.println(jugador1+" vs " +jugador2);
        Imagenes i =new Imagenes();
        prevjuego=i.cargar("portada.jpg");
        oceano=i.cargar("oceano1.jpg");
        initComponents();
        setBounds(0,0,800,600);
        if(getJugador2().equals("COM"))//modo jug1 vs com
        {
            addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getModifiers() == MouseEvent.BUTTON3_MASK && estado==1){
                            pHor=1-pHor;
                            rectificarBarcoPoner(pTam);
                            repaint();
                            return;
                        }
                        if (estado==0){
                            estado=1;
                            iniciarPartida();
                            repaint();
                        }else if (estado==1){//pone barco el jugador1
                            if (puedePonerBarco(TableroJug1, pTam)){
                                int pDF=0;
                                int pDC=0;
                                if (pHor==1){
                                    pDF=1;
                                }else{
                                    pDC=1;
                                }
                                for (int m=pFila;m<=pFila+(pTam-1)*pDF;m++){
                                    for (int n=pCol;n<=pCol+(pTam-1)*pDC;n++){
                                        TableroJug1[m][n]=pTam;
                                    }
                                }
                                pTam--;
                                if (pTam==0){
                                    estado=2;

                                    repaint();
                                }
                            }

                        }else if (estado==2){



                                do{
                                    int f=(e.getY()-50)/25;
                                    int c=(e.getX()-400)/25;
                                    if (f!=pFila || c!=pCol){
                                        pFila=f;
                                        pCol=c;
                                        if (celdaEstaEnTablero(f, c)){//////////////////////aqui quede jdchsdjbcjdscjsv
                                            if (bTableroJug2[f][c]==false){
                                                bTableroJug2[f][c]=true;
                                                repaint();
                                                if (victoria(TableroJug2, bTableroJug2)){
                                                    estado=0;
                                                    JOptionPane.showMessageDialog(null, "Has ganado "+getJugador1());
                                                    break;

                                                }
                                                if(getJugador2().equals("COM")){

                                                    dispararCom();
                                                    repaint();
                                                    if (victoria(TableroJug1, bTableroJug1)){
                                                        estado=0;
                                                        JOptionPane.showMessageDialog(null, "Has ganado"+getJugador2());
                                                        break;
                                                    }
                                                    repaint();
                                                }else{ //significa que estajugando modo jug1 vs jug2

                                                }
                                            }
                                        }
                                    }
                                    System.out.println("turno "+cont+")jugador"+(cont%2)+"dispara posicion ["+f+"]["+c+"]");
                                    cont++;
                                }while((cont%2)==0);



                        }

                    }
                }
            );
            addMouseMotionListener(
                new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        int x=e.getX();
                        int y=e.getY();
                        if (estado==1 && x>=20 && y>=50 && x<20+25*10 && y<50+25*10){
                            int f=(y-50)/25;
                            int c=(x-20)/25;
                            if (f!=pFila || c!=pCol){
                                pFila=f;
                                pCol=c;
                                rectificarBarcoPoner(pTam);
                                repaint();
                            }
                        }
                    }
                }
            );
        }else{//modo jug1 vs jug2
            //colocando barcos jug1
            addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getModifiers() == MouseEvent.BUTTON3_MASK && estado==1){
                            pHor=1-pHor;
                            rectificarBarcoPoner(pTam);
                            repaint();
                            return;
                        }
                        if (estado==0){
                            estado=1;
                            iniciarPartida();
                            repaint();
                        }else if (estado==1){//pone barco el jugador1
                            if (puedePonerBarco(TableroJug2,pTam)){
                                int pDF=0;
                                int pDC=0;
                                if (pHor==1){
                                    pDF=1;
                                }else{
                                    pDC=1;
                                }
                                for (int m=pFila;m<=pFila+(pTam-1)*pDF;m++){
                                    for (int n=pCol;n<=pCol+(pTam-1)*pDC;n++){
                                        TableroJug1[m][n]=pTam;
                                    }
                                }
                                pTam--;
                                System.out.println("ubicando barcos"+pTam);
                                if (pTam==0){
                                    estado=3;

                                    repaint();
                                }
                                while(estado==3){///jug2
                                   System.out.println("ubicando jug2 barcos");
                                   puedePonerBarco(TableroJug1,pTam2);
                                         pDF=0;
                                         pDC=0;
                                        if (pHor==1){
                                            pDF=1;
                                        }else{
                                            pDC=1;
                                        }
                                        for (int m=pFila;m<=pFila+(pTam2-1)*pDF;m++){
                                            for (int n=pCol;n<=pCol+(pTam2-1)*pDC;n++){
                                                TableroJug2[m][n]=pTam2;
                                            }
                                        }
                                        pTam2--;
                                        System.out.println(estado+" jug2 barcos"+pTam2);
                                        if (pTam2==0){
                                            estado2=2;
                                            estado=2;

                                            repaint();
                                        }
                                    
                                };
                                System.out.println("saio del bucle jug2 barcos");
                        }else if (estado==2){
                               do{
                                   int f, c;
                                    if((cont%2)==0)//condicion para jug1
                                     {
                                         f=(e.getY()-50)/25;
                                         c=(e.getX()-400)/25;
                                     }else{//condicion para jug2
                                         f=(e.getY()-50)/25;
                                         c=(e.getX()-20)/25;
                                    }
                                        
                                        if (f!=pFila || c!=pCol){
                                            pFila=f;
                                            pCol=c;
                                            if (celdaEstaEnTablero(f, c)){//////////////////////aqui quede jdchsdjbcjdscjsv
                                                if((cont%2)==0){//condicion para jug1
                                                    if (bTableroJug2[f][c]==false){
                                                        bTableroJug2[f][c]=true;
                                                        repaint();
                                                        if (victoria(TableroJug2, bTableroJug2)){
                                                            estado=0;
                                                            JOptionPane.showMessageDialog(null, "Has ganado "+getJugador1());
                                                            break;

                                                        }

                                                            if (victoria(TableroJug1, bTableroJug1)){
                                                                estado=0;
                                                                JOptionPane.showMessageDialog(null, "Has ganado"+getJugador2());
                                                                break;
                                                            }
                                                            repaint();

                                                    }
                                                }else{//condicion para jug2
                                                    if (bTableroJug1[f][c]==false){
                                                        bTableroJug1[f][c]=true;
                                                        repaint();
                                                        if (victoria(TableroJug2, bTableroJug2)){
                                                            estado=0;
                                                            JOptionPane.showMessageDialog(null, "Has ganado "+getJugador1());
                                                            break;

                                                        }

                                                            if (victoria(TableroJug1, bTableroJug1)){
                                                                estado=0;
                                                                JOptionPane.showMessageDialog(null, "Has ganado"+getJugador2());
                                                                break;
                                                            }
                                                            repaint();

                                                    }
                                                
                                                }
                                            }
                                        }
                                        System.out.println("turno "+cont+")jugador"+(cont%2)+"dispara posicion ["+f+"]["+c+"]");
                                        cont++;
                                    }while((cont%2)==0);

                        }
                        }

                    }
                }
            );
            addMouseMotionListener(
                new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        int x=e.getX();
                        int y=e.getY();
                        if((cont%2)==0){//condicion para jug1
                            if (estado==1 && x>=20 && y>=50 && x<20+25*10 && y<50+25*10){
                                int f=(y-50)/25;
                                int c=(x-20)/25;

                                if (f!=pFila || c!=pCol){
                                    pFila=f;
                                    pCol=c;
                                    rectificarBarcoPoner(pTam);
                                    repaint();
                                }
                            }
                        }else{//condicion para jugador2
                            if (estado2==1 && x>=400 && y>=50 && x<400+25*10 && y<50+25*10){
                                int f=(y-50)/25;
                                int c=(x-400)/25;

                                if (f!=pFila || c!=pCol){
                                    pFila=f;
                                    pCol=c;
                                    rectificarBarcoPoner(pTam2);
                                    repaint();
                                }
                            }
                        
                        }
                    }
                }
            );
/*
            //colocando barcos jug2
            //estado=1;
            addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getModifiers() == MouseEvent.BUTTON3_MASK && estado2==1){
                            pHor=1-pHor;
                            rectificarBarcoPoner(pTam2);
                            repaint();
                            return;
                        }
                        if (estado2==0){
                            estado2=1;
                           // iniciarPartida();
                            repaint();
                        }else if (estado2==1){//pone barco el jugador1
                            if (puedePonerBarco(TableroJug1)){
                                int pDF=0;
                                int pDC=0;
                                if (pHor==1){
                                    pDF=1;
                                }else{
                                    pDC=1;
                                }
                                for (int m=pFila;m<=pFila+(pTam2-1)*pDF;m++){
                                    for (int n=pCol;n<=pCol+(pTam2-1)*pDC;n++){
                                        TableroJug2[m][n]=pTam2;
                                    }
                                }
                                pTam2--;
                                if (pTam2==0){
                                    estado2=2;

                                    repaint();
                                }
                            }
                        }

                            if (estado2==2){
                                do{
                                    int f=(e.getY()-50)/25;
                                    int c=(e.getX()-20)/25;
                                    if (f!=pFila || c!=pCol){
                                        pFila=f;
                                        pCol=c;
                                        if (celdaEstaEnTablero(f, c)){//////////////////////aqui quede jdchsdjbcjdscjsv
                                            if (bTableroJug1[f][c]==false){
                                                bTableroJug1[f][c]=true;
                                                repaint();
                                                if (victoria(TableroJug1, bTableroJug1)){
                                                    estado2=0;
                                                    JOptionPane.showMessageDialog(null, "Has ganado "+getJugador1());
                                                    break;

                                                }
                                                
                                                    if (victoria(TableroJug1, bTableroJug1)){
                                                        estado2=0;
                                                        JOptionPane.showMessageDialog(null, "Has ganado"+getJugador2());
                                                        break;
                                                    }
                                                    repaint();
                                                
                                            }
                                        }
                                    }
                                    System.out.println("turno "+cont+")jugador"+(cont%2)+"dispara posicion ["+f+"]["+c+"]");
                                    cont++;
                                }while((cont%2)==0);



                        }

                    }
                }
            );
            addMouseMotionListener(
                new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        int x=e.getX();
                        int y=e.getY();
                        if (estado2==1 && x>=400 && y>=50 && x<400+25*10 && y<50+25*10){
                            int f=(y-50)/25;
                            int c=(x-400)/25;
                            if (f!=pFila || c!=pCol){
                                pFila=f;
                                pCol=c;
                                rectificarBarcoPoner(pTam2);
                                repaint();
                            }
                        }
                    }
                }
            );
  */      
        }
    }

    public boolean noHayInvisible(int tab[][], int valor, boolean bVisible[][]){
        for (int n=0;n<12;n++){
            for (int m=0;m<10;m++){
                if (bVisible[n][m]==false){
                    if (tab[n][m]==valor){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void pintarTablero(Graphics g, int tab[][], int x, int y, boolean bVisible[][]){
        for (int n=0;n<12;n++){
            for (int m=0;m<10;m++){
                if (tab[n][m]>0 && bVisible[n][m]){
                    g.setColor(Color.yellow);
                    if (noHayInvisible(tab, tab[n][m], bVisible)){
                        g.setColor(Color.red);
                    }
                    g.fillRect(x+m*25, y+n*25, 25, 25);////////////
                }
                if (tab[n][m]==0 && bVisible[n][m]){
                    g.setColor(Color.cyan);
                    g.fillRect(x+m*25, y+n*25, 25, 25); /////////
                }
                if (tab[n][m]>0 && tab==TableroJug1 && !bVisible[n][m]){
                    g.setColor(Color.blue);
                    g.fillRect(x+m*25, y+n*25, 25, 25);///////////////
                }
                g.setColor(Color.black);
                g.drawRect(x+n*25, y+m*25, 25, 25);//////////////
                if (estado==1 && tab==TableroJug1){
                    int pDF=0;
                    int pDC=0;
                    if (pHor==1){
                        pDF=1;
                    }else{
                        pDC=1;
                    }
                    if (n>=pFila && m>=pCol && n<=pFila+(pTam-1)*pDF && m<=pCol+(pTam-1)*pDC){
                        g.setColor(Color.green);
                        g.fillRect(x+m*25, y+n*25, 25, 25);////////////////////
                    }
                }
            }
        }
    }

    boolean bPrimeraVez=true;
    @Override
    public void paint(Graphics g){
        if (bPrimeraVez){
            g.drawImage(prevjuego, 0,0,1,1,this);
            g.drawImage(oceano, 0,0,1,1,this);
            bPrimeraVez=false;
        }
        if (estado==0){
            g.drawImage(prevjuego, 0, 0, this);
        }else {
            g.drawImage(oceano, 0, 0, this);
            pintarTablero(g, TableroJug1, 20, 50, bTableroJug1);
            pintarTablero(g, TableroJug2, 400, 50, bTableroJug2);
        }
    }
     
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")/*
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabelJug1 = new javax.swing.JLabel();
        jLabelJug2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jLabelImgOcea = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        ReiniciarParti = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        SalirdelJuego = new javax.swing.JMenuItem();
        SalirPAntaPrinc = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 700));
        setResizable(false);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(1000, 800));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(800, 700));

        jPanel1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel1.setFocusTraversalPolicyProvider(true);
        jPanel1.setLayout(null);

        jLabelJug1.setBackground(new java.awt.Color(0, 0, 0));
        jLabelJug1.setText("hkcbdbcdbcdbcbdkbc");
        jPanel1.add(jLabelJug1);
        jLabelJug1.setBounds(70, 80, 101, 14);

        jLabelJug2.setBackground(new java.awt.Color(0, 0, 0));
        jLabelJug2.setText("hkcbdbcdbcdbcbdkbc");
        jPanel1.add(jLabelJug2);
        jLabelJug2.setBounds(470, 80, 101, 14);

        jTextPane1.setEditable(false);
        jScrollPane4.setViewportView(jTextPane1);

        jPanel1.add(jScrollPane4);
        jScrollPane4.setBounds(10, 456, 319, 690);

        jTextPane2.setEditable(false);
        jScrollPane5.setViewportView(jTextPane2);

        jPanel1.add(jScrollPane5);
        jScrollPane5.setBounds(464, 456, 319, 690);

        jLabelImgOcea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/oceano1.jpg"))); // NOI18N
        jPanel1.add(jLabelImgOcea);
        jLabelImgOcea.setBounds(0, 0, 820, 460);

        jScrollPane1.setViewportView(jPanel1);

        jMenu1.setText("Opciones");

        ReiniciarParti.setText("Reiniciar Partida");
        jMenu1.add(ReiniciarParti);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Salir");

        SalirdelJuego.setText("Salir Completamente de juego");
        jMenu2.add(SalirdelJuego);

        SalirPAntaPrinc.setText("Salir a la pantalla principal del juego");
        jMenu2.add(SalirPAntaPrinc);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
*/
    /**
     * @param args the command line arguments
     */
   private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>
   /*
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ReiniciarParti;
    private javax.swing.JMenuItem SalirPAntaPrinc;
    private javax.swing.JMenuItem SalirdelJuego;
    private javax.swing.JLabel jLabelImgOcea;
    private javax.swing.JLabel jLabelJug1;
    private javax.swing.JLabel jLabelJug2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    // End of variables declaration//GEN-END:variables
*/
}
