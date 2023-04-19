import {AppState} from './AppState';
import type {LoginActions, ToggleProgressAction} from './actions';

const initialState: AppState = {
  loggedIn: false,
  loggedUser: {name: '', id: -1, min_money: -1, max_money: -1},
  inProgress: false,
};

type RootAction = LoginActions | ToggleProgressAction;

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
    default:
      return state;
  }
};
