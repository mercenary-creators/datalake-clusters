import React from 'react';
import {DemoPage} from './DemoPage';
import {clusterOperations, datalakeOperations} from '../operations';

export const ClustersDemo = () => <DemoPage operations={clusterOperations}/>;
export const DatalakeDemo = () => <DemoPage operations={datalakeOperations}/>;