package com.example.android.horiavideoprototype;

import android.support.annotation.StringRes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoiceCollection {

  public enum Questions {
    FRENCH_WOMAN(R.raw.nested_sequence_2111, R.string.french_woman_question_one,
        R.string.french_woman_question_two, R.string.french_woman_question_three,
        R.string.french_woman_question_answer),

    FRENCH_MAN(R.raw.guy, R.string.french_man_question_one, R.string.french_man_question_two,
        R.string.french_man_question_three, R.string.french_man_question_answer),

    MAKO(R.raw.mako, R.string.mako_question_one, R.string.mako_question_two,
        R.string.mako_question_three, R.string.mako_question_answer);

    private int videoFile;
    @StringRes final int firstChoice;
    @StringRes final int secondChoice;
    @StringRes final int threeChoice;
    @StringRes final int answer;

    Questions(int videoFile, @StringRes int firstChoice, @StringRes int secondChoice,
        @StringRes int threeChoice, @StringRes int answer) {
      this.videoFile = videoFile;
      this.firstChoice = firstChoice;
      this.secondChoice = secondChoice;
      this.threeChoice = threeChoice;
      this.answer = answer;
    }

    public int getVideoFile() {
      return videoFile;
    }

    public int getFirstChoice() {
      return firstChoice;
    }

    public int getSecondChoice() {
      return secondChoice;
    }

    public int getThreeChoice() {
      return threeChoice;
    }

    public int getAnswer() {
      return answer;
    }
  }

  public static List<Questions> getQuestionsCollection() {
    return new ArrayList<>(Arrays.asList(Questions.values()));
  }
  
}
