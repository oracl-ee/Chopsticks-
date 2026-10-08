
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TwoPlayersVsAIGame extends JFrame {
    private int[] human1 = {1, 1};
    private int[] human2 = {1, 1};
    private int[] ai = {1, 1};
    private boolean isHuman1Turn = true;

    private JTextField[] human1Fields = new JTextField[2];
    private JTextField[] human2Fields = new JTextField[2];
    private JTextField[] aiFields = new JTextField[2];
    private JButton attackButton;

    public TwoPlayersVsAIGame() {
        setTitle("Chopsticks - Two Players vs AI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        setupGUIPanels();
        updateDisplay();
        setVisible(true);
    }

    private void setupGUIPanels() {
        // AI Top Panel
        JPanel aiPanel = new JPanel(new GridLayout(1, 2));
        aiPanel.setBorder(BorderFactory.createTitledBorder("AI"));
        for (int i = 0; i < 2; i++) {
            aiFields[i] = createHandField(ai[i], 40);
            aiPanel.add(aiFields[i]);
        }

        // Human Players Bottom Panel
        JPanel playersPanel = new JPanel(new GridLayout(2, 2));
        playersPanel.setBorder(BorderFactory.createTitledBorder("Players"));
        for (int i = 0; i < 2; i++) {
            human1Fields[i] = createHandField(human1[i], 20);
            playersPanel.add(human1Fields[i]);
        }
        for (int i = 0; i < 2; i++) {
            human2Fields[i] = createHandField(human2[i], 20);
            playersPanel.add(human2Fields[i]);
        }

        // Attack Button
        attackButton = new JButton("Attack");
        attackButton.addActionListener(new AttackListener());

        add(aiPanel, BorderLayout.NORTH);
        add(playersPanel, BorderLayout.CENTER);
        add(attackButton, BorderLayout.SOUTH);
    }

    private JTextField createHandField(int value, int fontSize) {
        JTextField field = new JTextField(String.valueOf(value));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setFont(new Font("Arial", Font.BOLD, fontSize));
        field.setEditable(false);
        return field;
    }

    private void updateDisplay() {
        for (int i = 0; i < 2; i++) {
            human1Fields[i].setText(human1[i] >= 5 ? "0" : String.valueOf(human1[i]));
            human2Fields[i].setText(human2[i] >= 5 ? "0" : String.valueOf(human2[i]));
            aiFields[i].setText(ai[i] >= 5 ? "0" : String.valueOf(ai[i]));
        }
    }

    private class AttackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (isGameOver(ai)) {
                JOptionPane.showMessageDialog(null, "Game over! AI is already defeated.");
                return;
            }

            if (isGameOver(human1) && isGameOver(human2)) {
                JOptionPane.showMessageDialog(null, "Game over! Both human players are defeated.");
                return;
            }

            // Human player's turn
            if (isHuman1Turn && !isGameOver(human1)) {
                performAttack(human1, ai);
            } else if (!isHuman1Turn && !isGameOver(human2)) {
                performAttack(human2, ai);
            }

            if (isGameOver(ai)) {
                updateDisplay();
                JOptionPane.showMessageDialog(null, (isHuman1Turn ? "Human 1" : "Human 2") + " wins!");
                resetGame();
                return;
            }

            isHuman1Turn = !isHuman1Turn;
            updateDisplay();

            // AI's turn
            performAITurn();
            updateDisplay();

            if (isGameOver(human1) && isGameOver(human2)) {
                JOptionPane.showMessageDialog(null, "AI wins!");
                resetGame();
            }
        }
    }

    private void performAttack(int[] attacker, int[] defender) {
        int attackingHand = attacker[0] > 0 ? 0 : 1;
        int targetHand = defender[0] >= defender[1] ? 0 : 1;

        if (attacker[attackingHand] == 0 || defender[targetHand] == 0) return;

        defender[targetHand] = (defender[targetHand] + attacker[attackingHand]) % 5;
    }

    private void performAITurn() {
        int attackingHand = ai[0] >= ai[1] && ai[0] > 0 ? 0 : (ai[1] > 0 ? 1 : 0);
        int[] bestTarget = null;
        int targetPlayer = -1, targetHand = -1;

        // Search best hand (most fingers) among humans
        if (!isGameOver(human1)) {
            for (int i = 0; i < 2; i++) {
                if (human1[i] > 0 && (bestTarget == null || human1[i] > bestTarget[targetHand])) {
                    bestTarget = human1;
                    targetHand = i;
                    targetPlayer = 1;
                }
            }
        }

        if (!isGameOver(human2)) {
            for (int i = 0; i < 2; i++) {
                if (human2[i] > 0 && (bestTarget == null || human2[i] > bestTarget[targetHand])) {
                    bestTarget = human2;
                    targetHand = i;
                    targetPlayer = 2;
                }
            }
        }

        if (bestTarget != null && ai[attackingHand] > 0) {
            bestTarget[targetHand] = (bestTarget[targetHand] + ai[attackingHand]) % 5;
        }
    }

    private boolean isGameOver(int[] hands) {
        return hands[0] == 0 && hands[1] == 0;
    }

    private void resetGame() {
        human1 = new int[]{1, 1};
        human2 = new int[]{1, 1};
        ai = new int[]{1, 1};
        isHuman1Turn = true;
        updateDisplay();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TwoPlayersVsAIGame::new);
    }
}
