import React from 'react';
import {Text, Button} from 'react-native';

const Login = ({navigation}: {navigation: any}) => {
  return (
    <>
      <Text>Login</Text>
      <Button title="move" onPress={() => navigation.navigate('SignUp')} />
    </>
  );
};

export default Login;
