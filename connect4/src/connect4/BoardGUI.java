package connect4;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BoardGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -534231991389053219L;
	public static final boolean NOFADE = false;
	private static final boolean FADE = true;
	private static final Color FRAMECOLOR = Color.YELLOW;
	private static final int PLAYERISFIRST = 0;
	private static final int OPPONENTISFIRST = 1;
	private static final int COLOR1 = myC4Panel.COLOR1;
	private static final int EMPTY = myC4Panel.EMPTY;
	private static final String COLOR1STRING = myC4Panel.COLOR1STRING;
	private static final String COLOR2STRING = myC4Panel.COLOR2STRING;
	private static final Color COLOR1COLOR = myC4Panel.COLOR1COLOR;
	private static final Color COLOR2COLOR = myC4Panel.COLOR2COLOR;
	public static int TURN = COLOR1;
	private int NEXTTURN = COLOR1;
	private int FIRSTTURN = PLAYERISFIRST;
	private int[] WINNERS = new int[8];
	private boolean WIN;
	private JPanel contentPane;
	private JPanel boardPanel;
	private JLabel turnLabel;
	private final ButtonGroup firstTurn = new ButtonGroup();
	private myC4Panel boardPanels[][] = new myC4Panel[7][6];
	private myC4Panel currentPanel;
	private myC4Panel lowestPanel;
	private final ButtonGroup playerPiece = new ButtonGroup();
	private int radius;
	
	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(javax.swing.UIManager
				.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoardGUI frame = new BoardGUI();
					frame.setVisible(true);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public BoardGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				attemptClose();
			}
		});
		setTitle("Connect 4");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		setMinimumSize(new Dimension(430, 300));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5, 0));
		
		boardPanel = new JPanel();
		boardPanel.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				if(!WIN)
					mouseExitedEVT(e);
			}
			
			public void mousePressed(MouseEvent e) {
				if(!WIN)
					mousePressedEVT(e);
			}
		});
		boardPanel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				if(!WIN)
					mouseMovedEVT(e);
			}
		});
		boardPanel.setBackground(FRAMECOLOR);
		contentPane.add(boardPanel, BorderLayout.CENTER);
		GridBagLayout gbl_boardPanel = new GridBagLayout();
		gbl_boardPanel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0,
				1.0, 1.0 };
		gbl_boardPanel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0,
				1.0, 1.0, 1.0 };
		gbl_boardPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_boardPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		boardPanel.setLayout(gbl_boardPanel);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.BOTH;
		int i, j;
		for(i = 0; i < 7; i++) {
			for(j = 0; j < 6; j++) {
				boardPanels[i][j] = new myC4Panel(i, j);
				boardPanels[i][j].setBackground(FRAMECOLOR);
				gbc.gridx = i;
				gbc.gridy = j;
				boardPanel.add(boardPanels[i][j], gbc);
			}
		}
		
		JPanel sidePanel = new JPanel();
		contentPane.add(sidePanel, BorderLayout.EAST);
		sidePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel southPanel = new JPanel();
		sidePanel.add(southPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_southPanel = new GridBagLayout();
		gbl_southPanel.columnWidths = new int[] { 89 };
		gbl_southPanel.rowHeights = new int[] { 23 };
		southPanel.setLayout(gbl_southPanel);
		
		JButton btnNewButton = new JButton("Restart");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restartEVT();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(5, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		southPanel.add(btnNewButton, gbc_btnNewButton);
		
		JButton restartButton = new JButton("Exit");
		restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitEVT();
			}
		});
		GridBagConstraints gbc_restartButton = new GridBagConstraints();
		gbc_restartButton.insets = new Insets(0, 0, 0, 0);
		gbc_restartButton.fill = GridBagConstraints.BOTH;
		gbc_restartButton.gridx = 0;
		gbc_restartButton.gridy = 1;
		southPanel.add(restartButton, gbc_restartButton);
		
		JPanel centerPanel = new JPanel();
		sidePanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 5));
		
		JPanel ftPanel = new JPanel();
		ftPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		centerPanel.add(ftPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_ftPanel = new GridBagLayout();
		gbl_ftPanel.columnWidths = new int[] { 89 };
		gbl_ftPanel.rowHeights = new int[] { 0 };
		ftPanel.setLayout(gbl_ftPanel);
		
		JLabel ftLabel = new JLabel("First Turn:");
		GridBagConstraints gbc_ftLabel = new GridBagConstraints();
		gbc_ftLabel.fill = GridBagConstraints.BOTH;
		gbc_ftLabel.insets = new Insets(0, 10, 5, 0);
		gbc_ftLabel.gridx = 0;
		gbc_ftLabel.gridy = 0;
		ftPanel.add(ftLabel, gbc_ftLabel);
		
		JRadioButton ftPlayer = new JRadioButton("Player");
		ftPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerEVT();
			}
		});
		ftPlayer.setSelected(true);
		firstTurn.add(ftPlayer);
		ftPlayer.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_ftPlayer = new GridBagConstraints();
		gbc_ftPlayer.anchor = GridBagConstraints.WEST;
		gbc_ftPlayer.fill = GridBagConstraints.BOTH;
		gbc_ftPlayer.insets = new Insets(0, 5, 5, 0);
		gbc_ftPlayer.gridx = 0;
		gbc_ftPlayer.gridy = 1;
		ftPanel.add(ftPlayer, gbc_ftPlayer);
		
		JRadioButton ftOpponent = new JRadioButton("Opponent");
		ftOpponent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opponentEVT();
			}
		});
		firstTurn.add(ftOpponent);
		ftOpponent.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_ftOpponent = new GridBagConstraints();
		gbc_ftOpponent.anchor = GridBagConstraints.WEST;
		gbc_ftOpponent.fill = GridBagConstraints.BOTH;
		gbc_ftOpponent.insets = new Insets(0, 5, 5, 0);
		gbc_ftOpponent.gridx = 0;
		gbc_ftOpponent.gridy = 2;
		ftPanel.add(ftOpponent, gbc_ftOpponent);
		
		JPanel center2Panel = new JPanel();
		centerPanel.add(center2Panel, BorderLayout.CENTER);
		center2Panel.setLayout(new BorderLayout(0, 0));
		
		JPanel ppPanel = new JPanel();
		ppPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		center2Panel.add(ppPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_ppPanel = new GridBagLayout();
		gbl_ppPanel.columnWidths = new int[] { 89, 0 };
		gbl_ppPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_ppPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_ppPanel.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		ppPanel.setLayout(gbl_ppPanel);
		
		JLabel ppLabel = new JLabel("Player Piece:");
		GridBagConstraints gbc_ppLabel = new GridBagConstraints();
		gbc_ppLabel.fill = GridBagConstraints.BOTH;
		gbc_ppLabel.insets = new Insets(0, 10, 5, 0);
		gbc_ppLabel.gridx = 0;
		gbc_ppLabel.gridy = 0;
		ppPanel.add(ppLabel, gbc_ppLabel);
		
		JRadioButton ppColor1 = new JRadioButton(COLOR1STRING);
		ppColor1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redEVT();
			}
		});
		ppColor1.setSelected(true);
		playerPiece.add(ppColor1);
		ppColor1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_ppColor1 = new GridBagConstraints();
		gbc_ppColor1.fill = GridBagConstraints.BOTH;
		gbc_ppColor1.anchor = GridBagConstraints.WEST;
		gbc_ppColor1.insets = new Insets(0, 5, 5, 0);
		gbc_ppColor1.gridx = 0;
		gbc_ppColor1.gridy = 1;
		ppPanel.add(ppColor1, gbc_ppColor1);
		
		JRadioButton ppColor2 = new JRadioButton(COLOR2STRING);
		ppColor2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blackEVT();
			}
		});
		playerPiece.add(ppColor2);
		ppColor2.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_ppColor2 = new GridBagConstraints();
		gbc_ppColor2.fill = GridBagConstraints.BOTH;
		gbc_ppColor2.anchor = GridBagConstraints.WEST;
		gbc_ppColor2.insets = new Insets(0, 5, 5, 0);
		gbc_ppColor2.gridx = 0;
		gbc_ppColor2.gridy = 2;
		ppPanel.add(ppColor2, gbc_ppColor2);
		
		JPanel lPanel = new JPanel();
		center2Panel.add(lPanel, BorderLayout.NORTH);
		GridBagLayout gbl_lPanel = new GridBagLayout();
		gbl_lPanel.columnWidths = new int[] { 66 };
		gbl_lPanel.rowHeights = new int[] { 14 };
		lPanel.setLayout(gbl_lPanel);
		
		JLabel currentLabel = new JLabel("Current Turn:");
		GridBagConstraints gbc_currentLabel = new GridBagConstraints();
		gbc_currentLabel.fill = GridBagConstraints.BOTH;
		gbc_currentLabel.insets = new Insets(0, 0, 5, 0);
		gbc_currentLabel.gridx = 0;
		gbc_currentLabel.gridy = 0;
		lPanel.add(currentLabel, gbc_currentLabel);
		
		turnLabel = new JLabel(COLOR1STRING);
		turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_turnLabel = new GridBagConstraints();
		gbc_turnLabel.fill = GridBagConstraints.BOTH;
		gbc_turnLabel.gridx = 0;
		gbc_turnLabel.gridy = 1;
		lPanel.add(turnLabel, gbc_turnLabel);
	}
	
	protected void attemptClose() {
		if(JOptionPane.showConfirmDialog(this,
				"Are you sure you want to quit?", "Exit",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	protected void mouseExitedEVT(MouseEvent e) {
		currentPanel = null;
		repaint();
	}
	
	protected void mousePressedEVT(MouseEvent e) {
		if(lowestPanel == null)
			return;
		placePiece(lowestPanel);
		repaint();
	}
	
	protected void mouseMovedEVT(MouseEvent e) {
		int i = (int) (e.getX() / (boardPanel.getWidth() / 7));
		int j = (int) (e.getY() / (boardPanel.getHeight() / 6));
		if(i == 7)
			i--;
		if(j == 6)
			j--;
		currentPanel = boardPanels[i][j];
		i = currentPanel.getIndexX();
		for(j = 5; j >= 0; j--) {
			if(boardPanels[i][j].getPiece() == myC4Panel.EMPTY) {
				lowestPanel = boardPanels[i][j];
				break;
			}
			if(j == 0)
				lowestPanel = null;
		}
		repaint();
	}
	
	protected void exitEVT() {
		attemptClose();
	}
	
	protected void restartEVT() {
		int i, j;
		for(i = 0; i < 7; i++) {
			for(j = 0; j < 6; j++) {
				boardPanels[i][j].setPiece(myC4Panel.EMPTY);
				repaint();
			}
		}
		TURN = NEXTTURN;
		if(FIRSTTURN == OPPONENTISFIRST)
			TURN = opposite(TURN);
		WINNERS = new int[8];
		WIN = false;
		setLabel();
	}
	
	private int opposite(int turn) {
		return myC4Panel.oppositePiece(turn);
	}
	
	protected void opponentEVT() {
		FIRSTTURN = OPPONENTISFIRST;
	}
	
	protected void playerEVT() {
		FIRSTTURN = PLAYERISFIRST;
	}
	
	protected void blackEVT() {
		NEXTTURN = myC4Panel.COLOR2;
	}
	
	protected void redEVT() {
		NEXTTURN = myC4Panel.COLOR1;
	}
	
	protected void placePiece(myC4Panel cp) {
		cp.setPiece(TURN);
		if(WIN = win()) {
			JOptionPane.showMessageDialog(this,
					(TURN == COLOR1 ? COLOR1STRING : COLOR2STRING)
							+ " wins!", getTitle(),
					JOptionPane.PLAIN_MESSAGE);
		} else if(tie()) {
			JOptionPane.showMessageDialog(this, "Tie!", getTitle(),
					JOptionPane.PLAIN_MESSAGE);
		} else {
			TURN = TURN == myC4Panel.COLOR1 ? myC4Panel.COLOR2
					: myC4Panel.COLOR1;
		}
		if(lowestPanel.getIndexY() != 0 && !WIN)
			lowestPanel = boardPanels[lowestPanel.getIndexX()][lowestPanel
					.getIndexY() - 1];
		else
			lowestPanel = null;
		setLabel();
	}
	
	private void setLabel() {
		turnLabel.setText(TURN == COLOR1 ? COLOR1STRING : COLOR2STRING);
	}
	
	private boolean tie() {
		int i, j;
		for(i = 0; i < 7; i++) {
			for(j = 0; j < 6; j++) {
				if(boardPanels[i][j].getPiece() == EMPTY)
					return false;
			}
		}
		return true;
	}
	
	private boolean win() {
		int c = 0, i, j, k;		//number of o in a line
		for(i = 0; i <= 6; i++)				//COLUMNS
		{
			for(j = 0; j <= 5; j++) {
				if(boardPanels[i][j].getPiece() == TURN) {
					c++;
					if(c == 4) {
						for(k = 6; k >= 0; k -= 2)
							WINNERS[k] = i;
						for(k = 7; k >= 1; k -= 2) {
							WINNERS[k] = j;
							j--;
						}
						return true;
					}
				} else
					c = 0;
			}
			c = 0;
		}
		c = 0;
		for(j = 0; j <= 5; j++)				//ROWS
		{
			for(i = 0; i <= 6; i++) {
				if(boardPanels[i][j].getPiece() == TURN) {
					c++;
					if(c == 4) {
						for(k = 6; k >= 0; k -= 2) {
							WINNERS[k] = i;
							i--;
						}
						for(k = 7; k >= 1; k -= 2)
							WINNERS[k] = j;
						return true;
					}
				} else
					c = 0;
			}
			c = 0;
		}
		c = 0;
		int jt = 2;
		j = 2;
		int it = i = 0;
		while(i != 4 || j != 0)								//down-right diagonal
		{
			if(boardPanels[i][j].getPiece() == TURN) {
				c++;
				if(c == 4) {
					for(k = 6; k >= 0; k -= 2) {
						WINNERS[k] = i;
						i--;
					}
					for(k = 7; k >= 1; k -= 2) {
						WINNERS[k] = j;
						j--;
					}
					return true;
				}
			} else
				c = 0;
			i++;
			j++;
			if(i == 7 || (j == 6 && i == 6)) {
				j = 0;
				it++;
				i = it;
				c = 0;
			} else if(j == 6) {
				jt--;
				j = jt;
				i = 0;
				c = 0;
			}
		}
		c = 0;
		i = it = 0;
		j = 3;
		jt = 3;
		while(i != 4 || j != 5)						//up-right diagonal
		{
			if(boardPanels[i][j].getPiece() == TURN) {
				c++;
				if(c == 4) {
					for(k = 6; k >= 0; k -= 2) {
						WINNERS[k] = i;
						i--;
					}
					for(k = 7; k >= 1; k -= 2) {
						WINNERS[k] = j;
						j++;
					}
					return true;
				}
			} else
				c = 0;
			if(i == 6 || (j == 0 && i == 5)) {
				j = 5;
				it++;
				i = it;
				c = 0;
			} else if(j == 0) {
				jt++;
				j = jt;
				i = 0;
				c = 0;
			} else {
				i++;
				j--;
			}
		}
		return false;
	}
	
	public void paint(Graphics gOn) {
		int i, j;
		Image image = createImage(getWidth(), getHeight()); //creates image for double buffering
		Graphics g = image.getGraphics();	//make a graphics object that updates offscreen, everything updates this object now
		super.paint(g);
		for(i = 0; i < 7; i++) {
			for(j = 0; j < 6; j++) {
				drawPiece(g, boardPanels[i][j]);
			}
		}
		if(lowestPanel != null) {
			drawPiece(g, lowestPanel, FADE);
		}
		if(WIN)
			drawWinLine(g);
		gOn.drawImage(image, 0, 0, null);	//actual repaint of image on Frame
	}
	
	private void drawWinLine(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		myC4Panel firstWinner = boardPanels[WINNERS[0]][WINNERS[1]];
		myC4Panel secondWinner = boardPanels[WINNERS[6]][WINNERS[7]];
		int x1 = (int) (boardPanel.getX() + 8 + firstWinner.getX() + firstWinner
				.getWidth() * .5);					//aligns x axis to center of panel
		int y1 = (int) (boardPanel.getY() + 27 + firstWinner.getY() + firstWinner
				.getHeight() * .5);					//aligns y axis to center of panel
		int x2 = (int) (boardPanel.getX() + 8 + secondWinner.getX() + secondWinner
				.getWidth() * .5);					//aligns x axis to center of panel
		int y2 = (int) (boardPanel.getY() + 27 + secondWinner.getY() + secondWinner
				.getHeight() * .5);					//aligns y axis to center of panel
		radius = (int) (firstWinner.getWidth() < firstWinner.getHeight() ? firstWinner
				.getWidth() / 2.35 : firstWinner.getHeight() / 2.35);
		g2.setColor((TURN == COLOR1 ? new Color(COLOR1COLOR.getRed(),
				COLOR1COLOR.getGreen(), COLOR1COLOR.getBlue(), 194)
				: new Color(COLOR2COLOR.getRed(), COLOR2COLOR.getGreen(),
						COLOR2COLOR.getBlue(), 194)).darker());
		g2.setStroke(new BasicStroke(radius / 2, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g2.drawLine(x1, y1, x2, y2);
	}
	
	private void drawPiece(Graphics g, myC4Panel bp) {
		drawPiece(g, bp, NOFADE);
	}
	
	private void drawPiece(Graphics g, myC4Panel bp, boolean fadeStatus) {
		int x = (int) (boardPanel.getX() + 8 + bp.getX() + bp.getWidth() * .5);		//aligns x axis to center of panel
		int y = (int) (boardPanel.getY() + 27 + bp.getY() + bp.getHeight() * .5);	//aligns y axis to center of panel
		radius = (int) (bp.getWidth() < bp.getHeight() ? bp.getWidth() / 2.35
				: bp.getHeight() / 2.35);
		g.setColor(bp.getColor(fadeStatus));
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);//draws shape
		g.setColor(Color.BLACK);
		g.drawOval(x - radius, y - radius, radius * 2, radius * 2);//draws shape outline
	}
}