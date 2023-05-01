import React, {useEffect, useState} from 'react';
import Lottie from 'lottie-react-native';
import FontAwesome5Icon from 'react-native-vector-icons/FontAwesome5';
import {Defs, LinearGradient as LG, Stop} from 'react-native-svg';
import {
  VictoryAxis,
  VictoryChart,
  VictoryLabel,
  VictoryLine,
  VictoryPie,
  VictoryScatter,
} from 'victory-native';

import {useAxios} from '../hooks/useAxios';
import {Common} from '../components/Common';
import {
  Container,
  TitleText,
  ScreenScroll,
  PageContainer,
  EmoKingContainer,
  TextWrapper,
  EmoLottieWrapper,
  EmoKingText,
  ErrorText,
  TitleWrapper,
  LottieWrapper,
} from '../styles/OverallStatsStyle';

interface monthTotalType {
  emotion: string;
  amount: number;
  x: number;
  y: number;
}

interface prevTotalType {
  emotion: string;
  amount: number;
}

interface emotionKingType {
  count: number;
  amount: number;
}

interface totalMoneyType {
  // emotion: string;
  amount: number;
  x: number;
  y: number;
  label: string;
}

interface ApiResponse {
  message: string;
  emotionKing: emotionKingType;
  total: totalMoneyType[];
  totalThisMonth: monthTotalType[];
  yesterday: [prevTotalType[], prevTotalType[], prevTotalType[]];
}

const AngerGradient = () => (
  <Defs>
    <LG id="anger-gradient" x1="0" y1="0" x2="1" y2="0">
      <Stop offset="0%" stopColor="#FF6335" />
      <Stop offset="100%" stopColor="#e9cdc6" />
    </LG>
  </Defs>
);

const JoyGradient = () => (
  <Defs>
    <LG id="joy-gradient" x1="0" y1="0" x2="1" y2="0">
      <Stop offset="0%" stopColor="#FEAA23" />
      <Stop offset="100%" stopColor="#FFEFD4" />
    </LG>
  </Defs>
);

const SadGradient = () => (
  <Defs>
    <LG id="sad-gradient" x1="0" y1="0" x2="1" y2="0">
      <Stop offset="0%" stopColor="#223C88" />
      <Stop offset="100%" stopColor="#8490B3" />
    </LG>
  </Defs>
);

