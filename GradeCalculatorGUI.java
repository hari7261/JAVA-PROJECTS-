import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeCalculatorGUI extends JFrame {

    private JTextField[] subjectFields;
    private JLabel totalMarksLabel;
    private JLabel averagePercentageLabel;
    private JLabel gradeLabel;

    public GradeCalculatorGUI() {
        // Set up the main frame
        setTitle("Grade Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));
        getContentPane().setBackground(new Color(64,  64, 64)); // Set background color to black

        // Create input fields for subjects
        JLabel[] subjectLabels = new JLabel[5];
        subjectFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            subjectLabels[i] = new JLabel("Subject " + (i + 1) + ":");
            subjectFields[i] = new JTextField();
            subjectLabels[i].setForeground(new Color(255, 255, 255)); // Set label text color to white
            subjectFields[i].setBackground(new Color(255, 255, 255)); // Set text field background color to white
            add(subjectLabels[i]);
            add(subjectFields[i]);
        }

        // Create buttons
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBackground(new Color(100, 149, 237)); // Set button background color
        calculateButton.setForeground(Color.WHITE); // Set button text color
        add(calculateButton);

        // Create labels for displaying results
        totalMarksLabel = new JLabel("Total Marks: ");
        averagePercentageLabel = new JLabel("Average Percentage: ");
        gradeLabel = new JLabel("Grade: ");
        totalMarksLabel.setForeground(new Color(255, 255, 255)); // Set label text color to white
        averagePercentageLabel.setForeground(new Color(255, 255, 255)); // Set label text color to white
        gradeLabel.setForeground(new Color(255, 255, 255)); // Set label text color to white
        add(totalMarksLabel);
        add(averagePercentageLabel);
        add(gradeLabel);

        // Add action listener to the calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResults();
            }
        });
    }

    private void calculateResults() {
        int totalMarks = 0;
        int totalSubjects = subjectFields.length;

        // Calculate total marks
        for (int i = 0; i < totalSubjects; i++) {
            try {
                int marks = Integer.parseInt(subjectFields[i].getText());
                totalMarks += marks;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid marks for all subjects.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Calculate average percentage
        double averagePercentage = (double) totalMarks / totalSubjects;

        // Determine the grade based on average percentage
        String grade;
        if (averagePercentage == 100) {
            grade = "O";
        }else if (averagePercentage >= 90) {
            grade = "A";
        } else if (averagePercentage >= 80) {
            grade = "B";
        } else if (averagePercentage >= 70) {
            grade = "C";
        } else if (averagePercentage >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }

        // Display results
        totalMarksLabel.setText("Total Marks: " + totalMarks);
        averagePercentageLabel.setText("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
        gradeLabel.setText("Grade: " + grade);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GradeCalculatorGUI().setVisible(true);
            }
        });
    }
}
