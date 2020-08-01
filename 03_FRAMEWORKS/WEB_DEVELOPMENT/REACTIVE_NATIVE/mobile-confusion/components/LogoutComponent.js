import React from 'react';
import {View, Text, Button, StyleSheet} from 'react-native';
import  {Input, CheckBox} from 'react-native-elements';
import * as SecureStore from 'expo-secure-store';

class Logout extends React.Component{

   constructor(props){
       super(props);
       SecureStore.deleteItemAsync('userinfo')
            .catch(error => console.log('couldnt delete userinfo', error));
       props.navigation.navigate('LoggedOut');
   }

    render(){

        return(
            <View>
                <Text>Logout Page</Text>
            </View>
        );
    }
}

export default Logout;

