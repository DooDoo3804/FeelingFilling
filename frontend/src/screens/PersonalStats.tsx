import React, {useState, useEffect} from 'react';
import {
  Container,
  TitleText,
  ThisMonthSavingContainer,
  ThisMonthSavingTitleContainer,
  ThisMonthSavingBodyContainer,
  ThisMonthSavingEmotion1,
  ThisMonthSavingEmotion2,
  ThisMonthSavingEmotion3,
  ThisMonthSavingPrice1,
  ThisMonthSavingPrice2,
  ThisMonthSavingPrice3,
  //
  TotalStatisticsContainer,
  TotalStatisticsTitleContainer,
  TotalStatisticsBodyContainer,
  //
  ThisMonthEmotionContainer,
  ThisMonthEmotionTitleContainer,
  ThisMonthEmotionBodyContainer,
  //
  CumulativeAmountContainer,
  CumulativeAmountTitleContainer,
  CumulativeAmountBodyContainer,
} from '../styles/PersonalStatsStyle';
// import Lottie from 'lottie-react-native';
import {VictoryChart, VictoryBar} from 'victory-native';
import {Common} from '../components/Common';
import EmoAngry from '../assets/emo_angry.png';
import EmoHappy from '../assets/emo_happy.png';
import EmoSad from '../assets/emo_sad.png';

interface DataType {
  emotion: string;
  totalCnt: number;
  totalPrice: number;
}

const PersonalStats = () => {
  const [data, setData] = useState<DataType[]>([]);
  useEffect(() => {
    const datas: DataType[] = [
      {emotion: 'sad', totalCnt: 2, totalPrice: 30000},
      {emotion: 'angry', totalCnt: 13, totalPrice: 80820},
      {emotion: 'joy', totalCnt: 5, totalPrice: 10000},
    ];
    const sortedData = datas.sort(function (a, b) {
      return b.totalPrice - a.totalPrice;
    });
    setData(sortedData);
  }, []);

  const priceConverter = (price: string) => {
    return price.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  };

  return (
    <Container>
      <ThisMonthSavingContainer>
        <ThisMonthSavingTitleContainer>
          <TitleText>이번 달 저금</TitleText>
        </ThisMonthSavingTitleContainer>
        <ThisMonthSavingBodyContainer>
          {data.length > 0 ? (
            <>
              <VictoryBar
                data={data}
                x="emotion"
                y="totalPrice"
                sortKey="totalPrice"
                sortOrder="ascending"
                cornerRadius={{top: 2, bottom: 2}}
                style={{
                  data: {
                    fill: (
                      {datum}, //datum은 VictoryBar에서 사용하는 데이터의 하나의 항목을 나타내는 객체
                    ) =>
                      datum.emotion === 'angry'
                        ? Common.colors.emotionColor01
                        : datum.emotion === 'joy'
                        ? Common.colors.emotionColor02
                        : Common.colors.emotionColor03,
                    width: 15, // 막대 두께
                  },
                }}
                horizontal={true}
                padding={{top: 30, bottom: 180, left: 10, right: 220}}
                labels={({datum}) => `${datum.totalCnt}회`}
              />
              <ThisMonthSavingPrice1>
                {priceConverter(data[0].totalPrice + '')} 원
              </ThisMonthSavingPrice1>
              <ThisMonthSavingEmotion1
                source={
                  data[0].emotion === 'angry'
                    ? EmoAngry
                    : data[0].emotion === 'joy'
                    ? EmoHappy
                    : EmoSad
                }
              />
              <ThisMonthSavingPrice2>
                {priceConverter(data[1].totalPrice + '')} 원
              </ThisMonthSavingPrice2>
              <ThisMonthSavingEmotion2
                source={
                  data[1].emotion === 'angry'
                    ? EmoAngry
                    : data[1].emotion === 'joy'
                    ? EmoHappy
                    : EmoSad
                }
              />
              <ThisMonthSavingPrice3>
                {priceConverter(data[2].totalPrice + '')} 원
              </ThisMonthSavingPrice3>
              <ThisMonthSavingEmotion3
                source={
                  data[2].emotion === 'angry'
                    ? EmoAngry
                    : data[2].emotion === 'joy'
                    ? EmoHappy
                    : EmoSad
                }
              />
            </>
          ) : (
            ''
          )}
        </ThisMonthSavingBodyContainer>
      </ThisMonthSavingContainer>
      <TotalStatisticsContainer>
        <TotalStatisticsTitleContainer>
          <TitleText>월별 추이</TitleText>
        </TotalStatisticsTitleContainer>
        <TotalStatisticsBodyContainer>
          <VictoryChart>
            <VictoryBar
              data={data}
              x="emotion"
              y="totalPrice"
              horizontal={true}
            />
          </VictoryChart>
        </TotalStatisticsBodyContainer>
      </TotalStatisticsContainer>
      <ThisMonthEmotionContainer>
        <ThisMonthEmotionTitleContainer>
          <TitleText>이번 달, 감정 최고조는?</TitleText>
        </ThisMonthEmotionTitleContainer>
        <ThisMonthEmotionBodyContainer></ThisMonthEmotionBodyContainer>
      </ThisMonthEmotionContainer>
      <CumulativeAmountContainer>
        <CumulativeAmountTitleContainer>
          <TitleText>저금 누적액</TitleText>
        </CumulativeAmountTitleContainer>
        <CumulativeAmountBodyContainer></CumulativeAmountBodyContainer>
      </CumulativeAmountContainer>
    </Container>
  );
};

export default PersonalStats;
