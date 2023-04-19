import {configureStore} from '@reduxjs/toolkit';
import {rootReducer} from './rootReducer';

export const makeStore = () => {
  const store = configureStore({
    reducer: rootReducer,
  });
  return store;
};
