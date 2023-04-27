import React, {useState} from 'react';
import {
  BadgeDescription,
  BadgeImage,
  BadgePageContainer,
  BadgePageContent,
  BadgePageWrapper,
  BadgeTitle,
  SingleBadgeContainer,
} from '../styles/MypageStyle';

import meet01 from '../assets/badges/badge_meet01.png';
import meet02 from '../assets/badges/badge_meet02.png';
import angry01 from '../assets/badges/badge_angry01.png';
import angry02 from '../assets/badges/badge_angry02.png';
import angry03 from '../assets/badges/badge_angry03.png';
import gloomy01 from '../assets/badges/badge_gloomy01.png';
import gloomy02 from '../assets/badges/badge_gloomy02.png';
import gloomy03 from '../assets/badges/badge_gloomy03.png';
import happy01 from '../assets/badges/badge_happy01.png';
import happy02 from '../assets/badges/badge_happy02.png';
import happy03 from '../assets/badges/badge_happy03.png';
import money01 from '../assets/badges/badge_money01.png';
import money02 from '../assets/badges/badge_money02.png';
import money03 from '../assets/badges/badge_money03.png';
import nomoney from '../assets/badges/badge_nomoney.png';
import default01 from '../assets/badges/badge_default.png';

const badgeList = [
  {
    src: meet01,
    name: '감정 적금 초심자',
    description: '필링필링 서비스를 처음 이용했어요.',
  },
  {
    src: meet02,
    name: '감정 적금 마스터',
    description: '필링필링 서비스를 이용한 지 1년이 되었어요.',
  },
  {
    src: angry01,
    name: '분노 조절 장인',
    description: '분노를 통해 10번 저금했어요.',
  },
  {
    src: angry02,
    name: '화가 잔뜩 났네',
    description: '분노를 통해 100번 저금했어요.',
  },
  {
    src: angry03,
    name: '분노의 화신',
    description:
      '분노를 통해 1,000번 저금했는데... 핸드폰 아직 안 부서진 거 맞죠?',
  },
  {
    src: gloomy01,
    name: '속상했어',
    description: '우울함을 통해 10번 저금했어요.',
  },
  {
    src: gloomy02,
    name: '눈물 젖은 적금파이',
    description: '우울함을 통해 100번 저금했어요.',
  },
  {
    src: gloomy03,
    name: '난 ㄱr끔...',
    description: '우울함을 통해 1,000번 저금했어요. 괜찮아지는 날이 올 거예요.',
  },
  {
    src: happy01,
    name: '행복의 요정',
    description: '기쁨을 통해 10번 저금했어요.',
  },
  {
    src: happy02,
    name: '장밋빛 세상',
    description: '기쁨을 통해 100번 저금했어요.',
  },
  {
    src: happy03,
    name: '이 시대의 긍정왕',
    description: '기쁨을 통해 1,000번 저금했어요. 행복은 나의 것!',
  },
  {
    src: money01,
    name: '뭐 했다고 돈이',
    description: '누적 저금액이 10만원을 돌파했어요. 세상에나!',
  },
  {
    src: money02,
    name: '돈이 어디서 나왔지',
    description: '누적 저금액이 100만원을 돌파했어요. 이 정도면 부업 아닌가요?',
  },
  {
    src: money03,
    name: '감정 부자',
    description: '누적 저금액이 1,000만원을 돌파했어요. 돈이 다 어디서 났대?',
  },
  {
    src: nomoney,
    name: '말도 안 돼',
    description:
      '잔액 부족으로 결제에 실패했어요. 살다 보면 이런 날도 있기 마련이죠.',
  },
];

const Badges = () => {
  const [badges, setBadges] = useState([0, 2, 3, 4, 5, 6, 8, 9]);

  return (
    <BadgePageContainer>
      <BadgePageWrapper>
        <BadgePageContent>
          {Array(15)
            .fill(1)
            .map((item, idx) => {
              if (badges.includes(idx)) {
                return (
                  <SingleBadgeContainer>
                    <BadgeImage source={badgeList[idx].src} />
                    <BadgeTitle>{badgeList[idx].name}</BadgeTitle>
                    <BadgeDescription>
                      {badgeList[idx].description}
                    </BadgeDescription>
                  </SingleBadgeContainer>
                );
              } else {
                return (
                  <SingleBadgeContainer>
                    <BadgeImage source={default01} />
                    <BadgeTitle>숨겨진 배지</BadgeTitle>
                    <BadgeDescription>
                      새로운 활동 배지를 획득해보세요!
                    </BadgeDescription>
                  </SingleBadgeContainer>
                );
              }
            })}
        </BadgePageContent>
      </BadgePageWrapper>
    </BadgePageContainer>
  );
};

export default Badges;
