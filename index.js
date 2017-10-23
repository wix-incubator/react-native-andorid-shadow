
import { NativeModules,requireNativeComponent } from 'react-native';
// import PropTypes from 'prop-types';

// const { RNReactNativeAndoridShadow } = NativeModules;

// export default RNReactNativeAndoridShadow;

// var iface = {
//   name: 'ShadowParentView',
//   propTypes: {
//     shadowStyle: PropTypes.string,
    
//     ...View.propTypes // include the default view properties
//   },
// };

const ShadowParentView = requireNativeComponent('ShadowParentView',null);
const AndroidShadowManager = NativeModules.AndroidShadowManager;

// export default NativeModules.AndroidShadowManager;


module.exports = {AndroidShadowManager,ShadowParentView};


