package uk.ac.ncl.daniel.baranowski.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HelpConstants {
    public static final String SETUP_EXAM_PAPER = "The latest version of one of the existing papers. Papers can be created in the Create section. All versions of all papers can be browsed in Browse section.";
    public static final String SETUP_EXAM_MODULE = "A module of which your are a module leader. All students in this module will be able to take this exam.";
    public static final String SETUP_EXAM_DATE = "Date on which the exam is supposed to take place.";
    public static final String SETUP_EXAM_START_TIME = "Start time of the exam. Exam will not start automatically. Privileged user will need to press start.";
    public static final String SETUP_EXAM_LOCATION = "Venue in which the exam will take place.";
    public static final String SETUP_EXAM_TIME_ALLOWED = "Amount of time in minutes the candidates will have to complete exam. 50% extra time will automatically be granted to candidates who require it.";

    public static final String CREATE_QUESTION_LANGUAGE = "If a question is related to any programming language, this field should be populated with the name of that programming language";
    public static final String CREATE_QUESTION_REFERENCE_NAME = "The name of the question that will be visible when browsing existing questions in the Browse section. It won't be presented to candidates taking the exam.";
    public static final String CREATE_QUESTION_MAX_MARK = "Maximum mark that can be awarded for answering this question correctly.";
    public static final String CREATE_QUESTION_PROPOSED_TIME_SCALE = "Time scale in minutes which will be used to calculate proposed time scale of a paper.";
    public static final String CREATE_QUESTION_TYPE_CODE = "Gives the candidate a text area with simple code highlighting support. Does not support auto marking.";
    public static final String CREATE_QUESTION_TYPE_ESSAY = "Gives the candidate a text area to support long answers. Does not support auto marking.";
    public static final String CREATE_QUESTION_MULTIPLE_CHOICE = "Gives a candidate a choice of possible answers. Auto marking wizard will look at max mark and match an upper case letter followed by ')' in question text. You can use the update button to refresh the wizard if either has changed.";
    public static final String CREATE_QUESTION_DRAWING = "Gives a candidate a simple computer graphics app. Does not support auto marking.";
    public static final String CREATE_QUESTION_EXPRESISON = "Asks the candidate to fill in up to 9 text boxes. Auto marking wizard match a digit from 1 to 9 surrounded by '[[' ']]'. Then you'll be able to add any number of possible answers for each of the blanks. The wizard check boxes are used to generate regex. White Space Collapsing: Multiple white spaces are treated as single. Alternative Punctuation: punctuation in the answer can be treated as any other punctuation mark or single white space. Case Insensitive: answer will be case insensitive. Alternatively you can write your own regex by pressing customise regex button.";
    public static final String CREATE_QUESTION_QUESTION_TEXT = "The question text that will be displayed to the candidate. It uses editor with styling support. In order to enable code highlighting select a paragraph of text and press the Hashtag icon on the editor toolbar. If question type is 'expression', a digit surrounded by double square brackets eg. [[1]]] will be interpreted as a blank to fill by candidate. If question type is 'multiple choice' upper case letter followed by a round bracket eg. A) will be interpreted as possible answer.";
    public static final String CREATE_QUESTION_MARKING_GUIDE = "The marking guide will be displayed to the marker to help them pick the correct mark. It uses editor which styling support. In order to enable code highlighting select a paragraph of text and press the Hashtag icon on the editor toolbar.";
    public static final String CREATE_QUESTION_TYPE_COMBINED = "Code: " + CREATE_QUESTION_TYPE_CODE + "\nEssay: " + CREATE_QUESTION_TYPE_ESSAY + "\nMultiple Choice: " + CREATE_QUESTION_MULTIPLE_CHOICE + "\nDrawing: " + CREATE_QUESTION_DRAWING + "\nExpression: " + CREATE_QUESTION_EXPRESISON;
    public static final String AUTO_MARKING_MULTIPLE_CHOICE = "This wizard lets you set up automatic marking for multiple choice questions. Table will contain rows from 0 to max mark. Each row contains checkboxes for each of the possible answers found in question text. To add new answers press the 'ABC' icon on the question text toolbar.";
    public static final String AUTO_MARKING_MULTIPLE_CHOICE_REFRESH = "This button updates the wizard to reflect current question state. Changes in question text and max mark will be reflected in the wizard once refreshed.";
    public static final String AUTO_MARKING_EXPRESSION = "This wizard lets you set up automatic marking for expression questions.";
    public static final String AUTO_MARKING_EXPRESSION_ANSWER = "The answer you expect your candidates to give to receive the mark when auto-marking.";
    public static final String AUTO_MARKING_EXPRESSION_MARK = "The mark you want to give for this answer, when auto-marking.";
    public static final String AUTO_MARKING_EXPRESSION_REGEX = "The regex you want the automaker to use when matching candidates answers for this mark. It has to be Java compatible";
    public static final String AUTO_MARKING_EXPRESSION_OPTION_WHITE_SPACE = "With this option enabled auto-marker will treat multiple consecutive spaces as one when matching candidates answers..";
    public static final String AUTO_MARKING_EXPRESSION_OPTION_ALTERNATIVE_PUNCTUATION = "With this option enabled auto-marker will treat [,.:;/'|] punctuation interchangeably.";
    public static final String AUTO_MARKING_EXPRESSION_OPTION_CASE_INSENSITIVE = "With this option enabled auto-marker will ignore the case of answers.";
    public static final String AUTO_MARKING_EXPRESSION_CUSTOM_REGEX = "When you press this button you will disable pre-made options and enable witting your own custom regex instead.";
    public static final String AUTO_MARKING_EXPRESSION_ADD = "Press to add another possible answer to the marking wizard.";
    public static final String AUTO_MARKING_EXPRESSION_UPADTE = "If you have changed the question text to contain more blanks ([[1-9]]) press this button to refresh the wizard.";
    public static final String AUTO_MARKING_EXPRESSION_REMOVE = "Removes this row from auto marked answers. WARNING, can't be reverted.";
    public static final String AUTO_MARKING_EXPRESSION_BLANK_NO = "If you have blanks (eg. [[1]]) in the question text you will be able to specify which blank this answer relates to. ";

    public static final String CREATE_SECTION_REFERENCE_NAME = "The name of the section that will be visible when browsing existing section in the Browse section.  It will also be presented to candidates taking the exam.";
    public static final String CREATE_SECTION_NO_OF_QUESTIONS = "Sometimes candidates won't be required to answer all questions presented in a section. Pick a number equal to number of questions candidates are required to answer in this section.";
    public static final String CREATE_SECTION_TIME_TO_COMPLETE = "Time scale in minutes which will be used to calculate proposed time scale of a test paper. It will be shown to candidates.";
    public static final String CREATE_SECTION_INSTRUCTIONS_TEXT = "The section instructions text will be presented to candidates at the beginning of the section. It uses editor which styling support. In order to enable code highlighting select a paragraph of text and press the Hashtag icon on the editor toolbar.";
    public static final String CREATE_SECTION_SECTION_QUESTIONS = "Once the section is created, Questions can be added to it. Questions can be reordered by dragging their number up or down. The order is the order in which candidates will see the sections.";
    public static final String CREATE_SECTION_AVAILABLE_QUESTIONS = "Existing questions. New questions can be created in the Create sections of the app.";
    public static final String CREATE_SECTION_SECTION_QUESTION_ORDER = "By dragging the rows up and down, you can reorder the questions withing section.";

    public static final String CREATE_PAPER_REFERENCE_NAME = "The name of the paper that will be visible when browsing existing questions in the Browse section.  It will also be presented to candidates taking the exam.";
    public static final String CREATE_PAPER_TIME_TO_COMPLETE = "Time scale in minutes which will be used to propose the time allowed for the exam. The paper proposed time can be overridden for each exam.";
    public static final String CREATE_PAPER_INSTRUCTIONS_TEXT = "The paper instructions text will be presented to candidates at the beginning of the section. It uses editor with styling support. In order to enable code highlighting select a paragraph of text and press the Hashtag icon on the editor toolbar.";
    public static final String CREATE_PAPER_AVAILABLE_SECTIONS = "Existing sections. New sections can be created in the Create sections of the app.";
    public static final String CREATE_PAPER_PAPER_SECTION_ORDER = "By dragging the rows up and down, you can reorder the sections withing paper.";


    public static final String CREATE_PAPER_SECTION_QUESTIONS = "Once paper has been created, sections can be added to it. Sections can be reordered by dragging their number up or down.";

    public static final String NAVBAR_SETUP_EXAM = "Setup an exam, for students who are enrolled on the module chosen from amongst the modules you are a leader of. Modules have been hard coded for the purpose of this demo.";
    public static final String NAVBAR_BROWSE = "Browse existing content, such as past papers and questions, as well as check status of exams and mark them.";
    public static final String NAVBAR_CREATE = "Design new questions and arrange them into sections and papers.";
    public static final String NAVBAR_HELP = "Read complete system help all in one place.";

    public static final String MARKING_GUIDE = "Press here to show the marking guide for this question.";

    public static final String MARKING_ATTEMPT_FINNISH = "Set this attempt to marked. You will not be able to come back to it later.";
    public static final String MARKING_ATTEMPT_UNLOCK = "Unlock this attempt, so you or other privileged user can come back to mark it later.";

    public static final String MARKING_EXAM_FINNISH = "Set all attempts in this exam to marked. You will not be able to come back to it later.";
    public static final String MARKING_EXAM_UNLOCK = "Unlock all attempts in this exam, so you or other privileged user can come back to mark them later.";
    public static final String MARKING_EXAM_DONUTS = "This donut chart represents the ration of answers marked by people to these who were marked automatically and not marked at all. Blue means marked automatically, yellow is not marked and green is marked by someone.";
    public static final String MARKING_EXAM_VIEW_FULL = "Press this to see the full answer. It will open in new tab. All changes will automatically synchronise between open tabs";

    public static Map<String, String> getHelpFields() {
        HashMap<String,String> result = new HashMap<>();

        for (Field field : HelpConstants.class.getDeclaredFields()) {
            try {
                result.put(field.getName(), String.valueOf(field.get(null)));
            } catch (IllegalAccessException e) {
                continue;
            }
        }

        return result;
    }
}
