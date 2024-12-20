class TrueFalseQuestion implements Question {
    private String questionText;
    private boolean correctAnswer;

    public TrueFalseQuestion(String questionText, boolean correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public void display() {
        System.out.println("True/False: " + questionText);
    }

    @Override
    public Question clone() {
        return new TrueFalseQuestion(this.questionText, this.correctAnswer);  // نسخ الكائن
    }
}

