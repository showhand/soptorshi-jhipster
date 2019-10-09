import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SystemGroupMapExtendedComponent,
    SystemGroupMapExtendedDetailComponent,
    SystemGroupMapExtendedUpdateComponent,
    systemGroupMapExtendedRoute,
    systemGroupMapExtendedPopupRoute
} from './';
import {
    SystemGroupMapComponent,
    SystemGroupMapDeleteDialogComponent,
    SystemGroupMapDeletePopupComponent,
    SystemGroupMapDetailComponent,
    systemGroupMapPopupRoute,
    SystemGroupMapUpdateComponent
} from 'app/entities/system-group-map';

const ENTITY_STATES = [...systemGroupMapExtendedRoute, ...systemGroupMapExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SystemGroupMapComponent,
        SystemGroupMapDetailComponent,
        SystemGroupMapUpdateComponent,
        SystemGroupMapExtendedComponent,
        SystemGroupMapExtendedDetailComponent,
        SystemGroupMapExtendedUpdateComponent,
        SystemGroupMapDeleteDialogComponent,
        SystemGroupMapDeletePopupComponent
    ],
    entryComponents: [
        SystemGroupMapExtendedComponent,
        SystemGroupMapExtendedUpdateComponent,
        SystemGroupMapDeleteDialogComponent,
        SystemGroupMapDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSystemGroupMapModule {}
