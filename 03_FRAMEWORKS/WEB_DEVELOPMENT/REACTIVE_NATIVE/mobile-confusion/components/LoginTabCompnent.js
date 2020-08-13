import React, { Component } from 'react';
import { View, Text, StyleSheet} from 'react-native';
import { Input, CheckBox, Button, Icon } from 'react-native-elements';
import * as SecureStore from 'expo-secure-store';
import { connect } from 'react-redux';
import {login } from '../redux/ActionCreators';

const mapStateToProps = state => {
    return {
        user:state.user
    }
  }
  
const mapDispatchToProps = dispatch => ({
    login: (username, password) => dispatch(login(username, password))
})

class LoginTab extends Component {

    constructor(props) {

        super(props);
        this.state = {
            username: '',
            password: '',
            msg:'',
            remember: false
        }
    }

    componentDidMount() {

        SecureStore.getItemAsync('userinfo')
        .then((userdata) => {
            
            let userinfo = JSON.parse(userdata);
            if (userinfo) {
                this.setState({username: userinfo.username});
                this.setState({password: userinfo.password});
                this.setState({remember: true})
            }
        })
    }

    static navigationOptions = {

        title: 'Login',
        tabBarIcon: ({ tintColor }) => (
            <Icon
              name='sign-in'
              type='font-awesome'            
              size={24}
              iconStyle={{ color: tintColor }}
            />
        ) 
    };

    handleLogin() {

        console.log(JSON.stringify(this.state));
        this.props.login(this.state.username, this.state.password)
        .then(()=>{
            console.log('Login Response---------------'+JSON.stringify(this.props.user));
            if(this.props.user.user){
    
                if (this.state.remember){
                    var userInfo = JSON.stringify({
                            username: this.state.username, 
                            password: this.state.password,
                            token:this.props.user.user.token
                    })
                    SecureStore.setItemAsync('userinfo', userInfo )
                    .catch((error) => console.log('Could not save user info', error));
                    
                }
                else{
                    SecureStore.deleteItemAsync('userinfo')
                    .catch((error) => console.log('Could not delete user info', error));
                    this.setState({
                        username:'',
                        password:''
                    });
                }
                this.props.navigation.navigate('LoggedIn');
               
            }  
            else{
                this.setState({
                    msg: this.props.user.errMsg
                });
            }
        })
        
    }

    render() {

        return (
            <View style={styles.container}>
                <Input
                    placeholder="Username"
                    leftIcon={{ type: 'font-awesome', name: 'user-o' }}
                    onChangeText={(username) => this.setState({username})}
                    value={this.state.username}
                    containerStyle={styles.formInput}
                    />
                <Input
                    placeholder="Password"
                    leftIcon={{ type: 'font-awesome', name: 'key' }}
                    onChangeText={(password) => this.setState({password})}
                    value={this.state.password}
                    containerStyle={styles.formInput}
                    />
                <CheckBox title="Remember Me"
                    center
                    checked={this.state.remember}
                    onPress={() => this.setState({remember: !this.state.remember})}
                    containerStyle={styles.formCheckbox}
                    />
                <Text>{this.state.msg}</Text>
                <View style={styles.formButton}>
                    <Button
                        onPress={() => this.handleLogin()}
                        title=" Login"
                        icon={
                            <Icon
                                name='sign-in'
                                type='font-awesome'            
                                size={24}
                                color= 'white'
                            />
                        }
                        buttonStyle={{
                            backgroundColor: "#512DA8"
                        }}
                        />
                </View>
                
                 <View style={styles.formButton}>
                    <Button
                        onPress={() => this.props.navigation.navigate('Register')}
                        title=" Register"
                        clear
                        icon={
                            <Icon
                                name='user-plus'
                                type='font-awesome'            
                                size={24}
                                color= 'white'
                            />
                        }
                        titleStyle={{
                            color: "white"
                        }}
                        buttonStyle={{
                            backgroundColor: "#512DA8"
                        }}
                        />
                </View> 
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        justifyContent: 'center',
        margin: 20,
    },
    imageContainer: {
        flex: 1,
        flexDirection: 'row',
        margin: 20
    },
    image: {
      margin: 10,
      width: 80,
      height: 60
    },
    formInput: {
        margin: 10
    },
    formCheckbox: {
        margin: 20,
        backgroundColor: null
    },
    formButton: {
        margin: 20
    }
});

export default connect(mapStateToProps, mapDispatchToProps)(LoginTab);