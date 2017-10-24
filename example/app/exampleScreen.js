
import React, { Component } from 'react';
import ReactNative, {
  StyleSheet,
  Text,
  
  ListView,
  FlatList,
  Platform
} from 'react-native';
import { View,Button } from 'react-native-ui-lib';
import { AndroidShadowManager, ShadowParentView } from 'react-native-android-shadow';

const ANDROID_PLATFORM = (Platform.OS === 'android');
const ButtonContainer = ANDROID_PLATFORM ? ShadowParentView : View;

export default class exampleScreen extends Component {

  render() {
    console.log('rendering!');
    const shadowProps = StyleSheet.flatten(styles.shadowStyle1);
    // var fruits = ["Banana", "Orange", "Apple", "Mango"];
    var fruits = [];
    for (i = 0; i < 11; i++) {
      fruits.push({key:i, txt: 'Lemon '+i });
    }
    const ds = new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 });
    const data = ds.cloneWithRows(fruits);

    return (
      <View style={styles.container}>

        <ButtonContainer style={[styles.buttonContainer,styles.shadowStyleTxt]}
        shadowStyle={StyleSheet.flatten(styles.shadowStyleTxt)}>
          <Text style={styles.welcome}>
            Welcome to React Native!
              </Text>
        </ButtonContainer>

        {/*<ListView dataSource={data} enableEmptySections
          renderRow={this._renderRow.bind(this)}>
        </ListView>*/}

        {/*<FlatList
          data={fruits}
          renderItem={({item}) => this._renderRow(item)}
        />*/}


        <ButtonContainer style={[styles.buttonContainer]}>
          <Button
            label="Normal Shadow" onPress={() => { }}
            ref={(r) => { this.applyShadowForButton(r); }}
            enableShadow />
        </ButtonContainer>

        <ButtonContainer style={[styles.buttonContainer,styles.shadowStyle2]}>
          <Button
            style={styles.shadowStyle2}
            label="Max Shadow " onPress={() => { }}
            ref={(r) => { this.applyShadowForButton(r); }}
            enableShadow />
        </ButtonContainer>

        <Button style={{ marginTop: 45 }} label="No Shadow" onPress={() => { }} />

      </View>
    );
  }

  _renderRow(fruit) {
    return (
      <View style={styles.listItemContainer}>
        <ButtonContainer style={styles.buttonContainer}
          shadowStyle={StyleSheet.flatten(styles.shadowStyle1)}>
          <Button
            label={fruit.txt} onPress={() => { }}
            enableShadow />
        </ButtonContainer>
      </View>
    );
  }

  // render() {

  //   if (ANDROID_PLATFORM) {
  //     return this.renderAndroid();
  //   }
  //   return this.renderIos();

  // }
  applyShadowForButton(button) {
    // if (ANDROID_PLATFORM) {
    //   const buttonTag = ReactNative.findNodeHandle(button);
    //   AndroidShadowManager.applyShadowForView(buttonTag,{});
    // }
  }
}
const SHAPE_DIAMETER = 80;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
    // backgroundColor: '#F5FCFF',
  },
  buttonContainer: {
    // padding: 20,
    // backgroundColor: '#FF0000',
    // borderRadius: 6,
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
    alignItems: 'flex-start',
    marginTop: 40
  },
  listItemContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 30
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    // margin: 30,
  },
  shadowStyle1: {
    shadowColor: '#3082C8',
    shadowOffset: { height: 5, width: 0 },
    shadowOpacity: 0.35,
    shadowRadius: 9.5,
  },
  shadowStyle2: {
    // shadowColor: '#459FED',
    shadowColor: '#008200',
    shadowOffset: { height: 15, width: 15 },
    shadowOpacity: 1,
    shadowRadius: 9.5,
  },
shadowStyleTxt: {
    // shadowColor: '#459FED',
    shadowColor: '#000000',
    shadowOffset: { height: 5, width: 0 },
    shadowOpacity: 1,
    shadowRadius: 9.5,
  },
  innerCircle: {
    width: SHAPE_DIAMETER,
    height: SHAPE_DIAMETER,
    borderRadius: SHAPE_DIAMETER / 2,
    backgroundColor: 'white',
    justifyContent: 'center',
  },
  redCircle: {
    width: SHAPE_DIAMETER,
    height: SHAPE_DIAMETER,
    borderRadius: SHAPE_DIAMETER / 2,
    backgroundColor: 'blue',
    margin: 40,
    justifyContent: 'center',
    alignItems: 'center',
  },
});
