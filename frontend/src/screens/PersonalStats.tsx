import React, {useState, useEffect} from 'react';
import {
  Container,
  TitleText,
  LottieContainer1,
  LottieContainer2,
  //
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
  BestDateContainer,
  BestDateSmileContainer,
  BestDateText,
  BestSamllContainer,
  BestHourContainer,
  BestHourLottie,
  BestHourText,
  BestDayContainer,
  BestDayLottie,
  BestDayText,
  //
  CumulativeAmountContainer,
  CumulativeAmountTitleContainer,
  CumulativeAmountBodyContainer,
  AmountHeadingContainer,
  AmountHeadingLottieContainer,
  AmountHeadingTextContainer,
  AmountHeadingText1,
  AmountHeadingText2,
  AmountCoffeeContainer,
  AmountCoffeeLottieContainer,
  AmountCoffeeTextContainer,
  AmountBurgerContainer,
  AmountBurgerLottieContainer,
  AmountBurgerTextContainer,
  AmountText1,
  AmountText2,
} from '../styles/PersonalStatsStyle';
import Lottie from 'lottie-react-native';
import {
  VictoryChart,
  VictoryBar,
  VictoryLine,
  VictoryAxis,
} from 'victory-native';
import {Common} from '../components/Common';
import EmoAngry from '../assets/emo_angry.png';
import EmoHappy from '../assets/emo_happy.png';
import EmoSad from '../assets/emo_sad.png';
import LinearGradient from 'react-native-linear-gradient';

interface ThisMonthSavingDataType {
  emotion: string;
  cnt: number;
  amount: number;
}

interface TotalStatisticsDataType {
  emotion: string;
  month: number;
  amount: number;
}

interface EmotionHighDataType {
  date: number;
  hour: string;
  day: string;
}

interface TotalAmountDataType {
  total: number;
  coffee: number;
  burger: number;
}

