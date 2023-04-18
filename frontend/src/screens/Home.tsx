import React from 'react';
import {Text, Button} from 'react-native';
import styled from 'styled-components/native';

const Container = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
`;

const Home = ({navigation}: {navigation: any}) => {
  return (
    <Container>
      <Text>Home</Text>
      <Button
        title="go somewhere"
        onPress={() => navigation.navigate('Saving')}
      />
    </Container>
  );
};

export default Home;
