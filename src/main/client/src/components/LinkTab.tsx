import {RouteComponentProps, withRouter} from 'react-router';
import {TabProps} from '@material-ui/core/Tab';
import {Tab} from '@material-ui/core';
import React from 'react';

interface ILinkTabProps extends RouteComponentProps, TabProps {
    to: string;
}

export const LinkTab = withRouter<ILinkTabProps>(({history, to, ...props}) => {
    delete props.staticContext;
    return <Tab component='a' onClick={event => {
        event.preventDefault();
        history.push(to)
    }}{...props}
    />;
});