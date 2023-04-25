import {AppState} from './AppState';
import type {LoginActions, ToggleProgressAction, TokenAction} from './actions';

const initialState: AppState = {
  loggedIn: false,
  loggedUser: {
    name: '',
    id: -1,
    min_money: -1,
    max_money: -1,
    refresh_token: '',
    access_token: '',
  },
  inProgress: false,
};

type RootAction = LoginActions | ToggleProgressAction | TokenAction;

export const rootReducer = (
  state: AppState = initialState,
  action: RootAction,
) => {
  switch (action.type) {
    case 'login':
      // ...
      return state;
    case 'logout':
      // ...
      return state;
    case 'set_progress':
      return {...state, inProgress: action.inProgress};
    case 'set_accesstoken':
      return {
        ...state,
        loggedUser: {
          ...state.loggedUser,
          refresh_token: action.refresh_token,
          access_token: action.access_token,
        },
      };
    default:
      return state;
  }
};
