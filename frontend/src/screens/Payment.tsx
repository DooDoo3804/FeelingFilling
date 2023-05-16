import React, {useEffect, useState} from 'react';
import {WebView} from 'react-native-webview';

const Payment = ({navigation, route}: {navigation: any; route: any}) => {
  const [data, setData] = useState<JSON | null>(null);
  const kakaoId = route.params.kakaoId.toString();

  useEffect(() => {
    fetch('http://13.124.31.137:8702/billing/subscription/active/', {
      method: 'POST',
      headers: {'Content-type': 'application/json'},
      body: JSON.stringify({
        serviceName: 'abcd',
        serviceUserId: 3,
      }),
    })
      .then(response => response.json())
      .then(res => {
        console.log(res);
        setData(res);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);

  return (
    <>{data && <WebView originWhitelist={['*']} source={{uri: data.url}} />}</>
  );
};

export default Payment;
