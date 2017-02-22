package uk.ac.ncl.daniel.baranowski.data.access;

enum TableNames {
    ANSWER("Answer"),
    ANSWER_ASSET("AnswerAsset"),
    CANDIDATE("Candidate"),
    MARK("Mark"),
    QUESTION("Question"),
    QUESTION_TYPE("QuestionType"),
    QUESTION_VERSION("QuestionVersion"),
    QUESTION_VERSION_ASSET("QuestionVersionAsset"),
    QUESTION_VERSION_ENTRY("QuestionVersionEntry"),
    ROLE("Role"),
    TEST_DAY("TestDay"),
    TEST_DAY_ENTRY("TestDayEntry"),
    TEST_PAPER("TestPaper"),
    TEST_PAPER_VERSION("TestPaperVersion"),
    TEST_PAPER_SECTION("TestPaperSection"),
    TEST_PAPER_SECTION_VERSION("TestPaperSectionVersion"),
    TEST_PAPER_SECTION_VERSION_ENTRY("TestPaperSectionVersionEntry"),
    USER("User"),
    USERROLE("UserRole"),
    TERMS_AND_CONDITIONS("TermsAndConditions"),
    MODULE("Module"),
    MODULE_LEADER("ModuleLeader"),
    EXAM("Exam"),
    CANDIDATE_MODULE("CandidateModule"),
    CANDIDATE_CREDENTIALS("ExamCandidateCredentials");


    private final String tableName;

    TableNames(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return tableName;
    }
}
