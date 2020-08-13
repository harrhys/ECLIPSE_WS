import * as ActionTypes from './ActionTypes';
import {baseUrl} from '../shared/baseUrl';

export const registerUser = (username, password, firstname, lastname, email) => (dispatch) => {

    const newuser = {
        username: username,
        password: password,
        firstname: firstname,
        lastname: lastname,
        email:email
    };
    
    const fetchUrl = baseUrl+'users/signup';

    return fetch(fetchUrl, {
        method: "POST",
        body: JSON.stringify(newuser),
        headers: {
          "Content-Type": "application/json",
        }
        
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
    .then(response => {
        var res = response.json();
        return res;
    })
    .then(user => dispatch(addUser(user)))
    .catch(error => dispatch(registerFailed(error.message)));
};

export const addUser = (user) => ({
    type: ActionTypes.ADD_USER,
    payload: user

});

export const registerFailed = (errMess) => ({
    type: ActionTypes.REGISTER_FAILED,
    payload: errMess

});

export const login = (username, password) => (dispatch) => {

    const loginUser = {
        username: username,
        password: password,
      };
    
    const fetchUrl = baseUrl+'users/login';
    
    return fetch(fetchUrl, {
        method: "POST",
        body: JSON.stringify(loginUser),
        headers: {
          "Content-Type": "application/json"
        }
        
    })
    .then(response => {
        if (response.ok) {

          return response.json();

        } else {
          var error = new Error('Error ' + response.status + ': ' + response.statusText);
          error.response = response;
          throw error;
        }
      },
      error => {
            throw error;
      })
    .then(user => dispatch(addUser(user)))
    .catch(error => dispatch(loginFailed(error.message)));
};

export const loginFailed = (errMess) => ({
    type: ActionTypes.LOGIN_FAILED,
    payload: errMess

});

export const logout = () => ({
    type: ActionTypes.LOGOUT_USER,
    payload: 'Logout is Successful'

});

export const fetchDishes = () => (dispatch) => {

    dispatch(dishesLoading(true));

    return fetch(baseUrl+'dishes')
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
    .then(dishes => dispatch(loadDishes(dishes)))
    .catch(error => dispatch(dishesFailed(error.message)));
}



export const dishesLoading = () => ({
    type: ActionTypes.DISHES_LOADING
});

export const dishesFailed = (errMess) => ({
    type: ActionTypes.DISHES_FAILED,
    payload: errMess

});

export const loadDishes = (dishes) => ({
    type: ActionTypes.LOAD_DISHES,
    payload: dishes
});

export const updateDishes = (dish) => ({
    type: ActionTypes.UPDATE_DISHES,
    payload: dish
});

export const postComment = (dishId, rating, author, comment, user) => (dispatch) => {

    const newComment = {
        dishId: dishId,
        rating: rating,
        author: author,
        comment: comment
    };
    
    const fetchUrl = baseUrl+'dishes/'+dishId+'/comments';
    
    return fetch(fetchUrl, {
        method: "POST",
        body: JSON.stringify(newComment),
        headers: {
          "Content-Type": "application/json",
          "Authorization": "bearer "+ user.token
        }
        
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
    .then(response => dispatch(updateDishes(response)))
    .catch(error =>  { console.log('post comments', error.message); alert('Your comment could not be posted\nError: '+error.message); });
};

export const fetchPromos = () => (dispatch) => {

    dispatch(promosLoading(true));
    return fetch(baseUrl+'promotions')
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

export const fetchLeaders = () => (dispatch) => {

    dispatch(leadersLoading());
    return fetch(baseUrl+'leaders')
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

export const postFavorite = (dishId)  => (dispatch) => {
    setTimeout(() => {
        dispatch(addFavorite(dishId));
    }, 100);
};

export const removeFavorite = (dishId) =>(dispatch) => {
    setTimeout(() => {
        dispatch(deleteFavorite(dishId));
    }, 100);
   
};  

export const addFavorite = (dishId) => ({
    type: ActionTypes.ADD_FAVORITE,
    payload: dishId
});

export const deleteFavorite = (dishId) => ({
    type: ActionTypes.DELETE_FAVORITE,
    payload: dishId
});


//Below methods are useful if comments are stored in a separate schema, not as part of dish
export const fetchComments = (dish) => (dispatch) => {

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

export const addComments = (comments) => ({
    type: ActionTypes.LOAD_COMMENTS,
    payload: comments
});

export const addComment = (comment) => ({
    type: ActionTypes.ADD_COMMENT,
    payload: comment
});

export const commentsFailed = (errMess) => ({
    type: ActionTypes.COMMENTS_FAILED,
    payload: errMess

});







