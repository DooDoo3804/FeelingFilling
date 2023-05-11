import React, {useEffect, useState} from 'react';
import {WebView} from 'react-native-webview';

const Payment = () => {
  const [data, setData] = useState<JSON | null>(null);

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
