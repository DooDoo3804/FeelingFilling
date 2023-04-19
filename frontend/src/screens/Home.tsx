import React from 'react';
import {Text, Button} from 'react-native';
import styled from 'styled-components/native';

import {useSelector, useDispatch} from 'react-redux';
import {toggleProgress} from '../redux';
import type {AppState} from '../redux';

const Container = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Home = ({navigation}: {navigation: any}) => {
  // 로딩중 화면 설정하는 함수
  const inProgress = useSelector<AppState, boolean>(state => state.inProgress);
  const dispatch = useDispatch();

  const handleProgress = () => {
    dispatch(toggleProgress(!inProgress));
  };

  return (
    <Container>
      <Text>Home</Text>
      <Button
        title="go somewhere"
        onPress={() => navigation.navigate('Saving')}
      />
      <Button title="click" onPress={() => handleProgress()} />
    </Container>
  );
};

export default Home;
