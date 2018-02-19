package quizes;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Currency;
import java.util.Scanner;
import javax.swing.*;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

public class Viewer {
	private DrawingPanel dp = new DrawingPanel();
	private static int width,height,maxVal;
	private static int[] pixels;
	private static File currentFile;
	private static JLabel labelInf,labelPic;
	public static void main(String[] args) {
		
		JFrame w = new JFrame("Viewer");
		w.setSize(800,800);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setVisible(true);
		w.setLocationRelativeTo(null);
		JMenuBar jmb = new JMenuBar();
		
		JMenu jmFile = new JMenu("File");
		JMenu jmHelp = new JMenu("Help");
			jmb.add(jmFile);
			jmb.add(jmHelp);
			
		JMenuItem jmiP2 = new JMenuItem("P2");
		JMenuItem jmiOpen = new JMenuItem("Open");
		JMenuItem jmiClear = new JMenuItem("Clear");
		JMenuItem jmiExit = new JMenuItem("Exit");
		JMenuItem jmiInfo = new JMenuItem("Info");
		
		jmiP2.setMnemonic('2');
		jmiClear.setMnemonic('C');
		jmiOpen.setMnemonic('O');
		jmiExit.setMnemonic('E');
		
		jmFile.add(jmiP2);
		jmFile.add(jmiOpen);
		jmFile.add(jmiClear);
		jmFile.addSeparator();
		jmFile.add(jmiExit);
		jmHelp.add(jmiInfo);

		JToolBar jtb = new JToolBar();
		jtb.setFloatable(false);
		w.setJMenuBar(jmb);
		w.add(jtb, BorderLayout.NORTH);
		
		labelPic = new JLabel();
		
		jmiP2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame w = new JFrame();
				w.setVisible(true);
				w.setSize(800, 800);
				try {
					Scanner sc = new Scanner(new File("agascii.pgm"));
					System.out.println(sc.next());
					
					width = Integer.parseInt(sc.next());
					height = Integer.parseInt(sc.next());
					maxVal = Integer.parseInt(sc.next());
					pixels = new int[width*height];
					
					for(int i = 0; i<width * height; i++) {
						pixels[i] = Integer.parseInt(sc.next());
					}
				}catch(FileNotFoundException e2) {}
			}
		});
		jmiOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(".");
				int retVal = jfc.showOpenDialog(null);
				if(retVal == JFileChooser.APPROVE_OPTION) {
					currentFile = jfc.getSelectedFile();
				}
			}
		});
		
		jmiInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				labelInf = new JLabel();
				JFrame w = new JFrame("Information");
				w.setVisible(true);
				w.setSize(300, 70);
				w.setLocationRelativeTo(null);
				labelInf.setText("	Created By Kubilay Çakmak");
				w.add(labelInf);
			}
		});
		jmiExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	class DrawingPanel extends JPanel{
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			for(int row = 0; row < height; row++) {
				for(int col = 0; col < width; col++) {
					int intColor = pixels[row*width+col];
					g.setColor(new Color(intColor, intColor, intColor));
					g.fillRect(row, col, 1, 1);
				}
			}
		}
	}
}
