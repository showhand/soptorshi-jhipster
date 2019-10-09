import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SystemAccountMapExtendedComponent,
    SystemAccountMapExtendedDetailComponent,
    SystemAccountMapExtendedUpdateComponent,
    systemAccountMapExtendedRoute,
    systemAccountMapExtendedPopupRoute
} from './';
import {
    SystemAccountMapComponent,
    SystemAccountMapDeleteDialogComponent,
    SystemAccountMapDeletePopupComponent,
    SystemAccountMapDetailComponent,
    SystemAccountMapUpdateComponent
} from 'app/entities/system-account-map';

const ENTITY_STATES = [...systemAccountMapExtendedRoute, ...systemAccountMapExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SystemAccountMapComponent,
        SystemAccountMapDetailComponent,
        SystemAccountMapUpdateComponent,
        SystemAccountMapExtendedComponent,
        SystemAccountMapExtendedDetailComponent,
        SystemAccountMapExtendedUpdateComponent,
        SystemAccountMapDeleteDialogComponent,
        SystemAccountMapDeletePopupComponent
    ],
    entryComponents: [
        SystemAccountMapExtendedComponent,
        SystemAccountMapExtendedUpdateComponent,
        SystemAccountMapDeleteDialogComponent,
        SystemAccountMapDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSystemAccountMapModule {}
