/**
 * Created by chkui on 2016/11/16.
 */
import React from 'react'
import {render} from 'react-dom'
import {Provider} from 'react-redux'
import {createStore,applyMiddleware} from 'redux'
import thunk from 'redux-thunk'
import AppReducer from './reducers'
import App from './components/App'
import {Router,Route,browserHistory,IndexRoute} from 'react-router'
import HotelListContainer from './containers/HotelListContainer'
import HotelDetailContainer from './containers/HotelDetailContainer'
import Login from './components/Login'

let middleware=applyMiddleware(thunk)
let store = createStore(AppReducer,middleware);
render(
    <Provider store={store}>
        <Router history={browserHistory}>
            <Route path="/Home/Login" component={Login} />
            <Route path="/Hotel" component={App}>
                <IndexRoute component={HotelListContainer} />
                <Route path="/Review/:hotelId" component={HotelDetailContainer} />
            </Route>
        </Router>
    </Provider>,
    document.getElementById('root')
);