const PersonalStats = () => {
  // 이번달 저금
  const [thisMonthSavingData, setThisMonthSavingData] = useState<
    ThisMonthSavingDataType[]
  >([]);
  // 6개월 저금 추이
  const [totalStatisticsData, setTotalStatisticsData] = useState<
    TotalStatisticsDataType[][]
  >([]);
  // 이번달 감정적 고조
  const [thisMonthEmotionData, setThisMonthEmotionData] =
    useState<EmotionHighDataType>();
  // 누적 합계
  const [totalAmountData, setTotalAmountData] = useState<TotalAmountDataType>();

  useEffect(() => {
    // 이번달 저금
    const userThisMonth: ThisMonthSavingDataType[] = [
      {emotion: 'sad', cnt: 2, amount: 40000},
      {emotion: 'angry', cnt: 13, amount: 100820},
      {emotion: 'joy', cnt: 5, amount: 60000},
    ];
    const sortedThisMonthData = userThisMonth.sort(function (a, b) {
      return b.amount - a.amount;
    });
    setThisMonthSavingData(sortedThisMonthData);
    // 6개월 저금 추이
    const userMonths: TotalStatisticsDataType[][] = [
      [
        {emotion: 'angry', month: 2, amount: 100000},
        {emotion: 'angry', month: 3, amount: 324000},
        {emotion: 'angry', month: 4, amount: 8000},
        {emotion: 'angry', month: 5, amount: 23000},
        {emotion: 'angry', month: 6, amount: 423400},
        {emotion: 'angry', month: 7, amount: 3000},
      ],
      [
        {emotion: 'joy', month: 2, amount: 2400},
        {emotion: 'joy', month: 3, amount: 64000},
        {emotion: 'joy', month: 4, amount: 444000},
        {emotion: 'joy', month: 5, amount: 40000},
        {emotion: 'joy', month: 6, amount: 5400},
        {emotion: 'joy', month: 7, amount: 3000},
      ],
      [
        {emotion: 'sad', month: 2, amount: 405345},
        {emotion: 'sad', month: 3, amount: 3345},
        {emotion: 'sad', month: 4, amount: 83462},
        {emotion: 'sad', month: 5, amount: 2356},
        {emotion: 'sad', month: 6, amount: 42450},
        {emotion: 'sad', month: 7, amount: 300454},
      ],
    ];
    setTotalStatisticsData(userMonths);
    // 이번달 감정적 고조
    const emotionHigh = {
      date: 15,
      hour: 11,
      day: '월',
    };
    let hour;
    if (emotionHigh.hour < 12) {
      hour = 'A.M ' + emotionHigh.hour;
    } else {
      hour = 'P.M' + (emotionHigh.hour - 12);
    }
    const thisMonthEmotion: EmotionHighDataType = {
      date: emotionHigh.date,
      hour: hour,
      day: emotionHigh.day,
    };
    setThisMonthEmotionData(thisMonthEmotion);
    // 누적 합계
    const total = 1328100;
    const coffee = 295;
    const burger = 255;
    setTotalAmountData({total: total, coffee: coffee, burger: burger});
  }, []);

  const priceConverter = (price: string) => {
    return price.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  };

  return (
    <Container>
      <ThisMonthSavingContainer>
        <ThisMonthSavingTitleContainer>
          <TitleText>이번 달 저금</TitleText>
          <LottieContainer1>
            <Lottie source={require('../assets/coin.json')} autoPlay loop />
          </LottieContainer1>
        </ThisMonthSavingTitleContainer>
        <ThisMonthSavingBodyContainer>
          {thisMonthSavingData.length > 0 ? (
            <>
              <VictoryBar
                data={thisMonthSavingData}
                x="emotion"
                y="amount"
                sortKey="amount"
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
                    width: 13, // 막대 두께
                  },
                }}
                horizontal={true}
                padding={{top: 30, bottom: 180, left: 10, right: 220}}
                labels={({datum}) => `${datum.cnt}회`}
              />
              <ThisMonthSavingPrice1>
                {priceConverter(thisMonthSavingData[0].amount + '')} 원
              </ThisMonthSavingPrice1>
              <ThisMonthSavingEmotion1
                source={
                  thisMonthSavingData[0].emotion === 'angry'
                    ? EmoAngry
                    : thisMonthSavingData[0].emotion === 'joy'
                    ? EmoHappy
                    : EmoSad
                }
              />
              <ThisMonthSavingPrice2>
                {priceConverter(thisMonthSavingData[1].amount + '')} 원
              </ThisMonthSavingPrice2>
              <ThisMonthSavingEmotion2
                source={
                  thisMonthSavingData[1].emotion === 'angry'
                    ? EmoAngry
                    : thisMonthSavingData[1].emotion === 'joy'
                    ? EmoHappy
                    : EmoSad
                }
              />
              <ThisMonthSavingPrice3>
                {priceConverter(thisMonthSavingData[2].amount + '')} 원
              </ThisMonthSavingPrice3>
              <ThisMonthSavingEmotion3
                source={
                  thisMonthSavingData[2].emotion === 'angry'
                    ? EmoAngry
                    : thisMonthSavingData[2].emotion === 'joy'
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
          <LottieContainer2>
            <Lottie
              source={require('../assets/chart-grow-up.json')}
              autoPlay
              loop
            />
          </LottieContainer2>
        </TotalStatisticsTitleContainer>
        <TotalStatisticsBodyContainer>
          {totalStatisticsData.length > 0 ? (
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
                tickFormat={t => priceConverter(`${t}`)}
              />
              <VictoryLine
                data={totalStatisticsData[0]}
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
                data={totalStatisticsData[1]}
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
                data={totalStatisticsData[2]}
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
          ) : (
            ''
          )}
        </TotalStatisticsBodyContainer>
      </TotalStatisticsContainer>
      <ThisMonthEmotionContainer>
        <ThisMonthEmotionTitleContainer>
          <TitleText>이번 달, 감정 최고조는?</TitleText>
        </ThisMonthEmotionTitleContainer>
        <ThisMonthEmotionBodyContainer>
          <BestDateContainer>
            <BestDateSmileContainer>
              <LinearGradient
                colors={['#FFB53F', '#FFF1DA']}
                // eslint-disable-next-line react-native/no-inline-styles
                style={{width: 100, height: 100, borderRadius: 50}}>
                <Lottie
                  source={require('../assets/emotion-changing.json')}
                  autoPlay
                  loop
                />
              </LinearGradient>
            </BestDateSmileContainer>
            <BestDateText>{thisMonthEmotionData?.date}일</BestDateText>
          </BestDateContainer>
          <BestSamllContainer>
            <BestHourContainer>
              <BestHourLottie>
                <Lottie
                  source={require('../assets/sunny.json')}
                  autoPlay
                  loop
                />
              </BestHourLottie>
              <BestHourText>{thisMonthEmotionData?.hour}</BestHourText>
            </BestHourContainer>
            <BestDayContainer>
              <BestDayLottie>
                <Lottie
                  source={require('../assets/calendar-days.json')}
                  autoPlay
                  loop
                />
              </BestDayLottie>
              <BestDayText>{thisMonthEmotionData?.day}요일</BestDayText>
            </BestDayContainer>
          </BestSamllContainer>
        </ThisMonthEmotionBodyContainer>
      </ThisMonthEmotionContainer>
      <CumulativeAmountContainer>
        <CumulativeAmountTitleContainer>
          <TitleText>저금 누적액</TitleText>
          <LottieContainer1>
            <Lottie source={require('../assets/coin.json')} autoPlay loop />
          </LottieContainer1>
        </CumulativeAmountTitleContainer>
        <CumulativeAmountBodyContainer>
          <AmountHeadingContainer>
            <AmountHeadingLottieContainer>
              <Lottie
                source={require('../assets/making-money.json')}
                autoPlay
                loop
              />
            </AmountHeadingLottieContainer>
            <AmountHeadingTextContainer>
              <AmountHeadingText1>나의 누적 저금</AmountHeadingText1>
              <AmountHeadingText2>
                {priceConverter(totalAmountData?.total + '')} 원
              </AmountHeadingText2>
            </AmountHeadingTextContainer>
          </AmountHeadingContainer>
          <AmountCoffeeContainer>
            <AmountCoffeeLottieContainer>
              <Lottie
                source={require('../assets/hot-smiling-coffee-good-morning.json')}
                autoPlay
                loop
              />
            </AmountCoffeeLottieContainer>
            <AmountCoffeeTextContainer>
              <AmountText1>STARBUCKS 아메리카노</AmountText1>
              <AmountText2>{totalAmountData?.coffee} 잔</AmountText2>
            </AmountCoffeeTextContainer>
          </AmountCoffeeContainer>
          <AmountBurgerContainer>
            <AmountBurgerLottieContainer>
              <Lottie
                source={require('../assets/hamburger.json')}
                autoPlay
                loop
              />
            </AmountBurgerLottieContainer>
            <AmountBurgerTextContainer>
              <AmountText1>McDonald's 빅맥</AmountText1>
              <AmountText2>{totalAmountData?.burger} 개</AmountText2>
            </AmountBurgerTextContainer>
          </AmountBurgerContainer>
        </CumulativeAmountBodyContainer>
      </CumulativeAmountContainer>
    </Container>
  );
};

export default PersonalStats;
