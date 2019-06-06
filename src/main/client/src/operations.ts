import {IOperation} from './pages/DemoPage';

const bindingOperation = (path: string): IOperation => {
    return {
        path,
        label: 'Bindings',
        dataJsonPath: '$..bindings[*]'
    }
};

const queryOperation = (path: string, label: string): IOperation => {
    return {
        path,
        label,
        dataJsonPath: '$..results[*]'
    }
};

export const clusterOperations: IOperation[] = [
    bindingOperation('/open/clusters'),
    queryOperation('/open/clusters/roles', 'Roles')
];

export const datalakeOperations: IOperation[] = [
    bindingOperation('/open/datalake'),
    {
        label: 'Posts',
        path: '/open/datalake/posts',
    },
    queryOperation('/open/datalake/users', 'Users')
];