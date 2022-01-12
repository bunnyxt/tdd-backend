const path = require('path');
const nodeExternals = require('webpack-node-externals');

module.exports = {
  entry: './src/app.js',
  output: {
    filename: 'app.js',
    path: path.resolve(__dirname, 'dist'),
  },
  mode: 'development',
  externalsPresets: { node: true },
  externals: [nodeExternals()]
};
