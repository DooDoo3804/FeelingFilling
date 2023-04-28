import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';
import {
  Platform,
  Keyboard,
  TouchableWithoutFeedback,
  LogBox,
  Modal,
  View,
} from 'react-native';
import {Common} from '../components/Common';
import FontawesomeIcon5 from 'react-native-vector-icons/FontAwesome5';
import {
  Container,
  ChatSectionContainer,
  DateContainer,
  DateTextSection,
  DisplayNone,
  InputBtn,
  MsgTimeSection,
  RecordinBtnRedSection,
  RecordStopBtnSection,
  RecordingBar,
  RecordingBarDisable,
  RecordingBtn,
  RecordingBtnSection,
  RecordingSection,
  RecordingSectionContainer,
  RecordingSectionOutSide,
  RecordingSendBtn,
  RecordingSendBtnDisable,
  RecordingTimer,
  RecordingTimerDisable,
  RecordingTitleText,
  SendMsgSection,
  SendingChatContainer,
  ReceivedChatContainer,
  SendingSectionContainer,
  TextInputSection,
  ReceiveMsgSection,
  ChatMoneyPngContainer,
  ReceivePriceSection,
  EmotionPngContainer,
  LottieContainer,
  PlayButton,
  RecordingDisplayView,
} from '../styles/ChatStyle';
import Lottie from 'lottie-react-native';

/////// png
import ChatMoneyPng from '../assets/chat_money.png';
import EmoAngry from '../assets/emo_angry.png';
import EmoSad from '../assets/emo_sad.png';
import EmoHappy from '../assets/emo_happy.png';
LogBox.ignoreAllLogs();

/////// recording, file
import {PERMISSIONS, RESULTS, request} from 'react-native-permissions';
import AudioRecorderPlayer from 'react-native-audio-recorder-player';
import RNFetchBlob from 'rn-fetch-blob';

let sendingText: string = '';
export const clickSave = () => {
  console.log(sendingText + '적금혀');
};

