import React from 'react';
import {View, ScrollView, Text, Button, Alert, StyleSheet, Image} from 'react-native';
import  {Input, CheckBox} from 'react-native-elements';
import * as SecureStore from 'expo-secure-store';
import Main from './MainComponent';
import { createBottomTabNavigator } from 'react-navigation';
import { baseUrl } from '../shared/baseUrl';


class LoginPage extends React.Component{

    constructor(props){
        super(props);
        this.state = {
            username:'',
            password:'',
            remember: false
        }
    }

    componentDidMount(){

        console.disableYellowBox = true;
        SecureStore.getItemAsync('userinfo')
        .then((userdata) => {
            let userinfo = JSON.parse(userdata);
            if (userinfo) {
                console.log(userinfo);
                this.setState({username: userinfo.username});
                this.setState({password: userinfo.password});
                this.setState({remember: true});
            }
        })
        .catch((error) => console.log('Could not load user info', error));
    }

    static navigationOptions = {
        title: 'Login'
    }

    handleLogin(){

        if(this.state.username=='' || this.state.password==''){

            Alert.alert(
                'Credentials required',
                'Please enter Username and password',
                [ {text: 'OK', onPress: () => console.log('Cancel Pressed'), style: 'cancel'}],
                { cancelable: false }
            );
        }
        else{

            SecureStore.getItemAsync('userinfo')
            .then((userdata) => {
                let userinfo = JSON.parse(userdata);
                console.log(userinfo);
                console.log('handle Login : Stored Username: ' + userinfo.username + ', Stored Password:' +  userinfo.password);

            });

            console.log(JSON.stringify(this.state));

            if(this.state.remember){

                SecureStore.setItemAsync('userinfo', JSON.stringify({
                    username:this.state.username,
                    password:this.state.password
                }))
                .catch(error => console.log('couldnt save userinfo', error));

                var userinfo = SecureStore.getItemAsync('userinfo');
                console.log(userinfo);
                console.log('Stored Username: ' + userinfo.username + ', Stored Password:' +  userinfo.password);
            }
            else{

                SecureStore.deleteItemAsync('userinfo')
                .catch(error => console.log('couldnt delete userinfo', error));
                this.setState({username: ''});
                this.setState({password: ''});
                this.setState({remember: false});

            }

            this.props.navigation.navigate('LoggedIn');
        }

    }

    validateLogin(){

        SecureStore.getItemAsync('userinfo')
        .then((userdata) => {
            let userinfo = JSON.parse(userdata);
            if (userinfo) {
                this.setState({isLoggedIn: true});
                this.props.navigation.navigate('LoggedIn');
            }
           
        })
        .catch((error) => console.log('Could not load user info', error));
              
    }

    render(){

        this.validateLogin();
        
        return(
            <View style={styles.container}> 

                <Input
                    placeholder="Username"
                    leftIcon={{type:'font-awesome', name:'user-o', }}
                    onChangeText={username => this.setState({username:username})}
                    value= {this.state.username}
                    containerStyle= {styles.formInput}
                />
                <Input
                    placeholder="Password"
                    leftIcon={{type:'font-awesome', name:'key', }}
                    onChangeText={password => this.setState({password:password})}
                    value= {this.state.password}
                    containerStyle= {styles.formInput}
                />
                <CheckBox
                    title='Remember me'
                    center
                    checked={this.state.remember}
                    onPress={()=> this.setState({remember:!this.state.remember})}
                    containerStyle={styles.CheckBox}
                />         
                <View style={styles.container}>
                    <Button
                        onPress={()=> this.handleLogin()}
                        title='Login'
                    />
                    <Text></Text>
                    <Button
                        onPress={()=> this.props.navigation.navigate('Account')}
                        title='Create Account'
                     />
                </View>    
                     
                

            </View>
        );
        
    }
}

const styles = StyleSheet.create({
    container: {
        justifyContent: 'center',
        margin:20,
    },
    formInput :{
        margin:40
    },
    formCheckbox :{
        margin:40,
        backgroundColor: null
    },
    formButton :{
        margin:60
    }
});

export default LoginPage;