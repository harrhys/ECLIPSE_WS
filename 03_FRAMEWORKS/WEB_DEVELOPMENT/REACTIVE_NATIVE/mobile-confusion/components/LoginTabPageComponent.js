import React from 'react';
import { createBottomTabNavigator } from 'react-navigation';
import LoginTab from './LoginTabCompnent';
import RegisterTab from './RegisterTabComponent';

const LoginTabPage = createBottomTabNavigator(
    {
        Login: LoginTab,
        Register: RegisterTab
    }, 
    {
        tabBarOptions: {
            activeBackgroundColor: '#9575CD',
            inactiveBackgroundColor: '#D1C4E9',
            activeTintColor: '#ffffff',
            inactiveTintColor: 'gray'
        }
    }
);

export default LoginTabPage;