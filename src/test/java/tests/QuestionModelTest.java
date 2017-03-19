package tests;

import org.junit.Test;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionModelTest {
    @Test
    public void canGetAnswerLetters() {
        QuestionModel question = new QuestionModel();
        question.setId(15);
        question.setVersionNo(1);
        question.setLanguage("C#");
        question.setTimeScale(1);
        question.setReferenceName("Access a private variable");
        question.setType("Multiple Choice");
        question.setText("Which classes can access a variable declared as private? </br> A) All classes. </br> B) Within the body of the class that encloses the declaration. </br> C)Inheriting sub classes. </br> D) Classes in the same namespace.");
        question.setCorrectAnswer("B");

        List<String> expected = Arrays.asList("A", "B", "C", "D");
        List<String> actual = question.getAnswerLetters();
        assertEquals(expected, actual);
    }
}
