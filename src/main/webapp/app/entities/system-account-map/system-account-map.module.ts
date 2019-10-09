import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SystemAccountMapComponent,
    SystemAccountMapDetailComponent,
    SystemAccountMapUpdateComponent,
    SystemAccountMapDeletePopupComponent,
    SystemAccountMapDeleteDialogComponent,
    systemAccountMapRoute,
    systemAccountMapPopupRoute
} from './';

const ENTITY_STATES = [...systemAccountMapRoute, ...systemAccountMapPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [
        SystemAccountMapComponent,
        SystemAccountMapDetailComponent,
        SystemAccountMapUpdateComponent,
        SystemAccountMapDeleteDialogComponent,
        SystemAccountMapDeletePopupComponent
    ],
    entryComponents: [
        SystemAccountMapComponent,
        SystemAccountMapUpdateComponent,
        SystemAccountMapDeleteDialogComponent,
        SystemAccountMapDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSystemAccountMapModule {}
