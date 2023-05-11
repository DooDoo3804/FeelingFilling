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
import {useAxios} from '../hooks/useAxios';
import {Defs, LinearGradient as LG, Stop} from 'react-native-svg';
//
import {useSelector} from 'react-redux';
import type {AppState, User} from '../redux';

const AngerGradient = () => (
  <Defs>
    <LG id="anger" x1="0" y1="0" x2="1" y2="0">
      <Stop offset="0%" stopColor="#FA3800" />
      <Stop offset="50%" stopColor="#F7673D" />
      <Stop offset="100%" stopColor="#F5987D" />
    </LG>
  </Defs>
);

const SadGradient = () => (
  <Defs>
    <LG id="sad" x1="0" y1="0" x2="1" y2="0">
      <Stop offset="0%" stopColor="#4A5882" />
      <Stop offset="50%" stopColor="#5F6C93" />
      <Stop offset="100%" stopColor="#8490B3" />
    </LG>
  </Defs>
);

const JoyGradient = () => (
  <Defs>
    <LG id="joy" x1="0" y1="0" x2="1" y2="0">
      <Stop offset="0%" stopColor="#FFB948" />
      <Stop offset="50%" stopColor="#FFCD7E" />
      <Stop offset="100%" stopColor="#FFE1B1" />
    </LG>
  </Defs>
);

interface PersonalStatDataType {
  userThisMonth: ThisMonthSavingDataType[];
  userMonths: TotalStatisticsDataType[][];
  emotionHigh: EmotionHighDataType;
  total: number;
  coffee: number;
  burger: number;
}

interface ThisMonthSavingDataType {
  emotion: string;
  count: number;
  amount: number;
}

interface TotalStatisticsDataType {
  emotion: string;
  amount: number;
  count: number;
  month: string;
}

interface EmotionHighDataType {
  date: number;
  hour: number;
  day: string;
}

interface EmotionHighDataToStringType {
  date: string;
  hour: string;
  day: string;
}

interface TotalAmountDataType {
  total: number;
  coffee: number;
  burger: number;
}

