package com.example.mjcstagram.add.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.StaticLayout;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.mjcstagram.R;
import com.example.mjcstagram.databinding.AddBinding;
import com.example.mjcstagram.databinding.VideoFragmentBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;

public class VideoFragment extends androidx.fragment.app.Fragment
        implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

        // 카메라 전면 후면
        private static final String CAM_FRONT = "1";
        private static final String CAM_REAR = "0";
        private String mCamId;


        private static final int SENSOR_ORIENTATION_DEFAULT_DEGREES = 90;
        private static final int SENSOR_ORIENTATION_INVERSE_DEGREES = 270;
        private static final SparseIntArray DEFAULT_ORIENTATIONS = new SparseIntArray();
        private static final SparseIntArray INVERSE_ORIENTATIONS = new SparseIntArray();

        private static final String TAG = "VideoFragment";
        private static final int REQUEST_VIDEO_PERMISSIONS = 1;
        private static final String FRAGMENT_DIALOG = "dialog";

        private static final String[] VIDEO_PERMISSIONS = {
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
        };

        static {
                DEFAULT_ORIENTATIONS.append(Surface.ROTATION_0, 90);
                DEFAULT_ORIENTATIONS.append(Surface.ROTATION_90, 0);
                DEFAULT_ORIENTATIONS.append(Surface.ROTATION_180, 270);
                DEFAULT_ORIENTATIONS.append(Surface.ROTATION_270, 180);
        }

        static {
                INVERSE_ORIENTATIONS.append(Surface.ROTATION_0, 270);
                INVERSE_ORIENTATIONS.append(Surface.ROTATION_90, 180);
                INVERSE_ORIENTATIONS.append(Surface.ROTATION_180, 90);
                INVERSE_ORIENTATIONS.append(Surface.ROTATION_270, 0);
        }

        private AutoFitTextureView mTextureView;
        private ImageView picture;
        private ImageView switchImgBtn;
        private CameraDevice mCameraDevice;
        private CameraCaptureSession mPreviewSession;
        private TextureView.SurfaceTextureListener mSurfaceTextureListener
                = new TextureView.SurfaceTextureListener() {

                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture,
                                                      int width, int height) {
                        openCamera(width, height);
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture,
                                                        int width, int height) {
                        configureTransform(width, height);
                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                        return true;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                }

        };

        private Size mPreviewSize;
        private Size mVideoSize;
        private MediaRecorder mMediaRecorder;
        private boolean mIsRecordingVideo;
        private HandlerThread mBackgroundThread;
        private Handler mBackgroundHandler;
        private Semaphore mCameraOpenCloseLock = new Semaphore(1);
        private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

                @Override
                public void onOpened(@NonNull CameraDevice cameraDevice) {
                        mCameraDevice = cameraDevice;
                        startPreview();
                        mCameraOpenCloseLock.release();
                        if (null != mTextureView) {
                                configureTransform(mTextureView.getWidth(), mTextureView.getHeight());
                        }
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                        mCameraOpenCloseLock.release();
                        cameraDevice.close();
                        mCameraDevice = null;
                }

                @Override
                public void onError(@NonNull CameraDevice cameraDevice, int error) {
                        mCameraOpenCloseLock.release();
                        cameraDevice.close();
                        mCameraDevice = null;
                        Activity activity = getActivity();
                        if (null != activity) {
                                activity.finish();
                        }
                }

        };
        private Integer mSensorOrientation;
        private String mNextVideoAbsolutePath;
        private CaptureRequest.Builder mPreviewBuilder;

        public static VideoFragment newInstance() {
                return new VideoFragment();
        }

        private static Size chooseVideoSize(Size[] choices) {
                for (Size size : choices) {
                        if (size.getWidth() == size.getHeight() * 4 / 3 && size.getWidth() <= 1080) {
                                return size;
                        }
                }
                Log.e(TAG, "Couldn't find any suitable video size");
                return choices[choices.length - 1];
        }

        private static Size chooseOptimalSize(Size[] choices, int width, int height, Size aspectRatio) {
                // Collect the supported resolutions that are at least as big as the preview Surface
                List<Size> bigEnough = new ArrayList<>();
                int w = aspectRatio.getWidth();
                int h = aspectRatio.getHeight();
                for (Size option : choices) {
                        if (option.getHeight() == option.getWidth() * h / w &&
                                option.getWidth() >= width && option.getHeight() >= height) {
                                bigEnough.add(option);
                        }
                }

                // Pick the smallest of those, assuming we found any
                if (bigEnough.size() > 0) {
                        return Collections.min(bigEnough, new CompareSizesByArea());
                } else {
                        Log.e(TAG, "Couldn't find any suitable preview size");
                        return choices[0];
                }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
                mCamId = CAM_REAR;
                return inflater.inflate(R.layout.video_fragment, container, false);
        }

        @Override
        public void onViewCreated(final View view, Bundle savedInstanceState) {
                mTextureView = (AutoFitTextureView) view.findViewById(R.id.texture);
                picture = (ImageView) view.findViewById(R.id.picture);
                picture.setOnClickListener(this);
                switchImgBtn = (ImageView)view.findViewById(R.id.switchImgBtn);
                switchImgBtn.setOnClickListener(this);

        }

        @Override
        public void onResume() {
                super.onResume();
                startBackgroundThread();
                if (mTextureView.isAvailable()) {
                        openCamera(mTextureView.getWidth(), mTextureView.getHeight());
                } else {
                        mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
                }
        }

        @Override
        public void onPause() {
                closeCamera();
                stopBackgroundThread();
                super.onPause();
        }

        @Override
        public void onClick(View view) {
                switch (view.getId()) {
                        case R.id.picture: {
                                if (mIsRecordingVideo) {
                                        stopRecordingVideo();
                                } else {
                                        startRecordingVideo();
                                }
                                break;
                        }
                        case R.id.switchImgBtn:
                                switch (mCamId) {
                                        case CAM_REAR:
                                                mCamId = CAM_FRONT;
                                                break;
                                        case CAM_FRONT:
                                                mCamId = CAM_REAR;
                                                break;
                                }
                                closeCamera();
                                openCamera(mTextureView.getWidth(),mTextureView.getHeight());
                                break;


                }
        }
        private void startBackgroundThread() {
                mBackgroundThread = new HandlerThread("CameraBackground");
                mBackgroundThread.start();
                mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
        }
        private void stopBackgroundThread() {
                mBackgroundThread.quitSafely();
                try {
                        mBackgroundThread.join();
                        mBackgroundThread = null;
                        mBackgroundHandler = null;
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
        }

        @SuppressWarnings("MissingPermission")
        private void openCamera(int width, int height) {

                final Activity activity = getActivity();
                if (null == activity || activity.isFinishing()) {
                        return;
                }
                CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
                try {
                        Log.d(TAG, "tryAcquire");
                        if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                                throw new RuntimeException("Time out waiting to lock camera opening.");
                        }

                        // Choose the sizes for camera preview and video recording
                        CameraCharacteristics characteristics = manager.getCameraCharacteristics(mCamId);
                        StreamConfigurationMap map = characteristics
                                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                        mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                        if (map == null) {
                                throw new RuntimeException("Cannot get available preview/video sizes");
                        }
                        mVideoSize = chooseVideoSize(map.getOutputSizes(MediaRecorder.class));
                        mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                                width, height, mVideoSize);

                        int orientation = getResources().getConfiguration().orientation;
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                mTextureView.setAspectRatio(mPreviewSize.getWidth(), mPreviewSize.getHeight());
                        } else {
                                mTextureView.setAspectRatio(mPreviewSize.getHeight(), mPreviewSize.getWidth());
                        }
                        configureTransform(width, height);
                        mMediaRecorder = new MediaRecorder();
                        manager.openCamera(mCamId, mStateCallback, null);
                } catch (CameraAccessException e) {
                        Toast.makeText(activity, "Cannot access the camera.", Toast.LENGTH_SHORT).show();
                        activity.finish();
                } catch (NullPointerException e) {
                        // Currently an NPE is thrown when the Camera2API is used but not supported on the
                        // device this code runs.

                } catch (InterruptedException e) {
                        throw new RuntimeException("Interrupted while trying to lock camera opening.");
                }
        }

        private void closeCamera() {
                try {
                        mCameraOpenCloseLock.acquire();
                        closePreviewSession();
                        if (null != mCameraDevice) {
                                mCameraDevice.close();
                                mCameraDevice = null;
                        }
                        if (null != mMediaRecorder) {
                                mMediaRecorder.release();
                                mMediaRecorder = null;
                        }
                } catch (InterruptedException e) {
                        throw new RuntimeException("Interrupted while trying to lock camera closing.");
                } finally {
                        mCameraOpenCloseLock.release();
                }
        }

        private void startPreview() {
                if (null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
                        return;
                }
                try {
                        closePreviewSession();
                        SurfaceTexture texture = mTextureView.getSurfaceTexture();
                        assert texture != null;
                        texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
                        mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

                        Surface previewSurface = new Surface(texture);
                        mPreviewBuilder.addTarget(previewSurface);

                        mCameraDevice.createCaptureSession(Collections.singletonList(previewSurface),
                                new CameraCaptureSession.StateCallback() {

                                        @Override
                                        public void onConfigured(@NonNull CameraCaptureSession session) {
                                                mPreviewSession = session;
                                                updatePreview();
                                        }

                                        @Override
                                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                                                Activity activity = getActivity();
                                                if (null != activity) {
                                                        Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                        }
                                }, mBackgroundHandler);
                } catch (CameraAccessException e) {
                        e.printStackTrace();
                }
        }
        private void updatePreview() {
                if (null == mCameraDevice) {
                        return;
                }
                try {
                        setUpCaptureRequestBuilder(mPreviewBuilder);
                        HandlerThread thread = new HandlerThread("CameraPreview");
                        thread.start();
                        mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, mBackgroundHandler);
                } catch (CameraAccessException e) {
                        e.printStackTrace();
                }
        }

        private void setUpCaptureRequestBuilder(CaptureRequest.Builder builder) {
                builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        }
        private void configureTransform(int viewWidth, int viewHeight) {
                Activity activity = getActivity();
                if (null == mTextureView || null == mPreviewSize || null == activity) {
                        return;
                }
                int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                Matrix matrix = new Matrix();
                RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
                RectF bufferRect = new RectF(0, 0, mPreviewSize.getHeight(), mPreviewSize.getWidth());
                float centerX = viewRect.centerX();
                float centerY = viewRect.centerY();
                if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
                        bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
                        matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
                        float scale = Math.max(
                                (float) viewHeight / mPreviewSize.getHeight(),
                                (float) viewWidth / mPreviewSize.getWidth());
                        matrix.postScale(scale, scale, centerX, centerY);
                        matrix.postRotate(90 * (rotation - 2), centerX, centerY);
                }
                mTextureView.setTransform(matrix);
        }

        private void setUpMediaRecorder() throws IOException {
                final Activity activity = getActivity();
                if (null == activity) return;
                mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                if (mNextVideoAbsolutePath == null || mNextVideoAbsolutePath.isEmpty()) {
                        mNextVideoAbsolutePath = getVideoFilePath();
                }
                mMediaRecorder.setOutputFile(mNextVideoAbsolutePath);
                mMediaRecorder.setVideoEncodingBitRate(10000000);
                mMediaRecorder.setVideoFrameRate(30);
                mMediaRecorder.setVideoSize(mVideoSize.getWidth(), mVideoSize.getHeight());
                mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                switch (mSensorOrientation) {
                        case SENSOR_ORIENTATION_DEFAULT_DEGREES:
                                mMediaRecorder.setOrientationHint(DEFAULT_ORIENTATIONS.get(rotation));
                                break;
                        case SENSOR_ORIENTATION_INVERSE_DEGREES:
                                mMediaRecorder.setOrientationHint(INVERSE_ORIENTATIONS.get(rotation));
                                break;
                }
                mMediaRecorder.prepare();
        }

        private String getVideoFilePath() {
                final File dir = Environment.getExternalStorageDirectory().getAbsoluteFile();
                String path = dir.getPath() + "/" + "DCIM/MjcStagram_Video/";
                File dst = new File(path);
                if(!dst.exists()) dst.mkdirs();
                return path + System.currentTimeMillis() + ".mp4";
        }

        private void startRecordingVideo() {
                if (null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
                        return;
                }
                Toast.makeText(getActivity(),"녹화를 중지하려면 버튼을 다시 누르세요",Toast.LENGTH_SHORT).show();
                try {
                        closePreviewSession();
                        setUpMediaRecorder();
                        SurfaceTexture texture = mTextureView.getSurfaceTexture();
                        assert texture != null;
                        texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
                        mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
                        List<Surface> surfaces = new ArrayList<>();

                        // Set up Surface for the camera preview
                        Surface previewSurface = new Surface(texture);
                        surfaces.add(previewSurface);
                        mPreviewBuilder.addTarget(previewSurface);

                        // Set up Surface for the MediaRecorder
                        Surface recorderSurface = mMediaRecorder.getSurface();
                        surfaces.add(recorderSurface);
                        mPreviewBuilder.addTarget(recorderSurface);

                        // Start a capture session
                        // Once the session starts, we can update the UI and start recording
                        mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {

                                @Override
                                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                                        mPreviewSession = cameraCaptureSession;
                                        updatePreview();
                                        getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                        // UI
                                                        mIsRecordingVideo = true;

                                                        // Start recording
                                                        mMediaRecorder.start();
                                                }
                                        });
                                }

                                @Override
                                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                                        Activity activity = getActivity();
                                        if (null != activity) {
                                                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                }
                        }, mBackgroundHandler);
                } catch (CameraAccessException | IOException e) {
                        e.printStackTrace();
                }

        }

        private void closePreviewSession() {
                if (mPreviewSession != null) {
                        mPreviewSession.close();
                        mPreviewSession = null;
                }
        }

        private void stopRecordingVideo() {
                // UI
                mIsRecordingVideo = false;
                // Stop recording
                mMediaRecorder.stop();
                mMediaRecorder.reset();

                Activity activity = getActivity();
                if (null != activity) {
                        Toast.makeText(activity, "Video saved: " + mNextVideoAbsolutePath,
                                Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Video saved: " + mNextVideoAbsolutePath);

                        File file = new File(mNextVideoAbsolutePath);
                        if(!file.exists()) file.mkdir();
                        getActivity().getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                }
                mNextVideoAbsolutePath = null;
                startPreview();
        }
        static class CompareSizesByArea implements Comparator<Size> {

                @Override
                public int compare(Size lhs, Size rhs) {
                        // We cast here to ensure the multiplications won't overflow
                        return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                                (long) rhs.getWidth() * rhs.getHeight());
                }

        }



}
