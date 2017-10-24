import _ from 'lodash';
import React, { Component } from 'react';
import { ScrollView, View, Text, StyleSheet, Dimensions, Platform } from 'react-native';
import { Colors, Shadows, Button } from 'react-native-ui-lib'; // eslint-disable-line
// import {Cards} from '../../src';

import { AndroidShadowManager, ShadowParentView } from 'react-native-android-shadow';

const {height} = Dimensions.get('window');

const ANDROID_PLATFORM = (Platform.OS === 'android');
const ShapeContainer = ANDROID_PLATFORM ? ShadowParentView : View;

const shadowsOverWhiteBkg = _.reduce(Shadows, (results, value, key) => {
  if (key.startsWith('white')) {
    results[key] = value;
  }
  return results;
}, {});
const shadowsOverDarkBkg = _.reduce(Shadows, (results, value, key) => {
  if (key.startsWith('dark')) {
    results[key] = value;
  }
  return results;
}, {});

function cardContent(shadowName) {
  return {
    body: {
      topImage: 'http://media.wixapps.net/ggl-104602851384044558414/images/99065eb0442e4911ac6ececee3e00c31~mv2/v1/fit/w_831,h_400,q_75,usm_0.5_0.2_0.0/file.png',
      title: shadowName,
      subtitle: `Shadow is ${shadowName}`,
      description: `This cards demonstrates ${shadowName} (white bkg) applied over a card`
    },
    actions: [
      {
        text: 'action',
      },
    ],
  };
}

export default class ShadowsScreen extends Component {
  renderCards() {
    /*return (
      <View style={{marginVertical: 40}}>
        <Cards.GenericCard {...cardContent('shadow10')} shadow={Shadows.white10}/>
        <View style={{height: 20}}/>
        <Cards.GenericCard {...cardContent('shadow20')} shadow={Shadows.white20}/>
        <View style={{height: 20}}/>
        <Cards.GenericCard {...cardContent('shadow30')} shadow={Shadows.white30}/>
        <View style={{height: 20}}/>
        <Cards.GenericCard {...cardContent('shadow40')} shadow={Shadows.white40}/>
      </View>);*/
  }

  renderShadows(shadowsList) {
    return []
      .concat(_.map(shadowsList, (value, key) => this.renderCircleWithShadow(value, key)))
      .concat(_.map(shadowsList, (value, key) => this.renderSquareWithShadow(value, key)));
  }

  renderCircleWithShadow(shadow, name) {

    /*return (
      <View key={`${name}_circle`} style={[styles.shadowCircle, shadow.top]}>
        <View style={[styles.innerCircle, shadow.bottom]}>
          <Text style={styles.shadowLabel}>{name}</Text>
        </View>
      </View>
    );*/

    console.log(' color ' + shadow.top.shadowColor);
    return (
      <ShapeContainer key={`${name}_circle`} style={[styles.shadowCircle, shadow.top]}>
        <ShapeContainer style={[styles.innerCircle, shadow.bottom]}>
          <Text style={styles.shadowLabel}>{name}</Text>
        </ShapeContainer>
      </ShapeContainer>
    );
  }

  renderSquareWithShadow(shadow, name) {
    /*const innerView = <View key={`${name}_square`} style={[styles.shadowSquare, shadow.top]}>
      <View style={[styles.innerSquare, shadow.bottom]}>
        <Text style={styles.shadowLabel}>{name}</Text>
      </View>
    </View>;

    return (
      <ShadowParentView key={`${name}_square`} shadowStyle={shadow.top}>
        {innerView}
      </ShadowParentView>);*/

    /*return (
      <View key={`${name}_square`} style={[styles.shadowSquare, shadow.top]}>
        <View style={[styles.innerSquare, shadow.bottom]}>
          <Text style={styles.shadowLabel}>{name}</Text>
        </View>
      </View>
    );*/

    return (
      <ShapeContainer key={`${name}_square`}
        style={[styles.shadowSquare, shadow.top]} shadowStyle={shadow.top}>
        <ShapeContainer style={[styles.innerSquare, shadow.bottom]} shadowStyle={shadow.bottom}>
          <Text style={styles.shadowLabel}>{name}</Text>
        </ShapeContainer>
      </ShapeContainer>
    );
  }

  renderButton(name, shadow) {
    const button = <Button
      style={shadow}
      label={name} onPress={() => { }}
      enableShadow />;

    if (ANDROID_PLATFORM) {
      return (<ShadowParentView style={shadow}>{button}</ShadowParentView>)
    }
    else {
      return (button);
    }
  }

  render() {
    return (
      <View>
        <View style={{ flexDirection: 'row', position: 'absolute', top: 0, left: 0, bottom: 0, right: 0 }}>
          <View style={[styles.subContainer, { backgroundColor: Colors.dark80 }]} />
          <View style={[styles.subContainer, { backgroundColor: Colors.white }]} />
        </View>
        <ScrollView>
          <View style={styles.container}>
            <View style={styles.subContainer}>
              {this.renderShadows(shadowsOverDarkBkg)}
            </View>
            <View style={styles.subContainer}>
              {this.renderShadows(shadowsOverWhiteBkg)}
            </View>
          </View>
          {this.renderCards()}
          <View style={styles.buttonContainer}>
            {this.renderButton('Normal Shadow', styles.shadowStyle1)}
          </View>
          <View style={styles.buttonContainer}>
            {this.renderButton('Max Shadow', styles.shadowStyle2)}
          </View>
        </ScrollView>
      </View>
    );
  }
}

const SHAPE_DIAMETER = 80;

const styles = StyleSheet.create({
  buttonContainer: {

    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginTop: 40,
    marginBottom: 40
  },
  container: {
    flexDirection: 'row',
    flex: 1,
    justifyContent: 'space-between',
  },
  subContainer: {
    flex: 1,
    flexDirection: 'column',
    minHeight: 0.8 * height,
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  shadowCircle: {
    width: SHAPE_DIAMETER,
    height: SHAPE_DIAMETER,
    borderRadius: SHAPE_DIAMETER / 2,
    backgroundColor: 'white',
    margin: 40,
    justifyContent: 'center',
    alignItems: 'center',
  },
  innerCircle: {
    width: SHAPE_DIAMETER,
    height: SHAPE_DIAMETER,
    borderRadius: SHAPE_DIAMETER / 2,
    backgroundColor: 'white',
    justifyContent: 'center',
  },
  shadowSquare: {
    width: SHAPE_DIAMETER,
    height: SHAPE_DIAMETER,
    borderRadius: 6,
    backgroundColor: 'white',
    margin: 40,
    justifyContent: 'center',
  },
  innerSquare: {
    width: SHAPE_DIAMETER,
    height: SHAPE_DIAMETER,
    borderRadius: 6,
    backgroundColor: 'white',
    justifyContent: 'center',
  },
  shadowLabel: {
    fontSize: 9,
    textAlign: 'center',
  },
  shadowStyle1: {
    shadowColor: '#3082C8',
    shadowOffset: { height: 5, width: 0 },
    shadowOpacity: 0.35,
    shadowRadius: 9.5,
  },
  shadowStyle2: {
    shadowColor: '#3082C8',
    shadowOffset: { height: 5, width: 0 },
    shadowOpacity: 1,
    shadowRadius: 25,
  },
});
