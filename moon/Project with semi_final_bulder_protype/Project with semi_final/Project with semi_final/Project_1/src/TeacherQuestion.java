public class TeacherQuestion {
    private int id;  // إضافة id للتماشي مع قاعدة البيانات
    private String questionText;  // تغيير الاسم ليتناسب مع قاعدة البيانات
    private String option1, option2, option3, option4;
    private int correctOption;

    // المُنشئ مع استخدام الأسماء الجديدة
    public TeacherQuestion(int id, String questionText, String option1, String option2, String option3, String option4, int correctOption) {
        this.id = id;  // تعيين id
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;
    }

    TeacherQuestion(String questionText, String option1, String option2, String option3, String option4, int correctOption) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // تعريف getters لاستخدام الأسماء الجديدة
    public int getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}
