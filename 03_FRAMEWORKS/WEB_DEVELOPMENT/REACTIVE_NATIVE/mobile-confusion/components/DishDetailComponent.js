import React from 'react';
import { Text, View, ScrollView, FlatList, Modal, StyleSheet, Button, Alert, PanResponder, Share } from 'react-native';
import { Card, Input, Icon, Rating, AirbnbRating } from 'react-native-elements';
import { connect } from 'react-redux';
import * as Animatable from 'react-native-animatable';
import { baseUrl } from '../shared/baseUrl';
import { postFavorite,postComment } from '../redux/ActionCreators';

const mapStateToProps = state => {
    return {
      dishes: state.dishes,
      comments: state.comments,
      favorites: state.favorites
    }
  }

const mapDispatchToProps = dispatch => ({
    postFavorite: (dishId) => dispatch(postFavorite(dishId)),
    postComment:  (dishId, rating, author, comment) => dispatch(postComment(dishId, rating, author, comment))
})


function RenderDish(props) {

    const dish = props.dish;

    const recognizeAddFavDrag = ({ moveX, moveY, dx, dy }) => {
        if ( dx > 200 )
            return true;
        else
            return false;
    }

    const recognizeAddCommentDrag = ({ moveX, moveY, dx, dy }) => {
        if ( dx < -200 )
            return true;
        else
            return false;
    }

    const panResponder = PanResponder.create({

        onStartShouldSetPanResponder: (e, gestureState) => {
            return true;
        },

        onPanResponderEnd: (e, gestureState) => {
            console.log("pan responder end", gestureState);
            if (recognizeAddFavDrag(gestureState))
                Alert.alert(
                    'Add Favorite',
                    'Are you sure you wish to add ' + dish.name + ' to favorite?',
                    [
                    {text: 'Cancel', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
                    {text: 'OK', onPress: () => {props.favorite ? console.log('Already favorite') : props.onPress()}},
                    ],
                    { cancelable: false }
                );
            else if(recognizeAddCommentDrag(gestureState))
                props.toggleForm();
            return true;
        }
    })

    const shareDish = (title, message, url) => {
        Share.share({
            title: title,
            message: title + ': ' + message + ' ' + url,
            url: url
        },{
            dialogTitle: 'Share ' + title
        })
    }
    
    if (dish != null) {

        return(
            
            <Animatable.View 
                animation="fadeInDown" 
                duration={500} 
                {...panResponder.panHandlers}
            >
                <Card
                featuredTitle={dish.name}
                image={{uri: baseUrl + dish.image}}>
                    <Text style={{margin: 10}}>
                        {dish.description}
                    </Text>
                    <View style={ styles.formRow }>
                        <Icon
                            raised
                            reverse
                            name={ props.favorite ? 'heart' : 'heart-o'}
                            type='font-awesome'
                            color='#f50'
                            onPress={() => props.favorite ? console.log('Already favorite') : props.onPress()}
                        />
                        <Icon
                            raised
                            reverse
                            name={ 'pencil'}
                            type='font-awesome'
                            color='#512DA8'
                            onPress={()=>props.toggleForm()}
                        />
                        <Icon
                            raised
                            reverse
                            name='share'
                            type='font-awesome'
                            color='#51D2A8'
                            style={styles.cardItem}
                            onPress={() => shareDish(dish.name, dish.description, baseUrl + dish.image)} />
                        <Modal 
                            animationType = {"fadeIn"} 
                            transparent = {false}
                            visible = {props.isFormVisible}
                            onDismiss = {()=>props.toggleForm()}
                            onRequestClose = {()=>props.toggleForm() }
                        >
                            <View style = {styles.modal}>
                                <Rating 
                                    showRating 
                                    type='heart'
                                    onStartRating={3}
                                    onFinishRating={rating=>props.setRating(rating)}
                                />
                                <Input
                                    placeholder='Author'
                                    leftIcon={{ type: 'font-awesome', name: 'user-o' }}
                                    onChangeText={text => props.setAuthor(text)}
                                />  
                                <Input
                                    placeholder='Comment'
                                    leftIcon={{ type: 'font-awesome', name: 'comment-o' }} 
                                    onChangeText={text => props.setComment(text)}
                                />  
                                <View style={styles.formRow}>
                                    <Button 
                                        onPress = {()=>props.addComment()}
                                        color="#512DA8"
                                        title="Submit" 
                                    />
                                </View>
                                <View style={styles.formRow}>
                                    <Button 
                                        onPress = {() =>{props.resetComment()}}
                                        color="grey"
                                        title="Cancel" 
                                    />
                                </View>
                            </View>
                        </Modal>
                    </View>
                </Card>
            </Animatable.View>
        );
    }
    else {
        return(<View></View>);
    }
}

function RenderComments(props) {

    const comments = props.comments;
            
    const renderCommentItem = ({item, index}) => {
        
        return (
            <View key={index} style={styles.comments}>
                
                <Rating 
                    imageSize={14}
                    readonly 
                    type='heart' 
                    startingValue={item.rating}
                    
                />
                <Text style={{fontSize: 14}}>{item.comment}</Text>
                <Text style={{fontSize: 12}}>{'-- ' + item.author + ', ' + item.date} </Text>
            </View>
        );
    };
    
    return (
        <Animatable.View animation="slideInUp" duration={2000} delay={10}>
            <Card title='Comments' >
            <   FlatList 
                    data={comments}
                    renderItem={renderCommentItem}
                    keyExtractor={item => item.id.toString()}
                />
            </Card>
        </Animatable.View>
        
    );
}

class DishDetail extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            favorites: [],
            isFormVisible: false,
            rating:0,
            author:'',
            comment:''
        };
    }

    markFavorite(dishId) {
        this.props.postFavorite(dishId);
    }

    setRating(rating){
        this.setState({rating: rating});
    }

    setAuthor(author){
        this.setState({author:author});
    }

    setComment(comment){
        this.setState({ comment:comment,});
    }

    resetComment(){

        this.setState({
            rating: 0,
            author:'',
            comment:'',
            isFormVisible: false
        });
    }

    addComment(dishId){

        console.log('dishId:----------------'+dishId);
        console.log(JSON.stringify(this.state));
        this.props.postComment(dishId,this.state.rating,this.state.author,this.state.comment);
        this.resetComment();
    }

    toggleForm(){
        this.setState({isFormVisible: !this.state.isFormVisible});
    }

    static navigationOptions = {title: 'Dish Details'};

    render(){

        const dishId = this.props.navigation.getParam('dishId','');
        return(
            <ScrollView>
                <RenderDish 
                    dish={this.props.dishes.dishes[+dishId]}
                    favorite={this.props.favorites.some(el => el === dishId)}
                    onPress={() => this.markFavorite(dishId)} 
                    toggleForm={()=> this.toggleForm()}
                    isFormVisible={this.state.isFormVisible}
                    setRating={(rating)=>this.setRating(rating)}
                    setAuthor={(author)=>this.setAuthor(author)}
                    setComment={(comment)=>this.setComment(comment)}
                    addComment={()=>this.addComment(dishId)}
                    resetComment={()=>this.resetComment()}
                />
                <RenderComments 
                    comments={this.props.comments.comments.filter((comment) => comment.dishId === dishId)} 
                />
            </ScrollView>
        );
    }
}

const styles = StyleSheet.create({
    formRow: {
      alignItems: 'center',
      justifyContent: 'center',
      flex: 1,
      flexDirection: 'row',
      margin: 20
    },
    comments: {
        alignItems: 'flex-start',
        justifyContent: 'space-around',
        flex: 1,
        flexDirection: 'column',
        margin: 20
    },
    formLabel: {
        fontSize: 18,
        flex: 3
    },
    formItem: {
        flex: 1
    },
    modal: {
        justifyContent: 'center',
        margin: 20
     },
     modalTitle: {
         fontSize: 24,
         fontWeight: 'bold',
         backgroundColor: '#512DA8',
         textAlign: 'center',
         color: 'white',
         marginBottom: 20
     },
     modalText: {
         fontSize: 18,
         margin: 10
     },
     rating:{
         justifyContent:'flex-start'

     }
});

export default connect(mapStateToProps, mapDispatchToProps)(DishDetail);