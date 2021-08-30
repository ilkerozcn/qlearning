package qlearning;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextField;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Handler;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class oyun extends JFrame {
	static int[][] harita = new int[20][20];
	static double[][] Q = new double[400][400];
	static JLabel[][] Lharita = new JLabel[20][20];
	private static JPanel contentPane;
	static int konumx=2;
	static int konumy=0;
	static int hedefx=5;
	static int hedefy=19;
	static thread th=new thread();
	private static JTextField say;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					oyun frame = new oyun();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public oyun() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		loadmap();
		JButton btnNewButton = new JButton("Baslat");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				th.start();
				
			}
		});
		btnNewButton.setBounds(773, 188, 113, 21);
		contentPane.add(btnNewButton);
		
		say = new JTextField();
		say.setBounds(790, 219, 96, 19);
		contentPane.add(say);
		say.setColumns(10);
		
		textField = new JTextField();
		textField.setBounds(773, 41, 96, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText("0");
		textField_1 = new JTextField();
		textField_1.setBounds(773, 104, 96, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText("1");
		JLabel lblNewLabel = new JLabel("Baþlangýç Yükseliði");
		lblNewLabel.setBounds(773, 18, 96, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Hedef Yüksekliði");
		lblNewLabel_1.setBounds(773, 81, 96, 13);
		contentPane.add(lblNewLabel_1);
	
		JButton btnNewButton_1 = new JButton("Kaydet");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				konumx=Integer.parseInt(textField.getText());
				hedefx=Integer.parseInt(textField_1.getText());
				Lharita[hedefx][hedefy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\kýr.png"));
				harita[hedefx][hedefy]=100;
			}
		});
		btnNewButton_1.setBounds(784, 133, 85, 21);
		contentPane.add(btnNewButton_1);
		
	}
	static void loadmap() {
		
		File file = new File("engel.txt");
		int duvar=0;
		for(int i=0;i<20;i++) {
			for(int y=0;y<20;y++) {
				harita[i][y]=0;				
			}
		}
		Random ran = new Random();
		while(duvar<120) {
			int randx= ran.nextInt(20);
			int randy=ran.nextInt(20);
			if(harita[randx][randy]==0)
			{
				harita[randx][randy]=-1;
				duvar++;
			}	
		}
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				Lharita[i][j] = new JLabel("");
				if (harita[i][j] == -1)
					Lharita[i][j].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\duvar.png"));
				if (harita[i][j] == 0)
					Lharita[i][j].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\yol.png"));
				Lharita[i][j].setBounds(j * 30, i * 30, 30, 30);
				contentPane.add(Lharita[i][j]);
				Lharita[i][j].repaint();
			}
		}
		try(BufferedWriter br = new BufferedWriter(new FileWriter(file))){
		for(int i=0;i<20;i++) {
			for(int y=0;y<20;y++) {
				System.out.print(harita[i][y]+" ");
			br.write("("+i+","+y+","+harita[i][y]+")   ");
			}
				System.out.println(); br.newLine();}} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		for(int i=0;i<400;i++) {
			for(int y=0;y<400;y++) {
				Q[i][y]=0;
			}}
		/*
		if(harita[hedefx-3][hedefy+1]==0) {
			System.out.println("eklendi1");
		harita[hedefx-3][hedefy+1]=20;}
		if(harita[hedefx-3][hedefy-2]==0) {
		harita[hedefx-3][hedefy-2]=20;
		System.out.println("eklend2");}
		if(harita[hedefx-5][hedefy+5]==0) {
		harita[hedefx-5][hedefy+5]=10;
		System.out.println("eklendi3");}
		if(harita[hedefx-1][hedefy+1]==0) {
			harita[hedefx-1][hedefy+1]=10;
			System.out.println("eklendi4");}
		if(harita[hedefx-2][hedefy+2]==0) {
			harita[hedefx-2][hedefy+2]=10;
			System.out.println("eklendi5");}*/
	
	}
	static int hedef=0;
	private static JTextField textField;
	private JTextField textField_1;
	static void move() {
		
		
		 int rastgele=0;
		int right=konumx+1;int left=konumx-1;int down=konumy+1;int up=konumy-1;int gox,goy;int go=-2;double geç=-5;
		double[] temp=new double[5]; 
		if(right<20) {temp[3]=Q[right][konumy];}
		else temp[0]=-1000;
		if(left>=0) {temp[1]=Q[left][konumy];}
		else temp[1] = -1000;
		if(down<20) {temp[2]=Q[konumx][down];}
		else temp[2] = -1000;
		if(up>=0) {temp[0]=Q[konumx][up];}
		else temp[3] =-1000;
	int salla1=0, salla2 = 7777; int salla3 = 7777;
	int salla4=7777;
	
		for(int i=0;i<4;i++) {
			if(geç<temp[i]) {
				go=i; geç=temp[i];}
			if(geç==temp[i]) {
				salla1=go;salla2=i;
				rastgele++;
			}
			if(geç==temp[i] && i!=salla2) {
				salla1=go;salla3=i;
				rastgele++;
			}
			if(geç==temp[i] && i!=salla2) {
				salla1=go;salla4=i;
				rastgele++;
			}
		}
		if(salla1!=7777 && temp[go]==temp[salla1]) {
			Random ra= new Random();
		//	System.out.print("sjw");
			int de =ra.nextInt(4);
			if(de==0)
				go=salla1;
			if(de==1)
				go=salla2;
			if(de==2)
				go=salla3;
			if(de==3)
				go=salla4;
		}
	//	System.out.println(go);
		if(rastgele>=3) {
		Random rand=new Random();
	//	System.out.print("ses");
		go=4;
		temp[4]=1;
		while(true) {
		go=rand.nextInt(4);
		if(go==0 && temp[go]!=-1000 && Q[right][konumy]!=-1) {
			rastgele=0;
			break;
		}
		if(go==1 && temp[go]!=-1000 && Q[left][konumy]!=-1) {
			rastgele=0;
			break;
		}
		if(go==2 && temp[go]!=-1000 && Q[konumx][down]!=-1) {
			rastgele=0;
			break;
		}
		if(go==3 && temp[go]!=-1000 && Q[konumx][up]!=-1) {
			rastgele=0;
			break;
		}
		}}
									if(go==0) {
										if(konumy>=0 && konumy<20 && konumx>=0 && konumx<20) 
										if(harita[konumx][konumy]==-1)
											{Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\duvar.png"));
											}else Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\yol.png"));
							
										konumx=right;	
										if(konumx>=0 && konumx<20) {
										qlearn(konumx,konumy);
										Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\kýr.png"));
										}	}
									if(go==1) {
										if(konumy>=0 && konumy<20 && konumx>=0 && konumx<20) 
										if(harita[konumx][konumy]==-1)
									{Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\duvar.png"));
									}else Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\yol.png"));
										konumx=left;
										if(konumx>=0 && konumx<20) {
										qlearn(konumx,konumy);
										Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\kýr.png"));
										}	}
									if(go==2) {
										if(konumy>=0 && konumy<20 && konumx>=0 && konumx<20) 
										if(harita[konumx][konumy]==-1)
									{Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\duvar.png"));
									}else Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\yol.png"));
										konumy=down;
										if(konumy>=0 && konumy<20 && konumx>=0 && konumx<20) {
										qlearn(konumx,konumy);
										Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\kýr.png"));
										}	}
									if(go==3) {
										if(konumy>=0 && konumy<20 && konumx>=0 && konumx<20) 
										if(harita[konumx][konumy]==-1)
									{Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\duvar.png"));
									}else Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\yol.png"));
										konumy=up;
										if(konumy>=0 && konumy<20 && konumx>=0 && konumx<20) {
										qlearn(konumx,konumy);
										Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\kýr.png"));
										}	}
									if(konumy>=0 && konumy<20 && konumx>=0 && konumx<20) 
										
									if(harita[konumx][konumy]==-1) {
										Lharita[konumx][konumy].setIcon(new ImageIcon("C:\\Users\\pc\\Desktop\\qlearning\\src\\image\\duvar.png"));
										konumx=Integer.parseInt(textField.getText());konumy=0;
									}
									if(hedefx==konumx && hedefy==konumy && hedef<10) {
										konumx=Integer.parseInt(textField.getText());konumy=0;
										hedef++;
										say.setText(""+hedef);
									}
									if(hedefx==konumx && hedefy==konumy && hedef==10) {
										File d = new File("Q.txt");
										try(BufferedWriter br = new BufferedWriter(new FileWriter(d))){
											for(int i=0;i<20;i++) {
												for(int y=0;y<20;y++) {
													System.out.print(Q[i][y]+" ");
												br.write("("+i+","+y+","+Q[i][y]+")   ");
												}
													System.out.println(); br.newLine();}} catch (IOException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
										System.out.println("HEDEFE ULASILDI");hedef++;
										th.stop();
										System.exit(0);
									}
	}
	static void qlearn(int x,int y){
	
	
		int right=x+1;int left=x-1;int down=y+1;int up=y-1;int gox,goy;int go=-2;double geç=-5;
		double[] temp=new double[4]; 
		if(right<20) {temp[0]=Q[right][konumy];}
		else temp[0]=-1000;
		if(left>=0) {temp[1]=Q[left][konumy];}
		else temp[1] = -1000;
		if(down<20) {temp[2]=Q[konumx][down];}
		else temp[2] = -1000;
		if(up>=0) {temp[3]=Q[konumx][up];}
		else temp[3] =-1000;
		for(int i=0;i<4;i++) {
			if(geç<temp[i]) { go=i; geç=temp[i];}
		}
	//	if(Q[x][y]==0)
		if(y>=0 && y<20 && x>=0 && x<20)
	if(Q[x][y]==0)
	Q[x][y]=harita[x][y]+(0.9)*temp[go];
	
/*	if(temp[go]!=-1000) {
		while(true) {
			if(Q[x][y]>geç)
			Q[x][y]=Q[x][y]-1;
			else break;
		}
	}*/
	
	/*	for(int i=0;i<400;i++) {
			for(int j=0;j<400;j++) {
				if(Q[i][j]>0)
					System.out.print(Q[i][j] +" |||");
			}
			
		}*/
	
	
}	
}
