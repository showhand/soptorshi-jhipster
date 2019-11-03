import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPackagingComponent,
    CommercialPackagingDeleteDialogComponent,
    CommercialPackagingDeletePopupComponent,
    CommercialPackagingDetailComponent,
    commercialPackagingPopupRoute,
    commercialPackagingRoute,
    CommercialPackagingUpdateComponent
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
