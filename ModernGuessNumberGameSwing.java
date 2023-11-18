import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ModernGuessNumberGameSwing extends JFrame {
    private int lowerBound = 1;
    private int upperBound = 100;
    private int targetNumber;
    private int maxAttempts;
    private int attemptsLeft;
    private int roundsWon;

    private JTextField guessField;
    private JLabel resultLabel;
    private JLabel attemptsLabel;
    private JButton guessButton;
    private JButton playAgainButton;
    private JComboBox<String> difficultyComboBox;

    public ModernGuessNumberGameSwing() {
        setTitle("Modern Guess the Number Game");

        // Layout
        setLayout(new BorderLayout());
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        initializeGame();

        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel difficultyLabel = new JLabel("Difficulty:");
        difficultyComboBox = new JComboBox<>();
        difficultyComboBox.addItem("Easy");
        difficultyComboBox.addItem("Medium");
        difficultyComboBox.addItem("Hard");
        difficultyComboBox.setSelectedItem("Medium");

        difficultyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDifficulty();
            }
        });

        topPanel.add(difficultyLabel);
        topPanel.add(difficultyComboBox);

        return topPanel;
    }

    private void setDifficulty() {
        switch ((String) difficultyComboBox.getSelectedItem()) {
            case "Easy":
                maxAttempts = 15;
                break;
            case "Medium":
                maxAttempts = 10;
                break;
            case "Hard":
                maxAttempts = 5;
                break;
        }
        initializeGame();
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        guessField = new JTextField();
        guessField.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessField.setMaximumSize(new Dimension(200, 30));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.setToolTipText("Enter your guess");

        resultLabel = new JLabel("Take a guess!");
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        attemptsLabel = new JLabel("Attempts left: " + attemptsLeft);
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        guessButton = new JButton("Guess");
        guessButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        playAgainButton = new JButton("Play Again");
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewRound();
            }
        });

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(guessField);
        centerPanel.add(resultLabel);
        centerPanel.add(attemptsLabel);
        centerPanel.add(guessButton);
        centerPanel.add(playAgainButton);

        return centerPanel;
    }

    private void initializeGame() {
        lowerBound = 1;
        upperBound = 100;
        targetNumber = generateRandomNumber();
        attemptsLeft = maxAttempts;
        updateAttemptsLabel();
        resultLabel.setText("Take a guess!");
        playAgainButton.setEnabled(false);
    }

    private int generateRandomNumber() {
        return new Random().nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    private void checkGuess() {
        try {
            int userGuess = Integer.parseInt(guessField.getText());

            if (userGuess == targetNumber) {
                handleCorrectGuess();
            } else {
                handleIncorrectGuess();
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter a valid number.");
        }
    }

    private void handleCorrectGuess() {
        resultLabel.setText("Congratulations! You guessed the number.");
        roundsWon++;
        playAgainButton.setEnabled(true);
        guessButton.setEnabled(false);
    }

    private void handleIncorrectGuess() {
        attemptsLeft--;

        if (attemptsLeft > 0) {
            resultLabel.setText("Wrong guess! Try again.");
        } else {
            resultLabel.setText("Sorry, you ran out of attempts. The correct number was: " + targetNumber);
            playAgainButton.setEnabled(true);
            guessButton.setEnabled(false);
        }

        updateAttemptsLabel();
    }

    private void updateAttemptsLabel() {
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
    }

    private void startNewRound() {
        initializeGame();
        guessField.setText("");
        guessButton.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ModernGuessNumberGameSwing();
            }
        });
    }
}
