import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    FineManagementComponent,
    FineManagementDetailComponent,
    FineManagementUpdateComponent,
    FineManagementDeletePopupComponent,
    FineManagementDeleteDialogComponent,
    fineManagementRoute,
    fineManagementPopupRoute
} from './';

const ENTITY_STATES = [...fineManagementRoute, ...fineManagementPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FineManagementComponent,
        FineManagementDetailComponent,
        FineManagementUpdateComponent,
        FineManagementDeleteDialogComponent,
        FineManagementDeletePopupComponent
    ],
    entryComponents: [
        FineManagementComponent,
        FineManagementUpdateComponent,
        FineManagementDeleteDialogComponent,
        FineManagementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiFineManagementModule {}
