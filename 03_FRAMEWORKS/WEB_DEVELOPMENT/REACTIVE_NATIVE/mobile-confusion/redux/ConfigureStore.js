import {createStore, combineReducers, applyMiddleware} from 'redux';
import thunk from 'redux-thunk';
import logger from 'redux-logger';
import {Dishes} from './dishes';
import {Comments} from './comments';
import {Promotions} from './promotions';
import {Leaders} from './leaders';
import { Favorites } from './favorites';


export const ConfigureStore = () => {

    const store = createStore(
       
        combineReducers(
            {
                dishes: Dishes,
                comments: Comments,
                promotions: Promotions,
                leaders: Leaders,
                favorites: Favorites
            }
        ),
        applyMiddleware(thunk, logger)
        
    );
    return store;
}




