package com.example.android.horiavideoprototype;

import android.support.annotation.StringRes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoiceCollection {

  public enum Questions {
    FRENCH_WOMAN(1, R.raw.nested_sequence_2111, R.string.french_woman_question_one,
        R.string.french_woman_question_two, R.string.french_woman_question_three,
        R.string.french_woman_question_four, R.string.french_woman_question_answer),

    FRENCH_MAN(2, R.raw.guy, R.string.french_man_question_one, R.string.french_man_question_two,
        R.string.french_man_question_three, R.string.french_man_question_four,
        R.string.french_man_question_answer),

    MAKO(3, R.raw.mako, R.string.mako_question_one, R.string.mako_question_two,
        R.string.mako_question_three, R.string.mako_question_four, R.string.mako_question_answer),

    EMI(4, R.raw.emi, R.string.emi_question_one, R.string.emi_question_two,
        R.string.emi_question_three, R.string.emi_question_four, R.string.emi_question_answer),

    JUN(5, R.raw.jun, R.string.jun_question_one, R.string.jun_question_two,
        R.string.jun_question_three, R.string.jun_question_four, R.string.jun_question_answer),

    ENA(6, R.raw.ena, R.string.ena_question_one, R.string.ena_question_two,
        R.string.ena_question_three, R.string.ena_question_four, R.string.ena_question_answer);

    private int id;
    private int videoFile;
    @StringRes final int firstChoice;
    @StringRes final int secondChoice;
    @StringRes final int thirdChoice;
    @StringRes final int fourthChoice;
    @StringRes final int answer;

    Questions(int id, int videoFile, @StringRes int firstChoice, @StringRes int secondChoice,
        @StringRes int threeChoice, int fourthChoice, @StringRes int answer) {
      this.id = id;
      this.videoFile = videoFile;
      this.firstChoice = firstChoice;
      this.secondChoice = secondChoice;
      this.thirdChoice = threeChoice;
      this.fourthChoice = fourthChoice;
      this.answer = answer;
    }

    public int getId() {
      return id;
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

    public int getThirdChoice() {
      return thirdChoice;
    }

    public int getFourthChoice() {
      return fourthChoice;
    }

    public int getAnswer() {
      return answer;
    }
  }

  public static List<Questions> getQuestionsCollection() {
    return new ArrayList<>(Arrays.asList(Questions.values()));
  }
}
