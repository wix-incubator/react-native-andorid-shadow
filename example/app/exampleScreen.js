
import React, { Component } from 'react';
import ReactNative, {
  StyleSheet,
  Text,

  ListView,
  FlatList,
  Platform
} from 'react-native';
import { View, Button, Card } from 'react-native-ui-lib';
import { AndroidShadowManager } from 'react-native-android-shadow';

const ANDROID_PLATFORM = (Platform.OS === 'android');

export default class exampleScreen extends Component {

  render() {
    console.log('rendering!');
    const shadowProps = StyleSheet.flatten(styles.shadowStyle1);
    // var fruits = ["Banana", "Orange", "Apple", "Mango"];
    var fruits = [];
    for (i = 0; i < 11; i++) {
      fruits.push({ key: i, txt: 'Lemon ' + i });
    }
    const ds = new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 });
    const data = ds.cloneWithRows(fruits);

    return (
      <View style={styles.container}>


        <Text style={styles.welcome}>
          Welcome to React Native!
              </Text>

        {/*<ListView dataSource={data} enableEmptySections
          renderRow={this._renderRow.bind(this)}>
        </ListView>*/}

        {/*<FlatList
          data={fruits}
          renderItem={({item}) => this._renderRow(item)}
        />*/}


        <View style={[styles.buttonContainer]}>
          <Button
            label="Normal Shadow" onPress={() => { }}
            ref={(r) => { this.applyShadowForButton(r); }}
            enableShadow />
        </View>

        <Button style={{ marginTop: 45,marginBottom:30 }} label="No Shadow" onPress={() => { }} />

        <Card height={200} style={{  padding: 10 }}
          ref={element => (this.applyShadowForCard(element))}>
          <Text>
            Card with shadow
            </Text>
        </Card>

      </View>
    );
  }

  _renderRow(fruit) {
    return (
      <View style={styles.listItemContainer}>

        <Button
          label={fruit.txt} onPress={() => { }}
          enableShadow />
      </View>
    );
  }

  applyShadowForCard(card) {
    if (ANDROID_PLATFORM) {
      const cardTag = ReactNative.findNodeHandle(card);
      if (cardTag) {
        AndroidShadowManager.applyShadowForView(cardTag, {
          insetX: 4,
          insetY: 4,
          offsetX: 0,
          offsetY: -10,
          cornerRadius: 12,
          elevation: 9
        });
      }
    }
  }
  applyShadowForButton(button) {
    if (ANDROID_PLATFORM) {
      const buttonTag = ReactNative.findNodeHandle(button);
      if (buttonTag) {
        AndroidShadowManager.applyShadowForView(buttonTag, {
          insetX: 4,
          insetY: 4,
          offsetX: 0,
          offsetY: 0,
          cornerRadius: 27,
          elevation: 9
        });
      }
    }
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
