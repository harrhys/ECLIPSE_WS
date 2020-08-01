import * as ActionTypes from './ActionTypes';
import {baseUrl} from '../shared/baseUrl';

export const addComment = (comment) => ({
    type: ActionTypes.ADD_COMMENT,
    payload: comment
});

export const postComment = (dishId, rating, author, comment) => (dispatch) => {

    const newComment = {
        dishId: dishId,
        rating: rating,
        author: author,
        comment: comment
    };
    newComment.date = new Date().toISOString();
    
    return fetch('https://mconfusion-9aeb.restdb.io/media/5f24ad119236d304001cbcf7', {
        method: "POST",
        body: JSON.stringify(newComment),
        headers: {
          "Content-Type": "application/json"
        },
        credentials: "same-origin"
    })
    .then(response => {
        if (response.ok) {
          return response;
        } else {
          var error = new Error('Error ' + response.status + ': ' + response.statusText);
          error.response = response;
          throw error;
        }
      },
      error => {
            throw error;
      })
    .then(response => response.json())
    .then(response => dispatch(addComment(response)))
    .catch(error =>  { console.log('post comments', error.message); alert('Your comment could not be posted\nError: '+error.message); });
};


export const fetchDishes = () => (dispatch) => {

    dispatch(dishesLoading(true));

    return fetch('https://mconfusion-9aeb.restdb.io/media/5f24a9aa9236d304001cbc9b')
    .then(response =>
        {
            if(response.ok)
            {
                return response;
            }
            else{
                var error = new Error('Error  '+ response.status + ': ' + response.statusText);
                error.response = response;
                throw error;
            }
        }, error => {
            var errMess = new Error(error.message);
            throw errMess;
        })
    .then(response => response.json())
    .then(dishes => dispatch(addDishes(dishes)))
    .catch(error => dispatch(dishesFailed(error.message)));
}

export const fetchPromos = () => (dispatch) => {

    dispatch(promosLoading(true));
    return fetch('https://mconfusion-9aeb.restdb.io/media/5f24ac859236d304001cbce8')
    .then(response =>
        {
            if(response.ok)
            {
                return response;
            }
            else{
                var error = new Error('Error  '+ response.status + ': ' + response.statusText);
                error.response = response;
                throw error;
            }
        }, error => {
            var errMess = new Error(error.message);
            throw errMess;
        })
    .then(response => response.json())
    .then(promos => dispatch(addPromos(promos)))
    .catch(error => dispatch(promosFailed(error.message)));
}

export const fetchLeaders = () => (dispatch) => {

    dispatch(leadersLoading());
    return fetch('https://mconfusion-9aeb.restdb.io/media/5f24ab1a9236d304001cbcbb')
    .then(response =>
        {
            if(response.ok)
            {
                return response;
            }
            else{
                var error = new Error('Error  '+ response.status + ': ' + response.statusText);
                error.response = response;
                throw error;
            }
        }, error => {
            var errMess = new Error(error.message);
            throw errMess;
        })
    .then(response => response.json())
    .then(leaders => dispatch(loadLeaders(leaders)))
    .catch(error => dispatch(leadersFailed(error.message)));
}

export const fetchComments = () => (dispatch) => {

     return fetch('https://mconfusion-9aeb.restdb.io/media/5f24ad119236d304001cbcf7')
     .then(response =>
        {
            if(response.ok)
            {
                return response;
            }
            else{
                var error = new Error('Error  '+ response.status + ': ' + response.statusText);
                error.response = response;
                throw error;
            }
        }, error => {
            var errMess = new Error(error.message);
            throw errMess;
        })
    .then(response => response.json())
    .then(comments => dispatch(addComments(comments)))
    .catch(error => dispatch(commentsFailed(error.message)));
}

export const dishesLoading = () => ({
    type: ActionTypes.DISHES_LOADING
});

export const dishesFailed = (errMess) => ({
    type: ActionTypes.DISHES_FAILED,
    payload: errMess

});

export const addDishes = (dishes) => ({
    type: ActionTypes.LOAD_DISHES,
    payload: dishes
});

export const promosLoading = () => ({
    type: ActionTypes.PROMOS_LOADING
});

export const promosFailed = (errMess) => ({
    type: ActionTypes.PROMOS_FAILED,
    payload: errMess

});

export const addPromos = (promos) => ({
    type: ActionTypes.LOAD_PROMOS,
    payload: promos
});

export const leadersLoading = () => ({
    type: ActionTypes.LEADERS_LOADING
});

export const leadersFailed = (errMess) => ({
    type: ActionTypes.LEADERS_FAILED,
    payload: errMess

});

export const loadLeaders = (leaders) => ({
    type: ActionTypes.LOAD_LEADERS,
    payload: leaders
});

export const commentsFailed = (errMess) => ({
    type: ActionTypes.COMMENTS_FAILED,
    payload: errMess

});

export const addComments = (comments) => ({
    type: ActionTypes.LOAD_COMMENTS,
    payload: comments
});

export const postFavorite = (dishId)  => (dispatch) => {
    setTimeout(() => {
        dispatch(addFavorite(dishId));
    }, 2000);
};

export const addFavorite = (dishId) => ({
    type: ActionTypes.ADD_FAVORITE,
    payload: dishId
});

export const deleteFavorite = (dishId) => ({
    type: ActionTypes.DELETE_FAVORITE,
    payload: dishId
});  


