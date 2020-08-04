import React, { Component } from 'react';
import { FlatList, View, Text, Alert  } from 'react-native';
import Swipeout from 'react-native-swipeout';
import { ListItem,Card, Button } from 'react-native-elements';
import Icon  from 'react-native-vector-icons/FontAwesome';
import { connect } from 'react-redux';
import * as Animatable from 'react-native-animatable';
import { Loading } from './LoadingComponent';
import { baseUrl } from '../shared/baseUrl';

import { deleteFavorite } from '../redux/ActionCreators';

const mapStateToProps = state => {
  return {
    dishes: state.dishes,
    favorites: state.favorites
  }
}

const mapDispatchToProps = dispatch => ({
  deleteFavorite: (dishId) => dispatch(deleteFavorite(dishId))
})

class Favorites extends Component {

    static navigationOptions = {
        title: 'My Favorites'
    };

    render() {

        const { navigate } = this.props.navigation;
        
          const renderMenuItem = ({item, index}) => {
      
            const rightButton = [
              {
                text: 'Delete', 
                type: 'delete',
                onPress: () => {
                  Alert.alert(
                    'Delete Favorite?',
                    'Are you sure you wish to delete the favorite dish ' + item.name + '?',
                    [
                        { 
                            text: 'Cancel', 
                            onPress: () => console.log(item.name + 'Not Deleted'),
                            style: ' cancel'
                        },
                        {
                            text: 'OK',
                            onPress: () => this.props.deleteFavorite(item._id)
                        }
                    ],
                    { cancelable: false }
                  );
                  
                }
              }
          ];

          return (
              <Swipeout right={rightButton} autoClose={true}>
                
                  <ListItem
                      key={index}
                      title={item.name}
                      subtitle={item.description}
                      hideChevron={true}
                      onPress={() => navigate('Dishdetail', { dishId: item._id })}
                      leftAvatar={{ source: {uri: baseUrl + item.image}}}
                      />
                
              </Swipeout>
          );
        };

        if (this.props.dishes.isLoading) {
            return(
                <Loading />
            );
        }
        else if (this.props.dishes.errMess) {
            return(
                <View>            
                    <Text>{this.props.dishes.errMess}</Text>
                </View>            
            );
        }
        else if (this.props.favorites.length==0) {
          return(
              <View>            
                  <Card title={'Favorites Not Added'} > 
                        <Button
                            icon={<Icon  name="hand-o-right" size={20} color="white" marginRight="10"/> }
                            color='#512DA8'
                            onPress={() => navigate('Menu')}
                            buttonStyle={{borderRadius: 0, marginLeft: 0, marginRight: 0, color: '#512DA8', marginBottom: 0}}
                            title={ 'Go to Menu'}>
                        </Button>
                    </Card>
              </View>            
          );
      }
        else {
            return (
              <Animatable.View animation="fadeInDown" duration={2000} delay={1000}> 
                <FlatList 
                    data={this.props.dishes.dishes.filter(dish => this.props.favorites.some(el => el === dish._id))}
                    renderItem={renderMenuItem}
                    keyExtractor={item => item._id.toString()}
                    />
              </Animatable.View>
            );
        }
    }
}


export default connect(mapStateToProps, mapDispatchToProps)(Favorites);