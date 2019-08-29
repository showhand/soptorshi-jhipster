import { Route } from '@angular/router';

import { HomeComponent } from './';

export const HOME_ROUTE: Route = {
    path: 'home-legacy',
    component: HomeComponent,
    data: {
        authorities: [],
        pageTitle: 'Welcome, to Soptorshi'
    }
};
