/**
 * Created by Yiding Liu on 11/28/2016.
 */
import React from 'react'
import Header from './Headers/Header'

class App extends React.Component{
    render(){
        let {params,children}=this.props;
        return (
            <div>
                <Header/>
                {children}
            </div>
        );
    }
}

export default App