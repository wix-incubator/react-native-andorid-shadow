
import { NativeModules,requireNativeComponent,View } from 'react-native';
import PropTypes from 'prop-types';

// const { RNReactNativeAndoridShadow } = NativeModules;

// export default RNReactNativeAndoridShadow;

var iface = {
  name: 'ShadowParentView',
  propTypes: {
    shadowStyle: PropTypes.object,
    useUIThread: PropTypes.bool,
    ...View.propTypes // include the default view properties
  }

};

const ShadowParentView = requireNativeComponent('ShadowParentView',iface,{nativeOnly: {
    nativeBackgroundAndroid: true,
    nativeForegroundAndroid: true
  }});
const AndroidShadowManager = NativeModules.AndroidShadowManager;

// export default NativeModules.AndroidShadowManager;


module.exports = {AndroidShadowManager,ShadowParentView};


