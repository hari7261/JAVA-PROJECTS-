import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModernQuizApp extends JFrame {
    private int currentQuestionIndex = 0;
    private int score = 0;

    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup buttonGroup;
    private JButton submitButton;
    private JProgressBar progressBar;
    private Timer timer;

    private String[][] quizData = {
            {"What is the capital of France?", "Berlin", "Madrid", "Paris", "Rome", "Paris"},
            {"Which planet is known as the Red Planet?", "Mars", "Jupiter", "Venus", "Saturn", "Mars"},
            {"Who wrote 'Romeo and Juliet'?", "Charles Dickens", "Jane Austen", "William Shakespeare", "Mark Twain", "William Shakespeare"}
    };

    public ModernQuizApp() {
        setTitle("Modern Quiz App");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeComponents();

        showNextQuestion();

        setVisible(true);
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        questionLabel = new JLabel();
        mainPanel.add(questionLabel);

        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            buttonGroup.add(optionButtons[i]);
            mainPanel.add(optionButtons[i]);
        }

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });
        mainPanel.add(submitButton);

        progressBar = new JProgressBar(0, 100);
        mainPanel.add(progressBar);

        add(mainPanel, BorderLayout.CENTER);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < quizData.length) {
            questionLabel.setText(quizData[currentQuestionIndex][0]);

            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(quizData[currentQuestionIndex][i + 1]);
                optionButtons[i].setSelected(false);
                optionButtons[i].setEnabled(true);
            }

            timer.restart();
            progressBar.setValue(100);
        } else {
            showResult();
        }
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this, "Quiz completed!\nYour score: " + score, "Result", JOptionPane.INFORMATION_MESSAGE);

        if (score == quizData.length) {
            showCelebration();
        } else {
            System.exit(0);
        }
    }

    private void checkAnswer() {
        timer.stop();

        for (int i = 0; i < 4; i++) {
            optionButtons[i].setEnabled(false);
        }

        String userAnswer = getSelectedAnswer();
        String correctAnswer = quizData[currentQuestionIndex][5];

        if (userAnswer.equals(correctAnswer)) {
            score++;
            JOptionPane.showMessageDialog(this, "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect!\nCorrect Answer: " + correctAnswer, "Result", JOptionPane.ERROR_MESSAGE);
        }

        currentQuestionIndex++;
        showNextQuestion();
    }

    private void updateTime() {
        if (progressBar.getValue() > 0) {
            progressBar.setValue(progressBar.getValue() - 1);
        } else {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Time's up!", "Result", JOptionPane.INFORMATION_MESSAGE);
            showNextQuestion();
        }
    }

    private String getSelectedAnswer() {
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                return optionButtons[i].getText();
            }
        }
        return "";
    }

    private void showCelebration() {
        JFrame celebrationFrame = new JFrame("Celebration!");
        celebrationFrame.setSize(600, 400);
        celebrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel celebrationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCelebration((Graphics2D) g);
            }
        };

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                celebrationFrame.dispose();
                System.exit(0);
            }
        });

        celebrationFrame.add(celebrationPanel, BorderLayout.CENTER);
        celebrationFrame.add(closeButton, BorderLayout.SOUTH);

        celebrationFrame.setLocationRelativeTo(null);
        celebrationFrame.setVisible(true);
    }

    private void drawCelebration(Graphics2D g2d) {
        // Customize the celebration graphics
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        draw3DText(g2d, "Congratulations!", 150, 100, Color.RED);
        draw3DText(g2d, "You got all answers correct!", 100, 200, Color.GREEN);

        // Add more colorful graphics or animations as desired
        drawFireworks(g2d, 300, 300, 50, 5); // Example: Fireworks animation
    }

    private void draw3DText(Graphics2D g2d, String text, int x, int y, Color color) {
        Font font = new Font("Arial", Font.BOLD, 50);
        FontMetrics metrics = g2d.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();

        // Draw 3D effect
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(text, x - 2, y - 2);
        g2d.drawString(text, x + 2, y + 2);
        g2d.drawString(text, x, y);
    }

    private void drawFireworks(Graphics2D g2d, int centerX, int centerY, int size, int numParticles) {
        for (int i = 0; i < numParticles; i++) {
            int angle = (int) (Math.random() * 360);
            int distance = (int) (Math.random() * size);
            int particleX = centerX + (int) (distance * Math.cos(Math.toRadians(angle)));
            int particleY = centerY + (int) (distance * Math.sin(Math.toRadians(angle)));

            Color randomColor = new Color(
                    (int) (Math.random() * 256),
                    (int) (Math.random() * 256),
                    (int) (Math.random() * 256)
            );

            g2d.setColor(randomColor);
            g2d.fillOval(particleX, particleY, 5, 5);
        }
    }
}

class QuizAppRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ModernQuizApp().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}