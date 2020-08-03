import React, { Component } from 'react';
import { View, Platform, Text, ScrollView, Image, StyleSheet } from 'react-native';
import { createStackNavigator, createDrawerNavigator, DrawerItems, SafeAreaView } from 'react-navigation';
import Constants from 'expo-constants';
import { Icon } from 'react-native-elements';
import { connect } from 'react-redux';
import * as SecureStore from 'expo-secure-store';
import { fetchDishes, fetchComments, fetchPromos, fetchLeaders } from '../redux/ActionCreators';

import LoginTabPage from './LoginTabComponent';
import Account from './AccountComponent';
import LoginPage from './LoginPageComponent';
import Logout from './LogoutComponent';
import Home from './HomeComponent';
import AboutUs from './AboutUsComponent';
import Menu from './MenuComponent';
import DishDetail from './DishDetailComponent';
import Contact from './ContactUsComponent';
import Reservation from './ReservationComponent';
import Favorites from './FavoriteComponent';
import AppError from './ErrorComponent';

const mapStateToProps = state => {
  return {
  }
}

const mapDispatchToProps = dispatch => ({
  fetchDishes: () => dispatch(fetchDishes()),
  fetchComments: () => dispatch(fetchComments()),
  fetchPromos: () => dispatch(fetchPromos()),
  fetchLeaders: () => dispatch(fetchLeaders()),
})

const LoginTabNavigator = createStackNavigator({
  LoginTabPage: LoginTabPage
}, {
navigationOptions: ({ navigation }) => ({
  headerStyle: {
      backgroundColor: "#512DA8"
  },
  headerTitleStyle: {
      color: "#fff"            
  },
  title: 'Login',
  headerTintColor: "#fff",
  headerLeft: <Icon name="menu" size={24}
    iconStyle={{ color: 'white' }} 
    onPress={ () => navigation.toggleDrawer() } />    
})
});

