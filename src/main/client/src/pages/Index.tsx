import React from 'react';
import {Theme, WithStyles, createStyles, withStyles} from '@material-ui/core/styles';
import {routes} from '../routes';
import {Route, Switch} from 'react-router';
import {AppBar, Tabs} from '@material-ui/core';
import {LinkTab} from '../components/LinkTab';

const styles = (theme: Theme) => createStyles({
    root: {
        textAlign: 'center',
        paddingTop: theme.spacing.unit * 20,
    },
});

interface State {
    selected: string;
}

interface Properties {
    main?: boolean
}

interface StyleProperties extends WithStyles<typeof styles> {
}

const Index = withStyles(styles)(
    class extends React.Component<Properties & StyleProperties, State> {

        constructor(props: Properties & StyleProperties) {
            super(props);
            const currentRoute = routes.find((r) => {
                return r.path === document.location.pathname
            });
            let defaultSelection = routes[0].label;
            if (currentRoute) {
                defaultSelection = currentRoute.label;
            }
            this.state = {
                selected: defaultSelection
            };
        }

        private onTabChange = (event: React.ChangeEvent<{}>, value: any) => {
            this.setState({selected: value})
        };

        render() {
            const {selected} = this.state;
            return <div>
                <AppBar position='static'>
                    <Tabs variant='fullWidth' value={selected} onChange={this.onTabChange}>
                        {routes.map((r, i) => <LinkTab key={i} label={r.label} value={r.label} to={r.path}/>)}
                    </Tabs>
                </AppBar>
                <Switch>
                    <Route {...routes[0]} path='/' exact={true}/>
                    {routes.map((r, i) => <Route key={i} {...r}/>)}
                </Switch>
            </div>;
        };
    }
);

export default Index;