/**
 * Created by chkui on 2016/11/16.
 */
module.exports = {
    entry: './index.js',
    output: {
        path: __dirname,
        filename: 'bundle.js'
    },
    debug:true,
    devtool:"#eval-source-map",
    module: {
        loaders: [
            {test: /\.css$/, loader: 'style!css'},
            {
                test: /\.js[x]?$/,
                exclude:/node_modules/,
                loader:"babel-loader",query: {
                presets: ['stage-0','es2015','react']
            }}
        ]
    }
}