const webpack = require("webpack");
module.exports = {
	entry: [
		"react-hot-loader/patch",
		"./src/index.tsx"
	],

	output: {
		path: __dirname + '/public/dist/',
		filename: "main.js",
		publicPath: '/dist'
	},

	devtool: 'inline-source-map',

	resolve: {
		extensions: ['.js', '.jsx', '.ts', '.tsx']
	},

	plugins: [
		new webpack.NamedModulesPlugin(),
		new webpack.HotModuleReplacementPlugin(),
	],

	module: {
		rules: [
			{
				test: /\.less$/,
				use: [{
					loader: "style-loader" // creates style nodes from JS strings
				}, {
					loader: "css-loader" // translates CSS into CommonJS
				}, {
					loader: "less-loader" // compiles Sass to CSS
				}]
			},
			{
				test: /\.(graphql|gql)$/,
				exclude: /node_modules/,
				loader: ['graphql-tag/loader'],
			},
			{
				test: /\.(t|j)sx?$/,
				use: [
					"react-hot-loader/webpack",
					'awesome-typescript-loader',
				],
				exclude: /node_modules/,
			},
			{
				test: /\.(png|jpg)$/,
				loader: 'url-loader',
				options: {
					limit: 25000
				}
			},
			{
				test: /\.(eot|svg|ttf|woff|woff2)$/,
				loader: 'file-loader',
				options: {
					'name': 'public/fonts/[name].[ext]'
				}
			},
			{
				enforce: "pre",
				test: /\.js$/,
				loader: "source-map-loader",
				exclude: [/node_modules/, /dist/, /__test__/],
			}
		]
	},
	devServer: {
		hot: true,
		historyApiFallback: true
	}
};