const AccountNavigator = createStackNavigator(
  {
    Account: { 
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

const loginuser = () => this.login();

const LoginPageNavigator = createStackNavigator(
  {
    Login: { 
      screen: LoginPage ,
      navigationOptions: ({ navigation, loginuser }) => (
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

const HomeNavigator = createStackNavigator(
  {
    Home: { 
      screen: Home ,
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

const AboutUSNavigator = createStackNavigator(
  {
    AboutUS: { 
      screen: AboutUs ,
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


const MenuNavigator = createStackNavigator(
  {
    Menu: { 
      screen: Menu,
      navigationOptions: ({ navigation }) => (
        {
          headerLeft: <Icon name="menu" size={28} 
          iconStyle={{ color: 'white', margin:15 }} 
          onPress={ () => navigation.toggleDrawer() } />          
        }
      )  
    },
    Dishdetail: { 
      screen: DishDetail 
    }
  },
  {
    initialRouteName: 'Menu',

    navigationOptions: {

      headerStyle: {
        backgroundColor: "#512DA8"
      },

      headerTitleStyle: {
        color: "#fff"            
      },

      headerTintColor: '#fff'
    
    }
  }
);

const ReservationNavigator = createStackNavigator(
  {
    Reservation: { screen: Reservation }
  }, 
  {
    navigationOptions: ({ navigation }) => (
      {
        headerLeft: <Icon name="menu" size={24}
          iconStyle={{ color: 'white', margin:10  }} 
          onPress={ () => navigation.toggleDrawer() } /> ,  

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
)

const FavoritesNavigator = createStackNavigator(
  {
    Favorites: { screen: Favorites }
  }, 
  {
    navigationOptions: ({ navigation }) => (
      {
        headerLeft: <Icon name="menu" size={24}
        iconStyle={{ color: 'white', margin:15 }} 
          onPress={ () => navigation.toggleDrawer() } /> ,  

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
)

const ContactUSNavigator = createStackNavigator(
  {
    ContactUs: { screen: Contact,
      navigationOptions: ({ navigation }) => ({

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

      })   
    }
  }
);

const LogoutNavigator = createStackNavigator(
  {
    Logout: { screen: Logout,
      navigationOptions: ({ navigation }) => ({

        headerLeft: <Icon name="sign-out" size={28} 
        iconStyle={{ color: 'white', margin:15 }} 
        onPress={ () => navigation.toggleDrawer() } />,

        headerStyle: {
          backgroundColor: "#512DA8"
        },

        headerTitleStyle: {
        color: "#fff"            
        },

        headerTintColor: "#fff"  

      })   
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
    Account: 
      { screen: AccountNavigator,
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
        navigationOptions: ({login})=> ({
          title: 'Login',
          drawerLabel: 'Login',
          drawerIcon: ({tintColor}) => (
            <Icon name="sign-in" type="font-awesome" size={24} color={tintColor} />
            ),
        })
      }
  },
  {
    initialRouteName:'Login',
    drawerBackgroundColor: '#D1C4E9',
    contentComponent: CustomDrawerContentComponent
  }
);

const MainNavigator = createDrawerNavigator(
  {
    LoginTab: 
      { screen: LoginTabNavigator,
        navigationOptions: {
          title: 'Login',
          drawerLabel: 'Login',
          drawerIcon: ({tintColor}) => (
            <Icon name="sign-in" type="font-awesome" size={24} color={tintColor} />
            ),
        }
      }
      ,
    Home: 
      { screen: HomeNavigator,
        navigationOptions: {
          title: 'Home',
          drawerLabel: 'Home',
          drawerIcon: ({tintColor}) => (
            <Icon name="home" type="font-awesome" size={24} color={tintColor} />
            ),
        }
      }
      ,
    AboutUS: 
      { screen: AboutUSNavigator,
        navigationOptions: {
          title: 'About Us',
          drawerLabel: 'About Us',
          drawerIcon: ({tintColor}) => (
            <Icon name="info" type="font-awesome" size={24} color={tintColor} />
            ),
        }, 
      },
    Menu: 
      { screen: MenuNavigator,
        navigationOptions: {
          title: 'Menu',
          drawerLabel: 'Menu',
          drawerIcon: ({ tintColor, focused }) => (
            <Icon
              name='list'
              type='font-awesome'            
              size={24}
              color={tintColor}
            />
          ),
        }, 
      },
    Reservation:
      { screen: ReservationNavigator,
        navigationOptions: {
          title: 'Reserve Table',
          drawerLabel: 'Reserve Table',
          drawerIcon: ({ tintColor, focused }) => (
            <Icon
              name='cutlery'
              type='font-awesome'            
              size={24}
              iconStyle={{ color: tintColor }}
            />
          ),
        }
      },
    Favorites:
      { screen: FavoritesNavigator,
        navigationOptions: {
          title: 'Favorites',
          drawerLabel: 'Favorites',
          drawerIcon: ({ tintColor, focused }) => (
            <Icon
              name='heart'
              type='font-awesome'            
              size={24}
              iconStyle={{ color: tintColor }}
            />
          ),
        }
      },
    ContactUS: 
      { screen: ContactUSNavigator,
        navigationOptions: {
          title: 'Contact Us',
          drawerLabel: 'Contact Us',
          drawerIcon: ({tintColor, focused}) => (
            <Icon name="address-card" type="font-awesome" size={22} color={tintColor} />
          )
        }, 
      },
    Logout: 
      { screen: Logout,
        navigationOptions: {
          title: 'Logout',
          drawerLabel: 'Logout',
          drawerIcon: ({tintColor, focused}) => (
            <Icon name="sign-out" type="font-awesome" size={22} color={tintColor} onPress={navigation=>navigation.push('LoggedOut')}/>
          )
         
        }
      }
  }, 
  {
    initialRouteName:'Home',
    drawerBackgroundColor: '#D1C4E9',
    contentComponent: CustomDrawerContentComponent
  }
);

const AppNavigator = createStackNavigator({

    LoggedOut:{screen:LoginNavigator, navigationOptions:
      {header:null}},
    LoggedIn:{screen:MainNavigator, navigationOptions:
    {header:null}}

});






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

class Main extends Component {
    constructor(props) {
      super(props);
      this.state = {
            selectedDish: null,
            isUserLoggedIn: false
    };
  }

  UNSAFE_componentWillUpdate(){

  }

  componentDidMount() {

    console.disableYellowBox = true;
    this.props.fetchDishes();
    this.props.fetchComments();
    this.props.fetchPromos();
    this.props.fetchLeaders();
    SecureStore.getItemAsync('userinfo')
        .then((userdata) => {
            let userinfo = JSON.parse(userdata);
            if (userinfo) {
              this.setState({isUserLoggedIn: true})
            }
        })
        .catch((error) => console.log('Could not load user info', error));
  }

  login(){
    this.setState({isUserLoggedIn:true})
  }

  logout(){
    SecureStore.deleteItemAsync('userinfo')
    .catch((error) => console.log('Could not delete user info', error));
    this.setState({isUserLoggedIn:false});
  }

  render() {

    return (
      <View style={{flex:1, paddingTop: Platform.OS === 'ios' ? 0 : Constants.statusBarHeight }}>
        <AppError>
          <MainNavigator />
        </AppError>
      </View>
    );

    /*  if(this.state.isUserLoggedIn)
      return (
        <View style={{flex:1, paddingTop: Platform.OS === 'ios' ? 0 : Constants.statusBarHeight }}>
          <AppError>
            <MainNavigator />
          </AppError>
        </View>
      );
    else
      return (
        <View style={{flex:1, paddingTop: Platform.OS === 'ios' ? 0 : Constants.statusBarHeight }}>
          <AppError>
            <LoginNavigator login={()=>this.login()} />
          </AppError>
        </View>
      );  */
  }
}
  
export default connect(mapStateToProps, mapDispatchToProps)(Main);