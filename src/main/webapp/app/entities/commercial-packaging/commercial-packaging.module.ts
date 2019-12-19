import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPackagingComponent,
    CommercialPackagingDetailComponent,
    CommercialPackagingUpdateComponent,
    CommercialPackagingDeletePopupComponent,
    CommercialPackagingDeleteDialogComponent,
    commercialPackagingRoute,
    commercialPackagingPopupRoute
} from './';

const ENTITY_STATES = [...commercialPackagingRoute, ...commercialPackagingPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPackagingComponent,
        CommercialPackagingDetailComponent,
        CommercialPackagingUpdateComponent,
        CommercialPackagingDeleteDialogComponent,
        CommercialPackagingDeletePopupComponent
    ],
    entryComponents: [
        CommercialPackagingComponent,
        CommercialPackagingUpdateComponent,
        CommercialPackagingDeleteDialogComponent,
        CommercialPackagingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPackagingModule {}
