import { Route } from '@angular/router';

import { HomeExtendedComponent } from './';

export const HOME_EXTENDED_ROUTE: Route = {
    path: '',
    component: HomeExtendedComponent,
    data: {
        authorities: [],
        pageTitle: 'Welcome, to Soptorshi'
    }
};
