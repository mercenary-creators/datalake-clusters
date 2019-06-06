import {RouteProps} from 'react-router';
import {ClustersDemo, DatalakeDemo} from './pages/demos';

interface IDemoRoute extends RouteProps {
    path: string;
    label: string;
}

export const routes: IDemoRoute[] = [
    {
        label: 'Clusters',
        path: '/clusters',
        component: ClustersDemo
    },
    {
        label: 'Datalake',
        path: '/datalake',
        component: DatalakeDemo
    },
];