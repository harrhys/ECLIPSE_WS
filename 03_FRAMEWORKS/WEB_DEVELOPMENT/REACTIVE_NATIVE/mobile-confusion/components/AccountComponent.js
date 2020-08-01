import React from 'react';
import {View, Text, Button, Alert, StyleSheet} from 'react-native';
import  {Input, CheckBox} from 'react-native-elements';
import * as SecureStore from 'expo-secure-store';
import Main from './MainComponent';
import LoginPage from './LoginPageComponent';


class Account extends React.Component{

    constructor(props){
        super(props);
        this.state = {
            username:'',
            password:'',
            remember: false,
            message:''
        }
    }

    componentDidMount(){
        console.disableYellowBox = true;
       /*  SecureStore.getItemAsync('userinfo')
        .then((userdata) => {
            let userinfo = JSON.parse(userdata);
            if (userinfo) {
                this.setState({username: userinfo.username});
                this.setState({password: userinfo.password});
                this.setState({remember: true})
            }
        })
        .catch((error) => console.log('Could not load user info', error)); */
    }

    static navigationOptions = {
        title: 'Create Account'
    }

   

    handleCreateAccount(){

        const { navigate } = this.props.navigation;

        console.log(JSON.stringify(this.state));
        if(this.state.username!='' && this.state.password!=''){

            SecureStore.deleteItemAsync('userinfo');
            SecureStore.setItemAsync('userinfo', JSON.stringify({
                username:this.state.username,
                password:this.state.password
            }))
            .catch(error => console.log('couldnt save userinfo', error));

            SecureStore.getItemAsync('userinfo')
            .then((userdata) => {
                let userinfo = JSON.parse(userdata);
                console.log(userinfo);
                console.log('Stored Username: ' + userinfo.username + ', Stored Password:' +  userinfo.password);
    
            });

            this.setState(
                {
                    username:'',
                    password:'',
                    message:'User Account created Successfully click on login to continue'
                }
            );
            Alert.alert(
                'Account Created',
                'Your Account has been created successfully',
                [
                {text: 'Cancel', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
                {text: 'Login', onPress: () =>  navigate('Login')},
                ],
                { cancelable: false } 
            );
        }
        else{
                 Alert.alert(
                'Account Created',
                    'Please enter username and password',
                    [
                    {text: 'Ok', onPress: () => console.log('Cancel Pressed'), style: 'cancel'}
                   
                    ],
                    { cancelable: false }
            );
            
        }
    }

    render(){
        
        
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

                <Text>{this.state.message}</Text>
                     
                <View style={styles.formButton}>
                    <Button
                        onPress={()=> this.handleCreateAccount()}
                        title='Create Account'
                        
                    />
                     <Text></Text>
                    <Button
                        onPress={()=> this.props.navigation.navigate('Login')}
                        title='Login'
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

export default Account;