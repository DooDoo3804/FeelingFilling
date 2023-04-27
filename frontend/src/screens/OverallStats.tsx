import React, {useEffect, useState} from 'react';
import Lottie from 'lottie-react-native';
import {
  VictoryAxis,
  VictoryChart,
  VictoryLabel,
  VictoryLine,
  VictoryPie,
  VictoryScatter,
} from 'victory-native';
import FontAwesome5Icon from 'react-native-vector-icons/FontAwesome5';

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
} from '../styles/OverallStatsStyle';
import {useAxios} from '../hooks/useAxios';

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
  // amount: number;
  x: number;
  y: number;
  label: string;
}

const OverallStats = () => {
  const [monthTotal, setMonthTotal] = useState<monthTotalType[]>([
    {emotion: 'happy', amount: 1, x: 2, y: 0},
    {emotion: 'sad', amount: 1, x: 0, y: 0},
    {emotion: 'angry', amount: 1, x: 1, y: 1},
  ]);

  const [prevTotal, setPrevTotal] = useState<prevTotalType[][]>([[]]);

  const [emotionKing, setEmotionKing] = useState<emotionKingType>({
    count: 0,
    amount: 0,
  });

  const [totalMoney, setTotalMoney] = useState<totalMoneyType[]>([
    {x: 1, y: 5, label: 'asdfasdfd원'},
    {x: 2, y: 5, label: 'asdfasdfd원'},
    {x: 3, y: 5, label: 'asdfasdfd원'},
  ]);

  const {data, error} = useAxios(
    'http://k8a702.p.ssafy.io:8080/api/stat/all',
    'GET',
    null,
  );
  // console.log(data);
  // console.log(error);

  const amountConverter = (amount: number): string => {
    return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  };

  useEffect(() => {
    setMonthTotal([
      {emotion: 'anger', amount: 218342300, x: 1, y: 1},
      {emotion: 'joy', amount: 10156730, x: 2, y: 0},
      {emotion: 'sadness', amount: 5134820, x: 0, y: 0.2},
    ]);

    // setPrevTotal()

    setTotalMoney([
      {x: 1, y: 35, label: 'asdfasdfd원'},
      {x: 2, y: 40, label: 'asdfasdfd원'},
      {x: 3, y: 55, label: 'asdfasdfd원'},
    ]);
  }, []);

  return (
    <Container>
      <ScreenScroll>
        <PageContainer>
          <TitleText>이번 달 저금</TitleText>
          <VictoryScatter
            data={monthTotal}
            style={{
              data: {
                fill: (
                  {datum}, //datum은 VictoryBar에서 사용하는 데이터의 하나의 항목을 나타내는 객체
                ) =>
                  datum.emotion === 'anger'
                    ? Common.colors.emotionColor01
                    : datum.emotion === 'joy'
                    ? Common.colors.emotionColor02
                    : Common.colors.emotionColor03,
              },
            }}
            bubbleProperty="amount"
            maxBubbleSize={80}
            minBubbleSize={60}
            labels={({datum}) => amountConverter(datum.amount)}
            // animate={{
            //   duration: 2000,
            //   easing: 'bounce',
            //   onLoad: {duration: 1000},
            // }}
            padding={{top: 90, bottom: 120, left: 60, right: 100}}
            labelComponent={
              <VictoryLabel
                style={[
                  {
                    fontFamily: 'NotoSansKR-Bold',
                    fontSize: 20,
                    dy: 20,
                  },
                ]}
              />
            }
          />
          <TitleText>전날 저금 추이</TitleText>
          {/* {prevTotal.length > 0 ? (
            <VictoryChart
              height={250}
              style={{
                background: {fill: '#F9F9F9'},
              }}
              domainPadding={{x: 5, y: 5}}
              padding={{top: 1, bottom: 30, left: 70, right: 50}}>
              <VictoryAxis
                style={{
                  axis: {stroke: ''},
                  tickLabels: {
                    fontSize: 14,
                    fill: Common.colors.selectGrey,
                  },
                }}
                tickFormat={month => `${month}월`}
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
                x="month"
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
                x="month"
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
                x="month"
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
          ) : null} */}
          <EmoKingContainer>
            <TextWrapper>
              <EmoKingText>
                이번달 감정 왕{'  '}
                <FontAwesome5Icon name="crown" color="#FFE24B" size={20} />
              </EmoKingText>
              <EmoKingText>98회 저금</EmoKingText>
              <EmoKingText>저금액 21,134,000원</EmoKingText>
            </TextWrapper>
            <EmoLottieWrapper>
              <Lottie
                source={require('../assets/emotion_king.json')}
                autoPlay
                loop
              />
            </EmoLottieWrapper>
          </EmoKingContainer>
          <TitleText>저금 총 누적액</TitleText>
          <VictoryPie
            data={totalMoney}
            innerRadius={120}
            labelRadius={({innerRadius}) => innerRadius + 5}
            radius={80}
            cornerRadius={10}
            padAngle={3}
            colorScale={[
              Common.colors.emotionColor01,
              Common.colors.emotionColor02,
              Common.colors.emotionColor03,
            ]}
            style={{labels: {fontSize: 14, fontWeight: 'bold'}}}
            padding={{top: -80, bottom: -50, left: 100, right: 120}}
            animate={{
              easing: 'exp',
              duration: 12000,
            }}
            events={[
              {
                target: 'data',
                eventHandlers: {
                  onPressIn: () => {
                    return [
                      {
                        target: 'data',
                        mutation: ({style}) => {
                          return style.fill === '#c43a31'
                            ? null
                            : {style: {fill: '#c43a31'}};
                        },
                      },
                      // {
                      //   target: 'data',
                      //   mutation: ({innerRadius}) => {
                      //     return innerRadius === 120 ? null : {innerRadius: 80};
                      //   },
                      // },
                    ];
                  },
                },
              },
            ]}
          />
        </PageContainer>
      </ScreenScroll>
    </Container>
  );
};

export default OverallStats;
