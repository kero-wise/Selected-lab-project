class MultipleChoiceQuestion implements Question {
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctOption;

    public MultipleChoiceQuestion(String questionText, String option1, String option2, String option3, String option4, int correctOption) {
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public void display() {
        System.out.println("Multiple Choice: " + questionText);
        System.out.println("1. " + option1);
        System.out.println("2. " + option2);
        System.out.println("3. " + option3);
        System.out.println("4. " + option4);
    }

    @Override
    public Question clone() {
        return new MultipleChoiceQuestion(this.questionText, this.option1, this.option2, this.option3, this.option4, this.correctOption);  // نسخ الكائن
    }
}
