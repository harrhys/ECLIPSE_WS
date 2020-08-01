import React, { Component } from 'react';
import { Text, ScrollView, View, Animated, Easing } from 'react-native';
import { Card } from 'react-native-elements';
import { connect } from 'react-redux';
import { baseUrl } from '../shared/baseUrl';
import { Loading } from './LoadingComponent';

const mapStateToProps = state => {
    
    return {
      dishes: state.dishes,
      comments: state.comments,
      promotions: state.promotions,
      leaders: state.leaders
    }
}

function RenderItem(props) {
    
    const item = props.item;

    if (props.isLoading) {
        return(
                <Loading />
        );
    }
    else if (props.errMess) {
        return(
            <View> 
                <Text>{props.erreMess}</Text>
            </View>
        );
    }
    else { 
        if (item != null) {
            return(
                <Card
                    featuredTitle={item.name}
                    featuredSubtitle={item.designation}
                    image={{uri: baseUrl + item.image}}> 
                    <Text
                        style={{margin: 10}}>
                        {item.description}
                    </Text>
                </Card>
            );
        }
        else {
            return(<View></View>);
        }
    }
}

class Home extends Component {

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
        title: 'Home',
    };

    render() {

        const xpos1 = this.animatedValue.interpolate({
            inputRange: [0, 2, 6, 8, 10, 12, 14, 16, 20],
            outputRange: [0, 0, 620, 620, 340, 340, 200, 0, 0]
        })
        const xpos2 = this.animatedValue.interpolate({
            inputRange: [0, 2, 6, 8, 10, 12, 14, 16, 20],
            outputRange: [0, 0, -280, -280, 340, 340, 200, 0, 0]
        })
        const xpos3 = this.animatedValue.interpolate({
            inputRange: [0, 2, 6, 8, 10, 12, 14, 16, 20],
            outputRange: [0, 0, -280, -280, -560, -560, -560, 0, 0]
        })
        
        return(
            <View style={{flex: 1, flexDirection: 'column', justifyContent: 'center'}}>
            <ScrollView>
            <Animated.View style={{ width: '100%', transform: [{translateY: xpos1}]}}>
                <RenderItem item={this.props.dishes.dishes.filter((dish) => dish.featured)[0]}
                    isLoading={this.props.dishes.isLoading}
                    erreMess={this.props.dishes.erreMess} 
                    />
            </Animated.View>
            <Animated.View style={{ width: '100%',  transform: [{translateY: xpos2}]}}>
                <RenderItem item={this.props.promotions.promotions.filter((promo) => promo.featured)[0]}
                    isLoading={this.props.promotions.isLoading}
                    erreMess={this.props.promotions.erreMess} 
                    />
            </Animated.View>
            <Animated.View style={{ width: '100%',  transform: [{translateY: xpos3}]}}>
                <RenderItem item={this.props.leaders.leaders.filter((leader) => leader.featured)[0]}
                    isLoading={this.props.leaders.isLoading}
                    erreMess={this.props.leaders.erreMess} 
                    />
                </Animated.View>
                </ScrollView>
            </View>
        );
    }
}
                
export default connect(mapStateToProps)(Home);