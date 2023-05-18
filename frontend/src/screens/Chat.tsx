import React, {useState, useEffect, useRef, useCallback} from 'react';
import axios from 'axios';
import {
  Platform,
  Keyboard,
  TouchableWithoutFeedback,
  LogBox,
  Modal,
  View,
} from 'react-native';
/////// components ///////
import {Common} from '../components/Common';
import ErrorModal from '../components/ErrorModal';
import OkModal from '../components/OkModal';
import DeleteModal from '../components/DeleteModal';
import AnalyzingModal from '../components/AnalyzingModal';
/////// style ///////
import FontawesomeIcon5 from 'react-native-vector-icons/FontAwesome5';
import Lottie from 'lottie-react-native';
import {
  Container,
  AnalyzingButton,
  ChatSectionContainer,
  DateContainer,
  DateTextSection,
  DisplayNone,
  InputBtn,
  MsgTimeSection,
  MsgTimeText,
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
  SendMsgText,
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
import {useAxiosWithRefreshToken} from '../hooks/useAxioswithRfToken';
/////// png ///////
import ChatMoneyPng from '../assets/chat_money.png';
import EmoAngry from '../assets/emo_angry.png';
import EmoSad from '../assets/emo_sad.png';
import EmoHappy from '../assets/emo_happy.png';
LogBox.ignoreAllLogs();
/////// recording, file ///////
import {PERMISSIONS, RESULTS, request} from 'react-native-permissions';
import AudioRecorderPlayer from 'react-native-audio-recorder-player';
import RNFetchBlob from 'rn-fetch-blob';
/////// redux
import {useSelector} from 'react-redux';
import {AppState, User} from '../redux';
//
import SaveBtn from '../components/SaveBtn';

export interface ChatDataType {
  chattingId: number;
  content: string;
  chatDate: string;
  type: number;
  mood: string;
  amount: number;
}

const Chat = () => {
  // user info
  const user = useSelector<AppState, User | null>(state => state.loggedUser);

  const backendChatUri = 'https://feelingfilling.store/api/';
  const backendRecordUri = 'https://feelingfilling.store/feelings/voice';

  const {data, error} = useAxiosWithRefreshToken<any>(
    backendChatUri + 'chatting?page=' + 1,
    'GET',
    null,
  );

  const [chattings, setChattings] = useState<ChatDataType[]>([]);

  // modal control
  const [errorModalView, setErrorModalView] = useState<boolean>(false);
  const [okModalView, setOkModalView] = useState<boolean>(false);
  const [deleteModalView, setDeleteModalView] = useState<boolean>(false);
  const [analyzingModalView, setAnalyzingModalView] = useState<boolean>(false);

  // for msg
  const [targetChatId, setTargetChatId] = useState<number>(0);
  const [text, setText] = useState<string>('');
  const [isTexting, setIsTexting] = useState<boolean>(false);

  // for recording
  const [isRecordStart, setIsRecordStart] = useState<boolean>(false);
  const [isClickRecordModal, setIsClickRecordModal] = useState<boolean>(false);
  const audioRecorder = useRef<AudioRecorderPlayer>();

  // scroll, page control
  const scrollViewRef = useRef<any>(null); // ScrollView ref 선언
  const chatContentRef = useRef<any>(null);
  const [totalChatHeight, setTotalChatHeight] = useState<number>(0);
  const [page, setPage] = useState<number>(2);
  const [hasNextPage, setHasNextPage] = useState<boolean>(true);

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
  // 첫 페이지 채팅 data setting
  useEffect(() => {
    scrollViewRef.current.scrollToEnd({animated: true});
    const chatData = data?.chattings as ChatDataType[];
    setChattings(chatData);
  }, [data]);

  // 처음 페이지 마운팅시 recorder 객체를 생성
  useEffect(() => {
    audioRecorder.current = new AudioRecorderPlayer();
    if (error) {
      setErrorModalView(true);
    }
    axios
      .get(backendChatUri + 'chatting/exist', {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${user?.access_token}`,
        },
      })
      .then((res: any) => {
        console.log(res.data);
      })
      .catch((err: any) => {
        console.log(err);
      });
  }, [error, user?.access_token]);

  // 채팅 data get Func
  const nextChatdataRend = () => {
    axios
      .get(backendChatUri + 'chatting?page=' + page, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${user?.access_token}`,
        },
      })
      .then((res: any) => {
        setPage(page + 1);
        const newChats = res.data.chattings;
        // eslint-disable-next-line @typescript-eslint/no-shadow
        setChattings(chattings => [...newChats, ...chattings]);
        setHasNextPage(newChats.length < 20 ? false : true);
      })
      .catch((err: any) => {
        console.log(err);
      });
  };

  // 권한 상태 check
  const checkRecord = async () => {
    try {
      const result = await request(PERMISSIONS.IOS.SPEECH_RECOGNITION);
      if (result === RESULTS.BLOCKED) {
        return false;
      }
      return true;
    } catch (e) {
      console.log(`에러 \n ${e}`);
      return false;
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
    if (await checkRecord()) {
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
            recordTime: audioRecorderPlayer.mmssss(
              Math.floor(e.currentPosition),
            ),
          });
        });
        setRecordUri(uri);
      }
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
    setAnalyzingModalView(true);
    const formData = new FormData();
    formData.append('file', {
      uri: recordUri,
      type: 'audio/mp4',
      name: 'audio.mp4',
    });
    axios
      .post(backendRecordUri, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          Authorization: `Bearer ${user?.access_token}`,
        },
      })
      .then(response => {
        const responseChat = response.data as ChatDataType[];
        setAnalyzingModalView(false);
        // eslint-disable-next-line @typescript-eslint/no-shadow
        setChattings(chattings => [...chattings, ...responseChat]);
        resetRecordingSetting();
      })
      // eslint-disable-next-line @typescript-eslint/no-shadow
      .catch(error => {
        setErrorModalView(true);
        setAnalyzingModalView(false);
        console.log(error);
      });
  };

  // 레코딩 세팅 초기화
  const resetRecordingSetting = () => {
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
  };

  // 채팅 속 날짜 변환기
  const dateConverter = (input: string) => {
    const chatsDate = new Date(input);
    const year = chatsDate.getFullYear() + '년';
    const month =
      (chatsDate.getMonth() === 12 ? 1 : chatsDate.getMonth() + 1) + '월';
    const date = chatsDate.getDate() + '일';
    const dayArr = ['일', '월', '화', '수', '목', '금', '토'];
    const day = dayArr[chatsDate.getDay()] + '요일';
    return year + ' ' + month + ' ' + date + ' ' + day;
  };

  // 가격에 , 단위 구분자 넣기
  const priceConverter = (price: string) => {
    return price.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  };

  // 채팅 보낸 시간 표시기
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
        return (
          '오전 ' + '0' + hour + ':' + (minute < 10 ? '0' + minute : minute)
        );
      } else {
        return '오전 ' + hour + ':' + (minute < 10 ? '0' + minute : minute);
      }
    } else {
      if (hour === 0) {
        hour = 12;
      } else if (hour > 12) {
        hour -= 12;
      }
      if (hour < 10) {
        return (
          '오후 ' + '0' + hour + ':' + (minute < 10 ? '0' + minute : minute)
        );
      } else {
        return '오후 ' + hour + ':' + (minute < 10 ? '0' + minute : minute);
      }
    }
  };

  // 사용자가 text인풋을 시작하면, scroll을 맨 아래로 내려줍니다.
  const handleTextKeyPress = () => {
    setIsTexting(true);
    scrollViewRef.current.scrollToEnd({animated: true});
  };

  const handleTextingKeyDismiss = () => {
    Keyboard.dismiss();
    setIsTexting(false);
  };

  const handleTextChange = (inputText: string) => {
    setText(inputText);
  };

  // text 전송
  const handleTextSubmit = () => {
    if (text.length > 0) {
      axios
        .post(
          'https://feelingfilling.store/api/chatting',
          {
            content: text,
            type: 0,
          },
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${user?.access_token}`,
            },
          },
        )
        .then(response => {
          const chat: ChatDataType[] = [];
          chat.push(response.data.chatting);
          // eslint-disable-next-line @typescript-eslint/no-shadow
          setChattings(chattings => [...chattings, ...chat]);
          setText('');
          scrollViewRef.current.scrollToEnd({animated: true});
        })
        // eslint-disable-next-line @typescript-eslint/no-shadow
        .catch(error => {
          setErrorModalView(true);
          console.log(error);
        });
    }
  };

  // 채팅 분석 요청 함수
  const textAnalyzingReq = () => {
    axios
      .get('https://feelingfilling.store/api/chatting/analyze', {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${user?.access_token}`,
        },
      })
      .then(response => {
        console.log(response.data);
      })
      // eslint-disable-next-line @typescript-eslint/no-shadow
      .catch(error => {
        setErrorModalView(true);
        console.log(error);
      });
  };

  // 채팅 삭제 모달
  const handleChatDelete = (chattingId: number) => {
    setDeleteModalView(true);
    setTargetChatId(chattingId);
  };

  const onScrollChat = (e: any) => {
    if (e.nativeEvent.contentOffset.y === 0 && hasNextPage) {
      nextChatdataRend();
      setTotalChatHeight(e.nativeEvent.contentSize.height);
    }
  };

  const onChangeChatSize = useCallback(() => {
    chatContentRef.current.measure(
      (x: number, y: number, width: number, height: number) => {
        scrollViewRef.current.scrollTo({
          x: 0,
          y: height - totalChatHeight,
          animated: false,
        });
      },
    );
  }, [totalChatHeight]);

  /////////////////////////
  // Rendering func

  // chat render
  const renderOfChat = () => {
    const result: any[] = [];
    let size = 0;
    if (chattings?.length) {
      size = chattings.length;
    }
    for (let i = 0; i < size; i++) {
      const chat = chattings[i];
      if (chat.type === 3) {
        result.push(
          <DateContainer key={chat.chatDate + i}>
            <FontawesomeIcon5
              name="calendar-check"
              color={'#484848'}
              size={14}
            />
            <DateTextSection>{dateConverter(chat.chatDate)} </DateTextSection>
          </DateContainer>,
        );
      }
      let nextChat = '';
      if (
        i < size - 1 &&
        chat.chatDate.length > 5 &&
        chattings[i + 1].type === 0
      ) {
        nextChat = chatTimeConverter(chattings[i + 1].chatDate);
      }
      // 유저가 보낸 채팅
      if (Number(chat.type) === 0) {
        const convertedChatDate = chatTimeConverter(chat.chatDate);
        result.push(
          <SendingChatContainer key={chat.chattingId}>
            {convertedChatDate === nextChat ? (
              ''
            ) : (
              <MsgTimeSection>
                <MsgTimeText>{convertedChatDate}</MsgTimeText>
              </MsgTimeSection>
            )}
            <SendMsgSection
              onLongPress={() => {
                handleChatDelete(chat.chattingId);
              }}>
              <SendMsgText>{chat.content}</SendMsgText>
            </SendMsgSection>
          </SendingChatContainer>,
        );
      } else if (Number(chat.type) === 1) {
        const convertedChatDate = chatTimeConverter(chat.chatDate);
        result.push(
          <SendingChatContainer key={chat.chattingId}>
            {convertedChatDate === nextChat ? (
              ''
            ) : (
              <MsgTimeSection>
                <MsgTimeText>{convertedChatDate}</MsgTimeText>
              </MsgTimeSection>
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
      } else if (Number(chat.type) === 2) {
        if (chat.mood === 'anger') {
          result.push(
            <View key={chat.chattingId}>
              <ReceivedChatContainer>
                <ReceiveMsgSection color={Common.colors.chatColor01}>
                  {chat.content}
                </ReceiveMsgSection>
              </ReceivedChatContainer>
              <ReceivedChatContainer>
                <ReceivePriceSection
                  backgroundColor={Common.colors.chatColor01}
                  color={Common.colors.receivedChatColor01}>
                  {'+ ' + priceConverter(chat.amount + '') + '₩'}
                  <EmotionPngContainer source={EmoAngry} />
                </ReceivePriceSection>
                <ChatMoneyPngContainer source={ChatMoneyPng} />
              </ReceivedChatContainer>
            </View>,
          );
        } else if (chat.mood === 'joy') {
          result.push(
            <View key={chat.chattingId}>
              <ReceivedChatContainer>
                <ReceiveMsgSection color={Common.colors.chatColor02}>
                  {chat.content}
                </ReceiveMsgSection>
              </ReceivedChatContainer>
              <ReceivedChatContainer>
                <ReceivePriceSection
                  backgroundColor={Common.colors.chatColor02}
                  color={Common.colors.receivedChatColor02}>
                  {'+ ' + priceConverter(chat.amount + '') + '₩'}
                  <EmotionPngContainer source={EmoHappy} />
                </ReceivePriceSection>
                <ChatMoneyPngContainer source={ChatMoneyPng} />
              </ReceivedChatContainer>
            </View>,
          );
        } else {
          result.push(
            <View key={chat.chattingId}>
              <ReceivedChatContainer>
                <ReceiveMsgSection color={Common.colors.chatColor03}>
                  {chat.content}
                </ReceiveMsgSection>
              </ReceivedChatContainer>
              <ReceivedChatContainer>
                <ReceivePriceSection
                  backgroundColor={Common.colors.chatColor03}
                  color={Common.colors.receivedChatColor03}>
                  {'+ ' + priceConverter(chat.amount + '') + '₩'}
                  <EmotionPngContainer source={EmoSad} />
                </ReceivePriceSection>
                <ChatMoneyPngContainer source={ChatMoneyPng} />
              </ReceivedChatContainer>
            </View>,
          );
        }
      }
    }
    return result;
  };

  return (
    <Container>
      <TouchableWithoutFeedback onPress={() => handleTextingKeyDismiss()}>
        <ChatSectionContainer
          ref={scrollViewRef}
          onScroll={onScrollChat}
          onContentSizeChange={onChangeChatSize}>
          <View ref={chatContentRef}>{renderOfChat()}</View>
        </ChatSectionContainer>
      </TouchableWithoutFeedback>
      {/* text 채팅 분석 버튼ㄹ */}
      <AnalyzingButton>
        <SaveBtn clickFunc={textAnalyzingReq} />
      </AnalyzingButton>
      <SendingSectionContainer>
        <TextInputSection
          value={text}
          onChangeText={handleTextChange}
          onFocus={handleTextKeyPress}
          onSubmitEditing={handleTextSubmit}
        />
        {isTexting ? (
          <InputBtn onPress={handleTextSubmit}>
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
      {/* 분석중 모달 */}
      <AnalyzingModal showAnalyzingModal={analyzingModalView} />
      {/* 삭제 모달 */}
      <DeleteModal
        showDeleteModal={deleteModalView}
        setShowDeleteModal={setDeleteModalView}
        targetChatId={targetChatId}
        chattings={chattings}
        setChattings={setChattings}
      />
      {/* 오류 모달 */}
      <ErrorModal
        showErrorModal={errorModalView}
        setShowErrorModal={setErrorModalView}
      />
      {/* 성공 모달 */}
      <OkModal
        showOkModal={okModalView}
        setShowOkModal={setOkModalView}
        msg="test"
      />
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