const Chat = () => {
  const [text, setText] = useState<string>('');
  const [isTexting, setIsTexting] = useState<boolean>(false);

  const [isRecordStart, setIsRecordStart] = useState<boolean>(false);
  const [isClickRecordModal, setIsClickRecordModal] = useState<boolean>(false);
  const audioRecorder = useRef<AudioRecorderPlayer>();

  const scrollViewRef = useRef<any>(null); // ScrollView ref 선언

  // file uploading 관련 상수
  const [recordUri, setRecordUri] = useState<string>('');
  const dirs = RNFetchBlob.fs.dirs;
  const path = Platform.select({
    ios: 'sound.m4a',
    android: `${dirs.CacheDir}/sound.mp4`,
  });

  // recording time, play time 관련 상수
  const [recordDuration, setRecordDuration] = useState({
    recordSecs: 0,
    recordTime: '00:00:00',
  });

  const [playerDuration, setPlayerDuration] = useState({
    currentPositionSec: 0,
    currentDurationSec: 0,
    playTime: '00:00:00',
    duration: '00:00:00',
  });

  // 처음 페이지 마운팅시 scroll은 가장 최근 대화로
  useEffect(() => {
    scrollViewRef.current.scrollToEnd({animated: true});
  }, []);

  // 처음 페이지 마운팅시 recorder 객체를 생성
  useEffect(() => {
    audioRecorder.current = new AudioRecorderPlayer();
    checkRecord();
  }, []);

  // 권한 상태 check
  const checkRecord = async () => {
    try {
      const result = await request(PERMISSIONS.IOS.SPEECH_RECOGNITION);
      if (result === RESULTS.BLOCKED) {
      }
    } catch (e) {
      console.log(`에러 \n ${e}`);
    }
  };

  // 같은 rec 버튼을 눌렀을 때 상태에 따라 start or stop
  const audioToggle = () => {
    if (isRecordStart) {
      onStopRecord();
    } else {
      onStartRecord();
    }
  };

  // 녹음 시작
  const onStartRecord = async () => {
    setRecordDuration({...recordDuration, recordSecs: 0});
    setPlayerDuration({
      ...playerDuration,
      currentPositionSec: 0,
      currentDurationSec: 0,
      playTime: '00:00:00',
      duration: '00:00:00',
    });
    audioRecorder.current = new AudioRecorderPlayer();
    if (audioRecorder.current) {
      setIsRecordStart(true); // 녹음 시작 state
      // 실제 녹음 시작
      const uri = await audioRecorder.current.startRecorder(path);
      const audioRecorderPlayer = audioRecorder.current;
      audioRecorder.current.addRecordBackListener(e => {
        setRecordDuration({
          ...recordDuration,
          recordSecs: e.currentPosition,
          recordTime: audioRecorderPlayer.mmssss(Math.floor(e.currentPosition)),
        });
      });
      setRecordUri(uri);
      console.log(uri);
    }
  };

  // 녹음 중지
  const onStopRecord = async () => {
    if (audioRecorder.current) {
      setIsRecordStart(false);
      await audioRecorder.current.stopRecorder();
    }
    audioRecorder.current?.removeRecordBackListener();
  };

  // 재생
  const soundStart = async () => {
    console.log('onStartPlay');
    const audioRecorderPlayer: any = audioRecorder.current;
    await audioRecorder.current?.startPlayer();
    audioRecorder.current?.addPlayBackListener(e => {
      setPlayerDuration({
        currentPositionSec: e.currentPosition,
        currentDurationSec: e.duration,
        playTime: audioRecorderPlayer.mmssss(Math.floor(e.currentPosition)),
        duration: audioRecorderPlayer.mmssss(Math.floor(e.duration)),
      });
    });
  };

  // 녹음 파일 uploading
  const uploadFile = async () => {
    const formData = new FormData();
    formData.append('file', {
      uri: recordUri,
      type: 'audio/mp4',
      name: 'audio.mp4',
    });
    axios
      .post('http://3.38.191.128:8000/feelings/voice', formData, {
        // .post('http://10.0.2.2:8080/api/test', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then(response => {
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });
  };

  const chatting = [
    {
      chatDate: '2023년 04월 21일',
      chatDay: '금요일',
      chat: [
        {
          chattingId: '1',
          content: '김사장 나오라그래',
          chatDate: '2023-04-21T16:34:30.388',
          type: '0', //0, 1, 2
          mood: '', //2일떄만
          amount: '', //2일때만
        },
        {
          chattingId: '2',
          content: '확마 그냥 다 확구냥',
          chatDate: '2023-04-21T16:34:30.388',
          type: '0', //0, 1, 2
          mood: '', //2일떄만
          amount: '', //2일때만
        },
        {
          chattingId: '3',
          content: '응? 내가 가만 안둘라요',
          chatDate: '2023-04-21T16:35:30.388',
          type: '0', //0, 1, 2
          mood: '', //2일떄만
          amount: '', //2일때만
        },
        {
          chattingId: '4',
          content: '저런! 화나셨군염',
          chatDate: '',
          type: '2', //0, 1, 2
          mood: 'angry', //2일떄만
          amount: '20000', //2일때만
        },
      ],
    },
    {
      chatDate: '2023년 04월 22일',
      chatDay: '토요일',
      chat: [
        {
          chattingId: '5',
          content: '얏호우 아싸 보너스 받앗다뤼',
          chatDate: '2023-04-22T13:21:30.388',
          type: '0', //0, 1, 2
          mood: '', //2일떄만
          amount: '', //2일때만
        },
        {
          chattingId: '6',
          content: '완전 플렉스할거야',
          chatDate: '2023-04-22T13:21:30.388',
          type: '0', //0, 1, 2
          mood: '', //2일떄만
          amount: '', //2일때만
        },
        {
          chattingId: '7',
          content: '좋겠다! 부러워',
          chatDate: '',
          type: '2', //0, 1, 2
          mood: 'joy', //2일떄만
          amount: '18000', //2일때만
        },
      ],
    },
    {
      chatDate: '2023년 04월 23일',
      chatDay: '일요일',
      chat: [
        {
          chattingId: '8',
          content: '',
          chatDate: '2023-04-23T10:18:30.388',
          type: '1', //0, 1, 2
          mood: '', //2일떄만
          amount: '', //2일때만
        },
        {
          chattingId: '9',
          content: '괜찮을거야!',
          chatDate: '',
          type: '2', //0, 1, 2
          mood: 'gloomy', //2일떄만
          amount: '22000', //2일때만
        },
      ],
    },
  ];

  /////////////////////////
  // Rendering func

  // chat render
  const renderOfChat = () => {
    const result: any[] = [];
    chatting.forEach(e => {
      result.push(
        <DateContainer key={e.chatDate}>
          <FontawesomeIcon5 name="calendar-check" color={'#484848'} size={14} />
          <DateTextSection>
            {e.chatDate} {e.chatDay}
          </DateTextSection>
        </DateContainer>,
      );
      const chat = e.chat;
      for (let i = 0; i < chat.length; i++) {
        const el = chat[i];
        let nextChat = '';
        if (i < chat.length - 1 && chat[i + 1].chatDate.length > 5) {
          nextChat = chatTimeConverter(chat[i + 1].chatDate);
        }
        // 유저가 보낸 채팅
        if (Number(el.type) === 0) {
          const convertedChatDate = chatTimeConverter(el.chatDate);
          result.push(
            <SendingChatContainer key={el.chattingId}>
              {convertedChatDate === nextChat ? (
                ''
              ) : (
                <MsgTimeSection>{convertedChatDate}</MsgTimeSection>
              )}
              <SendMsgSection>{el.content}</SendMsgSection>
            </SendingChatContainer>,
          );
        } else if (Number(el.type) === 1) {
          const convertedChatDate = chatTimeConverter(el.chatDate);
          result.push(
            <SendingChatContainer key={el.chattingId}>
              {convertedChatDate === nextChat ? (
                ''
              ) : (
                <MsgTimeSection>{convertedChatDate}</MsgTimeSection>
              )}
              <SendMsgSection>
                <FontawesomeIcon5
                  name="microphone"
                  color={Common.colors.deepGrey}
                  size={18}
                />
              </SendMsgSection>
            </SendingChatContainer>,
          );
          /// 여기부터 응답
        } else {
          if (el.mood === 'angry') {
            result.push(
              <View key={el.chattingId}>
                <ReceivedChatContainer>
                  <ReceiveMsgSection color={Common.colors.chatColor01}>
                    {el.content}
                  </ReceiveMsgSection>
                </ReceivedChatContainer>
                <ReceivedChatContainer>
                  <ReceivePriceSection
                    backgroundColor={Common.colors.chatColor01}
                    color={Common.colors.receivedChatColor01}>
                    {'+ ' + priceConverter(el.amount) + '₩'}
                    <EmotionPngContainer source={EmoAngry} />
                  </ReceivePriceSection>
                  <ChatMoneyPngContainer source={ChatMoneyPng} />
                </ReceivedChatContainer>
              </View>,
            );
          } else if (el.mood === 'joy') {
            result.push(
              <View key={el.chattingId}>
                <ReceivedChatContainer>
                  <ReceiveMsgSection color={Common.colors.chatColor02}>
                    {el.content}
                  </ReceiveMsgSection>
                </ReceivedChatContainer>
                <ReceivedChatContainer>
                  <ReceivePriceSection
                    backgroundColor={Common.colors.chatColor02}
                    color={Common.colors.receivedChatColor02}>
                    {'+ ' + priceConverter(el.amount) + '₩'}
                    <EmotionPngContainer source={EmoHappy} />
                  </ReceivePriceSection>
                  <ChatMoneyPngContainer source={ChatMoneyPng} />
                </ReceivedChatContainer>
              </View>,
            );
          } else {
            result.push(
              <View key={el.chattingId}>
                <ReceivedChatContainer>
                  <ReceiveMsgSection color={Common.colors.chatColor03}>
                    {el.content}
                  </ReceiveMsgSection>
                </ReceivedChatContainer>
                <ReceivedChatContainer>
                  <ReceivePriceSection
                    backgroundColor={Common.colors.chatColor03}
                    color={Common.colors.receivedChatColor03}>
                    {'+ ' + priceConverter(el.amount) + '₩'}
                    <EmotionPngContainer source={EmoSad} />
                  </ReceivePriceSection>
                  <ChatMoneyPngContainer source={ChatMoneyPng} />
                </ReceivedChatContainer>
              </View>,
            );
          }
        }
      }
    });
    return result;
  };

  const priceConverter = (price: string) => {
    return price.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  };

  const chatTimeConverter = (time: string) => {
    //chatDate: '2023-04-21T16:34:30.388',
    const times = time.split('T');
    let hour = Number(times[1].substring(0, 2));
    const minute = Number(times[1].substring(3, 5));
    if (hour < 12) {
      if (hour === 0) {
        hour = 12;
      } else if (hour > 12) {
        hour -= 12;
      }
      if (hour < 10) {
        return '오전 ' + '0' + hour + ':' + minute;
      } else {
        return '오전 ' + hour + ':' + minute;
      }
    } else {
      if (hour === 0) {
        hour = 12;
      } else if (hour > 12) {
        hour -= 12;
      }
      if (hour < 10) {
        return '오후 ' + '0' + hour + ':' + minute;
      } else {
        return '오후 ' + hour + ':' + minute;
      }
    }
  };

  const handleTextKeyPress = () => {
    setIsTexting(true);
  };

  const handleTextingKeyDismiss = () => {
    Keyboard.dismiss();
    setIsTexting(false);
  };

  const handleTextChange = (inputText: string) => {
    setText(inputText);
  };

  const handleTextSubmit = () => {
    console.log(text);
    setText('');
  };

  const textSend = (): void => {
    if (text.length > 0) {
      console.log(text);
      setText('');
    }
  };

  return (
    <Container>
      <TouchableWithoutFeedback onPress={() => handleTextingKeyDismiss()}>
        <ChatSectionContainer ref={scrollViewRef}>
          {renderOfChat()}
        </ChatSectionContainer>
      </TouchableWithoutFeedback>
      <SendingSectionContainer>
        <TextInputSection
          value={text}
          onChangeText={handleTextChange}
          onFocus={handleTextKeyPress}
          onSubmitEditing={handleTextSubmit}
        />
        {isTexting ? (
          <InputBtn onPress={textSend}>
            <FontawesomeIcon5
              name="arrow-up"
              color={Common.colors.white01}
              size={22}
            />
          </InputBtn>
        ) : (
          <InputBtn
            onPress={() => {
              setIsClickRecordModal(true);
            }}>
            <FontawesomeIcon5
              name="microphone"
              color={Common.colors.white01}
              size={20}
            />
          </InputBtn>
        )}
      </SendingSectionContainer>
      {/* 녹음 모달 */}
      <Modal
        animationType={'fade'}
        transparent={true}
        visible={isClickRecordModal}>
        <RecordingSectionContainer>
          <RecordingSectionOutSide
            onTouchStart={() => {
              if (!isRecordStart) {
                setIsClickRecordModal(false);
                setIsRecordStart(false);
                setRecordUri('');
                setRecordDuration({...recordDuration, recordSecs: 0});
                setPlayerDuration({
                  ...playerDuration,
                  currentPositionSec: 0,
                  currentDurationSec: 0,
                  playTime: '00:00:00',
                  duration: '00:00:00',
                });
              }
            }}
          />
          <RecordingSection>
            <RecordingTitleText>음성메세지</RecordingTitleText>
            {isRecordStart ? (
              // 녹음중
              <RecordingBar
                onTouchEnd={() => {
                  if (!isRecordStart) {
                    soundStart();
                  }
                }}>
                <RecordingDisplayView />
                <LottieContainer>
                  <Lottie
                    source={require('../assets/recording.json')}
                    autoPlay
                    loop
                  />
                </LottieContainer>
                <RecordingTimer>
                  {recordDuration.recordTime.substring(0, 5)}
                </RecordingTimer>
              </RecordingBar>
            ) : recordUri.length > 5 ? (
              // 녹음 완료
              // 플레이
              playerDuration.currentDurationSec < recordDuration.recordSecs ? (
                <RecordingBar>
                  <PlayButton
                    onTouchStart={() => {
                      soundStart();
                    }}>
                    <FontawesomeIcon5
                      name="play"
                      color={Common.colors.deepGrey}
                      size={20}
                    />
                  </PlayButton>
                  <RecordingTimer>
                    {recordDuration.recordTime.substring(0, 5)}
                  </RecordingTimer>
                </RecordingBar>
              ) : (
                // 플레이 하고 있지 않음
                <RecordingBar>
                  <PlayButton
                    onTouchStart={() => {
                      soundStart();
                    }}>
                    <FontawesomeIcon5
                      name="play"
                      color={Common.colors.deepGrey}
                      size={20}
                    />
                  </PlayButton>
                  <RecordingTimer>
                    {playerDuration.playTime.substring(0, 5)}
                  </RecordingTimer>
                </RecordingBar>
              )
            ) : (
              // 녹음 전
              <RecordingBarDisable>
                <RecordingTimerDisable>00:00</RecordingTimerDisable>
              </RecordingBarDisable>
            )}
            <RecordingBtnSection>
              <DisplayNone />
              {isRecordStart ? (
                <RecordingBtn
                  onTouchStart={() => {
                    audioToggle();
                  }}>
                  <RecordStopBtnSection />
                </RecordingBtn>
              ) : (
                <RecordingBtn
                  onTouchStart={() => {
                    audioToggle();
                  }}>
                  <RecordinBtnRedSection />
                </RecordingBtn>
              )}
              {audioRecorder.current && recordUri.length > 5 ? (
                <RecordingSendBtn
                  onPress={() => {
                    uploadFile();
                  }}>
                  <FontawesomeIcon5
                    name="arrow-up"
                    color={Common.colors.white01}
                    size={20}
                  />
                </RecordingSendBtn>
              ) : (
                <RecordingSendBtnDisable>
                  <FontawesomeIcon5
                    name="arrow-up"
                    color={Common.colors.selectGrey}
                    size={20}
                  />
                </RecordingSendBtnDisable>
              )}
            </RecordingBtnSection>
          </RecordingSection>
        </RecordingSectionContainer>
      </Modal>
    </Container>
  );
};

export default Chat;
