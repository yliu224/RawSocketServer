/**
 * Created by Yiding Liu on 11/30/2016.
 */
import React from 'react';
import {Link} from 'react-router';

const RouteFilterLink=({filter,children})=>(
    <Link
        to={filter === 'all' ? '' : filter}
        activeStyle={{
            textDecoration:'none',
            color:'black'
        }}
    >
        {children}
    </Link>
);

export default RouteFilterLink;