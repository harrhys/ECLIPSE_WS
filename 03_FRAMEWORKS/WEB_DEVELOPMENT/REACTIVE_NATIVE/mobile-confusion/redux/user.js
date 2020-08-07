import * as ActionTypes from './ActionTypes';

export const User = (state = {user:null,errMsg:null}, action) => {

    switch (action.type) {
        case ActionTypes.ADD_USER:
            return {...state, errMsg:null, user:action.payload};
        
        case ActionTypes.REGISTER_FAILED:
            return {...state, errMsg:action.payload, user:null};
        
        case ActionTypes.LOGOUT_USER:
            return {...state, errMsg:action.payload, user:null};
        
        case ActionTypes.LOGIN_FAILED:
                return {...state, errMsg:action.payload, user:null};
                    
        default:
          return state;
      }
};