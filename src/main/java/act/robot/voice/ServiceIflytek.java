package act.robot.voice;

import com.iflytek.cloud.speech.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wave on 17-2-27.
 */
class ServiceIflytek {

    private static final Logger log = LoggerFactory.getLogger(ServiceIflytek.class);
    private SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer();
    private SpeechRecognizer mIat= SpeechRecognizer.createRecognizer( );

    ServiceIflytek(){
        log.info("ifytek service init...");
        //语言云服务初始化
        SpeechUtility.createUtility(SpeechConstant.APPID +"=5850f914");
        //语音合成初始化
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./iflytek.pcm");
        mTts.startSpeaking("科大讯飞，让世界聆听我们的声音", mSynListener);
        //语音识别初始化
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        log.info("ifytek service init done.");
    }


    void speak(String s){
        mTts.startSpeaking("科大讯飞，让世界聆听我们的声音", mSynListener);
    }

    void listen(){
        mIat.startListening(mRecoListener);
    }


    //语音合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener(){
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
            log.error(error.toString());
        }
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}
        //开始播放
        public void onSpeakBegin() {}
        //暂停播放
        public void onSpeakPaused() {}
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {}
        //恢复播放回调接口
        public void onSpeakResumed() {}
    };


    //语音识别监听器
    private RecognizerListener mRecoListener = new RecognizerListener(){
        //听写结果回调接口(返回Json格式结果，用户可参见附录)；
        //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        //关于解析Json的代码可参见MscDemo中JsonParser类；
        //isLast等于true时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {
            log.info("Result:"+results.getResultString ());
            if (!isLast)
                log.info("continue");
            else
                log.info("the end");
        }
        //会话发生错误回调接口
        public void onError(SpeechError error) {
            log.error(error.toString());
        }
        //开始录音
        public void onBeginOfSpeech() {
            log.info("start");
        }
        //音量值0~30
        public void onVolumeChanged(int volume){}
        //结束录音
        public void onEndOfSpeech() {
            log.info("end");
        }
        //扩展用接口
        public void onEvent(int eventType,int arg1,int arg2,String msg) {}
    };
}
