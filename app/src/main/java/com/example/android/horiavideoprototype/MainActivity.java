package com.example.android.horiavideoprototype;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import java.util.List;

public class MainActivity extends BaseActivity {

  @BindView(R.id.layout) FrameLayout layout;
  @BindView(R.id.playVideoMode) TextView playVideoMode;
  @BindView(R.id.mainLayout) LinearLayout mainLayout;
  @BindView(R.id.exoplayer) PlayerView playerView;
  @BindView(R.id.choice1) AppCompatTextView choiceOneTextView;
  @BindView(R.id.choice2) AppCompatTextView choiceTwoTextView;
  @BindView(R.id.choice3) AppCompatTextView choiceThreeTextView;
  @BindView(R.id.choice4) AppCompatTextView choiceFourTextView;
  @BindView(R.id.playButton) ImageView playButton;

  private SimpleExoPlayer exoPlayer;
  private int count = 1;
  private int currentWindow;
  private Long playbackPosition = 0L;
  private boolean playWhenReady = true;
  private Player player;
  private int videoFile;
  private int answer;

  List<ChoiceCollection.Questions> questions = ChoiceCollection.getQuestionsCollection();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this, getViewGroup());
    View decorView = getWindow().getDecorView();
    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
    decorView.setSystemUiVisibility(uiOptions);
    ActionBar actionBar = getSupportActionBar();
    actionBar.hide();

    getChoices();

    playVideoMode.setOnClickListener(v -> {
      playVideoMode.setVisibility(View.GONE);
      layout.setVisibility(View.VISIBLE);
      playerView.setVisibility(View.VISIBLE);
      initialisePlayer(getMyVideoFile(videoFile));
    });
  }

  private void getChoices() {
    if (count < 7) {
      for (ChoiceCollection.Questions question : questions) {
        setChoices(question);
      }
      count++;
    }
  }

  private void setChoices(ChoiceCollection.Questions question) {
    if (count == question.getId()) {
      videoFile = question.getVideoFile();
      answer = question.getAnswer();
      choiceOneTextView.setText(question.getFirstChoice());
      choiceTwoTextView.setText(question.getSecondChoice());
      choiceThreeTextView.setText(question.getThirdChoice());
      choiceFourTextView.setText(question.getFourthChoice());
      videoFile = question.getVideoFile();
      answer = question.getAnswer();
    }
  }

  private MediaSource getMyVideoFile(int videoFile) {
    final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(getApplication());
    DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(videoFile));
    try {
      rawResourceDataSource.open(dataSpec);

      DataSource.Factory factory = () -> rawResourceDataSource;

      return new ExtractorMediaSource.Factory(factory).createMediaSource(
          rawResourceDataSource.getUri());
    } catch (RawResourceDataSource.RawResourceDataSourceException e) {
      e.printStackTrace();
      showNoVideoError();
    }

    return null;
  }

  private void initExoPlayer() {
    if (exoPlayer == null) {
      exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getApplication()),
          new DefaultTrackSelector(), new DefaultLoadControl());
      playerView.setPlayer(exoPlayer);
      showExoPlayer();
    }
  }

  public void initialisePlayer(MediaSource myVideoFile) {
    initExoPlayer();
    exoPlayer.prepare(myVideoFile, true, false);
    exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
    exoPlayer.setPlayWhenReady(playWhenReady);
    exoPlayer.seekTo(currentWindow, playbackPosition);
    player = exoPlayer;
    player.addListener(new Player.EventListener() {
      @Override public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

      }

      @Override public void onTracksChanged(TrackGroupArray trackGroups,
          TrackSelectionArray trackSelections) {

      }

      @Override public void onLoadingChanged(boolean isLoading) {

      }

      @Override public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (player.getPlaybackState()) {
          case Player.STATE_ENDED:
            choiceOneTextView.setVisibility(View.VISIBLE);
            choiceTwoTextView.setVisibility(View.VISIBLE);
            choiceThreeTextView.setVisibility(View.VISIBLE);
            choiceFourTextView.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.VISIBLE);
            resetAllTextState();
            break;
        }
      }

      @Override public void onRepeatModeChanged(int repeatMode) {

      }

      @Override public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

      }

      @Override public void onPlayerError(ExoPlaybackException error) {

      }

      @Override public void onPositionDiscontinuity(int reason) {

      }

      @Override public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

      }

      @Override public void onSeekProcessed() {

      }
    });

    choiceOneTextView.setOnClickListener(v -> {
      choiceOneTextView.setTextColor(getResources().getColor(R.color.white));
      if (choiceOneTextView.getText().toString().equals(getResources().getString(answer))) {
        choiceOneTextView.setBackgroundColor(getResources().getColor(R.color.green));
      } else {
        choiceOneTextView.setBackgroundColor(getResources().getColor(R.color.red));
      }
      moveOnNextQuestion();
    });

    choiceTwoTextView.setOnClickListener(v -> {
      choiceTwoTextView.setTextColor(getResources().getColor(R.color.white));
      if (choiceTwoTextView.getText().toString().equals(getResources().getString(answer))) {
        choiceTwoTextView.setBackgroundColor(getResources().getColor(R.color.green));
      } else {
        choiceTwoTextView.setBackgroundColor(getResources().getColor(R.color.red));
      }
      moveOnNextQuestion();
    });

    choiceThreeTextView.setOnClickListener(v -> {
      choiceThreeTextView.setTextColor(getResources().getColor(R.color.white));
      if (choiceThreeTextView.getText().toString().equals(getResources().getString(answer))) {
        choiceThreeTextView.setBackgroundColor(getResources().getColor(R.color.green));
      } else {
        choiceThreeTextView.setBackgroundColor(getResources().getColor(R.color.red));
      }
      moveOnNextQuestion();
    });

    choiceFourTextView.setOnClickListener(v -> {
      choiceFourTextView.setTextColor(getResources().getColor(R.color.white));
      if (choiceFourTextView.getText().toString().equals(getResources().getString(answer))) {
        choiceFourTextView.setBackgroundColor(getResources().getColor(R.color.green));
      } else {
        choiceFourTextView.setBackgroundColor(getResources().getColor(R.color.red));
      }
      moveOnNextQuestion();
    });

    playButton.setOnClickListener(v -> {
      playButton.setVisibility(View.INVISIBLE);
      hideAll();
      resetAllTextState();
      initialisePlayer(getMyVideoFile(videoFile));
    });
  }

  public void releasePlayer() {
    if (exoPlayer != null) {
      playbackPosition = exoPlayer.getCurrentPosition();
      currentWindow = exoPlayer.getCurrentWindowIndex();
      playWhenReady = exoPlayer.getPlayWhenReady();
      exoPlayer.stop();
      exoPlayer.release();
      exoPlayer = null;
    }
  }

  public void showExoPlayer() {
    playerView.setVisibility(View.VISIBLE);
  }

  private void showNoVideoError() {
    playerView.setVisibility(View.INVISIBLE);
  }

  @Override public void onResume() {
    super.onResume();
  }

  @Override public void onPause() {
    super.onPause();
    releasePlayer();
  }

  private void resetAllTextState() {
    choiceOneTextView.setTextColor(getResources().getColor(R.color.black));
    choiceTwoTextView.setTextColor(getResources().getColor(R.color.black));
    choiceThreeTextView.setTextColor(getResources().getColor(R.color.black));
    choiceFourTextView.setTextColor(getResources().getColor(R.color.black));
    choiceOneTextView.setBackgroundColor(getResources().getColor(R.color.white));
    choiceTwoTextView.setBackgroundColor(getResources().getColor(R.color.white));
    choiceThreeTextView.setBackgroundColor(getResources().getColor(R.color.white));
    choiceFourTextView.setBackgroundColor(getResources().getColor(R.color.white));
  }

  private void hideAll() {
    playButton.setVisibility(View.INVISIBLE);
    choiceOneTextView.setVisibility(View.INVISIBLE);
    choiceTwoTextView.setVisibility(View.INVISIBLE);
    choiceThreeTextView.setVisibility(View.INVISIBLE);
    choiceFourTextView.setVisibility(View.INVISIBLE);
  }

  private void moveOnNextQuestion() {
    Handler handler = new Handler();
    handler.postDelayed(() -> {
      if (count < 7) {
        getChoices();
        initialisePlayer(getMyVideoFile(videoFile));
        hideAll();
        resetAllTextState();
      } else {
        count = 1;
        hideAll();
        resetAllTextState();
        playerView.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        playVideoMode.setVisibility(View.VISIBLE);
        playVideoMode.setText("Restart");
      }
    }, 2000);
  }
}
