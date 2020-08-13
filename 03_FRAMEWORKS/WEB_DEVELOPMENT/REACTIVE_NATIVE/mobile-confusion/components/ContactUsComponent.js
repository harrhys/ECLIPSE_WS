import React, { Component } from 'react';
import { Text} from 'react-native';
import { Card , Button, Icon} from 'react-native-elements';
import * as Animatable from 'react-native-animatable';
import * as MailComposer  from 'expo-mail-composer';

class Contact extends Component {

    static navigationOptions = {
        title: 'Contact Us',
    };

    sendMail() {
        MailComposer.composeAsync({
            recipients: ['harrhys@gmail.com'],
            subject: 'Enquiry',
            body: 'To whom it may concern:'
        })
    }


    render() {
        
        return(
            <Animatable.View animation="fadeInDown" duration={2000} delay={100}> 
                <Card title={'Contact Information'}>
                    <Text style={{margin: 10}}> 
                        104, Saroj Residency
                    </Text>
                    <Text style={{margin: 10}}> 
                        Silver Spring Road, Marathahalli
                    </Text>
                    <Text style={{margin: 10}}> 
                        Bengaluru, Karnataka
                    </Text>
                    <Text style={{margin: 10}}> 
                        Tel: +91 80 40960555
                    </Text>
                    <Text style={{margin: 10}}> 
                        Mob: +91 98862247762
                    </Text>
                    <Text style={{margin: 10}}> 
                        Email: harrhys@gmail.com
                    </Text>
                    <Text></Text>
                    <Button
                        title="Send Email"
                        buttonStyle={{backgroundColor: "#512DA8"}}
                        icon={<Icon name='envelope-o' type='font-awesome' color='white' />}
                        onPress={()=>this.sendMail()}
                    />
                </Card>
            </Animatable.View>
        );
    }
}

export default Contact;