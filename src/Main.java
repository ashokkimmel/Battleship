import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main{
    private static final int BOARD_SIZE = 10;
    private static final int TILE_SIZE = 50;
    private static final int PANEL_SIZE = 550;
    private static final int FRAME_WIDTH = 1114;
    private static final int FRAME_HEIGHT = 638;

    private Game myBattleship = new Game();
    private JLabel lastErrorMessage;
    private JLabel whoseMove;
    private CardLayout leftCardLayout, rightCardLayout;
    private JPanel leftBoard, rightBoard;
    private boolean gameOver = false;

    public Main() {
        initializeUIComponents();
        setupFrame();
        setupGamePanels();
    }

    private void initializeUIComponents() {
        lastErrorMessage = new JLabel();
        whoseMove = new JLabel("It is player 1's turn            ");
        leftCardLayout = new CardLayout();
        rightCardLayout = new CardLayout();
        leftBoard = new JPanel(leftCardLayout);
        rightBoard = new JPanel(rightCardLayout);
    }

    private void setupFrame() {
        JFrame myFrame = new JFrame();
        myFrame.setTitle("Battleship");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setupStatusPanel(myFrame);
        addPanelsToFrame(myFrame);
        myFrame.setVisible(true);
    }

    private void setupStatusPanel(JFrame myFrame) {
        lastErrorMessage.setBackground(Color.RED);
        JPanel statusPanel = new JPanel(new GridBagLayout());
        statusPanel.add(whoseMove);
        statusPanel.add(lastErrorMessage);
        statusPanel.setPreferredSize(new Dimension(FRAME_WIDTH, TILE_SIZE));
        myFrame.getContentPane().add(statusPanel, BorderLayout.SOUTH);
    }

    private void addPanelsToFrame(JFrame myFrame) {
        leftBoard.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        rightBoard.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        myFrame.getContentPane().add(leftBoard, BorderLayout.WEST);
        myFrame.getContentPane().add(rightBoard, BorderLayout.EAST);
    }

    private void setupGamePanels() {
        JPanel leftContainerPlayer1 = setupBoardContainer(true);
        JPanel leftContainerPlayer2 = setupBoardContainer(true);

        JPanel rightContainerPlayer1 = setupBoardContainer(false);
        addWhiteScreenButton();
        JPanel rightContainerPlayer2 = setupBoardContainer(false);
        addWhiteScreenButton();

        setupJLayeredPane(leftContainerPlayer1, true, true);
        setupJLayeredPane(leftContainerPlayer2, false, true);
        setupJLayeredPane(rightContainerPlayer1, true, false);
        setupJLayeredPane(rightContainerPlayer2, false, false);
    }

    private JPanel setupBoardContainer(boolean isLeft) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        panel.add(createTopBorder(), BorderLayout.NORTH);
        panel.add(createLeftBorder(), BorderLayout.WEST);
        if (isLeft) {
            leftBoard.add(panel);
        } else {
            rightBoard.add(panel);
        }
        return panel;
    }

    private JPanel createTopBorder() {
        JPanel topBorder = new JPanel(new GridLayout(1, BOARD_SIZE));
        JPanel cell;
        cell = createBorderedPanel();
        topBorder.add(cell);

        for (int i = 1; i <= BOARD_SIZE; i++) {
            cell = createBorderedPanel();
            cell.add(new JLabel(String.valueOf(i)));
            topBorder.add(cell);
        }
        return topBorder;
    }

    private JPanel createLeftBorder() {
        JPanel leftBorder = new JPanel(new GridLayout(BOARD_SIZE, 1));
        for (int i = 0; i < BOARD_SIZE; i++) {
            JPanel cell = createBorderedPanel();
            cell.add(new JLabel(Character.toString('A' + i)));
            leftBorder.add(cell);
        }
        return leftBorder;
    }

    private JPanel createBorderedPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY));
        panel.setBackground(Color.BLUE);
        panel.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
        return panel;
    }
   /* private JButton createGameButton() {
        JButton button = new JButton();
        button.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY));
        button.setBackground(Color.BLUE);
        button.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
        button.addActionListener(TileClickListener);
        return button;
    }*/

    private void addWhiteScreenButton() {
        JButton whiteScreenButton = new JButton("Press this after you turn the computer around.");
        whiteScreenButton.addActionListener(new WhiteScreenListener());
        rightBoard.add(whiteScreenButton);
    }

    private void setupJLayeredPane(JPanel container, boolean isPlayer1, boolean isAttacker) {
        JLayeredPane layeredPane = new JLayeredPane();
        if (isAttacker) {
            layeredPane.add(initializeGameBoard(isPlayer1), JLayeredPane.DEFAULT_LAYER);
            layeredPane.add(new AttackerShipPanel(
                    isPlayer1 ? myBattleship.pl1atk : myBattleship.pl2atk,
                    isPlayer1 ? myBattleship.pl2s : myBattleship.pl1s
            ), JLayeredPane.PALETTE_LAYER);
        } else {
            layeredPane.add(initializeGamePanel(), JLayeredPane.DEFAULT_LAYER);
            layeredPane.add(new DefenderShipPanel(
                    isPlayer1 ? myBattleship.pl2atk : myBattleship.pl1atk,
                    isPlayer1 ? myBattleship.pl1s : myBattleship.pl2s
            ), JLayeredPane.PALETTE_LAYER);
        }
        container.add(layeredPane, BorderLayout.CENTER);
    }
    private JButton createButton(boolean isPlayer1, int x, int y) {
        JButton button = new JButton();
        button.setBackground(Color.BLUE);
        button.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY));
        button.addActionListener(new TileClickListener(x, y, isPlayer1));
        return button;
    }

    private JPanel initializePanel(boolean isGameBoard, boolean isPlayer1) {
        JPanel panel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (isGameBoard) {
                    panel.add(createButton(isPlayer1, x, y));
                } else {
                    panel.add(createBorderedPanel());
                }
            }
        }
        panel.setBounds(0, 0, TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);
        return panel;
    }

    private JPanel initializeGameBoard(boolean isPlayer1) {
        return initializePanel(true, isPlayer1);
    }

    private JPanel initializeGamePanel() {
        return initializePanel(false, false);
    }

    private class TileClickListener implements ActionListener {
        private final int x, y;
        private final boolean isPlayer1Turn;

        public TileClickListener(int x, int y, boolean isPlayer1Turn) {
            this.x = x;
            this.y = y;
            this.isPlayer1Turn = isPlayer1Turn;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver) return;
            try {
                lastErrorMessage.setText("");
                if (myBattleship.attack(new Posn(x, y), isPlayer1Turn)) {
                    whoseMove.setText("Player " + (myBattleship.isPl1w() ? 1 : 2) + " won!");
                    whoseMove.setBackground(Color.GREEN);
                    whoseMove.setOpaque(true);
                    whoseMove.setFont(new Font("Serif", Font.BOLD, 40));
                    gameOver = true;
                }
                if (!gameOver) {
                    whoseMove.setText("It is player " + (myBattleship.isPl1t() ? 1 : 2) + "'s turn            ");
                }
                rightCardLayout.next(rightBoard);
                leftBoard.repaint();
            } catch (Exception ex) {
                lastErrorMessage.setText(ex.getMessage());
                lastErrorMessage.setBackground(Color.YELLOW);
                lastErrorMessage.setForeground(Color.RED);
                lastErrorMessage.setOpaque(true);
            }
        }
    }

    private class WhiteScreenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver) return;
            leftCardLayout.next(leftBoard);
            rightCardLayout.next(rightBoard);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
