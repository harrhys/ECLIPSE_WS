import React from 'react';
import { View,  Text, ScrollView, Image, StyleSheet } from 'react-native';
import { Icon } from 'react-native-elements';
import { createStackNavigator, createDrawerNavigator, DrawerItems, SafeAreaView } from 'react-navigation';
import LoginPage from './LoginPageComponent';
import Account from './AccountComponent';


const CreateAccountNavigator = createStackNavigator(
    {
    CreateAccount: { 
        screen: Account ,
        navigationOptions: ({ navigation }) => (
          {
            headerLeft: <Icon name="menu" size={28} 
            iconStyle={{ color: 'white', margin:15 }} 
            onPress={ () => navigation.toggleDrawer() } />,
  
            headerStyle: {
              backgroundColor: "#512DA8"
            },
  
            headerTitleStyle: {
            color: "#fff"            
            },
  
            headerTintColor: "#fff" 
              
          }
        )
      }
    }
);

const LoginPageNavigator = createStackNavigator(
    {
      Login: { 
        screen: LoginPage ,
        navigationOptions: ({ navigation }) => (
          {
            headerLeft: <Icon name="menu" size={28} 
            iconStyle={{ color: 'white', margin:15 }} 
            onPress={ () => navigation.toggleDrawer() } />,
  
            headerStyle: {
              backgroundColor: "#512DA8"
            },
  
            headerTitleStyle: {
            color: "#fff"            
            },
  
            headerTintColor: "#fff" 
              
          }
        )
      }
    }
);

const CustomDrawerContentComponent = (props) => (
<ScrollView>
    <SafeAreaView style={styles.container} forceInset={{ top: 'always', horizontal: 'never' }}>
    <View style={styles.drawerHeader}>
        <View style={{flex:1}}>
        <Image source={require('./images/logo.png')} style={styles.drawerImage} />
        </View>
        <View style={{flex: 3}}>
        <Text style={styles.drawerHeaderText}>Ristorante Con Fusion</Text>
        </View>
    </View>
    <DrawerItems {...props} />
    </SafeAreaView>
</ScrollView>
);

const LoginNavigator = createDrawerNavigator(

    { 
      CreateAccount: 
        { screen: CreateAccountNavigator,
          navigationOptions: {
            title: 'Create Account',
            drawerLabel: 'Create Account',
            drawerIcon: ({tintColor}) => (
              <Icon name="sign-in" type="font-awesome" size={24} color={tintColor} />
              ),
            
          }
        
        },

        Login: 
        { screen: LoginPageNavigator,
          navigationOptions: {
            title: 'Login',
            drawerLabel: 'Login',
            drawerIcon: ({tintColor}) => (
              <Icon name="sign-in" type="font-awesome" size={24} color={tintColor} />
              ),
          }
        }
    },
    {
      initialRouteName:'Login',
      drawerBackgroundColor: '#D1C4E9',
      contentComponent: CustomDrawerContentComponent
    }
);

const styles = StyleSheet.create({
    container: {
      flex: 1,
    },
    drawerHeader: {
      backgroundColor: '#512DA8',
      height: 80,
      alignItems: 'center',
      justifyContent: 'center',
      flex: 1,
      flexDirection: 'row'
    },
    drawerHeaderText: {
      margin: 10,
      color: 'white',
      fontSize: 18,
      fontWeight: 'bold'
    },
    drawerImage: {
      margin: 10,
      width: 60,
      height: 45
    }
  });

class Login extends React.Component{

    constructor(props){
        this.state={
            isLoggedin:false
        }
    }

    login(){
        this.setState({isLoggedin:true})
    }

    render(){
        if(this.state.isLoggedin)
        return(
            <LoginNavigator login={()=>this.login()}/>
        );
        else
        return(
            <Main />
        );
    }

}

export default Login;