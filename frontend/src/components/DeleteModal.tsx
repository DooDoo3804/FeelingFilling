import React from 'react';
import {Modal, TouchableWithoutFeedback} from 'react-native';
import styled from 'styled-components/native';
import {Common} from './Common';
import Lottie from 'lottie-react-native';
import axios from 'axios';

/////// redux
import {useSelector} from 'react-redux';
import {AppState, User} from '../redux';

const DeleteModalContainer = styled.View`
  background-color: rgba(0, 0, 0, 0.2);
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
`;

const DeleteModalView = styled.View`
  width: 300px;
  height: 300px;
  background-color: ${Common.colors.white01};
  border-radius: 15px;
  justify-content: space-between;
  align-items: center;
`;

const LottieContainer = styled.View`
  margin-top: 20px;
  width: 180px;
  height: 180px;
`;

const DeleteHeaderText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-family: 'NotoSansKR-Bold';
  font-size: 15px;
  padding-left: 20px;
  padding-right: 20px;
  padding-bottom: 10px;
  text-align: center;
`;

const DeleteTextView = styled.View`
  width: 100%;
  height: 65px;
  background-color: ${Common.colors.emotionColor01};
  overflow: hidden;
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
  justify-content: center;
  align-items: center;
`;

const CloseText = styled.Text`
  font-size: 15px;
  font-family: 'NotoSansKR-Medium';
  color: ${Common.colors.white01};
`;

const DeleteModal = (props: any) => {
  const modalClose = () => {
    props.setShowDeleteModal(!props.showDeleteModal);
  };

  const user = useSelector<AppState, User | null>(state => state.loggedUser);

  return (
    <Modal
      animationType={'fade'}
      transparent={true}
      visible={props.showDeleteModal}>
      <TouchableWithoutFeedback onPress={modalClose}>
        <DeleteModalContainer>
          <TouchableWithoutFeedback>
            <DeleteModalView>
              <LottieContainer>
                <Lottie
                  source={require('../assets/delete.json')}
                  autoPlay
                  loop
                />
              </LottieContainer>
              <DeleteHeaderText>
                채팅을 삭제 하시겠습니까?{'\n'}삭제된 채팅은 복구할 수 없습니다.
              </DeleteHeaderText>
              <DeleteTextView
                onTouchStart={() => {
                  axios
                    .delete(
                      'https://feelingfilling.store/api/chatting?chattingId=' +
                        props.targetChatId,
                      {
                        headers: {
                          'Content-Type': 'application/json',
                          Authorization: `Bearer ${user?.access_token}`,
                        },
                      },
                    )
                    .then(response => {
                      console.log(response.data);
                      props.setChattings(
                        props.chattings.filter(
                          (e: any) => e.chattingId !== props.targetChatId,
                        ),
                      );
                      modalClose();
                    })
                    .catch(error => {
                      console.log(error);
                    });
                }}>
                <CloseText>삭제</CloseText>
              </DeleteTextView>
            </DeleteModalView>
          </TouchableWithoutFeedback>
        </DeleteModalContainer>
      </TouchableWithoutFeedback>
    </Modal>
  );
};

export default DeleteModal;
