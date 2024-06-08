import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Quiz {
    private static final int TIME_LIMIT = 20; 

    private static class Question {
        String question;
        String[] options;
        char correctAnswer;

        Question(String question, String[] options, char correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    private static Question[] questions = {
        new Question("Who is present Governer of the Reserve Bank of India?", new String[]{"A. Ashwini Vaishnaw", "B. piyush Goyal", "C. Shakthikanta Das", "D. Amit Shah"}, 'C'),
        new Question("What is 9 + 8?", new String[]{"A. 10", "B. 17", "C. 12", "D. 19"}, 'B'),
        new Question("Who wrote 'Last Leaf Story'?", new String[]{"A. RabinranathTagore", "B. Shakespeare", "C. Hemingway", "D. O.Henry"}, 'D')
    };

    private static int score = 0;
    private static int currentQuestionIndex = 0;
    private static boolean answerSubmitted = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (Question question : questions) {
            displayQuestion(question);
            startTimer();

            answerSubmitted = false;
            while (!answerSubmitted) {
                if (scanner.hasNext()) {
                    char answer = scanner.next().charAt(0);
                    answerSubmitted = true;
                    checkAnswer(answer, question.correctAnswer);
                }
            }
        }
        displayResults();
        scanner.close();
    }

    private static void displayQuestion(Question question) {
        System.out.println(question.question);
        for (String option : question.options) {
            System.out.println(option);
        }
    }

    private static void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!answerSubmitted) {
                    System.out.println("Time's up!");
                    answerSubmitted = true;
                }
            }
        }, TIME_LIMIT * 1000);
    }

    private static void checkAnswer(char answer, char correctAnswer) {
        if (Character.toUpperCase(answer) == correctAnswer) {
            score++;
        }
    }

    private static void displayResults() {
        System.out.println("Quiz over!");
        System.out.println("Your score: " + score + " out of " + questions.length);
        System.out.println("Correct answers:");
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Q" + (i + 1) + ": " + questions[i].correctAnswer);
        }
    }
}

