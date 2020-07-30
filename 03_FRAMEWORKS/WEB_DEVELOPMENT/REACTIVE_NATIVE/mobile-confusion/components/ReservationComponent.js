import React, { Component , useState } from 'react';
import { Text, View, ScrollView, StyleSheet, Picker, Switch, Button, Modal } from 'react-native';
import { Icon, Card } from 'react-native-elements';
import DateTimePickerModal from "react-native-modal-datetime-picker";
import { TextInput } from 'react-native-gesture-handler';

function SelectedDate(props) {
           
    return(
       
        
            <Text style={{margin: 10}}>
            {props.date}
            </Text>
           
      
    );
}

class Reservation extends Component {

    constructor(props) {
        super(props);
        this.state = {
            guests: 1,
            smoking: false,
            date: '',
            isDatePickerVisible:false,
            showModal: false
        }
    }

    static navigationOptions = {
        title: 'Reserve Table',
    };

    toggleModal() {
        this.setState({showModal: !this.state.showModal});
    }

    handleReservation() {
        console.log(JSON.stringify(this.state));
        this.toggleModal();
    }

    resetForm() {
        this.setState({
            guests: 1,
            smoking: false,
            date: '',
            showModal: false
        });
    }

    
    
    render() {

 
        const showDatePicker = () => {
            this.setState(
                {
                    isDatePickerVisible:true,

                }
            )
        };
        
        const hideDatePicker = () => {
            this.setState(
                {
                    isDatePickerVisible:false,
                }
            )
        };
        
        const handleConfirm = (date) => {
            let d =   date.toDateString()+', ';   
            let h =   date.getHours()+':'; 
            let m =   (date.getMinutes()<1)?'00':date.getMinutes();   
            let ap =   date.getHours()>11?' PM':' AM';     
            this.setState(
                { 
                    isDatePickerVisible:false,
                    date: d + h + m + ap
                }
            )
        };

        
        return(
            <ScrollView>
                <View style={styles.formRow}>
                <Text style={styles.formLabel}>Number of Guests</Text>
                <Picker
                    style={styles.formItem}
                    selectedValue={this.state.guests}
                    onValueChange={(itemValue, itemIndex) => this.setState({guests: itemValue})}>
                    <Picker.Item label="1" value="1" />
                    <Picker.Item label="2" value="2" />
                    <Picker.Item label="3" value="3" />
                    <Picker.Item label="4" value="4" />
                    <Picker.Item label="5" value="5" />
                    <Picker.Item label="6" value="6" />
                </Picker>
                </View>
                <View style={styles.formRow}>
                <Text style={styles.formLabel}>Smoking/Non-Smoking?</Text>
                <Switch
                    style={styles.formItem}
                    value={this.state.smoking}
                    trackColor='#512DA8'
                    onValueChange={(value) => this.setState({smoking: value})}>
                </Switch>
                </View>
                <View style={styles.formRow}>
                <Button  
                    title="Select Date and Time " 
                    onPress={showDatePicker} 
                    color="#512DA8"
                    style={styles.formItem}
                    />
                <DateTimePickerModal
                    isVisible={this.state.isDatePickerVisible}
                    mode="datetime"
                    onConfirm={handleConfirm}
                    onCancel={hideDatePicker}
                    
                />
                </View>
                
                <View style={styles.formRow}>
                           
                <Text style={styles.formLabel}>Date and Time :  {this.state.date}</Text>
               
                </View>
                <View style={styles.formRow}>
                <Button
                    onPress={() => this.handleReservation()}
                    title="Reserve"
                    color="#512DA8"
                    accessibilityLabel="Learn more about this purple button"
                    icon={<Icon
                        name='cutlery'
                        type='font-awesome'            
                        size={24}
                       
                      />}
                    />
                </View>
                <Modal animationType = {"slide"} transparent = {false}
                    visible = {this.state.showModal}
                    onDismiss = {() => this.toggleModal() }
                    onRequestClose = {() => this.toggleModal() }>
                    <View style = {styles.modal}>
                        <Text style = {styles.modalTitle}>Your Reservation</Text>
                        <Text style = {styles.modalText}>Number of Guests: {this.state.guests}</Text>
                        <Text style = {styles.modalText}>Smoking?: {this.state.smoking ? 'Yes' : 'No'}</Text>
                        <Text style = {styles.modalText}>Date and Time: {this.state.date}</Text>
                        
                        <Button 
                            onPress = {() =>{this.toggleModal(); this.resetForm();}}
                            color="#512DA8"
                            title="Close" 
                            />
                    </View>
                </Modal>
            </ScrollView>


        );
    }

};

const styles = StyleSheet.create({
    formRow: {
      alignItems: 'center',
      justifyContent: 'center',
      flex: 1,
      flexDirection: 'row',
      margin: 20
    },
    formLabel: {
        fontSize: 18,
        flex: 3
    },
    formItem: {
        flex: 1
    },
    modal: {
        justifyContent: 'center',
        margin: 20
     },
     modalTitle: {
         fontSize: 24,
         fontWeight: 'bold',
         backgroundColor: '#512DA8',
         textAlign: 'center',
         color: 'white',
         marginBottom: 20
     },
     modalText: {
         fontSize: 18,
         margin: 10
     }
});

export default Reservation;