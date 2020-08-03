import React, { Component , useState } from 'react';
import { Text, View, ScrollView, StyleSheet, Picker, Switch, Button, Modal } from 'react-native';
import { Icon, Card } from 'react-native-elements';
import DateTimePickerModal from "react-native-modal-datetime-picker";

class CButton extends Component{

    constructor(props){
        super(props);
        this.state={
            isDatePickerVisible:false
        }
    }

    

    render(){

        const showDatePicker = () => { this.setState({ isDatePickerVisible:true,})};
        
        const hideDatePicker = () => { this.setState( {isDatePickerVisible:false, }) };
        
        const handleConfirm = (date) => {
            let d =   date.toDateString()+', ';   
            let h =   date.getHours()+':'; 
            let m =   (date.getMinutes()<1)?'00':date.getMinutes();   
            let ap =   date.getHours()>11?' PM':' AM';     
            this.setState(
                { 
                    isDatePickerVisible:false,
                    date:date,
                    fdate: d + h + m + ap
                }
            )
        };

        return(

            <View style={styles.formRow}>
                    <Button  
                        title="Select Date and Time " 
                        onPress={showDatePicker} 
                        color="#512DA8"
                        style={styles.formItem}
                        />
                    <View onPress={showDatePicker} 
                        style={styles.button}>
                        <Icon
                            name='fontawesome|calender'
                            size={25}
                            color='#512DA8'
                            style={{height:25,width:25}}/>
                        <Text style={styles.buttonText}>Date & Time</Text>
                    </View>
                    <DateTimePickerModal
                        isVisible={this.state.isDatePickerVisible}
                        mode="datetime"
                        onConfirm={handleConfirm}
                        onCancel={hideDatePicker}
                    />
                </View>

        );
    }

}

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

export default CButton;