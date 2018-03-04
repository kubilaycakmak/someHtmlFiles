package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.crypto.dsig.keyinfo.PGPData;
//import javax.swing.filechooser.FileSystemView;
public class Viewer extends JFrame{
    private static File currentFile;
    private int width, height, mval;
    private int[] pixels;
    private DrawingPanel dp;
    private JFileChooser jfchooser;
    private JToolBar jtbar;
    private JPanel jpPic;
    private static JMenuBar jmb;
    private JMenu jmFile, jmHelp;
    private JMenuItem jmiOpen, jmiExit, jmiInfo;
    private JTextArea jtxArea;
    private FileFilter filter;
    Viewer(){
    	dp = new DrawingPanel();
        currentFile = null;
        jmb = new JMenuBar();
        jmFile = new JMenu("File");
        jmHelp = new JMenu("Help");
        jmb.add(jmFile);
        jmb.add(jmHelp);

        jmiOpen = new JMenuItem("Open");
        jmiExit = new JMenuItem("Exit");
        jmiInfo = new JMenuItem("Info");

        jmFile.add(jmiOpen);
        jmFile.addSeparator();
        jmFile.add(jmiExit);
        jmHelp.add(jmiInfo);
        
        jtbar = new JToolBar();
        jtbar.setFloatable(false);
        
        jtxArea = new JTextArea();
        jtxArea.setText("\n\nHello, This is a Image Viewer for Different format of Pictures.\n"
				+"•'P1': PBM raster data following the header is interpreted as ASCII text\n "
				+ "•'P2': PGM raster data following the header is interpreted as ASCII text\n "
				+ "•'P3': PPM raster data following the header is interpreted as ASCII text\n "
				+ "•'P4': PBM raster data following the header is interpreted as binary\n "
				+ "•'P5': PGM raster data following the header is interpreted as binary\n "
				+ "•'P6': PPM raster data following the header is interpreted as binary\r\n" + "\n\nIt will open on second window."
				);
      
        this.add(jtxArea);
        jtxArea.setEditable(false);
        jtxArea.setBackground(Color.LIGHT_GRAY);
        
        //----------------------------------------------------------------------------------------------//
 
        jmiOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
					JFileChooser jfchooser = new JFileChooser(".");
					jfchooser.setAcceptAllFileFilterUsed(false);

					filter = new FileNameExtensionFilter("PGM binary files", "pgm");
					jfchooser.addChoosableFileFilter(filter);
					int retValue = jfchooser.showOpenDialog(null);

					JFrame a = new JFrame();
					a.setSize(800, 600);
					a.setVisible(true);

					try {
					//	Scanner sc = new Scanner(currentFile);
						//sc.nextLine();
						
						if(retValue == JFileChooser.APPROVE_OPTION) {
							currentFile = jfchooser.getSelectedFile();
							FileInputStream fis = 
									new FileInputStream(currentFile);
							BufferedInputStream bis =
									new BufferedInputStream(fis);	
							

							 byte [] magicNum = new byte[2];
								fis.read(magicNum);

								char chByte = (char) fis.read();
								while(Character.isWhitespace(chByte))
									chByte = (char) fis.read();

								byte fb = (byte) chByte;
								
								int count = 1;
								byte [] w = new byte[3];
								w[0] = fb; 

								byte aByte = (byte) fis.read();
								while(!Character.isWhitespace(aByte)){
									w[count++] = aByte;
									aByte = (byte) fis.read();
								}
								
								int i = 0;
								String strWidth = new String(w);

								System.out.println(new String(magicNum)); 
								//System.out.println(w[0]+":" + w[1]+":"+w[2]);
								System.out.println(strWidth);
								
								width = Integer.parseInt(strWidth);
								
								aByte = (byte) fis.read();
								while(Character.isWhitespace(aByte))
									aByte = (byte) fis.read();
								
								count = 1;
								byte [] h = new byte[3];
								h[0] = aByte; 
								
								aByte = (byte) fis.read();
								while(!Character.isWhitespace(aByte)){
									h[count++] = aByte;
									aByte = (byte) fis.read();
								}				
								height = Integer.parseInt(new String(h));
								System.out.println("height: " + height);

								aByte = (byte) fis.read();
								while(Character.isWhitespace(aByte))
									aByte = (byte) fis.read();

								count = 1;
								byte [] mv = new byte[3];
								mv[0] = aByte;
								aByte = (byte) fis.read();
								while(!Character.isWhitespace(aByte)){
									mv[count++] = aByte;
									aByte = (byte) fis.read();
								}
								System.out.println("Max.Value: " + 
									new String(mv));

								aByte = (byte) fis.read();
								while(Character.isWhitespace(aByte))
									aByte = (byte) fis.read();

								pixels = new int[width*height];
								pixels[0] = aByte;
								for(int k = 1; k < width*height; k++)
									pixels[k] =  fis.read();
								System.out.println("Done reading the file");
								a.add(dp);
						}
					}catch(FileNotFoundException fnfe) {}
					catch(IOException ie){}
				}
				
				
			
		});  
        //--------------------------------------------------------------------------------------------------//
        
        jmiExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
        jmiInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame w = new JFrame("Information");
				
				w.setLocationRelativeTo(null);
				w.setSize(200, 100);
				w.setVisible(true);
				
				jtxArea = new JTextArea();
				jtxArea.setText("Created by Kubilay Çakmak");
				w.add(jtxArea);
				jtxArea.setEditable(false);
				
				jtxArea.setBackground(Color.YELLOW);
			}
		});
    }
    class DrawingPanel extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            for(int row = 0; row < height; row++)
				for(int col = 0; col < width; col++)
				{
					
				  try{
					Color c = new Color(
							pixels[row*width+col],
							pixels[row*width+col],
							pixels[row*width+col]);
						g.setColor(c);
						g.fillRect(col,row,1,1);
					}catch(IllegalArgumentException e){
					  //System.out.println(pixs[i*width+j]+":"+"i: "+i+"j: "+j);
					}
				}
        }
    }
    public static void main(String args[]){
    	Viewer w = new Viewer();
        w.setSize(800,600);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setVisible(true);
        w.setLocationRelativeTo(null);
        w.setJMenuBar(jmb);
    }
}
