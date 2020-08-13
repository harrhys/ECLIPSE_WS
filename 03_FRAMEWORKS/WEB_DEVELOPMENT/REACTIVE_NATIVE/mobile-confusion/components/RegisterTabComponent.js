import React, { Component } from 'react';
import { View, StyleSheet, Text, ScrollView, Image } from 'react-native';
import { Input, CheckBox, Button, Icon } from 'react-native-elements';
import * as SecureStore from 'expo-secure-store';
import * as Permissions from 'expo-permissions';
import * as ImagePicker from 'expo-image-picker';
import { connect } from 'react-redux';
import { baseUrl } from '../shared/baseUrl';
import {registerUser } from '../redux/ActionCreators';

const mapStateToProps = state => {
    return {
        user:state.user
    }
  }
  
const mapDispatchToProps = dispatch => ({
    registerUser: (username, password, firstname, lastname, email) => dispatch(registerUser(username, password, firstname, lastname, email))
})

class RegisterTab extends Component {

    constructor(props) {

        super(props);
        this.state = {
            username: '',
            password: '',
            firstname: '',
            lastname: '',
            email: '',
            msg:'',
            remember: false,
            imageUrl: baseUrl + 'images/logo.png'
        }
    }

    getImageFromCamera = async () => {

        const cameraPermission = await Permissions.askAsync(Permissions.CAMERA);
        const cameraRollPermission = await Permissions.askAsync(Permissions.CAMERA_ROLL);

        if (cameraPermission.status === 'granted' && cameraRollPermission.status === 'granted') {

            let capturedImage = await ImagePicker.launchCameraAsync({

                allowsEditing: true,
                aspect: [4, 3],
            });
            if (!capturedImage.cancelled) {

                console.log(capturedImage);
                this.setState({imageUrl: capturedImage.uri });
            }
        }
    }
    
    static navigationOptions = {

        title: 'Register',
        tabBarIcon: ({ tintColor, focused }) => (
            <Icon
              name='user-plus'
              type='font-awesome'            
              size={24}
              iconStyle={{ color: tintColor }}
            />
          ) 
    };

    handleRegister() {

        console.log(JSON.stringify(this.state));
        this.props.registerUser(this.state.username,this.state.password,this.state.firstname,this.state.lastname,this.state.email)
        
        if(this.props.user.errMsg!=null){
            this.setState({msg:this.props.user.errMsg})
        }
        else if(this.props.user.user!=null){
            this.setState({
                msg: 'You are Successfully Registered with username: '+ this.props.user.user.username,
                username:'',
                password:'',
                firstname:'',
                lastname:'',
                email:''
            })
            if (this.state.remember)
                SecureStore.setItemAsync('userinfo', JSON.stringify({username: this.state.username, password: this.state.password}))
                    .catch((error) => console.log('Could not save user info', error));
                console.log(JSON.stringify(this.state));
        }
    }

    render() {

        return(
            <ScrollView>
            <View style={styles.container}>
                {/* <View style={styles.imageContainer}>
                    <Button
                        title="Click your photo"
                        onPress={this.getImageFromCamera}
                    />
                    <Image 
                        source={{uri: this.state.imageUrl}} 
                        //loadingIndicatorSource={require('./images/logo.png')}
                        style={styles.image} 
                    />
                </View> */}
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
                <Input
                    placeholder="First Name"
                    leftIcon={{ type: 'font-awesome', name: 'user-o' }}
                    onChangeText={(firstname) => this.setState({firstname})}
                    value={this.state.firstname}
                    containerStyle={styles.formInput}
                    />
                <Input
                    placeholder="Last Name"
                    leftIcon={{ type: 'font-awesome', name: 'user-o' }}
                    onChangeText={(lastname) => this.setState({lastname})}
                    value={this.state.lastname}
                    containerStyle={styles.formInput}
                    />
                <Input
                    placeholder="Email"
                    leftIcon={{ type: 'font-awesome', name: 'envelope-o' }}
                    onChangeText={(email) => this.setState({email})}
                    value={this.state.email}
                    containerStyle={styles.formInput}
                    />
                <Text>{this.state.msg}</Text>
                <View style={styles.formButton}>
                    <Button
                        onPress={() => this.handleRegister()}
                        title="Register"
                        icon={
                            <Icon
                                name='user-plus'
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
            </View>
            </ScrollView>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        justifyContent: 'center',
        margin: 25,
    },
    imageContainer: {
        flex: 1,
        flexDirection: 'row',
        margin: 20
    },
    image: {
      margin: 10,
      width: 60,
      height: 80
    },
    formInput: {
        margin: 5
    },
    formCheckbox: {
        margin: 5,
        backgroundColor: null
    },
    formButton: {
        margin: 5
    }
});

export default connect(mapStateToProps, mapDispatchToProps)(RegisterTab);