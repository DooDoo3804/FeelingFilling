import React from 'react';
import {Container, ScreenScroll, TitleText} from '../styles/OverallStatsStyle';

const OverallStats = () => {
  // 전체 통계 보여주는 화면입니다.
  return (
    <Container>
      <ScreenScroll>
        <TitleText>이번 달 저금</TitleText>
      </ScreenScroll>
    </Container>
  );
};

export default OverallStats;
