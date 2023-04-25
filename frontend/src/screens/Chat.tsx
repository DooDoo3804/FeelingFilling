import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';
import {
  Platform,
  Alert,
  Keyboard,
  TouchableWithoutFeedback,
  LogBox,
  Modal,
  View,
} from 'react-native';
import {Common} from '../components/Common';
import FontawesomeIcon5 from 'react-native-vector-icons/FontAwesome5';
import Timeout from 'node';
import {
  Container,
  ChatSectionContainer,
  DateContainer,
  DateTextSection,
  DisplayNone,
  InputBtn,
  MsgTimeSection,
  RecordinBtnRedSection,
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
} from '../styles/ChatStyle';
import ChatMoneyPng from '../assets/chat_money.png';
import EmoAngry from '../assets/emo_angry.png';
import EmoSad from '../assets/emo_sad.png';
import EmoHappy from '../assets/emo_happy.png';
LogBox.ignoreAllLogs();

//////////////////////////////////////////////////////

import {PERMISSIONS, RESULTS, request} from 'react-native-permissions';
import AudioRecorderPlayer from 'react-native-audio-recorder-player';
import RNFetchBlob from 'rn-fetch-blob';

const Chat = () => {
  const [text, setText] = useState<string>('');
  const [isTexting, setIsTexting] = useState<boolean>(false);

  const [isRecordStart, setIsRecordStart] = useState<boolean>(false);
  const [isClickRecordModal, setIsClickRecordModal] = useState<boolean>(false);

  const [seconds, setSeconds] = useState(0);

  const intervalRef = useRef<NodeJS.Timeout>();
  const scrollViewRef = useRef<any>(null); // ScrollView ref 선언
  const audioRecorder = useRef<AudioRecorderPlayer>();

  const [recordUri, setRecordUri] = useState<string>('');

  const dirs = RNFetchBlob.fs.dirs;
  const path = Platform.select({
    ios: 'sound.m4a',
    android: `${dirs.CacheDir}/sound.mp4`,
  });

  const checkRecord = async () => {
    try {
      const result = await request(PERMISSIONS.IOS.SPEECH_RECOGNITION);
      if (result === RESULTS.BLOCKED) {
      }
    } catch (e) {
      console.log(`에러 \n ${e}`);
    }
  };

  useEffect(() => {
    audioRecorder.current = new AudioRecorderPlayer();
    checkRecord();
  }, []);

  const audioToggle = () => {
    if (isRecordStart) {
      onStopRecord();
    } else {
      onStartRecord();
    }
  };

  const onStartRecord = async () => {
    audioRecorder.current = new AudioRecorderPlayer();
    if (audioRecorder.current) {
      const uri = await audioRecorder.current.startRecorder(path);
      setRecordUri(uri);
      console.log(uri);
      setIsRecordStart(true);
      setSeconds(0);
      // timer
      if (intervalRef.current) {
        clearInterval(intervalRef.current);
      }
      intervalRef.current = setInterval(() => {
        setSeconds(prevTime => prevTime + 1);
      }, 1000);
    }
  };

  const onStopRecord = async () => {
    if (audioRecorder.current) {
      setIsRecordStart(false);
      await audioRecorder.current.stopRecorder();
    }
  };

  const soundStart = async () => {
    console.log('onStartPlay');
    const msg = await audioRecorder.current?.startPlayer();
    console.log(msg);
    if (audioRecorder.current) {
      uploadFile();
    }
  };

  const pausePlay = async () => {
    await audioRecorder.current?.stopPlayer();
  };

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
          nextChat = timeConverter(chat[i + 1].chatDate);
        }
        // 유저가 보낸 채팅
        if (Number(el.type) === 0) {
          const convertedChatDate = timeConverter(el.chatDate);
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
          const convertedChatDate = timeConverter(el.chatDate);
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

  const timeConverter = (time: string) => {
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

  const textSend = (): void => {
    if (text.length > 0) {
      Alert.alert(text);
    }
  };

  useEffect(() => {
    scrollViewRef.current.scrollToEnd({animated: true});
  }, []);

  useEffect(() => {
    return () => {
      if (intervalRef.current) {
        clearInterval(intervalRef.current);
      }
    };
  });

  const convertSecondsToTime = (second: number): string => {
    const minutes = Math.floor(second / 60);
    const remainingSeconds = second % 60;
    const minutesStr = String(minutes).padStart(2, '0');
    const secondsStr = String(remainingSeconds).padStart(2, '0');
    return `${minutesStr}:${secondsStr}`;
  };

  return (
    <Container>
      <TouchableWithoutFeedback onPress={() => handleTextingKeyDismiss()}>
        <ChatSectionContainer ref={scrollViewRef}>
          {renderOfChat()}
        </ChatSectionContainer>
      </TouchableWithoutFeedback>
      <Modal
        animationType={'fade'}
        transparent={true}
        visible={isClickRecordModal}>
        <RecordingSectionContainer>
          <RecordingSectionOutSide
            onTouchStart={() => {
              if (!isRecordStart) {
                setIsClickRecordModal(false);
              }
            }}
          />
          <RecordingSection>
            <RecordingTitleText>음성메세지</RecordingTitleText>
            {isRecordStart ? (
              <RecordingBar>
                <RecordingTimer>{convertSecondsToTime(seconds)}</RecordingTimer>
              </RecordingBar>
            ) : (
              <RecordingBarDisable>
                <RecordingTimerDisable>00:00</RecordingTimerDisable>
              </RecordingBarDisable>
            )}
            <RecordingBtnSection>
              <DisplayNone />
              <RecordingBtn
                onTouchStart={() => {
                  audioToggle();
                }}>
                <RecordinBtnRedSection />
              </RecordingBtn>
              {audioRecorder.current && recordUri.length > 5 ? (
                <RecordingSendBtn
                  onPress={() => {
                    soundStart();
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
      <SendingSectionContainer>
        <TextInputSection
          value={text}
          onChangeText={handleTextChange}
          onFocus={handleTextKeyPress}
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
    </Container>
  );
};

export default Chat;
