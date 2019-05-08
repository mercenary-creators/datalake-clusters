import React from 'react';
import Button from '@material-ui/core/Button';
import {Theme, WithStyles, createStyles, withStyles} from '@material-ui/core/styles';

const styles = (theme: Theme) => createStyles({
    root: {
        textAlign: 'center',
        paddingTop: theme.spacing.unit * 20,
    },
});

interface State {
    open: boolean
}

interface Properties {
    main?: boolean
}

interface StyleProperties extends WithStyles<typeof styles> {
}

const Index = withStyles(styles)(
    class extends React.Component<Properties & StyleProperties, State> {
        state = {
            open: false
        };
        open = () => {
            this.setState({
                open: true,
            });
        };
        done = () => {
            this.setState({
                open: false,
            });
        };

        render() {
            return <div>
                <Button onClick={this.open}>
                    Open
                </Button>
                <Button onClick={this.done}>
                    Done
                </Button>
            </div>
        };
    }
);

export default Index;