const OverallStats = () => {
  const [monthTotal, setMonthTotal] = useState<monthTotalType[] | null>([]);
  const [prevTotal, setPrevTotal] = useState<prevTotalType[][] | null>(null);
  const [emotionKing, setEmotionKing] = useState<emotionKingType | null>(null);
  const [totalMoney, setTotalMoney] = useState<totalMoneyType[] | null>(null);

  const {data, error} = useAxios<ApiResponse>(
    'http://k8a702.p.ssafy.io:8080/api/stat/all',
    'GET',
    null,
  );

  const amountConverter = (amount: number): string => {
    return Math.floor(amount)
      .toString()
      .replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  };

  useEffect(() => {
    if (data && data.message === 'success') {
      const totalAmount =
        data.total[0].amount + data.total[1].amount + data.total[2].amount + 1;

      let angerAmount = data.totalThisMonth[1].amount;
      let joyAmount = data.totalThisMonth[0].amount;
      let sadAmount = data.totalThisMonth[2].amount;

      if (angerAmount < 1) angerAmount = 0.1;
      if (joyAmount < 1) joyAmount = 0.1;
      if (sadAmount < 1) sadAmount = 0.1;

      setMonthTotal([
        {
          emotion: 'anger',
          amount: angerAmount,
          x: 1,
          y: 1,
        },
        {
          emotion: 'joy',
          amount: joyAmount,
          x: 2,
          y: 0,
        },
        {
          emotion: 'sadness',
          amount: sadAmount,
          x: 0,
          y: 0.2,
        },
      ]);

      setPrevTotal(data.yesterday);

      setEmotionKing(data.emotionKing);

      setTotalMoney([
        {
          x: 1,
          y: (data.total[0].amount * 100) / totalAmount,
          label: amountConverter(data.total[0].amount) + '원',
          amount: data.total[0].amount,
        },
        {
          x: 2,
          y: (data.total[1].amount * 100) / totalAmount,
          label: amountConverter(data.total[1].amount) + '원',
          amount: data.total[1].amount,
        },
        {
          x: 3,
          y: (data.total[2].amount * 100) / totalAmount,
          label: amountConverter(data.total[2].amount) + '원',
          amount: data.total[2].amount,
        },
      ]);
    }
  }, [data, error]);

  return (
    <Container>
      <ScreenScroll>
        <PageContainer>
          <TitleWrapper>
            <TitleText>이번 달 저금</TitleText>
            <LottieWrapper>
              <Lottie source={require('../assets/coin.json')} autoPlay loop />
            </LottieWrapper>
          </TitleWrapper>
          <VictoryChart
            height={250}
            padding={{top: 90, bottom: 80, left: 60, right: 130}}>
            <AngerGradient />
            <JoyGradient />
            <SadGradient />
            <VictoryAxis
              style={{
                axis: {stroke: ''},
              }}
              tickFormat={() => ''}
            />
            <VictoryAxis
              dependentAxis
              style={{
                axis: {stroke: ''},
              }}
              tickFormat={() => ''}
            />
            <VictoryScatter
              data={monthTotal}
              style={{
                data: {
                  fill: (
                    {datum}, //datum은 VictoryBar에서 사용하는 데이터의 하나의 항목을 나타내는 객체
                  ) =>
                    datum.emotion === 'anger'
                      ? 'url(#anger-gradient)'
                      : datum.emotion === 'joy'
                      ? 'url(#joy-gradient)'
                      : 'url(#sad-gradient)',
                },
              }}
              bubbleProperty="amount"
              maxBubbleSize={80}
              minBubbleSize={60}
              height={250}
              labels={({datum}) => amountConverter(datum.amount)}
              labelComponent={
                <VictoryLabel
                  style={[
                    // eslint-disable-next-line react-native/no-inline-styles
                    {
                      fontFamily: 'NotoSansKR-Bold',
                      fontSize: 20,
                      dy: 20,
                      color: Common.colors.white01,
                    },
                  ]}
                />
              }
            />
          </VictoryChart>

          <TitleText>전날 저금 추이</TitleText>
          {prevTotal && (
            <VictoryChart
              height={260}
              style={{
                background: {fill: Common.colors.backgroundColor01},
              }}
              domainPadding={{x: 5, y: 5}}
              minDomain={{y: -40}}
              // maxDomain={{y: 26000}}
              padding={{top: 20, bottom: 50, left: 60, right: 50}}>
              <VictoryAxis
                style={{
                  axis: {stroke: ''},
                  tickLabels: {
                    fontSize: 14,
                    fill: Common.colors.selectGrey,
                  },
                }}
                tickFormat={hour => `${hour}시`}
              />
              <VictoryAxis
                dependentAxis
                style={{
                  axis: {stroke: ''},
                  tickLabels: {
                    fontSize: 12,
                    fill: Common.colors.selectGrey,
                  },
                }}
                tickFormat={t => amountConverter(t)}
              />
              <VictoryLine
                data={prevTotal[0]}
                x="hour"
                y="amount"
                interpolation="natural"
                style={{
                  data: {
                    stroke: Common.colors.emotionColor01,
                    strokeWidth: 2,
                    strokeLinecap: 'round',
                  },
                }}
              />
              <VictoryLine
                data={prevTotal[1]}
                x="hour"
                y="amount"
                interpolation="natural"
                style={{
                  data: {
                    stroke: Common.colors.emotionColor02,
                    strokeWidth: 2,
                    strokeLinecap: 'round',
                  },
                }}
              />
              <VictoryLine
                data={prevTotal[2]}
                x="hour"
                y="amount"
                interpolation="natural"
                style={{
                  data: {
                    stroke: Common.colors.emotionColor03,
                    strokeWidth: 2,
                    strokeLinecap: 'round',
                  },
                }}
              />
            </VictoryChart>
          )}
          <EmoKingContainer>
            <TextWrapper>
              {emotionKing ? (
                <>
                  <EmoKingText>
                    이번달 감정 왕{'  '}
                    <FontAwesome5Icon name="crown" color="#FFE24B" size={20} />
                  </EmoKingText>
                  <EmoKingText>{emotionKing.count}회 저금</EmoKingText>
                  <EmoKingText>
                    저금액 {amountConverter(emotionKing.amount)}원
                  </EmoKingText>
                </>
              ) : (
                <>
                  <EmoKingText>필링필링을 이용하고</EmoKingText>
                  <EmoKingText>매달 감정 왕에</EmoKingText>
                  <EmoKingText>도전해보세요!</EmoKingText>
                </>
              )}
            </TextWrapper>
            <EmoLottieWrapper>
              <Lottie
                source={require('../assets/emotion_king.json')}
                autoPlay
                loop
              />
            </EmoLottieWrapper>
          </EmoKingContainer>
          <TitleWrapper>
            <TitleText>저금 총 누적액</TitleText>
            <LottieWrapper>
              <Lottie source={require('../assets/coin.json')} autoPlay loop />
            </LottieWrapper>
          </TitleWrapper>
          {totalMoney ? (
            <VictoryPie
              data={totalMoney}
              innerRadius={100}
              labelRadius={({innerRadius}) => innerRadius + 2}
              radius={70}
              cornerRadius={10}
              padAngle={3}
              colorScale={[
                Common.colors.emotionColor01,
                Common.colors.emotionColor02,
                Common.colors.emotionColor03,
              ]}
              style={{labels: {fontSize: 14, fontWeight: 'bold'}}}
              padding={{top: -20, bottom: 0, left: 100, right: 140}}
              animate={{
                easing: 'exp',
                duration: 12000,
              }}
              height={280}
            />
          ) : (
            <ErrorText>정보를 불러올 수 없어요.</ErrorText>
          )}
        </PageContainer>
      </ScreenScroll>
    </Container>
  );
};

export default OverallStats;