const PersonalStats = () => {
  const user = useSelector<AppState, User | null>(state => state.loggedUser);
  const {data, error} = useAxios<PersonalStatDataType>(
    `https://feelingfilling.store/api/stat/user/${user?.id}`,
    'GET',
    null,
  );
  // 이번달 저금
  const [thisMonthSavingData, setThisMonthSavingData] = useState<
    ThisMonthSavingDataType[] | null
  >(null);
  // 6개월 저금 추이
  const [totalAngerStatisticsData, setTotalAngerStatisticsData] = useState<
    TotalStatisticsDataType[] | null
  >(null);
  const [totalJoyStatisticsData, setTotalJoyStatisticsData] = useState<
    TotalStatisticsDataType[] | null
  >(null);
  const [totalSadStatisticsData, setTotalSadStatisticsData] = useState<
    TotalStatisticsDataType[] | null
  >(null);
  // 이번달 감정적 고조
  const [thisMonthEmotionData, setThisMonthEmotionData] =
    useState<EmotionHighDataToStringType | null>(null);
  // 누적 합계
  const [totalAmountData, setTotalAmountData] =
    useState<TotalAmountDataType | null>(null);

  // 최근 6개월 저금 최고금액
  const [maxMoney, setMaxMoney] = useState<number>(100000);

  useEffect(() => {
    // 이번달 저금
    const sortedThisMonthData = data?.userThisMonth?.sort(function (a, b) {
      return b.amount - a.amount;
    }) as ThisMonthSavingDataType[];
    setThisMonthSavingData(sortedThisMonthData);
    // 6개월 저금 추이
    const anger: TotalStatisticsDataType[] = [];
    const joy: TotalStatisticsDataType[] = [];
    const sad: TotalStatisticsDataType[] = [];
    for (let i = 0; i < 3; i++) {
      data?.userMonths[i].forEach(e => {
        const month = Number(e.month) % 100;
        const year = (e.month + '').substring(3, 4);
        const date = year + (month < 10 ? '0' + month : month);
        if (e.amount > maxMoney) {
          setMaxMoney(e.amount);
        }
        if (i === 0) {
          anger.push({
            emotion: e.emotion,
            amount: e.amount,
            count: e.count,
            month: date,
          });
        } else if (i === 1) {
          joy.push({
            emotion: e.emotion,
            amount: e.amount,
            count: e.count,
            month: date,
          });
        } else {
          sad.push({
            emotion: e.emotion,
            amount: e.amount,
            count: e.count,
            month: date,
          });
        }
      });
    }
    setTotalAngerStatisticsData(anger);
    setTotalJoyStatisticsData(joy);
    setTotalSadStatisticsData(sad);
    // 이번달 감정적 고조
    const emotionHigh = data?.emotionHigh as EmotionHighDataType;
    let hour;
    if (emotionHigh?.hour === -1) {
      hour = '언제?';
    } else if (emotionHigh?.hour < 12) {
      if (emotionHigh?.hour < 10) {
        hour = 'A.M 0' + emotionHigh?.hour;
      } else {
        hour = 'A.M' + emotionHigh?.hour;
      }
    } else {
      hour = 'P.M' + (emotionHigh?.hour - 12);
    }
    let date;
    if (emotionHigh?.date === 0) {
      date = '?';
    } else {
      date = emotionHigh?.date + '일';
    }
    let day;
    if (emotionHigh?.day === '없음') {
      day = '아직!';
    } else {
      day = emotionHigh?.day + '요일';
    }
    const thisMonthEmotion: EmotionHighDataToStringType = {
      date: date,
      hour: hour,
      day: day,
    };
    setThisMonthEmotionData(thisMonthEmotion);
    // 누적 합계
    const totalAmountDatas = {
      total: data?.total,
      coffee: data?.coffee,
      burger: data?.burger,
    } as TotalAmountDataType;
    setTotalAmountData(totalAmountDatas);
  }, [data, error, maxMoney]);

  const priceConverter = (price: string) => {
    return price.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  };

  const monthConverter = (month: string) => {
    return month.substring(1, 3) + '월';
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
          {thisMonthSavingData && thisMonthSavingData?.length > 0 ? (
            <>
              <VictoryChart
                padding={{top: 30, bottom: 180, left: 10, right: 220}}>
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
                        datum.emotion === 'anger'
                          ? 'url(#anger)'
                          : datum.emotion === 'joy'
                          ? 'url(#joy)'
                          : 'url(#sad)',
                      width: 13, // 막대 두께
                    },
                  }}
                  horizontal={true}
                  labels={({datum}) => `${datum.count}회`}
                />
              </VictoryChart>
              <ThisMonthSavingPrice1>
                {priceConverter(thisMonthSavingData[0].amount + '')} 원
              </ThisMonthSavingPrice1>
              <ThisMonthSavingEmotion1
                source={
                  thisMonthSavingData[0].emotion === 'anger'
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
                  thisMonthSavingData[1].emotion === 'anger'
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
                  thisMonthSavingData[2].emotion === 'anger'
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
          {totalAngerStatisticsData &&
          totalJoyStatisticsData &&
          totalSadStatisticsData ? (
            <VictoryChart
              maxDomain={{y: maxMoney}}
              height={250}
              style={{
                background: {fill: '#F9F9F9'},
              }}
              domainPadding={{x: 5, y: 5}}
              padding={{top: 10, bottom: 30, left: 70, right: 50}}>
              <VictoryAxis
                style={{
                  axis: {stroke: ''},
                  tickLabels: {
                    fontSize: 14,
                    fill: Common.colors.selectGrey,
                  },
                }}
                tickFormat={t => monthConverter(`${t}`)}
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
                data={totalAngerStatisticsData}
                x="month"
                y="amount"
                style={{
                  data: {
                    stroke: Common.colors.emotionColor01,
                    strokeWidth: 2,
                    strokeLinecap: 'round',
                  },
                }}
              />
              <VictoryLine
                data={totalJoyStatisticsData}
                x="month"
                y="amount"
                style={{
                  data: {
                    stroke: Common.colors.emotionColor02,
                    strokeWidth: 2,
                    strokeLinecap: 'round',
                  },
                }}
              />
              <VictoryLine
                data={totalSadStatisticsData}
                x="month"
                y="amount"
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
      {thisMonthEmotionData ? (
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
              <BestDateText>{thisMonthEmotionData?.date}</BestDateText>
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
                <BestDayText>{thisMonthEmotionData?.day}</BestDayText>
              </BestDayContainer>
            </BestSamllContainer>
          </ThisMonthEmotionBodyContainer>
        </ThisMonthEmotionContainer>
      ) : (
        ''
      )}
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
