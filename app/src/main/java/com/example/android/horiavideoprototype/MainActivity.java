package com.example.android.horiavideoprototype;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

public class MainActivity extends BaseActivity {

  @BindView(R.id.layout) FrameLayout layout;
  @BindView(R.id.playVideoMode) TextView playVideoMode;
  @BindView(R.id.exoplayer) PlayerView playerView;
  @BindView(R.id.text1) AppCompatTextView text1;
  @BindView(R.id.text2) AppCompatTextView text2;
  @BindView(R.id.text3) AppCompatTextView text3;
  @BindView(R.id.text4) AppCompatTextView text4;
  @BindView(R.id.playButton) ImageView playButton;

  private SimpleExoPlayer exoPlayer;
  //private Uri videoUri;
  private int currentWindow;
  private Long playbackPosition = 0L;
  private boolean playWhenReady = true;
  private Player player;
  private int videoFile;
  private View decorView;
  private int uiOptions; 
  //String path;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this, getViewGroup());
    decorView = getWindow().getDecorView();
    uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
    decorView.setSystemUiVisibility(uiOptions);
    ActionBar actionBar = getSupportActionBar();
    actionBar.hide();

    playVideoMode.setOnClickListener(v -> {
      Animator.shrinkFadeOutRemove(playVideoMode);
      layout.setVisibility(View.VISIBLE);
      playerView.setVisibility(View.VISIBLE);
      videoFile = R.raw.nested_sequence_2111;
      initialisePlayer(getMyVideoFile(videoFile));
    });
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
    Log.d("Joshua", "initExoPlayer: before position is :"
        + playbackPosition
        + " is exoplayer null? : "
        + exoPlayer);
    if (exoPlayer == null) {
      exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getApplication()),
          new DefaultTrackSelector(), new DefaultLoadControl());
      playerView.setPlayer(exoPlayer);
      showExoPlayer();
    }
  }

  public void initialisePlayer(MediaSource myVideoFile) {
    initExoPlayer();
    //if (!TextUtils.isEmpty(videoURL)) {
    //videoUri = Uri.parse(videoURL);
    //MediaSource mediaSource =
    //new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("VideoApp")).
    // createMediaSource(videoUri);

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
            if (videoFile == R.raw.guy) {
              text1.setText("I don't speak French");
              text2.setText("I only speak English");
              text3.setText("What is my name?");
              text4.setText("Hello there, beautiful");
            }
            text1.setVisibility(View.VISIBLE);
            text2.setVisibility(View.VISIBLE);
            text3.setVisibility(View.VISIBLE);
            text4.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.VISIBLE);

            Log.d("Joshua1", "onPlayerStateChanged: I have ended");
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

    text3.setOnClickListener(v -> {
      text3.setBackgroundColor(getResources().getColor(R.color.green));
      text3.setTextColor(getResources().getColor(R.color.white));
    });

    text2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    });

    playButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        playButton.setVisibility(View.INVISIBLE);
        text1.setVisibility(View.INVISIBLE);
        text2.setVisibility(View.INVISIBLE);
        text3.setVisibility(View.INVISIBLE);
        text3.setBackgroundColor(getResources().getColor(R.color.white));
        text3.setTextColor(getResources().getColor(R.color.black));
        text4.setVisibility(View.INVISIBLE);
        initialisePlayer(getMyVideoFile(videoFile));
      }
    });

    text3.setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View v) {
        videoFile = R.raw.guy;
        initialisePlayer(getMyVideoFile(videoFile));
        text3.setBackgroundColor(getResources().getColor(R.color.white));
        text3.setTextColor(getResources().getColor(R.color.black));
        playButton.setVisibility(View.INVISIBLE);
        text1.setVisibility(View.INVISIBLE);
        text2.setVisibility(View.INVISIBLE);
        text3.setVisibility(View.INVISIBLE);
        text4.setVisibility(View.INVISIBLE);
        return true;
      }
    });

    //initialisePlayer();
    //Player.STATE_ENDED

    //} else {
    //showNoVideoError();
    //}
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
    //thumbnail.setVisibility(View.INVISIBLE);
    playerView.setVisibility(View.VISIBLE);
  }

  private void showNoVideoError() {
    //thumbnail.setVisibility(View.VISIBLE);
    playerView.setVisibility(View.INVISIBLE);
  }

  @Override public void onResume() {
    super.onResume();
    //initialisePlayer();
  }

  @Override public void onPause() {
    super.onPause();
    Log.d("Joshua", "onPause is called ");
    // if (steps.getVideoURL() != null) {
    releasePlayer();
    //}
  }
}
