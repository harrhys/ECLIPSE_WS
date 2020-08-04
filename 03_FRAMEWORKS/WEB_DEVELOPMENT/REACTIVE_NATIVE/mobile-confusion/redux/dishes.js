import * as ActionTypes from './ActionTypes';

export const Dishes = (state = {
    isLoading:true, 
    errMess:null, 
    dishes:[]}, action) => {

    switch(action.type){

        case ActionTypes.LOAD_DISHES:
            return {...state, isLoading:false,errMess:null, dishes:action.payload};
        
        case ActionTypes.UPDATE_DISHES:
            var updatedDish = action.payload;
            var temp =[];
            for(var i=0; i<state.dishes.length; i++)
            {   
                var dish = state.dishes[i];
                console.log('dishid---'+dish._id);
                if(updatedDish._id==dish._id)
                    temp[i]=(updatedDish);
                else
                temp[i]=(dish);
            };
            return {...state, isLoading:false,errMess:null, dishes:temp};
            
        case ActionTypes.DISHES_LOADING:
            return {...state, isLoading:true,errMess:null, dishes:[]};

        case ActionTypes.DISHES_FAILED:
            return {...state, isLoading:false,errMess:action.payload, dishes:[]};

        default: return state;
    }
}