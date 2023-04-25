import styled, {css} from 'styled-components/native';
import {Common} from '../components/Common';
import {Platform} from 'react-native';

// 전체 컨테이너
export const Container = styled.View`
  flex: 1;
`;

// 채팅창 컨테이너
export const ChatSectionContainer = styled.ScrollView`
  flex: 10;
  background-color: ${Common.colors.backgroundColor01};
`;

// 오늘 날짜 표시
export const DateContainer = styled.View`
  flex-direction: row;
  background-color: ${Common.colors.white01};
  justify-content: center;
  align-items: center;
  margin-left: auto;
  margin-right: auto;
  padding: 5px 15px 5px 15px;
  border-radius: 20px;
  margin-bottom: 20px;
  margin-top: 20px;
`;

export const DateTextSection = styled.Text`
  color: ${Common.colors.black01};
  margin-left: 10px;
  font-size: 14px;
  line-height: 24px;
`;

export const MsgTimeSection = styled.Text`
  color: ${Common.colors.selectGrey};
  font-size: 12px;
`;

// 보내는 메세지
export const SendingChatContainer = styled.View`
  margin-left: auto;
  margin-right: 20px;
  margin-bottom: 15px;
  flex-direction: row;
  align-items: center;
`;

// 받는 메세지
export const ReceivedChatContainer = styled.View`
  margin-right: auto;
  margin-left: 17px;
  margin-bottom: 10px;
  flex-direction: row;
  align-items: center;
`;

export const SendMsgSection = styled.Text`
  margin-left: 10px;
  font-size: 14px;
  line-height: 24px;
  color: ${Common.colors.black01};
  padding: 5px 13px 5px 13px;
  background-color: ${Common.colors.sendingChatColor01};
  border-radius: 20px;
  ${Platform.select({
    android: css`
      elevation: 3;
    `,
  })}
`;

export const ReceiveMsgSection = styled.Text<{color: string}>`
  margin-left: 10px;
  font-size: 14px;
  line-height: 24px;
  color: ${Common.colors.black01};
  padding: 5px 13px 5px 13px;
  background-color: ${(props: any) => props.color};
  border-radius: 20px;
  ${Platform.select({
    android: css`
      elevation: 3;
    `,
  })}
`;

export const ReceivePriceSection = styled.Text<{
  backgroundColor: string;
  color: string;
}>`
  opacity: 0.9;
  margin-right: 10px;
  margin-left: 10px;
  font-size: 14px;
  line-height: 26px;
  color: ${(props: any) => props.color};
  padding: 0px 8px 8px 13px;
  background-color: ${(props: any) => props.backgroundColor};
  border-radius: 20px;
  ${Platform.select({
    android: css`
      elevation: 3;
    `,
  })}
`;

export const ChatMoneyPngContainer = styled.Image`
  width: 35px;
  height: 35px;
`;

export const EmotionPngContainer = styled.Image`
  width: 35px;
  height: 35px;
`;

// 인풋섹션
export const SendingSectionContainer = styled.View`
  height: 65px;
  background-color: ${Common.colors.white01};
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

export const TextInputSection = styled.TextInput`
  background-color: ${Common.colors.lightGrey};
  border-radius: 30px;
  width: 80%;
  height: 65%;
  padding-left: 20px;
  margin-left: 10px;
`;

export const InputBtn = styled.TouchableOpacity`
  width: 35px;
  height: 35px;
  margin-right: 10px;
  background-color: ${Common.colors.emotionColor01};
  justify-content: center;
  align-items: center;
  border-radius: 30px;
`;

export const VoiceText = styled.Text`
  margin: 32px;
`;

export const RecordingSectionContainer = styled.View`
  background-color: rgba(0, 0, 0, 0.2);
  width: 100%;
  height: 100%;
  justify-content: flex-end;
  align-items: center;
`;

export const RecordingSectionOutSide = styled.View`
  width: 100%;
  height: 100%;
`;

export const RecordingSection = styled.View`
  border-top-right-radius: 20px;
  border-top-left-radius: 20px;
  background-color: ${Common.colors.white01};
  width: 100%;
  height: 180px;
  align-items: center;
`;

export const RecordingTitleText = styled.Text`
  color: ${Common.colors.black01};
  font-size: 18px;
  margin: 15px;
`;

export const RecordingBar = styled.View`
  align-items: flex-end;
  width: 90%;
  height: 40px;
  background-color: ${Common.colors.emotionColor02};
  border-radius: 30px;
  margin-bottom: 30px;
`;

export const RecordingBarDisable = styled.View`
  align-items: flex-end;
  width: 90%;
  height: 40px;
  background-color: ${Common.colors.lightGrey};
  border-radius: 30px;
  margin-bottom: 30px;
`;

export const RecordingTimerDisable = styled.Text`
  line-height: 40px;
  margin-right: 20px;
  font-size: 18px;
  color: ${Common.colors.selectGrey};
`;

export const RecordingTimer = styled.Text`
  line-height: 40px;
  margin-right: 20px;
  font-size: 18px;
  color: ${Common.colors.deepGrey};
`;

export const RecordingBtnSection = styled.View`
  flex-direction: row;
  width: 90%;
  justify-content: space-between;
  align-items: center;
`;

export const DisplayNone = styled.View`
  width: 30px;
  height: 30px;
`;

export const RecordinBtnRedSection = styled.View`
  background-color: ${Common.colors.recordingRed};
  width: 22px;
  height: 22px;
  border-radius: 22px;
`;

export const RecordingBtn = styled.View`
  border-radius: 35px;
  width: 35px;
  height: 35px;
  background-color: ${Common.colors.lightGrey};
  justify-content: center;
  align-items: center;
`;

export const RecordingSendBtn = styled.TouchableOpacity`
  border-radius: 30px;
  width: 35px;
  height: 35px;
  background-color: ${Common.colors.emotionColor01};
  justify-content: center;
  align-items: center;
`;

export const RecordingSendBtnDisable = styled.TouchableOpacity`
  border-radius: 30px;
  width: 35px;
  height: 35px;
  background-color: ${Common.colors.lightGrey};
  justify-content: center;
  align-items: center;
`;
