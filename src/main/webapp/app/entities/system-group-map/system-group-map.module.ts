import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SystemGroupMapComponent,
    SystemGroupMapDetailComponent,
    SystemGroupMapUpdateComponent,
    SystemGroupMapDeletePopupComponent,
    SystemGroupMapDeleteDialogComponent,
    systemGroupMapRoute,
    systemGroupMapPopupRoute
} from './';

const ENTITY_STATES = [...systemGroupMapRoute, ...systemGroupMapPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SystemGroupMapComponent,
        SystemGroupMapDetailComponent,
        SystemGroupMapUpdateComponent,
        SystemGroupMapDeleteDialogComponent,
        SystemGroupMapDeletePopupComponent
    ],
    entryComponents: [
        SystemGroupMapComponent,
        SystemGroupMapUpdateComponent,
        SystemGroupMapDeleteDialogComponent,
        SystemGroupMapDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSystemGroupMapModule {}
