import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AdvanceManagementComponent,
    AdvanceManagementDetailComponent,
    AdvanceManagementUpdateComponent,
    AdvanceManagementDeletePopupComponent,
    AdvanceManagementDeleteDialogComponent,
    advanceManagementRoute,
    advanceManagementPopupRoute
} from './';

const ENTITY_STATES = [...advanceManagementRoute, ...advanceManagementPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdvanceManagementComponent,
        AdvanceManagementDetailComponent,
        AdvanceManagementUpdateComponent,
        AdvanceManagementDeleteDialogComponent,
        AdvanceManagementDeletePopupComponent
    ],
    entryComponents: [
        AdvanceManagementComponent,
        AdvanceManagementUpdateComponent,
        AdvanceManagementDeleteDialogComponent,
        AdvanceManagementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAdvanceManagementModule {}
