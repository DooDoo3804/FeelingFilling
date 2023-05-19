import {AppState} from './AppState';
import type {LoginActions, ToggleProgressAction, TokenAction} from './actions';

const initialState: AppState = {
  loggedIn: false,
  loggedUser: null,
  inProgress: false,
};

type RootAction = LoginActions | ToggleProgressAction | TokenAction;

export const rootReducer = (
  state: AppState = initialState,
  action: RootAction,
) => {
  switch (action.type) {
    case 'login':
      return {...state, loggedIn: true, loggedUser: action.loggedUser};
    case 'logout':
      return {...state, loggedIn: false, loggedUser: null};
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
