/**
 * Created by Yiding Liu on 11/30/2016.
 */
import Avater from './Avater'
import LogoutLink from '../../containers/LogoutLinkContainer'
import {Link} from 'react-router'
import React, { PropTypes } from 'react'

class Header extends React.Component{
    render(){
        return(
            <nav className="navbar navbar-default navbar-fixed-top">
                <div className="container-fluid">
                    <div className="navbar-header">
                        <Avater/>
                    </div>
                    <div className="collapse navbar-collapse" id="myNavbar">
                        <ul className="nav navbar-nav navbar-right">
                            <li><Link to="/Hotel">HOTEL LIST</Link></li>
                            <li><LogoutLink>LOG OUT</LogoutLink></li>
                        </ul>
                    </div>
                </div>
            </nav>
        );
    }
}
export default Header
//<li><Link to="/Reviews">TO SOMEWHERE</Link></li>