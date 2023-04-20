import React from 'react';
import styled from 'styled-components/native';
import {Common} from '../components/Common';
import font_logo from '../assets/font_logo.png';

const Container = styled.View`
  flex: 1;
  flex-direction: column;
  background-color: ${Common.colors.white01};
  align-items: center;
  padding: 10px;
`;

const FontLogo = styled.Image`
  width: 200px;
  height: 200px;
`;

const Landing = () => {
  return (
    <Container>
      <FontLogo source={font_logo} />
    </Container>
  );
};

export default Landing;
