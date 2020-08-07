import React from 'react';
import {View, Text, Button, Alert,StyleSheet} from 'react-native';
import * as SecureStore from 'expo-secure-store';
import { connect } from 'react-redux';
import {logout } from '../redux/ActionCreators';

const mapStateToProps = state => {
    return {
    }
}

const mapDispatchToProps = dispatch => ({
    logout: () => dispatch(logout())
})

class Logout extends React.Component{

   constructor(props){

       super(props);
       this.props.logout();
       props.navigation.navigate('LoggedOut');
   }
   

    render(){
        
        return(
           <Text onPress={this.props.logout()}>Logout Page</Text>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Logout);

