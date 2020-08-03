import React from 'react';
import { View, Text, FlatList, Animated, Easing } from 'react-native';
import { Tile, Card, Button } from 'react-native-elements';
import { connect } from 'react-redux';
import * as Animatable from 'react-native-animatable';
import { baseUrl } from '../shared/baseUrl';
import { Loading } from './LoadingComponent';
import Icon  from 'react-native-vector-icons/FontAwesome';


const mapStateToProps = state => {
    
    return {
      dishes: state.dishes,
    }
}

class Menu extends React.Component{

    constructor(props) {
        super(props);
        this.animatedValue = new Animated.Value(0);        
    }

    componentDidMount () {
       this.animate()
    }

    animate () {
        this.animatedValue.setValue(0)
        Animated.timing(
          this.animatedValue,
          {
            toValue: 20,
            duration: 20000,
            easing: Easing.linear,
            useNativeDriver:true
          },
        ).start(() => this.animate())
    }

    static navigationOptions = {
        title: 'Menu',
    };

    render()
    {
        const { navigate } = this.props.navigation;

        const xpos1 = this.animatedValue.interpolate({
            inputRange: [0, 2, 5, 10, 15, 18,20],
            outputRange: [0, 0,-100, 0, 100, 0, 0]
        })

        const xpos2 = this.animatedValue.interpolate({
            inputRange: [0, 2, 5, 10, 15, 18, 20],
            outputRange: [0, 0, 100, 0, -100, 0, 0]
        })

        const ypos = this.animatedValue.interpolate({
            inputRange: [0, 2, 5, 10, 15, 18, 20],
            outputRange: [0, 0, -150, -300, -150, 0, 0]
        })

        const renderMenuItem = ({item, index}) => {
            if(index%2==0)
            return (
                
                <Animated.View style={{ width: '100%', transform: [{translateX: xpos1},{translateY: ypos}]}}>
                    <Card featuredTitle={item.name} featuredSubtitle={item.designation} image={{uri: baseUrl + item.image}}> 
                        <Button
                            icon={<Icon  name="hand-o-right" size={20} color="white" marginRight="10"/> }
                            color='#512DA8'
                            onPress={() => navigate('Dishdetail', { dishId: item.id })}
                            buttonStyle={{borderRadius: 0, marginLeft: 0, marginRight: 0, color: '#512DA8', marginBottom: 0}}
                            title={` Press for ${item.name}`}>
                        </Button>
                    </Card>
                    {/* <Tile
                        key={index}
                        title={item.name}
                        caption={item.description}
                        featured
                        onPress={() => navigate('Dishdetail', { dishId: item.id })}
                        imageSrc={{ uri: baseUrl + item.image}}
                    /> */}
                </Animated.View>
            );
            else
            return (
                
                <Animated.View style={{ width: '100%', transform: [{translateX: xpos2},{translateY: ypos}]}}>
                    <Card featuredTitle={item.name} featuredSubtitle={item.designation} image={{uri: baseUrl + item.image}}> 
                        <Button
                            icon={<Icon  name="hand-o-right" size={20} color="white" marginRight="10"/> }
                            onPress={() => navigate('Dishdetail', { dishId: item.id })}
                            buttonStyle={{borderRadius: 0, marginLeft: 0, marginRight: 0, marginBottom: 0}}
                            title={` Press for ${item.name}`}>
                        </Button>
                    </Card>
                    {/* <Tile
                        key={index}
                        title={item.name}
                        caption={item.description}
                        featured
                        onPress={() => navigate('Dishdetail', { dishId: item.id })}
                        imageSrc={{ uri: baseUrl + item.image}}
                    /> */}
                </Animated.View>
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
                    <Text>{props.dishes.errMess}</Text>
                </View>            
            );
        }
        else {
            return (
                <FlatList 
                    data={this.props.dishes.dishes}
                    renderItem={renderMenuItem}
                    keyExtractor={item => item.id.toString()}
                />
            );
        }
    }
}

export default connect(mapStateToProps)(Menu);